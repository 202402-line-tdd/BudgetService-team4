import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

        if (YearMonth.from(start).equals(YearMonth.from(end))) {
            final Map<YearMonth, Double> budgetMap = budgets.stream()
                    .collect(Collectors.toMap(b -> YearMonth.parse(b.yearMonth, DateTimeFormatter.ofPattern("yyyyMM")), b -> b.amount));
            Double localAmount = budgetMap.get(YearMonth.from(end));
            localAmount = localAmount == null ? 0 : localAmount;
            final int days = end.getDayOfMonth() - start.getDayOfMonth() + 1;
            return (localAmount / start.lengthOfMonth()) * days;
        }
        double result = 0.0;
        LocalDate current = LocalDate.of(start.getYear(), start.getMonth(), 1);
        while (!current.isAfter(end)) {
            final YearMonth currentYearMonth = YearMonth.from(current);

            final Optional<Budget> currentBudget = budgets.stream().filter(b -> YearMonth.parse(b.yearMonth, DateTimeFormatter.ofPattern("yyyyMM")).equals(currentYearMonth)).findFirst();
            if (currentBudget.isPresent()) {
                final double amount = currentBudget.get().amount;

                final int daysOfMonth;
                if (currentYearMonth.equals(YearMonth.from(start))) {
                    daysOfMonth = start.lengthOfMonth() - start.getDayOfMonth() + 1;
                } else if (currentYearMonth.equals(YearMonth.from(end))) {
                    daysOfMonth = end.getDayOfMonth();
                } else {
                    daysOfMonth = currentYearMonth.lengthOfMonth();
                }
                final double dailyAmount = amount / currentYearMonth.lengthOfMonth();
                result += dailyAmount * daysOfMonth;
            }
            current = current.plusMonths(1);
        }

        return result;
    }
}
