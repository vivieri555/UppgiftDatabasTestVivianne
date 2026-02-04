package service;

import com.eriksson.repo.MemberRepositoryInterface;
import com.eriksson.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

class MemberServiceTest {

    //Enhetstester här 6 st minst i memberService/ RentalService
    private MemberRepositoryInterface memberRepo;
    private MemberService memberService;

    @BeforeEach
    void setUp() {
        memberRepo = mock(MemberRepositoryInterface.class);
        memberService = new MemberService(memberRepo);
    }

    @Test
    void createMember() {

    }

    @Test
    void getAllMembers() {
    }
}