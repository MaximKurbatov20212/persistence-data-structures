package nsu.stuctures.array;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
public class ArrayFatNode<T> {
  private final List<ArrayNode<T>> nodes;

  public ArrayFatNode(UUID id, T value) {
    this.nodes = new LinkedList<>();
    nodes.add(new ArrayNode<>(id, value));
  }

  public void add(UUID id, T value){
    nodes.add(new ArrayNode<>(id, value));
  }

  public void addFirst(ArrayNode<T> element){
    nodes.add(0, element);
  }

  public void deleteNodeByVersionId(UUID versionId) {
    nodes.removeIf(node -> node.getVersionId() == versionId);
  }

  public ArrayNode<T> getFirstNodeWithVersionInList(List<UUID> uuids){
    return nodes.stream()
            .filter(fatNode -> uuids.contains(fatNode.getVersionId()))
            .findFirst()
            .orElse(null);
  }
}
