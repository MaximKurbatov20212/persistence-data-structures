import nsu.stuctures.list.PersistenceLinkedList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TestPersistenceLinkedList {

    <T> boolean isEqual(List<T> listOne, List<T> listTwo) {
        return listOne.stream()
                .filter(element -> !listTwo.contains(element))
                .toList()
                .isEmpty();
    }

    @Test
    void emptyList() {
        PersistenceLinkedList<Integer> linkedList = new PersistenceLinkedList<>();
        Assertions.assertEquals(linkedList.size(), 0);
    }

    @Test
    void testPushBack() {
        PersistenceLinkedList<Integer> linkedList = new PersistenceLinkedList<>();
        linkedList.pushBack(1);

        Assertions.assertEquals(linkedList.size(), 1);
        Assertions.assertTrue(isEqual(linkedList.getLinkedList(), List.of(1)));

        linkedList.pushBack(2);
        Assertions.assertEquals(linkedList.size(), 2);
        Assertions.assertTrue(isEqual(linkedList.getLinkedList(), List.of(1, 2)));

        linkedList.pushBack(3);
        linkedList.pushBack(4);
        linkedList.pushBack(5);
        Assertions.assertTrue(isEqual(linkedList.getLinkedList(), List.of(1, 2, 3, 4, 5)));
        Assertions.assertEquals(linkedList.size(), 5);
    }

    @Test
    void testPushFront() {
        PersistenceLinkedList<Integer> linkedList = new PersistenceLinkedList<>();
        linkedList.pushFront(1);

        Assertions.assertEquals(linkedList.size(), 1);
        Assertions.assertTrue(isEqual(linkedList.getLinkedList(), List.of(1)));

        linkedList.pushFront(2);
        Assertions.assertEquals(linkedList.size(), 2);
        Assertions.assertTrue(isEqual(linkedList.getLinkedList(), List.of(2, 1)));

        linkedList.pushFront(3);
        linkedList.pushFront(4);
        linkedList.pushFront(5);
        Assertions.assertEquals(linkedList.size(), 5);
        Assertions.assertTrue(isEqual(linkedList.getLinkedList(), List.of(5, 4, 3, 2, 1)));
    }

    @Test
    void testBack() {
        PersistenceLinkedList<Integer> linkedList = new PersistenceLinkedList<>();
        linkedList.pushBack(1);
        Assertions.assertEquals(linkedList.back(), 1);

        linkedList.pushBack(2);
        Assertions.assertEquals(linkedList.back(), 2);

        linkedList.pushBack(3);
        linkedList.pushBack(4);
        linkedList.pushBack(5);
        Assertions.assertEquals(linkedList.back(), 5);
    }

    @Test
    void testFront() {
        PersistenceLinkedList<Integer> linkedList = new PersistenceLinkedList<>();
        linkedList.pushFront(1);
        Assertions.assertEquals(linkedList.front(), 1);

        linkedList.pushFront(2);
        Assertions.assertEquals(linkedList.front(), 2);

        linkedList.pushFront(3);
        linkedList.pushFront(4);
        linkedList.pushFront(5);
        Assertions.assertEquals(linkedList.front(), 5);
    }

    @Test
    void testClear() {
        PersistenceLinkedList<Integer> linkedList = new PersistenceLinkedList<>();

        linkedList.clear();
        Assertions.assertEquals(linkedList.size(), 0);

        linkedList.pushBack(1);
        linkedList.pushBack(2);
        linkedList.pushBack(3);
        linkedList.pushBack(4);
        Assertions.assertEquals(linkedList.size(), 4);

        linkedList.clear();
        Assertions.assertEquals(linkedList.size(), 0);
    }


    @Test
    void testUndo() {
        PersistenceLinkedList<Integer> linkedList = new PersistenceLinkedList<>();
        linkedList.pushBack(1);
        linkedList.pushBack(2);
        linkedList.undo();
        Assertions.assertTrue(isEqual(linkedList.getLinkedList(), List.of(1)));

        linkedList.pushFront(0);
        Assertions.assertTrue(isEqual(linkedList.getLinkedList(), List.of(0, 1)));

        linkedList.undo();
        Assertions.assertTrue(isEqual(linkedList.getLinkedList(), List.of(1)));

        linkedList.clear();
        Assertions.assertEquals(linkedList.size(), 0);
        Assertions.assertTrue(isEqual(linkedList.getLinkedList(), List.of(1)));
    }

    @Test
    void testRedo() {
        PersistenceLinkedList<Integer> linkedList = new PersistenceLinkedList<>();
        linkedList.pushBack(1);
        linkedList.pushBack(2);
        linkedList.pushBack(3);
        linkedList.pushBack(4);
        linkedList.pushBack(5);
        linkedList.undo();
        linkedList.undo();
        linkedList.undo();
        Assertions.assertTrue(isEqual(linkedList.getLinkedList(), List.of(1, 2)));

        linkedList.pushBack(-1);
        linkedList.pushBack(-2);
        Assertions.assertTrue(isEqual(linkedList.getLinkedList(), List.of(1, 2, -1, -2)));

        linkedList.undo();
        linkedList.undo();
        Assertions.assertTrue(isEqual(linkedList.getLinkedList(), List.of(1, 2)));

        linkedList.undo();
        Assertions.assertTrue(isEqual(linkedList.getLinkedList(), List.of(1)));

        linkedList.undo();
        Assertions.assertTrue(isEqual(linkedList.getLinkedList(), List.of()));

        linkedList.redo();
        Assertions.assertTrue(isEqual(linkedList.getLinkedList(), List.of(1)));

        linkedList.redo();
        Assertions.assertTrue(isEqual(linkedList.getLinkedList(), List.of(1, 2)));

        linkedList.redo();
        Assertions.assertTrue(isEqual(linkedList.getLinkedList(), List.of(1, 2, -1)));

        linkedList.redo();
        Assertions.assertTrue(isEqual(linkedList.getLinkedList(), List.of(1, 2, -1, -2)));

        linkedList.redo();
        Assertions.assertTrue(isEqual(linkedList.getLinkedList(), List.of(1, 2, -1, -2)));
    }

    @Test
    void pushBack_AfterUndo_oldListHasOwnVersions() {
        PersistenceLinkedList<Integer> linkedList = new PersistenceLinkedList<>();
        linkedList.pushBack(1);
        linkedList.pushBack(2);
        linkedList.pushBack(3);

        PersistenceLinkedList<Integer> oldList = linkedList.undo();
        Assertions.assertTrue(isEqual(oldList.getLinkedList(), List.of(1, 2, 3)));
        Assertions.assertTrue(isEqual(linkedList.getLinkedList(), List.of(1, 2)));

        linkedList.pushBack(4);

        Assertions.assertTrue(isEqual(oldList.getLinkedList(), List.of(1, 2, 3)));
    }
}

