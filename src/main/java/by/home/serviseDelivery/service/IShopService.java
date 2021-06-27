package by.home.serviseDelivery.service;

import by.home.serviseDelivery.domain.Order;
import by.home.serviseDelivery.domain.Product;
import by.home.serviseDelivery.domain.Shop;
import by.home.serviseDelivery.service.interfase.CRUDService;

import java.util.List;
import java.util.Map;

public interface IShopService extends CRUDService<Integer, Shop> {

    void addProduct(Integer shopId, Product product);

    void delProduct(Integer productId, Integer shopId);

    Order addOrder(Integer shopId, Order order);

    void delOrder(Integer shopId, Integer orderId);

    void editProduct(Integer shopId, Product product);

    Map<Integer, Product> getAllShopProducts(Integer shopId);

    List<Order> getAllOrderShop(Integer shopId);

    List<Product> getAllProductShopFindByPriceAndName(Integer shopId, Integer price, String productName);

    List<Product> getAllProductSortedByPrice(Integer shopId);

    List<Product> getAllProductSortedByCategory(Integer shopId, String category);

    Map<Integer, String> getShopInfoIdAndName();

    Shop getShopById(Integer id);
}
