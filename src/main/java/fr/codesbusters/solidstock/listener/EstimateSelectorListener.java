package fr.codesbusters.solidstock.listener;

public interface EstimateSelectorListener {

    void processEstimateContent(String estimateContent);

    void onEstimateSelected(int estimateId, String estimateSubject);
}
