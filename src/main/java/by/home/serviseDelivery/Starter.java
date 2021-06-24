package by.home.serviseDelivery;

import by.home.serviseDelivery.domain.Product;
import by.home.serviseDelivery.domain.Shop;
import by.home.serviseDelivery.service.IShopService;
import by.home.serviseDelivery.service.JsonFileService;
import by.home.serviseDelivery.service.impl.ShopService;
import by.home.serviseDelivery.service.interfase.FileService;
import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Starter {
    public static void main(String[] args) {
        JsonFileService fileService = new JsonFileService();
        IShopService shopService = new ShopService(fileService);
        Product product = new Product(7,22,152,"Овощь",null);
        Product product2 = new Product(8,22,152,"Фрукт",null);
        List<Product> productList = new ArrayList<>();
        productList.add(product);
        productList.add(product2);
        Shop shop = new Shop(5,"КулинарныйМагаз","Шорса",null,productList);
        shopService.save(shop); //create shop
        shopService.delProduct(7,5);
//        shopService.addProduct(1, new Product(3,15,125,"Фрукты",null)); //add product
//        shopService.addProduct(1, new Product(4,15,125,"овощи",null)); //add product
        shopService.getAll(); //get add shop


    }
}
