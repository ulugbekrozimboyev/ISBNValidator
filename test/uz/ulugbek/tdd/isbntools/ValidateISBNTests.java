package uz.ulugbek.tdd.isbntools;

import org.junit.Test;

import static org.junit.Assert.*;

public class ValidateISBNTests {

    @Test
    public void checkValidTest(){
        ValidateISBN validateISBN = new ValidateISBN();
        boolean result = validateISBN.checkISBN("0735619670");
        assertTrue("First value", result);
        result = validateISBN.checkISBN("0135957052");
        assertTrue("Second value", result);
    }

    @Test
    public void checkValid13DigitTest(){
        ValidateISBN validateISBN = new ValidateISBN();
        boolean result = validateISBN.isLongISBN("9781617295850");
        assertTrue("First value", result);
        result = validateISBN.isLongISBN("9781617295102");
        assertTrue("Second value", result);
    }

    @Test
    public void checkValidWithEndingXTest(){
        ValidateISBN validateISBN = new ValidateISBN();
        boolean result = validateISBN.checkISBN("161729585X");
        assertTrue(result);
    }

    @Test
    public void checkInValidTest(){
        ValidateISBN validateISBN = new ValidateISBN();
        boolean result = validateISBN.checkISBN("0735619671");
        assertFalse(result);
    }

    @Test(expected = NumberFormatException.class)
    public void wrongFormatTest(){
        ValidateISBN validateISBN = new ValidateISBN();
        validateISBN.checkISBN("735619671");
    }

    @Test(expected = NumberFormatException.class)
    public void wrongFormatWithTextTest(){
        ValidateISBN validateISBN = new ValidateISBN();
        validateISBN.checkISBN("QWERTASDFG");
    }

}
