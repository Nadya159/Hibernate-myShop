package by.javaguru.mapper;

import by.javaguru.dto.OrderDto;
import by.javaguru.entity.Order;

public class OrderReadMapper implements Mapper<Order, OrderDto>{
    @Override
    public OrderDto mapFrom(Order object) {
        return new OrderDto(
                object.getId(),
                object.getAddress()     //дописать поля
        );
    }
}
