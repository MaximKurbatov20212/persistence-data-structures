package nsu.stuctures.array;

import nsu.UndoRedoControllable;

import java.util.ArrayList;

public class PersistenceArrayList<T> implements UndoRedoControllable {
    private final ArrayList<ArrayList<T>> versions;
    private int versionIndex;
    public PersistenceArrayList() {
        this.versions = new ArrayList<>();
        versions.add(new ArrayList<>());
        versionIndex = 0;
    }

    public int size() {
        return versions.get(versionIndex).size();
    }
    public boolean isEmpty() {
        return versions.get(versionIndex).isEmpty();
    }

    public void clear() {
        ArrayList<T> newList = new ArrayList<>();
        versions.add(newList);
        versionIndex++;
    }
    public T get(int index) {
        return getCurrentListVersion().get(index);
    }

    public void addLast(T value){
        ArrayList<T> current = getCurrentListVersion();
        ArrayList<T> newList = (ArrayList<T>) current.clone();

        try{
            T cloneValue = (T) value.getClass().getMethod("clone").invoke(value);
            newList.add(cloneValue);
            versions.add(newList);
            versionIndex++;
        }catch (Exception e){
            throw new RuntimeException("Добавляемое в конец значение не поддерживает клонирование");
        }
    }

    public void set(int index, T value) {
        ArrayList<T> currentList = getCurrentListVersion();
        ArrayList<T> newList = (ArrayList<T>) currentList.clone();

        try{
            T cloneValue = (T) value.getClass().getMethod("clone").invoke(value);
            newList.set(index, cloneValue);
            versions.add(newList);
            versionIndex++;
        }catch (IndexOutOfBoundsException e){
            throw new RuntimeException(index + " находится за пределами массива.", e);
        }catch (Exception e){
            throw new RuntimeException("Добавляемое значение в массив не поддерживает клонирование.", e);
        }
    }

    public void delete(int index) {
        ArrayList<T> currentList = getCurrentListVersion();
        ArrayList<T> newList = (ArrayList<T>) currentList.clone();
        newList.remove(index);
        versions.add(newList);
        versionIndex++;
    }
    @Override
    public void undo() {
        if (versionIndex == 0)
            return;
        versionIndex--;
    }
    @Override
    public void redo() {
        if (versionIndex == (versions.size() - 1))
            return;
        versionIndex++;
    }

    private ArrayList<T> getCurrentListVersion(){
        return versions.get(versionIndex);
    }
}
