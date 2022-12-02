package com.unhuman.adventofcode.aoc_framework;

import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Unit test for InputParser
 */
public class InputParserTest {
    @Test
    public void testSimpleFixture() {
        InputParser inputParser = new TestInputParser("src/test/resources/simpleInputFixture.txt",
                "(\\d)",1, 2, 6);
        inputParser.process();
    }

    @Test
    public void testRepeatingSimpleFixture() {
        InputParser inputParser = new TestInputParser("src/test/resources/repeatingSimpleInputFixture.txt",
                "(\\d)", 3, 1, 6);
        inputParser.process();
    }

    @Test
    public void testSimpleFixtureCommas() {
        InputParser inputParser = new TestInputParser("src/test/resources/simpleInputFixtureCommas.txt",
                "(\\d+),?", 1, 2, 6);
        inputParser.process();
    }

    @Test
    public void test2PartsSimpleFixture() {
        InputParser inputParser = new TestInputParser("src/test/resources/simpleInputFixture2Parts.txt",
                "(\\w+)\\s?", 1, 1, 3,
                "(\\d+),?",1, 1, 6);
        inputParser.process();
    }

    @Test
    public void test2PartsMultipleFixture() {
        InputParser inputParser = new TestInputParser("src/test/resources/simpleInputFixture2PartsMultiple.txt",
                "(\\w+)\\s?", 1, 2, 3,
                "(\\d+),?",2, 2, 6);
        inputParser.process();
    }

    @Test
    public void inputFixtureLineMatch() {
        InputParser inputParser = new TestInputParser("src/test/resources/inputFixtureLineMatch.txt",
                "(\\d+) (\\w+) (\\d+) (\\w+) (\\d+) (\\w+)",1, 1, 6,
                "(\\w) (\\w?)\s?(.) (\\w)", 1, 2, 4);
        inputParser.process();
    }

    public class TestInputParser extends InputParser {
        private final int expectedItems1;
        private final int expectedLines1;
        private final int expectedItemsPerLine1;
        private final int expectedItems2;
        private final int expectedLines2;
        private final int expectedItemsPerLine2;

        /**
         * Creates an InputParser that will process line-by-line
         * @param filename
         * @param lineItemRegex1
         * @param expectedItems1
         * @param expectedLines1
         * @param expectedItemsPerLine1
         */
        public TestInputParser(String filename, String lineItemRegex1,
                               int expectedItems1, int expectedLines1, int expectedItemsPerLine1) {
            this(filename, lineItemRegex1, expectedItems1, expectedLines1, expectedItemsPerLine1,
                    null, 0, 0, 0);
        }
            /**
             * Creates an InputParser that will process line-by-line
             * @param filename
             * @param lineItemRegex1
             * @param lineItemRegex2
             * @param expectedItems1
             * @param expectedLines1
             * @param expectedItemsPerLine1
             * @param expectedItems2
             * @param expectedLines2
             * @param expectedItemsPerLine2
             */
        public TestInputParser(String filename, String lineItemRegex1,
                               int expectedItems1, int expectedLines1, int expectedItemsPerLine1,
                               String lineItemRegex2,
                               int expectedItems2, int expectedLines2, int expectedItemsPerLine2) {
            super(new String[] { filename }, lineItemRegex1, lineItemRegex2);
            this.expectedItems1 = expectedItems1;
            this.expectedLines1 = expectedLines1;
            this.expectedItemsPerLine1 = expectedItemsPerLine1;
            this.expectedItems2 = expectedItems2;
            this.expectedLines2 = expectedLines2;
            this.expectedItemsPerLine2 = expectedItemsPerLine2;
        }

        @Override
        protected void processInput1(ConfigGroup dataItems1, ConfigGroup dataItems2) {
            validateInput(dataItems1, expectedItems1, expectedLines1, expectedItemsPerLine1);
            if (expectedItems2 > 0) {
                System.out.println("\n----------------\n");
            }
            validateInput(dataItems2, expectedItems2, expectedLines2, expectedItemsPerLine2);
        }

        @Override
        protected void processInput2(ConfigGroup dataItems1, ConfigGroup dataItems2) {
            validateInput(dataItems1, expectedItems1, expectedLines1, expectedItemsPerLine1);
            if (expectedItems2 > 0) {
                System.out.println("\n----------------\n");
            }
            validateInput(dataItems2, expectedItems2, expectedLines2, expectedItemsPerLine2);
        }

        protected void validateInput(ConfigGroup dataItems,
                                     int expectedItems, int expectedLines, int expectedItemsPerLine) {
            if (dataItems.size() != expectedItems) {
                throw new RuntimeException("Data items: " + dataItems.size() + " did not match expected items: " + expectedItems);
            }

            for (int i = 0; i < dataItems.size(); i++) {
                GroupItem dataItem = dataItems.get(i);
                if (dataItem.size() != expectedLines) {
                    throw new RuntimeException("Data item: " + i + " lines: " + dataItem.size()
                            + " did not match expected lines: " + expectedLines);
                }

                if (i > 0) {
                    System.out.println();
                }

                AtomicInteger lineNum = new AtomicInteger(0);
                for (List<String> line: dataItem) {
                    if (line.size() != expectedItemsPerLine) {
                        throw new RuntimeException("Data item: " + i + " Line num: " + lineNum
                                + " didn't have expected items per line: " + expectedItemsPerLine
                                + " data was: '" + line + "'");
                    }

                    line.forEach(item -> System.out.print(" " + item));
                    System.out.println();

                    lineNum.incrementAndGet();
                }
            }
        }
    }
}