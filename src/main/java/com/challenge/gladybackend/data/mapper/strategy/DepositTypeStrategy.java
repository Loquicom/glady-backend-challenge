package com.challenge.gladybackend.data.mapper.strategy;

import java.util.Date;

public interface DepositTypeStrategy {

    /**
     * Calculate the expired date for a deposit
     *
     * @param now The actual date
     * @return The expired date
     */
    public Date calculateExpireDate(Date now);

}
