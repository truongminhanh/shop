package com.example.shoppingCartDemo.Entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.*;

public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="shoppingCartid",updatable = false,nullable = false)
    private Integer shoppingCartID;

    private String shop_name;
    private List<Items> items;
    private List<Customer> customers;
    private HashMap<Date,Customer>invoice;

    public ShoppingCart(String shop_name){
        this.shop_name=shop_name;
        this.customers=new ArrayList<>();
        this.items=new ArrayList<>();
        this.invoice=new HashMap<>();
    }

    public boolean add_item(Items item){
        int pos=check_item(item.getName());
        if(pos<0){
            this.items.add(item);
            return true;
        }else{
            System.out.println("Item =>"+item.getName()+"already exist");
        }
        return false;
    }

    public int check_item(String item){
        int i=0;
        for(Items item1:this.items){
            if(item1.getName().trim().toLowerCase().equals(item.trim().toLowerCase())){
                return i;
            }
            i++;
        }
        return -1;
    }

    public boolean add_customer(Customer customer){
        int pos=check_cust(customer.getName());
        if(pos<0){
            this.customers.add(customer);
            return true;
        }else{
            System.out.println("Customer"+customer.getName()+"already exist");
        }
        return false;
    }

    public int check_cust(String customer){
        int i=0;
        for(Customer cust:this.customers){
            if(cust.getName().toLowerCase().equals(customer.toLowerCase())){
                return i;
            }
            i++;
        }
        return -1;
    }

    public boolean add_prod_basket(String customer,String item,int quantity){
        int checkcust=check_cust(customer);
        int itemcheck=check_item(item);
        if(checkcust>=0 && itemcheck>=0){
            Customer cust=this.customers.get(checkcust);
            Items item_add= this.items.get(itemcheck);
            HashMap<Items,Integer> basket=cust.getBasket().getBasket();
            if(basket.containsKey(item_add)){
                basket.put(item_add,quantity+basket.get(item_add));
                item_add.reduce_stock(quantity);
            }else{
                basket.put(item_add,quantity);
                item_add.reduce_stock(quantity);
            }
            return true;
        }else{
            return false;
        }
    }

    public boolean remove_prod_basket(String customer,String item){
        int checkcust=check_cust(customer);
        int itemcheck=check_item(item);
        if(checkcust>=0 && itemcheck>=0){
            Customer cust = this.customers.get(checkcust);
            Items item_add=this.items.get(itemcheck);
            HashMap<Items,Integer> basket = cust.getBasket().getBasket();
            if(basket.containsKey(item_add)){
                basket.remove(item_add);
            }
            return true;
        }else {
            return false;
        }
    }

    public void print_bill(String customer) {

        int pos = check_cust(customer);
        Double price = 0.0;
        if(pos >= 0){
            Customer find = this.customers.get(pos);
            System.out.println("******Billing******");
            System.out.println("Customer Name:" +find.getName()+"|t,Customer contact :"+find.getContact());
            for(Map.Entry<Items,Integer> e:find.getBasket().getBasket().entrySet()){
                System.out.println("Item => "+e.getKey().getName()+",Price =>"+e.getKey().getPrice()+",Quantity =>"+e.getValue());
            }
            if(price!=0){
                find.setBill_amount(price);
                System.out.println("Total Amount to pay:" +find.getBill_amount());
                invoice.put(new Date(),find);
            }
        }else{
            System.out.println("Customer doesnt exist!");
        }
    }

    //public void print_invoice(){}


    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public List<Items> getItems() {
        return items;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public HashMap<Date, Customer> getInvoice() {
        return invoice;
    }

    public void setInvoice(HashMap<Date, Customer> invoice) {
        this.invoice = invoice;
    }
}
