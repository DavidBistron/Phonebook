package Phonebook_Package;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

// Class with an ArrayList for all entries
public class PhonebookTableModel extends AbstractTableModel {

    ArrayList<PhonebookEntry> entries;
    String [] columnNames = {"Number", "Name" ,"isPrivate"};

    public PhonebookTableModel(ArrayList<PhonebookEntry> list) {
        this.entries = list;
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return entries.size();
    }

    //
    public Object getValueAt(int row, int col) {
        return switch (col) {
            case 0 -> entries.get(row).isPrivate() ? entries.get(row).getNumber() : "Contact";
            case 1 -> entries.get(row).isPrivate() ? entries.get(row).getName() : "Contact";
            case 2 -> entries.get(row).isPrivate();
            default -> null;
        };
    }

    // Method for adding new entries to phonebook
    public void setValueAt(Object value, int row, int col) {
        PhonebookEntry entry = entries.get(row);
        switch(col) {
            case 0: entry.setNumber((int) value);
                break;
            case 1: entry.setName((String) value);
                break;
            case 2: entry.setPrivate((boolean) value);
                fireTableDataChanged();
                break;
        }
    }

    // Entries can be edited afterwards
    public boolean isCellEditable(int row, int col) {
        return true;
    }

    // Show colum names in view
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @SuppressWarnings({"rawtypes"})
    public Class getColumnClass(int col) {
        return getValueAt(0,col).getClass();
    }

    public void setEntry(int number, String name, boolean isPrivate) {
        entries.add(new PhonebookEntry(number, name, isPrivate));
    }
}