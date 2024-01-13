package by.javaguru.service;

import by.javaguru.dao.ProductDao;
import by.javaguru.dto.ProductDto;
import by.javaguru.mapper.ProductReadMapper;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE, force = true)
@RequiredArgsConstructor
public class ProductService {
    private static final ProductService INSTANCE = new ProductService();
    private final ProductDao productDao;
    private final ProductReadMapper productReadMapper;

    public List<ProductDto> findAll() {
        return productDao.findAll().stream().map(productReadMapper::mapFrom).collect(Collectors.toList());
    }

/*    public List<ProductDto> findAll() {
        return productDao.findAll().stream().map(product ->
                        new ProductDto(product.getId(), "%s - %s - %s".formatted(
                                product.getName(), product.getPrice(), product.getBalance())))
                .collect(Collectors.toList());
    }*/

    public static ProductService getInstance() {
        return INSTANCE;
    }
}
