package com.orderprocessing.inventory.dto;

public class OrderItemDTO {
    private int quantity;
    private double price;
    private ProductDTO productDTO;

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
                "quantity=" + quantity +
                ", price=" + price +
                ", productDTO=" + productDTO +
                '}';
    }
}
