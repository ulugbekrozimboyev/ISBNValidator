package uz.ulugbek.tdd.isbntools;

import org.junit.Test;
import uz.ulugbek.tdd.isbntools.models.Book;
import uz.ulugbek.tdd.isbntools.services.ExternalISBNDataService;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

public class StockManagerTest {

    @Test
    public void stockManagerTest() {

        ExternalISBNDataService externalISBNDataService = new ExternalISBNDataService() {
            @Override
            public Book lookup(String isbn) {
                return new Book(isbn, "J. Smit", "Of Mice And Men");
            }
        };

        ExternalISBNDataService dataService = new ExternalISBNDataService() {
            @Override
            public Book lookup(String isbn) {
                return null;
            }
        };

        StockManager stockManager = new StockManager();
        stockManager.setExternalISBNDataService(externalISBNDataService);
        stockManager.setDatabaseISBNDataService(dataService);

        String locatorCode = stockManager.getLocatorCode("0735619670");
        assertEquals("9670J4", locatorCode);

    }

    @Test
    public void databaseIsUsedIfDataIsPresent() {
        ExternalISBNDataService databaseservice = mock(ExternalISBNDataService.class);
        ExternalISBNDataService webService = mock(ExternalISBNDataService.class);

        when(databaseservice.lookup("0735619670")).thenReturn(new Book("0735619670", "sdfsd", "sdfsdf"));

        StockManager stockManager = new StockManager();
        stockManager.setExternalISBNDataService(webService);
        stockManager.setDatabaseISBNDataService(databaseservice);

        String locatorCode = stockManager.getLocatorCode("0735619670");

        verify(databaseservice, times(1)).lookup("0735619670");
        verify(webService, times(0)).lookup("0735619670");
    }

    @Test
    public void databaseIsUsedIfDataIsNotPresentInDatabase() {
        ExternalISBNDataService databaseservice = mock(ExternalISBNDataService.class);
        ExternalISBNDataService webService = mock(ExternalISBNDataService.class);

        when(databaseservice.lookup("0735619670")).thenReturn(null);
        when(webService.lookup("0735619670")).thenReturn(new Book("0735619670", "sdfsd", "sdfsdf"));

        StockManager stockManager = new StockManager();
        stockManager.setExternalISBNDataService(webService);
        stockManager.setDatabaseISBNDataService(databaseservice);

        String locatorCode = stockManager.getLocatorCode("0735619670");

        verify(databaseservice, times(1)).lookup("0735619670");
        verify(webService, times(1)).lookup("0735619670");
    }

}
