package comp3111.examsystem.service.internal;

import comp3111.examsystem.dao.internal.MemberDAO;
import comp3111.examsystem.entity.Member;
import comp3111.examsystem.service.MemberService;

import java.util.List;

public class MemberServiceImpl implements MemberService {
    private final MemberDAO memberDAO;

    public MemberServiceImpl() {
        this.memberDAO = new MemberDAO();
    }
    public MemberServiceImpl(MemberDAO memberDAO) {
        this.memberDAO = memberDAO;
    }

    @Override
    public void addMember(Member member) {
        memberDAO.addMember(member);
    }

    @Override
    public Member getMember(int id) {
        return memberDAO.getMember(id);
    }

    @Override
    public List<Member> getAllMembers() {
        return memberDAO.getAllMembers();
    }

    @Override
    public void updateMember(Member member) {
        memberDAO.updateMember(member);
    }

    @Override
    public void deleteMember(int id) {
        memberDAO.deleteMember(id);
    }
}
