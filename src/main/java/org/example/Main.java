package org.example;

public class Main {
    public static void main(String[] args) {
        try {
            ArgumentParser argumentParser = new ArgumentParser(args);
            FilesFilter filesFilter = new FilesFilter(argumentParser);
            filesFilter.filter();
        }
        catch (NoFileNamesException | WrongFileExtensionException e) {
            System.out.println(e.getMessage());
        }
    }
}