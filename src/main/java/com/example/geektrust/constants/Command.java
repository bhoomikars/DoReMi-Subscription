package com.example.geektrust.constants;


import com.example.geektrust.service.ISubscriptionService;
import com.example.geektrust.service.SubscriptionServiceImpl;

public enum Command {
    START_SUBSCRIPTION((svc, tokens) -> svc.startSubscription(tokens[1])),
    ADD_SUBSCRIPTION((svc, tokens) -> svc.addSubscription(SubscriptionCategory.valueOf(tokens[1]), SubscriptionPlan.valueOf(tokens[2]))),
    ADD_TOPUP((svc, tokens) -> svc.addTopUp(TopUpDevice.valueOf(tokens[1]), Integer.parseInt(tokens[2]))),
    PRINT_RENEWAL_DETAILS((svc, tokens) -> svc.printRenewalDetails());

    private static final ISubscriptionService subscriptionService = new SubscriptionServiceImpl();

    private final CommandProcessor commandProcessor;

    Command(CommandProcessor processor) {
        this.commandProcessor = processor;
    }

    public void process(String[] inputTokens) {
        this.commandProcessor.process(subscriptionService, inputTokens);
    }
}

@FunctionalInterface
interface CommandProcessor {
    void process(ISubscriptionService metroService, String[] tokens);
}
