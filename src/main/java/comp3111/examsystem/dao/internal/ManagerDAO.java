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

    /**
     * Constructor
     * <p>
     *     This constructor initializes the DSLContext for interacting with the database
     *     using the SQLite dialect. It attempts to establish a connection to the database
     *     and sets up the DSLContext for executing SQL queries.
     *     </p>
     */
    public ManagerDAO(){
        try {
            Connection conn = DatabaseConnection.getConnection();
            this.create = DSL.using(conn, SQLDialect.SQLITE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructor
     * <p>
     *     This constructor initializes the DSLContext for interacting with the database
     *     using the SQLite dialect. It attempts to establish a connection to the database
     *     and sets up the DSLContext for executing SQL queries.
     *     </p>
     * @param conn connection to the database
     * */
    public ManagerDAO(Connection conn) {
        try {
            this.create = DSL.using(conn, SQLDialect.SQLITE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a manager to the database.
     * @param manager manager to be added
     */
    public void addManager(Manager manager) {
        create.insertInto(MANAGERS, MANAGERS.USERNAME, MANAGERS.PASSWORD)
                .values(manager.getUsername(), manager.getPassword())
                .execute();
    }

    /**
     * Retrieves a manager from the database using the manager ID.
     * <p>
     * This function fetches a manager from the database using the given manager ID.
     * </p>
     * @param id the ID of the manager to be retrieved
     * @return the manager with the given ID
     */
    public Manager getManager(int id) {
        return create.selectFrom(MANAGERS)
                .where(MANAGERS.ID.eq(id))
                .fetchOneInto(Manager.class);
    }

    /**
     * Retrieves a manager from the database using the username.
     * @param username username of the manager
     * @return the manager with the given username
     */
    public Manager getManager(String username) {
        return create.selectFrom(MANAGERS)
                .where(MANAGERS.USERNAME.eq(username))
                .fetchOneInto(Manager.class);
    }

    /**
     * Updates a manager in the database.
     * @param manager manager to be updated
     */
    public void updateManager(Manager manager) {
        create.update(MANAGERS)
                .set(MANAGERS.USERNAME, manager.getUsername())
                .set(MANAGERS.PASSWORD, manager.getPassword())
                .where(MANAGERS.ID.eq(manager.getId()))
                .execute();
    }

    /**
     * Deletes a manager from the database.
     * @param id id of manager
     */
    public void deleteManager(int id) {
        create.deleteFrom(MANAGERS)
                .where(MANAGERS.ID.eq(id))
                .execute();
    }

    /**
     * Retrieves all managers from the database.
     * @return a list of all managers
     */
    public List<Manager> getAllManagers() {
        return create.selectFrom(MANAGERS)
                .fetchInto(Manager.class);
    }
}
