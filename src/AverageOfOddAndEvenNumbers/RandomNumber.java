package src.AverageOfOddAndEvenNumbers;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class RandomNumber {
    Random random = new Random();
    int[] numbers;
    int oddCpt;
    int evenCpt;
    double averageAll;
    double averageOdd;
    double averageEven;


    RandomNumber() {
        this.numbers = this.random.ints(10, 0, 1001).toArray();

        this.oddCpt = (int) IntStream.of(this.numbers).filter(n -> n % 2 != 0).count();
        this.evenCpt = (int) IntStream.of(this.numbers).filter(n -> n % 2 == 0).count();
    }

    RandomNumber(int range) {
        this.numbers = random.ints(range, 0, 1001).toArray();

        this.oddCpt = (int) IntStream.of(this.numbers).filter(n -> n % 2 != 0).count();
        this.evenCpt = (int) IntStream.of(this.numbers).filter(n -> n % 2 == 0).count();

        this.averageAll = IntStream.of(this.numbers).average().orElse(0);
        this.averageOdd = IntStream.of(this.numbers).filter(n -> n % 2 != 0).average().orElse(0);
        this.averageEven = IntStream.of(this.numbers).filter(n -> n % 2 == 0).average().orElse(0);
    }

    @Override
    public String toString() {
        return "--------------------------------------" +
                "\nOur list of numbers (size" + this.numbers.length + ") : " + Arrays.toString(this.numbers) +
                "\n|\t> Number of odd number : " + this.oddCpt +
                "\n|\t> Number of even number : " + this.evenCpt +
                "\n|\t> Average of odd number : " + this.averageOdd +
                "\n|\t> Average of even number : " + this.averageEven +
                "\n|\t> Average of all number : " + this.averageEven +
                "\n--------------------------------------";
    }
}
