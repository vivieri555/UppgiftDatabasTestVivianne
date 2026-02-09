package service;

import com.eriksson.entity.Member;
import com.eriksson.entity.Rental;
import com.eriksson.repo.*;
import com.eriksson.service.RentalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.eriksson.enums.RENTALTYPE.CAR;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RentalServiceTest {

    private RentalService rentalService;
    private RentalRepositoryInterface rentalRepo;
    private MemberRepositoryInterface memberRepo;
    private CarRepositoryInterface carRepo;
    private BikeRepositoryInterface bikeRepo;
    private CaravanRepositoryInterface caravanRepo;

//    Minst 6 enhetstester
// Testar service-lagret
// Repositories mockas med Mockito
// Ingen Hibernate-kod i dessa tester

    @BeforeEach
    void setUp() {
        rentalRepo = mock(RentalRepositoryInterface.class);
        memberRepo = mock(MemberRepositoryInterface.class);
        rentalService = new RentalService(rentalRepo, carRepo, bikeRepo, caravanRepo, memberRepo);
    }

    @Test
    void dateFormat_whenSaveDateFormat() {
        LocalDate result = rentalService.rentalDate("2026-01-01");
        assertEquals(LocalDate.parse("2026-01-01"), result);
    }
    @Test
    void dateDiff() {
        LocalDate rentalDate = LocalDate.of(2026, 01, 01);
        LocalDate returnDate = LocalDate.of(2026, 01, 03);
        long result = rentalService.dateDiff(rentalDate, returnDate);
        assertEquals(2, result, "Bör vara 2 dagar mellan datumen");
    }

    @Test
    void rentCar() {
       // Member member = new Member()
        Rental rental = mock(Rental.class);

    }

    @Test
    void cost_whenPremiumMember_shouldGetDiscount() {
        Member member = new Member("Vivianne", "Eriksson"
                , "vivi@email", "Premium", "h");
        BigDecimal result = rentalService.cost(member, 2);
        assertEquals(BigDecimal.valueOf(2000.5), result);
    }
    @Test
    void cost_whenStandardMember_shouldNotGetDiscount() {
        Member member = new Member("Vivianne", "Eriksson"
                , "vivi@email", "Standard", "h");
        BigDecimal result = rentalService.cost(member, 2);
        assertEquals(BigDecimal.valueOf(2101.0), result);
    }
    private static void setId(Object entity, Long id) {
        try {
            var field = entity.getClass().getDeclaredField("id");
            field.setAccessible(true);
            field.set(entity, id);
        } catch (Exception e) {
            throw new RuntimeException("Kunde inte sätta id ", e);
        }
    }
}