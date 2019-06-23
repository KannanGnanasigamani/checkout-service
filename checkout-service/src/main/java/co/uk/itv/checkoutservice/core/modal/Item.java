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
public class Item {
    private String itemName;
    private Double itemPrice;
}
