package payroll;

import java.util.Random;

/** An employee with a random gross salary and the four deductions applied by the workers. */
public class Employee {

    public final int code;
    public final double grossSalary;
    public double incomeTaxDeduction = 0,
                  socialSecurityDeduction = 0,
                  privatePensionDeduction = 0,
                  healthPlanDeduction = 0,
                  totalDeductions = 0,
                  netSalary;

    public Employee(int code) {
        this.code = code;
        this.grossSalary = 1000 + new Random().nextInt(5000 - 1000);
        this.netSalary = grossSalary;
    }

    private void deduct(double value) {
        totalDeductions += value;
        netSalary -= value;
    }

    public void withholdIncomeTax()  { incomeTaxDeduction = 0.20 * grossSalary;      deduct(incomeTaxDeduction); }
    public void socialSecurity()     { socialSecurityDeduction = 0.08 * grossSalary; deduct(socialSecurityDeduction); }
    public void privatePension()     { privatePensionDeduction = 0.04 * grossSalary; deduct(privatePensionDeduction); }
    public void healthPlan()         { healthPlanDeduction = 0.02 * grossSalary;     deduct(healthPlanDeduction); }

    public String report() {
        return "ID: " + code
             + "\nGross salary: " + grossSalary
             + "\nNet salary: " + netSalary
             + "\nDeduction (income tax): " + incomeTaxDeduction
             + "\nDeduction (social security): " + socialSecurityDeduction
             + "\nDeduction (private pension): " + privatePensionDeduction
             + "\nDeduction (health plan): " + healthPlanDeduction
             + "\nTotal deductions: " + totalDeductions
             + "\n\n\n";
    }
}
