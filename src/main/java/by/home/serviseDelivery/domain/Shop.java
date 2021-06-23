package by.home.serviseDelivery.domain;

import java.util.List;

public class Shop {
    private Integer id;
    private String name;
    private String address;
    private List<Order> orderList;
    private List<Product> productList;

    public Shop(Integer id, String name, String address, List<Order> orderList, List<Product> productList) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.orderList = orderList;
        this.productList = productList;
    }

    public Shop() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public String toString() {
        return "Shop{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
