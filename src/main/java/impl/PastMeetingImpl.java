package impl;

import spec.Contact;
import spec.PastMeeting;

import java.util.Calendar;
import java.util.Set;
import java.io.Serializable;
/**
 * A meeting that was held in the past.
 *
 * It includes your notes about what happened and what was agreed.
 */
public class PastMeetingImpl extends MeetingImpl implements PastMeeting, Serializable {
    private String notes ;
    public PastMeetingImpl(int id, Calendar date, Set<Contact> contacts, String notes)throws IllegalArgumentException, NullPointerException {
        super(id, date, contacts);
        if(notes == null) {
            throw new NullPointerException("empty notes");
        }
        this.notes = notes;

    }

    /**
     * Returns the notes from the meeting.
     *
     * If there are no notes, the empty string is returned.
     *
     * @return the notes from the meeting.
     */
    public String getNotes() {

        return notes;

    }
}