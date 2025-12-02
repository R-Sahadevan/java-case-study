package com.solvians.showcase;

import java.util.concurrent.ThreadLocalRandom;

public class CertificateUpdate
{

    // TODO: implement me.
    // Prices and size ranges constants
    private static final double MIN_BID_PRICE = 100.00;
    private static final double MAX_BID_PRICE = 200.01;
    private static final int MIN_BID_SIZE = 1000;
    private static final int MAX_BID_SIZE = 5000;
    private static final int MAX_ASK_SIZE = 10000;

    //Character constants  for Isin generation
    public static final String ALPHANUMERIC_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    static final int[] LETTERS = {10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25,
            26, 27, 28, 29, 30, 31, 32, 33, 34, 35};

    private long timeStamp;
    private String isin;
    private double bidPrice;
    private int bidSize;
    private double askPrice;
    private int askSize;

    /* To create a random certificate update
        based on the specification */
    public CertificateUpdate()
    {
        //To generate timestamp
        this.timeStamp = System.currentTimeMillis();
        this.isin = toGenerateIsin();

        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
        this.bidPrice = threadLocalRandom.nextDouble(MIN_BID_PRICE, MAX_BID_PRICE);
        this.bidSize = threadLocalRandom.nextInt(MIN_BID_SIZE, MAX_BID_SIZE + 1);
        this.askPrice = threadLocalRandom.nextDouble(MIN_BID_PRICE, MAX_BID_PRICE);
        this.askSize = threadLocalRandom.nextInt(MIN_BID_SIZE, MAX_ASK_SIZE + 1);
    }

    /* This method to calculate digit  */
    public int calcCheckDigit(String code, int[] letters)
    {
        StringBuilder result = new StringBuilder();
        for (char charData : code.toCharArray())
        {
            if (Character.isLetter(charData))
            {
                result.append(letters[charData - 'A']);
            }
            else
            {
                result.append(charData);
            }
        }

        int[] digits = new int[result.length()];
        for (int i = 0; i < digits.length; i++)
        {
            digits[i] = Character.getNumericValue(result.charAt(result.length() - 1 - i));
            if (i % 2 == 0)
            {
                digits[i] *= 2;
            }
        }

        int sum = 0;
        for (int digit : digits)
        {
            if (digit > 9)
            {
                sum += (digit / 10) + (digit % 10);
            }
            else
            {
                sum += digit;
            }
        }
        return (10 - sum % 10) % 10;
    }

    /* This method for generate an isin with 2 letters ,
     9 alphanumeric value and 1 check digit
     */
    private String toGenerateIsin()
    {
        String randomLetters = toGenerateRandomLetters(2);
        String randomAlphanumeric = toGenerateRandomAlphanumeric(9);
        String letterWithAlphaNumericData = randomLetters + randomAlphanumeric;
        int checkDigitData = calcCheckDigit(letterWithAlphaNumericData, LETTERS);
        return letterWithAlphaNumericData + checkDigitData;
    }

    private String toGenerateRandomLetters(int length)
    {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++)
        {
            result.append((char) ('A' + ThreadLocalRandom.current().nextInt(26)));
        }
        return result.toString();
    }

    private String toGenerateRandomAlphanumeric(int length)
    {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++)
        {
            result.append(ALPHANUMERIC_CHARS.charAt(ThreadLocalRandom.current().nextInt(ALPHANUMERIC_CHARS.length())));
        }
        return result.toString();
    }
    /* To Return result as a csv line
     */

    @Override
    public String toString()
    {
        return String.format("%d,%s,%.2f,%d,%.2f,%d", timeStamp, isin, bidPrice, bidSize, askPrice, askSize);
    }

    public String getIsin()
    {
        return isin;
    }

}

