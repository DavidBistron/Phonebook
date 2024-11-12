import java.io.Serializable;

public class Person implements Serializable {

    // Instance Variables
    private long id;
    private String name;
    private int age;
    private boolean presence;

    // Constructor
    public Person(String name, int age, boolean presence) {
        this.name = name;
        this.age = age;
        this.presence = presence;
    }

    // get-/set Methods
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public boolean getPresence() {
        return presence;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setPresence(boolean presence) {
        this.presence = presence;
    }

    // String-to-String Method
    public String toString() {
        return this.name + this.age + this.presence;
    }
}
