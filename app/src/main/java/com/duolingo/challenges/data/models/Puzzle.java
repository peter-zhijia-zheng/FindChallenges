package com.duolingo.challenges.data.models;

import java.util.HashMap;
import java.util.List;

public class Puzzle {
    public String source_language;
    public String word;
    public HashMap<String, String> word_locations;
    public String target_language;
    public List<List<String>> character_grid;
}
