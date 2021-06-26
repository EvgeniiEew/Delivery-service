package by.home.serviseDelivery;

import by.home.serviseDelivery.domain.*;
import by.home.serviseDelivery.service.IClientService;
import by.home.serviseDelivery.service.IShopService;
import by.home.serviseDelivery.service.JsonFileService;
import by.home.serviseDelivery.service.impl.CenterControlService;
import by.home.serviseDelivery.service.impl.ClientService;
import by.home.serviseDelivery.service.impl.ShopService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class Starter {
    public static void main(String[] args) {
        JsonFileService fileService = new JsonFileService();
        CenterControlService centerControlService = new CenterControlService(fileService);
        centerControlService.showMainMenu();
////        JsonFileService fileService = new JsonFileService();
//        IShopService shopService = new ShopService(fileService);
//        IClientService clientService = new ClientService(fileService);
//        List<Product> productList = shopService.getAllProductShopFindByPriceAndName(1, 3, "хлеб");
//        System.out.println(  productList.get(0).getCount());
//        Client client = new Client(1,"name","fname","street",null);
//        clientService.update(client);
//        clientService.save(client);
//        clientService.deleteEntity(client);
//
//        Product product = new Product(7, 22, 152, "Овощь", null);
//        Product product2 = new Product(9, 22, 152, "Фрукт", null);
//        List<Product> productList = new ArrayList<>();
//        productList.add(product);
//        productList.add(product2);
//        Shop shop = new Shop(6, "КуwМагаз", "Шорса", null, productList);
////        shopService.update(shop); //upd shop
//        shopService.save(shop); //create shop
//        shopService.delProduct(7,5); //del product
//        shopService.addProduct(3,product2); //add product
//        Order order = new Order(1, 123, "Куw", "Брикеля",new Date(), StatusOrder.PENDING, null);
//        clientService.addOrder(order,1);
//        shopService.addOrder(3, order);
//        clientService.editOrder(order,1);
//        clientService.delOrder(1,1);
//        Map<Integer,Order> orderMap = clientService.getOrderList(1);
//        System.out.println( orderMap.get(1).getAddress());

//        shopService.getAll(); //get add shop


    }
}
