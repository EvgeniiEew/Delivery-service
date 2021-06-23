package by.home.serviseDelivery.service.impl;

import by.home.serviseDelivery.domain.Order;
import by.home.serviseDelivery.service.IOrderService;
import by.home.serviseDelivery.service.interfase.FileService;

import java.util.List;

public class OrderService implements IOrderService {
    private final FileService fileService;

    OrderService(FileService fileService) {
        this.fileService = fileService;
    }

    @Override
    public List<Order> getAll() {
        return null;
    }

    @Override
    public Order save(Order entity) {
        return null;
    }

    @Override
    public Order update(Order entity) {
        return null;
    }

    @Override
    public void deleteEntity(Order entity) {

    }
}
