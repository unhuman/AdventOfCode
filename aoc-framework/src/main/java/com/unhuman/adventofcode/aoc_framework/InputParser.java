package com.unhuman.adventofcode.aoc_framework;

import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;
import com.unhuman.adventofcode.aoc_framework.helper.LineInput;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class InputParser {
    private final String filename;
    private String cookieOrCookieFile;
    private final String lineItemRegex1;
    private final String lineItemRegex2;

    /**
     * Creates an InputParser that will process line-by-line
     * @param filenameAndCookieInfo
     * @param lineItemRegex1
     * @param lineItemRegex2
     */
    public InputParser(String[] filenameAndCookieInfo, String lineItemRegex1, String lineItemRegex2) {
        if (filenameAndCookieInfo.length < 1 || filenameAndCookieInfo.length > 2) {
            throw new RuntimeException("Must provide filename (or url) and cookie");
        }
        this.filename = filenameAndCookieInfo[0];
        this.cookieOrCookieFile = (filenameAndCookieInfo.length >= 2) ? filenameAndCookieInfo[1] : null;
        this.lineItemRegex1 = lineItemRegex1;
        this.lineItemRegex2 = lineItemRegex2;
    }

    public void setCookieOrCookieFile(String cookieOrCookieFile) {
        this.cookieOrCookieFile = cookieOrCookieFile;
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

        Scanner scanner = null;
        File file = null;
        InputStream inputStream = null;
        if (filename.startsWith("https://")) {
            String inputFilename = filename + "/input";
            try {
                URL url = new URL(inputFilename);
                URLConnection connection = url.openConnection();
                if (cookieOrCookieFile == null) {
                    System.err.println("No cookie provided");
                    System.exit(-1);
                }

                if (!cookieOrCookieFile.startsWith("session=")) {
                    try (Scanner scannerCookie = new Scanner(new File(cookieOrCookieFile))) {
                        cookieOrCookieFile = scannerCookie.nextLine();
                    }
                }

                connection.setRequestProperty("COOKIE", cookieOrCookieFile);
                connection.connect();

                inputStream = connection.getInputStream();
                scanner = new Scanner(inputStream, StandardCharsets.UTF_8.toString());
            } catch (IOException e) {
                System.err.println("Could not process url: " + inputFilename + " message: " + e.getMessage());
                System.exit(-1);
            }
        } else {
            try {
                file = new File(filename);
                scanner = new Scanner(file);
            } catch (FileNotFoundException e) {
                System.err.println("Could not find file: " + filename);
                System.exit(-1);
            }
        }

        try {
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
        } catch (Exception e) {
            System.err.println("Error processing file: " + filename + " message: " + e.getMessage());
            System.exit(-1);
        }
        return new LineInput(lines);
    }

    /**
     * Convert lines into usable data
     * @param lineInput
     * @param regexLineItem
     * @return
     */
    protected GroupItem parseLinesItem(LineInput lineInput, String regexLineItem) {
        if (regexLineItem == null) {
            return null;
        }

        Pattern patternLineItem = Pattern.compile(regexLineItem);

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

            ItemLine dataLine = new ItemLine();
            Matcher matcher = patternLineItem.matcher(line);
            while (true) {
                if (matcher.find()) {
                    if (matcher.groupCount() >= 1) {
                        for (int i = 1; i <= matcher.groupCount(); i++) {
                            String lineItem = (matcher.group(i) != null && matcher.group(i).length() > 0)
                                    ? matcher.group(i) : null;
                            dataLine.add(lineItem);
                        }
                        // allow continuation for duplicate matchers on a line
                        line = line.substring(matcher.group(0).length());
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
        ConfigGroup dataItems1 = configGroups[0];
        ConfigGroup dataItems2 = configGroups[1];

        System.out.println("*** Start " + getClass().getSimpleName() + " Task 1 *** ");
        time = System.nanoTime();
        Object results1 = processInput1(dataItems1, dataItems2);
        System.out.println(results1);
        time = System.nanoTime() - time;
        timeMs = (float) time / 1000000;
        System.out.println("*** End " + getClass().getSimpleName() + " Task 1 - time: " + time / 1000 + "us, " + timeMs + "ms ***");

        System.out.println("*** Start " + getClass().getSimpleName() + " Task 2 *** ");
        time = System.nanoTime();
        Object results2 = processInput2(dataItems1, dataItems2);
        System.out.println(results2);
        time = System.nanoTime() - time;
        timeMs = (float) time / 1000000;
        System.out.println("*** End " + getClass().getSimpleName() + " Task 2 - time: " + time / 1000 + "us, " + timeMs + "ms ***");

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
