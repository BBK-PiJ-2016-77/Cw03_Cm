package test;
import static org.junit.Assert.*;

import impl.ContactImpl;
import impl.ContactManagerImpl;
import org.junit.Before;

import org.junit.Test;
import spec.*;

import java.io.File;
import java.util.*;

public class ContactManagerImplTest {
    private ContactManager contactManager;

    @Before
    public void beforeTest() {
        try {
            String folderName = new File(".").getAbsolutePath();
            String fileName = folderName + ContactManagerImpl.FILE;
            fileName = fileName.replace(".", "");
            File file = new File(fileName);
            if(file.exists() && !file.isDirectory()) {
                file.delete();
            }
        }catch (NullPointerException | SecurityException e) {
            e.printStackTrace();
        }

        contactManager = new ContactManagerImpl();
        try {
            contactManager.addNewContact("Maria", "1st Contact");
        }catch(IllegalArgumentException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addNewContactTestSuccess() {
        try {
            contactManager.addNewContact("bill", "Test");
        } catch(IllegalArgumentException | NullPointerException e) {
            fail();
        }
    }

    /**
    It is expected that the method will fail with a Null pointer Exception
    If it doesnt fail we consider the test a failure.
    If it does fail but with a diferent type of exception
     we consider the test a failure.
    */
    @Test
    public void addNewContactTestFailure() {
        try {
            contactManager.addNewContact(null, "Test");
            fail();
        } catch (IllegalArgumentException e) {
            fail();
        } catch (NullPointerException e) {

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void addNewContactReturnValueTestSuccess() {
        try {
            int x = contactManager.addNewContact
                    ("Vasilis", "2nd Contact");
            assertEquals(2,x);
        } catch (IllegalArgumentException | NullPointerException e){
            fail();
        }
    }

    @Test
    public void getContactSizeTestSuccess() {
        try {
            Set<Contact> contactManagerSet =
                    contactManager.getContacts("Maria");
            assertEquals(1,contactManagerSet.size());
        } catch (NullPointerException e){
            fail();
        }
    }
    @Test
    public void getContactEmptyStringSuccess() {
        Set<Contact> testSet = new TreeSet<>();
        testSet.add(new ContactImpl(1,"Maria","1st Contact"));
        try {
           Set<Contact> contactManagerSet =
                   contactManager.getContacts("");
            Iterator<Contact> iterate = contactManagerSet.iterator();
            while(iterate.hasNext()) {
                ContactImpl contact = (ContactImpl)iterate.next();
                Iterator<Contact> iterator = testSet.iterator();
                boolean found = false;
                while(iterator.hasNext() && !found){
                    ContactImpl contactTest =(ContactImpl)iterator.next();
                    if(contact.compareTo(contactTest) == 0){
                        found = true;
                    }
                }
                if (!found) {
                    fail();
                }
            }
        }catch (NullPointerException e) {
            fail();
        }
    }

    @Test
    public void getContactsValueTestSuccess() {
        try {
            Set<Contact> contactManagerSet =
                    contactManager.getContacts("Maria");
            Object[] contactManagerArray = contactManagerSet.toArray();
            Contact contact = (Contact)contactManagerArray[0];
            assertEquals(contact.getName(),"Maria");
            assertEquals(contact.getNotes(), "1st Contact");
        }catch (NullPointerException e){
            fail();
        }
    }
    /**
     It is expected that the method will be successful.
     The assertion will return true.
     If the method return false or throw a NullPointerException
     we consider the test a failure.
     */
    @Test
    public void getContactsValueTestFailure() {
        try {
            Set<Contact> contactManagerSet =
                    contactManager.getContacts("Maria");
            Object[] contactManagerArray = contactManagerSet.toArray();
            Contact contact = (Contact)contactManagerArray[0];
            assertFalse(contact.getName().equals(""));
            assertFalse(contact.getNotes().equals(""));
        }catch(NullPointerException e){
            fail();
        }
    }
    @Test
    public void getContactsNullNameTestFailure() {

        try {
            Set<Contact> contactManagerSet =
                    contactManager.getContacts((String)null);
            fail();
        }catch (NullPointerException e) {

        }
    }
    @Test
    public void getContactsArbitraryIdsValueTestSuccess() {
        try {
            int i = contactManager.addNewContact("Kwstantinos",
                    "2nd Contact");
            Set<Contact> contactManagerSet =
                    contactManager.getContacts(1,i);
            int[] contactIds = new int[contactManagerSet.size()];
            int j = 0;
            for(Contact contact : contactManagerSet){
                contactIds[j++] = contact.getId();
            }
            assertEquals(contactIds.length, 2);
            boolean ok = contactIds[0] == 1 || contactIds[0] == 2;
            ok = ok && (contactIds[1] == 1 || contactIds[1] == 2);
            assertTrue(ok);
        }catch (NullPointerException e) {
            fail();
        }
    }
    @Test
    public void getContactsArbitraryIdsValueTestFailure() {
        try {
            Set<Contact> contactManagerSet = contactManager.getContacts(1);
            Object[] contactManagerArray = contactManagerSet.toArray();
            Contact contact = (Contact)contactManagerArray[0];
            assertFalse(contact.getName().equals(""));
            assertFalse(contact.getNotes().equals(""));
        } catch (NullPointerException e) {
            fail();
        }
    }
    @Test
    public void addNewPastMeetingTestSuccess() {
        try {
            Set<Contact> contactManagerSet =
                    contactManager.getContacts("Maria");
            contactManager.addNewPastMeeting(contactManagerSet,
                    Calendar.getInstance(), "meeting notes");
        } catch (NullPointerException | IllegalArgumentException e) {
            fail();
        }
    }
    @Test
    public void addNewPastMeetingEmptySetTestFailure() {
        try{
            Set<Contact> contactManagerSet = new TreeSet<>();
            contactManager.addNewPastMeeting(contactManagerSet,
                    Calendar.getInstance(), "meeting notes");
        } catch (NullPointerException e) {
            fail();
        } catch (IllegalArgumentException e) {

        } catch (Exception e) {
            fail();
        }
    }
    @Test
    public void addNewPastMeetingNullContactsTestFailure() {
        try {
            contactManager.addNewPastMeeting(null,
                    Calendar.getInstance(),"meeting notes");
            fail();
        } catch (NullPointerException e) {

        } catch (IllegalArgumentException e) {
            fail();
        }
    }
    @Test
    public void addNewPastMeetingUnknownContactTestFailure() {
        try {
            Set<Contact> contactManagerSet = new TreeSet<>();
            Contact contact = new ContactImpl(1,
                    "Whatever", "Whatever");
            contactManagerSet.add(contact);
            contactManager.addNewPastMeeting(contactManagerSet,
                    Calendar.getInstance(), "meeting notes");
            fail();
        } catch (NullPointerException e) {
            fail();
        } catch (IllegalArgumentException e) {

        } catch (Exception e) {
            fail();
        }
    }
    @Test
    public void addFutureMeetingTestSuccess() {
        try {
            Set<Contact> contactManagerSet =
                    contactManager.getContacts("Maria");
            Calendar date = Calendar.getInstance();
            //Add 5 day  to the current date
            date.add(Calendar.DATE, 5 );
            int id = contactManager.addFutureMeeting(contactManagerSet, date);
            assertEquals(1,id);
        } catch (NullPointerException | IllegalArgumentException e) {
            fail();
        }
    }
    @Test
    public void addFutureMeetingUnknownContactTestFailure() {
        try {
            Set<Contact> contactManagerSet = new TreeSet<>();
            Contact contact = new ContactImpl(1, "Whatever",
                    "Whatever");
            contactManagerSet.add(contact);
            //Add 5 day  to the current date
            Calendar date = Calendar.getInstance();
            date.add(Calendar.DATE, 5 );
            contactManager.addFutureMeeting(contactManagerSet, date);
            fail();
        } catch (NullPointerException e) {
            fail();
        } catch (IllegalArgumentException e) {

        }
    }
    @Test
    public void addFutureMeetingWrongDateFailure() {
        try {
            Set<Contact> contactManagerSet =
                    contactManager.getContacts("Maria");
            Calendar date = Calendar.getInstance();
            // travels back in time
            date.add(Calendar.DAY_OF_MONTH, -5);
            contactManager.addFutureMeeting(contactManagerSet,date);
            fail();
        } catch (NullPointerException e) {
            fail();
        } catch (IllegalArgumentException e) {

        }
    }
    @Test
    public void addFutureMeetingEmptySetContactTestFailure() {
        try {
            Set<Contact> contactManagerSet=new TreeSet<>();
            Calendar date = Calendar.getInstance();
            // adds 5 days to the current date
            date.add(Calendar.DATE, 5);
            contactManager.addFutureMeeting(contactManagerSet,date);
            fail();
        } catch (NullPointerException e) {
            fail();
        } catch (IllegalArgumentException e) {

        }
    }
    @Test
    public void addFutureMeetingNullContactTestFailure() {
        try {
            Calendar date = Calendar.getInstance();
            // adds 5 days to the current date
            date.add(Calendar.DATE, 5);
            contactManager.addFutureMeeting(null,date);
            fail();
        } catch (NullPointerException e) {

        } catch (IllegalArgumentException e) {
            fail();
        }
    }
    @Test
    public void addFutureMeetingNullDateTestFailure() {
        try {
            Set<Contact> contactManagerSet =
                    contactManager.getContacts("Maria");

            contactManager.addFutureMeeting(contactManagerSet,null);

            fail();

        } catch (NullPointerException e) {


        } catch (IllegalArgumentException e) {
            fail();
        }
    }
    @Test
    public void getPastMeetingTestSuccess() {
        try {
            Set<Contact> contactManagerSet =
                    contactManager.getContacts("Maria");
            int id = contactManager.addNewPastMeeting(contactManagerSet,
                    Calendar.getInstance(),"meeting notes");
            Meeting meeting = contactManager.getPastMeeting(id);
        } catch (NullPointerException | IllegalStateException e) {
            //addNewPastMeeting exceptions
            fail();
        } catch (IllegalArgumentException e) {
            fail();
        }
    }
    @Test
    public void getPastMeetingWrongIdTestFailure() {
        try {
            Set<Contact> contactManagerSet =
                    contactManager.getContacts("Maria");
            Calendar date = Calendar.getInstance();
            // adds 5 days to the current date
            date.add(Calendar.DATE, 5);
            int id = contactManager.addFutureMeeting(contactManagerSet,date);
            contactManager.getPastMeeting(id);
            fail();
        } catch (NullPointerException | IllegalArgumentException e) {
            //addNewPastMeeting exception
            fail();
        } catch (IllegalStateException e) {

        }
    }
    @Test
    public void getFutureMeetingWrongIdTestFailure() {

        try {
            Set<Contact> contactManagerSet =
                    contactManager.getContacts("Maria");
            int id = contactManager.addNewPastMeeting(contactManagerSet,
                    Calendar.getInstance(),"meeting notes");
            Meeting meeting = contactManager.getFutureMeeting(id);
            fail();
        } catch (NullPointerException | IllegalArgumentException e) {
            //addNewPastMeeting exception
            fail();
        } catch (IllegalStateException e) {

        } catch (Exception e) {
            fail();
        }
    }
    @Test
    public void getMeetingTestSuccess() {

        Set<Contact> contactManagerSet =
                contactManager.getContacts("Maria");
        int id = contactManager.addNewPastMeeting(contactManagerSet,
                Calendar.getInstance(),"meeting notes");
        Meeting meeting = contactManager.getMeeting(id);
        assertEquals(id,meeting.getId());

    }
    @Test
    public void getMeetingTestForNullSuccess() {

        Set<Contact> contactManagerSet =
                contactManager.getContacts("Maria");
        int id = contactManager.addNewPastMeeting(contactManagerSet,
                Calendar.getInstance(),"meeting notes");
        Meeting meeting = contactManager.getMeeting(100);
        assertNull(meeting);

    }
    @Test
    public void getFutureMeetingListTestSuccess() {
        Set<Contact> contactManagerSet =
                contactManager.getContacts("Maria");
        Contact maria = (Contact)contactManagerSet.toArray()[0];
        Calendar date = Calendar.getInstance();
        // adds 5 days to the current date
        date.add(Calendar.DATE, 5);
        int id = contactManager.addFutureMeeting(contactManagerSet,date);
        FutureMeeting[] futureMeetings = new FutureMeeting[1];
        futureMeetings[0] = contactManager.getFutureMeeting(id);
        try {
            List<Meeting> meetingList =
                    contactManager.getFutureMeetingList(maria);
            Object[] futureMeetings2 = meetingList.toArray();
            assertArrayEquals(futureMeetings,futureMeetings2);
        } catch (IllegalArgumentException | NullPointerException e) {
            fail();
        }

    }
    @Test
    public void getFutureMeetingListEmptyTestSuccess() {
        Set<Contact> contactManagerSet =
                contactManager.getContacts("Maria");
        Contact maria = (Contact)contactManagerSet.toArray()[0];
        try {
            List<Meeting> meetingList =
                    contactManager.getFutureMeetingList(maria);
            assertTrue(meetingList.isEmpty());
        } catch (IllegalArgumentException | NullPointerException e) {
            fail();
        }
    }
    @Test
    public void getFutureMeetingListWrongContactTestFailure() {
        Contact george = new ContactImpl(23,"george","dummyContact");
        try {
            List<Meeting> meetingList = contactManager.getFutureMeetingList(george);
            fail();
        } catch (IllegalArgumentException e) {

        } catch (NullPointerException e) {
            fail();
        }
    }
    @Test
    public void getFutureMeetingListNullContactTestFailure() {
        try {
            List<Meeting> meetingList =
                    contactManager.getFutureMeetingList(null);
            fail();
        } catch (IllegalArgumentException e) {
            fail();
        } catch (NullPointerException e) {

        }
    }
    @Test
    public void getMeetingListOnTestSuccess() {
        Set<Contact> contactManagerSet = contactManager.getContacts("Maria");
        Calendar date1 = Calendar.getInstance();
        Calendar date2 = Calendar.getInstance();
        Calendar date3 = Calendar.getInstance();
        //adds 5 days to the current date
        date1.add(Calendar.DATE, 5);
        date2.add(Calendar.DATE, 5);
        //5 minutes later than the first meeting
        date2.add(Calendar.MINUTE, 5);
        date3.add(Calendar.DATE, 3);
        int id = contactManager.addFutureMeeting(contactManagerSet, date1);
        FutureMeeting[] futureMeetings = new FutureMeeting[2];
        futureMeetings[0] = contactManager.getFutureMeeting(id);
        id = contactManager.addFutureMeeting(contactManagerSet, date2);
        futureMeetings[1] = contactManager.getFutureMeeting(id);
        contactManager.addFutureMeeting(contactManagerSet, date3);
        try {
            List<Meeting> meetingList = contactManager.getMeetingListOn(date1);
            Object[] futureMeetings2 = meetingList.toArray();
            assertEquals(futureMeetings.length, futureMeetings2.length);
            //check if the objects are sorted
            assertArrayEquals(futureMeetings, futureMeetings2);

        } catch (NullPointerException e) {
            fail();
        }
    }
    @Test
    public void getMeetingListOnEmptyListTestSuccess() {
        Calendar date = Calendar.getInstance();
        //adds 5 days to the current date
        date.add(Calendar.DATE,10);
        try{
            List<Meeting> meetingList =
                    contactManager.getMeetingListOn(date);
            // the list should be empty
            assertTrue(meetingList.isEmpty());
        } catch (NullPointerException e) {
            fail();
        }
    }
    @Test
    public void getMeetingListOnTestForDuplicatesSuccess() {
        Set<Contact> contactManagerSet =
                contactManager.getContacts("Maria");
        Calendar date = Calendar.getInstance();
        Calendar date1 = Calendar.getInstance();
        //adds 5 days to the current date
        date.add(Calendar.DATE, 5);
        date1.add(Calendar.DATE, 3);
        int id = contactManager.addFutureMeeting(contactManagerSet,date);
        FutureMeeting[] futureMeetings = new FutureMeeting[2];
        futureMeetings[0] = contactManager.getFutureMeeting(id);
        id = contactManager.addFutureMeeting(contactManagerSet,date);
        futureMeetings[1] = contactManager.getFutureMeeting(id);
        try {
            List<Meeting> meetingList = contactManager.getMeetingListOn(date);
            Object[] futureMeetings2 = meetingList.toArray();
            assertNotEquals(futureMeetings.length,futureMeetings2.length);

        } catch (NullPointerException e) {
            fail();
        } catch (Exception e) {
            fail();
        }
    }
    @Test
    public void getMeetingListOnTestFailure() {
        Set<Contact> contactManagerSet =
                contactManager.getContacts("Maria");
        Calendar date1 = Calendar.getInstance();
        Calendar date2 = Calendar.getInstance();
        Calendar date3 = Calendar.getInstance();
        //adds 5 days to the current date
        date1.add(Calendar.DATE, 5);
        date1.add(Calendar.MINUTE,10);
        date2.add(Calendar.DATE,5);
        //5 minutes later than the first meeting
        date2.add(Calendar.MINUTE,5);
        date3.add(Calendar.DATE, 3);
        int id = contactManager.addFutureMeeting(contactManagerSet,date1);
        FutureMeeting[] futureMeetings = new FutureMeeting[2];
        futureMeetings[0] = contactManager.getFutureMeeting(id);
        id = contactManager.addFutureMeeting(contactManagerSet,date2);
        futureMeetings[1] = contactManager.getFutureMeeting(id);
        contactManager.addFutureMeeting(contactManagerSet,date3);
        try{
            List<Meeting> meetingList = contactManager.getMeetingListOn(date1);
            Object[] futureMeetings2 =  meetingList.toArray();

            assertEquals(futureMeetings.length,futureMeetings2.length);
            //Check if the list is sorted - our dummy list its not sorted
            // so the assertion should fail.
            //If the assertion fail then we know that the list we get
            // from getMeetingListOn() is sorted.
            boolean found = false;
            int i = 0;

            while(!found) {
                if(futureMeetings2[i]!= futureMeetings[i]) {
                    found=true;
                } else {
                    i++;
                }
            }

            if (!found) {
                fail();
            }
        } catch (NullPointerException e) {
            fail();
        }
    }
    @Test
    public void getPastMeetingListForTestSuccess() {
        Set<Contact> contactManagerSet =
                contactManager.getContacts("Maria");
        Contact contact = (Contact)(contactManagerSet.toArray()[0]);
        Calendar date = Calendar.getInstance();
        //5 days past from this meeting
        date.add(Calendar.DATE, -5);
        int id = contactManager
                .addNewPastMeeting(contactManagerSet, date,"meeting notes");
        PastMeeting[] pastMeetings = new PastMeeting[2];
        pastMeetings[0] = contactManager.getPastMeeting(id);
        Calendar date1 = Calendar.getInstance();
        //7 days past from this meeting
        date1.add(Calendar.DATE, - 7);
        id = contactManager
                .addNewPastMeeting(contactManagerSet, date,"meeting notes");
        pastMeetings[1]= contactManager.getPastMeeting(id);
        try {
            List<PastMeeting> meetingList = contactManager
                    .getPastMeetingListFor(contact);
            Object[] arrayOfMeetings = meetingList.toArray();
            assertEquals(arrayOfMeetings.length, pastMeetings.length);
            assertArrayEquals(arrayOfMeetings,pastMeetings);
        } catch (IllegalArgumentException | NullPointerException e) {
            fail();
        }
    }
    @Test
    public void getPastMeetingListForNullTestFailure() {
        try {
            List<PastMeeting> meetingList = contactManager
                    .getPastMeetingListFor(null);
            fail();
        } catch (IllegalArgumentException e) {
            fail();
        } catch (NullPointerException e) {

        } catch (Exception e) {
            fail();
        }

    }
    @Test
    public void getPastMeetingListForWrongContactTestFailure() {
        Contact contact = new ContactImpl(1, "Katerina");
        Set<Contact> contactManagerSet = new TreeSet<>();
        contactManagerSet.add(contact);
        try {
            List<PastMeeting> meetingList = contactManager
                    .getPastMeetingListFor(contact);
            fail();
        } catch (IllegalArgumentException e) {

        } catch (NullPointerException e) {
            fail();
        }
    }
    @Test
    public void addMeetingNotesIllegalArgumentTestFailure() {
        try {
            PastMeeting ourMeeting = contactManager
                    .addMeetingNotes(10,"done");
            fail();
        } catch (IllegalArgumentException ex) {

        } catch (IllegalStateException | NullPointerException ex) {
            fail();
        }
    }
    @Test
    public void addMeetingNotesDateInFutureTestFailure() {
        Set<Contact> contacts = contactManager.getContacts("Maria");
        Calendar date = Calendar.getInstance();
        date.add(Calendar.DATE, 3);
        int id = contactManager.addFutureMeeting(contacts,date);
        try {
            PastMeeting ourMeeting = contactManager
                    .addMeetingNotes(id,"done");
            fail();
        } catch (IllegalArgumentException | NullPointerException ex) {
            fail();
        } catch (IllegalStateException ex) {

        }
    }
    @Test
    public void addMeetingNotesWithNullNotesTestFailure() {
        Set<Contact> contacts = contactManager.getContacts("Maria");
        Calendar date = Calendar.getInstance();
        date.add(Calendar.DATE, 3);
        int id = contactManager.addFutureMeeting(contacts,date);
        try {
            PastMeeting ourMeeting = contactManager
                    .addMeetingNotes(id,null);
            fail();
        } catch (IllegalArgumentException | IllegalStateException ex) {
            fail();
        } catch (NullPointerException ex) {

        }
    }
    @Test
    public void addMeetingNotesTestSuccess() {
        Set<Contact> contacts = contactManager.getContacts("Maria");
        Calendar date = Calendar.getInstance();
        int meetingId = contactManager.addFutureMeeting(contacts,date);
        //Sleep main thread for 1sec so
        //we can make the futureMeeting pastMeeting.
        try{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            PastMeeting ourMeeting = contactManager
                    .addMeetingNotes(meetingId, "notes");
            assertEquals(ourMeeting.getNotes(), "notes");
        } catch (IllegalArgumentException | IllegalStateException ex) {
            fail();
        }
    }
    @Test
    public void flushTestSuccess() {
        Set<Contact> contacts = contactManager.getContacts("Maria");
        Calendar date = Calendar.getInstance();
        contactManager.addFutureMeeting(contacts, date);
        contactManager.flush();
        ContactManager testFlushContactManager = new ContactManagerImpl();
        Set<Contact> afterFlushContacts =
                testFlushContactManager.getContacts(1);
        List<Meeting> meetings = testFlushContactManager.getMeetingListOn(date);
        Object [] arrayOfContacts = afterFlushContacts.toArray();
        Contact contact = (Contact)arrayOfContacts[0];
        Meeting meeting = meetings.get(0);
        assertEquals(contact.getName(), "Maria");
        assertEquals(meeting.getId(), 1);
    }
}
