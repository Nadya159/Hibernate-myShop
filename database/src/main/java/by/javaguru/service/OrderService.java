package by.javaguru.service;

import by.javaguru.dao.OrderDao;
import by.javaguru.dto.OrderDto;
import by.javaguru.mapper.OrderReadMapper;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE, force = true)
public class OrderService {
    private static final OrderService INSTANCE = new OrderService();
    private final OrderDao orderDao;
    private final OrderReadMapper orderReadMapper;

    public List<OrderDto> findAll(){
        return orderDao.findAll().stream().map(orderReadMapper::mapFrom).collect(Collectors.toList());
    }

/*    public List<OrderDto> findAll() {
        return orderDao.findAll().stream().map(order ->
                        new OrderDto(order.getId(), "%s - %s - %s".formatted(
                                order.getAddress(), order.getStatus(), order.getDeliveryDate().toLocalDate())))
                .collect(Collectors.toList());
    }
*/
    public static OrderService getInstance() {
        return INSTANCE;
    }
}
