package com.likeageek.randomizer.shufflers.gym.entities;

public class GymEvent {
    private Integer[] linesCheckEvents;

    public Integer[] getLinesSetEvents() {
        return linesSetEvents;
    }

    public GymEvent setLinesSetEvents(Integer[] linesSetEvents) {
        this.linesSetEvents = linesSetEvents;
        return this;
    }

    private Integer[] linesSetEvents;

    public Integer[] getLinesCheckEvents() {
        return linesCheckEvents;
    }

    public GymEvent setLinesCheckEvents(Integer[] linesCheckEvents) {
        this.linesCheckEvents = linesCheckEvents;
        return this;
    }

    String badge;
    Integer[] linesBadge;

    public String getBadge() {
        return badge;
    }

    public GymEvent setBadge(String badge) {
        this.badge = badge;
        return this;
    }

    public Integer[] getLinesBadge() {
        return linesBadge;
    }

    public GymEvent setLinesBadge(Integer[] linesBadge) {
        this.linesBadge = linesBadge;
        return this;
    }
}
