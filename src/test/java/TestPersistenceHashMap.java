import nsu.stuctures.hashmap.PersistenceHashMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestPersistenceHashMap {
    @Test
    void emptyHashMap() {
        PersistenceHashMap<String> persistentMap = new PersistenceHashMap<>();
        Assertions.assertEquals(persistentMap.size(), 0);
        Assertions.assertTrue(persistentMap.isEmpty());
    }

    @Test
    void testPut() {
        PersistenceHashMap<String> persistentMap = new PersistenceHashMap<>();
        persistentMap.put("1", "a");

        Assertions.assertEquals(persistentMap.size(), 1);
    }

    @Test
    void testPut1() {
        PersistenceHashMap<String> persistentMap = new PersistenceHashMap<>();
        persistentMap.put("1", "a");
        persistentMap.put("1", "a");

        Assertions.assertEquals(persistentMap.size(), 1);
        Assertions.assertFalse(persistentMap.isEmpty());
    }

    @Test
    void testPut2() {
        PersistenceHashMap<String> persistentMap = new PersistenceHashMap<>();
        persistentMap.put("1", "a");
        persistentMap.put("2", "a");

        Assertions.assertEquals(persistentMap.size(), 2);
        Assertions.assertFalse(persistentMap.isEmpty());
    }

    @Test
    void testContains() {
        PersistenceHashMap<String> persistentMap = new PersistenceHashMap<>();
        persistentMap.put("1", "a");
        Assertions.assertTrue(persistentMap.contains("1"));
        Assertions.assertFalse(persistentMap.contains("2"));
    }

    @Test
    void testGet() {
        PersistenceHashMap<String> persistentMap = new PersistenceHashMap<>();
        persistentMap.put("1", "a");

        Assertions.assertEquals(persistentMap.get("1"), "a");
        Assertions.assertNull(persistentMap.get("2"));
    }

    @Test
    void testResize() {
        PersistenceHashMap<String> persistentMap = new PersistenceHashMap<>();
        persistentMap.put("1", "a");
        persistentMap.put("2", "a");
        persistentMap.put("3", "a");
        persistentMap.put("4", "a");
        persistentMap.put("5", "a");
        persistentMap.put("6", "a");
        persistentMap.put("7", "a");
        persistentMap.put("8", "a");

        Assertions.assertEquals(persistentMap.get("1"), "a");
        Assertions.assertEquals(persistentMap.get("2"), "a");
        Assertions.assertEquals(persistentMap.get("3"), "a");
        Assertions.assertEquals(persistentMap.get("4"), "a");
        Assertions.assertEquals(persistentMap.get("5"), "a");
        Assertions.assertEquals(persistentMap.get("6"), "a");
        Assertions.assertEquals(persistentMap.get("7"), "a");
        Assertions.assertEquals(persistentMap.get("8"), "a");

        persistentMap.put("9", "a");

        Assertions.assertEquals(persistentMap.get("1"), "a");
        Assertions.assertEquals(persistentMap.get("2"), "a");
        Assertions.assertEquals(persistentMap.get("3"), "a");
        Assertions.assertEquals(persistentMap.get("4"), "a");
        Assertions.assertEquals(persistentMap.get("5"), "a");
        Assertions.assertEquals(persistentMap.get("6"), "a");
        Assertions.assertEquals(persistentMap.get("7"), "a");
        Assertions.assertEquals(persistentMap.get("8"), "a");
        Assertions.assertEquals(persistentMap.get("9"), "a");
    }


    @Test
    void testUndo() {
        PersistenceHashMap<String> persistentMap = new PersistenceHashMap<>();
        persistentMap.put("1", "a");
        persistentMap.put("2", "a");
        Assertions.assertEquals(persistentMap.get("1"), "a");
        Assertions.assertEquals(persistentMap.get("2"), "a");
        persistentMap.undo();

        Assertions.assertEquals(persistentMap.get("1"), "a");
        Assertions.assertNull(persistentMap.get("2"));
    }


    @Test
    void testRedo() {
        PersistenceHashMap<String> persistentMap = new PersistenceHashMap<>();
        persistentMap.put("1", "a");
        persistentMap.put("2", "a");
        persistentMap.undo();
        persistentMap.undo();
        persistentMap.redo();
        persistentMap.redo();

        Assertions.assertEquals(persistentMap.get("1"), "a");
        Assertions.assertEquals(persistentMap.get("2"), "a");
    }

    @Test
    void testPutReturnOldMap() {
        PersistenceHashMap<String> persistentMap = new PersistenceHashMap<>();
        persistentMap.put("1", "a");
        PersistenceHashMap<String> old = persistentMap.put("2", "a");

        Assertions.assertEquals(old.get("1"), "a");
        Assertions.assertNull(old.get("2"));

        old.redo();
        Assertions.assertEquals(old.get("1"), "a");
        Assertions.assertNull(old.get("2"));

        persistentMap.undo();
        Assertions.assertEquals(old.get("1"), "a");
        Assertions.assertNull(old.get("2"));
    }
}
