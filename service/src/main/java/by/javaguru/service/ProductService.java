package by.javaguru.service;

import by.javaguru.mapper.ProductReadMapper;
import by.javaguru.dao.ProductDao;
import by.javaguru.dto.ProductDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class ProductService {
    private static final ProductService INSTANCE = new ProductService();
    private final ProductDao productDao = ProductDao.getInstance();
    private final ProductReadMapper productReadMapper = ProductReadMapper.getInstance();

    public List<ProductDto> findAll() {
        return productDao.findAll().stream().map(productReadMapper::mapFrom).collect(Collectors.toList());
    }

    public static ProductService getInstance() {
        return INSTANCE;
    }
}
