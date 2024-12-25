import nsu.stuctures.array.CloneableArrayValue;
import nsu.stuctures.array.PersistenceArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestPersistenceArray {
    @Test
    void emptyArray(){
        PersistenceArrayList<CloneableArrayValue<String>> persistenceArrayList = new PersistenceArrayList<>();
        Assertions.assertEquals(persistenceArrayList.size(), 0);
        Assertions.assertTrue(persistenceArrayList.isEmpty());
    }

    @Test
    void testAdd(){
        PersistenceArrayList<CloneableArrayValue<String>> persistenceArrayList = new PersistenceArrayList<>();
        persistenceArrayList.addLast(new CloneableArrayValue<>("one"));
        persistenceArrayList.addLast(new CloneableArrayValue<>("two"));
        Assertions.assertEquals(persistenceArrayList.size(), 2);
    }

    @Test
    void testSet(){
        PersistenceArrayList<CloneableArrayValue<String>> persistenceArrayList = new PersistenceArrayList<>();
        persistenceArrayList.addLast(new CloneableArrayValue<>("One"));
        persistenceArrayList.set(0, new CloneableArrayValue<>("value"));
        Assertions.assertEquals(persistenceArrayList.get(0), new CloneableArrayValue<>("value"));
    }

    @Test
    void testClear(){
        PersistenceArrayList<CloneableArrayValue<String>> persistenceArrayList = new PersistenceArrayList<>();
        persistenceArrayList.addLast(new CloneableArrayValue<>("One"));
        persistenceArrayList.addLast(new CloneableArrayValue<>("Two"));
        persistenceArrayList.addLast(new CloneableArrayValue<>("Three"));
        persistenceArrayList.clear();
        Assertions.assertEquals(persistenceArrayList.size(), 0);
    }

    @Test
    void testDelete(){
        PersistenceArrayList<CloneableArrayValue<String>> persistenceArrayList = new PersistenceArrayList<>();
        persistenceArrayList.addLast(new CloneableArrayValue<>("One"));
        persistenceArrayList.addLast(new CloneableArrayValue<>("Two"));
        persistenceArrayList.addLast(new CloneableArrayValue<>("Three"));
        persistenceArrayList.delete(1);

        Assertions.assertEquals(persistenceArrayList.size(), 2);
    }

    @Test
    void testUndo(){
        PersistenceArrayList<CloneableArrayValue<String>> persistenceArrayList = new PersistenceArrayList<>();
        persistenceArrayList.addLast(new CloneableArrayValue<>("One"));
        persistenceArrayList.addLast(new CloneableArrayValue<>("Two"));
        persistenceArrayList.addLast(new CloneableArrayValue<>("Three"));
        persistenceArrayList.addLast(new CloneableArrayValue<>("Four"));
        persistenceArrayList.addLast(new CloneableArrayValue<>("Five"));

        persistenceArrayList.undo();
        persistenceArrayList.undo();
        Assertions.assertEquals(persistenceArrayList.size(), 3);
        Assertions.assertNotEquals(persistenceArrayList.size(), 5);
    }

    @Test
    void testRedo(){
        PersistenceArrayList<CloneableArrayValue<String>> persistenceArrayList = new PersistenceArrayList<>();
        persistenceArrayList.addLast(new CloneableArrayValue<>("One"));
        persistenceArrayList.addLast(new CloneableArrayValue<>("Two"));
        persistenceArrayList.addLast(new CloneableArrayValue<>("Three"));
        persistenceArrayList.addLast(new CloneableArrayValue<>("Four"));
        persistenceArrayList.addLast(new CloneableArrayValue<>("Five"));

        persistenceArrayList.clear();
        Assertions.assertEquals(persistenceArrayList.size(), 0);

        persistenceArrayList.redo();
        Assertions.assertNotEquals(persistenceArrayList.size(), 5);
    }
}
