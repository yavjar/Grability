package grability.com.grability.entities.contentInfo;

import java.math.BigDecimal;

/**
 * Created by Salsa on 28-May-16.
 */
public class PriceEntity {
    String lable;
    BigDecimal amount;
    String currency;

    public PriceEntity(String lable, BigDecimal amount, String currency) {
        this.lable = lable;
        this.amount = amount;
        this.currency = currency;
    }

    public String getLable() {
        return lable;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }
}
