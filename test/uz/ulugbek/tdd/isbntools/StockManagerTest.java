package uz.ulugbek.tdd.isbntools;

import org.junit.Before;
import org.junit.Test;
import uz.ulugbek.tdd.isbntools.models.Book;
import uz.ulugbek.tdd.isbntools.services.ExternalISBNDataService;

import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.*;

public class StockManagerTest {

    private ExternalISBNDataService testWebService;
    private ExternalISBNDataService testDatabaseService;
    private StockManager stockManager;

    @Before
    public void setup(){
        System.out.println("setup");

        testWebService = mock(ExternalISBNDataService.class);
        testDatabaseService = mock(ExternalISBNDataService.class);

        stockManager = new StockManager();

        stockManager.setExternalISBNDataService(testWebService);
        stockManager.setDatabaseISBNDataService(testDatabaseService);

    }

    @Test
    public void stockManagerTest() {

        when(testWebService.lookup("0735619670")).thenReturn(new Book("0735619670", "J. Smit", "Of Mice And Men"));

        when(testDatabaseService.lookup(anyString())).thenReturn(null);

        String locatorCode = stockManager.getLocatorCode("0735619670");
        assertEquals("9670J4", locatorCode);

    }

    @Test
    public void databaseIsUsedIfDataIsPresent() {

        when(testDatabaseService.lookup("0735619670")).thenReturn(new Book("0735619670", "sdfsd", "sdfsdf"));

        String locatorCode = stockManager.getLocatorCode("0735619670");

        verify(testDatabaseService, times(1)).lookup("0735619670");
        verify(testWebService, times(0)).lookup("0735619670");
    }

    @Test
    public void databaseIsUsedIfDataIsNotPresentInDatabase() {

        when(testDatabaseService.lookup("0735619670")).thenReturn(null);
        when(testWebService.lookup("0735619670")).thenReturn(new Book("0735619670", "sdfsd", "sdfsdf"));

        String locatorCode = stockManager.getLocatorCode("0735619670");

        verify(testDatabaseService, times(1)).lookup("0735619670");
        verify(testWebService, times(1)).lookup("0735619670");
    }

}
