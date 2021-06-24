package by.home.serviseDelivery;

import by.home.serviseDelivery.domain.Order;
import by.home.serviseDelivery.domain.Product;
import by.home.serviseDelivery.domain.StatusOrder;
import by.home.serviseDelivery.service.IShopService;
import by.home.serviseDelivery.service.JsonFileService;
import by.home.serviseDelivery.service.impl.ShopService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Starter {
    public static void main(String[] args) {
        JsonFileService fileService = new JsonFileService();
        IShopService shopService = new ShopService(fileService);
        Product product = new Product(7, 22, 152, "Овощь", null);
        Product product2 = new Product(9, 22, 152, "Фрукт", null);
        List<Product> productList = new ArrayList<>();
        productList.add(product);
        productList.add(product2);
//        Shop shop = new Shop(3,"КуwМагаз","Шорса",null,null);
//        shopService.update(shop); //upd shop
//        shopService.save(shop); //create shop
//        shopService.delProduct(7,5); //del product
//        shopService.addProduct(3,product2); //add product
        Order order = new Order(1, 123, "КуwМагаз",new Date(), StatusOrder.PENDING, productList);
        shopService.addOrder(3, order);
        shopService.getAll(); //get add shop


    }
}
