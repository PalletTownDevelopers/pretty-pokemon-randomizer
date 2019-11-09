package com.likeageek.randomizer;

import java.util.List;

public interface IRandomEngine {

    List<Object> random(List<Object> cities);

    Object randomBetweenRangeValues(List<Integer> asList);
}
