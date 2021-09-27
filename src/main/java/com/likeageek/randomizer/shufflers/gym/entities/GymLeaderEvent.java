package com.likeageek.randomizer.shufflers.gym.entities;

public class GymLeaderEvent {
    private String event;
    private String badge;

    public String getEvent() {
        return event;
    }

    public GymLeaderEvent setEvent(String event) {
        this.event = event;
        return this;
    }

    public String getBadge() {
        return badge;
    }

    public GymLeaderEvent setBadge(String badge) {
        this.badge = badge;
        return this;
    }
}
