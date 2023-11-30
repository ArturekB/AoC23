import java.util.stream.Stream;

public class Day0 extends Day {

    public static void main(String[] args) {
        Day0 day = new Day0();  // https://adventofcode.com/2023/day/0

        String sample = readFile("%s_sample.txt".formatted(day.name()));
        String full = readFile("%s.txt".formatted(day.name()));

        assertEquals(0, day.part1(sample));
        assertEquals(0, day.part1(full));

        assertEquals(0, day.part2(sample));
        assertEquals(0, day.part2(full));

        day.run(full, day::part1, "Part 1 result");
        day.run(full, day::part2, "Part 2 result");
    }


    @Override
    String streamPart1(Stream<String> input) {
        return "0";
    }

    @Override
    String streamPart2(Stream<String> input) {
        return "0";
    }

}
