package impl;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

import spec.Contact;
import spec.Meeting;
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

  /**
   * Indicates whether some other object is "equal to" this one.
   * @param o - the reference object with  which to compare.
   * @return true if this object is the same as the obj argument, false otherwise.
   */
  @Override
  public boolean equals(Object o) {
    boolean result = super.equals(o);
    if (!result) {
      return false;
    }
    PastMeeting c = (PastMeeting) o;
    return (meetingNotes.equals(c.getNotes()));
  }

  /**
   * A hash code value for the object.
   *
   * @return a hash code value for this object.
   */
  @Override
  public int hashCode() {
    int hash = super.hashCode();
    hash += meetingNotes.hashCode();
    return hash;
  }

}
