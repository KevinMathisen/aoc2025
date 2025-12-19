package com.kevinnm.aoc.day4;

import com.kevinnm.aoc.Day;

public class Day4 implements Day {    
    private char[][] map;
    int max_x, max_y;

    @Override
    public int part1(String[] input) {
        max_x = input[0].length(); 
        max_y = input.length;
        map = new char[max_y][max_x];

        for (int i = 0; i < input.length; i++) {
            map[i] = input[i].toCharArray();
        }

        int total = 0;
        for (int y = 0; y < max_y; y++) {
            for (int x = 0; x < max_x; x++) {
                if (map[y][x] != '@') continue;

                if (rollCanBeAccessed(x, y)) total++;
            }
        }

        return total;
    }

    private boolean rollCanBeAccessed(int x, int y) {
        int neighbourRolls = 0;
        for (int d_y = -1; d_y <= 1; d_y++) {
            for (int d_x = -1; d_x <= 1; d_x++) {
                if (d_y == 0 && d_x == 0) continue; // ignore self

                if (isRoll(x+d_x, y+d_y)) neighbourRolls++;
            }
        }
        return neighbourRolls < 4;
    }

    private boolean isRoll(int x, int y) {
        if (x < 0 || x >= max_x) return false;
        if (y < 0 || y >= max_y) return false;
        return map[y][x] == '@';
    }

    @Override
    public int part2(String[] input) {
        int total_removed = 0, iteration_removed = -1;

        while (iteration_removed != 0) {
            iteration_removed = 0;

            for (int y = 0; y < max_y; y++) {
                for (int x = 0; x < max_x; x++) {
                    if (map[y][x] != '@') continue;

                    if (rollCanBeAccessed(x, y)) {
                        iteration_removed++;
                        map[y][x] = 'x';
                    }
                }
            }
            total_removed += iteration_removed;
        }

        return total_removed;
    }

}
