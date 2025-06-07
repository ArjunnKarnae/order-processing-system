package com.orderprocessing.orders.dto;

public class OrderItemDTO {

    public OrderItemDTO() {
    }

    public OrderItemDTO(ProductDTO productDTO, int quantity, double price) {
        this.productDTO = productDTO;
        this.quantity = quantity;
        this.price = price;
    }

    private ProductDTO productDTO;
    private int quantity;
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
