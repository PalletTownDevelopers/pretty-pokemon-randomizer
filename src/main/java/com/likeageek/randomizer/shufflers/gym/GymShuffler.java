package com.likeageek.randomizer.shufflers.gym;

import com.likeageek.randomizer.IFileManager;
import com.likeageek.randomizer.IFileParser;
import com.likeageek.randomizer.IRandomEngine;
import com.likeageek.randomizer.shufflers.IShuffler;
import com.likeageek.randomizer.shufflers.gym.entities.City;
import com.likeageek.randomizer.shufflers.gym.entities.Gym;
import com.likeageek.randomizer.shufflers.gym.entities.Gyms;
import com.likeageek.randomizer.shufflers.gym.processors.CitiesGymProcessor;
import com.likeageek.randomizer.shufflers.gym.processors.LeaderTrainersProcessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.likeageek.randomizer.shufflers.gym.entities.Cities.*;
import static com.likeageek.randomizer.shufflers.gym.entities.CityBuilder.city;
import static com.likeageek.randomizer.shufflers.gym.entities.GymBuilder.gym;
import static com.likeageek.randomizer.shufflers.gym.entities.Gyms.*;
import static com.likeageek.randomizer.shufflers.gym.entities.Leaders.*;
import static java.util.Arrays.asList;

public class GymShuffler implements IShuffler {
    private IRandomEngine randomEngine;
    private CitiesGymProcessor citiesGymProcessor;
    private LeaderTrainersProcessor leaderTrainersProcessor;

    public GymShuffler(IFileManager asmFileManager, IFileParser asmFileParser, IRandomEngine randomEngine) {
        this.randomEngine = randomEngine;
        citiesGymProcessor = new CitiesGymProcessor(asmFileManager, asmFileParser);
        leaderTrainersProcessor = new LeaderTrainersProcessor(asmFileManager, asmFileParser, randomEngine);

        System.out.println("gym shuffler");
    }

    @Override
    public Map<String, Object> shuffle() {
        Map<String, Object> gymsRandomized = new HashMap<>();
        List<City> cities = this.buildCities();
        List<Object> citiesRandomized = this.randomEngine.random(new ArrayList<>(cities));

        for (int index = 0; index < cities.size(); index++) {
            City city = cities.get(index);
            String cityName = city.getName().toString();

            Gym gym = ((City) citiesRandomized.get(index)).getGym();
            Gym newGym = gym()
                    .name(Gyms.valueOf(gym.getName().toString()))
                    .warpId(city.getGym().getWarpId())
                    .leaderOld(city.getGym().getLeader())
                    .leader(gym.getLeader())
                    .pokemonRangeLevel(city.getGym().getPokemonRangeLevel())
                    .build();
            gymsRandomized.put(cityName, newGym);
        }
        return gymsRandomized;
    }

    @Override
    public void process(Map<String, Object> gyms) {
        citiesGymProcessor.process(gyms);
        leaderTrainersProcessor.process(gyms);
    }

    private List<City> buildCities() {
        Gym pewterGym = gym().warpId(3).name(PEWTER_GYM).leader(Brock).pokemonRangeLevel(new Integer[]{12, 14}).build(); //.trainers(getTrainers(PEWTER_GYM))
        Gym ceruleanGym = gym().warpId(4).name(CERULEAN_GYM).leader(Misty).pokemonRangeLevel(new Integer[]{18, 21}).build(); //trainers(getTrainers(CERULEAN_GYM))
        Gym vermilionGym = gym().warpId(4).name(VERMILION_GYM).leader(LtSurge).pokemonRangeLevel(new Integer[]{18, 24}).build(); //trainers(getTrainers(VERMILION_GYM))
        Gym celadonGym = gym().warpId(7).name(CELADON_GYM).leader(Erika).pokemonRangeLevel(new Integer[]{24, 29}).build(); //trainers(getTrainers(CELADON_GYM))
        Gym fuchsiaGym = gym().warpId(6).name(FUCHSIA_GYM).leader(Koga).pokemonRangeLevel(new Integer[]{37, 43}).build(); //trainers(getTrainers(FUCHSIA_GYM))
        Gym saffronGym = gym().warpId(3).name(SAFFRON_GYM).leader(Sabrina).pokemonRangeLevel(new Integer[]{37, 43}).build(); //trainers(getTrainers(SAFFRON_GYM))
        Gym cinnarbarGym = gym().warpId(2).name(CINNABAR_GYM).leader(Blaine).pokemonRangeLevel(new Integer[]{40, 47}).build(); //trainers(getTrainers(CINNABAR_GYM))
        Gym viridianGym = gym().warpId(5).name(VIRIDIAN_GYM).leader(Giovanni).pokemonRangeLevel(new Integer[]{42, 50}).build(); //trainers(getTrainers(VIRIDIAN_GYM))

        return asList(
                city().name(PewterCity).gym(pewterGym).build(),
                city().name(CeruleanCity).gym(ceruleanGym).build(),
                city().name(VermilionCity).gym(vermilionGym).build(),
                city().name(CeladonCity).gym(celadonGym).build(),
                city().name(FuchsiaCity).gym(fuchsiaGym).build(),
                city().name(SaffronCity).gym(saffronGym).build(),
                city().name(CinnabarIsland).gym(cinnarbarGym).build(),
                city().name(ViridianCity).gym(viridianGym).build());
    }
}
