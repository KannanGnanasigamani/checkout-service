package co.uk.itv.checkoutservice.service.impl;

import co.uk.itv.checkoutservice.core.exception.FatalException;
import co.uk.itv.checkoutservice.core.modal.Item;
import co.uk.itv.checkoutservice.core.modal.Promotion;
import co.uk.itv.checkoutservice.service.CheckoutService;
import co.uk.itv.checkoutservice.web.presentation.dto.CheckoutDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Kannan Gnanasigamani
 */
@Service
public class CheckoutServiceImpl implements CheckoutService {
    private static final Logger LOG = LoggerFactory.getLogger(CheckoutServiceImpl.class);

    @Override
    public double calculateCheckoutTotal(CheckoutDto checkoutDto) throws Exception {

        if (LOG.isDebugEnabled()) {
            LOG.debug("Service-Enter: calculateCheckoutTotal(checkoutDto = [" + checkoutDto + "])");
        }

        if (checkoutDto.getItems().size() == 0) {
            throw new FatalException("Your Shopping Cart is Empty.");
        }

        Integer count;
        Map<String, Integer> itemCountMap = new HashMap<>();
        Map<String, Double> itemPriceMap = new HashMap<>();

        for (Item item : checkoutDto.getItems()) {
            count = itemCountMap.get(item.getItemName());
            if (count == null) {
                itemPriceMap.put(item.getItemName(), item.getItemPrice());
                itemCountMap.put(item.getItemName(), 1);
            } else {
                itemCountMap.put(item.getItemName(), count + 1);
            }
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Service-Exit: calculateCheckoutTotal(checkoutDto = [" + checkoutDto + "])");
        }

        return calculateTotalAndApplyPromotion(itemPriceMap, itemCountMap, checkoutDto.getPromotions());
    }

    private double calculateTotalAndApplyPromotion(Map<String, Double> itemPriceMap, Map<String, Integer> itemCountMap, List<Promotion> promotions) {
        double checkoutTotal = 0.0;
        List<Promotion> promotionList;
        for (String itemName : itemCountMap.keySet()) {
            promotionList = promotions
                    .stream()
                    .filter(pro -> pro.getItemName().equals(itemName)).collect(Collectors.toList());

            if (promotionList.size() > 0) {
                checkoutTotal = applyPromotion(itemName, itemCountMap, itemPriceMap, promotionList.get(0), checkoutTotal);
            } else {
                checkoutTotal += itemCountMap.get(itemName) * itemPriceMap.get(itemName);
            }
        }

        return checkoutTotal;
    }

    private double applyPromotion(String itemName, Map<String, Integer> itemCountMap, Map<String, Double> itemPriceMap, Promotion promotion, double checkoutTotal) {
        int offerUnits = itemCountMap.get(itemName) / promotion.getOfferUnit();
        int units = itemCountMap.get(itemName) % promotion.getOfferUnit();

        if (offerUnits != 0) {
            checkoutTotal += offerUnits * promotion.getOfferValue();
        } else {
            checkoutTotal += units * itemPriceMap.get(itemName);
        }

        return checkoutTotal;
    }
}
