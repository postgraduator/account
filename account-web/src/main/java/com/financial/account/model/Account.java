package com.financial.account.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class Account {
    private UUID id;
    private BigDecimal value;
}
