package Phonebook_Package;

import java.io.Serial;
import java.io.Serializable;
import java.util.Comparator;

// Class for creating persons
public class PhonebookEntry implements Serializable {

    // Variables
    private int number;
    private String name;
    private boolean isPrivate;

    @Serial
    private static final long serialVersionUID = 1L;

    // Constructor - phonebookEntry contains a number, a name and an option for private or not private
    public PhonebookEntry(int number, String name, boolean isPrivate) {
        super();
        this.number = number;
        this.name = name;
        this.isPrivate = isPrivate;
    }

    // get-/set Methods
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    // Method - sort number descending
    public static class CompNumberAsc implements Comparator<PhonebookEntry> {
        public int compare(PhonebookEntry e1, PhonebookEntry e2) {
            return e1.getNumber() - e2.getNumber();
        }
    }

    // Method - sort name ascending
    public static class CompNameAsc implements Comparator<PhonebookEntry> {
        public int compare(PhonebookEntry e1, PhonebookEntry e2) {
            return e1.getName().compareTo(e2.getName());
        }
    }

    // Method - sort is private entry or not
    public static class CompIsPrivateAsc implements Comparator<PhonebookEntry> {
        public int compare(PhonebookEntry e1, PhonebookEntry e2) {
            return Boolean.compare(e1.isPrivate(), e2.isPrivate());
        }
    }
}