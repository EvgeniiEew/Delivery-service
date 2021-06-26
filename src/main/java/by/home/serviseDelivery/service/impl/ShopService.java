package by.home.serviseDelivery.service.impl;

import by.home.serviseDelivery.domain.Category;
import by.home.serviseDelivery.domain.Order;
import by.home.serviseDelivery.domain.Product;
import by.home.serviseDelivery.domain.Shop;
import by.home.serviseDelivery.service.IShopService;
import by.home.serviseDelivery.service.interfase.FileService;

import java.util.*;
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
        return save(shops.put(entity.getId(), shop));
    }

    @Override
    public void deleteEntity(Shop entity) {
        Map<Integer, Shop> shops = getAll();
        shops.remove(entity.getId());
        List<Shop> values = new ArrayList<>(shops.values());
        fileService.writeFile(values, SHOP);
    }

    public Shop getShopById(Integer id) {
        Map<Integer, Shop> shops = getAll();
        return shops.get(id);
    }

    @Override
    public void addProduct(Integer shopId, Product newProduct) {
        Shop shop = getShopById(shopId);
        List<Product> productList = shop.getProductList();
        if (productList != null) {
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
            return;
        }
        List<Product> newProductList = new ArrayList<>();
        newProductList.add(newProduct);
        shop.setProductList(newProductList);
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
        List<Order> orderList = shop.getOrderList();
        shop.setOrderList(orderList.stream().filter(order -> !order.getId().equals(orderId)).collect(Collectors.toList()));
        Map<Integer, Shop> mapShop = getAll();
        mapShop.put(shopId, shop);
        List<Shop> shopList = new ArrayList<>(mapShop.values());
        fileService.writeFile(shopList, SHOP);
    }

    @Override
    public void editProduct(Integer shopId, Product product) {
        Shop shop = getShopById(shopId);
        List<Product> productList = shop.getProductList();
        if (productList != null) {
            Map<Integer, Product> map = productList.stream().collect(Collectors.toMap(Product::getId, product1 -> product1));
            if (map.get(product.getId()) != null) {
                map.put(product.getId(), product);
                List<Product> newProducts = new ArrayList<>(map.values());
                shop.setProductList(newProducts);
                save(shop);
            }
        }
    }

    @Override
    public List<Order> getAllOrderShop(Integer shopId) {
        Shop shop = getShopById(shopId);
        return shop.getOrderList();
    }

    @Override
    public Map<Integer, Product> getAllShopProducts(Integer shopId) {
        Shop shop = getShopById(shopId);
        Map<Integer, Product> mapProduct = new HashMap<>();
        if (shop != null && shop.getProductList() != null) {
            List<Product> productList = shop.getProductList();
            mapProduct = productList.stream().collect(Collectors.toMap(Product::getId, product1 -> product1));
        }
        return mapProduct;
    }

    @Override
    public List<Product> getAllProductShopFindByPriceAndName(Integer shopId, Integer price, String productName) {
        Shop shop = getShopById(shopId);
        List<Product> productList = shop.getProductList();
        if (price != null && productName != null && productList != null) {
            return productList.stream().filter(p -> p.getPrice() == (price) && p.getName().equals(productName)).
                    collect(Collectors.toList());
        }
        return productList;
    }

    @Override
    public List<Product> getAllProductSortedByPrice(Integer shopId) {
        Shop shop = getShopById(shopId);
        List<Product> productList = shop.getProductList();
        return productList.stream().sorted(Comparator.comparingInt(Product::getPrice)).collect(Collectors.toList());
    }

    @Override
    public List<Product> getAllProductSortedByCategory(Integer shopId, String category) {
        Shop shop = getShopById(shopId);
        List<Product> productList = shop.getProductList();
        List<Product> sortedList = new ArrayList<>();
        for (Product product : productList) {
            for (Category category1 : product.getCategorySet()) {
                if (category1.getName().equals(category)) {
                    sortedList.add(product);
                }
            }
        }


//            product.setCategorySet(product.getCategorySet().stream().filter(category1 ->
//                    category1.getName().contains(category)).collect(Collectors.toSet()));
//            product.setCategorySet(product.getCategorySet().stream().filter(category1 ->
//                    category1.getName().equals(category)).collect(Collectors.toSet()));
//            if (product.getCategorySet() != null) {
//            sortedList.add(product);
//                System.out.println("W");
        return sortedList;
    }

    @Override
    public Map<Integer, String> getShopInfoIdAndName() {
        if (getListShop() != null) {
            return getListShop().stream().collect(Collectors.toMap(Shop::getId, Shop::getName));
        }
        Map<Integer, String> integerStringMap = new HashMap<>();
        integerStringMap.put(666, "Нет зарегистрированных магазинов");
        return integerStringMap;
    }
}
