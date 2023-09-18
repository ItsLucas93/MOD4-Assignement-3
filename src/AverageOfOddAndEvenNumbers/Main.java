package AverageOfOddAndEvenNumbers;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner myScan = new Scanner(System.in)) {
            int range = -1;
            System.out.print("------------RandomNumber--------------");
            while (range < 0) {
                try {
                    System.out.print("\nPlease enter a range : ");
                    String str = myScan.nextLine();
                    if (!(str.equalsIgnoreCase(""))) {
                        range = Integer.parseInt(str);
                    } else {
                        range = 10;
                    }
                    if (range < 0)
                        System.out.print("/!\\ Please enter positive integer number. /!\\");
                        

                } catch (Exception e) {
                    System.out.print("/!\\ Please enter a valid number /!\\");
                }
            }

            RandomNumber randomNumber = new RandomNumber(range);
            System.out.println(randomNumber);
            
        }
    }
}

