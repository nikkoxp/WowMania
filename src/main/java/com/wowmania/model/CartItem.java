package com.wowmania.model;

import java.math.BigDecimal;

public class CartItem {
    private final Listing listing;
    private int quantity;

    public CartItem(Listing listing) {
        this.listing = listing;
        this.quantity = 1;
    }

    public Listing getListing() {
        return listing;
    }

    public int getQuantity() {
        return quantity;
    }

    public void increment() {
        quantity++;
    }

    public BigDecimal getSubtotal() {
        return listing.getPrice().multiply(BigDecimal.valueOf(quantity));
    }
}