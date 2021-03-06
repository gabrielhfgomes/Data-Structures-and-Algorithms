package chapter_7.code.array_list;

/*
 * Created by JJMacagnan on 20/05/2017.
 */
public interface List<E> {

    int size();

    boolean isEmpty();

    E get(int i) throws IndexOutOfBoundsException;

    E set(int i, E e) throws IndexOutOfBoundsException;

    void add(int i, E e) throws IndexOutOfBoundsException;

    E remove(int i) throws IndexOutOfBoundsException;
}
