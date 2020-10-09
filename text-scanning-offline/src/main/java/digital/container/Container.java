package digital.container;

import java.util.Iterator;

public interface Container<T> {
    Iterator<T> iterator();
    void process(T t);
    String getActualDigits();
}
