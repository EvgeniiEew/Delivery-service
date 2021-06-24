package by.home.serviseDelivery.service;

import by.home.serviseDelivery.domain.Order;
import by.home.serviseDelivery.domain.Product;
import by.home.serviseDelivery.domain.Shop;
import by.home.serviseDelivery.service.interfase.CRUDService;

public interface IShopService extends CRUDService<Integer, Shop> {

    void addProduct(Integer shopId, Product product);

    void delProduct(Integer idProduct,Integer idShop); // дописать

    void addOrder(Integer shopId, Order order);

//    public Shop createShop(String name, String address);
}
