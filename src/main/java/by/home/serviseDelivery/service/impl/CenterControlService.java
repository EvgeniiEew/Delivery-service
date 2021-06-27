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
        stringBuilder.append("Main menu:\n");
        stringBuilder.append("\n1.Shops");
        stringBuilder.append("\n2.Clients");
        stringBuilder.append("\n3.User orders");
        stringBuilder.append("\n0.exit\n");
        return stringBuilder;
    }

    public void showMainMenu() {
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println(this.getViewMeu().toString());
            try {
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
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Error!");
            }
        }
    }

    private void showProductUser() {
        Scanner in = new Scanner(System.in);
        System.out.println("Select a user to view orders:");
        Map<Integer, Client> clients = clientService.getAll();
        if (clients == null || clients.size() == 0) {
            return;
        }
        for (Map.Entry<Integer, Client> clientMap : clients.entrySet()) {
            System.out.println(
                    clientMap.getKey() + ". User name: \n" + clientMap.getValue().getfName()
            );
        }
        int num = in.nextInt();
        if (num == 0) {
            return;
        }
        if (clients.get(num) == null) {
            return;
        }
        Map<Integer, Order> orderMap = clientService.getOrderList(num);
        if (orderMap != null && orderMap.size() != 0) {
            for (Map.Entry<Integer, Order> map : orderMap.entrySet()) {
                System.out.println(" Order N: " + map.getKey() + " , price: " + map.getValue().getPriceOrder() + " ,name of shop: " + map.getValue().getNameShop() + " , Delivery address: " + map.getValue().getAddress() + ",  Products: " + "");
                if (map.getValue().getProductList() == null) {
                    System.out.println("no products");
                } else {
                    for (Product products : map.getValue().getProductList()) {
                        System.out.println(products.getId() + ". Name: " + products.getName() + ", Price :" + products.getCount() + "\n");
                        getViewMeu();
                    }
                }
            }
        } else {
            System.out.println(" could not find orders");
        }
    }

    private StringBuilder getViewClients() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n1.Default user selection. View users / edit and delete");
        stringBuilder.append("\n2.Registration of clients");
        stringBuilder.append("\n0.To the main menu \n");
        return stringBuilder;
    }

    private void listUser() {
        Scanner in = new Scanner(System.in);
        Map<Integer, Client> clients = clientService.getAll();
        if (clients != null && clients.size() != 0) {
            for (Map.Entry<Integer, Client> clientMap : clients.entrySet()) {
                System.out.println(" User number " + clientMap.getKey() + ", name: " + clientMap.getValue().getfName() + ", Surname: " + clientMap.getValue().getlName() + ", Address: " + clientMap.getValue().getAddress() + " \n");
            }
            System.out.println("Select the default user, to fulfill orders from the store or to change. ");
            System.out.println("0.Return");
            clientNumber = in.nextInt();
            if (clientNumber == 0) {
                showClients();
            }
            if (clients.get(clientNumber) == null) {
                listUser();
            }
            editDelUser(clientNumber);
        }
        System.out.println("No user found");
        showClients();
    }

    private void editDelUser(int clientNumber) {
        Scanner in = new Scanner(System.in);
        System.out.println("1. Change" + " 2. Delete" + " 3. Select and go to store selection");
        System.out.println("0.Come back");
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
            System.out.println("default user is selected");
        }
    }

    private void editUser(int clientNumber) {
        Client newClient = readClientData(clientNumber);
        clientService.update(newClient);
    }

    private Client readClientData(int clientNumber) {
        Scanner in = new Scanner(System.in);
        Client client = new Client();
        client.setId(clientNumber);
        System.out.println("Enter a name:");
        client.setfName(in.nextLine());
        System.out.println("Enter the last name:");
        client.setlName(in.nextLine());
        System.out.println("Enter the address of residence :");
        client.setAddress(in.nextLine());
        return client;
    }

    private void delUser(int clientNumber) {
        clientService.deleteEntity(clientService.getAll().get(clientNumber));
        System.out.println("User removed");
    }

    private void registerUser() {
        Client newClient = readClientData(sequenceId);
        sequenceId++;
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
                    break;
            }
        }
    }

    private StringBuilder getShopView() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n1.Choose a store for: ordering, viewing, changing and deleting products");
        stringBuilder.append("\n2.Shop registration");
        stringBuilder.append("\n0.To the main menu \n");
        return stringBuilder;
    }

    private void showMenuShop() {
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println(getShopView().toString());
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
                    break;
            }
        }
    }

    private void shopRegister() {
        Scanner in = new Scanner(System.in);
        Shop newShop = new Shop();
        newShop.setId(sequenceId);
        sequenceId++;
        System.out.println("\nEnter store name:");
        newShop.setName(in.nextLine());
        System.out.println("Enter store address:");
        newShop.setAddress(in.nextLine());
        shopService.save(newShop);
        showListShops();
    }

    private void showListShops() {
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println(getShopsFromSql().toString());
            numberShop = in.nextInt();
            if (numberShop == 0) {
                showMenuShop();
                return;
            }
            if (shopService.getShopInfoIdAndName().get(numberShop) != null && numberShop != 404) {
                showShops(numberShop);
            }
        }
    }

    private StringBuilder getShopsFromSql() {
        StringBuilder stringBuilder = new StringBuilder();
        Map<Integer, String> infoShops = shopService.getShopInfoIdAndName();
        infoShops.forEach((key, value) -> stringBuilder.append(key).append(". Shop Title: ").append(value).append("\n"));
        stringBuilder.append("Number of stores ").append(shopService.getAll().size());
        stringBuilder.append("\n Select shop by number:");
        stringBuilder.append("\n0. Return to the shop menu \n");
        return stringBuilder;
    }

    private void showShops(int numberShop) {
        Scanner in = new Scanner(System.in);
        while (true) {
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
                    break;
            }
        }
    }

    private Set<Category> addCategory() {
        Scanner in = new Scanner(System.in);
        Set<Category> categories = new HashSet<>();
        Category category = new Category();
        System.out.println("Enter product category");
        category.setId(sequenceId);
        sequenceId++;
        category.setName(in.nextLine());
        categories.add(category);
        System.out.println("Add another category to the product? y/n");
        String categoryProduct = in.nextLine();
        if (categoryProduct.equals("y") || categoryProduct.equals("Y")) {
            Category category2 = new Category();
            System.out.println("Enter product category");
            category2.setId(sequenceId);
            sequenceId++;
            category2.setName(in.nextLine());
            categories.add(category2);
        }
        return categories;
    }

    private void addProductShop(int numberShop) {
        Scanner in = new Scanner(System.in);
        System.out.println("Adding a product");
        Product product = new Product();
        product.setId(sequenceId);
        sequenceId++;
        System.out.println("enter the name of the product:");
        product.setName(in.nextLine());
        System.out.println("indicate the quantity:");
        product.setCount(in.nextInt());
        System.out.println("indicate the price: (numerical value)");
        product.setPrice(in.nextInt());
        product.setCategorySet(addCategory());
        shopService.addProduct(numberShop, product);
        showShops(numberShop);
    }

    private StringBuilder infoShop(int numberShop) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Shop " + "№").append(numberShop);
        stringBuilder.append("\n1.View items to order or change items");
        stringBuilder.append("\n2.Add product");
        stringBuilder.append("\n3.Change of registration data");
        stringBuilder.append("\n4.Deleting a shop");
        stringBuilder.append("\n0.Back to the list of stores\n");
        return stringBuilder;
    }

    private void productShop(int numberShop) {
        Scanner in = new Scanner(System.in);
        String message = "1: search for products by one or more attributes \n" +
                " 2: show all products sorted by price \n" +
                " 3: viewing products in specified categories \n" +
                " 0: Back to shop menu \n";
        System.out.println(message);
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
                break;
        }
    }

    private void viewingProductsInSpecifiedCategories(int numberShop) {
        StringBuilder stringBuilder = new StringBuilder();
        Scanner in = new Scanner(System.in);
        System.out.println("specify the product category: ");
        String nameCategory = in.nextLine();
        List<Product> productList = shopService.getAllProductSortedByCategory(numberShop, nameCategory);
        if (productList != null && productList.size() != 0) {
            for (Product product : productList) {
                System.out.println(" Number product " + product.getId() + ", Product name: " + product.getName() + ", Price: " + product.getPrice() + ", Number of products: " + product.getCount() + ", Product categories: ");
                for (Category category : product.getCategorySet()) {
                    System.out.println("" + category.getName());
                }
            }
            stringBuilder.append("\n select a product ");
            stringBuilder.append("\n 0.Come back\n");
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
        System.out.println("could not find product\n");
        showShops(numberShop);
    }

    private void searchForProductsByOneOrMoreAttributes(int numberShop) {
        StringBuilder stringBuilder = new StringBuilder();
        Scanner in = new Scanner(System.in);
        System.out.println("\nEnter the name of the product:");
        String nameProduct = in.nextLine();
        System.out.println("Indicate the price of the item:");
        int priceProduct = in.nextInt();
        List<Product> productList = shopService.getAllProductShopFindByPriceAndName(numberShop, priceProduct, nameProduct);
        if (productList != null && productList.size() != 0) {
            Map<Integer, Product> productsMap = getProductsMap(numberShop, stringBuilder, in, productList);
            if (productsMap.get(numberProduct) == null) {
                searchForProductsByOneOrMoreAttributes(numberShop);
            }
            productBuyEditDel(numberShop, numberProduct);
        }
        System.out.println("could not find product");
        showShops(numberShop);
    }

    private void searchForProductsSortedByPrice(int numberShop) {
        StringBuilder stringBuilder = new StringBuilder();
        Scanner in = new Scanner(System.in);
        List<Product> productList = shopService.getAllProductSortedByPrice(numberShop);
        if (productList != null && productList.size() != 0) {
            Map<Integer, Product> productsMap = getProductsMap(numberShop, stringBuilder, in, productList);
            if (productsMap.get(numberProduct) == null) {
                System.out.println("could not find product");
                return;
            }
            productBuyEditDel(numberShop, numberProduct);
        }
        showShops(numberShop);
    }

    private Map<Integer, Product> getProductsMap(int numberShop, StringBuilder stringBuilder, Scanner in, List<Product> productList) {
        for (Product products : productList) {
            System.out.println(" Number product: " + products.getId() + ", Product name: " + products.getName() + ", Price: " + products.getPrice() + ", Number of products: " + products.getCount() + ", Product categories: ");
            for (Category category : products.getCategorySet()) {
                System.out.println("" + category.getName());
            }
        }
        stringBuilder.append("\n select a product ");
        stringBuilder.append("\n 0.Come back \n");
        System.out.println(stringBuilder.toString());
        numberProduct = in.nextInt();
        if (numberProduct == 0) {
            productShop(numberShop);
        }
        return productList.stream().collect(Collectors.toMap(Product::getId, product -> product));
    }

    private StringBuilder productBuyEditDelInfo() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("1.Order the selected product");
        stringBuilder.append("\n2.Change");
        stringBuilder.append("\n3.Delete");
        stringBuilder.append("\n0.Come back view the list of products of the selected store");
        return stringBuilder;
    }

    private void productBuyEditDel(int numberShop, int numberProduct) {
        Scanner in = new Scanner(System.in);
        while (true) {
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
                    break;
            }
        }
    }

    public void checkProductAvailability(Product product, Shop shop) {
        Scanner in = new Scanner(System.in);
        Order order = new Order();
        List<Product> productList1 = new ArrayList<>();
        productList1.add(product);
        order.setId(sequenceId);
        sequenceId++;
        order.setPriceOrder(product.getCount());
        order.setNameShop(shop.getName());
        System.out.println("Enter delivery address");
        order.setAddress(in.nextLine());
        order.setDateOrder(new Date());
        order.setStatusOrder(StatusOrder.PENDING);
        order.setProductList(productList1);
        shopService.addOrder(numberShop, order);
        clientService.addOrder(order, clientNumber);
    }

    private void byProduct(int numberShop, int numberProduct) {
        Shop shop = shopService.getShopById(numberShop);
        List<Product> productList = shop.getProductList();
        Map<Integer, Product> productMap = productList.stream().collect(Collectors.toMap(Product::getId, product1 -> product1));
        Product product = productMap.get(numberProduct);
        if (clientNumber == 0) {
            System.out.println("User number is not selected by default, select user and return to shops:");
            listUser();
        }
        if (product.getCount() > 0) {
            checkProductAvailability(product, shop);
            product.setCount(product.getCount() - 1);
            shopService.editProduct(numberShop, product);
            System.out.println("order is accepted\n");
        } else {
            System.out.println("no product");
        }
        productShop(numberShop);
    }

    private void editShopProduct(int numberShop, int numberProduct) {
        Scanner in = new Scanner(System.in);
        Product product = new Product();
        System.out.println("Enter the new item quantity:");
        product.setId(numberProduct);
        product.setCount(in.nextInt());
        System.out.println("Enter a new price:");
        product.setPrice(in.nextInt());
        System.out.println("Product new name");
        product.setName(in.nextLine());
        shopService.editProduct(numberShop, product);
        productBuyEditDel(numberShop, numberProduct);
    }

    private void delProductFormShop(int numberShop, int numberProduct) {
        shopService.delProduct(numberProduct, numberShop);
        System.out.println(" product" + numberProduct + " removed successfully");
        productShop(numberShop);
    }

    private void editShop(int numberShop) {
        Scanner in = new Scanner(System.in);
        Shop newShop = new Shop();
        newShop.setId(numberShop);
        Shop oldShop = shopService.getShopById(numberShop);
        System.out.println("Shop: " + oldShop.getName() + " Enter a new store name :");
        newShop.setName(in.nextLine());
        System.out.println("Address: " + oldShop.getAddress() + " Enter a new store address:");
        newShop.setAddress(in.nextLine());
        shopService.update(newShop);
        showListShops();
    }

    private void delShop(int numberShop) {
        shopService.deleteEntity(shopService.getShopById(numberShop));
        System.out.println("Shop : №" + numberShop + "deleted\n");
        showListShops();
    }
}
