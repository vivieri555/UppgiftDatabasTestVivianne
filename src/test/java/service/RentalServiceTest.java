package service;

import com.eriksson.repo.*;
import com.eriksson.service.RentalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RentalServiceTest {

    private RentalService rentalService;
    private RentalRepositoryInterface rentalRepo;
    private MemberRepositoryInterface memberRepo;
    private CarRepositoryInterface carRepo;
    private BikeRepositoryInterface bikeRepo;
    private CaravanRepositoryInterface caravanRepo;

    @BeforeEach
    void setUp() {
        rentalRepo = mock(RentalRepositoryInterface.class);
        rentalService = new RentalService(rentalRepo, carRepo, bikeRepo, caravanRepo, memberRepo);
    }

//    @Test
//    void dateFormat() {
//        rentalService.dateFormat("2026-01-01");
//        verify(rentalRepo, times(1)).save(any());
//    }

    @Test
    void rentCar() {
    }
//    @Test
//    void cost() {
//        rentalService.cost(2);
//        assertEquals(2000, rentalService.cost(2));
//    }
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