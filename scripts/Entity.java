package comp3111.examsystem.entity;

public class Entity implements java.io.Serializable, Comparable<Member> {
    protected Long id = 0L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Entity() {
        super();
    }

    public Entity(Long id) {
        super();
        this.id = id;
    }

    public int compareTo(Member o) {
        return Long.compare(this.id, o.id);
    }
}
