package impl;

import spec.Contact;

import java.io.Serializable;


/**
 * A contact is a person we are making business with or may do in the future.
 *
 * Contacts have an ID (unique, a non-zero positive integer),
 * a name (not necessarily unique), and notes that the user
 * may want to save about them.
 */


public class ContactImpl implements Contact, Comparable, Serializable {

    private final int id;
    private final String name;
    private String notes;


    public ContactImpl(int id, String name, String notes) throws IllegalArgumentException, NullPointerException {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid ID!");
        }

        if (name == null || notes == null) {
            throw new NullPointerException();
        }

        this.id = id;
        this.name = name;
        this.notes = notes;


    }

    public ContactImpl(int id, String name) throws IllegalArgumentException, NullPointerException {
        this(id, name, "");
    }

    /**
     * Returns the ID of the contact.
     *
     * @return the ID of the contact.
     */
    public int getId() {
        return  id;
    }

    /**
     * Returns the name of the contact.
     *
     * @return the name of the contact.
     */
    public String getName() { return  name; }

    /**
     * Returns our notes about the contact, if any.
     *
     * If we have not written anything about the contact, the empty
     * string is returned.
     *
     * @return a string with notes about the contact, maybe empty.
     */
    public String getNotes() { return notes; }

    /**
     * Add notes about the contact.
     *
     * @param note the notes to be added
     */
    public void addNotes(String note) { notes = note; }
    /**
     * Compares this object with the specified object for order. Returns a negative integer, zero, or a positive integer
     * as this object is less than, equal to, or greater than the specified object.
     * @param o the object to be compared
     * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the
     * specified object.
     */
    @Override
    public int compareTo(Object o) {
        Contact c = (Contact)o;
        if(id == c.getId() && name.compareTo(c.getName()) == 0 && notes.compareTo(c.getNotes()) == 0){
            return 0;
        }else{
            return -1;
        }
    }


}

