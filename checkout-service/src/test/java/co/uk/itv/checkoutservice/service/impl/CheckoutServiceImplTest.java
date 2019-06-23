package co.uk.itv.checkoutservice.service.impl;

import co.uk.itv.checkoutservice.core.exception.FatalException;
import co.uk.itv.checkoutservice.core.modal.Item;
import co.uk.itv.checkoutservice.core.modal.Promotion;
import co.uk.itv.checkoutservice.web.presentation.dto.CheckoutDto;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class CheckoutServiceImplTest {

    public static final String NO_ITEMS_ERROR = "Your Shopping Cart is Empty.";
    @InjectMocks
    private CheckoutServiceImpl mockCheckoutServiceImpl;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldCalculateCheckoutTotalWithPromotions() throws Exception {
        //given
        //when
        double checkoutTotal = mockCheckoutServiceImpl.calculateCheckoutTotal(buildCheckoutDto());
        //then
        assertEquals(1.15, checkoutTotal, 0.0);
    }

    @Test(expected = FatalException.class)
    public void shouldThrowFatalExceptionIfShoppingCartIsEmpty() throws Exception {
        CheckoutDto checkoutDto = buildCheckoutDto();
        List<Item> items = new ArrayList<>();
        checkoutDto.setItems(items);
        //given
        //when
        mockCheckoutServiceImpl.calculateCheckoutTotal(checkoutDto);
    }

    @Test(expected = Exception.class)
    public void shouldThrowExceptionIfItemPriceIsNull() throws Exception {
        CheckoutDto checkoutDto = buildCheckoutDto();
        checkoutDto.setItems(Arrays.asList(new Item("A", null)));
        //given
        //when
        mockCheckoutServiceImpl.calculateCheckoutTotal(checkoutDto);
    }

    @Test
    public void shouldCheckExceptionMessageWhenShoppingCartIsEmpty() throws Exception {
        CheckoutDto checkoutDto = buildCheckoutDto();
        List<Item> items = new ArrayList<>();
        String exceptionMessage = null;
        checkoutDto.setItems(items);
        //given
        //when
        try {
            mockCheckoutServiceImpl.calculateCheckoutTotal(checkoutDto);
        } catch (FatalException ex) {
            exceptionMessage = ex.getMessage();
        }
        assertEquals(NO_ITEMS_ERROR, exceptionMessage);
    }

    @Test
    public void shouldCalculateCheckoutTotalWithoutPromotions() throws Exception {
        CheckoutDto checkoutDto = buildCheckoutDto();
        checkoutDto.setPromotions(new ArrayList<>());
        //given
        //when
        double checkoutTotal = mockCheckoutServiceImpl.calculateCheckoutTotal(checkoutDto);
        //then
        assertEquals(1.3, checkoutTotal, 0.0);
    }

    @Test
    public void shouldCalculateCheckoutTotalWithPromotionsForItemsInOrder() throws Exception {
        CheckoutDto checkoutDto = buildCheckoutDto();
        List<Item> items = new ArrayList<>();
        Collections.addAll(items,
                new Item("A", .50),
                new Item("A", .50),
                new Item("A", .50),
                new Item("B", .30),
                new Item("B", .30),
                new Item("C", .20),
                new Item("D", .15));

        checkoutDto.setItems(items);

        //given
        //when
        double checkoutTotal = mockCheckoutServiceImpl.calculateCheckoutTotal(checkoutDto);
        //then
        assertEquals(2.1, checkoutTotal, 0.0);
    }

    private CheckoutDto buildCheckoutDto() {
        List<Item> items = new ArrayList<>();
        Collections.addAll(items,
                new Item("B", .30),
                new Item("A", .50),
                new Item("B", .30),
                new Item("C", .20));

        List<Promotion> promotions = new ArrayList<>();
        Collections.addAll(promotions,
                new Promotion("B", 2, .45),
                new Promotion("A", 3, 1.30));

        return new CheckoutDto(items, promotions);
    }
}