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

        System.out.println("----- Fresh ids -----");
        for (long[] range : freshIds2) {
            System.out.println(Long.toString(range[0]) + " - " + Long.toString(range[1]));
        }

        // merge each range with subsequent ranges such that
        //   the next range's start should be larger than current end
        int curIdx = 0;
        while (curIdx < freshIds2.size()-1) {

            // need to keep merging as long as current end is larger than next's start
            while (curIdx < freshIds2.size()-1 && freshIds2.get(curIdx)[1] > freshIds2.get(curIdx+1)[0]) {
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

        System.out.println("----- Fresh ids -----");
        for (long[] range : freshIds2) {
            System.out.println(Long.toString(range[0]) + " - " + Long.toString(range[1]));
        }

        // done with merge; have ranges with no overlap
        // go through each range and add to total ids
        long totalIds = 0;
        for (long[] range : freshIds2) {
            totalIds += range[1] - range[0] + 1;   
        }

        System.out.println("day 5 part 2: " + totalIds);
        return 0;
    }

    private void insertRangeIntoMerged(long[] rangeToInsert) {
        long startInsert = rangeToInsert[0], endInsert = rangeToInsert[1];

        int startInsertIndex = 0, endInsertIndex = 0;
        while (startInsertIndex < freshIds2.size() 
            && startInsert > freshIds2.get(startInsertIndex)[0]
        ) startInsertIndex++; 

        boolean isStartInsidePrevRange = (startInsertIndex > 0) && 
                    startInsert <= freshIds2.get(startInsertIndex-1)[1];
        if (isStartInsidePrevRange) startInsertIndex--;

        while (endInsertIndex < freshIds2.size() 
            && endInsert > freshIds2.get(endInsertIndex)[1]
        ) endInsertIndex++; 

        // e.g. 
        // [1, 10], [12, 16]
        // insert [12,14]
        //          -> [1, 10], [12, 16]

        // [1, 10], [12, 16]
        // insert [12,18]
        //          -> [1, 10], [12, 18]

        // [5, 6], [8, 20], [21, 40]
        // insert [10, 30]
        //          -> [5, 6], [8, 40]

        // [5, 6], [8, 20], [21, 40]
        // insert [10, 50]
        //          -> [5, 6], [8, 50]

        // [5, 6], [8, 20], [21, 40]
        // insert [45, 45]
        //          -> [5, 6], [8, 20], [21, 40], [45, 45]

        // need to find where to insert start, i.e. are we inside another range? between?
        //  after this, we need to find where to insert end. 

        // if any ranges between: remove
        // if start inside, end not, update the one start is inside if end is larger
        
        for (int i = 0; i < freshIds2.size(); i++) {
            long[] existingRange = freshIds2.get(i);
            
            if (startInsert > existingRange[1]) continue; // go to next range if our range is larger than whole

            // we know we want to insert our range somewhere here. 

            // check if we are smaller than start of this range as well:
            boolean startInsertSmaller = startInsert < existingRange[0];

            // need to check where our end is. 
            for (int j = i; j < freshIds2.size(); j++) {
                continue;
            }


            //  else, need to check how large the end is. 
            //    if we end up being smaller than start, 
            //          then we know we can set 1. start is insertstart, 2. end is insertend
            //      and remove all ranges from i, (j-i) times
            //          
            //    if we end up being larger than a start, but smaller than end,
            //          then we can update current to 1. start is insertstart, 2. end is end of where we ended up
            
        }

        // no place to insert; insert at end
        freshIds2.add(rangeToInsert);
    }
}
