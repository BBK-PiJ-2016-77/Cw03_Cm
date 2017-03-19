package test;

import impl.ContactImpl;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import spec.Contact;



/**
 * Tests for ContactImpl.
 */
public class ContactImplTest {
  private Contact contact = null;
  private int id;
  private String name;
  private String notes;

  /**
   * Setting up values before the tests.
   */
  @Before
  public void beforeTest() {
    name = "Maria";
    notes = "Cleaver";
    id = 30;
  }

  /**
   * Asserting the constructor added the right id.
   */
  @Test
  public void contactImplInitializationIdSuccess() {
    contact = new ContactImpl(id, name, notes);
    assertEquals(id,contact.getId());
  }

  /**
   * Asserting the constructor added the right name.
   */
  @Test
  public void contactImplInitializationNameSuccess() {
    contact = new ContactImpl(id, name, notes);
    assertEquals(name,contact.getName());
  }

  /**
   * Asserting the constructor added the right notes.
   */
  @Test
  public void contactImplInitializationNotesSuccess() {
    contact = new ContactImpl(id, name, notes);
    assertEquals(notes,contact.getNotes());
  }

  /**
   * Asserting the constructor throws an exception with the instructed message.
   */
  @Test
  public void contactImplInitializationIdFailure() {
    try {
      contact = new ContactImpl(-1, name , notes);
    } catch (Exception e) {
      assertEquals(e.getMessage(), "Invalid ID!");
    }
  }

  /**
   * Asserting the constructor throws exception for null values.
   */
  @Test
  public void contactImplInitializationNameFailure() {
    try {
      contact = new ContactImpl(id,null, notes);
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  /**
   * Asserting the constructor throws exception for null values.
   */
  @Test
  public void contactImplInitializationNotesFailure() {
    try {
      contact = new ContactImpl(id, name, null);
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  /**
   * Testing that addNotes method adds the right notes.
   */
  @Test
  public void addNotesTestSuccess() {
    ContactImpl contactNotes = new ContactImpl(id, name, "");
    contactNotes.addNotes("Vasilis");
    assertEquals("Vasilis", contactNotes.getNotes());
  }

  /**
   * Clear.
   */
  @After
  public void afterTest() {
    name = null;
    notes = null;
    contact = null;
  }

}
