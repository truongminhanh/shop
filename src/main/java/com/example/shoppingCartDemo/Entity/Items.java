package com.example.shoppingCartDemo.Entity;
import javax.persistence.*;

@Entity
public class Items {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="item_id",updatable = false,nullable = false)
    private Integer itemId;

    private String name;
    private Double price;
    private int stock;
    public Items(){}

    public Items(String name,Double price,int stock){
        this.name=name;
        this.price=price;
        this.stock=stock;
    }

    public void add_stock(int add){
        this.stock+=add;
    }

    public void reduce_stock(int add){
        this.stock-=add;
    }

    public String getName(){
        return name;
    }

    @Override
    public String toString() {
        return "Items{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
