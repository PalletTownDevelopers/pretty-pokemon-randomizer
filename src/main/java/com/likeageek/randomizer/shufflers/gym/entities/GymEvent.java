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

    Integer badgeNumber;
    Integer[] linesBadge;

    public Integer getBadgeNumber() {
        return badgeNumber;
    }

    public GymEvent setBadgeNumber(Integer badgeNumber) {
        this.badgeNumber = badgeNumber;
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
