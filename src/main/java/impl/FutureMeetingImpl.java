package impl;

import spec.Contact;
import spec.FutureMeeting;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

/**
 * A meeting to be held in the future
 */
public class FutureMeetingImpl extends MeetingImpl implements FutureMeeting, Serializable {
    public FutureMeetingImpl(int id, Calendar date, Set<Contact> contacts) throws IllegalArgumentException,
            NullPointerException {
        super(id, date, contacts);
        if(date.before(Calendar.getInstance())){
            throw new IllegalArgumentException();
        }
    }
}
