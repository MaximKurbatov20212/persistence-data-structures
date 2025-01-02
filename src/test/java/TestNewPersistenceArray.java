import nsu.stuctures.array.PersistenceArray;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TestNewPersistenceArray {

  <T> boolean isEqual(List<T> listOne, List<T> listTwo) {
    return listOne.stream()
            .filter(element -> !listTwo.contains(element))
            .toList()
            .isEmpty();
  }

  @Test
  void testSize(){
    PersistenceArray<String> array = new PersistenceArray<>();
    array.addLast("1");
    array.addLast("2");
    array.addLast("3");
    Assertions.assertEquals(3, array.size());

    array.change(1, "spot");
    Assertions.assertEquals(3, array.size());
  }

  @Test
  void testUndo(){
    PersistenceArray<String> array2 = new PersistenceArray<>();
    array2.addLast("1");
    array2.addLast("2");
    array2.addLast("3");

    array2.change(1, "ch1");
    array2.change(1, "ch2");

    array2.undo();
    Assertions.assertEquals(3, array2.size());
  }

  @Test
  void testRedo(){
    PersistenceArray<String> array = new PersistenceArray<>();
    array.addLast("1");
    array.addLast("2");
    array.addLast("3");

    array.undo();
    array.redo();
    Assertions.assertEquals(3, array.size());
  }

  @Test
  void testUndo2(){
    PersistenceArray<String> array = new PersistenceArray<>();
    array.addLast("1");
    array.addLast("2");
    array.addLast("3");
    array.change(1, "two");

    array.undo();
    Assertions.assertEquals(3, array.size());

    array.undo();
    Assertions.assertEquals(2, array.size());

    array.change(0, "ch1");
    array.change(0, "ch2");
    array.undo();
    Assertions.assertEquals(2, array.size());
  }

  @Test
  void testAddLast(){
    PersistenceArray<String> array2 = new PersistenceArray<>();
    array2.addLast("1");
    array2.addLast("2");
    array2.addLast("3");

    Assertions.assertEquals(3, array2.size());
    Assertions.assertNotEquals(2, array2.size());
    Assertions.assertNotEquals(4, array2.size());
  }

  @Test
  void testGet(){
    PersistenceArray<String> array2 = new PersistenceArray<>();
    array2.addLast("1");
    array2.addLast("2");
    array2.addLast("3");

    Assertions.assertEquals("1", array2.get(0));
    Assertions.assertEquals("2", array2.get(1));
    Assertions.assertEquals("3", array2.get(2));
  }

  @Test
  void testChange(){
    PersistenceArray<String> array = new PersistenceArray<>();
    array.addLast("1");
    array.addLast("2");
    array.addLast("3");

    array.change(1, "pop");
    Assertions.assertEquals("pop", array.get(1));
    Assertions.assertNotEquals("2", array.get(1));
  }

  @Test
  void addLast_AfterUndo_oldListHasOwnVersions() {
    PersistenceArray<Integer> arrayList = new PersistenceArray<>();
    arrayList.addLast(1);
    arrayList.addLast(2);
    arrayList.addLast(3);

    PersistenceArray<Integer> oldList = arrayList.undo();
    Assertions.assertTrue(isEqual(oldList.getCurrentArrayList(), List.of(1, 2, 3)));
    Assertions.assertTrue(isEqual(arrayList.getCurrentArrayList(), List.of(1, 2)));

    arrayList.addLast(4);

    Assertions.assertTrue(isEqual(oldList.getCurrentArrayList(), List.of(1, 2, 3)));
  }


}
