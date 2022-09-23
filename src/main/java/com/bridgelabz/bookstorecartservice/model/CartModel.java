package com.bridgelabz.bookstorecartservice.model;

import com.bridgelabz.bookstorecartservice.dto.CartDTO;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "CartDetails")
@Data
public class CartModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cartId;
    private Long userId;
    private Long bookId;
    private Long quantity;
    private Long totalPrice;

    public CartModel(CartDTO cartDTO) {
        this.quantity = cartDTO.getQuantity();
    }

    public CartModel() {
    }
}
