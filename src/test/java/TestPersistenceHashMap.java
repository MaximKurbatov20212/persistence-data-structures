import nsu.stuctures.hashmap.CloneableKey;
import nsu.stuctures.hashmap.CloneableValue;
import nsu.stuctures.hashmap.PersistenceHashMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestPersistenceHashMap {
    @Test
    void emptyHashMap() {
        PersistenceHashMap<CloneableKey<String>, CloneableValue<String>> persistentMap = new PersistenceHashMap<>();
        Assertions.assertEquals(persistentMap.size(), 0);
        Assertions.assertTrue(persistentMap.isEmpty());

    }

    @Test
    void testPut() {
        PersistenceHashMap<CloneableKey<String>, CloneableValue<String>> persistentMap = new PersistenceHashMap<>();
        persistentMap.put(new CloneableKey<>("k"), new CloneableValue<>("v"));
        Assertions.assertEquals(persistentMap.size(), 1);
    }

    @Test
    void testGet() {
        PersistenceHashMap<CloneableKey<String>, CloneableValue<String>> persistentMap = new PersistenceHashMap<>();
        persistentMap.put(new CloneableKey<>("k"), new CloneableValue<>("v"));
        Assertions.assertEquals(persistentMap.get(new CloneableKey<>("k")), new CloneableValue<>("v"));
    }

    @Test
    void testClear() {
        PersistenceHashMap<CloneableKey<String>, CloneableValue<String>> persistentMap = new PersistenceHashMap<>();
        persistentMap.put(new CloneableKey<>("k"), new CloneableValue<>("v"));
        persistentMap.clear();
        Assertions.assertNull(persistentMap.get(new CloneableKey<>("k")));
    }

    @Test
    void testUndo() {
        PersistenceHashMap<CloneableKey<String>, CloneableValue<String>> persistentMap = new PersistenceHashMap<>();
        persistentMap.put(new CloneableKey<>("1"), new CloneableValue<>("1"));
        persistentMap.put(new CloneableKey<>("2"), new CloneableValue<>("2"));
        persistentMap.put(new CloneableKey<>("3"), new CloneableValue<>("3"));
        persistentMap.put(new CloneableKey<>("4"), new CloneableValue<>("4"));

        persistentMap.undo();
        persistentMap.undo();
        persistentMap.undo();
        persistentMap.undo();
        Assertions.assertTrue(persistentMap.isEmpty());
    }

    @Test
    void testRedo() {
        PersistenceHashMap<CloneableKey<String>, CloneableValue<String>> persistentMap = new PersistenceHashMap<>();
        persistentMap.put(new CloneableKey<>("1"), new CloneableValue<>("1"));
        persistentMap.put(new CloneableKey<>("2"), new CloneableValue<>("2"));
        persistentMap.put(new CloneableKey<>("3"), new CloneableValue<>("3"));
        persistentMap.put(new CloneableKey<>("4"), new CloneableValue<>("4"));

        persistentMap.undo();
        persistentMap.undo();

        Assertions.assertTrue(persistentMap.contains(new CloneableKey<>("1")));
        Assertions.assertTrue(persistentMap.contains(new CloneableKey<>("2")));
        Assertions.assertFalse(persistentMap.contains(new CloneableKey<>("3")));
        Assertions.assertFalse(persistentMap.contains(new CloneableKey<>("4")));

        persistentMap.redo();
        Assertions.assertEquals(persistentMap.size(), 3);
        Assertions.assertTrue(persistentMap.contains(new CloneableKey<>("3")));
        Assertions.assertFalse(persistentMap.contains(new CloneableKey<>("4")));

        persistentMap.redo();
        Assertions.assertTrue(persistentMap.contains(new CloneableKey<>("3")));
        Assertions.assertTrue(persistentMap.contains(new CloneableKey<>("4")));
    }
}
