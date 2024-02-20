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

        final Period period = new Period(start, end);

        return budgetRepo.getAll().stream()
                .mapToDouble(budget -> budget.overlappingAmount(period))
                .sum();
    }
}
