package com.likeageek.randomizer.api;

import java.util.LinkedHashMap;

public class RomInformation {
    Long seed;

    public Long getSeed() {
        return seed;
    }

    public void setSeed(Long seed) {
        this.seed = seed;
    }

    public RomInformation() {
    }

    Long timestamp;

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    LinkedHashMap<String, Boolean> parameters;

    public LinkedHashMap<String, Boolean> getParameters() {
        return parameters;
    }

    public void setParameters(LinkedHashMap<String, Boolean> parameters) {
        this.parameters = parameters;
    }

    LinkedHashMap<String, Boolean> customization;

    public LinkedHashMap<String, Boolean> getCustomization() {
        return customization;
    }

    public void setCustomization(LinkedHashMap<String, Boolean> customization) {
        this.customization = customization;
    }
}
