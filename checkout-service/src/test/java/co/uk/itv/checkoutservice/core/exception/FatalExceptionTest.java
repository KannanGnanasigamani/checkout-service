package co.uk.itv.checkoutservice.core.exception;

import org.junit.Test;

import static org.junit.Assert.*;

public class FatalExceptionTest {

    private static final String EXAMPLE_MESSAGE = "Sample Exception Message";

    @Test
    public void checkExceptionConstructors() {
        Exception e2 = new FatalException(EXAMPLE_MESSAGE);
        assertEquals(EXAMPLE_MESSAGE, e2.getMessage());
    }
}