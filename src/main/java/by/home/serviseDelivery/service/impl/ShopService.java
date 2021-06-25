package by.home.serviseDelivery.service.impl;

import by.home.serviseDelivery.domain.Category;
import by.home.serviseDelivery.domain.Order;
import by.home.serviseDelivery.domain.Product;
import by.home.serviseDelivery.domain.Shop;
import by.home.serviseDelivery.service.IShopService;
import by.home.serviseDelivery.service.interfase.FileService;

import java.util.ArrayList;
import java.util.HashMap;
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
        if (shopList != null) {
            return shopList.stream().collect(Collectors.toMap(Shop::getId, shop -> shop));
        }
        return new HashMap<Integer, Shop>();
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
//        shop.setOrderList(entity.getOrderList());
//        shop.setProductList(entity.getProductList());
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
    private Shop getShopById(Integer id) {
        Map<Integer, Shop> shops = getAll();
        return shops.get(id);
    }

    @Override
    public void addProduct(Integer shopId, Product newProduct) {
        Shop shop = getShopById(shopId);
        List<Product> productList = shop.getProductList();
        Map<Integer, Product> map = productList.stream().collect(Collectors.toMap(Product::getId, product1 -> product1));
        if (map.get(newProduct.getId()) != null) {
            Product product = map.get(newProduct.getId());
            product.setCount(product.getCount() + newProduct.getCount());
            map.put(newProduct.getId(), product);
            shop.setProductList(new ArrayList<>(map.values()));
            save(shop);
            return;
        }
        productList.add(newProduct);
        shop.setProductList(productList);
        save(shop);
    }

    @Override
    public void delProduct(Integer idProduct, Integer shopId) {
        Shop shop = getShopById(shopId);
        List<Product> productList = shop.getProductList();
        shop.setProductList(productList.stream()
                .filter(p -> !p.getId().equals(idProduct)).collect(Collectors.toList()));
        save(shop);
    }

    @Override
    public Order addOrder(Integer shopId, Order order) {
        Shop shop = getShopById(shopId);
        List<Order> orderList = shop.getOrderList();
        if (orderList != null) {
            orderList.add(order);
            shop.setOrderList(orderList);
            save(shop);
            return order;
        }
        List<Order> orderL = new ArrayList<>();
        orderL.add(order);
        shop.setOrderList(orderL);
        save(shop);
        return order;
    }

    @Override
    public void delOrder(Integer shopId, Integer orderId) {
        Shop shop = getShopById(shopId);
        //del by id
        shop.setOrderList(null);

        Map<Integer, Shop> mapShop = getAll();
        mapShop.put(shopId, shop);
        List<Shop> shopList = new ArrayList<>(mapShop.values());
        fileService.writeFile(shopList, SHOP);
    }

    @Override
    public List<Order> getAllOrderShop(Integer shopId) {
        Shop shop = getShopById(shopId);
        return shop.getOrderList();
    }

    @Override
    public List<Product> getAllShopProducts(Integer shopId) {
        Shop shop = getShopById(shopId);
        return shop.getProductList();
    }

    @Override
    public List<Product> getAllProductShopFindByPriceAndCategory(Integer shopId, Integer price, Category category) {
//        Shop shop = getShopById(shopId);
//        List<Product> productList = shop.getProductList();
//        if (price != null & category != null)
//            productList.stream().filter(p -> p.getCount().equals(price)).collect(Collectors.toList()).stream()
//                    .filter(product -> product.getCategorySet().) ()
        return null;
    }

    @Override
    public List<Product> getAllProductSortedByPrice(Integer shopId) {
//        Shop shop = getShopById(shopId);
//        List<Product> productList = shop.getProductList();
//        productList.stream().sorted(product -> product.getPrice()).collect(Collectors.toList());
        return null;
    }
}
