package com.likeageek.randomizer.shufflers.gym.processors;

import com.likeageek.randomizer.IFileManager;
import com.likeageek.randomizer.IFileParser;
import com.likeageek.randomizer.shufflers.gym.entities.Gym;
import com.likeageek.randomizer.shufflers.gym.entities.Gyms;
import org.apache.commons.lang.WordUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.join;

public class CitiesGymProcessor {

    private static final String MAP_OBJECTS_FILEPATH = "mapObjects/";
    private static final String DATA_FILEPATH = "/data/";

    private IFileManager asmFileManager;
    private IFileParser asmFileParser;

    private Map<String, Integer> cityLineNumber = new HashMap<>() {{
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

    public CitiesGymProcessor(IFileManager asmFileManager, IFileParser asmFileParser) {
        this.asmFileManager = asmFileManager;
        this.asmFileParser = asmFileParser;
    }

    public void process(Map<String, Object> gyms) {
        for (Map.Entry<String, Object> entry : gyms.entrySet()) {
            String city = entry.getKey();
            Gym gym = (Gym) (gyms.get(city));
            String gymName = gym.getName().toString();

            buildCityAsmFile(city, gymName);
            buildGymAsmFile(gymName, gym.getWarpId());
            buildGymAsmScriptFile(gym);
        }
    }

    private void buildCityAsmFile(String city, String gymToReplace) {
        try {
            String[] lines = this.asmFileManager.read(DATA_FILEPATH + MAP_OBJECTS_FILEPATH + city);
            String cityFileContent = buildAsmFileCity(city, gymToReplace, lines);
            this.asmFileManager.write(DATA_FILEPATH + MAP_OBJECTS_FILEPATH + city, cityFileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void buildGymAsmFile(String gym, int warpId) {
        try {
            String gymName = Gyms.valueOf(gym).getName();
            String[] gymLines = this.asmFileManager.read(DATA_FILEPATH + MAP_OBJECTS_FILEPATH + gymName);
            String gymFileContent = buildAsmFileGym(warpId, gymLines);
            this.asmFileManager.write(DATA_FILEPATH + MAP_OBJECTS_FILEPATH + gymName, gymFileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void buildGymAsmScriptFile(Gym gym) {
        try {
            String gymCamelCase = WordUtils.capitalize(WordUtils.capitalizeFully(gym.getName().toString(), new char[]{'_'}).replaceAll("_", ""));
            String[] lines = this.asmFileManager.read("/scripts/" + gymCamelCase);
            int index = gymSetEventLineNumber.get(gymCamelCase);
            lines[index] = "SetEvent " + gymLeaderEvents.get(gym.getLeaderOld().name());
            this.asmFileManager.write("/scripts/" + gymCamelCase, join("\n\t", lines));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String buildAsmFileCity(String cityName, String gymName, String[] lines) {
        Integer lineNumber = this.cityLineNumber.get(cityName);
        lines[lineNumber] = this.asmFileParser.editLine(lines[lineNumber], gymName, 4);
        addNewlineToViridianCity(cityName, lines, lineNumber);
        return join("\n\t", lines);
    }

    private void addNewlineToViridianCity(String cityName, String[] lines, Integer lineNumber) {
        if ("ViridianCity".equals(cityName)) {
            lines[lineNumber] = lines[lineNumber].concat("\n");
        }
    }

    private String buildAsmFileGym(int warpId, String[] lines) {
        lines[3] = this.asmFileParser.editLine(lines[3], Integer.toString(warpId), 3);
        lines[4] = this.asmFileParser.editLine(lines[4], Integer.toString(warpId), 3);
        return join("\n\t", lines);
    }
}
