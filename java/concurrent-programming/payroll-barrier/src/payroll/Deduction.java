package payroll;

/** The four payroll deductions, one per worker thread. */
public enum Deduction {
    INCOME_TAX("Income tax withheld at source") { void apply(Employee e) { e.withholdIncomeTax(); } },
    SOCIAL_SECURITY("Mandatory social security") { void apply(Employee e) { e.socialSecurity(); } },
    PRIVATE_PENSION("Private pension")           { void apply(Employee e) { e.privatePension(); } },
    HEALTH_PLAN("Health plan")                   { void apply(Employee e) { e.healthPlan(); } };

    public final String label;

    Deduction(String label) { this.label = label; }

    abstract void apply(Employee e);
}
