package nsu.stuctures.hashmap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class HashMapNode<T> {
    private UUID versionId;
    private T value;
    private String key;
    private boolean isDeleted;
}
