import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

public record Period(LocalDate start, LocalDate end) {
    long getOverlappingDays(Budget budget) {
        LocalDate firstDay = budget.firstDay();
        LocalDate lastDay = budget.lastDay();
        final LocalDate overlappingStart = start.isAfter(firstDay) ? start : firstDay;
        final LocalDate overlappingEnd = end.isBefore(lastDay) ? end : lastDay;
        return DAYS.between(overlappingStart, overlappingEnd) + 1;
    }
}