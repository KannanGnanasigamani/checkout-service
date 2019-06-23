package co.uk.itv.checkoutservice.service;

import co.uk.itv.checkoutservice.web.presentation.dto.CheckoutDto;

public interface CheckoutService {
    double calculateCheckoutTotal(CheckoutDto checkoutDto) throws Exception;
}
