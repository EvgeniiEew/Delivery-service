package by.home.serviseDelivery.service.impl;

import by.home.serviseDelivery.service.IClientService;
import by.home.serviseDelivery.service.IShopService;
import by.home.serviseDelivery.service.JsonFileService;

import java.util.Scanner;

public class CenterControlService {
    private final IShopService shopService;
    private final IClientService clientService;

    public CenterControlService(JsonFileService fileService) {
        this.shopService = new ShopService(fileService);
        this.clientService = new ClientService(fileService);
    }

    private StringBuilder getViewMeu() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Главное меню: \n");
        stringBuilder.append("\nМагазины: 1");
        stringBuilder.append("\nКлиенты: 2 ");
        stringBuilder.append("\nТовары: 3");
        stringBuilder.append("\nВыход: 0");
        return stringBuilder;
    }

    public void showMainMenu() {
        Scanner in = new Scanner(System.in);
        for (; ; ) {
            System.out.println(this.getViewMeu().toString());
            switch (in.nextInt()) {
                case (1):
                    showMenuShop();
                    break;
                case (2):
                    break;
                case (3):
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

    private StringBuilder getViewShop() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\nВыбор магазина для: заказа, просмотра, изменения и удаления товаров: 1");
        stringBuilder.append("\nРегистрация магазина: 2");
        stringBuilder.append("\nВ главное меню: 0");
        return stringBuilder;
    }

    private void showMenuShop() {
        Scanner in = new Scanner(System.in);
        for (; ; ) {
            System.out.println(this.getViewShop().toString());
            switch (in.nextInt()) {
                case (1):
                    showListShops();
                    break;
                case (2):
                    break;
                case (0):
                    showMainMenu();
                    break;
                default:
                    this.getViewShop();
                    break;
            }

        }
    }
    private StringBuilder getShopsFromSql() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Колличество магазинов " + shopService.getAll().size());
        stringBuilder.append("\nВыбрать: ");
        stringBuilder.append("\nВ меню магазинов  :0 ");
        return stringBuilder;
    }

    private StringBuilder infoShop() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Магазин " + "");
        stringBuilder.append("\n Просмотр товаров для заказа или изменения товара:1");
        stringBuilder.append("\n Изменение регистрационных данных:2  ");
        stringBuilder.append("\n Удаление магазина:3 ");
        stringBuilder.append("\n К просмотру списка магазинов: 0");
        return stringBuilder;
    }

    private void showListShops() {
        Scanner in = new Scanner(System.in);
        for (; ; ) {
            System.out.println(getShopsFromSql().toString());
            switch (in.nextInt()) {
                case ( ?):

                    break;
                case (0):
                    showMenuShop();
                    break;
                default:
                    this.getShopsFromSql();
                    break;
            }

        }
    }
    private void showShops() {
        Scanner in = new Scanner(System.in);
        for (; ; ) {
            System.out.println(infoShop().toString());
            switch (in.nextInt()) {
                case ( 1):
//                    Просмотр товаров для заказа или изменения товара:
                    break;
                case (2):
//                    Изменение регистрационных данных:2
                    break;
                case (3):
//                    Удаление магазина:3
                    break;
                case (0):
                    showListShops();
                    break;
                default:
                    this.infoShop();
                    break;
            }

        }
    }
}
