
import java.util.function.Consumer;
import java.util.stream.Stream;

public class DayNewSchema extends Day {

    public static void main(String[] args) {
        DayNewSchema day = new DayNewSchema();  // https://adventofcode.com/2023/day/0
        Stream<String> sample = readLines("%s_sample.txt".formatted("Day0"));

        assertEquals(0, day.streamPart1(sample));
        assertEquals(0, day.streamPart2(sample));

        day.run(readLines("%s.txt".formatted("Day0")), day::streamPart1, "Part 1 result");

        day.run(readLines("%s.txt".formatted("Day0")), day::streamPart2, "Part 2 result");
    }


    @Override
    public String streamPart1(Stream<String> input) {
        input.forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {

            }
        });
        return "0";
    }

    @Override
    public String streamPart2(Stream<String> input) {
        input.forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {

            }
        });
        return "0";
    }
}
