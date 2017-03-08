package impl;



import spec.Contact;
import spec.Meeting;


import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

/**
 * A class to represent meetings.
 *
 * Meetings have unique IDs, scheduled date and a list of participating contacts
 */
public class MeetingImpl implements Meeting, Comparable, Serializable{

    private final int id;
    private final Calendar date;
    private final Set<Contact> contacts;
    /**
     * Creates a new meeting.
     * @param id the unique id of a meeting
     * @param date the date on which the meeting will take place
     * @param contacts a set of contacts that will participate in the meeting
     * @throws IllegalArgumentException if the set is empty or if the id of the
     * meeting is negative.
     * @throws NullPointerException if the contacts or the date are null.
     */
    public MeetingImpl(int id, Calendar date, Set<Contact> contacts) throws IllegalArgumentException, NullPointerException {
        if (contacts == null || date == null) {
            throw new NullPointerException();
        }
        if (contacts.size() == 0) {
            throw new IllegalArgumentException("No contacts are given");
        }
        if (id <= 0) {
            throw new IllegalArgumentException("Wrong Id");
        }

        this.id = id;
        this.date = date;
        this.contacts = contacts;
    }
    /**
     * Returns the id of the meeting.
     *
     * @return the id of the meeting.
     */
    public int getId() {

        return id;
    }

    /**
     * Return the date of the meeting.
     *
     * @return the date of the meeting.
     */
    public Calendar getDate() {

        return date;
    }

    /**
     * Return the details of people that attended the meeting.
     *
     * The list contains a minimum of one contact (if there were
     * just two people: the user and the contact) and may contain an
     * arbitrary number of them.
     *
     * @return the details of people that attended the meeting.
     */
    public Set<Contact> getContacts() {

        return contacts;
    }

    /**
     * Compares this object with the specified object for order. Returns a negative integer, zero, or a positive integer
     * as this object is less than, equal to, or greater than the specified object.
     * @param o the object to be compared
     * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the
     * specified object.
     */
    @Override
    public int compareTo(Object o) {
        Meeting c = (Meeting) o;
        if (id == c.getId() && date.compareTo(c.getDate()) == 0 && contacts.equals(c.getContacts()) == true) {
            return 0;
        } else {

            return -1;
        }

    }

}
