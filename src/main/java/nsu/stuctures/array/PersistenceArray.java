package nsu.stuctures.array;

import nsu.UndoRedoControllable;

import java.util.*;
import java.util.stream.Collectors;


public class PersistenceArray<T> implements UndoRedoControllable<PersistenceArray<T>> {
  private final List<ArrayFatNode<T>> fatNodeArray;
  private List<UUID> nodeVersions;
  private int currentVersionIndex;

  public PersistenceArray() {
    final UUID id = java.util.UUID.randomUUID();
    this.fatNodeArray = new ArrayList<>();
    this.nodeVersions = new LinkedList<>(Collections.singletonList(id));
    this.currentVersionIndex = 0;
  }

  private PersistenceArray(PersistenceArray<T> other) {
    fatNodeArray = other.fatNodeArray;
    currentVersionIndex = other.currentVersionIndex;

    nodeVersions = new LinkedList<>();
    nodeVersions.addAll(other.nodeVersions);
  }

  public int size() {
    return getCurrentArray().size();
  }

  public boolean isEmpty() {
    return getCurrentArray().isEmpty();
  }

  public T get(int index){
    List<ArrayNode<T>> array = getCurrentArray();
    return array.get(index).getValue();
  }

  public PersistenceArray<T> addLast(T element) {
    PersistenceArray<T> oldArray = new PersistenceArray<>(this);
    deleteUnreachableVersions();

    UUID id = getNewId();

    nodeVersions.add(id);
    fatNodeArray.add(new ArrayFatNode<>(id, element));
    setCurrentVersionIndexToLastVersion();

    return oldArray;
  }

  public PersistenceArray<T> change(int index, T value){
    PersistenceArray<T> oldArray = new PersistenceArray<>(this);
    deleteUnreachableVersions();

    UUID id = getNewId();
    nodeVersions.add(id);
    try {
      ArrayFatNode<T> buf = fatNodeArray.get(index);
      buf.addFirst(new ArrayNode<>(id, value));
    } catch (IndexOutOfBoundsException e){
      throw new RuntimeException("Вы изменяете элемент, которого нет в массиве."
              + "\nIndex = " + index
              + "\nТекущая длина массива = " + size());
    }

    setCurrentVersionIndexToLastVersion();
    return oldArray;
  }

  @Override
  public PersistenceArray<T> undo() {
    PersistenceArray<T> oldArray = new PersistenceArray<>(this);
    if (currentVersionIndex == 0) {
      return this;
    }
    currentVersionIndex--;
    return oldArray;
  }

  @Override
  public PersistenceArray<T> redo() {
    PersistenceArray<T> oldArray = new PersistenceArray<>(this);
    if (currentVersionIndex == (nodeVersions.size()) - 1) {
      return this;
    }
    currentVersionIndex++;
    return oldArray;
  }

   private ArrayList<ArrayNode<T>> getCurrentArray(){
    List<UUID> previousVersions = getAllPreviousVersions();

    return fatNodeArray.stream()
            .map(fatNode -> fatNode.getFirstNodeWithVersionInList(previousVersions))
            .filter(Objects::nonNull)
            .collect(Collectors.toCollection(ArrayList::new));
  }

  public ArrayList<T> getCurrentArrayList() {
    List<UUID> previousVersions = getAllPreviousVersions();

    return fatNodeArray.stream()
            .map(fatNode -> fatNode.getFirstNodeWithVersionInList(previousVersions))
            .filter(Objects::nonNull)
            .map(ArrayNode::getValue)
            .collect(Collectors.toCollection(ArrayList::new));
  }

  private UUID getNewId(){
    return java.util.UUID.randomUUID();
  }

  private void deleteUnreachableVersions() {
    List<UUID> unreachableVersions = getAllNextVersion();

    unreachableVersions.forEach(this::deleteVersionInFatNodeArray);
    fatNodeArray.removeIf(fatNode -> fatNode.getNodes().isEmpty());
    nodeVersions = getAllPreviousVersions();
  }

  private List<UUID> getAllPreviousVersions() {
    return nodeVersions.subList(0, currentVersionIndex + 1);
  }

  private void deleteVersionInFatNodeArray(UUID versionId){
    fatNodeArray.forEach(fatNode -> fatNode.deleteNodeByVersionId(versionId));
  }

  private void setCurrentVersionIndexToLastVersion() {
    currentVersionIndex = nodeVersions.size() - 1;
  }

  private List<UUID> getAllNextVersion() {
    return nodeVersions.subList(currentVersionIndex + 1, nodeVersions.size());
  }
}
