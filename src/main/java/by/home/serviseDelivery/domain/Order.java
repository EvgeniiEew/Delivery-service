package by.home.serviseDelivery.domain;

import java.util.Date;
import java.util.List;

public class Order {
    private int priceOrder;
    private String nameShop;
    private Date dateOrder;
    private StatusOrder statusOrder;
    List<Product> productList;
}
