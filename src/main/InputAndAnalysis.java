package main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InputAndAnalysis {


    private String pathToFile;
    private double sumOfAllNumbers;
    private double avgOfAllNumbers;
    private double medianOfAllNumbers;
    private int countOfNumbers;
    private List<Double> numbers;
    private List<String> strings;

    /*
     * Block default constructor
     */
    private InputAndAnalysis() {
    }

    /**
     * Construct main.InputAndAnalysis with an absolute path to a file
     *
     * @param pathToFile
     */
    public InputAndAnalysis(String pathToFile) {
        this.numbers = new ArrayList<>();
        this.strings = new ArrayList<>();
        this.pathToFile = pathToFile;
    }

    /**
     * Return the sum of numbers found in the file
     *
     * @return the sum of all numbers as a double rounded to 2 decimal places
     */
    public double getTotal() {
        return this.sumOfAllNumbers;
    }

    private void setTotal(double sumOfAllNumbers) {
        this.sumOfAllNumbers = InputAndAnalysis.roundDouble(sumOfAllNumbers);
    }

    /**
     * Return the average of all the numbers found in the file
     *
     * @return a double rounded to 2 decimal places
     */
    public double getAverage() {
        return this.avgOfAllNumbers;
    }

    private void setAverage(double avgOfAllNumbers) {
        this.avgOfAllNumbers = InputAndAnalysis.roundDouble(avgOfAllNumbers);
    }

    public double getMedian() {
        return this.medianOfAllNumbers;
    }

    private void setMedian() {
        int mid = this.numbers.size() / 2;
        if (mid % 2 == 1) {
            this.medianOfAllNumbers = this.numbers.get(mid);
        } else {
            this.medianOfAllNumbers = ((this.numbers.get(mid - 1) + this.numbers.get(mid)) / 2.0);
        }
    }

    /**
     * Return the amount of lines in the file that are numbers
     *
     * @return int for the sum of numbers found in the file
     */
    public int getCountOfNumbers() {
        return this.countOfNumbers;
    }

    private void setCountOfNumbers(List<Double> numbers) {
        this.countOfNumbers = numbers.size();
    }

    /**
     * Verify if the string being passed in is present in the file
     *
     * @param src string user passed in to search for
     * @return boolean, true if src is present in file else false
     */
    public boolean contains(String src) {
        /*
        This function assumes that the requirement's definition of string would be any string
        found in the line of a file, not an exact match for the line itself.

        If the assumption is wrong than the implementation of this function is simpler.  We would
        only need to check that src exists as an entry in this.strings list.
         */
        for (String line : this.strings) {
            if (line.contains(src)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Need to run the class before able to call for stats on the object
     */
    public void run() {
        //Read the file into memory
        List<String> rawList = this.readFile();
        this.splitListsByType(rawList);

        this.setCountOfNumbers(this.numbers);
        DoubleSummaryStatistics stats = this.getStats(numbers);
        this.setTotal(stats.getSum());
        this.setAverage(stats.getAverage());
        this.setMedian();
    }

    @Override
    public String toString() {
        return String.format("Sum of number in file: %s,\n" +
                        "Average of numbers in file: %s,\n" +
                        "Median of numbers in file: %s,\n" +
                        "Percentage of values that were numbers: %s,\n" +
                        "Reverse alphabetical list of strings in file with " +
                        "count for amount of times that string appeared in the file: %s\n",
                this.getTotal(),
                this.getAverage(),
                this.getMedian(),
                InputAndAnalysis.roundDouble((double) this.numbers.size() /
                        ((double) this.numbers.size() + (double) this.strings.size())),
                this.buildRevListString()
        );
    }

    /*
     * Using dynamic programming solution here to keep time complexity O(log n)
     *
     * More difficult to read but if the size of the file is large enough the time saved will
     * be significant
     */
    private String buildRevListString() {
        Map<String, Integer> stringMap = new TreeMap<>(Collections.reverseOrder());
        List<Integer> foundDuplicatesIndexes = new ArrayList<>();
        for (int i = 0; i < this.strings.size() && !foundDuplicatesIndexes.contains(i); i++) {
            int occurrences = 1;
            for (int j = 0; j < this.strings.size(); j++) {
                if ((j > i) && (this.strings.get(i).equals((this.strings.get(j))))) {
                    foundDuplicatesIndexes.add(j);
                    occurrences++;
                }
                stringMap.put(this.strings.get(i).toLowerCase(), occurrences);
            }
        }

        StringBuilder revList = new StringBuilder("\n");
        for (Map.Entry<String, Integer> entry : stringMap.entrySet()) {
            revList.append(String.format("\t%s : %s%n", entry.getKey(), entry.getValue()));
        }

        return revList.toString();
    }

    private static double roundDouble(double numToRound) {
        double num = Math.round(numToRound * 100);
        return num / 100;
    }

    private DoubleSummaryStatistics getStats(List<Double> numbers) {
        return numbers.stream().collect(
                Collectors.summarizingDouble(Double::doubleValue));
    }

    private List<String> readFile() {
        List<String> rawFileList = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(this.pathToFile))) {
            rawFileList = stream.collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rawFileList;
    }

    private void splitListsByType(List<String> rawFile) {
        for (String line : rawFile) {
            try {
                this.numbers.add(new Double(line));
            } catch (NumberFormatException cfe) {
                this.strings.add(line);
            }
        }
    }

    public static void main(String[] args) {
        String parDir = new File("").getAbsolutePath();
        String pathToSampleData = parDir + "/src/test/sample_data.txt";

        InputAndAnalysis iaa = new InputAndAnalysis(pathToSampleData);
        iaa.run();
        System.out.print(iaa);
    }
}
