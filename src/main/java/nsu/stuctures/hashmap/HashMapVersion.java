package nsu.stuctures.hashmap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class HashMapVersion {
    UUID id;
    Integer size;
}
