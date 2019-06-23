package co.uk.itv.checkoutservice.core.modal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Kannan Gnanasigamani
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Promotion {
    private String itemName;
    private int offerUnit;
    private double offerValue;
}
