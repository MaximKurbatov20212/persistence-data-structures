package nsu.stuctures.array;

import nsu.UndoRedoControllable;

import java.util.ArrayList;

public class PersistenceArrayList<T> implements UndoRedoControllable {
    private ArrayList<ArrayList<T>> versions;
    private int versionIndex = 0;
    public PersistenceArrayList() {
        /*
        * так как мы заранее объявили ArrayList<ArrayList<T>>,
        * то jvm знает, сколько памяти нужно выделить под эту коллекцию
        * */
        this.versions = new ArrayList<>();
    }

    public int size() {
        return versions.get(versionIndex).size();
    }
    public boolean isEmpty() {
        return versions.get(versionIndex).isEmpty();
    }
    public void clear() {
        // todo Epov
    }
    public T get(int index) {
        return getCurrentListVersion().get(index);
    }
    public void set(int index, T value) {
        ArrayList<T> currentList = getCurrentListVersion();
        ArrayList<T> newList = (ArrayList<T>) currentList.clone();

        try{
            T cloneValue = (T) value.getClass().getMethod("clone").invoke(value);
            newList.add(cloneValue);
            versions.set(index, newList);
            versionIndex++;
        }catch (Exception e){
            throw new RuntimeException("Добавляемое значение в массив не поддерживает клонирование.", e);
        }
    }

    private ArrayList<T> getCurrentListVersion(){
        return versions.get(versionIndex);
    }

    public void delete(int index) {
        // todo Epov
    }
    @Override
    public void undo() {
        // todo Epov
    }
    @Override
    public void redo() {
        // todo Epov
    }

    public static void main(String[] args) {
        PersistenceArrayList<String> arrayList = new PersistenceArrayList<>();

    }
}
