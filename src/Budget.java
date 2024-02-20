import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class Budget {

    public String yearMonth;
    public double amount;

    public Budget(String yearMonth, double amount) {
        this.yearMonth = yearMonth;
        this.amount = amount;
    }

    double overlappingAmount(Period period) {
        return getDailyAmount() * period.getOverlappingDays(createPeriod());
    }

    private Period createPeriod() {
        return new Period(firstDay(), lastDay());
    }

    private LocalDate firstDay() {
        return getYearMonth().atDay(1);
    }

    private long days() {
        return getYearMonth().lengthOfMonth();
    }

    private LocalDate lastDay() {
        return getYearMonth().atEndOfMonth();
    }

    private double getDailyAmount() {
        return amount / days();
    }

    private YearMonth getYearMonth() {
        return YearMonth.parse(yearMonth, DateTimeFormatter.ofPattern("yyyyMM"));
    }
}
