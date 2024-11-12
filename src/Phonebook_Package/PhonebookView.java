package Phonebook_Package;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;

public class PhonebookView extends JFrame {
    private JTable table;
    private ArrayList<PhonebookEntry> entries = new ArrayList<>();
    private PhonebookTableModel phonebookTableModel;
    private JMenuItem menuSaveItem;
    private JMenuItem menuOpenItem;
    private JMenuItem menuItemCW;
    private JComboBox comboBox;

    public PhonebookView() {

        setTitle("Phonebook_Package");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        getContentPane().add(panel, BorderLayout.SOUTH);
        panel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));

        JButton btnSortBy = new JButton("Sort by:");
        panel.add(btnSortBy);
        btnSortBy.addActionListener(new BtnSortListener());

        String[] cbItems = { "Number", "Name", "isPrivate" };
        comboBox = new JComboBox(cbItems);
        panel.add(comboBox);

        JButton btnAdd = new JButton("Add");
        panel.add(btnAdd);
        btnAdd.addActionListener(new BtnAddListener());

        JButton btnDelete = new JButton("Delete");
        panel.add(btnDelete);
        btnDelete.addActionListener(new BtnDeleteListener());

        JButton btnQuit = new JButton("Quit");
        panel.add(btnQuit);
        btnQuit.addActionListener(new BtnQuitListener());

        JPanel panel_1 = new JPanel();
        getContentPane().add(panel_1, BorderLayout.NORTH);
        panel_1.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

        JLabel lblPhonebookByMir = new JLabel("PhoneBook by mir Private Mode");
        lblPhonebookByMir.setHorizontalAlignment(SwingConstants.LEFT);
        panel_1.add(lblPhonebookByMir);

        JScrollPane scrollPane = new JScrollPane();
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        phonebookTableModel = new PhonebookTableModel(entries);
        table = new JTable(phonebookTableModel);
        scrollPane.setViewportView(table);

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
                    Collections.sort(entries, new PhonebookEntry.CompNumberAsc());
                    phonebookTableModel.fireTableDataChanged();
                    break;
                case "Name":
                    Collections.sort(entries, new PhonebookEntry.CompNameAsc());
                    phonebookTableModel.fireTableDataChanged();
                    break;
                case "isPrivate":
                    Collections.sort(entries, new PhonebookEntry.CompIsPrivateAsc());
                    phonebookTableModel.fireTableDataChanged();
                    break;
            }
        }
    }

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