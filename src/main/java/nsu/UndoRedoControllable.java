package nsu;

public interface UndoRedoControllable<T> {
    T undo();
    T redo();
}
