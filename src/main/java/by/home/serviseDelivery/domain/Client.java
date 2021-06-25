package by.home.serviseDelivery.domain;

import java.util.List;

public class Client {
    private Integer id;
    private String lName;
    private String fName;
    private String address;
    private List<Order> orderList;

    public Client(Integer id, String lName, String fName, String address, List<Order> orderList) {
        this.id = id;
        this.lName = lName;
        this.fName = fName;
        this.address = address;
        this.orderList = orderList;
    }

    public Client() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
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
}
