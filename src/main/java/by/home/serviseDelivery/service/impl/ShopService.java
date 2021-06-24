package by.home.serviseDelivery.service.impl;

import by.home.serviseDelivery.domain.Order;
import by.home.serviseDelivery.domain.Product;
import by.home.serviseDelivery.domain.Shop;
import by.home.serviseDelivery.service.IShopService;
import by.home.serviseDelivery.service.interfase.FileService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class ShopService implements IShopService {

    private final FileService<Shop> fileService;
    private final String SHOP = "shop";

    public ShopService(FileService<Shop> fileService) {
        this.fileService = fileService;
    }

    @Override
    public Map<Integer, Shop> getAll() {
        List<Shop> shopList = (List<Shop>) fileService.readFile(SHOP, new Shop());
        return shopList.stream().collect(Collectors.toMap(Shop::getId, shop -> shop));
    }

    private List<Shop> getListShop() {
        return (List<Shop>) fileService.readFile(SHOP, new Shop());
    }


    @Override
    public Shop save(Shop entity) {
        List<Shop> shopList = getListShop();
        Map<Integer, Shop> shops = getAll();
        if (shopList == null) {
            List<Shop> newShopList = new ArrayList<>();
            newShopList.add(entity);
            fileService.writeFile(newShopList, SHOP);
            return entity;
        }
        Shop shop = shops.get(entity.getId());
        if (shop != null) {
            shops.put(entity.getId(), entity);
            List<Shop> values = new ArrayList<>(shops.values());
            fileService.writeFile(values, SHOP);
            return entity;
        } else {
            shopList.add(entity);
        }
        fileService.writeFile(shopList, SHOP);
        return entity;
    }

    @Override
    public Shop update(Shop entity) {
        Map<Integer, Shop> shops = getAll();
        Shop shop = shops.get(entity.getId());
        shop.setAddress(entity.getAddress());
        shop.setName(entity.getName());
        shop.setOrderList(entity.getOrderList());
        shop.setProductList(entity.getProductList());
        return save(shops.put(entity.getId(), shop));
    }

    @Override
    public void deleteEntity(Shop entity) {
        Map<Integer, Shop> shops = getAll();
        shops.remove(entity.getId());
        List<Shop> values = new ArrayList<>(shops.values());
        fileService.writeFile(values, SHOP);
    }

//    public Shop createShop(String name, String address) {
//        Product product = new Product(4, 123, 112, "wadw", null);
//        ArrayList pr = new ArrayList();
//        pr.add(product);
//        Shop shop = new Shop();
//        shop.setId(1);
//        shop.setName(name);
//        shop.setAddress(address);
//        shop.setProductList(pr);
//        List<Shop> shopList = new ArrayList<>();
//        shopList.add(shop);
//        fileService.writeFile(shopList, SHOP);
//        return shop;
//    }

    @Override
    public void addProduct(Integer shopId, Product product) {
        Map<Integer, Shop> shops = getAll();
        Shop shop = shops.get(shopId);
        List<Product> productList = shop.getProductList();
        productList.add(productList.size() + 1, product);
        shop.setProductList(productList);
        save(shop);
    }

    @Override
    public void delProduct(Integer idProduct, Integer shopId) {
        Map<Integer, Shop> shops = getAll();
        Shop shop = shops.get(shopId);
        List<Product> productList = shop.getProductList();
        shop.setProductList(productList.stream()
                .filter(p -> !p.getId().equals(idProduct)).collect(Collectors.toList()));
        save(shop);
    }

    @Override
    public void addOrder(Integer shopId, Order order) {
        Map<Integer, Shop> shops = getAll();
        Shop shop = shops.get(shopId);
        List<Order> orderList = shop.getOrderList();
        orderList.set(orderList.size() + 1, order);
        shop.setOrderList(orderList);
        save(shop);
    }
}
