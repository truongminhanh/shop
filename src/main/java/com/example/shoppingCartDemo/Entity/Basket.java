package com.example.shoppingCartDemo.Entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

public class Basket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="basket_id",updatable = false,nullable = false)
    private Integer basketiD;

    private HashMap<Items,Integer> basket;

    public Basket(){
        this.basket=new HashMap<>();
    }

    public HashMap<Items,Integer> getBasket(){
        return basket;
    }

   // public int search_item(String item){
   //     for(Items exist:this.items){
   //         if(exist.getName().toLowerCase().equals(item.toLowerCase())){
   //             return 0;
   //         }
   //         return -1;
   //     }
   // }

    /*
    public boolean add_item(Items item){
        int pos = search_item(item.getName());
        if(pos>=0){
            items item1=this.items.get(pos);
        }
    }





    public List<Items> getItems(){
        return items;
    }
    */



}
