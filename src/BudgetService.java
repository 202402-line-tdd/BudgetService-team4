import java.time.LocalDate;
import java.util.List;

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

        final List<Budget> budgets = budgetRepo.getAll();

//        if (YearMonth.from(start).equals(YearMonth.from(end))) {
//            final Map<YearMonth, Double> budgetMap = budgets.stream()
//                    .collect(Collectors.toMap(b -> YearMonth.parse(b.yearMonth, DateTimeFormatter.ofPattern("yyyyMM")), b -> b.amount));
//            Double localAmount = budgetMap.get(YearMonth.from(end));
//            localAmount = localAmount == null ? 0 : localAmount;
//            final int days = end.getDayOfMonth() - start.getDayOfMonth() + 1;
//            return (localAmount / start.lengthOfMonth()) * days;
//        }
        double result = 0.0;
        final Period period = new Period(start, end);
        for (Budget budget : budgets) {
            result += budget.overlappingAmount(period);
        }

        return result;
    }
}
