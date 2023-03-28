package com.aixo.demoshop.controller.HomeControllerpackage;

import com.aixo.demoshop.global.GlobalCart;
import com.aixo.demoshop.model.Product;
import com.aixo.demoshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CartController {
    @Autowired
    ProductService productService;
    @GetMapping("/addToCart/{id}")
    public String addToCart(@PathVariable int id ) {
        GlobalCart.cart.add(productService.getProductById(id).get());
        return "redirect:/shop";
    }

    @GetMapping("/cart")
    public String cartGet(Model model) {
        model.addAttribute("cartCount", GlobalCart.cart.size());
        model.addAttribute("total", GlobalCart.cart.stream().mapToDouble(Product::getPrice).sum());
        model.addAttribute("cart", GlobalCart.cart);
        return "cart";
    }
}
