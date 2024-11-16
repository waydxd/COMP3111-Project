package comp3111.examsystem.service.internal;

import comp3111.examsystem.dao.internal.MemberDAO;
import comp3111.examsystem.entity.Teacher;
import comp3111.examsystem.service.TeacherService;

import java.util.List;

public class TeacherServiceImpl implements TeacherService  {
    private final MemberDAO teacherDAO = new MemberDAO();
    /**
     * @param teacher
     */
    @Override
    public void addTeacher(Teacher teacher) {
        teacherDAO.addTeacher(teacher);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Teacher getTeacher(int id) {
        return teacherDAO.getTeacher(id);
    }

    /**
     * @return
     */
    @Override
    public List<Teacher> getAllTeachers() {
        return teacherDAO.getAllTeachers();
    }

    /**
     * @param id
     * @param teacher
     */
    @Override
    public void updateTeacher(int id, Teacher teacher) {
        teacher.setId(id);
        teacherDAO.updateMember(teacher);
    }

    /**
     * @param id
     */
    @Override
    public void deleteTeacher(int id) {
        teacherDAO.deleteMember(id);
    }
}
