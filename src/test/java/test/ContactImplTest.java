package test;

import impl.ContactImpl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import spec.Contact;



import static org.junit.Assert.*;


public class ContactImplTest {
  private Contact contact = null;
  private int id;
  private String name;
  private String notes;

  @Before
  public void beforeTest() {
    name = "Maria";
    notes = "Cleaver";
    id = 30;
  }

  @Test
  public void contactImplInitializationIdSuccess() {
    contact = new ContactImpl(id, name, notes);
    assertEquals(id,contact.getId());
  }


  @Test
  public void contactImplInitializationNameSuccess() {
    contact = new ContactImpl(id, name, notes);
    assertEquals(name,contact.getName());
  }

  @Test
  public void contactImplInitializationNotesSuccess() {
    contact = new ContactImpl(id, name, notes);
    assertEquals(notes,contact.getNotes());
  }

  @Test
  public void contactImplInitializationIdFailure() {
    try {
      contact = new ContactImpl(-1, name , notes);
    } catch (Exception e) {
      assertEquals(e.getMessage(), "Invalid ID!");
    }
  }

  @Test
  public void contactImplInitializationNameFailure() {
    try {
      contact = new ContactImpl(id,null, notes);
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  @Test
  public void contactImplInitializationNotesFailure() {
    try {
      contact = new ContactImpl(id, name, null);
    } catch (Exception e) {
      System.out.println(e);
    }
  }
  @Test
  public void addNotesTest() {
    ContactImpl contactNotes = new ContactImpl(id, name, "");
    contactNotes.addNotes("Vasilis");
    assertEquals("Vasilis", contactNotes.getNotes());
  }

  @After
  public void afterTest() {
    name = null;
    notes = null;
    contact = null;
  }

}
