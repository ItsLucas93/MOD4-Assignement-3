package src.PrimeNumberAndPrimeFactor;

import java.util.HashSet;
import java.util.Set;

public class PrimeFactors {
    private Set<Integer> factors;
    private int number;

    PrimeFactors(int number) {
        this.factors = new HashSet<>();
        this.number = number;

        this.searchFactors();
    }

    private void searchFactors() {
        int temp = this.number;
        for (int i = 2; i <= temp; i++) {
            while (temp % i == 0) {
                this.factors.add(i);
                temp /= i;
            }
        }
    }

    public boolean isPrime() {
        return factors.size() == 1;
    }

    public String displayFactors() {
        StringBuilder str = new StringBuilder("\n" + this.number + " is a not a prime number." +
                "\nHere's the following factors : ");

        for (Integer i : this.factors) {
            str.append(i).append(" ");
        }

        str.append("\nHere's the following operation to get ").append(this.number).append(" : ");

        int temp = this.number;
        for (int i = 2; i <= temp; ++i) {
            while (temp % i == 0) {
                str.append(i);
                temp /= i;

                if (temp != 1) {
                    str.append("*");
                }
            }
        }

        return str.toString();
    }

    public String displayPrime() {
        return "\n" + this.number + " is a prime number";
    }
}
