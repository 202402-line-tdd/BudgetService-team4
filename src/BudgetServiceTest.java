import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;

public class BudgetServiceTest {
    BudgetService budgetService;
    private FakeBudgetRepo budgetRepo;

    @BeforeEach
    public void setUp() {
        budgetRepo = new FakeBudgetRepo();
        budgetService = new BudgetService(budgetRepo);
    }

    @Test
    public void invalidPeriod() {
        assertEquals(0.00, budgetService.query(LocalDate.of(2024, 2, 2), LocalDate.of(2024, 2, 1)));
    }

    @Test
    public void getADay() {
        budgetRepo.setBudgets(List.of(new Budget("202401", 310)));
        assertEquals(10, budgetService.query(LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 1)));
    }

    @Test
    public void getMultipleMonths() {
        budgetRepo.setBudgets(
                List.of(
                        new Budget("202401", 310),
                        new Budget("202402", 29),
                        new Budget("202404", 3000)
                )
        );

        assertEquals(
                239,
                budgetService.query(
                        LocalDate.of(2024, 1, 31),
                        LocalDate.of(2024, 4, 2)
                )
        );
    }

    @Test
    public void getNoData() {
        budgetRepo.setBudgets(
                List.of(
                        new Budget("202401", 310),
                        new Budget("202402", 29),
                        new Budget("202404", 3000)
                )
        );

        assertEquals(
                0,
                budgetService.query(
                        LocalDate.of(2024, 3, 1),
                        LocalDate.of(2024, 3, 3)
                )
        );
    }


}