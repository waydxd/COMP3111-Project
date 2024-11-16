package comp3111.examsystem.dao.internal;

import comp3111.examsystem.DatabaseConnection;
import comp3111.examsystem.entity.Manager;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static com.examsystem.jooq.generated.Tables.MANAGERS;

public class ManagerDAO {
    private DSLContext create;

    public ManagerDAO(){
        try {
            Connection conn = DatabaseConnection.getConnection();
            this.create = DSL.using(conn, SQLDialect.SQLITE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addManager(Manager manager) {
        create.insertInto(MANAGERS, MANAGERS.USERNAME, MANAGERS.PASSWORD)
                .values(manager.getUsername(), manager.getPassword())
                .execute();
    }

    public Manager getManager(int id) {
        return create.selectFrom(MANAGERS)
                .where(MANAGERS.ID.eq(id))
                .fetchOneInto(Manager.class);
    }

    public Manager getManager(String username) {
        return create.selectFrom(MANAGERS)
                .where(MANAGERS.USERNAME.eq(username))
                .fetchOneInto(Manager.class);
    }

    public void updateManager(Manager manager) {
        create.update(MANAGERS)
                .set(MANAGERS.USERNAME, manager.getUsername())
                .set(MANAGERS.PASSWORD, manager.getPassword())
                .where(MANAGERS.ID.eq(manager.getId()))
                .execute();
    }

    public void deleteManager(int id) {
        create.deleteFrom(MANAGERS)
                .where(MANAGERS.ID.eq(id))
                .execute();
    }

    public List<Manager> getAllManagers() {
        return create.selectFrom(MANAGERS)
                .fetchInto(Manager.class);
    }

    public Manager getManagerByUsername(String username) {
        return create.selectFrom(MANAGERS)
                .where(MANAGERS.USERNAME.eq(username))
                .fetchOneInto(Manager.class);
    }
}
