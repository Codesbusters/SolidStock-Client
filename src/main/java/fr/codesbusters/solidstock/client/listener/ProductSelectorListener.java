package fr.codesbusters.solidstock.client.listener;

public interface ProductSelectorListener {
    void processProductContent(String productContent, String productName, String productPrice);
}