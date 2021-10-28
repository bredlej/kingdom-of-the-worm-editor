package com.kingdom.kingdomofthewormeditor.util;

import javafx.event.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomEventBus implements EventDispatcher {
    Map<EventType<?>, List<WeakEventHandler<Event>>> listenerMap;

    private static CustomEventBus instance;

    public static CustomEventBus getInstance() {
        if (instance == null) {
            instance = new CustomEventBus();
        }
        return instance;
    }

    private CustomEventBus() {
        listenerMap = new HashMap<>();
    }

    public <T extends Event> void addListener(EventType<T> clazz, WeakEventHandler<Event> listener) {
        List<WeakEventHandler<Event>> list = listenerMap.computeIfAbsent(clazz, k -> new ArrayList<>());
        list.add(listener);
    }

    public void fireEvent(Event event) {
        if (!listenerMap.containsKey(event.getEventType()))
            return;
        listenerMap.get(event.getEventType()).forEach(eventHandler -> eventHandler.handle(event));
    }

    @Override
    public Event dispatchEvent(Event event, EventDispatchChain eventDispatchChain) {
        fireEvent(event);
        return event;
    }
}
