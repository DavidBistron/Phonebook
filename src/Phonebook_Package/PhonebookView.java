package Phonebook_Package;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class PhonebookView extends JFrame {

    // Variables
    private final JTable table;
    private ArrayList<PhonebookEntry> entries = new ArrayList<>();
    private final PhonebookTableModel phonebookTableModel;
    private final JMenuItem menuSaveItem;
    private final JMenuItem menuOpenItem;
    private final JMenuItem menuItemCW;
    private final JComboBox comboBox;

    // Constructor
    public PhonebookView() {

        // Better look and feel of whole phonebook view
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        // Frame Options
        setTitle("Phonebook_Package");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout(0, 0));

        // Panel for Btn's
        JPanel panel = new JPanel();
        getContentPane().add(panel, BorderLayout.SOUTH);
        panel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));

        // Btn to sort entries
        JButton btnSortBy = new JButton("Sort by:");
        panel.add(btnSortBy);
        btnSortBy.addActionListener(new BtnSortListener());

        String[] cbItems = { "Number", "Name", "isPrivate" };
        comboBox = new JComboBox(cbItems);
        panel.add(comboBox);

        // Btn to add new entries
        JButton btnAdd = new JButton("Add");
        panel.add(btnAdd);
        btnAdd.addActionListener(new BtnAddListener());

        // Btn to delete entries
        JButton btnDelete = new JButton("Delete");
        panel.add(btnDelete);
        btnDelete.addActionListener(new BtnDeleteListener());

        // Btn to quit program
        JButton btnQuit = new JButton("Quit");
        panel.add(btnQuit);
        btnQuit.addActionListener(new BtnQuitListener());

        // Panel header
        JPanel panel_1 = new JPanel();
        getContentPane().add(panel_1, BorderLayout.NORTH);
        panel_1.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        panel_1.setBackground(Color.ORANGE);

        // Label header
        JLabel lblPhonebook = new JLabel("PhoneBook");
        lblPhonebook.setHorizontalAlignment(SwingConstants.LEFT);
        panel_1.add(lblPhonebook);

        // Add scroll
        JScrollPane scrollPane = new JScrollPane();
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        phonebookTableModel = new PhonebookTableModel(entries);
        table = new JTable(phonebookTableModel);
        scrollPane.setViewportView(table);

        // Top Menu
        JMenuBar menuBar = new JMenuBar();
        JMenu menuAbout = new JMenu("About");
        JMenu menuFile = new JMenu("File");
        menuItemCW = new JMenuItem("Copyright");
        menuAbout.add(menuItemCW);
        menuSaveItem = new JMenuItem("Save...");
        menuOpenItem = new JMenuItem("Open...");
        menuFile.add(menuSaveItem);
        menuFile.add(menuOpenItem);
        menuBar.add(menuFile);
        menuBar.add(menuAbout);
        setJMenuBar(menuBar);
        menuItemCW.addActionListener(new MenuItemListener());
        menuSaveItem.addActionListener(new MenuItemListener());
        menuOpenItem.addActionListener(new MenuItemListener());

        pack();
        setVisible(true);
    }

    public void setEntry(int number, String name, boolean isPrivate) {
        phonebookTableModel.setEntry(number, name, isPrivate);
        phonebookTableModel.fireTableDataChanged();
    }

    private class BtnQuitListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            dispose();
            System.exit(0);
        }
    }

    private class BtnDeleteListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            entries.remove(table.getSelectedRow());
            phonebookTableModel.fireTableDataChanged();
        }
    }

    private class BtnAddListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            new PhonebookAddEntryView(phonebookTableModel);
        }
    }

    private class BtnSortListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String sort = (String) comboBox.getSelectedItem();

            switch (sort) {
                case "Number":
                    entries.sort(new PhonebookEntry.CompNumberAsc());
                    phonebookTableModel.fireTableDataChanged();
                    break;
                case "Name":
                    entries.sort(new PhonebookEntry.CompNameAsc());
                    phonebookTableModel.fireTableDataChanged();
                    break;
                case "isPrivate":
                    entries.sort(new PhonebookEntry.CompIsPrivateAsc());
                    phonebookTableModel.fireTableDataChanged();
                    break;
            }
        }
    }

    // Action Listener for Menu
    private class MenuItemListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == menuItemCW) {
                JOptionPane.showMessageDialog(null, "© David Bistron", "Copyright",
                        JOptionPane.PLAIN_MESSAGE);
            }
            if (e.getSource() == menuSaveItem) {
                safeObj(entries);
            }
            if (e.getSource() == menuOpenItem) {
                entries = loadObj();
                phonebookTableModel.fireTableDataChanged();
            }
        }
    }

    /* Method to sava phonebook as a file */
    public void safeObj(ArrayList<PhonebookEntry> entries) {

        File file;
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        JFileChooser fc = new JFileChooser();

        if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                file = fc.getSelectedFile();
                fos = new FileOutputStream(file);
                oos = new ObjectOutputStream(fos);
                oos.writeObject(entries);

            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "IO Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                try {
                    if (oos != null) {
                        oos.close();
                    }
                    if (fos != null) {
                        fos.close();
                    }
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(this, e.getMessage(), "IO Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    // Method to load saved phonebook files
    @SuppressWarnings("unchecked")
    public ArrayList<PhonebookEntry> loadObj() {

        ArrayList<PhonebookEntry> entries = null;
        File file;
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        JFileChooser fc = new JFileChooser();

        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                file = fc.getSelectedFile();
                fis = new FileInputStream(file);
                ois = new ObjectInputStream(fis);
                Object obj = ois.readObject();
                if (obj instanceof ArrayList<?>) {
                    entries = (ArrayList<PhonebookEntry>) obj;
                }

            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "IO Error", JOptionPane.ERROR_MESSAGE);
            } catch (ClassNotFoundException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "ClassNotFound Error", JOptionPane.ERROR_MESSAGE);
            } finally {

                try {
                    if (ois != null) {
                        ois.close();
                    }
                    if (fis != null) {
                        fis.close();
                    }
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(this, e.getMessage(), "IO Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        return entries;
    }
}