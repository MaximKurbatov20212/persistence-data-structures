package nsu;

import nsu.stuctures.list.PersistenceLinkedList;

public class    Main {
    public static void main(String[] args) {
        PersistenceLinkedList<String> persistenceLinkedList = new PersistenceLinkedList<>();

        persistenceLinkedList.push_back("first");
        persistenceLinkedList.printCurrentLinkedList();
        persistenceLinkedList.push_back("second");
        persistenceLinkedList.printCurrentLinkedList();

        persistenceLinkedList.clear();
        persistenceLinkedList.printCurrentLinkedList();
        persistenceLinkedList.undo();
        persistenceLinkedList.printCurrentLinkedList();
        persistenceLinkedList.redo();
        persistenceLinkedList.printCurrentLinkedList();

        persistenceLinkedList.push_back("first");
        persistenceLinkedList.printCurrentLinkedList();
        persistenceLinkedList.push_back("second");
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
