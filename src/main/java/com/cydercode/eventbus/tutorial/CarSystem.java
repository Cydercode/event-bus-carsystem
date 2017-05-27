package com.cydercode.eventbus.tutorial;


import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import java.util.Random;

public class CarSystem {

    public static void main(String[] args) {
        EventBus eventBus = new EventBus();

        Engine engine = new Engine(eventBus);
        Clocks clocks = new Clocks();
        Ignition ignition = new Ignition(eventBus);

        eventBus.register(engine);
        eventBus.register(clocks);
        eventBus.register(ignition);

        ignition.doIgnition();
    }
}

class Engine {

    private final EventBus eventBus;

    public Engine(EventBus eventBus) {
        this.eventBus = eventBus;
    }


    @Subscribe
    public void onIgnitionEvent(IgnitionEvent event) {
        System.out.println("Engine is starting!");
        eventBus.post(new RpmEvent(new Random().nextInt(10000) + 1000));
    }
}

class Clocks {

    @Subscribe
    public void onRpmEvent(RpmEvent event) {
        System.out.println("Engine is running, rpm: " + event.getRpm());
    }
}

class Ignition {

    private final EventBus eventBus;

    public Ignition(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void doIgnition() {
        eventBus.post(new IgnitionEvent());
    }
}


class IgnitionEvent {

}

class RpmEvent {

    private int rpm;

    public RpmEvent(int rpm) {
        this.rpm = rpm;
    }

    public int getRpm() {
        return rpm;
    }

}