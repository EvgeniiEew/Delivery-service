package by.home.serviseDelivery.service;

import by.home.serviseDelivery.domain.Category;
import by.home.serviseDelivery.domain.Order;
import by.home.serviseDelivery.domain.Product;
import by.home.serviseDelivery.domain.Shop;
import by.home.serviseDelivery.service.interfase.CRUDService;

import java.util.List;

public interface IShopService extends CRUDService<Integer, Shop> {

    void addProduct(Integer shopId, Product product);

    void delProduct(Integer productId, Integer shopId);

    void addOrder(Integer shopId, Order order);

    void delOrder(Integer shopId, Integer orderId);

    List<Product> getAllProductShop(Integer shopId);

    List<Order> getAllOrderShop(Integer shopId);

    //поиск товара по цене и по категории  или
    List<Product> getAllProductShopFindByPriceAndCategory(Integer shopId, Integer price, Category category);

    //сортировка товара по Ценее
    List<Product> getAllProductSortedByPrice(Integer shopId);
    //сортировка

}
