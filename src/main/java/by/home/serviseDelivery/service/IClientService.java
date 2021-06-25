package by.home.serviseDelivery.service;

import by.home.serviseDelivery.domain.Client;
import by.home.serviseDelivery.domain.Order;
import by.home.serviseDelivery.service.interfase.CRUDService;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.util.List;
import java.util.Map;

public interface IClientService extends CRUDService<Integer, Client> {
    void addOrder(Order order, Integer clientId);

    void editOrder(Order order, Integer clientId);

    Map<Integer,Order> getOrderList(Integer clientId);

    void delOrder(Integer clientId, Integer orderId);
}
