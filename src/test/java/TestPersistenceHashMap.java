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

//
//    @Test
//    void testGet() {
//        PersistenceHashMap<CloneableKey<String>, CloneableValue<String>> persistentMap = new PersistenceHashMap<>();
//        persistentMap.put(new CloneableKey<>("k"), new CloneableValue<>("v"));
//        Assertions.assertEquals(persistentMap.get(new CloneableKey<>("k")), new CloneableValue<>("v"));
//    }
//
//    @Test
//    void testClear() {
//        PersistenceHashMap<CloneableKey<String>, CloneableValue<String>> persistentMap = new PersistenceHashMap<>();
//        persistentMap.put(new CloneableKey<>("k"), new CloneableValue<>("v"));
//        persistentMap.clear();
//        Assertions.assertNull(persistentMap.get(new CloneableKey<>("k")));
//    }
//
//    @Test
//    void testUndo() {
//        PersistenceHashMap<CloneableKey<String>, CloneableValue<String>> persistentMap = new PersistenceHashMap<>();
//        persistentMap.put(new CloneableKey<>("1"), new CloneableValue<>("1"));
//        persistentMap.put(new CloneableKey<>("2"), new CloneableValue<>("2"));
//        persistentMap.put(new CloneableKey<>("3"), new CloneableValue<>("3"));
//        persistentMap.put(new CloneableKey<>("4"), new CloneableValue<>("4"));
//
//        persistentMap.undo();
//        persistentMap.undo();
//        persistentMap.undo();
//        persistentMap.undo();
//        Assertions.assertTrue(persistentMap.isEmpty());
//    }
//
//    @Test
//    void testRedo() {
//        PersistenceHashMap<CloneableKey<String>, CloneableValue<String>> persistentMap = new PersistenceHashMap<>();
//        persistentMap.put(new CloneableKey<>("1"), new CloneableValue<>("1"));
//        persistentMap.put(new CloneableKey<>("2"), new CloneableValue<>("2"));
//        persistentMap.put(new CloneableKey<>("3"), new CloneableValue<>("3"));
//        persistentMap.put(new CloneableKey<>("4"), new CloneableValue<>("4"));
//
//        persistentMap.undo();
//        persistentMap.undo();
//
//        Assertions.assertTrue(persistentMap.contains(new CloneableKey<>("1")));
//        Assertions.assertTrue(persistentMap.contains(new CloneableKey<>("2")));
//        Assertions.assertFalse(persistentMap.contains(new CloneableKey<>("3")));
//        Assertions.assertFalse(persistentMap.contains(new CloneableKey<>("4")));
//
//        persistentMap.redo();
//        Assertions.assertEquals(persistentMap.size(), 3);
//        Assertions.assertTrue(persistentMap.contains(new CloneableKey<>("3")));
//        Assertions.assertFalse(persistentMap.contains(new CloneableKey<>("4")));
//
//        persistentMap.redo();
//        Assertions.assertTrue(persistentMap.contains(new CloneableKey<>("3")));
//        Assertions.assertTrue(persistentMap.contains(new CloneableKey<>("4")));
//    }
}
