package nsu.stuctures.hashmap;

public class CloneableValue<T> implements Cloneable {
    private final T value;

    public CloneableValue(T name) {
        this.value = name;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof CloneableValue<?> other)) return false;
        return this.value.equals(other);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
