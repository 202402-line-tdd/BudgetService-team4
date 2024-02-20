import java.time.LocalDate;

public class BudgetService {
    private final BudgetRepo budgetRepo;
//    private BudgetRepo budgetRepo;

    public BudgetService(BudgetRepo budgetRepo) {

        this.budgetRepo = budgetRepo;
    }

    public double query(LocalDate start, LocalDate end) {
        if (start.isAfter(end)) {
            return 0.00;
        }

        double result = 0.0;
        final Period period = new Period(start, end);
        for (Budget budget : budgetRepo.getAll()) {
            result += budget.overlappingAmount(period);
        }

        return result;
    }
}
