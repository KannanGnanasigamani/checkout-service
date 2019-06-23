package co.uk.itv.checkoutservice.web;

import co.uk.itv.checkoutservice.core.exception.FatalException;
import co.uk.itv.checkoutservice.core.modal.Item;
import co.uk.itv.checkoutservice.core.modal.Promotion;
import co.uk.itv.checkoutservice.service.CheckoutService;
import co.uk.itv.checkoutservice.web.presentation.dto.CheckoutDto;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class CheckoutControllerTest {

    private static final HttpStatus STATUS_OK = HttpStatus.OK;
    private static final HttpStatus STATUS_EXPECTATION_FAILED = HttpStatus.EXPECTATION_FAILED;
    private static final HttpStatus INTERNAL_SERVER_ERROR = HttpStatus.INTERNAL_SERVER_ERROR;

    private static final Double CHECKOUT_TOTAL = 10.00;

    @Mock
    private CheckoutService mockCheckoutService;

    @InjectMocks
    private CheckoutController checkoutController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnStatus200WhenCalculatingCheckoutTotal() throws Exception {
        CheckoutDto checkoutDto = new CheckoutDto();
        //given
        given(mockCheckoutService.calculateCheckoutTotal(checkoutDto)).willReturn(CHECKOUT_TOTAL);

        //when
        ResponseEntity<Object> response = checkoutController.calculateCheckoutTotal(checkoutDto);

        //then
        assertEquals(STATUS_OK, response.getStatusCode());
        verify(mockCheckoutService).calculateCheckoutTotal(checkoutDto);
        verifyNoMoreInteractions(mockCheckoutService);
    }

    @Test
    public void shouldReturnStatus417WhenCalculatingCheckoutTotal() throws Exception {
        CheckoutDto checkoutDto = new CheckoutDto();
        //given
        given(mockCheckoutService.calculateCheckoutTotal(checkoutDto)).willThrow(FatalException.class);

        //when
        ResponseEntity<Object> response = checkoutController.calculateCheckoutTotal(checkoutDto);

        //then
        assertEquals(STATUS_EXPECTATION_FAILED, response.getStatusCode());
        verify(mockCheckoutService).calculateCheckoutTotal(checkoutDto);
        verifyNoMoreInteractions(mockCheckoutService);
    }

    @Test
    public void shouldReturnStatus500WhenCalculatingCheckoutTotal() throws Exception {
        CheckoutDto checkoutDto = new CheckoutDto();
        checkoutDto.setItems(Arrays.asList(new Item("A", null)));
        //given
        given(mockCheckoutService.calculateCheckoutTotal(checkoutDto)).willThrow(Exception.class);

        //when
        ResponseEntity<Object> response = checkoutController.calculateCheckoutTotal(checkoutDto);

        //then
        assertEquals(INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(mockCheckoutService).calculateCheckoutTotal(checkoutDto);
        verifyNoMoreInteractions(mockCheckoutService);
    }
}