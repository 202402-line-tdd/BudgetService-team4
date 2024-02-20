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

    public LocalDate firstDay() {
        return getYearMonth().atDay(1);
    }

    public long days() {
        return getYearMonth().lengthOfMonth();
    }

    public LocalDate lastDay() {
        return getYearMonth().atEndOfMonth();
    }

    double getDailyAmount() {
        return this.amount / days();
    }

    private YearMonth getYearMonth() {
        YearMonth yyyyMM = YearMonth.parse(yearMonth, DateTimeFormatter.ofPattern("yyyyMM"));
        return yyyyMM;
    }
}
