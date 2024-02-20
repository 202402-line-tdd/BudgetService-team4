import java.time.LocalDate;
import java.time.YearMonth;

import static java.time.temporal.ChronoUnit.DAYS;

public record Period(LocalDate start, LocalDate end) {
    long getOverlappingDays(Budget budget) {
        final LocalDate overlappingStart = start.isAfter(budget.firstDay()) ? start : budget.firstDay();
        final LocalDate overlappingEnd;
        if (budget.getYearMonth().equals(YearMonth.from(start()))) {
//            overlappingStart = start();
            overlappingEnd = budget.lastDay();
        } else if (budget.getYearMonth().equals(YearMonth.from(end()))) {
//            overlappingStart = budget.firstDay();
            overlappingEnd = end();
        } else {
//            overlappingStart = budget.firstDay();
            overlappingEnd = budget.lastDay();
        }
        return DAYS.between(overlappingStart, overlappingEnd) + 1;
    }
}