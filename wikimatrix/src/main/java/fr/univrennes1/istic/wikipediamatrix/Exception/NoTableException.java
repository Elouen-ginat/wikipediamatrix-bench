package fr.univrennes1.istic.wikipediamatrix.Exception;

public class NoTableException extends Exception {

    public NoTableException(String url) {
       super(getMessage(url));
    }

    private static String getMessage(String url) {
        StringBuilder errorMessage = new StringBuilder();
        errorMessage.append("No table found at: ");
        errorMessage.append(url);
        return errorMessage.toString();
    }
}
