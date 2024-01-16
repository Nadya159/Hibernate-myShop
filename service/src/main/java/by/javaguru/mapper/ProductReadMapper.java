package by.javaguru.mapper;

import by.javaguru.dto.ProductDto;
import by.javaguru.entity.Product;

public class ProductReadMapper implements Mapper<Product, ProductDto> {
    private static final ProductReadMapper INSTANCE = new ProductReadMapper();

    @Override
    public ProductDto mapFrom(Product object) {
        return new ProductDto(
                object.getId(),
                object.getName()
        );
    }

    public static ProductReadMapper getInstance() {
        return INSTANCE;
    }
}
