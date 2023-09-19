package src.AverageOfOddAndEvenNumbers;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner myScan = new Scanner(System.in);
        Random rand = new Random();
        int range = Integer.MIN_VALUE;
        System.out.print("------------RandomNumber--------------");
        while (range == Integer.MIN_VALUE) {
            try {
                System.out.print("\nPlease enter a range : ");
                String str = myScan.nextLine();
                if (!(str.equalsIgnoreCase(""))) {
                    range = Integer.parseInt(str);
                } else {
                    range = 10;
                }

            } catch (Exception e) {
                System.out.print("/!\\ Please enter a valid number /!\\");
            }

            RandomNumber randomNumber = new RandomNumber(range);
            System.out.println(randomNumber);
        }
    }
}

