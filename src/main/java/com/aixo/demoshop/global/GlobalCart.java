package com.aixo.demoshop.global;

import com.aixo.demoshop.model.Product;

import java.util.ArrayList;
import java.util.List;

public class GlobalCart {
    public static List<Product> cart;
    static {
        cart = new ArrayList<Product>();
    }
}
