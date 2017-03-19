package test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import impl.ContactImpl;
import impl.PastMeetingImpl;

import java.util.Calendar;
import java.util.Set;
import java.util.TreeSet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import spec.Contact;
import spec.Meeting;


/**
 * Test for MeetingImpl.
 */
public class MeetingImplTest {
  private int id;
  private Calendar calendar;
  private Set<Contact> contacts = new TreeSet<>();
  private Meeting meeting = null;

  /**
   * Setting up before tests.
   */
  @Before
  public void beforeTest() {
    id = 12;
    Contact contact = new ContactImpl(id,"Maria");
    contacts.add(contact);
    calendar = Calendar.getInstance();
  }

  /**
   * Asserting we receive the correct message from the constructor
   for using wrong/negative id.
   */

  @Test
  public void meetingInitializationIdFailure() {
    try {
      new PastMeetingImpl(-1, calendar, contacts, "");
    } catch (Exception e) {
      assertEquals(e.getMessage(), "Wrong Id");
    }
  }

  /**
   * Constructor fails with null date.
   */

  @Test
  public void meetingInitializationCalendarFailure() {
    try {
      new PastMeetingImpl(id, null, contacts, "");
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  @Test
  public void meetingInitializationContactsFailure() {
    try {
      new PastMeetingImpl(id, calendar, null, "");
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  @Test
  public void getContactsTest() {
    meeting = new PastMeetingImpl(id, calendar, contacts, "");
    assertThat(contacts, is(meeting.getContacts()));
  }

  @Test
  public void getIdTest() {
    meeting = new PastMeetingImpl(id, calendar, contacts, "");
    assertEquals(id, meeting.getId());
  }

  @Test
  public void getNotesFromPastMeetingTest() {
    meeting = new PastMeetingImpl(id, calendar, contacts, "");
  }

  /**
   * Clear.
   */
  @After
  public void afterTest() {
    calendar = null;
    contacts = null;
    meeting = null;
  }
}
