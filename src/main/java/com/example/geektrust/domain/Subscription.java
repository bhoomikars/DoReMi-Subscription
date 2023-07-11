package com.example.geektrust.domain;

import com.example.geektrust.constants.SubscriptionCategory;
import com.example.geektrust.constants.SubscriptionConstant;
import com.example.geektrust.constants.SubscriptionPlan;
import com.example.geektrust.constants.TopUpDevice;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import static com.example.geektrust.constants.SubscriptionConstant.*;


public class Subscription {

    @Override
    public String toString() {
        return "Subscription{" +
                "subscriptionMap=" + subscriptionMap +
                ", addSubscriptionMap=" + addSubscriptionMap +
                ", renewalMap=" + renewalMap +
                ", subscriptionDate='" + subscriptionDate + '\'' +
                ", topUpPrice=" + topUpPrice +
                ", totalSubscriptionCharges=" + totalSubscriptionCharges +
                ", subscriptionCategory=" + subscriptionCategory +
                ", subscriptionPlan=" + subscriptionPlan +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subscription that = (Subscription) o;
        return topUpPrice == that.topUpPrice && totalSubscriptionCharges == that.totalSubscriptionCharges && Objects.equals(subscriptionMap, that.subscriptionMap) && Objects.equals(addSubscriptionMap, that.addSubscriptionMap) && Objects.equals(renewalMap, that.renewalMap) && Objects.equals(subscriptionDate, that.subscriptionDate) && subscriptionCategory == that.subscriptionCategory && subscriptionPlan == that.subscriptionPlan;
    }

    @Override
    public int hashCode() {
        return Objects.hash(subscriptionMap, addSubscriptionMap, renewalMap, subscriptionDate, topUpPrice, totalSubscriptionCharges, subscriptionCategory, subscriptionPlan);
    }

    private static final Map<SubscriptionCategory, Map<SubscriptionPlan, Integer>> subscriptionCategoryMap = new HashMap<>();
    private static final Map<SubscriptionPlan, Integer> planChargesMapForVideo = new HashMap<>();
    private static final Map<SubscriptionPlan, Integer> planChargesMapForMusic = new HashMap<>();
    private static final Map<SubscriptionPlan, Integer> planChargesMapForPodCast = new HashMap<>();

    static {
        planChargesMapForMusic.put(SubscriptionPlan.FREE, 0);
        planChargesMapForMusic.put(SubscriptionPlan.PERSONAL, 100);
        planChargesMapForMusic.put(SubscriptionPlan.PREMIUM, 250);
        subscriptionCategoryMap.put(SubscriptionCategory.MUSIC, planChargesMapForMusic);

        planChargesMapForPodCast.put(SubscriptionPlan.FREE, 0);
        planChargesMapForPodCast.put(SubscriptionPlan.PERSONAL, 100);
        planChargesMapForPodCast.put(SubscriptionPlan.PREMIUM, 300);
        subscriptionCategoryMap.put(SubscriptionCategory.PODCAST, planChargesMapForPodCast);

        planChargesMapForVideo.put(SubscriptionPlan.FREE, 0);
        planChargesMapForVideo.put(SubscriptionPlan.PERSONAL, 200);
        planChargesMapForVideo.put(SubscriptionPlan.PREMIUM, 500);
        subscriptionCategoryMap.put(SubscriptionCategory.VIDEO, planChargesMapForVideo);

    }

    private final Map<String, Subscription> subscriptionMap = new HashMap<>();
    private final Map<SubscriptionCategory, SubscriptionPlan> addSubscriptionMap = new HashMap<>();
    private final Map<SubscriptionCategory, String> renewalMap = new LinkedHashMap<>();
    private final String subscriptionDate;

    public Map<SubscriptionCategory, String> getRenewalMap() {
        return renewalMap;
    }

    private int topUpPrice = 0;

    public int getTopUpPrice() {
        return topUpPrice;
    }

    private int totalSubscriptionCharges = 0;
    private SubscriptionCategory subscriptionCategory;
    private SubscriptionPlan subscriptionPlan;

    public Subscription(String subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    public void addSubscription(SubscriptionCategory subscriptionCategory, SubscriptionPlan subscriptionPlan) {
        if (!addSubscriptionMap.containsKey(subscriptionCategory)) {
            addSubscriptionMap.put(subscriptionCategory, subscriptionPlan);
            String renewalDate = getRenewalDate(subscriptionPlan);
            Map<SubscriptionPlan, Integer> subscriptionPlanIntegerMap = subscriptionCategoryMap.get(subscriptionCategory);
            this.totalSubscriptionCharges += subscriptionPlanIntegerMap.get(subscriptionPlan);
            this.renewalMap.put(subscriptionCategory, renewalDate);
        } else {
            System.out.println(ADD_SUBSCRIPTION_FAILED + DUPLICATE_CATEGORY);
        }
    }

    private String getRenewalDate(SubscriptionPlan subscriptionPlan) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(DATE_FORMAT);
        LocalDate renewalDate = LocalDate.parse(this.subscriptionDate, df);
        LocalDate result = renewalDate.plusMonths(subscriptionPlan.getLabel()).minusDays(SubscriptionConstant.NO_OF_DAYS_BEFORE_TO_NOTIFIED);
        return df.format(result);
    }

    public void addTopUp(TopUpDevice topUpDevice, int numberOfPlans) {
        if (this.addSubscriptionMap.size() == 0) {
            System.out.println(ADD_TOPUP_FAILED + SUBSCRIPTIONS_NOT_FOUND);
        } else {
            if (topUpPrice != 0) {
                System.out.println(ADD_TOPUP_FAILED + DUPLICATE_TOPUP);
            }
            this.topUpPrice = (topUpDevice.equals(TopUpDevice.FOUR_DEVICE)) ? 50 : 100;
            this.topUpPrice = (this.topUpPrice * numberOfPlans);
        }

    }

    public void printSubscriptionRenewalDetails() {
        if (renewalMap.size() == 0) {
            System.out.println(SUBSCRIPTIONS_NOT_FOUND);
            return;
        }
        this.renewalMap.entrySet().stream().forEach(subscriptionCategory ->
                System.out.println(RENEWAL_REMINDER + subscriptionCategory.getKey() + " " + subscriptionCategory.getValue()));

        System.out.println(RENEWAL_AMOUNT + (this.topUpPrice + this.totalSubscriptionCharges));
    }
}
