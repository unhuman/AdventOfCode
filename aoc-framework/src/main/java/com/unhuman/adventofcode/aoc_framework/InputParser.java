package com.unhuman.adventofcode.aoc_framework;

import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;
import com.unhuman.adventofcode.aoc_framework.utility.LineInput;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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

        // Once we have all the data, process it
        processInputWrapper(dataItems1, dataItems2);
    }

    /**
     * Gets all the data from a file, returns a list of strings
     * @return list of strings
     */
    protected LineInput readFile() {
        List<String> lines = new ArrayList<>();

        Scanner scanner = null;
        if (filename.startsWith("https://")) {
            try {
                URL url = new URL(filename);
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

                scanner = new Scanner(connection.getInputStream(),
                        StandardCharsets.UTF_8.toString());
            } catch (IOException e) {
                System.err.println("Could not process url: " + filename + " message: " + e.getMessage());
                System.exit(-1);
            }
        } else {
            try {
                scanner = new Scanner(new File(filename));
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

            int start = 0;
            ItemLine dataLine = new ItemLine();
            Matcher matcher = patternLineItem.matcher(line);

            while (true) {
                if (matcher.find(start)) {
                    if (matcher.groupCount() >= 1) {
                        for (int i = 1; i <= matcher.groupCount(); i++) {
                            String lineItem = (matcher.group(i).length() > 0) ? matcher.group(i) : null;
                            dataLine.add(lineItem);
                        }
                        start += matcher.group(0).length();
                        // if we advance to the end of the string and it's done, then we process next line
                        if (start == line.length()) {
                            break;
                        }
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
     * @param dataItems1 this is a list (items) of rows of items
     * @param dataItems2 (optional/empty) this is a list (items) of rows of items
     */
    protected void processInputWrapper(ConfigGroup dataItems1, ConfigGroup dataItems2) {
        long time;
        System.out.println("\n*** Start " + getClass().getSimpleName() + " Task 1 ***\n");
        time = System.nanoTime();
        processInput1(dataItems1, dataItems2);
        time = System.nanoTime() - time;
        System.out.println("\n*** End " + getClass().getSimpleName() + " Task 1 - Time " + time + "us ***\n");

        System.out.println("\n*** Start " + getClass().getSimpleName() + " Task 2 ***\n");
        time = System.nanoTime();
        processInput2(dataItems1, dataItems2);
        time = System.nanoTime() - time;
        System.out.println("\n*** End  " + getClass().getSimpleName() + " Task 2 - Time " + time + "us ***\n");
    }

    /**
     * This method should process the input and output the requested information to stdout
     * @param dataItems1 this is a list (items) of rows of items
     * @param dataItems2 (optional/empty) this is a list (items) of rows of items
     */
    protected abstract void processInput1(ConfigGroup dataItems1, ConfigGroup dataItems2);

    /**
     * This method should process the input and output the requested information to stdout
     * @param dataItems1 this is a list (items) of rows of items
     * @param dataItems2 (optional/empty) this is a list (items) of rows of items
     */
    protected abstract void processInput2(ConfigGroup dataItems1, ConfigGroup dataItems2);

}
