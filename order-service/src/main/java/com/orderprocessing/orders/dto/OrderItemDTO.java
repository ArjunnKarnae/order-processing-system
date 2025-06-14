package com.orderprocessing.orders.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Order Item", description = "Order Item DTO")
public class OrderItemDTO {

    public OrderItemDTO() {
    }

    public OrderItemDTO(ProductDTO productDTO, int quantity, double price) {
        this.productDTO = productDTO;
        this.quantity = quantity;
        this.price = price;
    }

    @Schema(name = "Product", description = "Product Description")
    private ProductDTO productDTO;
    @Schema(name = "quantity", description = "The number of products Ordered")
    private int quantity;
    @Schema(name = "price", description = "Price for each product")
    private double price;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ProductDTO getProductDTO() {
        return productDTO;
    }

    public void setProductDTO(ProductDTO productDTO) {
        this.productDTO = productDTO;
    }

    @Override
    public String toString() {
        return "OrderItemDTO{" +
                "productDTO=" + productDTO +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
