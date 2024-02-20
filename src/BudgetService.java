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

        List<Budget> budgets = budgetRepo.getAll();
        final Map<YearMonth, Double> budgetMap = budgets.stream()
                .collect(Collectors.toMap(b -> YearMonth.parse(b.yearMonth, DateTimeFormatter.ofPattern("yyyyMM")), b -> b.amount));

        if (YearMonth.from(start).equals(YearMonth.from(end))) {
            Double localAmount = budgetMap.get(YearMonth.from(end));
            localAmount = localAmount == null ? 0 : localAmount;
            final int days = end.getDayOfMonth() - start.getDayOfMonth() + 1;
            return (localAmount / start.lengthOfMonth()) * days;
        }
        Double result = 0.0;
        LocalDate current = LocalDate.of(start.getYear(), start.getMonth(), 1);
        while (!current.isAfter(end)) {
            final YearMonth currentYearMonth = YearMonth.from(current);

            Optional<Budget> currentBudget = budgets.stream().filter(b -> YearMonth.parse(b.yearMonth, DateTimeFormatter.ofPattern("yyyyMM")).equals(currentYearMonth)).findFirst();
            if (currentBudget.isPresent()) {
                final Double amonut = currentBudget.get().amount;
//                final Double amonut = budgetMap.get(YearMonth.from(current));

                if (currentYearMonth.equals(YearMonth.from(start))) {
                    final int daysOfMonth = start.lengthOfMonth() - start.getDayOfMonth() + 1;
                    result += (amonut / start.lengthOfMonth()) * daysOfMonth;
                } else if (currentYearMonth.equals(YearMonth.from(end))) {
                    final int daysOfMonth = end.getDayOfMonth();
                    result += (amonut / end.lengthOfMonth()) * daysOfMonth;
                } else {
                    result += amonut;
                }
            }
            current = current.plusMonths(1);
        }

        return result;
    }
}
