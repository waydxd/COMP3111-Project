package comp3111.examsystem.service;

import comp3111.examsystem.dao.internal.MemberDAO;
import comp3111.examsystem.entity.Member;
import comp3111.examsystem.service.internal.MemberServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MemberServiceTest {

    @Mock
    private MemberDAO memberDAO;

    @InjectMocks
    private MemberServiceImpl memberService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        memberService = new MemberServiceImpl(memberDAO);
    }

    @Test
    void addMember() {
        Member member = new Member("user1", "password1", "John Doe", "Male", "25", "CS");

        memberService.addMember(member);

        verify(memberDAO).addMember(member);
    }

    @Test
    void getMember() {
        Member mockMember = new Member("user1", "password1", "John Doe", "Male", "25", "CS");
        when(memberDAO.getMember(1)).thenReturn(mockMember);

        Member result = memberService.getMember(1);

        verify(memberDAO).getMember(1);
        assertNotNull(result);
        assertEquals(mockMember.getUsername(), result.getUsername());
        assertEquals(mockMember.getName(), result.getName());
    }

    @Test
    void getAllMembers() {
        List<Member> mockMembers = Arrays.asList(new Member(), new Member());
        when(memberDAO.getAllMembers()).thenReturn(mockMembers);

        List<Member> result = memberService.getAllMembers();

        verify(memberDAO).getAllMembers();
        assertEquals(mockMembers, result);
    }

    @Test
    void updateMember() {
        Member member = new Member("user1", "password1", "John Doe", "Male", "25", "CS");
        member.setId(1);

        memberService.updateMember(member);

        verify(memberDAO).updateMember(member);
    }

    @Test
    void deleteMember() {
        memberService.deleteMember(1);

        verify(memberDAO).deleteMember(1);
    }
}