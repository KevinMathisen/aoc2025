package com.kevinnm.aoc.day2;

import com.kevinnm.aoc.Day;

public class Day2 implements Day {
    private boolean isNumberRepeated(long n) {
        char[] numAsChars = Long.toString(n).toCharArray();

        int charsLength = numAsChars.length;
        if (charsLength % 2 != 0) return false;

        int charsLengthHalf = charsLength/2;
        for (int i = 0; i < charsLengthHalf; i++) {
            if (numAsChars[i] != numAsChars[charsLengthHalf + i]) return false;
        }

        return true;
    }
    
    @Override
    public int part1(String[] input) {
        String[] rangesRaw = input[0].split(",");
        long[][] ranges = new long[rangesRaw.length][2];

        for (int i = 0; i < rangesRaw.length; i++) {
            String[] startStop = rangesRaw[i].split("-");
            ranges[i][0] = Long.parseLong(startStop[0]);
            ranges[i][1] = Long.parseLong(startStop[1]);
        }

        long total = 0;
        // go through each range, and each number in each range
        for (long[] range : ranges) {
            for (long i = range[0]; i <= range[1]; i++) {
                if (isNumberRepeated(i)) total += i;
            }
        }

        System.out.println("day 1 as long: " + total);
        return 0;
    }

    @Override
    public int part2(String[] input) {
        String[] rangesRaw = input[0].split(",");
        long[][] ranges = new long[rangesRaw.length][2];

        for (int i = 0; i < rangesRaw.length; i++) {
            String[] startStop = rangesRaw[i].split("-");
            ranges[i][0] = Long.parseLong(startStop[0]);
            ranges[i][1] = Long.parseLong(startStop[1]);
        }

        long total = 0;
        // go through each range, and each number in each range
        for (long[] range : ranges) {
            for (long i = range[0]; i <= range[1]; i++) {
                if (isNumberRepeated2(i)) total += i;
            }
        }

        System.out.println("day 2 as long: " + total);
        return 0;
    }

    private boolean isNumberRepeated2(long n) {
        char[] numAsChars = Long.toString(n).toCharArray();

        int charsLength = numAsChars.length;

        if (charsLength == 1) return false;

        // go through each possible way to divide, from dividing in 2 up to length
        for (int i = 2; i <= charsLength; i++) {
            if (isNumberRepeatedForSequences(numAsChars, i)) return true;
        }
        return false;
    }

    private boolean isNumberRepeatedForSequences(char[] numAsChars, int sequences) {
        int charsLength = numAsChars.length;
        
        // check if we are able to split up number by this amount of sequences
        if (charsLength % sequences != 0) return false;

        // if we are able to split up, we need to check if all of these 
        //  sequences of digits are equal

        int sequenceLength = (int) (charsLength/sequences);

        for (int i = 0; i < sequenceLength; i++) {
            char charInFirstSequence = numAsChars[i];
            for (int j = 1; j < sequences; j++) {
                if (numAsChars[j*sequenceLength + i] != charInFirstSequence) return false;
            }
        }
        return true;        
    }
}
