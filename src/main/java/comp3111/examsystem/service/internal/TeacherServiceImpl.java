package comp3111.examsystem.service.internal;

import comp3111.examsystem.dao.internal.MemberDAO;
import comp3111.examsystem.entity.Teacher;
import comp3111.examsystem.service.TeacherService;

import java.util.List;
import java.util.stream.Collectors;

public class TeacherServiceImpl implements TeacherService  {
    private final MemberDAO teacherDAO;

    /**
     * Constructor
     */
    public TeacherServiceImpl() {
        this.teacherDAO = new MemberDAO();
    }

    /**
     * Constructor
     * @param teacherDAO self-defined MemberDAO object for testing
     */
    public TeacherServiceImpl(MemberDAO teacherDAO) {
        this.teacherDAO = teacherDAO;
    }
    /**
     * @param teacher teacher to be added
     */
    @Override
    public void addTeacher(Teacher teacher) {
        teacherDAO.addTeacher(teacher);
    }

    /**
     * @param id id of teacher
     * @return Teacher with the given id
     */
    @Override
    public Teacher getTeacher(int id) {
        return teacherDAO.getTeacher(id);
    }

    /**
     * @return List of all teachers
     */
    @Override
    public List<Teacher> getAllTeachers() {
        return teacherDAO.getAllTeachers();
    }

    /**
     * @param id id of teacher
     * @param teacher teacher to be updated
     */
    @Override
    public void updateTeacher(int id, Teacher teacher) {
        teacher.setId(id);
        teacherDAO.updateMember(teacher);
    }

    /**
     * @param id id of teacher to be deleted
     */
    @Override
    public void deleteTeacher(int id) {
        teacherDAO.deleteMember(id);
    }

    /**
     * @param username username of teacher
     * @param name name of teacher
     * @param department department of teacher
     *        <p>
     * This method filters the teacherTable based on the given values.
     * If the value is empty, it will not be used as a filter.
     *       </p>
     */
    @Override
    public List<Teacher> filterTeachers(String username, String name, String department) {
        return teacherDAO.getAllTeachers().stream()
                .filter(teacher -> username.isEmpty() || teacher.getUsername().toLowerCase().contains(username))
                .filter(teacher -> name.isEmpty() || teacher.getName().toLowerCase().contains(name))
                .filter(teacher -> department.isEmpty() || teacher.getDepartment().toLowerCase().contains(department))
                .collect(Collectors.toList());
    }
}
