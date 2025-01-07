package nsu.stuctures.hashmap;


import nsu.UndoRedoControllable;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
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

    public PersistenceHashMap(PersistenceHashMap<T> other) {
        this.capacity = other.capacity;
        this.list = other.list;

        versions = new LinkedList<>();
        versions.addAll(other.versions);

        currentVersionIndex = other.currentVersionIndex;
    }

    public int size() {
        return versions.get(currentVersionIndex).getSize();
    }

    public boolean isEmpty() {
        return versions.get(currentVersionIndex).size == 0;
    }

    public PersistenceHashMap<T> put(String key, T value) {
        PersistenceHashMap<T> oldMap = new PersistenceHashMap<>(this);
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
        return oldMap;
    }

    public T get(String key) {
        int hash = hash(key, capacity);
        HashMapFatNode<T> targetFatNode = list.get(hash);
        return targetFatNode.get(key, versions.subList(0, currentVersionIndex + 1));
    }

    public boolean contains(String key) {
        return get(key) != null;
    }

    @Override
    public PersistenceHashMap<T> undo() {
        PersistenceHashMap<T> oldMap = new PersistenceHashMap<>(this);
        if (currentVersionIndex == 0) {
            return this;
        }
        currentVersionIndex--;
        return oldMap;
    }

    @Override
    public PersistenceHashMap<T> redo() {
        PersistenceHashMap<T> oldMap = new PersistenceHashMap<>(this);
        if (currentVersionIndex == (versions.size() - 1)) {
            return this;
        }
        currentVersionIndex++;
        return oldMap;
    }

    private int getCurrentHashMapSize() {
        return versions.get(currentVersionIndex).size;
    }

    int hash(String key, int capacity) {
        return key.hashCode() % capacity;
    }

    private void setCurrentVersionIndexToLastVersion() {
        currentVersionIndex = versions.size() - 1;
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
}
