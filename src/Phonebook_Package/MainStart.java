package Phonebook_Package;

// Main class for project start
public class MainStart {
    public static void main(String[] args) {

        // Create new GUI
        PhonebookView phonebookGUI = new PhonebookView();

        // Set default entries
        phonebookGUI.setEntry(12322455, "Harry Kane", true);
        phonebookGUI.setEntry(26457300, "Olaf Scholz", true);
        phonebookGUI.setEntry(34523489, "Harry Potter", false);
        phonebookGUI.setEntry(46575775, "Günter Jauch", true);
        phonebookGUI.setEntry(52256547, "Frank Sinatra", true);
        phonebookGUI.setEntry(67836637, "Daniel Son", false);
        phonebookGUI.setEntry(76746637, "Ruud van Nistelroy", true);
        phonebookGUI.setEntry(88946637, "Heribert Fassbender", false);
        phonebookGUI.setEntry(94523444, "James Dean", true);
    }
}