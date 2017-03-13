package impl;

import java.io.Serializable;

import java.util.Calendar;
import java.util.Set;

import spec.Contact;
import spec.FutureMeeting;



/**
 * A meeting to be held in the future.
 */

public class FutureMeetingImpl extends MeetingImpl implements
        FutureMeeting, Serializable {
  /**
   * Creates a new meeting.
   * @param id the unique id of a meeting
   * @param date the date on which the meeting will take place
   * @param contacts a set of contacts that will participate in the meeting
   * @throws IllegalArgumentException if the set is empty
   * @throws NullPointerException if the contacts or the date are null.
   */

  public FutureMeetingImpl(final int id, final Calendar date,
                           final Set<Contact> contacts) throws
          IllegalArgumentException, NullPointerException {
    super(id, date, contacts);

  }
}
