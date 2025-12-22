package com.kevinnm.aoc.day7;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.kevinnm.aoc.Day;

public class Day7 implements Day {
    @Override
    public int part1(String[] input) {
        int startPos = input[0].indexOf("S");
        int tachyonWidth = input[0].length();

        Set<Integer> prevBeams = new HashSet<>();
        prevBeams.add(startPos);
        
        Set<Integer> curBeams = new HashSet<>();

        int splitCount = 0;
        // for each line, we need to check for each prev beam if they collide with a splitter
        //  if so, add two new beams beside it, else keep it as current path/index
        for (int i = 1; i < input.length; i++) { 
            curBeams.clear();
            String curLine = input[i];
            for (Integer beamLocation : prevBeams) {
                if (curLine.charAt(beamLocation) == '^') {
                    curBeams.add(beamLocation-1);
                    curBeams.add(beamLocation+1);
                    splitCount++;
                } else {
                    curBeams.add(beamLocation);
                }
            }
            prevBeams.clear();
            prevBeams.addAll(curBeams);
        }

        return splitCount;
    }

    @Override
    public int part2(String[] input) {
        int startPos = input[0].indexOf("S");

        Map<Integer, Long> prevBeams = new HashMap<>();
        prevBeams.put(startPos, 1l);
        
        Map<Integer, Long> curBeams = new HashMap<>();

        // for each line, we need to check for each prev beam if they collide with a splitter
        //  if so, add two new beams beside it, else keep it as current path/index
        for (int i = 1; i < input.length; i++) { 
            curBeams.clear();
            String curLine = input[i];
            for (Map.Entry<Integer, Long> entry : prevBeams.entrySet()) {
                Integer beamLocation = entry.getKey();
                if (curLine.charAt(beamLocation) == '^') {
                    curBeams.merge(beamLocation-1, entry.getValue(), Long::sum);
                    curBeams.merge(beamLocation+1, entry.getValue(), Long::sum);
                } else {
                    curBeams.merge(beamLocation, entry.getValue(), Long::sum);
                }
            }
            prevBeams.clear();
            prevBeams.putAll(curBeams);
        }

        Long sum = curBeams.values().stream().mapToLong(Long::longValue).sum();
        System.out.println("day 7 part 2: " + sum);
        return 0;
    }
}
