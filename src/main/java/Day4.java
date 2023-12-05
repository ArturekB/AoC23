
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day4 extends Day {

    public static void main(String[] args) {
        Day4 day = new Day4();  // https://adventofcode.com/2023/day/0
        Stream<String> sample = readLines("%s_sample.txt".formatted(day.name()));

 //       assertEquals(13, day.streamPart1(sample));
        assertEquals(30, day.streamPart2(sample));
//
        day.run(readLines("%s.txt".formatted(day.name())), day::streamPart1, "Part 1 result");
//
        day.run(readLines("%s.txt".formatted(day.name())), day::streamPart2, "Part 2 result");
    }


    @Override
    public String streamPart1(Stream<String> input) {
        var consumer = new ScoreCalc();
        input.forEach(consumer);
        return consumer.total();
    }


    @Override
    public String streamPart2(Stream<String> input) {
        var consumer = new CardCopiesCounter();
        input.forEach(consumer);
        return consumer.total();
    }

    private class ScoreCalc implements Consumer<String> {

        private int total = 0;

        @Override
        public void accept(String line) {
            String[] split = line.split(":|\\|");
            var winningNumbersAsString = split[1].strip().split(" ");
            var winningNumbers = Arrays.stream(winningNumbersAsString)
                .filter(s -> !s.isEmpty())
                .map(Integer::valueOf)
                .collect(Collectors.toSet());
            var myNumbersAsString = split[2].strip().split(" ");
            var myNumbers = Arrays.stream(myNumbersAsString)
                .filter(s -> !s.isEmpty())
                .map(Integer::valueOf)
                .collect(Collectors.toSet());
            int matchesCount = myNumbers.stream().filter(winningNumbers::contains).mapToInt(x -> 1).sum();
            var score = matchesCount == 0 ? 0 : (int) Math.pow(2, matchesCount - 1);
            total += score;
        }

        String total() {
            return String.valueOf(total);
        }
    }

    private class CardCopiesCounter implements Consumer<String> {

        int total = 0;

        Map<Integer, Integer> wonCardsByNumber = new HashMap<>();

        @Override
        public void accept(String line) {
            line = line.replaceAll("   ", " ");
            line = line.replaceAll("  ", " ");
            String[] split = line.split(":|\\|");
            var cardNumber = Integer.valueOf(split[0].replace("Card ", ""));
            var winningNumbersAsString = split[1].strip().split(" ");
            var winningNumbers = Arrays.stream(winningNumbersAsString)
                .filter(s -> !s.isEmpty())
                .map(Integer::valueOf)
                .collect(Collectors.toSet());
            var myNumbersAsString = split[2].strip().split(" ");
            var myNumbers = Arrays.stream(myNumbersAsString)
                .filter(s -> !s.isEmpty())
                .map(Integer::valueOf)
                .collect(Collectors.toSet());
            int matchesCount = myNumbers.stream().filter(winningNumbers::contains).mapToInt(x -> 1).sum();
            int copiesToAdd = wonCardsByNumber.getOrDefault(cardNumber, 0) + 1;
            while (matchesCount > 0) {
                wonCardsByNumber.put(cardNumber + matchesCount,
                    wonCardsByNumber.getOrDefault(cardNumber + matchesCount, 0) + copiesToAdd);
                matchesCount--;
            }
            total += copiesToAdd;
        }

        String total() {
            return String.valueOf(total);
        }
    }
}
