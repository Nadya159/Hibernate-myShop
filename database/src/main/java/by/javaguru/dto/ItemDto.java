package by.javaguru.dto;

import java.math.BigDecimal;

public record ItemDto(
        Integer id,
        Integer quantity,
        BigDecimal priceOrder) {
}
