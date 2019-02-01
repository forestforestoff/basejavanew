package ru.javaops.webapp;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainStream {
    public static void main(String[] args) {
        System.out.println(minValue(new int[]{9, 8, 5, 2, 7, 8, 5, 2, 1, 3, 5, 9}));
        System.out.println(oddOrEven(Arrays.asList(3, 5, 2, 1)));
    }

    private static int minValue(int[] values) {
        return Arrays.stream(values)
                .sorted()
                .distinct()
                .reduce((s1, s2) -> s1 * 10 + s2)
                .orElse(0);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        Map<Boolean, List<Integer>> map = integers.stream().collect(Collectors.partitioningBy((p) -> p % 2 == 0));
        return integers.stream().mapToInt(Integer::intValue).sum() % 2 == 0?
                map.get(true) : map.get(false);
    }
}