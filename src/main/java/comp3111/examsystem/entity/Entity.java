package comp3111.examsystem.entity;

public class Entity implements java.io.Serializable, Comparable<Member> {
    protected int id = 0;

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Entity() {
        super();
    }

    public Entity(int id) {
        super();
        this.id = id;
    }

    public int compareTo(Member o) {
        return Long.compare(this.id, o.id);
    }
}
