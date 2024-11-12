package Phonebook_Package;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class PersonTableModel extends AbstractTableModel {

    private ArrayList<Person> myList;

    // Constructor
    public PersonTableModel(ArrayList<Person> myList) {
        this.myList = myList;
        initModelData();
    }

    private void initModelData() {
        this.addTableModelListener(null);
    }

    private String[] columnNames = {"Name", "Age", "Presence"};

    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return myList.size();
    }

    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    public Object getValueAt(int row, int col) {
        return switch (col) {
            case 0 -> myList.get(row).getName();
            case 1 -> myList.get(row).getAge();
            case 2 -> myList.get(row).getStatus();
            default -> null;
        };
    }

    public boolean isCellEditable(int row, int col) {
        return true;
    }

    public void setValueAt(Object value, int row, int col) {
        Person entry = myList.get(row);
        switch (col) {
            case 0: // name
                entry.setName((String) value);
                break;
            case 1: // age
                entry.setAge((int) value);
                break;
            case 2: // presence
                entry.setStatus((boolean) value);
                break;
        }
    }
}