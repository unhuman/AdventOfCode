package com.unhuman.adventofcode.aoc_framework;

import com.unhuman.adventofcode.aoc_framework.helper.LineInput;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.System.exit;

public abstract class InputParser {
    private static final String ADVENT_OF_CODE_HTTPS_PREFIX = "https://adventofcode.com/";
    private static final String CACHE_DIRECTORY = "cache/";

    private String filenameOrData;
    private final String aocSession;
    private final String lineItemRegex1;
    private final String lineItemRegex2;

    /**
     * Creates an InputParser that will process line-by-line
     * @param year
     * @param day
     * @param lineItemRegex1
     * you may need to prepend (?:.*?) to your regex to ignore cruft in the beginning
     * @param lineItemRegex2
     * you may need to prepend (?:.*?) to your regex to ignore cruft in the beginning
     */
    public InputParser(int year, int day, String lineItemRegex1, String lineItemRegex2) {
        this(generateUrlPath(year, day), lineItemRegex1, lineItemRegex2);
    }

    /**
     * Creates an InputParser that will process line-by-line
     * @param filenameOrData (filename or data (if multi-line))
     * @param lineItemRegex1
     * you may need to prepend (?:.*?) to your regex to ignore cruft in the beginning
     * @param lineItemRegex2
     * you may need to prepend (?:.*?) to your regex to ignore cruft in the beginning
     */
    public InputParser(String filenameOrData, String lineItemRegex1, String lineItemRegex2) {
        this.filenameOrData = filenameOrData;
        this.aocSession = determineAOCSession();
        this.lineItemRegex1 = lineItemRegex1;
        this.lineItemRegex2 = lineItemRegex2;
    }

    static String generateUrlPath(int year, int day) {
        return String.format("%s%d/day/%d", ADVENT_OF_CODE_HTTPS_PREFIX, year, day);
    }

    static String determineAOCSession() {
        File sessionTokenFile = new File("aocSessionTokenFile.txt");
        try (Scanner scannerCookie = new Scanner(sessionTokenFile)) {
            return scannerCookie.nextLine();
        } catch (Exception e) {
            // ignore this one, we'll try one directory back
        }

        sessionTokenFile = new File("../aocSessionTokenFile.txt");
        try (Scanner scannerCookie = new Scanner(sessionTokenFile)) {
            return scannerCookie.nextLine();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void process() {
        // get the data
        ConfigGroup[] configGroups = parseFiles();

        // Once we have all the data, process it
        processInputWrapper(configGroups);
    }

    public ConfigGroup[] parseFiles() {
        // read in data from file
        LineInput lineInput = readFile();

        // Convert file to usable data
        // This will try to read in dataItems1 and then read in dataItems2 if present
        // this will not allow multiple items for dataItems1 if lineItemRegex2 exists
        ConfigGroup dataItems1 = new ConfigGroup();
        ConfigGroup dataItems2 = new ConfigGroup();
        while (true) {
            GroupItem data = parseLinesItem(lineInput, lineItemRegex1);
            if (data == null) {
                break;
            }
            dataItems1.add(data);

            // if we will be processing another type after this one,
            // don't allow multiples of this type separated by a new line
            if (lineItemRegex2 != null) {
                break;
            }
        }

        while (true) {
            GroupItem data = parseLinesItem(lineInput, lineItemRegex2);
            if (data == null) {
                break;
            }
            dataItems2.add(data);
        }
        return new ConfigGroup[] { dataItems1, dataItems2 };
    }

    /**
     * Gets all the data from a file, returns a list of strings
     * @return list of strings
     */
    protected LineInput readFile() {
        List<String> lines = new ArrayList<>();

        // If we get data directly, just use it.
        if (filenameOrData.contains("\n")) {
            lines = Arrays.stream(filenameOrData.split("\\r?\\n")).toList();
            return new LineInput(lines);
        }

        Scanner scanner = null;
        File file = null;
        InputStream inputStream = null;

        String cacheFilename = null;
        if (filenameOrData.startsWith(ADVENT_OF_CODE_HTTPS_PREFIX)) {
            String checkCacheFilename = CACHE_DIRECTORY + filenameOrData.substring(ADVENT_OF_CODE_HTTPS_PREFIX.length()).replace('/', '-') + ".txt";
            if (new File(checkCacheFilename).exists()) {
                // switch to reading in this file
                filenameOrData = checkCacheFilename;
            } else {
                // We will need to cache after read
                cacheFilename = checkCacheFilename;
            }
        }

        if (filenameOrData.startsWith(ADVENT_OF_CODE_HTTPS_PREFIX)) {
            String inputFilename = filenameOrData + "/input";
            try {
                URL url = new URL(inputFilename);
                URLConnection connection = url.openConnection();
                if (aocSession == null) {
                    System.err.println("No cookie provided");
                    exit(-1);
                }

                connection.setRequestProperty("COOKIE", aocSession);
                connection.connect();

                inputStream = connection.getInputStream();
                scanner = new Scanner(inputStream, StandardCharsets.UTF_8.toString());
            } catch (IOException e) {
                System.err.println("Could not process url: " + inputFilename + " message: " + e.getMessage());
                exit(-1);
            }
        } else {
            try {
                file = new File(filenameOrData);
                scanner = new Scanner(file);
            } catch (FileNotFoundException e) {
                System.err.println("Could not find file: " + filenameOrData);
                exit(-1);
            }
        }

        try {
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
        } catch (Exception e) {
            System.err.println("Error processing file: " + filenameOrData + " message: " + e.getMessage());
            exit(-1);
        }

        // If there's information to cache, we store it
        if (cacheFilename != null) {
            new File(CACHE_DIRECTORY).mkdir();
            try {
                FileWriter writer = new FileWriter(cacheFilename);
                for (String line: lines) {
                    writer.write(line + "\r\n");
                }
                writer.close();
            } catch (IOException ioe) {
                System.err.println("Error writing cache file: " + cacheFilename);
            }
        }

        return new LineInput(lines);
    }

    /**
     * Convert lines into usable data
     * @param lineInput
     * note: to get this work properly, you may need to prepend (?:.*?) to your regex to ignore cruft in the beginning
     * @param regexLineItem
     * @return
     */
    protected GroupItem parseLinesItem(LineInput lineInput, String regexLineItem) {
        if (regexLineItem == null) {
            return null;
        }

        Pattern patternLineItem = Pattern.compile(regexLineItem, Pattern.MULTILINE | Pattern.DOTALL);

        // clear off any preceding blank lines
        while (true) {
            String precedingLine = lineInput.peekLine();
            if (precedingLine == null) {
                return null;
            }
            if (!precedingLine.trim().isBlank()) {
                break;
            }
        }

        GroupItem data = new GroupItem();
        while (true) {
            String line = lineInput.readLine();

            // if we are done processing, return what we have
            if (line == null || line.isBlank()) {
                return (data.size() > 0) ? data : null;
            }

            ItemLine dataLine = new ItemLine(line);
            Matcher matcher = patternLineItem.matcher(line);
            while (true) {
                if (matcher.find()) {
                    if (matcher.groupCount() >= 1) {
                        for (int i = 1; i <= matcher.groupCount(); i++) {
                            String lineItem = (matcher.group(i) != null && matcher.group(i).length() > 0)
                                    ? matcher.group(i) : null;
                            dataLine.add(lineItem);
                        }

                        if (matcher.group(0).length() == 0) {
                            throw new RuntimeException("Could not extract item from line: " + line + " with regex: " + regexLineItem);
                        }

                        // allow continuation for duplicate matchers on a line
                        line = line.substring(line.indexOf(matcher.group(0)) + matcher.group(0).length());
                        // if we advance to the end of the string and it's done, then we process next line
                        if (line.isEmpty()) {
                            break;
                        }
                        matcher = patternLineItem.matcher(line);
                    } else {
                        throw new RuntimeException("Found no item (" + matcher.groupCount()
                                + ") in line: " + line + " with regex: " + regexLineItem);
                    }
                } else {
                    break;
                }
            }

            if (dataLine.size() > 0) {
                data.add(dataLine);
            }
        }
    }

    /**
     * This method should process the input (call implementation)
     * @param configGroups 2 sets of data
     */
    protected void processInputWrapper(ConfigGroup[] configGroups) {
        long time;
        float timeMs;
        float timeS;
        ConfigGroup dataItems1 = configGroups[0];
        ConfigGroup dataItems2 = configGroups[1];

        System.out.println("*** Start " + getClass().getSimpleName() + " Task 1 - "
                + LocalTime.now().toString().split("\\.")[0]);
        time = System.nanoTime();
        Object results1 = processInput1(dataItems1, dataItems2);
        System.out.println(results1);
        time = System.nanoTime() - time;
        timeMs = (float) time / 1000000;
        timeS = timeMs / 1000;
        System.out.println("*** End " + getClass().getSimpleName().split("\\.")[0] + " Task 1 - "
                + LocalTime.now().toString().split("\\.")[0]
                + " - time: " + time / 1000 + "us, " + timeMs + "ms, " + timeS + "s ***");

        System.out.println("*** Start " + getClass().getSimpleName() + " Task 2 - "
                + LocalTime.now().toString().split("\\.")[0]);
        time = System.nanoTime();
        Object results2 = processInput2(dataItems1, dataItems2);
        System.out.println(results2);
        time = System.nanoTime() - time;
        timeMs = (float) time / 1000000;
        timeS = timeMs / 1000;
        System.out.println("*** End " + getClass().getSimpleName() + " Task 2 - "
                + LocalTime.now().toString().split("\\.")[0]
                + " - time: " + time / 1000 + "us, " + timeMs + "ms, " + timeS + "s ***");

        System.out.println();
    }

    /**
     * This method should process the input and output the requested information to stdout
     * @param dataItems1 this is a list (items) of rows of items
     * @param dataItems2 (optional/empty) this is a list (items) of rows of items
     */
    public abstract Object processInput1(ConfigGroup dataItems1, ConfigGroup dataItems2);

    /**
     * This method should process the input and output the requested information to stdout
     * @param dataItems1 this is a list (items) of rows of items
     * @param dataItems2 (optional/empty) this is a list (items) of rows of items
     */
    public abstract Object processInput2(ConfigGroup dataItems1, ConfigGroup dataItems2);
}
