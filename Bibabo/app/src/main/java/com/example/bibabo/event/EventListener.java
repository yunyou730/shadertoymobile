package com.example.bibabo.event;

public interface EventListener {

    public abstract void onEvent(EventDispatcher.EventType eventType,EventDispatcher.WizardEvent event);
}
