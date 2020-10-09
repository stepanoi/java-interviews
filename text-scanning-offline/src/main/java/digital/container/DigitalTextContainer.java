package digital.container;

import digital.model.Block;
import digital.processor.AbstractDigitalTextProcessor;
import digital.processor.DigitalTextProcessor;
import digital.processor.VariableDigitalTextProcessor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;


public class DigitalTextContainer implements Container<Block> {
    public final static int minHeight = 3;
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private final String[] lines;
    private final int width;
    private final boolean variableMode;
    private final AbstractDigitalTextProcessor digitalTextProcessor;
    private final List<String> results;

    public DigitalTextContainer(final String input, final int width, final boolean variableMode) {
        this.lines = input.split(LINE_SEPARATOR);
        this.width = width;
        this.variableMode = variableMode;
        this.digitalTextProcessor = variableMode? new VariableDigitalTextProcessor() : new DigitalTextProcessor();
        this.results = new ArrayList<>();
    }

    @Override
    public Iterator<Block> iterator() {
        return new DigitalTextIterator();
    }

    @Override
    public void process(final Block block) {
        results.add(digitalTextProcessor.processLine(block, width));
    }

    @Override
    public String getActualDigits() {
        return String.join(LINE_SEPARATOR, results);
    }

    private class DigitalTextIterator implements Iterator<Block> {
        private int start;
        private int end;
        private boolean initialised;

        public DigitalTextIterator() {
            this.start = newStartLine(lines, 0, lines.length);
            this.end = newLinePos(lines, 0);
            this.initialised = false;
        }

        @Override
        public boolean hasNext() {
            return !initialised || end != lines.length;
        }

        @Override
        public Block next() {
            if (end - start < minHeight) {
                throw new IllegalStateException("Input needs to have at least " + minHeight + " lines");
            }
            
            if(!variableMode && end - start != minHeight) {
                throw new IllegalStateException("Input needs to be exactly " + minHeight + " lines");
            }

            if (!initialised) {
                this.initialised = true;
                return Block.builder()
                            .lines(lines)
                            .start(start)
                            .end(end)
                            .build();
            }
            
            int newLinePos = newLinePos(lines, end);
            start = newStartLine(lines, end + 1, newLinePos);
            end = newLinePos;

            return Block.builder()
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
}
