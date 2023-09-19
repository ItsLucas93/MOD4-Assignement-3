package src.PrimeNumberAndPrimeFactor;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner myScan = new Scanner(System.in);

        int temp = -1;

        System.out.print("------------PrimeNumber--------------");
        while (temp <= -1) {
            try {
                System.out.print("\nPlease enter a integer number : ");
                temp = Integer.parseInt(myScan.nextLine());
                if (temp <= -1)
                    System.out.print("/!\\ Please enter positive integer number. /!\\");
            } catch (Exception e) {
                System.out.print("/!\\ Please enter positive integer number. /!\\");
            }
        }
        System.out.print("-------------------------------------");

        PrimeFactors factors = new PrimeFactors(temp);

        if (factors.isPrime())
            System.out.println(factors.displayPrime());
        else
            System.out.println(factors.displayFactors());

        System.out.print("-------------------------------------");
    }
}
