package nsu.stuctures.hashmap;


import nsu.UndoRedoControllable;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PersistenceHashMap<T> implements UndoRedoControllable<PersistenceHashMap<T>> {
    private List<HashMapFatNode<T>> list;
    private final List<HashMapVersion> versions;
    private int currentVersionIndex;
    private int capacity = 10;

    public PersistenceHashMap() {
        final UUID id = java.util.UUID.randomUUID();
        list = new LinkedList<>();

        for (int i = 0; i < capacity; i++) {
            list.add(new HashMapFatNode<>());
        }

        versions = new LinkedList<>(Collections.singletonList(new HashMapVersion(id, 0)));
        currentVersionIndex = 0;
    }

    public int size() {
        return versions.get(currentVersionIndex).getSize();
    }

    public boolean isEmpty() {
        return versions.get(currentVersionIndex).size == 0;
    }

    public void put(String key, T value) {
        final UUID id = UUID.randomUUID();

        if ((double) getCurrentHashMapSize() / capacity > 0.75) {
             resize();
        }

        int hash = hash(key, capacity);
        HashMapFatNode<T> targetFatNode = list.get(hash);

        boolean isUniqueKey = targetFatNode.addNode(new HashMapNode<>(id, value, key, false));

        if (isUniqueKey) {
            versions.add(new HashMapVersion(id, getCurrentHashMapSize() + 1));
        }

        setCurrentVersionIndexToLastVersion();
    }

    private void resize() {
        capacity *= 2;
        final List<HashMapFatNode<T>> newList = new LinkedList<>();

        for (int i = 0; i < capacity; i++) {
            newList.add(new HashMapFatNode<>());
        }

        for (HashMapFatNode<T> fatNode: list) {
            for (HashMapNode<T> node : fatNode.getNodes()) {
                int hash = hash(node.getKey(), capacity);
                HashMapFatNode<T> targetFatNode = newList.get(hash);
                targetFatNode.addNode(node);
            }
        }

        this.list = newList;
    }


    public T get(String key) {
        int hash = hash(key, capacity);
        HashMapFatNode<T> targetFatNode = list.get(hash);
        return targetFatNode.get(key);
    }

    private int getCurrentHashMapSize() {
        return versions.get(currentVersionIndex).size;
    }

    int hash(String key, int capacity) {
        final int PRIME = 7;
        int len = key.length();
        int p = 1;
        int hash = 0;
        for (int i = 0; i < len; i++) {
            hash = Math.abs((hash + key.charAt(i) * p) % capacity);
            if (p * PRIME > 0) {
                p *= PRIME;
            } else {
                p = 1;
            }
        }
        return hash;
    }

    private Map<Object, Object> getCurrentMapVersion() {
        return new HashMap<>();
    }

    private void setCurrentVersionIndexToLastVersion() {
        currentVersionIndex = versions.size() - 1;
    }

    @Override
    public void undo() {

    }

    @Override
    public void redo() {

    }

//    public void clear() {
//        deleteUnreachableVersions();
//
//        UUID newVersion = java.util.UUID.randomUUID();
//
//        list.forEach(fatNode -> {
//            if (fatNode.getNodes().isEmpty()) {
//                return;
//            }
//
//            fatNode.addNode(newVersion, null, true);
//        });
//
//        versions.add(newVersion);
//
//        setCurrentVersionIndexToLastVersion();
//    }

//    private void deleteUnreachableVersions() {
//        List<UUID> unreachableVersions = getAllNextVersion();
//
//        unreachableVersions.forEach(this::deleteVersion);
//        list.removeIf(fatNode -> fatNode.getNodes().isEmpty());
//
//        versions = getAllPreviousVersions();
//    }
//
//    private List<UUID> getAllPreviousVersions() {
//        return versions.subList(0, currentVersionIndex + 1);
//    }
//
//    private List<UUID> getAllNextVersion() {
//        return versions.subList(currentVersionIndex + 1, versions.size());
//    }
//
//    private void deleteVersion(UUID versionId) {
//        list.forEach(fatNode -> fatNode.deleteNodeByVersionId(versionId));
//    }
//
//    private void setCurrentVersionIndexToLastVersion() {
//        currentVersionIndex = versions.size() - 1;
//    }
//
//    private List<UUID> getAllVersionAfterCurrent() {
//        return getAllNextVersion();
//    }
}
