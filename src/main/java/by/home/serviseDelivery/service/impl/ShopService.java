package by.home.serviseDelivery.service.impl;

import by.home.serviseDelivery.domain.Product;
import by.home.serviseDelivery.domain.Shop;
import by.home.serviseDelivery.service.IShopService;
import by.home.serviseDelivery.service.interfase.FileService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class ShopService implements IShopService {

    private final FileService<Shop> fileService;

    public ShopService(FileService<Shop> fileService) {
        this.fileService = fileService;
    }

    @Override
    public List<Shop> getAll() {
        return (List<Shop>) fileService.readFile("report");
    }

    @Override
    public Shop save(Shop entity) {
        List<Shop> shopList = new ArrayList<>();
        shopList.add(entity);
        fileService.writeFile(shopList, "report");
        return entity;
    }

    @Override
    public Shop update(Shop entity) {
        Map<Integer, Shop> shops = getAll().stream().collect(Collectors.toMap(Shop::getId, shop -> shop));
        Shop shop = shops.get(entity.getId());
        shop.setAddress(entity.getAddress());
        shop.setName(entity.getName());
        shop.setOrderList(entity.getOrderList());
        shop.setProductList(entity.getProductList());
        return save(shops.put(entity.getId(), shop));
    }

    @Override
    public void deleteEntity(Shop entity) {

    }

    public Shop createShop(String name, String address) {
        Product product = new Product(4, 123, 112, "wadw", null);
        ArrayList pr = new ArrayList();
        pr.add(product);
        Shop shop = new Shop();
        shop.setId(1);
        shop.setName(name);
        shop.setAddress(address);
        shop.setProductList(pr);
        List<Shop> shopList = new ArrayList<>();
        shopList.add(shop);
        fileService.writeFile(shopList, "report");
//
//        Map<Integer, Shop> shops = new Map<Integer, Shop>()

        return shop;
    }

    @Override
    public void addProduct(Integer shopId, Product product) {
        Map<Integer, Shop> shops = getAll().stream().collect(Collectors.toMap(Shop::getId, shop -> shop));
        Shop shop = shops.get(shopId);
        List<Product> productList = shops.get(shopId).getProductList();
        productList.add(1, product);
        shop.setProductList(productList);
        save(shop);
    }
////
//    @Override
//    public void addOrder(Order order) {
//
//    }
}
