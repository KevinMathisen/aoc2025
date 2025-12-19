package com.kevinnm.aoc.day3;

import com.kevinnm.aoc.Day;

public class Day3 implements Day {    
    @Override
    public int part1(String[] input) {
        int total = 0;

        for (String bankRaw : input) {
            char[] bankChars = bankRaw.toCharArray();
            int[] bankInts = new int[bankChars.length];

            for (int i = 0; i < bankChars.length; i++) {
                bankInts[i] = bankChars[i] - '0';
            }
            total += largestJoltPossible(bankInts);
        }

        return total;
    }

    private int largestJoltPossible(int[] bank) {
        int maxJolt = 0, max2Jolt = 0;

        for (int i = 0; i < bank.length; i++) {
            if (bank[i] > maxJolt && i != bank.length-1) {
                maxJolt = bank[i];
                max2Jolt = 0;
            } else if (bank[i] > max2Jolt) {
                max2Jolt = bank[i];
            }
        }
        
        return Integer.parseInt(
            Integer.toString(maxJolt)+Integer.toString(max2Jolt));
    }

    @Override
    public int part2(String[] input) {
        long total = 0;

        for (String bankRaw : input) {
            char[] bankChars = bankRaw.toCharArray();
            int[] bankInts = new int[bankChars.length];

            for (int i = 0; i < bankChars.length; i++) {
                bankInts[i] = bankChars[i] - '0';
            }
            total += largestJoltPossible2(bankInts);
        }

        System.out.println("part 2 day 3: " + total);
        return 0;
    }

    private long largestJoltPossible2(int[] bank) {
        int[] maxJolts = new int[12];
        for (int i = 0; i < 12; i++) { maxJolts[i] = 0; }

        for (int i = 0; i < bank.length; i++) {
            // for each candiate battery, we need to check if it can be used for each maxJolts slot
            // first check if enough space behind (need 12-j slots), then check if larger than previous
            // if so, we need to reset (only) next maxJolt as well, so it will be set to next battery

            for (int j = 0; j < 12; j++) {
                int remainingSlots = bank.length-i, requiredSlots = 12-j;
                if (remainingSlots < requiredSlots) continue;
                if (bank[i] > maxJolts[j]) {
                    // found new max for a battery. 
                    // need to update max, set next to 0, and stop evaluation of this battery
                    maxJolts[j] = bank[i];
                    if (j+1 < 12) maxJolts[j+1] = 0;
                    break;
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int battery : maxJolts) {
            sb.append(battery);
        }
        // System.out.println("Found largest Jolt possible: " + sb.toString());
        return Long.parseLong(sb.toString());
    }

}
