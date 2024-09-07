package org.example;

import java.io.File;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ArgumentParser {
    private String resultsPath = "";
    private String resultsPrefix = "";
    private StatisticsType statistics = StatisticsType.NONE;
    private boolean appendMode = false;
    private final List<String> fileNames = new ArrayList<>();

    public ArgumentParser(String[] args) throws WrongFileExtensionException, NoFileNamesException {
        boolean isParametersPassed = false;
        int currentArgIndex = 0;
        while (currentArgIndex < args.length) {
            if (!isParametersPassed) {
                if (args[currentArgIndex].startsWith("-")) {
                    switch (args[currentArgIndex]) {
                        case "-o": {
                            try {
                                Path path = new File(args[++currentArgIndex]).toPath();
                                resultsPath = path.toAbsolutePath().toString();
                                currentArgIndex++;
                            }
                            catch (InvalidPathException e) {
                                System.out.println("Неверно указан путь к папке с результатами");
                                currentArgIndex++;
                            }
                            break;
                        }
                        case "-p": {
                            try {
                                Path file = new File(args[++currentArgIndex]).toPath();
                                resultsPrefix = args[currentArgIndex++];
                            }
                            catch (InvalidPathException e) {
                                System.out.println("Неверно указан префикс файлов с результатами");
                                currentArgIndex++;
                            }
                            break;
                        }
                        case "-s": {
                            if (statistics == StatisticsType.NONE) {
                                statistics = StatisticsType.SHORT;
                            }
                            currentArgIndex++;
                            break;
                        }
                        case "-f": {
                            if (statistics == StatisticsType.NONE) {
                                statistics = StatisticsType.FULL;
                            }
                            currentArgIndex++;
                            break;
                        }
                        case "-a": {
                            appendMode = true;
                            currentArgIndex++;
                            break;
                        }
                        default: {
                            System.out.println("Неизвестный параметр: " + args[currentArgIndex]);
                            currentArgIndex++;
                        }
                    }
                }
                else {
                    isParametersPassed = true;
                }
            }
            else {
                String fileName = args[currentArgIndex];
                if(fileName.lastIndexOf(".") != -1
                        && fileName.lastIndexOf(".") != 0
                        && fileName.substring(fileName.lastIndexOf(".")+1).equals("txt")
                ) {
                    fileNames.add(args[currentArgIndex]);
                    currentArgIndex++;
                }
                else {
                    throw new WrongFileExtensionException("Неверное расширение файла " + fileName);
                }
            }
        }
        if (fileNames.isEmpty()) {
            throw new NoFileNamesException("Не указаны имена входных файлов");
        }
    }

    public String getResultsPath() {
        return resultsPath;
    }

    public String getResultsPrefix() {
        return resultsPrefix;
    }

    public StatisticsType getStatistics() {
        return statistics;
    }

    public boolean isAppendMode() {
        return appendMode;
    }

    public List<String> getFileNames() {
        return fileNames;
    }
}
