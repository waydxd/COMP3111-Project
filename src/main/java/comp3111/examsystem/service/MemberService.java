package comp3111.examsystem.service;

import comp3111.examsystem.entity.Member;

import java.util.List;

public interface MemberService {
    void addMember(Member member);
    Member getMember(int id);
    List<Member> getAllMembers();
    void updateMember(Member member);
    void deleteMember(int id);
}
