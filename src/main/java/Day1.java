
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class Day1 extends Day {

    public static void main(String[] args) {
        Day1 day = new Day1();  // https://adventofcode.com/2023/day/0
        Stream<String> sample = readLines("%s_sample.txt".formatted(day.name()));

        //assertEquals(142, day.streamPart1(sample));
        assertEquals(281, day.streamPart2(sample));

        day.run(readLines("%s.txt".formatted(day.name())), day::streamPart1, "Part 1 result");

        day.run(readLines("%s.txt".formatted(day.name())), day::streamPart2, "Part 2 result");
    }


    @Override
    public String streamPart1(Stream<String> input) {
        Calibrator calibrator = new Calibrator();
        input.forEach(calibrator);
        return calibrator.total();
    }


    @Override
    public String streamPart2(Stream<String> input) {
        Calibrator2 calibrator = new Calibrator2();
        input.forEach(calibrator);
        return calibrator.total();
    }

    private static class Calibrator implements Consumer<String> {

        int total = 0;

        @Override
        public void accept(String line) {
            String numbers = line.replaceAll("[^0-9]", "");
            total += Integer.valueOf(
                numbers.substring(0, 1) + numbers.substring(numbers.length() - 1, numbers.length()));
        }

        String total() {
            return String.valueOf(total);
        }
    }

    private class Calibrator2 implements Consumer<String> {

        int total = 0;
        private static String[] digitWords = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
        private static Map<String, String> replacementsMap = Map.of("one","1",
            "two","2", "three", "3", "four", "4", "five","5", "six","6", "seven","7", "eight","8", "nine","9");

        String total() {
            return String.valueOf(total);
        }

        @Override
        public void accept(String line) {
            Map<String, Integer> firstIndexes = new HashMap<>();
            Map<String, Integer> lastIndexes = new HashMap<>();

            for (String word: digitWords) {
                firstIndexes.put(word, line.indexOf(word));
                lastIndexes.put(word, line.lastIndexOf(word));
            }
            int firstIndex = firstIndexes.values().stream()
                .mapToInt(Integer::intValue)
                .filter(i -> i!=-1)
                .min().orElse(-1);
            int lastIndex = lastIndexes.values().stream().max(Integer::compareTo)
                .get();
            String endline = "";
            if (lastIndex != -1 && lastIndex > firstIndex) {
                String lastReplacement = lastIndexes.entrySet().stream()
                    .filter(entry -> entry.getValue() == lastIndex)
                    .map(entry -> entry.getKey())
                    .findFirst().get();
                endline = line.substring(lastIndex);
                endline = endline.replace(lastReplacement, replacementsMap.get(lastReplacement));
            }
            if (firstIndex != -1) {
                String firstReplacement = firstIndexes.entrySet().stream()
                    .filter(entry -> entry.getValue() == firstIndex)
                    .map(entry -> entry.getKey())
                    .findFirst().get();
                line = line.replace(firstReplacement, replacementsMap.get(firstReplacement));
            }
            String numbers = line.replaceAll("[^0-9]", "");
            if (!endline.isEmpty())
                numbers = numbers + endline.replaceAll("[^0-9]", "");
            total += Integer.valueOf(
                numbers.substring(0, 1) + numbers.substring(numbers.length() - 1, numbers.length()));
        }
    }
}
