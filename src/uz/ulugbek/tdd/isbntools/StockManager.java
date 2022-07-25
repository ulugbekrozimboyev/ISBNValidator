package uz.ulugbek.tdd.isbntools;

import uz.ulugbek.tdd.isbntools.models.Book;
import uz.ulugbek.tdd.isbntools.services.ExternalISBNDataService;

public class StockManager {

    private ExternalISBNDataService externalISBNDataService;
    private ExternalISBNDataService databaseISBNDataService;

    public void setExternalISBNDataService(ExternalISBNDataService externalISBNDataService) {
        this.externalISBNDataService = externalISBNDataService;
    }

    public void setDatabaseISBNDataService(ExternalISBNDataService databaseISBNDataService) {
        this.databaseISBNDataService = databaseISBNDataService;
    }

    public String getLocatorCode(String isbn) {
        Book book = databaseISBNDataService.lookup(isbn);
        if (book == null) {
            book = externalISBNDataService.lookup(isbn);
        }
        StringBuilder locatorCode = new StringBuilder();
        locatorCode.append(isbn.substring(isbn.length() - 4));
        locatorCode.append(book.getAuthor().substring(0, 1));
        locatorCode.append(book.getTitle().split(" ").length);

        return locatorCode.toString();
    }
}
