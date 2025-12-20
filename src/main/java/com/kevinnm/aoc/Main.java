package com.kevinnm.aoc;

import java.util.ArrayList;
import java.util.Scanner;

import com.kevinnm.aoc.day0.Day0;
import com.kevinnm.aoc.day1.Day1;
import com.kevinnm.aoc.day2.Day2;
import com.kevinnm.aoc.day3.Day3;
import com.kevinnm.aoc.day4.Day4;
import com.kevinnm.aoc.day5.Day5;

public class Main {
    private static final Day[] days = {
        new Day0(), new Day1(), new Day2(), new Day3(),
        new Day4(), new Day5()
    };

    public static void main(String[] args) {
        int dayNum = 5;

        String[] input = readInput(dayNum);

        Day day = days[dayNum];
        System.out.println("Day " + dayNum);
        System.out.println("Part 1: " + day.part1(input));
        System.out.println("Part 1: " + day.part2(input));
    }

    private static String[] readInput(int dayNum) {
        ArrayList<String> lines = new ArrayList<>();

        try (var in = Main.class.getResourceAsStream("/"+dayNum+".txt")) {
            if (in == null) {
                System.out.println("File not found");
                return new String[0];
            }
            try (var reader = new Scanner(in)) {
                while (reader.hasNextLine()) {
                    lines.add(reader.nextLine());
                }
            }
        } catch (Exception e) {
            System.err.println("File not found: " + dayNum + ".txt");
        }

        return lines.toArray(String[]::new);
    }
}



