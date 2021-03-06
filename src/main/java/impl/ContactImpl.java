package impl;

import java.io.Serializable;

import spec.Contact;




/**
 * A contact is a person we are making business with or may do in the future.
 * Contacts have an ID (unique, a non-zero positive integer),
 * a name (not necessarily unique), and notes that the user
 * may want to save about them.
 */


public class ContactImpl implements Contact, Comparable, Serializable {
  /** The ID of the contact.*/
  private final int contactId;
  /**
   *  The name of the contact.
   */
  private final String contactName;
  /**
   *Notes for the contact.
   */
  private String contactNotes;
  /**
   * The constructor of contact.
   * @param id non-negative
   * @param name the name of the contact
   * @param notes notes about the contact
   * @throws IllegalArgumentException if the Id of the contact is negative
   * @throws NullPointerException if the name or the notes are null.
   */

  public ContactImpl(final int id, final String name, final String notes) throws
          IllegalArgumentException, NullPointerException {
    if (id <= 0) {
      throw new IllegalArgumentException("Invalid ID!");
    }
    if (name == null || notes == null) {
      throw new NullPointerException("warning null values");
    }
    contactId = id;
    contactName = name;
    contactNotes = notes;
  }

  /**
   *  The second constructor of contact.
   * @param id the unique id of the contact
   * @param name the name of the contact
   * @throws IllegalArgumentException if the id of the contact is negative
   * @throws NullPointerException if the name of the contact is null
   */
  public ContactImpl(int id, String name) throws
          IllegalArgumentException, NullPointerException {
    this(id, name, "");
  }
  /**
   * Returns the ID of the contact.
   *
   * @return the ID of the contact.
   */

  public int getId() {
    return  contactId;
  }
  /**
   * Returns the name of the contact.
   * @return the name of the contact.
   */

  public String getName() {
    return  contactName;
  }
  /**
   * Returns our notes about the contact, if any.
   * If we have not written anything about the contact, the empty
   * string is returned.
   *
   * @return a string with notes about the contact, maybe empty.
   */

  public String getNotes() {
    return contactNotes;
  }
  /**
   * Add notes about the contact.
   *
   * @param note the notes to be added
   */

  public void addNotes(String note) {
    contactNotes = note;
  }



  /** Compares this object with the specified object for order.
    * Returns a negative integer, zero, or
    * a positive integer as this object is less than, equal to,
    * or greater than the specified object.
    * @param o the object to be compared
    *
    * @return a negative integer, zero, or a positive integer as this object is less than,equal to,
    or greater than the specified object
    */

  @Override
  public int compareTo(Object o) {
    Contact c = (Contact) o;
    if (contactId == c.getId() && contactName.compareTo(c.getName()) == 0
            && contactNotes.compareTo(c.getNotes()) == 0) {
      return 0;
    } else {
      return -1;
    }
  }

  /**
   * A hash code value for the object.
   *
   * @return a hash code value for this object.
   */
  @Override
  public int hashCode() {
    int hash = contactId;
    hash += contactName.hashCode() + contactNotes.hashCode();
    return hash;
  }
}

