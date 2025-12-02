package com.solvians.showcase;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*This class used as unit test for
CertificateUpdate class*/
public class CertificateUpdateTest
{
    CertificateUpdate certificateUpdate = new CertificateUpdate();
    private static final String PRICE_REGEX = "\\d+\\.\\d{2}";


    @Test
    void testCheckDigitWithExampleData()
    {
        String code = "DE123456789";
        int checkDigit = calcCheckDigit(code);
        assertEquals(6, checkDigit);
    }

    @Test
    void testIsinFormatAndCheckDigit()
    {
        //when
        String isinData = certificateUpdate.getIsin();
        //then
        assertEquals(12, isinData.length());//length must be 12
        assertTrue(Character.isLetter(isinData.charAt(0)));
        assertTrue(Character.isLetter(isinData.charAt(1)));
        assertTrue(Character.isDigit(isinData.charAt(11)));

        String base = isinData.substring(0, 11);
        int expectedCheckDigit = calcCheckDigit(base);
        int actualCheckDigit = Integer.parseInt(isinData.substring(11));
        assertEquals(expectedCheckDigit, actualCheckDigit);
    }

    @Test
    void testIsinPattern()
    {
        String isinData = certificateUpdate.getIsin();
        assertEquals(12, isinData.length());
        assertTrue(Character.isLetter(isinData.charAt(0)));
        assertTrue(Character.isLetter(isinData.charAt(1)));
        assertTrue(Character.isDigit(isinData.charAt(11)));

        for (int i = 2; i <= 10; i++)
        {
            char charData = isinData.charAt(i);
            assertTrue(Character.isDigit(charData) || (charData >= 'A' && charData <= 'Z'),
                    "Invalid char at the position" + i + ":" + charData);
        }

    }

    @Test
    void testToStringCsvFormat()
    {
        String lineData = certificateUpdate.toString();

        String[] parts = lineData.split(",");
        assertEquals(6, parts.length, " Expected 6 comma- separated fields");

        long timestamp = Long.parseLong(parts[0]);
        assertTrue(timestamp > 0);

        String isin = parts[1];
        assertEquals(12, isin.length());
        assertTrue(Character.isLetter(isin.charAt(0)));
        assertTrue(Character.isLetter(isin.charAt(1)));

        assertTrue(parts[2].matches(PRICE_REGEX));
        int bidSize = Integer.parseInt(parts[3]);
        assertTrue(bidSize >= 1000 && bidSize <= 5000);
        assertTrue(parts[4].matches(PRICE_REGEX));

        int askSize = Integer.parseInt(parts[5]);
        assertTrue(askSize >= 1000 && askSize <= 10000);


    }

    private int calcCheckDigit(String base)
    {
        return certificateUpdate.calcCheckDigit(base, CertificateUpdate.LETTERS);
    }
}
