package com.likeageek.randomizer.shufflers.gym.processors;

import com.likeageek.randomizer.IFileManager;
import com.likeageek.randomizer.IFileParser;
import com.likeageek.randomizer.shufflers.gym.entities.Gym;

import java.util.HashMap;
import java.util.Map;

import static com.likeageek.randomizer.shufflers.gym.entities.Cities.ViridianCity;
import static org.apache.commons.lang.WordUtils.capitalize;
import static org.apache.commons.lang.WordUtils.capitalizeFully;

public class CitiesGymProcessor {

    private static final String MAP_OBJECTS_FILEPATH = "/data/mapObjects/";
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

    private Map<String, String> gymLeaderEvents = new HashMap<>() {{
        put("Brock", "EVENT_BEAT_BROCK");
        put("Misty", "EVENT_BEAT_MISTY");
        put("LtSurge", "EVENT_BEAT_LT_SURGE");
        put("Erika", "EVENT_BEAT_ERIKA");
        put("Koga", "EVENT_BEAT_KOGA");
        put("Sabrina", "EVENT_BEAT_SABRINA");
        put("Blaine", "EVENT_BEAT_BLAINE");
        put("Giovanni", "EVENT_BEAT_VIRIDIAN_GYM_GIOVANNI");
    }};

    private Map<String, Integer> gymSetEventLineNumber = new HashMap<>() {{
        put("ViridianGym", 97);
        put("CeladonGym", 34);
        put("SaffronGym", 34);
        put("VermilionGym", 49);
        put("CeruleanGym", 34);
        put("PewterGym", 34);
        put("FuchsiaGym", 36);
        put("CinnabarGym", 115);
    }};

    private Map<String, Integer> gymCheckEventLineNumber = new HashMap<>() {{
        put("ViridianGym", 192);
        put("CeladonGym", 115);
        put("SaffronGym", 116);
        put("VermilionGym", 99);
        put("CeruleanGym", 76);
        put("PewterGym", 75);
        put("FuchsiaGym", 110);
        put("CinnabarGym", 165);
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
        int lineBeatLeaderSetEventIndex = gymSetEventLineNumber.get(gymNameCamelCase);
        int lineBeatLeaderCheckEventIndex = gymCheckEventLineNumber.get(gymNameCamelCase);
        gymScriptAsm[lineBeatLeaderSetEventIndex] = "SetEvent " + gymLeaderEvents.get(leaderOldName);
        gymScriptAsm[lineBeatLeaderCheckEventIndex] = "CheckEvent " + gymLeaderEvents.get(leaderOldName);
        this.asmFileManager.write(gymScriptFilePath, gymScriptAsm);
    }

    private String convertToCamelCase(String gymName) {
        return capitalize(capitalizeFully(gymName, new char[]{'_'}).replaceAll("_", ""));
    }
}
