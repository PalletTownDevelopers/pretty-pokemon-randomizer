package com.likeageek.randomizer;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Town {
    private String name;
    private String arena;
}
