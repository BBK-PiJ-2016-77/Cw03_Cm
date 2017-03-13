package impl;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

import spec.Contact;
import spec.PastMeeting;


/**
 * A meeting that was held in the past.
 * It includes your notes about what happened and what was agreed.
 */
public class PastMeetingImpl extends MeetingImpl implements
        PastMeeting, Serializable {
  /**
   * The notes of the past meeting.
   */
  private String meetingNotes;
  /**
   * Past Meeting.
   * @param id the unique id of the meeting
   * @param date of the meeting
   * @param contacts of meeting
   * @param notes of meeting
   * @throws IllegalArgumentException if the contacts are empty
   * @throws NullPointerException if any of the references/pointers
   passed as parameters is null
   */

  public PastMeetingImpl(int id, Calendar date,
                         Set<Contact> contacts, String notes) throws
          IllegalArgumentException, NullPointerException {
      super(id, date, contacts);
    if (notes == null) {
      throw new NullPointerException("empty notes");
    }
    meetingNotes = notes;

  }
  /**
   * Returns the notes from the meeting.
   * If there are no notes, the empty string is returned.
   *
   * @return the notes from the meeting.
   */

  public String getNotes() {
    return meetingNotes;
  }
}
