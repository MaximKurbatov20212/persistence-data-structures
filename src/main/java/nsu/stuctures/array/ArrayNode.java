package nsu.stuctures.array;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class ArrayNode<T> {
  private UUID versionId;
  private T value;
}
