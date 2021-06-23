package by.home.serviseDelivery.domain;

import java.util.Set;

public class Product {
    private Integer id;
    private int count;
    private int price;
    private String name;
    private Set<Category> categorySet;

    public Product(Integer id, int count, int price, String name, Set<Category> categorySet) {
        this.id = id;
        this.count = count;
        this.price = price;
        this.name = name;
        this.categorySet = categorySet;
    }

    public Product() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Category> getCategorySet() {
        return categorySet;
    }

    public void setCategorySet(Set<Category> categorySet) {
        this.categorySet = categorySet;
    }
}
