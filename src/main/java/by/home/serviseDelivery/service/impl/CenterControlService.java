package by.home.serviseDelivery.service.impl;

import by.home.serviseDelivery.domain.Category;
import by.home.serviseDelivery.domain.Product;
import by.home.serviseDelivery.domain.Shop;
import by.home.serviseDelivery.service.IClientService;
import by.home.serviseDelivery.service.IShopService;
import by.home.serviseDelivery.service.interfase.FileService;

import java.util.*;
import java.util.stream.Collectors;

public class CenterControlService {
    private final IShopService shopService;
    private final IClientService clientService;
    private int numberShop;
    private Integer sequenceId = 1;
    private int typeSort;
    private int numberProduct;

    public CenterControlService(FileService fileService) {
        this.shopService = new ShopService(fileService);
        this.clientService = new ClientService(fileService);
    }

    private StringBuilder getViewMeu() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Главное меню:\n");
        stringBuilder.append("\n1.Магазины");
        stringBuilder.append("\n2.Клиенты");
        stringBuilder.append("\n3.Товары");
        stringBuilder.append("\n0.Выход");
        return stringBuilder;
    }

    public void showMainMenu() {
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println(this.getViewMeu().toString());
            switch (in.nextInt()) {
                case (1):
                    showMenuShop();
                    break;
                case (2):
//todo                    showClients();
                    break;
                case (3):
//todo                    showProduct();
                    break;
                case (0):
                    System.exit(0);
                    break;
                default:
                    this.getViewMeu();
                    break;
            }
        }
    }

    private StringBuilder getShopView() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n1.Выбор магазина для: заказа, просмотра, изменения и удаления товаров");
        stringBuilder.append("\n2.Регистрация магазина");
        stringBuilder.append("\n0.В главное меню");
        return stringBuilder;
    }

    private void showMenuShop() {
        Scanner in = new Scanner(System.in);
        for (; ; ) {
            System.out.println(this.getShopView().toString());
            switch (in.nextInt()) {
                case (1):
                    showListShops();
                    break;
                case (2):
                    shopRegister();
                    break;
                case (0):
                    showMainMenu();
                    break;
                default:
                    this.getShopView();
                    break;
            }
        }
    }

    private void shopRegister() {
        Scanner in = new Scanner(System.in);
        Shop newShop = new Shop();
        newShop.setId(sequenceId);
        sequenceId++;
        System.out.println("Введите название магазина:");
        newShop.setName(in.nextLine());
        System.out.println("Введите адрес магазина:");
        newShop.setAddress(in.nextLine());
        shopService.save(newShop);
        showListShops();
    }

    private void showListShops() {
        Scanner in = new Scanner(System.in);
        System.out.println(getShopsFromSql().toString());
        numberShop = in.nextInt();
        if (numberShop == 0) {
            showMenuShop();
        }
        if (shopService.getShopInfoIdAndName().get(numberShop) != null && numberShop != 666) {
            showShops(numberShop);
        }
        showListShops();
    }

    private StringBuilder getShopsFromSql() {
        StringBuilder stringBuilder = new StringBuilder();
        Map<Integer, String> infoShops = shopService.getShopInfoIdAndName();
        infoShops.forEach((key, value) -> stringBuilder.append("Номер -" + key + " . Название: " + value + "\n"));
        stringBuilder.append("Колличество магазинов " + shopService.getAll().size());
        stringBuilder.append("\n Выберите : №  магазина");
        stringBuilder.append("\n0. Вернуться в меню магазинов ");
        return stringBuilder;
    }

    private void showShops(int numberShop) {
        Scanner in = new Scanner(System.in);
        for (; ; ) {
            System.out.println(infoShop(numberShop).toString());
            switch (in.nextInt()) {
                case (1):
                    productShop(numberShop);
                    break;
                case (3):
                    editShop(numberShop);
                    break;
                case (4):
                    delShop(numberShop);
                    break;
                case (0):
                    showListShops();
                    break;
                case (2):
                    addProductShop(numberShop);
                    break;
                default:
                    this.infoShop(numberShop);
                    break;
            }

        }
    }

    private Set<Category> addCategory() {
        Scanner in = new Scanner(System.in);
        Set<Category> categories = new HashSet<>();
        Category category = new Category();
        System.out.println("Введите категорию товара");
        category.setId(sequenceId);
        sequenceId++;
        category.setName(in.nextLine());
        categories.add(category);
        System.out.println("Добавить еще категорию к товару?y/n");
        String categoryProduct = in.nextLine();
        if (categoryProduct.equals("y") || categoryProduct.equals("Y")) {
            Category category2 = new Category();
            System.out.println("Введите категорию товара");
            category2.setId(sequenceId);
            sequenceId++;
            category2.setName(in.nextLine());
            categories.add(category2);
        }
        return categories;
    }

    private void addProductShop(int numberShop) {
        Scanner in = new Scanner(System.in);
        System.out.println("Добавление товара");
        Product product = new Product();
        product.setId(sequenceId);
        sequenceId++;
        System.out.println("укажите имя товара");
        product.setName(in.nextLine());
        System.out.println("укажите колличество");
        product.setCount(in.nextInt());
        System.out.println("укажите цену:");
        product.setPrice(in.nextInt());

        product.setCategorySet(addCategory());
        shopService.addProduct(numberShop, product);
        showShops(numberShop);
    }

    private StringBuilder infoShop(int numberShop) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Магазин " + "№" + numberShop);
        stringBuilder.append("\n1.Просмотр товаров для заказа или изменения товара");
        stringBuilder.append("\n2.Добавить товар");
        stringBuilder.append("\n3.Изменение регистрационных данных");
        stringBuilder.append("\n4.Удаление магазина");
        stringBuilder.append("\n0.К просмотру списка магазинов");
        return stringBuilder;
    }

    private void productShop(int numberShop) {
        Scanner in = new Scanner(System.in);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("1: поиск товаров по одному или нескольким атрибутам");
        stringBuilder.append("2: поиск товаров c сортировкой по цене");
        stringBuilder.append("3: просмотр товаров в заданных категориях");
        stringBuilder.append("0: Вернуться к меню магазина");
        System.out.println(stringBuilder.toString());
        typeSort = in.nextInt();
        switch (typeSort) {
            case (1):
                searchForProductsByOneOrMoreAttributes(numberShop);
                break;
            case (2):
//todo                searchForProductsSortedByPrice(numberShop);
                break;
            case (3):
//todo                viewingProductsInSpecifiedCategories(numberShop);
                break;
            case (0):
                showShops(numberShop);
                break;
            default:
                productShop(numberShop);
                break;
        }
    }

    private void searchForProductsByOneOrMoreAttributes(int numberShop) {
        StringBuilder stringBuilder = new StringBuilder();
        Scanner in = new Scanner(System.in);
        System.out.println("укажите имя товара");
        String nameProduct = in.nextLine();
        System.out.println("Укажите цену товара");
        int priceProduct = in.nextInt();
        List<Product> productList = shopService.getAllProductShopFindByPriceAndName(numberShop, priceProduct, nameProduct);
        if (productList != null) {
            productList.forEach(product -> stringBuilder.append("Нормер товара "
                    + product.getId() + ", название: " + product.getName() + ", цена: " + product.getPrice() +
                    ", Колличество: " + product.getCount()));
            stringBuilder.append("\n выбирите товар ");
            System.out.println(stringBuilder.toString());
            numberProduct = in.nextInt();
            Map<Integer, Product> productMap = productList.stream().collect(Collectors.toMap(Product::getId, product -> product));
            if (productMap.get(numberProduct) == null) {
                searchForProductsByOneOrMoreAttributes(numberShop);
            }
            productBuyEditDel(numberShop, numberProduct);
        }
        System.out.println("не удалось найти товар");
        showShops(numberShop);
    }

    private StringBuilder productBuyEditDelInfo() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("1.Заказать выбраный продукт");
        stringBuilder.append("\n2.Изменить");
        stringBuilder.append("\n3.Удалить");
        stringBuilder.append("\n0.К просмотру списка продуктов выбраного магазина");
        return stringBuilder;
    }

    private void productBuyEditDel(int numberShop, int numberProduct) {
        Scanner in = new Scanner(System.in);
        for (; ; ) {
            System.out.println(productBuyEditDelInfo().toString());
            switch (in.nextInt()) {
                case (1):
//todo                    Заказать выбраный продукт
                    break;
                case (2):
//todo                    Изменить
                    break;
                case (3):
                    delProductFormShop(numberShop, numberProduct);
                    break;
                case (0):
                    productBuyEditDelInfo();
                    break;
                default:
                    this.infoShop(numberShop);
                    break;
            }

        }
    }

    private void editShopProduct(int numberShop, int numberProduct) {
//todo
    }

    private void delProductFormShop(int numberShop, int numberProduct) {
        shopService.delProduct(numberProduct, numberShop);
        System.out.println(" продукт успешно удалён");
        productShop(numberShop);
    }
//        StringBuilder stringBuilder = new StringBuilder();
//        Map<Integer, String> infoShops = shopService.getAllShopProducts(numberShop);
//        infoShops.forEach((key, value) -> stringBuilder.append("Номер -" + key + " . Название: " + value + "\n"));
//        stringBuilder.append("Колличество магазинов " + shopService.getAll().size());
//        stringBuilder.append("\n Выберите : №  магазина");
//        stringBuilder.append("\n0. Вернуться в меню магазинов ");
//        return stringBuilder;

    private void editShop(int numberShop) {
        Scanner in = new Scanner(System.in);
        Shop newShop = new Shop();
        newShop.setId(numberShop);
        Shop oldShop = shopService.getShopById(numberShop);
        System.out.println("Магазин: " + oldShop.getName() + " Введите новое название магазина :");
        newShop.setName(in.nextLine());
        System.out.println("Адресс: " + oldShop.getAddress() + " Введите новый адрес магазина:");
        newShop.setAddress(in.nextLine());
        shopService.update(newShop);
        showListShops();
    }

    private void delShop(int numberShop) {
        shopService.deleteEntity(shopService.getShopById(numberShop));
        System.out.println("Магазин : №" + numberShop + "удален");
        showListShops();
    }
}
