package impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import java.util.logging.Level;
import java.util.logging.Logger;

import spec.Contact;
import spec.ContactManager;
import spec.FutureMeeting;
import spec.Meeting;
import spec.PastMeeting;




/**
 * A class to manage your contacts and meetings.
 */

public class ContactManagerImpl implements ContactManager, Serializable {
  /**
   * Creating a logger to log the errors.
   */
  private static Logger logger = Logger
      .getLogger(ContactManagerImpl.class.getName());
  /**
   * A set of contacts.
   */
  private Set<Contact> setOfContacts = new TreeSet<>();
  /**
   * A list of meetings.
   */
  private List<Meeting> listOfMeetings = new ArrayList<>();
  /**
   * Unique meetingId produced by Sequence class.
   */
  private Sequence meetingId = new Sequence();
  /**
   * Unique contactId produced by Sequence class.
   */
  private Sequence contactId = new Sequence();
  /**
   * The File our contact manager is saved.
   */
  public static final String FILE = "Contact_manager";
  /**
   * A version number which is used during deserialization to verify that the
   * sender and receiver of a serialized object have loaded classes for that
   * are compatible with respect to serialization.
   */
  private static final long serialVersionUID = 507553869890625432L;
  /**
   * The constructor of Contact Manager.
   *
   */

  public ContactManagerImpl() {
    ObjectInputStream input = null;
    try {
      /* Check if file exists and is indeed a file */
      File file = new File(FILE);
      if (file.exists() && file.isFile()) {
        input = new ObjectInputStream(new FileInputStream(FILE));
        //Deserialize the objects - Create data
        //structures for the file content
        setOfContacts = (TreeSet<Contact>) input.readObject();
        listOfMeetings = (ArrayList<Meeting>) input.readObject();
        meetingId = (Sequence) input.readObject();
        contactId = (Sequence) input.readObject();
      }
    } catch (IOException | SecurityException
            | NullPointerException | ClassNotFoundException e) {
      logger.log(Level.SEVERE, "ContactManagerImpl_Constructor", e);
    } finally {
      try {
        if (input != null) {
          input.close();
        }
      } catch (IOException e) {
        logger.log(Level.SEVERE, "ContactManagerImpl_Constructor", e);
      }
    }
  }
  /**
   * Add a new meeting to be held in the future.
   * An ID is returned when the meeting is put into the system. This
   * ID must be positive and non-zero.
   * @param contacts a set of contacts that will participate in the meeting
   * @param date the date on which the meeting will take place
   *
   * @return the ID for the meeting
   * @throws IllegalArgumentException if the meeting is set for a time
   * in the past, of if any contact is unknown / non-existent.
   * @throws NullPointerException if the meeting or the date are null
   */

  public final int addFutureMeeting(final Set<Contact> contacts,
                                    final Calendar date) throws
          IllegalArgumentException, NullPointerException {
    if (contacts == null || date == null) {
      throw  new NullPointerException("Cannot accept null values ");
    }
    if (date.before(Calendar.getInstance())) {
      throw new IllegalArgumentException("wrong date");
    }
    Iterator<Contact> iterate = contacts.iterator();
    while (iterate.hasNext()) {
      Contact contact = iterate.next();
      if (!this.setOfContacts.contains(contact)) {
        throw new IllegalArgumentException("contact doesn't exist");
      }
    }
    Meeting futureMeeting =
            new FutureMeetingImpl(meetingId.nextValue(), date, contacts);
    listOfMeetings.add(futureMeeting);
    return futureMeeting.getId();
  }
  /**
   * Returns the PAST meeting with the requested ID,
   * or null if it there is none.
   * The meeting must have happened at a past date.
   *
   * @param id the ID for the meeting
   *
   * @return the meeting with the requested ID, or null if it there is none.
   * @throws IllegalStateException if there is a meeting with that ID happening in the future
   */

  public final PastMeeting getPastMeeting(final int id) throws IllegalStateException {
    Meeting meeting = searchForMeeting(id);
    if (meeting == null) {
      return null;
    }
    if (meeting instanceof PastMeeting) {
      return (PastMeeting) meeting;
    }
    throw new IllegalStateException(("This is a future Meeting"));
  }
  /**
   * Returns the FUTURE meeting with the requested ID,
   * or null if there is none.
   * @param id the ID for the meeting
   *
   * @return the meeting with the requested ID, or null if it there is none.
   * @throws IllegalStateException if there is a meeting with that ID
   * happening in the past
   */
  public final FutureMeeting getFutureMeeting(final int id) throws IllegalStateException {
    Meeting meeting = searchForMeeting(id);
    if (meeting == null) {
      return null;
    }
    if (meeting instanceof FutureMeeting) {
      return (FutureMeeting) meeting;
    }
    throw new IllegalStateException("This is a past Meeting");
  }
  /**
   * Returns the meeting with the requested ID, or null if it there is none.
   *
   * @param id the ID for the meeting
   *
   * @return the meeting with the requested ID, or null if it there is none.
   */

  public final Meeting getMeeting(final int id) {
    Meeting meeting = searchForMeeting(id);
    if (meeting == null) {
      return null;
    }
    return meeting;
  }
  /**
   * A serial search algorithm for meetings based on a given id.
   * @param id the ID for the meeting
   *
   * @return the meeting with the requested ID, or null if
   * there is none
   */

  private Meeting searchForMeeting(final int id) {
    Iterator<Meeting> iterate = listOfMeetings.iterator();
    Meeting meeting = null;
    boolean found = false;
    while (iterate.hasNext() && !found) {
      meeting = iterate.next();
      if (meeting.getId() == id) {
        found = true;
      }
    }
    if (!found) {
      return null;
    }
    return  meeting;
  }
  /**
   * Returns the list of future meetings scheduled with this contact.
   * If there are none, the returned list will be empty. Otherwise,
   * the list will be chronologically sorted and will not contain any
   * duplicates.
   * @param contact one of the user’s contacts
   *
   * @return the list of future meeting(s)
   * scheduled with this contact (maybe empty).
   * @throws IllegalArgumentException if the contact does not exist
   * @throws NullPointerException if the contact is null
   */

  public final List<Meeting> getFutureMeetingList(final Contact contact) throws
          IllegalArgumentException, NullPointerException {
    if (contact == null) {
      throw new NullPointerException("Null contact");
    }
    if (!setOfContacts.contains(contact)) {
      throw new IllegalArgumentException("This contact does not exist");
    }
    List<Meeting> futureMeetings = new ArrayList<>();
    Iterator<Meeting> iterate = listOfMeetings.iterator();
    while (iterate.hasNext()) {
      Meeting meeting = iterate.next();
      Set<Contact> meetingContacts = meeting.getContacts();
      if (meetingContacts.contains(contact)) {
        futureMeetings.add(meeting);
      }
    }
    sortMeetingByDate(futureMeetings);
    return futureMeetings;

  }
  /**
   * Sort meetings by date.
   * @param meetings the list of meetings that we want to sort out
   *
   * @return the sorted meeting list
   */

  private List<Meeting> sortMeetingByDate(final List<Meeting> meetings) {
    for (int j = 1; j < meetings.size(); j++) {
      for (int i = meetings.size() - 1; i >= j; i--) {
        Meeting nextMeeting = meetings.get(i);
        Meeting previousMeeting = meetings.get(i - 1);
        Calendar next = nextMeeting.getDate();
        Calendar previous = previousMeeting.getDate();
        if (next.before(previous)) {
          meetings.set(i, previousMeeting);
          meetings.set(i - 1, nextMeeting);
        }
      }
    }
    return meetings;
  }
  /**
   * Returns the list of meetings that are scheduled for, or that took
   * place on, the specified date.
   * If there are none, the returned list will be empty. Otherwise,
   * the list will be chronologically sorted and will not contain any
   * duplicates.
   * @param date the date
   * @return the list of meetings
   * @throws NullPointerException if the date are null
   */

  public final List<Meeting> getMeetingListOn(final Calendar date) throws
          NullPointerException {
    if (date == null) {
      throw new NullPointerException("Give me a date");
    }
    //Convert Calendar date to local date time in order
    // to have access to individual date components
    LocalDateTime localDateTime = calendarToLdT(date);
    //Create a list ot hold the meetings
    // that satisfy the criterion
    List<Meeting> meetingList = new ArrayList<>();
    //Go through all the meetings and select
    // the ones that satisfy the criterion
    Iterator<Meeting> iterate = listOfMeetings.iterator();
    while (iterate.hasNext()) {
      Meeting meeting = iterate.next();
      //Get the date of the meeting and convert it
      //to a local date time to compare it with the date
      //given as a parameter
      Calendar meetingDate = meeting.getDate();
      LocalDateTime localDateTimeOfMeeting = calendarToLdT(meetingDate);
      int year = localDateTimeOfMeeting.getYear();
      int hour = localDateTimeOfMeeting.getHour();
      int minutes = localDateTimeOfMeeting.getMinute();
      int day = localDateTimeOfMeeting.getDayOfMonth();
      int month = localDateTimeOfMeeting.getMonth().getValue();
      //If the date is the same select it for insertion
      if (year == localDateTime.getYear() && month
              == localDateTime.getMonth().getValue()
              && day == localDateTime.getDayOfMonth()) {
        Iterator<Meeting> iterator = meetingList.iterator();
        boolean found = false;
        //If the time is the same(duplicates) ignore the meeting
        while (iterator.hasNext() && !found) {
          Meeting meeting1 = iterator.next();
          Calendar dayOfMeeting = meeting1.getDate();
          LocalDateTime localDateCheckingForTime = calendarToLdT(dayOfMeeting);
          int comparingHours = localDateCheckingForTime.getHour();
          int comparingMinutes = localDateCheckingForTime.getMinute();
          if (hour == comparingHours && minutes == comparingMinutes) {
            found = true;
          }
        }
        if (!found) {
          meetingList.add(meeting);
        }
      }
    }
    //Sort it and return it to the caller
    return sortMeetingByDate(meetingList);
  }

  /**
   * Converting Calendar date to Local date time
   * in order to have access to individual date components.
   * @param date the date we want to convert
   * @return local date time
   */

  private LocalDateTime calendarToLdT(final Calendar date) {
    Instant instant = date.getTime().toInstant();
    ZoneId defaultZoneId = ZoneId.systemDefault();
    LocalDateTime localDateTime =
            instant.atZone(defaultZoneId).toLocalDateTime();
    return localDateTime;
  }
  /**
   * Returns the list of past meetings in which this contact has participated.
   * If there are none, the returned list will be empty.
   * Otherwise, the list will be
   * chronologically sorted and
   * will not contain any duplicates.
   * @param contact one of the user’s contacts
   *
   * @return the list of future meeting(s) scheduled with this contact (maybe empty).
   * @throws IllegalArgumentException if the contact does not exist
   * @throws NullPointerException if the contact is null
   */

  public final List<PastMeeting> getPastMeetingListFor(final Contact contact) throws
          IllegalArgumentException, NullPointerException {
    if (contact == null) {
      throw new NullPointerException("Empty input");
    }
    if (!setOfContacts.contains(contact)) {
      throw new IllegalArgumentException("I don't have this contact");
    }
    List<Meeting> meetingList = new ArrayList<>();
    Iterator<Meeting> iterate = listOfMeetings.iterator();
    while (iterate.hasNext()) {
      Meeting meeting = iterate.next();
      if (meeting instanceof PastMeeting) {
        Set<Contact> contacts = meeting.getContacts();
        if (contacts.contains(contact)) {
          meetingList.add(meeting);
        }
      }
    }
    meetingList = sortMeetingByDate(meetingList);
    List<PastMeeting> pastMeetingList = new ArrayList<>();
    iterate = meetingList.iterator();
    while (iterate.hasNext()) {
      PastMeeting meeting = (PastMeeting) iterate.next();
      pastMeetingList.add(meeting);
    }
    return pastMeetingList;
  }
  /**
   * Create a new record for a meeting that took place in the past.
   *
   * @param contacts a set of participants
   * @param date the date on which the meeting took place
   * @param text messages to be added about the meeting.
   * @return the ID for the meeting
   * @throws IllegalArgumentException if the list of contacts is
   * empty, if any of the contacts does not exist, or if the date provided is in the future
   * @throws NullPointerException if any of the arguments is null
   */

  public final int addNewPastMeeting(final Set<Contact> contacts,
                                     final Calendar date,
                                     final String text) throws
          IllegalArgumentException, NullPointerException {
    if (contacts == null || date == null || text == null) {
      throw new NullPointerException();
    }
    if (contacts.size() == 0) {
      throw new IllegalArgumentException("Empty set of contacts");
    }
    if (date.after(Calendar.getInstance())) {
      throw new IllegalArgumentException();
    }
    Iterator<Contact> iterate = contacts.iterator();
    while (iterate.hasNext()) {
      Contact contact = iterate.next();
      if (!this.setOfContacts.contains(contact)) {
        throw  new IllegalArgumentException("Unknown contact");
      }
    }
    Meeting pastMeeting = new
            PastMeetingImpl(meetingId.nextValue(), date, contacts, text);
    listOfMeetings.add(pastMeeting);
    return pastMeeting.getId();
  }
  /**
   * Add notes to a meeting.
   * This method is used when a future meeting takes place, and is
   * then converted to a past meeting (with notes) and returned.
   * It can be also used to add notes to a past meeting at a later date.
   * @param id the ID of the meeting
   * @param text messages to be added about the meeting.
   *
   * @return PastMeeting with notes.
   * @throws IllegalArgumentException if the meeting does not exist
   * @throws IllegalStateException if the meeting is set for a date in the future
   * @throws NullPointerException if the notes are null
   */

  public final PastMeeting addMeetingNotes(final int id, final String text) throws
          IllegalArgumentException, IllegalStateException, NullPointerException {
    if (text == null) {
      throw new NullPointerException();
    }
    boolean found = false;
    int i = 0;
    Meeting meeting = null;
    while (i < listOfMeetings.size() && !found) {
      meeting = listOfMeetings.get(i);

      if (meeting.getId() == id) {
        found = true;
      } else {
        i++;
      }
    }
    if (!found) {
      throw new IllegalArgumentException("Wrong id");
    }
    if (meeting.getDate().after(Calendar.getInstance())) {
      throw new IllegalStateException("This is a future meeting");
    }
    if (meeting instanceof PastMeeting) {
      meeting = new PastMeetingImpl(id,
              meeting.getDate(),
              meeting.getContacts(),
              text);
      listOfMeetings.set(i, meeting);
      return (PastMeeting) meeting;
    }
    int j = addNewPastMeeting(
            meeting.getContacts(),
            meeting.getDate(),
            text);
    return getPastMeeting(j);
  }
  /**
   * Create a new contact with the specified name and notes.
   *
   * @param name the name of the contact.
   * @param notes notes to be added about the contact.
   *
   * @return the ID for the new contact
   * @throws IllegalArgumentException if the name or the notes are empty strings
   * @throws NullPointerException if the name or the notes are null
   */

  public final int addNewContact(final String name, final String notes) throws
          IllegalArgumentException, NullPointerException {
    if (name == null || notes == null) {
      throw new NullPointerException();
    }
    if (name.equals("") || notes.equals("")) {
      throw new IllegalArgumentException();
    }
    Contact contact = new ContactImpl(contactId.nextValue(), name, notes);
    setOfContacts.add(contact);
    return contact.getId();
  }
  /**
   * Returns a set with the contacts whose name contains that string.
   * If the string is the empty string, this methods returns the set
   * that contains all current contacts.
   *
   * @param name the string to search for
   *
   * @return a set with the contacts whose name contains that string.
   * @throws NullPointerException if the parameter is null
   */

  public final Set<Contact> getContacts(final String name) throws NullPointerException {
    if (name == null) {
      throw new NullPointerException();
    }
    if (name.equals("")) {
      return setOfContacts;
    }
    Iterator<Contact> iterate = setOfContacts.iterator();
    Set<Contact> subContacts = new TreeSet<>();
    while (iterate.hasNext()) {
      Contact contact = iterate.next();
      if (contact.getName().contains(name)) {
        subContacts.add(contact);
      }
    }
    return  subContacts;
  }
  /**
   * Returns a set containing the contacts that correspond to the IDs.
   * Note that this method can be used to retrieve just
   * one contact by passing only one ID.
   * @param ids an arbitrary number of contact IDs
   * @return a set containing the contacts that correspond to the IDs.
   * @throws IllegalArgumentException if no IDs are provided or if
   * any of the provided IDs does not correspond to a real contact
   */
  public final Set<Contact> getContacts(final int... ids) throws
          IllegalArgumentException {
    if (ids.length == 0) {
      throw new IllegalArgumentException();
    }
    Set<Contact> subContacts = new TreeSet<>();
    for (int n : ids) {
      Iterator<Contact> iterator = setOfContacts.iterator();
      boolean found = false;
      while (iterator.hasNext() && !found) {
        Contact contact = iterator.next();
        if (contact.getId() == n) {
          subContacts.add(contact);
          found = true;
        }
      }
      if (!found) {
        throw new IllegalArgumentException();
      }
    }
    return subContacts;

  }
  /**
   * Save all data to disk.
   * This method must be executed when the program is
   * closed and when/if the user requests it.
   */

  public final void flush() {
    ObjectOutputStream output = null;
    try {
      output = new ObjectOutputStream(new FileOutputStream(FILE));
      output.writeObject(setOfContacts);
      output.writeObject(listOfMeetings);
      output.writeObject(meetingId);
      output.writeObject(contactId);
    } catch (FileNotFoundException
            | SecurityException
            | NullPointerException  e) {
      logger.log(Level.SEVERE, "flush", e);
    } catch (IOException e) {
      logger.log(Level.SEVERE, "flush", e);
    } finally {
      try {
        if (output != null) {
          output.close();
        }
      } catch (IOException e) {
        logger.log(Level.SEVERE, "flush", e);
      }
    }
  }

}
/**
 * Class that represents the concept of a sequence.
 */

class Sequence implements  Serializable {
   /**
    * Starting value at 0.
    */
   private int value = 0;
   /**
    * Providing a unique sequence.
    *
    * @return unique value for every object.
    */

   public int nextValue() {
     return  ++value;
   }
}
