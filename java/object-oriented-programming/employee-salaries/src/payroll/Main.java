package payroll;

import java.util.ArrayList;
import java.util.List;

/** Inheritance and method overriding: the same salary() call for employees, salespeople and managers.
 *  OOP course, PUCPR (2018). */
public class Main {

    public static void main(String[] args) {
        List<Employee> staff = new ArrayList<>();
        staff.add(new Employee(150, 10, "Joao"));
        staff.add(new Employee(140, 12, "Ded"));
        staff.add(new Employee(90, 5, "Gustavo"));
        staff.add(new Salesperson(200, 15, "Daisy"));
        staff.add(new Salesperson(200, 7, "Sam"));
        staff.add(new Manager(150, 10, "Eve"));

        for (Employee e : staff) {
            System.out.printf("%-12s %-11s monthly: %9.2f   five months: %10.2f%n",
                    e.getName(), e.getClass().getSimpleName(), e.salary(), e.salary(5));
        }
    }
}
