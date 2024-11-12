package Phonebook_Package;

import java.io.Serializable;

// Class with parameters for persons
public class Person implements Serializable {

    // Instance Variables
    private String name;
    private int age;
    private boolean status;

    // Constructor
    public Person(String name, int age, boolean status) {
        this.name = name;
        this.age = age;
        this.status = status;
    }

    // get-/set Methods
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public boolean getStatus() {
        return status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    // String-to-String Method - returns the string itself
    public String toString() {
        return this.name + this.age + this.status;
    }
}