import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

public record Period(LocalDate start, LocalDate end) {
    long getOverlappingDays(Budget budget) {
        final LocalDate overlappingStart = start.isAfter(budget.firstDay()) ? start : budget.firstDay();
        final LocalDate overlappingEnd = end.isBefore(budget.lastDay()) ? end : budget.lastDay();
        return DAYS.between(overlappingStart, overlappingEnd) + 1;
    }
}