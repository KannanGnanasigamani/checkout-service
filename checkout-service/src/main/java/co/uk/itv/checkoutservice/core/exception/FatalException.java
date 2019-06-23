package co.uk.itv.checkoutservice.core.exception;
/**
 * @author Kannan Gnanasigamani
 *
 */
public class FatalException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public FatalException(String message) {
        super(message);
    }
}