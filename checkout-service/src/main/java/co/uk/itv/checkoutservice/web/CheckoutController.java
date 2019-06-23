package co.uk.itv.checkoutservice.web;

import co.uk.itv.checkoutservice.core.exception.FatalException;
import co.uk.itv.checkoutservice.service.CheckoutService;
import co.uk.itv.checkoutservice.web.presentation.dto.CheckoutDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/checkout/api")
public class CheckoutController {
    private static final Logger LOG = LoggerFactory.getLogger(CheckoutController.class);
    private static final String CALCULATE_CHECKOUT_TOTAL = "/calculateTotal";
    private static final String PRICE_MISSING_ERROR = "One or more Item Prices are null.";

    @Autowired
    private CheckoutService checkoutService;

    @RequestMapping(value = CALCULATE_CHECKOUT_TOTAL, method = RequestMethod.GET)
    public ResponseEntity<Object> calculateCheckoutTotal(@RequestBody CheckoutDto checkoutDto) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Controller-Enter: calculateCheckoutTotal(checkoutDto = [" + checkoutDto + "])");
        }
        Double checkoutTotal;
        try {
            checkoutTotal = checkoutService.calculateCheckoutTotal(checkoutDto);
        } catch (FatalException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.EXPECTATION_FAILED);
        } catch (Exception ex) {
            return new ResponseEntity<>(PRICE_MISSING_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Controller-Exit: calculateCheckoutTotal(checkoutDto = [" + checkoutDto + "])");
        }

        return new ResponseEntity<>(checkoutTotal, HttpStatus.OK);
    }
}
