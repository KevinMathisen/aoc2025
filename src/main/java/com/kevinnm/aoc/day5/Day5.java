package com.kevinnm.aoc.day5;

import java.util.ArrayList;
import java.util.Arrays;

import com.kevinnm.aoc.Day;

public class Day5 implements Day {
    long[][] freshIds;
    ArrayList<long[]> freshIds2;

    @Override
    public int part1(String[] input) {
        int i = 0;
        while (!input[i].isEmpty()) { i++; }
        String[] freshIdsRaw = Arrays.copyOfRange(input, 0, i);
        String[] idsToCheckRaw = Arrays.copyOfRange(input, i+1, input.length);

        freshIds = new long[freshIdsRaw.length][2];
        long[] idsToCheck = new long[idsToCheckRaw.length];

        for (i = 0; i < freshIdsRaw.length; i++) {
            String[] splitIds = freshIdsRaw[i].split("-");
            freshIds[i][0] = Long.parseLong(splitIds[0]);
            freshIds[i][1] = Long.parseLong(splitIds[1]);
        }
        for (i = 0; i < idsToCheckRaw.length; i++) {
            idsToCheck[i] = Long.parseLong(idsToCheckRaw[i]);
        }

        int freshCount = 0;
        for (long id : idsToCheck) {
            if (isIdFresh(id)) freshCount++;
        }

        return freshCount;
    }

    private boolean isIdFresh(long id) {
        for (long[] range : freshIds) {
            if (id >= range[0] && id <= range[1]) return true;
        }
        return false;
    }
    
    @Override
    public int part2(String[] input) {
        // sort all fresh id ranges by their start value
        freshIds2 = new ArrayList<>(Arrays.asList(freshIds));
        freshIds2.sort((r1, r2) -> Long.compare(r1[0],r2[0]));

        // merge each range with subsequent ranges such that
        //   the next range's start should be larger than current end
        int curIdx = 0;
        while (curIdx < freshIds2.size()-1) {

            // need to keep merging as long as current end is larger than next's start
            while (curIdx < freshIds2.size()-1 && freshIds2.get(curIdx)[1] >= freshIds2.get(curIdx+1)[0]) {
                long[] curRange = freshIds2.get(curIdx);

                // if range to merge with has larger end, set this
                if (freshIds2.get(curIdx+1)[1] > curRange[1]) {
                    curRange[1] = freshIds2.get(curIdx+1)[1];
                    freshIds2.set(curIdx, curRange);
                }
                // remove range we merged with
                freshIds2.remove(curIdx+1);
            }
            curIdx++;
        }

        // done with merge; have ranges with no overlap
        long totalIds = 0;
        for (long[] range : freshIds2) {
            totalIds += range[1] - range[0] + 1;   
        }

        System.out.println("day 5 part 2: " + totalIds);
        return 0;
    }
}
