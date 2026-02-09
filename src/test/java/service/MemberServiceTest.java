package service;

import com.eriksson.entity.Member;
import com.eriksson.repo.MemberRepositoryInterface;
import com.eriksson.service.MemberService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;


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
        memberService.createMember("Vivi", "Eriksson", "vivi@email.se", "Standard", "O");
        ArgumentCaptor<Member> captor = ArgumentCaptor.forClass(Member.class);
        verify(memberRepo).save(captor.capture());
        assertSame("Vivi", captor.getValue().getFirstName());
        assertSame("vivi@email.se", captor.getValue().getEmail());
    }

    @Test
    void getAllMembers() {
        Member member1 = new Member("Vivi", "Eriksson", "vivi@gmail.com", "Standard", "Okänt");
        Member member2 = new Member("Tomas", "Wigell", "tomas.wigell@gmail.com", "Standard", "");
        Member member3 = new Member("Nora", "Andersson", "nora34@hotmail.com", "Premium", "Hyrt en bil");
        List<Member> members = new ArrayList<>();
        members.add(member1);
        members.add(member2);
        members.add(member3);
        
        when(memberRepo.getAllMembers()).thenReturn(members);
        List<Member> result = memberService.getAllMembers();

        assertEquals(3, result.size(), "Bör innehålla 3 medlemmar");
        assertSame(member1, result.getFirst());
        assertSame(member2, result.get(1));
        assertSame(member3, result.getLast());

        verify(memberRepo).getAllMembers();
    }
}