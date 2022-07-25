package uz.ulugbek.tdd.isbntools.services;

import uz.ulugbek.tdd.isbntools.models.Book;

public interface ExternalISBNDataService {

    public Book lookup(String isbn);

}
