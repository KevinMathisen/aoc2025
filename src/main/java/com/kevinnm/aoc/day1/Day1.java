package com.kevinnm.aoc.day1;

import java.util.ArrayList;

import com.kevinnm.aoc.Day;

record Instruction(Character direction, int distance) {}

public class Day1 implements Day {
    @Override
    public int part1(String[] input) {
        ArrayList<Instruction> instructions = new ArrayList<>();

        for (String line : input) {
            instructions.add(new Instruction(
                line.charAt(0), 
                Integer.parseInt(line.substring(1))
            ));
        }

        int dial = 50;
        int timesAtZero = 0;
        for (Instruction ins : instructions) {
            int direction = ins.direction() == 'R' ? 1 : -1;
            int delta = direction * ins.distance();
            dial = (dial + delta) % 100;
            if (dial == 0) timesAtZero++;
        }

        return timesAtZero;
    }

    // @Override
    public int part2(String[] input) {
        ArrayList<Instruction> instructions = new ArrayList<>();

        for (String line : input) {
            instructions.add(new Instruction(
                line.charAt(0), 
                Integer.parseInt(line.substring(1))
            ));
        }

        int dial = 50;
        int timesAtZero = 0;
        for (Instruction ins : instructions) {
            int direction = ins.direction() == 'R' ? 1 : -1;
            for (int i = 0; i < ins.distance(); i++) {
                dial += direction;
                if (dial == 100) dial = 0;
                else if (dial == -1) dial = 99;

                if (dial == 0) timesAtZero++;
            }
        }

        return timesAtZero;
    }
}
