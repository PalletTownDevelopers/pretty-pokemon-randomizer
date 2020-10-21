package com.likeageek.randomizer.shufflers.gym.processors;

import com.likeageek.randomizer.IFileManager;
import com.likeageek.randomizer.IFileParser;
import com.likeageek.randomizer.shufflers.gym.entities.GymEvent;
import com.likeageek.randomizer.shufflers.gym.entities.Gym;
import com.likeageek.randomizer.shufflers.gym.entities.GymLeaderEvent;

import java.util.HashMap;
import java.util.Map;

import static com.likeageek.randomizer.shufflers.gym.entities.Cities.ViridianCity;
import static org.apache.commons.lang.WordUtils.capitalize;
import static org.apache.commons.lang.WordUtils.capitalizeFully;

public class CitiesGymProcessor {

    private static final String MAP_OBJECTS_FILEPATH = "/data/maps/objects/";
    private static final String SCRIPTS_FILEPATH = "/scripts/";

    private IFileManager asmFileManager;
    private IFileParser asmFileParser;

    private Map<String, Integer> cityGymsLineNumber = new HashMap<>() {{
        put("ViridianCity", 7);
        put("CeladonCity", 9);
        put("VermilionCity", 6);
        put("CeruleanCity", 6);
        put("PewterCity", 5);
        put("SaffronCity", 5);
        put("FuchsiaCity", 8);
        put("CinnabarIsland", 4);
    }};

    private Map<String, GymLeaderEvent> listGymLeaderEvent = new HashMap<>() {{
        put("Brock", new GymLeaderEvent().setEvent("EVENT_BEAT_BROCK").setBadge(0));
        put("Misty", new GymLeaderEvent().setEvent("EVENT_BEAT_MISTY").setBadge(1));
        put("LtSurge", new GymLeaderEvent().setEvent("EVENT_BEAT_LT_SURGE").setBadge(2));
        put("Erika", new GymLeaderEvent().setEvent("EVENT_BEAT_ERIKA").setBadge(3));
        put("Koga", new GymLeaderEvent().setEvent("EVENT_BEAT_KOGA").setBadge(4));
        put("Sabrina", new GymLeaderEvent().setEvent("EVENT_BEAT_SABRINA").setBadge(5));
        put("Blaine", new GymLeaderEvent().setEvent("EVENT_BEAT_BLAINE").setBadge(6));
        put("Giovanni", new GymLeaderEvent().setEvent("EVENT_BEAT_VIRIDIAN_GYM_GIOVANNI").setBadge(7));
    }};

    private Map<String, GymEvent> listGymEvent = new HashMap<>() {{
        put("ViridianGym", new GymEvent().setLinesSetEvents(new Integer[]{97}).setLinesCheckEvents(new Integer[]{144,277}).setBadgeNumber(7).setLinesBadge(new Integer[]{110,112}));
        put("CeladonGym", new GymEvent().setLinesSetEvents(new Integer[]{34}).setLinesCheckEvents(new Integer[]{73}).setBadgeNumber(3).setLinesBadge(new Integer[]{47,49}));
        put("SaffronGym", new GymEvent().setLinesSetEvents(new Integer[]{34}).setLinesCheckEvents(new Integer[]{74,146}).setBadgeNumber(5).setLinesBadge(new Integer[]{47,49}));
        put("VermilionGym", new GymEvent().setLinesSetEvents(new Integer[]{49}).setLinesCheckEvents(new Integer[]{81}).setBadgeNumber(2).setLinesBadge(new Integer[]{62,64}));
        put("CeruleanGym", new GymEvent().setLinesSetEvents(new Integer[]{34}).setLinesCheckEvents(new Integer[]{64,129}).setBadgeNumber(1).setLinesBadge(new Integer[]{47,49}));
        put("PewterGym", new GymEvent().setLinesSetEvents(new Integer[]{34}).setLinesCheckEvents(new Integer[]{69}).setBadgeNumber(0).setLinesBadge(new Integer[]{47,49}));
        put("FuchsiaGym", new GymEvent().setLinesSetEvents(new Integer[]{36}).setLinesCheckEvents(new Integer[]{74,178}).setBadgeNumber(4).setLinesBadge(new Integer[]{49,51}));
        put("CinnabarGym", new GymEvent().setLinesSetEvents(new Integer[]{115}).setLinesCheckEvents(new Integer[]{165,333}).setBadgeNumber(6).setLinesBadge(new Integer[]{128,130}));
    }};

    public CitiesGymProcessor(IFileManager asmFileManager, IFileParser asmFileParser) {
        this.asmFileManager = asmFileManager;
        this.asmFileParser = asmFileParser;
    }

    public void process(Map<String, Object> gyms) {
        gyms.keySet().forEach(city -> {
            Gym gym = (Gym) (gyms.get(city));
            String gymName = gym.getName().toString();

            buildCityFileWithNewGym(gymName, city);
            buildGymFileWithNewWarp(gymName, gym.getWarpId());
            buildGymScriptFileWithEvent(gymName, gym.getLeaderOld().name());
        });
    }

    private void buildCityFileWithNewGym(String gymToReplace, String city) {
        String cityFilePath = MAP_OBJECTS_FILEPATH + city;
        String[] cityAsm = this.asmFileManager.read(cityFilePath);
        Integer gymLineNumber = this.cityGymsLineNumber.get(city);
        cityAsm[gymLineNumber] = this.asmFileParser.editLine(cityAsm[gymLineNumber], gymToReplace, 4);
        addNewlineToViridianCity(city, cityAsm, gymLineNumber);
        this.asmFileManager.write(cityFilePath, cityAsm);
    }

    private void addNewlineToViridianCity(String cityName, String[] lines, Integer lineNumber) {
        if (cityName.equals(ViridianCity.name())) {
            lines[lineNumber] = lines[lineNumber].concat("\n");
        }
    }

    private void buildGymFileWithNewWarp(String gymName, int warpId) {
        String gymNameCamelCase = convertToCamelCase(gymName);
        String gymFilePath = MAP_OBJECTS_FILEPATH + gymNameCamelCase;
        String[] gymAsm = this.asmFileManager.read(gymFilePath);
        gymAsm[3] = this.asmFileParser.editLine(gymAsm[3], Integer.toString(warpId), 3);
        gymAsm[4] = this.asmFileParser.editLine(gymAsm[4], Integer.toString(warpId), 3);
        this.asmFileManager.write(gymFilePath, gymAsm);
    }

    private void buildGymScriptFileWithEvent(String gymName, String leaderOldName) {
        String gymNameCamelCase = convertToCamelCase(gymName);
        String gymScriptFilePath = SCRIPTS_FILEPATH + gymNameCamelCase;
        String[] gymScriptAsm = this.asmFileManager.read(gymScriptFilePath);

        for(Integer lineSetEvent : listGymEvent.get(gymNameCamelCase).getLinesSetEvents()) {
            gymScriptAsm[lineSetEvent] = "SetEvent " + listGymLeaderEvent.get(leaderOldName).getEvent();
        }

        for(Integer lineCheckEvent : listGymEvent.get(gymNameCamelCase).getLinesCheckEvents()) {
            gymScriptAsm[lineCheckEvent] = "CheckEvent " + listGymLeaderEvent.get(leaderOldName).getEvent();
        }

        for(Integer lineBadge : listGymEvent.get(gymNameCamelCase).getLinesBadge()) {
            gymScriptAsm[lineBadge] = "set " + listGymLeaderEvent.get(leaderOldName).getBadge() + ", [hl]";
        }

        this.asmFileManager.write(gymScriptFilePath, gymScriptAsm);
    }

    private String convertToCamelCase(String gymName) {
        return capitalize(capitalizeFully(gymName, new char[]{'_'}).replaceAll("_", ""));
    }
}
