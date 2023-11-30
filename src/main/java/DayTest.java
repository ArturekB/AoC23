
import java.util.function.Consumer;
import java.util.stream.Stream;

public class DayTest extends Day {

    public static void main(String[] args) {
        DayTest day = new DayTest();  // https://adventofcode.com/2023/day/0
//        Stream<String> sample = readLines("%s_sample.txt".formatted(day.name()));
//
//        assertEquals(0, day.streamPart1(sample));
//        assertEquals(0, day.streamPart2(sample));

        day.run(readLines("%s.txt".formatted(day.name())), day::streamPart1, "Part 1 result");

        day.run(readLines("%s.txt".formatted(day.name())), day::streamPart2, "Part 2 result");
    }


    @Override
    public String streamPart1(Stream<String> input) {
        var consumer = new MaxValue();
        input.forEach(consumer);
        return consumer.maxValue();
    }

    private static class MaxValue implements Consumer<String> {
        int maxValue = 0;
        int current = 0;
        @Override
        public void accept(String line) {
            if (line.isEmpty()) {
                if (current > maxValue) {
                    maxValue = current;
                }
                current = 0;
            } else {
                current += Integer.parseInt(line);
            }
        }

        String maxValue() {
            return String.valueOf(maxValue);
        }
    }

    @Override
    public String streamPart2(Stream<String> input) {
        var consumer = new Top3List();
        input.forEach(consumer);
        return consumer.total();
    }

    private static class Top3List implements Consumer<String> {

        int top1 = 0;
        int top2 = 0;
        int top3 = 0;
        int current = 0;

        public void update(int topValue) {
            if (topValue < top2) {
                top3 = topValue;
            } else if (topValue < top1) {
                top3 = top2;
                top2 = topValue;
            } else {
                top3 = top2;
                top2 = top1;
                top1 = topValue;
            }
        }

        public String total() {
            return String.valueOf(top1 + top2 + top3);
        }

        public void accept(String line) {
            if (line.isEmpty()) {
                if (current > top3) {
                    update(current);
                }
                current = 0;
            } else {
                current += Integer.parseInt(line);
            }
        }
    }
}
