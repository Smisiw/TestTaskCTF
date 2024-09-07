package org.example;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FilesFilter {
    private final ArgumentParser parser;
    private final StatisticsService statisticsService = new StatisticsService();

    public FilesFilter(ArgumentParser parser) throws WrongFileExtensionException, NoFileNamesException {
        this.parser = parser;
    }

    public void filter() {
        List<BufferedReader> files = new ArrayList<>();
        try {
            for (String filename : parser.getFileNames()) {
                files.add(new BufferedReader(new FileReader(filename)));
            }
            Path path = Paths.get(parser.getResultsPath());
            Files.createDirectories(path);
            BufferedWriter integerOutput = null;
            BufferedWriter decimalOutput = null;
            BufferedWriter stringOutput = null;
            String line;
            while (!files.isEmpty()) {
                for (BufferedReader file : files) {
                    if ((line = file.readLine())==null) {
                        file.close();
                        files.remove(file);
                        break;
                    }
                    else {
                        switch (identifyType(line)) {
                            case INTEGER: {
                                if (integerOutput == null) {
                                    integerOutput = new BufferedWriter(new FileWriter((parser.getResultsPath().isEmpty()? "" : path + "/") + parser.getResultsPrefix() + "integers.txt", parser.isAppendMode()));
                                }
                                statisticsService.addInteger(line);
                                integerOutput.write(line + "\n");
                                break;
                            }
                            case DECIMAL: {
                                if (decimalOutput == null) {
                                    decimalOutput = new BufferedWriter(new FileWriter((parser.getResultsPath().isEmpty()? "" : path + "/")+ parser.getResultsPrefix() + "floats.txt", parser.isAppendMode()));
                                }
                                statisticsService.addDecimal(line);
                                decimalOutput.write(line + "\n");
                                break;
                            }
                            case STRING: {
                                if (stringOutput == null) {
                                    stringOutput = new BufferedWriter(new FileWriter((parser.getResultsPath().isEmpty()? "" : path + "/") + parser.getResultsPrefix() + "strings.txt", parser.isAppendMode()));
                                }
                                statisticsService.addString(line);
                                stringOutput.write(line + "\n");
                                break;
                            }
                        }
                    }
                }
            }
            if (integerOutput != null) {
                integerOutput.close();
            }
            if (decimalOutput != null) {
                decimalOutput.close();
            }
            if (stringOutput != null) {
                stringOutput.close();
            }
            if (parser.getStatistics() == StatisticsType.FULL) {
                System.out.println(statisticsService.getFullStatistics());
            }
            else if (parser.getStatistics() == StatisticsType.SHORT) {
                System.out.println(statisticsService.getShortStatistics());
            }
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private LineType identifyType(String line) {
        boolean isDecimal = false;
        boolean isDigitPassed = false;
        boolean isNormalDecimalForm = false;
        if (line == null) {
            return LineType.STRING;
        }
        int length = line.length();
        if (length == 0) {
            return LineType.STRING;
        }
        int i = 0;
        if (line.charAt(0) == '-') {
            if (length == 1) {
                return LineType.STRING;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = line.charAt(i);
            if (c < '0' || c > '9') {
                if (c == '.' && !isDecimal) {
                    isDecimal = true;
                }
                else if ((c == 'e' || c == 'E') && !isNormalDecimalForm) {
                    if (isDigitPassed) {
                        isNormalDecimalForm = true;
                        if (length == i + 1) {
                            return LineType.STRING;
                        }
                        i++;
                        if (line.charAt(i) != '-' && (line.charAt(i) < '0' || line.charAt(i) > '9')) {
                            return LineType.STRING;
                        }
                    }
                    else {
                        return LineType.STRING;
                    }
                }
                else {
                    return LineType.STRING;
                }
            }
            else {
                isDigitPassed = true;
            }
        }
        return isDecimal ? LineType.DECIMAL : LineType.INTEGER;
    }

}
