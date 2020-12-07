package com.example.shoppingCartDemo.Controller;

import com.example.shoppingCartDemo.Entity.Basket;
import com.example.shoppingCartDemo.Entity.Customer;
import com.example.shoppingCartDemo.Entity.Items;
import com.example.shoppingCartDemo.Entity.ShoppingCart;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/shopping")
public class HomeController {

    ShoppingCart shoppingCart = new ShoppingCart("Beats Music Shop");

    @PostConstruct
    public void addcustomers(){
        shoppingCart.add_item(new Items("Juice",15.99,120));
        shoppingCart.add_item(new Items("Klangkuensteler",14.99,100));
        shoppingCart.add_item(new Items("Kobosil",14.99,60));

        shoppingCart.add_customer(new Customer("Minh","123456780",new Basket()));
        shoppingCart.add_customer(new Customer("Tom","0981234",new Basket()));
        shoppingCart.add_customer(new Customer("Lena","119315",new Basket()));
    }

    @GetMapping("/init")
    public String homepage(Model model){

        model.addAttribute("customers",shoppingCart.getCustomers());

        return "home";
    }

    @GetMapping("/shop")
    public String cust_items(@RequestParam("customer")String name,Model model){

        int pos=shoppingCart.check_cust(name);
        if(pos>=0){

            model.addAttribute("customer",name);
            //model.addAttribute("items",shoppingCart.getItems());
            Customer customer= shoppingCart.getCustomers().get(pos);
            model.addAttribute("items",customer.getBasket().getBasket().entrySet());
            return "cust_list";

        }
        return "redirect:init";
    }


    @GetMapping("/addcustomer")
    public String add_cust(Model model){
        model.addAttribute("customer",new Customer());
        return "cust_form";
    }




    @PostMapping("/savecust")
    public String save_cust(@ModelAttribute("customer")Customer customer){
        if(customer.getName()!=null && customer.getContact()!=null){
            int pos=shoppingCart.check_cust(customer.getName());
            Customer tosave =null;
            if(pos>=0){
                tosave=shoppingCart.getCustomers().get(pos);
                tosave.setName(customer.getName());
                tosave.setContact(customer.getContact());
                return "redirect:init";
            }else{
                tosave=customer;
                shoppingCart.add_customer(tosave);
            }
        }
        return "redirect:init";
    }




    @GetMapping("/additems")
    public String add_item(Model model){
        model.addAttribute("Item",new Items());
        return "item_form";
    }

    @GetMapping("/items")
    public String listitem(Model model){
        model.addAttribute("Items",shoppingCart.getItems());
        return "items";
    }

    @PostMapping("/saveitem")
    public String save_item(@ModelAttribute("Item")Items item){

            if ((item.getName() != null && item.getPrice() != 0) || (item.getStock() != 0)) {
                int pos = shoppingCart.check_item(item.getName());
                if (pos >= 0) {
                    Items items = shoppingCart.getItems().get(pos);
                    items.setName(item.getName());
                    items.setPrice(item.getPrice());
                    items.setStock(item.getStock());
                    return "redirect:items";
                } else {
                    shoppingCart.add_item(item);
                }
            }

        return "redirect:items";
    }

    @PostMapping("updateitem")
    public String update_item(@RequestParam("name")String [] name,@ModelAttribute("Item")Items item){
        if ((item.getName() != null && item.getPrice() != 0) && (item.getStock() != 0)) {
            int pos = shoppingCart.check_item(name[0]);
            if (pos >= 0) {
                Items items = shoppingCart.getItems().get(pos);
                items.setName(name[1]);
                items.setPrice(item.getPrice());
                items.setStock(item.getStock());
                return "redirect:items";
            } else {
                shoppingCart.add_item(item);
            }
        }
        return "redirect:items";
    }

    @GetMapping("/itemupd")
    public String update_item(@RequestParam("name")String name,Model model){
        if(name!=null){
            int pos = shoppingCart.check_item(name);
            if(pos>=0){
                Items item = shoppingCart.getItems().get(pos);
                model.addAttribute("Item",item);
                return "item_form_update";
            }
        }
        return "redirect:items";
    }

    @GetMapping("/itemdel")
    public String del_item(@RequestParam("name")String name){
        if(name!=null){
            int pos = shoppingCart.check_item(name);
            if(pos>=0){
                //Items item = shoppingCart.getItems().get(pos);
                //model.addAttribute("Item",item);
                shoppingCart.getItems().remove(pos);
            }
        }
        return "redirect:items";
    }



    @GetMapping("/updcust")
    public String upd_cust(@RequestParam("customer")String name,Model model){
        if(name!=null){
            int pos = shoppingCart.check_cust(name);
            if(pos>=0){
                Customer customer = shoppingCart.getCustomers().get(pos);
                model.addAttribute("customer",customer);
                return "cust_update";
            }
        }
        return "init";
    }

    @PostMapping("/updatecust")
    public String save_cust (@RequestParam("name")String[] name,@ModelAttribute("customer") Customer customer){
        if(customer.getName()!=null && customer.getContact()!=null){
            int pos = shoppingCart.check_cust(name[0]);
            if(pos>=0){
                Customer customer1=shoppingCart.getCustomers().get(pos);
                customer1.setName(name[1]);
                customer1.setContact(customer.getContact());
                return "redirect:init";
            }
        }
        return "redirect:init";
    }

    @GetMapping("/delcust")
    public String del_cust(@RequestParam("customer")String name){
        if(name!=null){
            int pos = shoppingCart.check_cust(name);
            if(pos>=0){
                shoppingCart.getCustomers().remove(pos);

            }
        }
        return "redirect:init";
    }

    @GetMapping("/additemcust") // todo
    public String add_item_cust(@RequestParam("customer")String name,Model model){

        if(name!=null){
            int pos=shoppingCart.check_cust(name);
            if(pos>=0){

                Customer customer = shoppingCart.getCustomers().get(pos);
                model.addAttribute("customer",customer);
                model.addAttribute("items",shoppingCart.getItems());
            }
        }
        return "customer_items"; // todo
    }

    @GetMapping("/custitemupd") // todo
    public String add_custitem(@RequestParam("name")String item,@RequestParam("customer")String customer,Model model){
        if(item!=null && customer!=null){
            int pos=shoppingCart.check_cust(customer);
            int pos1=shoppingCart.check_item(item);
            if(pos>=0 && pos1>=0){
                model.addAttribute("item",item);
                model.addAttribute("customer",customer);
            }
        }
        return "add_cust_item"; // todo
    }

    @GetMapping("/custitemdel")
    public String del_custitem(@RequestParam("name")String name){
        if(name!=null){
            int pos = shoppingCart.check_item(name);
            if(pos>=0){
                //Items item = shoppingCart.getItems().get(pos);
                //model.addAttribute("Item",item);
                shoppingCart.getItems().remove(pos);
            }
        }
        return "redirect:init";
    }

    @PostMapping("/savecustprod")
    public String add_cust_item(@RequestParam("item")String item,@RequestParam("customer")String customer,@RequestParam("quantity")String quantity,Model model){
        if(item!=null && customer !=null && quantity!=null){
            try{
                int pos = shoppingCart.check_cust(customer);
                int pos1 = shoppingCart.check_item(item);
                if(pos>=0 && pos1>=0){
                    shoppingCart.add_prod_basket(customer,item,Integer.valueOf(quantity));
                    model.addAttribute("name",item);
                    model.addAttribute("customer",customer);
                }
            }catch(Exception e){
                System.out.println("Exception"+e.getMessage());
            }finally{
                return "redirect:shop?customer="+customer;
            }

        }
        return "redirect:shop?customer=" +customer;
    }

    @GetMapping("/printbill")
    public String printbill(@RequestParam("customer")String customer,Model model){

        int pos = shoppingCart.check_cust(customer);
        if(pos>=0){
            Double price= 0.0;
            Customer customer1 = shoppingCart.getCustomers().get(pos);
            model.addAttribute("customer",customer1);

            /*
            Map<Integer,Items> a=customer1.getBasket().getBasket().entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue,Map.Entry::getKey));
            model.addAttribute("basket",a.entrySet());
            for(Map.Entry<Integer,Items> e:a.entrySet()){
                price+=e.getKey()*e.getValue().getPrice();
            }

             */

            model.addAttribute("basket",customer1.getBasket().getBasket().entrySet());
            for (Map.Entry<Items,Integer>e:customer1.getBasket().getBasket().entrySet()
                 ) {
                price+=e.getKey().getPrice()*e.getValue();
            }



            model.addAttribute("total",price);
        }
        return "printbill";
    }
}
