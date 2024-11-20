package comp3111.examsystem.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EntityTest {

    @Test
    void testConstructor() {
        // Arrange
        int id = 1234;

        // Act
        Entity entity = new Entity(id);

        // Assert
        Assertions.assertEquals(id, entity.getId());
    }

    @Test
    void testDefaultConstructor() {
        // Arrange
        Entity entity = new Entity();

        // Act and Assert
        Assertions.assertEquals(0, entity.getId());
    }

    @Test
    void testSetId() {
        // Arrange
        Entity entity = new Entity();

        // Act
        entity.setId(1234);

        // Assert
        Assertions.assertEquals(1234, entity.getId());
    }

    @Test
    void testCompareTo() {
        // Arrange
        Member member1 = new Member();
        member1.id = 1;
        Member member2 = new Member();
        member2.id = 2;
        member2.compareTo(member1);
        // Act and Assert
        Assertions.assertTrue(member1.compareTo(member2) < 0);
    }
}