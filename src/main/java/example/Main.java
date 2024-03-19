package example;

import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Main {
    public static String RLEDeCode(String input) {
        StringBuilder result = new StringBuilder();
        char[] chars = input.toCharArray();
        boolean key = true;

        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (Character.isDigit(c)) {
                if(key) {
                    int num = Integer.parseInt(String.valueOf(c));
                    for (int j = 0; j < num; j++) {
                        result.append(chars[i + 1]);
                    }
                    key = false;
                } else key = true;

            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    public static String RLECode(String input) {
        StringBuilder result = new StringBuilder();
        int count = 1;
        char[] chars = input.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (i + 1 < chars.length && c == chars[i + 1]) {
                if(count < 9) count++;
                else {
                    result.append(count).append(c);
                    count = 1;
                }
            } else {
                result.append(count).append(c);
                count = 1;
            }
        }
        return result.toString();

    }
    public static String stringToBinary(String input) {
        StringBuilder binaryResult = new StringBuilder();
        for (char c : input.toCharArray()) {
            String binaryChar = Integer.toBinaryString(c);
            binaryResult.append(String.format("%8s", binaryChar).replace(' ', '0'));
        }
        return binaryResult.toString();
    }

    public static void main(String[] args) throws IOException {
        try (FileInputStream inputStream = new FileInputStream("C:\\Users\\koldun\\" +
                "Desktop\\labs\\graphic\\lab4\\src\\" +
                "main\\java\\example\\img.bmp");
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[1024 * 4];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            String bmpContentAsString = outputStream.toString(StandardCharsets.ISO_8859_1);
            String bmpBinary = stringToBinary(bmpContentAsString);
            try (FileWriter writer = new FileWriter("bmpBinary.txt")) {
                writer.write(bmpBinary);
                System.out.println("Binary успешно записана в файл.");
            } catch (IOException e) {
                System.err.println("Ошибка при записи в файл: " + e.getMessage());
            }
            String rleSrt = RLECode(bmpBinary);
            try (FileWriter writer = new FileWriter("RLECode.txt")) {
                writer.write(rleSrt);
                System.out.println("RLECode Строка успешно записана в файл.");
            } catch (IOException e) {
                System.err.println("Ошибка при записи в файл: " + e.getMessage());
            }
            String decodedRleStr = RLEDeCode(rleSrt);
            try (FileWriter writer = new FileWriter("RLEDecode.txt")) {
                writer.write(decodedRleStr);
                System.out.println("RLEDecode Строка успешно записана в файл.");
            } catch (IOException e) {
                System.err.println("Ошибка при записи в файл: " + e.getMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}