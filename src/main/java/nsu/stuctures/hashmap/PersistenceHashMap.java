package nsu.stuctures.hashmap;

import lombok.Getter;
import nsu.UndoRedoControllable;

import java.util.HashMap;
import java.util.LinkedList;

@Getter
public class PersistenceHashMap<T extends Cloneable, V extends Cloneable> implements UndoRedoControllable {
    private final LinkedList<HashMap<T, V>> maps; // версии

    private int currentVersionIndex;

    public PersistenceHashMap() {
        maps = new LinkedList<>();
        maps.add(new HashMap<>());
        currentVersionIndex = 0;
    }

    public int size() {
        return getCurrentHashMapVersion().size();
    }

    public boolean isEmpty() {
        return getCurrentHashMapVersion().isEmpty();
    }

    public void clear() {
        deleteUnreachableVersion();
        maps.add(new HashMap<>());
        setCurrentVersionIndexToLastVersion();
    }

    public void put(T key, V value) {
        deleteUnreachableVersion();
        HashMap<T, V> currentHashMap = getCurrentHashMapVersion();
        HashMap<T, V> newHashMap = (HashMap<T, V>) currentHashMap.clone();

        try {
            T clonedKey = (T) key.getClass().getMethod("clone").invoke(key);
            V clonedValue = (V) value.getClass().getMethod("clone").invoke(value);
            newHashMap.put(clonedKey, clonedValue);
        } catch (Exception e) {
            throw new RuntimeException("Ключ или значение не поддерживает клонирование", e);
        }

        maps.add(newHashMap);

        setCurrentVersionIndexToLastVersion();
    }

    public V get(T key) {
        return getCurrentHashMapVersion().get(key);
    }

    public boolean contains(T key) {
        return getCurrentHashMapVersion().containsKey(key);
    }

    private HashMap<T, V> getCurrentHashMapVersion() {
        return maps.get(currentVersionIndex);
    }

    private void deleteUnreachableVersion() {
        for (int i = currentVersionIndex + 1; i < maps.size(); i++) {
            maps.remove(maps.get(i));
        }
    }

    private void setCurrentVersionIndexToLastVersion() {
        currentVersionIndex = maps.size() - 1;
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
        if (currentVersionIndex == (maps.size() - 1)) {
            return;
        }
        currentVersionIndex++;
    }
}
