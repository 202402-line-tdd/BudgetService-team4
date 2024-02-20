import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

public record Period(LocalDate start, LocalDate end) {
    long getOverlappingDays(Period another) {
        if (end.isBefore(another.start) || start.isAfter(another.end)) {
            return 0;
        }
        final LocalDate overlappingStart = start.isAfter(another.start) ? start : another.start;
        final LocalDate overlappingEnd = end.isBefore(another.end) ? end : another.end;
        return DAYS.between(overlappingStart, overlappingEnd) + 1;
    }
}