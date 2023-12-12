package fr.codesbusters.solidstock.listener;

public interface CustomerSelectorListener {
    void processCustomerContent(String customerContent);

    void onCustomerSelected(int customerId, String customerName);
}
