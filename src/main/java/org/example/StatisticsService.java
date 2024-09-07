package org.example;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

public class StatisticsService {
    private int intCount = 0;
    private int decimalCount = 0;
    private int StringCount = 0;
    private BigInteger minInt;
    private BigInteger maxInt;
    private BigDecimal minDecimal;
    private BigDecimal maxDecimal;
    private BigInteger sumInt = new BigInteger("0");
    private BigDecimal sumDecimal = new BigDecimal("0");
    private int shortestStringLength = Integer.MAX_VALUE;
    private int longestStringLength = 0;

    public void addInteger(String stringValue) {
        BigInteger value = new BigInteger(stringValue);
        intCount++;
        sumInt = sumInt.add(value);
        if (minInt == null || minInt.compareTo(value) > 0) {
            minInt = value;
        }
        if (maxInt == null || maxInt.compareTo(value) < 0) {
            maxInt = value;
        }
    }

    public void addDecimal(String stringValue) {
        BigDecimal value = new BigDecimal(stringValue);
        decimalCount++;
        sumDecimal = sumDecimal.add(value);
        if (minDecimal == null || minDecimal.compareTo(value) > 0) {
            minDecimal = value;
        }
        if (maxDecimal == null || maxDecimal.compareTo(value) < 0) {
            maxDecimal = value;
        }
    }

    public void addString(String value) {
        StringCount++;
        if (shortestStringLength > value.length()) {
            shortestStringLength = value.length();
        }
        if (longestStringLength < value.length()) {
            longestStringLength = value.length();
        }
    }

    public String getShortStatistics() {
        return String.format("""
                Целых чисел: %d
                Вещественных чисел: %d
                Строк: %d""",
                intCount, decimalCount, StringCount
        );
    }

    public String getFullStatistics() {
        BigDecimal avgInt = new BigDecimal(sumInt).divide(new BigDecimal(String.valueOf(intCount)), MathContext.DECIMAL128);
        BigDecimal avgDecimal = sumDecimal.divide(new BigDecimal(String.valueOf(decimalCount)), MathContext.DECIMAL128);
        return String.format("""
                        Целые числа
                        Количество: %d
                        Максимальное: %d
                        Минимальное: %d
                        Сумма: %d
                        Среднее: %f
                        
                        Вещественные числа
                        Количество: %d
                        Максимальное: %f
                        Минимальное: %f
                        Сумма: %f
                        Среднее: %f
                        
                        Строки
                        Количество: %d
                        Размер самой короткой: %d
                        Размер самой длинной: %d
                        """,
                intCount, maxInt, minInt, sumInt, avgInt,
                decimalCount, maxDecimal, minDecimal, sumDecimal, avgDecimal,
                StringCount, shortestStringLength, longestStringLength
        );
    }
}
