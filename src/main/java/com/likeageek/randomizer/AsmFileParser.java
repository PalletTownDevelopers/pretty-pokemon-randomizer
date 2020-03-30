package com.likeageek.randomizer;

import static java.lang.String.join;

public class AsmFileParser implements IFileParser {

    public String editLine(String line, String value, int position) {
        String[] gymLineElements = line.split(",");
        String gymLineElement = gymLineElements[position - 1];
        String prefix = "";
        if (gymLineElement.contains("db")) {
            prefix = "db";
        }
        gymLineElements[position - 1] = prefix.concat(" ").concat(value);
        return join(",", gymLineElements);
    }
}
