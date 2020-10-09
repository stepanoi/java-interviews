import java.util.stream.IntStream;

public class TextScanner {
    public final static int minHeight = 3;
    public final static int minWidth = 3;
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private final TextProcessor textProcessor = new TextProcessor();

    public String scan(String input) {
        return scan(input, minWidth);
    }

    public String scan(String input, int width) {
        String[] lines = input.split(LINE_SEPARATOR);
        StringBuilder result = new StringBuilder();
        int start = newStartLine(lines, 0, lines.length);
        int end = newLinePos(lines, 0);
        boolean newLineDetected = true;

        do {
            if (end - start < minHeight) {
                throw new IllegalStateException("Input needs to have at least " + minHeight + " lines");
            }

            result.append(textProcessor.processLine(lines, start, end, width));

            int newLinePos = newLinePos(lines, end);

            if (newLinePos != end) {
                start = newStartLine(lines, end + 1, newLinePos);
                end = newLinePos;
                result.append(LINE_SEPARATOR);
            } else {
                newLineDetected = false;
            }

        }
        while (newLineDetected);

        return result.toString();
    }

    private int newLinePos(String[] lines, int previousEnd) {
        if(previousEnd == lines.length) {
            return previousEnd;
        }
        
        int newLinePos = IntStream.range(previousEnd + 1, lines.length)
                                  .limit(lines.length)
                                  .filter(value -> lines[value].trim().isEmpty())
                                  .findFirst()
                                  .orElse(lines.length);

        if (newLinePos - previousEnd < minHeight) {
            return newLinePos(lines, newLinePos);
        }

        return newLinePos;
    }

    private int newStartLine(String[] lines, int previousStart, int newLinePos) {
        if (lines.length > 0 && lines[previousStart].trim().isEmpty()) {
            return IntStream.range(previousStart + 1, lines.length)
                            .limit(newLinePos)
                            .filter(value -> !lines[value].trim().isEmpty())
                            .findFirst()
                            .orElse(lines.length);
        } else {
            return previousStart;
        }
    }
}
