package com.twa.mediospago.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class MPCheckoutProRequest {
    /** Item name. */
    public String title;

    /** Long item description. */
    public String description;

    /** Image URL. */
    public String pictureUrl;

    /** Category of the item. */
    public String categoryId;

    /** Item's quantity. */
    public Integer quantity;

    /** Unit price. */
    public BigDecimal unitPrice;

    /** Currency ID. ISO_4217 code. */
    public String currencyId;
}
