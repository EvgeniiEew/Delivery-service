package by.home.serviseDelivery.domain;

import java.util.Date;
import java.util.List;

public class Order {
    private Integer id;
    private int priceOrder;
    private String nameShop;
    private String address;
    private Date dateOrder ;
    private StatusOrder statusOrder;
    private List<Product> productList;

    public Order(Integer id, int priceOrder, String nameShop, String address, Date dateOrder, StatusOrder statusOrder, List<Product> productList) {
        this.id = id;
        this.priceOrder = priceOrder;
        this.nameShop = nameShop;
        this.address = address;
        this.dateOrder = dateOrder;
        this.statusOrder = statusOrder;
        this.productList = productList;
    }

    public Date getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(Date dateOrder) {
        this.dateOrder = dateOrder;
    }

    public Order() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getPriceOrder() {
        return priceOrder;
    }

    public void setPriceOrder(int priceOrder) {
        this.priceOrder = priceOrder;
    }

    public String getNameShop() {
        return nameShop;
    }

    public void setNameShop(String nameShop) {
        this.nameShop = nameShop;
    }

    public StatusOrder getStatusOrder() {
        return statusOrder;
    }

    public void setStatusOrder(StatusOrder statusOrder) {
        this.statusOrder = statusOrder;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
