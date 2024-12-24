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
        linkedList.push_back(1);

        Assertions.assertEquals(linkedList.size(), 1);
        Assertions.assertTrue(isEqual(linkedList.getLinkedList(), List.of(1)));

        linkedList.push_back(2);
        Assertions.assertEquals(linkedList.size(), 2);
        Assertions.assertTrue(isEqual(linkedList.getLinkedList(), List.of(1, 2)));

        linkedList.push_back(3);
        linkedList.push_back(4);
        linkedList.push_back(5);
        Assertions.assertEquals(linkedList.size(), 5);
        Assertions.assertTrue(isEqual(linkedList.getLinkedList(), List.of(1, 2, 3, 4, 5)));
    }

    @Test
    void testPushFront() {
        PersistenceLinkedList<Integer> linkedList = new PersistenceLinkedList<>();
        linkedList.push_front(1);

        Assertions.assertEquals(linkedList.size(), 1);
        Assertions.assertTrue(isEqual(linkedList.getLinkedList(), List.of(1)));

        linkedList.push_front(2);
        Assertions.assertEquals(linkedList.size(), 2);
        Assertions.assertTrue(isEqual(linkedList.getLinkedList(), List.of(2, 1)));

        linkedList.push_front(3);
        linkedList.push_front(4);
        linkedList.push_front(5);
        Assertions.assertEquals(linkedList.size(), 5);
        Assertions.assertTrue(isEqual(linkedList.getLinkedList(), List.of(5, 4, 3, 2, 1)));
    }

    @Test
    void testBack() {
        PersistenceLinkedList<Integer> linkedList = new PersistenceLinkedList<>();
        linkedList.push_back(1);
        Assertions.assertEquals(linkedList.back(), 1);

        linkedList.push_back(2);
        Assertions.assertEquals(linkedList.back(), 2);

        linkedList.push_back(3);
        linkedList.push_back(4);
        linkedList.push_back(5);
        Assertions.assertEquals(linkedList.back(), 5);
    }

    @Test
    void testFront() {
        PersistenceLinkedList<Integer> linkedList = new PersistenceLinkedList<>();
        linkedList.push_front(1);
        Assertions.assertEquals(linkedList.front(), 1);

        linkedList.push_front(2);
        Assertions.assertEquals(linkedList.front(), 2);

        linkedList.push_front(3);
        linkedList.push_front(4);
        linkedList.push_front(5);
        Assertions.assertEquals(linkedList.front(), 5);
    }

    @Test
    void testClear() {
        PersistenceLinkedList<Integer> linkedList = new PersistenceLinkedList<>();

        linkedList.clear();
        Assertions.assertEquals(linkedList.size(), 0);

        linkedList.push_back(1);
        linkedList.push_back(2);
        linkedList.push_back(3);
        linkedList.push_back(4);
        Assertions.assertEquals(linkedList.size(), 4);

        linkedList.clear();
        Assertions.assertEquals(linkedList.size(), 0);
    }


    @Test
    void testUndo() {
        PersistenceLinkedList<Integer> linkedList = new PersistenceLinkedList<>();
        linkedList.push_back(1);
        linkedList.push_back(2);
        linkedList.undo();
        Assertions.assertTrue(isEqual(linkedList.getLinkedList(), List.of(1)));


        linkedList.push_front(0);
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
        linkedList.push_back(1);
        linkedList.push_back(2);
        linkedList.push_back(3);
        linkedList.push_back(4);
        linkedList.push_back(5);
        linkedList.undo();
        linkedList.undo();
        linkedList.undo();
        Assertions.assertTrue(isEqual(linkedList.getLinkedList(), List.of(1, 2)));

        linkedList.push_back(-1);
        linkedList.push_back(-2);
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
}

