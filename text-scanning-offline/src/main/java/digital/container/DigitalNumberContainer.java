package digital.container;

import digital.model.Number;
import digital.processor.AbstractDigitalNumberProcessor;
import digital.processor.DigitalNumberProcessor;
import digital.processor.VariableDigitalNumberProcessor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;


public class DigitalNumberContainer {
    public final static int MIN_HEIGHT = 3;
    public final static int MIN_WIDTH = 3;
    public final static int MAX_DIGITS_PER_LINE = 9;
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private final String[] lines;
    private final int width;
    private final boolean variableMode;
    private final AbstractDigitalNumberProcessor digitalTextProcessor;
    private final List<String> results;

    public DigitalNumberContainer(final String input, final int width, final boolean variableMode) {
        this.lines = input.split(LINE_SEPARATOR);
        this.width = width;
        this.variableMode = variableMode;
        this.digitalTextProcessor = variableMode ? new VariableDigitalNumberProcessor() : new DigitalNumberProcessor();
        this.results = new ArrayList<>();
    }

    public Iterator<Number> iterator() {
        return new DigitalNumberIterator();
    }

    public void process(final Number number) {
        results.add(digitalTextProcessor.process(number, width));
    }

    public String getActualDigits() {
        return String.join(LINE_SEPARATOR, results);
    }

    private class DigitalNumberIterator implements Iterator<Number> {
        private int start;
        private int end;
        private boolean initialised;

        public DigitalNumberIterator() {
            this.start = newStartLine(lines, 0, lines.length);
            this.end = newLinePos(lines, 0);
            this.initialised = false;
        }

        @Override
        public boolean hasNext() {
            return !initialised || end != lines.length;
        }

        @Override
        public Number next() {
            if (end - start < MIN_HEIGHT) {
                throw new IllegalStateException("Input needs to have at least " + MIN_HEIGHT + " lines");
            }

            if (!variableMode && end - start != MIN_HEIGHT) {
                throw new IllegalStateException("Input needs to be exactly " + MIN_HEIGHT + " lines");
            }

            if (!initialised) {
                this.initialised = true;
                return Number.builder()
                             .lines(lines)
                             .start(start)
                             .end(end)
                             .build();
            }

            int newLinePos = newLinePos(lines, end);
            start = newStartLine(lines, end + 1, newLinePos);
            end = newLinePos;

            return Number.builder()
                         .lines(lines)
                         .start(start)
                         .end(end)
                         .build();
        }

        private int newLinePos(String[] lines, int previousEnd) {
            if (previousEnd == lines.length) {
                return previousEnd;
            }

            int newLinePos = IntStream.range(previousEnd + 1, lines.length)
                                      .limit(lines.length)
                                      .filter(value -> lines[value].trim().isEmpty())
                                      .findFirst()
                                      .orElse(lines.length);

            if (newLinePos - previousEnd < MIN_HEIGHT) {
                return newLinePos(lines, newLinePos);
            }

            return newLinePos;
        }

        private int newStartLine(final String[] lines, int previousStart, int newLinePos) {
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
}
