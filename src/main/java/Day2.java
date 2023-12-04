
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class Day2 extends Day {

    public static void main(String[] args) {
        Day2 day = new Day2();  // https://adventofcode.com/2023/day/0
        Stream<String> sample = readLines("%s_sample.txt".formatted(day.name()));

        //assertEquals(8, day.streamPart1(sample));
        assertEquals(2286, day.streamPart2(sample));

     //   day.run(readLines("%s.txt".formatted(day.name())), day::streamPart1, "Part 1 result");
        day.run(readLines("%s.txt".formatted(day.name())), day::streamPart2, "Part 2 result");
    }


    @Override
    public String streamPart1(Stream<String> input) {
        var gameVerifier = new GameVerifier();
        input.forEach(gameVerifier);
        return gameVerifier.total();
    }

    @Override
    public String streamPart2(Stream<String> input) {
        var powerSetsCounter = new PowerSetsCounter();
        input.forEach(powerSetsCounter);
        return powerSetsCounter.total();
    }

    private class GameVerifier implements Consumer<String> {
        private int total = 0;

        @Override
        public void accept(String line) {
            var describedGame = line.split(":");
            var gameNumber = Integer.valueOf(describedGame[0].replace("Game ", ""));
            var cubeSets = describedGame[1].split(";");
            if (Arrays.stream(cubeSets).allMatch(cubes -> doesNotExceedLimits(cubes))) {
                total += gameNumber;
            }
        }

        private boolean doesNotExceedLimits(String cubes) {
            var diceNumbers = cubes.split(",");
            return Arrays.stream(diceNumbers).allMatch(
                    ln -> {
                        ln = ln.strip();
                        return (ln.endsWith("blue") && Integer.valueOf(ln.split(" ")[0]) <= 14)
                                || (ln.endsWith("red") && Integer.valueOf(ln.split(" ")[0]) <= 12)
                                || (ln.endsWith("green") && Integer.valueOf(ln.split(" ")[0]) <= 13);
                    });
        }


        String total() {
            return String.valueOf(total);
        }
    }

    private class PowerSetsCounter implements Consumer<String> {
        private int total = 0;

        @Override
        public void accept(String line) {
            var describedGame = line.split(":");
            var cubeSets = describedGame[1].split(";");
            int[] counts = {0, 0, 0};
            for (String cubeSet : cubeSets) {
                var labeledNumbers = cubeSet.split(",");
                for (String labeledNumber : labeledNumbers) {
                    labeledNumber = labeledNumber.strip();
                    if (labeledNumber.endsWith("blue")) {
                        int value = Integer.valueOf(labeledNumber.split(" ")[0]);
                        if (value > counts[0]) {
                            counts[0] = value;
                        }
                    }
                    if (labeledNumber.endsWith("red")) {
                        int value = Integer.valueOf(labeledNumber.split(" ")[0]);
                        if (value > counts[1]) {
                            counts[1] = value;
                        }
                    }
                    if (labeledNumber.endsWith("green")) {
                        int value = Integer.valueOf(labeledNumber.split(" ")[0]);
                        if (value > counts[2]) {
                            counts[2] = value;
                        }
                    }
                }
            }
            total += counts[0] * counts[1] * counts[2];
        }

        String total() {
            return String.valueOf(total);
        }
    }
}
