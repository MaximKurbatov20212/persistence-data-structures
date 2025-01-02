package nsu.stuctures.list;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Setter
@Getter
public class LinkedListFatNode<T> {
    private LinkedList<LinkedListNode<T>> nodes;

    public LinkedListFatNode(UUID versionId, T value) {
        this.nodes = new LinkedList<>();
        addNode(versionId, value, false);
    }

    public LinkedListNode<T> getNodeByVersionId(UUID versionId) {
        return nodes.stream()
                .filter(node -> node.getVersionId() == versionId)
                .findAny()
                .orElse(null);
    }

    public void addNode(UUID versionId, T value, boolean isDeleted) {
        nodes.addFirst(new LinkedListNode<>(versionId, value, isDeleted));
    }

    public void deleteNodeByVersionId(UUID versionId) {
        nodes.removeIf(node -> node.getVersionId() == versionId);
    }

    public LinkedListNode<T> getFirstNodeWithVersionOutOfList(List<UUID> uuids) {
        return nodes.stream()
                .filter(node -> !uuids.contains(node.getVersionId()))
                .findFirst()
                .orElse(null);
    }
}
