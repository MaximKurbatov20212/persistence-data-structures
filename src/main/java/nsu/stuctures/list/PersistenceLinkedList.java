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

public class PersistenceLinkedList<T> implements UndoRedoControllable {

    private LinkedList<LinkedListFatNode<T>> list;
    private List<UUID> versions;
    private int currentVersionIndex;

    public PersistenceLinkedList() {
        final UUID id = java.util.UUID.randomUUID();
        list = new LinkedList<>();
        versions = new LinkedList<>(Collections.singletonList(id));
        currentVersionIndex = 0;
    }

    public int size() {
        return getCurrentListVersion().size();
    }

    public boolean isEmpty() {
        return getCurrentListVersion().isEmpty();
    }

    public void clear() {
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
    }

    public void push_back(T value) {
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
    }

    public void push_front(T value) {
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
    public void undo() {
        if (currentVersionIndex == 0) {
            return;
        }
        currentVersionIndex--;
    }

    @Override
    public void redo() {
        if (currentVersionIndex == (versions.size() - 1)) {
            return;
        }
        currentVersionIndex++;
    }

    public void printCurrentLinkedList() {
        System.out.println("[" + getCurrentListVersion().stream()
                .filter(n -> !n.isDeleted())
                .map(n -> String.valueOf(n.getValue()))
                .collect(Collectors.joining(", ")) + "]"
        );
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
                .map(fatNode -> fatNode.getFirstWithNotWithVersion(getAllVersionAfterCurrent()))
                .filter(Objects::nonNull)
                .filter(node -> !node.isDeleted())
                .toList();
    }

    private List<LinkedListNode<T>> getCurrentListVersionWithNulls() {
        return list.stream()
                .map(fatNode -> fatNode.getFirstWithNotWithVersion(getAllVersionAfterCurrent()))
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
}
