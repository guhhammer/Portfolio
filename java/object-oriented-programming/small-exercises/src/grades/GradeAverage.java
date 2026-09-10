package grades;

import java.util.Scanner;

/** First exercise: read four grades and print whether the student passed (average >= 7). */
public class GradeAverage {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        double sum = 0;
        for (int i = 1; i <= 4; i++) {
            System.out.println("Type grade " + i + ":");
            sum += in.nextDouble();
        }
        double average = sum / 4;
        System.out.println("Final average = " + average + "\n");
        System.out.println(average >= 7.0 ? "Passed." : "Failed.");
    }
}
