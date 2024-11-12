package Phonebook_Package;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JCheckBox;

// Class for window to add new phonebook entries
public class PhonebookAddEntryView extends JFrame {
    private final JTextField textField_NumberInput;
    private final JTextField textField_NameInput;

    // Create View for new entry
    public PhonebookAddEntryView(PhonebookTableModel phonebookTableModel) {

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Add Entry");
        setLocationRelativeTo(null);
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 0, 0, 0 };
        gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0 };
        gridBagLayout.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
        gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
        getContentPane().setLayout(gridBagLayout);

        JLabel lblNumber = new JLabel("Number:");
        GridBagConstraints gbc_lblNumber = new GridBagConstraints();
        gbc_lblNumber.anchor = GridBagConstraints.EAST;
        gbc_lblNumber.insets = new Insets(5, 5, 5, 5);
        gbc_lblNumber.gridx = 0;
        gbc_lblNumber.gridy = 0;
        getContentPane().add(lblNumber, gbc_lblNumber);

        textField_NumberInput = new JTextField();
        GridBagConstraints gbc_textField = new GridBagConstraints();
        gbc_textField.insets = new Insets(5, 0, 5, 5);
        gbc_textField.fill = GridBagConstraints.HORIZONTAL;
        gbc_textField.gridx = 1;
        gbc_textField.gridy = 0;
        getContentPane().add(textField_NumberInput, gbc_textField);
        textField_NumberInput.setColumns(10);

        JLabel lblName = new JLabel("Name:");
        GridBagConstraints gbc_lblName = new GridBagConstraints();
        gbc_lblName.anchor = GridBagConstraints.EAST;
        gbc_lblName.insets = new Insets(0, 0, 5, 5);
        gbc_lblName.gridx = 0;
        gbc_lblName.gridy = 1;
        getContentPane().add(lblName, gbc_lblName);

        textField_NameInput = new JTextField();
        GridBagConstraints gbc_textField_1 = new GridBagConstraints();
        gbc_textField_1.insets = new Insets(0, 0, 5, 5);
        gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
        gbc_textField_1.gridx = 1;
        gbc_textField_1.gridy = 1;
        getContentPane().add(textField_NameInput, gbc_textField_1);
        textField_NameInput.setColumns(10);

        JCheckBox checkBoxIsPrivate = new JCheckBox("isPrivate");
        GridBagConstraints gbc_checkBoxIsPrivate = new GridBagConstraints();
        gbc_checkBoxIsPrivate.insets = new Insets(0, 0, 5, 0);
        gbc_checkBoxIsPrivate.gridx = 1;
        gbc_checkBoxIsPrivate.gridy = 2;
        getContentPane().add(checkBoxIsPrivate, gbc_checkBoxIsPrivate);

        JButton btnAdd = new JButton("Add");
        GridBagConstraints gbc_btnAdd = new GridBagConstraints();
        gbc_btnAdd.insets = new Insets(0, 0, 5, 0);
        gbc_btnAdd.gridx = 1;
        gbc_btnAdd.gridy = 3;
        getContentPane().add(btnAdd, gbc_btnAdd);
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int number = -1;

                try {
                    number = Integer.parseInt(textField_NumberInput.getText());
                    String name = textField_NameInput.getText();
                    boolean isPrivate = checkBoxIsPrivate.isSelected();
                    phonebookTableModel.setEntry(number, name, isPrivate);
                    phonebookTableModel.fireTableDataChanged();
                    dispose();
                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage(), "NumberFormatError",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        pack();
        setVisible(true);
    }
}
