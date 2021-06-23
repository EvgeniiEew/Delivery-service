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

public class Starter {
    public static void main(String[] args) {
        JsonFileService fileService = new JsonFileService();
        IShopService shopService = new ShopService(fileService);
        shopService.createShop("Guns","street");
        shopService.addProduct(1, new Product(3,15,125,"Фрукты",null));
        shopService.addProduct(1, new Product(4,15,125,"овощи",null));
        shopService.getAll();
//        Gson gson = new Gson();
//        String jsonString = gson.toJson(shop);
//        System.out.println(jsonString);
//        try (FileWriter writer = new FileWriter("C:/report.json", true)) {
//            writer.write(jsonString);
//            writer.append('\n');
//
//            writer.flush();
//        } catch (IOException ex) {
//            System.out.println(ex.getMessage());
//        }
    }
}
