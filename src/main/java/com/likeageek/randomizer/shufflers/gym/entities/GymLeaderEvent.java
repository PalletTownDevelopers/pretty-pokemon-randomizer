package com.likeageek.randomizer.shufflers.gym.entities;

public class GymLeaderEvent {
    private String event;
    private Integer badge;

    public String getEvent() {
        return event;
    }

    public GymLeaderEvent setEvent(String event) {
        this.event = event;
        return this;
    }

    public Integer getBadge() {
        return badge;
    }

    public GymLeaderEvent setBadge(Integer badge) {
        this.badge = badge;
        return this;
    }
}
