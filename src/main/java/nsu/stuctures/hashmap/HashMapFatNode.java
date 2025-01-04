package nsu.stuctures.hashmap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
@Setter
@Getter
public class HashMapFatNode<T> {
    private List<HashMapNode<T>> nodes;

    public HashMapFatNode(UUID versionId, T value) {
        this.nodes = new LinkedList<>();
//        addNode(versionId, value, false);
    }

    public HashMapFatNode() {
        this.nodes = new LinkedList<>();
    }

    public boolean addNode(HashMapNode<T> newNode) {
        if (nodes.stream().anyMatch(n -> Objects.equals(newNode.getKey(), n.getKey()))) {
            return false;
        }
        nodes.add(newNode);
        return true;
    }

    public T get(String key) {
        HashMapNode<T> targetNode = nodes.stream()
                .filter(node -> Objects.equals(node.getKey(), key))
                .findFirst()
                .orElse(null);

        if (targetNode == null) {
            return null;
        }
        return targetNode.getValue();
    }
}
