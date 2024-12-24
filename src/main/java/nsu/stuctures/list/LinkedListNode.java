package nsu.stuctures.list;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class LinkedListNode<T> {
    private UUID versionId;
    private T value;
    private boolean isDeleted;
}
