package com.kevinnm.aoc.day8;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.kevinnm.aoc.Day;

public class Day8 implements Day {
    @Override
    public int part1(String[] input) {
        int[][] junctions = new int[input.length][3];
        ArrayList<int[]> distances = new ArrayList<>();

        for (int i = 0; i < input.length; i++) {
            String[] nums = input[i].split(",");
            for (int j = 0; j < 3; j++) {
                junctions[i][j] = Integer.parseInt(nums[j]);
            }
        }

        // for each junction, save its distance to all other junctions
        //  as array, with [a, b, distance]
        //      (ensure we shrink how many junctions we compare with as we iterate)
        //          to not cause duplicates [a, b, dis] and [b, a, dis]
        for (int i = 0; i < junctions.length; i++) {
            for (int j = i+1; j < junctions.length; j++) {
                int distance = euclidDistance(junctions[i], junctions[j]);
                distances.add(new int[]{i, j, distance});
            }
        }
        
        // sort array on distance
        distances.sort((d1, d2) -> Integer.compare(d1[2], d2[2]));

        // store each circuit (arraylist with sets)
        //  need to know which boxes are in each circuit
        //      when two junctions connect, we merge their entire circuits
        // to find which circuit we are in, iterate through arraylist, check if in each set
        ArrayList<Set<Integer>> circuits = new ArrayList<>();
        for (int i = 0; i < junctions.length; i++) {
            circuits.add(new HashSet<>(Set.of(i)));
        }

        // for 10 closest connections, merge circuits
        for (int i = 0; i < 1000; i++) {
            Integer aIdx = distances.get(i)[0];
            Integer bIdx = distances.get(i)[1];

            Set<Integer> aCir = getCircuitFromJunction(aIdx, circuits);
            Set<Integer> bCir = getCircuitFromJunction(bIdx, circuits);

            aCir.addAll(bCir);
            if (aCir != bCir) circuits.remove(bCir);
        }

        List<Integer> top3Sizes = circuits.stream()
            .map(Set::size) 
            .sorted(Comparator.reverseOrder())
            .toList(); 

        int result = 1;
        for (int i = 0; i < 3; i++) {
            result *= top3Sizes.get(i);
        }
        return result;
    }

    private int euclidDistance(int[] a, int[] b) {
        double innerSum = 0;
        for (int i = 0; i < 3; i++) {
            innerSum+=Math.pow((a[i]-b[i]), 2);
        }
        return (int) Math.round(Math.sqrt(innerSum));
    }

    private Set<Integer> getCircuitFromJunction(int a, ArrayList<Set<Integer>> circuits) {
        for (Set<Integer> circuit : circuits) {
            if (circuit.contains(a)) return circuit;
        }
        return new HashSet<>();
    }

    @Override
    public int part2(String[] input) {
        int[][] junctions = new int[input.length][3];
        ArrayList<int[]> distances = new ArrayList<>();

        for (int i = 0; i < input.length; i++) {
            String[] nums = input[i].split(",");
            for (int j = 0; j < 3; j++) {
                junctions[i][j] = Integer.parseInt(nums[j]);
            }
        }

        // for each junction, save its distance to all other junctions
        //  as array, with [a, b, distance]
        for (int i = 0; i < junctions.length; i++) {
            for (int j = i+1; j < junctions.length; j++) {
                int distance = euclidDistance(junctions[i], junctions[j]);
                distances.add(new int[]{i, j, distance});
            }
        }
        
        // sort array on distance
        distances.sort((d1, d2) -> Integer.compare(d1[2], d2[2]));

        // store each circuit (arraylist with sets)
        ArrayList<Set<Integer>> circuits = new ArrayList<>();
        for (int i = 0; i < junctions.length; i++) {
            circuits.add(new HashSet<>(Set.of(i)));
        }

        // connect junctions until we have one circuit
        for (int i = 0; i < distances.size(); i++) {
            Integer aIdx = distances.get(i)[0];
            Integer bIdx = distances.get(i)[1];

            Set<Integer> aCir = getCircuitFromJunction(aIdx, circuits);
            Set<Integer> bCir = getCircuitFromJunction(bIdx, circuits);

            aCir.addAll(bCir);
            if (aCir != bCir) circuits.remove(bCir);

            if (circuits.size() == 1) {
                return junctions[aIdx][0] * junctions[bIdx][0];
            }
        }

        return -1;
    }
}
