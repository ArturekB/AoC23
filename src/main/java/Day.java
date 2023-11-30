import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public abstract class Day {

    String name() {
        return getClass().getSimpleName();
    }

    long run() {
        return run(() -> readLines("%s.txt".formatted(name())));
    }

    long run(String input) {
        System.out.printf("Running %s%n", getClass().getSimpleName());
        long time1 = run(input, this::part1, "Part 1 result");
        long time2 = run(input, this::part2, "Part 2 result");
        return time1 + time2;
    }

    long run(Supplier<Stream<String>> inputSupplier) {
        System.out.printf("Running %s%n", getClass().getSimpleName());

        long time1 = run(inputSupplier.get(), this::streamPart1, "Part 1 result");
        long time2 = run(inputSupplier.get(), this::streamPart2, "Part 2 result");
        return time1 + time2;
    }

    long run(Stream<String> input, Function<Stream<String>, String> function, String label) {
        long start = System.currentTimeMillis();
        String res = function.apply(input);
        long time = System.currentTimeMillis() - start;
        System.out.printf("[%d ms] %s: %s%n", time, label, res);
        return time;
    }

    long run(String input, Function<String, String> function, String label) {
        long start = System.currentTimeMillis();
        String res = function.apply(input);
        long time = System.currentTimeMillis() - start;
        System.out.printf("[%d ms] %s: %s%n", time, label, res);
        return time;
    }

    String part1(String input) {
        return "0";
    }

    abstract String streamPart1(Stream<String> input);

    String part2(String input) {
        return "1";
    }

    abstract String streamPart2(Stream<String> input);

    public static String readFile(String fileName) {

        try (InputStream is = Day.class.getResourceAsStream(fileName);
            BufferedInputStream bis = new BufferedInputStream(
                Objects.requireNonNull(is, () -> "File %s not found".formatted(fileName)))) {

            ByteArrayOutputStream buf = new ByteArrayOutputStream();
            for (int result = bis.read(); result != -1; result = bis.read()) {
                buf.write((byte) result);
            }
            return buf.toString(StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Stream<String> readLines(String fileName) {
        try {
            URL resource = Day.class.getResource(fileName);
            Path path = Paths.get(resource.toURI());
            return Files.lines(path);
        } catch (Exception e) {
            return Stream.empty();
        }
    }

    public static void assertEquals(long expected, String actual) {
        assertEquals(String.valueOf(expected), actual);
    }

    public static void assertEquals(String expected, String actual) {
        if (!Objects.equals(expected, actual)) {
            throw new AssertionError("expected: %s, actual: %s".formatted(expected, actual));
        }
    }
}
