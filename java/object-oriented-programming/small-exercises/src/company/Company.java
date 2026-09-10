package company;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/** Registers employees typed at the console and prints their payslips. */
public class Company {

    private final String name, foundedOn;
    private final List<Employee> staff = new ArrayList<>();

    public Company(String name, String foundedOn) {
        this.name = name;
        this.foundedOn = foundedOn;
    }

    public boolean register(Employee e) { return staff.add(e); }

    public List<String> report() {
        List<String> lines = new ArrayList<>();
        lines.add(name + " (founded " + foundedOn + ")");
        for (Employee e : staff) { lines.add(e.payslip()); }
        return lines;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Company alpha = new Company("Alpha", "21/09/2018");
        boolean more;
        do {
            System.out.print("First name: ");
            String first = in.next();
            System.out.print("Last name: ");
            String last = in.next();
            System.out.print("Birth date: ");
            String birth = in.next();
            System.out.print("Hours worked: ");
            int hours = in.nextInt();
            System.out.print("Hourly rate: ");
            float rate = in.nextFloat();
            alpha.register(new Employee(first, last, birth, hours, rate));
            System.out.print("Register another employee? (true/false) ");
            more = in.nextBoolean();
        } while (more);
        for (String line : alpha.report()) { System.out.println(line); }
    }
}
