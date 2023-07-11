package com.example.geektrust.service;

import com.example.geektrust.constants.SubscriptionCategory;
import com.example.geektrust.constants.SubscriptionPlan;
import com.example.geektrust.constants.TopUpDevice;
import com.example.geektrust.domain.Subscription;
import com.example.geektrust.utils.ValidateDate;

import java.util.Optional;

import static com.example.geektrust.constants.SubscriptionConstant.*;

public class SubscriptionServiceImpl implements ISubscriptionService {

    private Optional<Subscription> subscription = Optional.empty();

    public Optional<Subscription> getSubscription() {
        return subscription;
    }

    @Override
    public void startSubscription(String date) {
        if (ValidateDate.validateSubscriptionDate(date)) {
            Subscription subscriptionObj = new Subscription(date);
            subscription = Optional.of(subscriptionObj);
        } else {
            System.out.println(INVALID_DATE);
        }

    }

    @Override
    public void addSubscription(SubscriptionCategory subscriptionCategory, SubscriptionPlan subscriptionPlan) {
        Subscription subscription = this.subscription.orElse(null);
        if (subscription == null) {
            System.out.println(ADD_SUBSCRIPTION_FAILED + INVALID_DATE);
            return;
        }
        subscription.addSubscription(subscriptionCategory, subscriptionPlan);
    }

    @Override
    public void addTopUp(TopUpDevice topUpDevice, int numberOfPlans) {
        Subscription subscription = this.subscription.orElse(null);
        if (subscription == null) {
            System.out.println(ADD_TOPUP_FAILED + INVALID_DATE);
            return;
        }
        subscription.addTopUp(topUpDevice, numberOfPlans);
    }

    @Override
    public void printRenewalDetails() {
        Subscription subscription = this.subscription.orElse(null);
        if (subscription == null) {
            System.out.println(SUBSCRIPTIONS_NOT_FOUND);
            return;
        }
        subscription.printSubscriptionRenewalDetails();
    }
}
