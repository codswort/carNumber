package com.example.carnumber;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/number")
public class CarNumberController {
    private static final char[] LETTERS = {'А', 'В', 'Е', 'К', 'М', 'Н', 'О', 'Р', 'С', 'Т', 'У', 'Х'};
    private static final String REGION = "116 RUS";
    private static final String startNumber = "А000АА"; //C400BA 116 RUS
    private static String current;
    private static final Set<String> givenNumbers = new HashSet<>();

    @GetMapping("/random")
    public String random() {
        do {
            int n = (int) (Math.random() * 1000);
            char l1 = LETTERS[(int) (Math.random() * LETTERS.length)];
            char l2 = LETTERS[(int) (Math.random() * LETTERS.length)];
            char l3 = LETTERS[(int) (Math.random() * LETTERS.length)];
            current = String.format("%c%03d%c%c", l1, n, l2, l3);
        } while (givenNumbers.contains(current));
        givenNumbers.add(current);
        return current + " " + REGION;
    }

    @GetMapping("/next")
    public String next() {
        int maxIterations = LETTERS.length * LETTERS.length * LETTERS.length * 1000;
        int count = 0;
        if (givenNumbers.isEmpty()) {
            givenNumbers.add(startNumber + " " + REGION);
            current = startNumber;
            return startNumber + " " + REGION;
        }

        do {
            current = numberIncrement(current);
            if (current == null) return "следующего номера не существует!";
            count++;
            if (count > maxIterations) return "все номера выданы!";
        } while (givenNumbers.contains(current));
        givenNumbers.add(current);
        return current + " " + REGION;
    }
    static String numberIncrement(String current) {
        int[] indexes = calculateIndexes(current);
        int indL1 = indexes[0], indL2 = indexes[1], indL3 = indexes[2];
        int l1 = current.charAt(0);//A
        int d1 = current.charAt(1);//0
        int d2 = current.charAt(2);//0
        int d3 = current.charAt(3);//0
        int l2 = current.charAt(4);//A
        int l3 = current.charAt(5);//A

        if (d3 < '9') {
            d3++;
            return buildNumber(l1, d1, d2, d3, l2, l3);
        }
        d3 = '0';
        if (d2 < '9') {
            d2++;
            return buildNumber(l1, d1, d2, d3, l2, l3);
        }
        d2 = '0';
        if (d1 < '9') {
            d1++;
            return buildNumber(l1, d1, d2, d3, l2, l3);
        }
        d1 = '0';
        if (indL3 < LETTERS.length-1) {
            l3 = LETTERS[++indL3];
            return buildNumber(l1, d1, d2, d3, l2, l3);
        }
        l3 = LETTERS[0];
        if (indL2 < LETTERS.length-1) {
            l2 = LETTERS[++indL2];
            return buildNumber(l1, d1, d2, d3, l2, l3);
        }
        l2 = LETTERS[0];
        if (indL1 < LETTERS.length-1) {
            l1 = LETTERS[++indL1];
            return buildNumber(l1, d1, d2, d3, l2, l3);
        }
        return null;
    }
    private static int[] calculateIndexes(String current) {
        int[] result = new int[3];
        for (int i = 0; i < LETTERS.length; i++) {
            if (LETTERS[i] == current.charAt(0)) result[0] = i;
            if (LETTERS[i] == current.charAt(4)) result[1] = i;
            if (LETTERS[i] == current.charAt(5)) result[2] = i;
        }
        return result;
    }
    private static String buildNumber(int l1, int d1, int d2, int d3, int l2, int l3) {
        return new StringBuilder()
                .append((char) l1)
                .append((char) d1)
                .append((char) d2)
                .append((char) d3)
                .append((char) l2)
                .append((char) l3)
                .toString();
    }
}