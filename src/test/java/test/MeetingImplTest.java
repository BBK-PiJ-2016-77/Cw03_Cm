package test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import impl.ContactImpl;
import impl.PastMeetingImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import spec.Contact;
import spec.Meeting;


import java.util.Calendar;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by vasileiosdavios on 3/8/17.
 */
public class MeetingImplTest {
    private int id;
    private Calendar calendar;
    private Set<Contact> contacts = new TreeSet<>();
    private Meeting meeting = null;

    @Before
    public void beforeTest() {
        id = 12;
        Contact contact = new ContactImpl(id,"Maria");
        contacts.add(contact);
        calendar = Calendar.getInstance();
    }

    @Test
    public void meetingInitializationIdFailure() {
        try {
            new PastMeetingImpl(-1, calendar, contacts, "");
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Wrong Id");
        }
    }

    @Test
    public void meetingInitializationCalendarFailure() {
        try {
            new PastMeetingImpl(id, null, contacts, "");
        }catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    public void meetingInitializationContactsFailure() {
        try {
            new PastMeetingImpl(id, calendar, null, "");
        }catch (Exception e) {
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
        meeting = new PastMeetingImpl (id, calendar, contacts, "");
        assertEquals(id, meeting.getId());
    }
    @Test
    public void getNotesFromPastMeetingTest() {
        meeting = new PastMeetingImpl(id, calendar, contacts, "");
    }

    @After
    public void afterTest() {
        calendar = null;
        contacts = null;
        meeting = null;
    }



}
