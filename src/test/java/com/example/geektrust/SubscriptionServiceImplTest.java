package com.example.geektrust;

import com.example.geektrust.constants.SubscriptionCategory;
import com.example.geektrust.constants.SubscriptionPlan;
import com.example.geektrust.constants.TopUpDevice;
import com.example.geektrust.domain.Subscription;
import com.example.geektrust.service.SubscriptionServiceImpl;
import junit.framework.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class SubscriptionServiceImplTest {

    @InjectMocks
    private SubscriptionServiceImpl subscriptionService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testStartSubscriptionWithInvalidDate() {
        subscriptionService.startSubscription("20-22-2022");
        Assert.assertEquals(subscriptionService.getSubscription(), Optional.empty());
    }

    @Test
    public void testStartSubscriptionWithValidDate() {
        subscriptionService.startSubscription("20-02-2022");
        Assert.assertEquals(subscriptionService.getSubscription(), Optional.of(new Subscription("20-02-2022")));
    }

    @Test
    public void testAddSubscriptionWhenSubscriptionIsNotPresent() {
        subscriptionService.addSubscription(SubscriptionCategory.MUSIC, SubscriptionPlan.FREE);
        Assert.assertEquals(subscriptionService.getSubscription(), Optional.empty());
    }

    @Test
    public void testAddSubscriptionWhenSubscriptionIsPresent() {
        subscriptionService.startSubscription("20-02-2022");
        subscriptionService.addSubscription(SubscriptionCategory.MUSIC, SubscriptionPlan.FREE);
        Subscription subscription = subscriptionService.getSubscription().get();
        Assert.assertEquals(subscription.getRenewalMap().size(), 1);
    }

    @Test
    public void testTopUpWhenSubscriptionIsNotPresent() {
        subscriptionService.addTopUp(TopUpDevice.FOUR_DEVICE, 3);
        Assert.assertEquals(subscriptionService.getSubscription(), Optional.empty());
    }

    @Test
    public void testTopUpWhenSubscriptionIsPresent() {
        subscriptionService.startSubscription("20-02-2022");
        subscriptionService.addSubscription(SubscriptionCategory.MUSIC, SubscriptionPlan.FREE);
        subscriptionService.addTopUp(TopUpDevice.FOUR_DEVICE, 1);
        Subscription subscription = subscriptionService.getSubscription().get();
        Assert.assertEquals(subscription.getTopUpPrice(), 50);
    }


    @Test
    public void testPrintRenewal() {
        subscriptionService.printRenewalDetails();
        Assert.assertEquals(subscriptionService.getSubscription(), Optional.empty());
    }

    @Test
    public void testPrintRenewalWhenRenewalDetailsArePresent() {
        subscriptionService.startSubscription("20-02-2022");
        subscriptionService.addSubscription(SubscriptionCategory.MUSIC, SubscriptionPlan.FREE);
        subscriptionService.printRenewalDetails();
        Assert.assertEquals(subscriptionService.getSubscription().get().getRenewalMap().size(), 1);
    }


}
