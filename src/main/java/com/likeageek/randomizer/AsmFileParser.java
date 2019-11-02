package com.likeageek.randomizer;

import static java.lang.String.join;

public class AsmFileParser implements IFileParser {

    public String editLine(String line, String value, int position) {
        String[] gymLineElements = line.split(",");
        gymLineElements[position - 1] = " ".concat(value);
        return join(",", gymLineElements);
    }
}
