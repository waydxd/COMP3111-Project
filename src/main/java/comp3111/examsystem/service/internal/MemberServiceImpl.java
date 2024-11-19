package comp3111.examsystem.service.internal;

import comp3111.examsystem.dao.internal.MemberDAO;
import comp3111.examsystem.entity.Member;
import comp3111.examsystem.service.MemberService;

import java.util.List;

public class MemberServiceImpl implements MemberService {
    /**
     * MemberDAO object
     */
    private final MemberDAO memberDAO;

    /**
     * Constructor
     */
    public MemberServiceImpl() {
        this.memberDAO = new MemberDAO();
    }

    /**
     * Constructor
     * @param memberDAO self-defined MemberDAO object for testing
     */
    public MemberServiceImpl(MemberDAO memberDAO) {
        this.memberDAO = memberDAO;
    }

    /**
     * @param member member to be added
     */
    @Override
    public void addMember(Member member) {
        memberDAO.addMember(member);
    }

    /**
     * @param id id of member
     * @return Member with the given id
     */
    @Override
    public Member getMember(int id) {
        return memberDAO.getMember(id);
    }

    /**
     * @return List of all members
     */
    @Override
    public List<Member> getAllMembers() {
        return memberDAO.getAllMembers();
    }

    /**
     * @param member member to be updated
     */
    @Override
    public void updateMember(Member member) {
        memberDAO.updateMember(member);
    }

    /**
     * @param id id of member to be deleted
     */
    @Override
    public void deleteMember(int id) {
        memberDAO.deleteMember(id);
    }
}
