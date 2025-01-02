package nsu.stuctures.list;

import nsu.UndoRedoControllable;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PersistenceLinkedList<T> implements UndoRedoControllable<PersistenceLinkedList<T>> {

    private LinkedList<LinkedListFatNode<T>> list;
    private List<UUID> versions;
    private int currentVersionIndex;

    public PersistenceLinkedList() {
        final UUID id = java.util.UUID.randomUUID();
        list = new LinkedList<>();
        versions = new LinkedList<>(Collections.singletonList(id));
        currentVersionIndex = 0;
    }

    private PersistenceLinkedList(PersistenceLinkedList<T> other) {
        list = other.list;
        currentVersionIndex = other.currentVersionIndex;

        versions = new LinkedList<>();
        versions.addAll(other.versions);
    }

    public int size() {
        return getCurrentListVersion().size();
    }

    public boolean isEmpty() {
        return getCurrentListVersion().isEmpty();
    }

    public PersistenceLinkedList<T> clear() {
        PersistenceLinkedList<T> oldList = new PersistenceLinkedList<>(this);
        deleteUnreachableVersions();

        UUID newVersion = java.util.UUID.randomUUID();

        list.forEach(fatNode -> {
            if (fatNode.getNodes().isEmpty()) {
                return;
            }

            fatNode.addNode(newVersion, null, true);
        });

        versions.add(newVersion);

        setCurrentVersionIndexToLastVersion();
        return oldList;
    }

    public PersistenceLinkedList<T> pushBack(T value) {
        PersistenceLinkedList<T> oldList = new PersistenceLinkedList<>(this);
        deleteUnreachableVersions();

        List<LinkedListNode<T>> currentList = getCurrentListVersionWithNulls();

        final UUID id = java.util.UUID.randomUUID();
        versions.add(id);

        OptionalInt lastNonNullIndex = IntStream.iterate(currentList.size() - 1, i -> i >= 0, i -> i - 1)
                .filter(i -> currentList.get(i) == null || currentList.get(i).isDeleted())
                .findFirst();

        lastNonNullIndex.ifPresentOrElse(
                i -> list.get(i).addNode(id, value, false),
                () -> list.addLast(new LinkedListFatNode<>(id, value))
        );

        setCurrentVersionIndexToLastVersion();

        return oldList;
    }

    public PersistenceLinkedList<T> pushFront(T value) {
        PersistenceLinkedList<T> oldList = new PersistenceLinkedList<>(this);
        deleteUnreachableVersions();

        List<LinkedListNode<T>> currentList = getCurrentListVersionWithNulls();

        final UUID id = java.util.UUID.randomUUID();
        versions.add(id);

        OptionalInt firstNonNullIndex = IntStream.range(0, currentList.size())
                .filter(i -> currentList.get(i) == null || currentList.get(i).isDeleted())
                .findFirst();

        firstNonNullIndex.ifPresentOrElse(
                i -> list.get(i).addNode(id, value, false),
                () -> list.addFirst(new LinkedListFatNode<>(id, value))
        );

        setCurrentVersionIndexToLastVersion();

        return oldList;
    }

    public T back() {
        List<LinkedListNode<T>> currentList = getCurrentListVersion();

        if (currentList.isEmpty()) {
            return null;
        }

        return currentList.get(currentList.size() - 1).getValue();
    }

    public T front() {
        List<LinkedListNode<T>> currentList = getCurrentListVersion();

        if (currentList.isEmpty()) {
            return null;
        }

        return currentList.get(0).getValue();
    }

    @Override
    public PersistenceLinkedList<T> undo() {
        PersistenceLinkedList<T> oldList = new PersistenceLinkedList<>(this);
        if (currentVersionIndex == 0) {
            return this;
        }
        currentVersionIndex--;
        return oldList;
    }

    @Override
    public PersistenceLinkedList<T> redo() {
        PersistenceLinkedList<T> oldList = new PersistenceLinkedList<>(this);
        if (currentVersionIndex == (versions.size() - 1)) {
            return this;
        }
        currentVersionIndex++;
        return oldList;
    }


    private void deleteUnreachableVersions() {
        List<UUID> unreachableVersions = getAllNextVersion();
        
        unreachableVersions.forEach(this::deleteVersion);
        list.removeIf(fatNode -> fatNode.getNodes().isEmpty());
        versions = getAllPreviousVersions();
    }

    private List<UUID> getAllPreviousVersions() {
        return versions.subList(0, currentVersionIndex + 1);
    }

    private List<UUID> getAllNextVersion() {
        return versions.subList(currentVersionIndex + 1, versions.size());
    }

    private void deleteVersion(UUID versionId) {
        list.forEach(fatNode -> fatNode.deleteNodeByVersionId(versionId));
    }

    private List<LinkedListNode<T>> getCurrentListVersion() {
        return list.stream()
                .map(fatNode -> fatNode.getFirstNodeWithVersionInList(getAllPreviousVersions()))
                .filter(Objects::nonNull)
                .filter(node -> !node.isDeleted())
                .toList();
    }

    private List<LinkedListNode<T>> getCurrentListVersionWithNulls() {
        return list.stream()
                .map(fatNode -> fatNode.getFirstNodeWithVersionInList(getAllVersionAfterCurrent()))
                .collect(Collectors.toList());
    }

    private void setCurrentVersionIndexToLastVersion() {
        currentVersionIndex = versions.size() - 1;
    }

    private List<UUID> getAllVersionAfterCurrent() {
        return getAllNextVersion();
    }

    public List<T> getLinkedList() {
        return getCurrentListVersion().stream()
                .map(LinkedListNode::getValue)
                .collect(Collectors.toList());
    }

    public void printCurrentLinkedList() {
        System.out.println("[" + getCurrentListVersion().stream()
                .filter(n -> !n.isDeleted())
                .map(n -> String.valueOf(n.getValue()))
                .collect(Collectors.joining(", ")) + "]"
        );
    }
}
