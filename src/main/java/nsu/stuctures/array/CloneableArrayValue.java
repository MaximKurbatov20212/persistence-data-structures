package nsu.stuctures.array;

import java.util.Objects;

public class CloneableArrayValue<T> implements Cloneable{
    private final T value;

    public CloneableArrayValue(T value) {
        this.value = value;
    }

    @Override
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CloneableArrayValue<?> that)) return false;
        return this.value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString(){
        return value.toString();
    }
}
