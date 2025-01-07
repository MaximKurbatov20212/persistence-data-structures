package nsu;

import nsu.stuctures.list.PersistenceLinkedList;

public class Main {
    public static void main(String[] args) {
        PersistenceLinkedList<String> persistenceLinkedList = new PersistenceLinkedList<>();

        persistenceLinkedList.pushBack("first");
        persistenceLinkedList.printCurrentLinkedList();
        persistenceLinkedList.pushBack("second");
        persistenceLinkedList.printCurrentLinkedList();

        persistenceLinkedList.clear();
        persistenceLinkedList.printCurrentLinkedList();
        persistenceLinkedList.undo();
        persistenceLinkedList.printCurrentLinkedList();
        persistenceLinkedList.redo();
        persistenceLinkedList.printCurrentLinkedList();

        persistenceLinkedList.pushBack("first");
        persistenceLinkedList.printCurrentLinkedList();
        persistenceLinkedList.pushBack("second");
        persistenceLinkedList.printCurrentLinkedList();

//        persistenceLinkedList.undo();
//        persistenceLinkedList.printCurrentLinkedList();
//
//        persistenceLinkedList.push_back("third");
//        persistenceLinkedList.printCurrentLinkedList();
//
//        persistenceLinkedList.undo();
//        persistenceLinkedList.printCurrentLinkedList();
//        persistenceLinkedList.undo();
//        persistenceLinkedList.printCurrentLinkedList();
    }
}
