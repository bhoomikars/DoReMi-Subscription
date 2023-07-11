package com.example.geektrust.service;

import com.example.geektrust.constants.SubscriptionCategory;
import com.example.geektrust.constants.SubscriptionPlan;
import com.example.geektrust.constants.TopUpDevice;

public interface ISubscriptionService {

    void startSubscription(String date);

    void addSubscription(SubscriptionCategory subscriptionCategory, SubscriptionPlan subscriptionPlan);

    void addTopUp(TopUpDevice topUpDevice, int numberOfDevices);

    void printRenewalDetails();

}
