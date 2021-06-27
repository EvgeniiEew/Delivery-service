package by.home.serviseDelivery.service.impl;

import by.home.serviseDelivery.domain.*;
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
    private int clientNumber;

    public CenterControlService(FileService fileService) {
        this.shopService = new ShopService(fileService);
        this.clientService = new ClientService(fileService);
    }

    private StringBuilder getViewMeu() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Главное меню:\n");
        stringBuilder.append("\n1.Магазины");
        stringBuilder.append("\n2.Клиенты");
        stringBuilder.append("\n3.Заказы пользователей");
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
                    showClients();
                    break;
                case (3):
                    showProductUser();
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

    private void showProductUser() {
        Scanner in = new Scanner(System.in);
        System.out.println("Выбирите юзера для просмотра заказов:");
        Map<Integer, Client> clients = clientService.getAll();
        if (clients != null && clients.size() != 0) {
            for (Map.Entry<Integer, Client> clientMap : clients.entrySet()) {
                System.out.println(" Номер юзера "
                        + clientMap.getKey()
                        + ", имя: "
                        + clientMap.getValue().getfName());
            }
            int num = in.nextInt();
            if (num == 0) {
                getViewMeu();
            }
            if (clients.get(num) == null) {
                showProductUser();
            }
            Map<Integer, Order> orderMap = clientService.getOrderList(num);
            if (orderMap != null) {
                for (Map.Entry<Integer, Order> map : orderMap.entrySet()) {
                    System.out.println(" Заказ N: "
                            + map.getKey()
                            + " , цена: " + map.getValue().getPriceOrder() + " , название магазина: "
                            + map.getValue().getNameShop() + " , Адрес доставки: " +
                            map.getValue().getAddress() + ",  Продукты: " + "");
                    if (map.getValue().getProductList() == null) {
                        System.out.println("нет товаров");
                    } else {
                        for (Product products : map.getValue().getProductList()) {
                            System.out.println("Нормер: " + products.getId() + " цена :" + products.getCount() + " Название: " + products.getName());
                            getViewMeu();
                        }
                    }
                }
            } else {
                System.out.println(" не удалось найти заказы");
            }
        }
        getViewMeu();
    }

    private StringBuilder getViewClients() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n1.Выбор пользователя по умолчанию. Просмотр пользователей/ редактирование и удаление");
        stringBuilder.append("\n2.Регистрация клиентов");
        stringBuilder.append("\n0.В главное меню");
        return stringBuilder;
    }

    private void listUser() {
        Scanner in = new Scanner(System.in);
        Map<Integer, Client> clients = clientService.getAll();
        if (clients != null && clients.size() != 0) {
            for (Map.Entry<Integer, Client> clientMap : clients.entrySet()) {
                System.out.println(" Номер юзера "
                        + clientMap.getKey()
                        + ", имя: "
                        + clientMap.getValue().getfName() +
                        ", Фамилия: "
                        + clientMap.getValue().getlName()
                        + ", Адресс: "
                        + clientMap.getValue().getAddress()
                        + " \n");
            }
            System.out.println("Выберите юзера по умолчанию, для выполнения заказов из магазина, или для изменения. ");
            System.out.println("0.Вернуться");
            clientNumber = in.nextInt();
            if (clientNumber == 0) {
                showClients();
            }
            if (clients.get(clientNumber) == null) {
                listUser();
            }
            editDelUser(clientNumber);
        }
        System.out.println("не удалось найти Юзера");
        showClients();
    }

    public void editDelUser(int clientNumber) {
        Scanner in = new Scanner(System.in);
        System.out.println("1. изменить" + " 2. Удалить" + " 3. Выбрать и перейти к выбору магазина");
        System.out.println("0.Вернуться назад");
        int num = in.nextInt();
        if (num == 1) {
            editUser(clientNumber);
        }
        if (num == 2) {
            delUser(clientNumber);
        }
        if (num == 0) {
            listUser();
        }
        if (num == 3) {
            this.clientNumber = clientNumber;
            showMenuShop();
            System.out.println("юзер по умолчанию выбран");
        }
    }

    private void editUser(int clientNumber) {
        Scanner in = new Scanner(System.in);
        Client newClient = new Client();
        newClient.setId(clientNumber);
        System.out.println("Введите имя:");
        newClient.setfName(in.nextLine());
        System.out.println("Введите Фамилию:");
        newClient.setlName(in.nextLine());
        System.out.println("Введите адрес проживания :");
        newClient.setAddress(in.nextLine());
        clientService.update(newClient);
    }

    private void delUser(int clientNumber) {
        clientService.deleteEntity(clientService.getAll().get(clientNumber));
        System.out.println("Юзер удален");
    }

    private void registerUser() {
        Scanner in = new Scanner(System.in);
        Client newClient = new Client();
        newClient.setId(sequenceId);
        sequenceId++;
        System.out.println("Введите имя:");
        newClient.setfName(in.nextLine());
        System.out.println("Введите Фамилию:");
        newClient.setlName(in.nextLine());
        System.out.println("Введите адрес проживания :");
        newClient.setAddress(in.nextLine());
        clientService.save(newClient);
        showClients();
    }

    private void showClients() {
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println(this.getViewClients().toString());
            switch (in.nextInt()) {
                case (1):
                    listUser();
                    break;
                case (2):
                    registerUser();
                    break;
                case (0):
                    showMainMenu();
                    break;
                default:
                    this.getViewClients();
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
        stringBuilder.append("Колличество магазинов ").append(shopService.getAll().size());
        stringBuilder.append("\n Выберите : N  магазина");
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
        stringBuilder.append("Магазин " + "N").append(numberShop);
        stringBuilder.append("\n1.Просмотр товаров для заказа или изменения товара");
        stringBuilder.append("\n2.Добавить товар");
        stringBuilder.append("\n3.изменение регистрационных данных");
        stringBuilder.append("\n4.Удаление магазина");
        stringBuilder.append("\n0.К просмотру списка магазинов");
        return stringBuilder;
    }

    private void productShop(int numberShop) {
        Scanner in = new Scanner(System.in);
        String stringBuilder = "1: поиск товаров по одному или нескольким атрибутам " +
                " 2: поиск товаров c сортировкой по цене " +
                " 3: просмотр товаров в заданных категориях " +
                " 0: Вернуться к меню магазина ";
        System.out.println(stringBuilder);
        typeSort = in.nextInt();
        switch (typeSort) {
            case (1):
                searchForProductsByOneOrMoreAttributes(numberShop);
                break;
            case (2):
                searchForProductsSortedByPrice(numberShop);
                break;
            case (3):
                viewingProductsInSpecifiedCategories(numberShop);
                break;
            case (0):
                showShops(numberShop);
                break;
            default:
                productShop(numberShop);
                break;
        }
    }

    private void viewingProductsInSpecifiedCategories(int numberShop) {
        StringBuilder stringBuilder = new StringBuilder();
        Scanner in = new Scanner(System.in);
        System.out.println("укажите категорию товара: ");
        String nameCategory = in.nextLine();
        List<Product> productList = shopService.getAllProductSortedByCategory(numberShop, nameCategory);
        if (productList != null && productList.size() != 0) {
            for (Product product : productList) {
                System.out.println(" Номер товара " + product.getId() + ", Название: " + product.getName() + ", Цена: " + product.getPrice() + ", Колличество: " + product.getCount() + ", Категории товара: ");
                for (Category category : product.getCategorySet()) {
                    System.out.println("" + category.getName());
                }
            }
            stringBuilder.append("\n выберите товар ");
            stringBuilder.append("\n 0.Вернуться назад ");
            System.out.println(stringBuilder.toString());
            numberProduct = in.nextInt();
            if (numberProduct == 0) {
                productShop(numberShop);
            }
            Map<Integer, Product> productMap = productList.stream().collect(Collectors.toMap(Product::getId, product -> product));
            if (productMap.get(numberProduct) == null) {
                viewingProductsInSpecifiedCategories(numberShop);
            }
            productBuyEditDel(numberShop, numberProduct);
        }
        System.out.println("не удалось найти товар");
        showShops(numberShop);
    }

    private void searchForProductsByOneOrMoreAttributes(int numberShop) {
        StringBuilder stringBuilder = new StringBuilder();
        Scanner in = new Scanner(System.in);
        System.out.println("укажите имя товара");
        String nameProduct = in.nextLine();
        System.out.println("Укажите цену товара");
        int priceProduct = in.nextInt();
        List<Product> productList = shopService.getAllProductShopFindByPriceAndName(numberShop, priceProduct, nameProduct);
        if (productList != null && productList.size() != 0) {
            for (Product product : productList) {
                System.out.println(" Номер товара: " + product.getId() + ", Название: " + product.getName() + ", Цена: " + product.getPrice() + ", Колличество: " + product.getCount() + ", Категории товара: ");
                for (Category category : product.getCategorySet()) {
                    System.out.println("" + category.getName());
                }
            }
            stringBuilder.append("\n выбирите товар ");
            stringBuilder.append("\n 0.Вернуться назад ");
            System.out.println(stringBuilder.toString());
            numberProduct = in.nextInt();
            if (numberProduct == 0) {
                productShop(numberShop);
            }
            Map<Integer, Product> productMap = productList.stream().collect(Collectors.toMap(Product::getId, product -> product));
            if (productMap.get(numberProduct) == null) {
                searchForProductsByOneOrMoreAttributes(numberShop);
            }
            productBuyEditDel(numberShop, numberProduct);
        }
        System.out.println("не удалось найти товар");
        showShops(numberShop);
    }

    private void searchForProductsSortedByPrice(int numberShop) {
        StringBuilder stringBuilder = new StringBuilder();
        Scanner in = new Scanner(System.in);
        List<Product> productList = shopService.getAllProductSortedByPrice(numberShop);
        if (productList != null && productList.size() != 0) {
            for (Product products : productList) {
                System.out.println(" Номер товара: " + products.getId() + ", Название: " + products.getName() + ", Цена: " + products.getPrice() + ", Колличество: " + products.getCount() + ", Категории товара: ");
                for (Category category : products.getCategorySet()) {
                    System.out.println("" + category.getName());
                }
            }
            stringBuilder.append("\n выберите товар ");
            stringBuilder.append("\n 0.Вернуться назад ");
            System.out.println(stringBuilder.toString());
            numberProduct = in.nextInt();
            if (numberProduct == 0) {
                productShop(numberShop);
            }
            Map<Integer, Product> productMap = productList.stream().collect(Collectors.toMap(Product::getId, product -> product));
            if (productMap.get(numberProduct) == null) {
                searchForProductsSortedByPrice(numberShop);
                System.out.println("не удалось найти товар");
            }
            productBuyEditDel(numberShop, numberProduct);
        }
        showShops(numberShop);
    }

    private StringBuilder productBuyEditDelInfo() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("1.Заказать выбраный продукт");
        stringBuilder.append("\n2.изменить");
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
                    byProduct(numberShop, numberProduct);
                    break;
                case (2):
                    editShopProduct(numberShop, numberProduct);
                    break;
                case (3):
                    delProductFormShop(numberShop, numberProduct);
                    break;
                case (0):
                    infoShop(numberShop);
                    break;
                default:
                    this.productBuyEditDel(numberShop, numberProduct);
                    break;
            }
        }
    }

    private void byProduct(int numberShop, int numberProduct) {
        Scanner in = new Scanner(System.in);
        Shop shop = shopService.getShopById(numberShop);
        List<Product> productList = shop.getProductList();
        Map<Integer, Product> productMap = productList.stream().collect(Collectors.toMap(Product::getId, product1 -> product1));
        Product product = productMap.get(numberProduct);
        List<Product> productList1 = new ArrayList<>();
        productList1.add(product);
        Order order = new Order();
        order.setId(sequenceId);
        sequenceId++;
        order.setPriceOrder(product.getCount());
        order.setNameShop(shop.getName());
        System.out.println("Укажите адресс доставки");
        order.setAddress(in.nextLine());
        order.setDateOrder(new Date());
        order.setStatusOrder(StatusOrder.PENDING);
        order.setProductList(productList1);
        shopService.addOrder(numberShop, order);
        if(clientNumber == 0){
            System.out.println("Номер юзера по умолчанию не выбран, выберите юзера и вернитесь к магазинам:");
            listUser();
        }
        clientService.addOrder(order, clientNumber);
        System.out.println("заказ принят");
        productShop(numberShop);
    }

    private void editShopProduct(int numberShop, int numberProduct) {
        Scanner in = new Scanner(System.in);
        Product product = new Product();
        System.out.println("Введите новое количество товара:");
        product.setId(numberProduct);
        product.setCount(in.nextInt());
        System.out.println("укажите новую цену:");
        product.setPrice(in.nextInt());
        System.out.println("Новое название товара");
        product.setName(in.nextLine());
        shopService.editProduct(numberShop, product);
        productBuyEditDel(numberShop, numberProduct);
    }

    private void delProductFormShop(int numberShop, int numberProduct) {
        shopService.delProduct(numberProduct, numberShop);
        System.out.println(" продукт успешно удалён");
        productShop(numberShop);
    }

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
        System.out.println("Магазин : N" + numberShop + "удален");
        showListShops();
    }
}
