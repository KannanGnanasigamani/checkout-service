package co.uk.itv.checkoutservice.web.presentation.dto;

import co.uk.itv.checkoutservice.core.modal.Item;
import co.uk.itv.checkoutservice.core.modal.Promotion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
/**
 * @author Kannan Gnanasigamani
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutDto {
    List<Item> items;
    List<Promotion> promotions;
}
