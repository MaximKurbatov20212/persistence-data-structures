package nsu.stuctures.array;

import nsu.UndoRedoControllable;

import java.util.*;


public class PersistenceArray<T> implements UndoRedoControllable {
  private final List<ArrayFatNode<T>> fatNodeArray;
  private List<UUID> nodeVersions;
  private int currentVersionIndex;

  public PersistenceArray(){
    final UUID id = java.util.UUID.randomUUID();
    this.fatNodeArray = new ArrayList<>();
    this.nodeVersions = new LinkedList<>(Collections.singletonList(id));
    this.currentVersionIndex = 0;
  }

  public int size(){
    return getCurrentArray().size();
  }

  public boolean isEmpty(){return getCurrentArray().isEmpty();}

  public T get(int index){
    List<ArrayNode<T>> array = getCurrentArray();
    return array.get(index).getValue();
  }

  public void addLast(T element){
    deleteUnreachableVersions();
    UUID id = getNewId();
    nodeVersions.add(id);
    fatNodeArray.add(new ArrayFatNode<>(id, element));
    currentVersionIndex++;
  }

  public void change(int index, T value){
    deleteUnreachableVersions();
    UUID id = getNewId();
    nodeVersions.add(id);
    try{
      ArrayFatNode<T> buf = fatNodeArray.get(index);
      buf.addFirst(new ArrayNode<>(id, value));
    }catch (IndexOutOfBoundsException e){
      throw new RuntimeException("Вы изменяете элемент, которого нет в массиве."
              + "\nIndex = " + index
              + "\nТекущая длина массива = " + size());
    }
    currentVersionIndex++;
  }

  @Override
  public void undo() {
    if (currentVersionIndex == 0)
      return;
    currentVersionIndex--;
  }


  @Override
  public void redo() {
    if (currentVersionIndex == (nodeVersions.size()) - 1)
      return;
    currentVersionIndex++;
  }

  private List<ArrayNode<T>> getCurrentArray(){
    List<UUID> versionsAfterCurrant = getAllVersionsAfterCurrent();
    return fatNodeArray.stream()
            .map(fatNode -> fatNode.getFirstNodeWithVersionOutOfList(versionsAfterCurrant))
            .filter(Objects::nonNull)
            .toList();
  }

  private List<UUID> getAllVersionsAfterCurrent(){
    return nodeVersions.subList(currentVersionIndex + 1, nodeVersions.size());
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

  private List<UUID> getAllNextVersion() {
    return nodeVersions.subList(currentVersionIndex + 1, nodeVersions.size());
  }
}
