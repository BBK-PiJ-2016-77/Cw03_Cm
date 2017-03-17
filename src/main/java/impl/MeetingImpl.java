package impl;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

import spec.Contact;
import spec.Meeting;




/**
 * A class to represent meetings.
 * Meetings have unique IDs, scheduled date and a list of participating contacts
 */
public class MeetingImpl implements Meeting, Comparable, Serializable {
  /**
   * The ID of the meeting.
   */
  private int meetingId;
  /**
   * The scheduled date of the meeting.
   */
  private Calendar meetingDate;
  /**
   * The set of contacts that will participate in the meeting.
   */

  private Set<Contact> contactsOfMeeting;

  /**
   * Creates a new meeting.
   *
   * @param id the unique id of a meeting
   * @param date the date on which the meeting will take place
   * @param contacts a set of contacts that will participate in the meeting
   * @throws IllegalArgumentException if the set is empty or if the id of the meeting is negative.
   * @throws NullPointerException     if the contacts or the date are null.
   */

  public MeetingImpl(int id, Calendar date,
                     Set<Contact> contacts) throws
          IllegalArgumentException, NullPointerException {
    if (contacts == null || date == null) {
      throw new NullPointerException();
    }
    if (contacts.size() == 0) {
      throw new IllegalArgumentException("No contacts are given");
    }
    if (id <= 0) {
      throw new IllegalArgumentException("Wrong Id");
    }
    this.meetingId = id;
    this.meetingDate = date;
    this.contactsOfMeeting = contacts;
  }

  /**
   * Returns the id of the meeting.
   *
   * @return the id of the meeting.
   */

  public int getId() {
    return meetingId;
  }

  /**
   * Return the date of the meeting.
   *
   * @return the date of the meeting.
   */

  public Calendar getDate() {
    return meetingDate;
  }

  /**
   * Return the details of people that attended the meeting.
   * The list contains a minimum of one contact (if there were
   * just two people: the user and the contact) and may contain an
   * arbitrary number of them.
   *
   * @return the details of people that attended the meeting.
   */

  public Set<Contact> getContacts() {
    return contactsOfMeeting;
  }

  /**
   * Compares this object with the specified object for order.
   * Returns a negative integer, zero, or a positive integer
   * as this object is less than, equal to,
   * or greater than the specified object.
   *
   * @param o the object to be compared
   * @return a negative integer, zero, or a positive integer as this object is less than, equal to,
   * or greater than the specified object
   */

  @Override
  public int compareTo(Object o) {

    Meeting c = (Meeting) o;
    return meetingDate.compareTo(c.getDate());

  }

  @Override
  public boolean equals(Object o) {
    if(o == null) {
      return false;
    }
    if(!(o instanceof Meeting)) {
      return false;
    }
    Meeting c = (Meeting) o;
    return (meetingId == c.getId() && meetingDate.compareTo(c.getDate()) == 0
            && contactsOfMeeting.equals(c.getContacts()));
  }

  @Override
  public int hashCode() {
    int hash = meetingId;
    hash+= meetingDate.hashCode();
    for(Contact contact : contactsOfMeeting){
      hash+= contact.hashCode();
    }
    return hash;
  }
}