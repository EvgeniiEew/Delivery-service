package by.home.serviseDelivery.service;

import by.home.serviseDelivery.domain.Order;
import by.home.serviseDelivery.domain.Product;
import by.home.serviseDelivery.domain.Shop;
import by.home.serviseDelivery.service.interfase.CRUDService;

public interface IShopService extends CRUDService<Shop> {

    void addProduct(Integer shopId, Product product);
//   delProduct // дописать
//   void addOrder(Order order);
    public Shop createShop(String name, String address);
}
