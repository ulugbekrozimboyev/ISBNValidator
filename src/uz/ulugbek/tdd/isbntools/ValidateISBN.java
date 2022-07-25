package uz.ulugbek.tdd.isbntools;

public class ValidateISBN {

    public static final int LENGTH_OF_LONG_ISBN = 13;
    public static final int LENGTH_OF_SHORT_ISBN = 10;

    public boolean checkISBN(String isbn) {

        if (isbn.length() == LENGTH_OF_LONG_ISBN) return isLongISBN(isbn);

        if (isbn.length() == LENGTH_OF_SHORT_ISBN) return isShortISBN(isbn);

        throw new NumberFormatException("ISBN must be 10 or 13 symbol");
    }

    private boolean isShortISBN(String isbn) {
        int total = 0;

        for (int i = 0; i < LENGTH_OF_SHORT_ISBN; i++) {
            char currentChar = isbn.charAt(i);
            if (!Character.isDigit(currentChar)) {
                if (i == 9 && currentChar == 'X') {
                    total += 10;
                } else {
                    throw new NumberFormatException("ISBN format not valid");
                }
            } else {
                int tmp = Character.getNumericValue(currentChar);
                total += tmp * (10 - i);
            }
        }

        return total % 11 == 0;
    }

    public boolean isLongISBN(String isbn) {

        int total = 0;

        for (int i = 0; i < LENGTH_OF_LONG_ISBN; i++) {
            char currentChar = isbn.charAt(i);
            if (!Character.isDigit(currentChar)) {
                if (i == 12 && currentChar == 'X') {
                    total += 10;
                } else {
                    throw new NumberFormatException("ISBN format not valid");
                }
            } else {
                int tmp = Character.getNumericValue(currentChar);
                total += tmp * ((i % 2 == 1) ? 3 : 1);
            }
        }

        return total % 10 == 0;
    }
}
