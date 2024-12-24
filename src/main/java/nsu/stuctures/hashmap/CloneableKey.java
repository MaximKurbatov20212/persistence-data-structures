package nsu.stuctures.hashmap;

public class CloneableKey<T> implements Cloneable {
    private final T name;

    public CloneableKey(T name) {
        this.name = name;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof CloneableKey<?> other)) return false;
        return this.name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name.toString();
    }
}
