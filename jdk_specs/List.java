//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

//package java.util;


public interface List extends SequencedCollection<E> {
    // use ghost for now
    //@ public ghost instance \seq content;

    /*@ public normal_behavior
      @ ensures \result == content.length;
      @ assignable \strictly_nothing;
     */
    int size();

    boolean isEmpty();


    boolean contains(Object var1);

    Iterator<E> iterator();

    Object[] toArray();

    <T> T[] toArray(T[] var1);
    /*@ public normal_behavior
      @ ensures content == \seq_concat(\old(content), \seq_singleton(var1));
      @ assignable content;
     */
    boolean add(E var1);

    boolean remove(Object var1);

    boolean containsAll(Collection<?> var1);

    boolean addAll(Collection<? extends E> var1);

    boolean addAll(int var1, Collection<? extends E> var2);

    boolean removeAll(Collection<?> var1);

    boolean retainAll(Collection<?> var1);

    default void replaceAll(UnaryOperator<E> operator) {
        Objects.requireNonNull(operator);
        ListIterator<E> li = this.listIterator();

        while(li.hasNext()) {
            li.set(operator.apply(li.next()));
        }

    }

    default void sort(Comparator<? super E> c) {
        Object[] a = this.toArray();
        Arrays.sort(a, c);
        ListIterator<E> i = this.listIterator();

        for(Object e : a) {
            i.next();
            i.set(e);
        }

    }

    void clear();

    boolean equals(Object var1);

    int hashCode();

    E get(int var1);

    E set(int var1, E var2);

    void add(int var1, E var2);

    E remove(int var1);

    int indexOf(Object var1);

    int lastIndexOf(Object var1);

    ListIterator<E> listIterator();

    ListIterator<E> listIterator(int var1);

    List<E> subList(int var1, int var2);

    default void addFirst(E e) {
        this.add(0, e);
    }

    default void addLast(E e) {
        this.add(e);
    }

    default E getFirst() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        } else {
            return (E)this.get(0);
        }
    }

    default E getLast() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        } else {
            return (E)this.get(this.size() - 1);
        }
    }

    default E removeFirst() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        } else {
            return (E)this.remove(0);
        }
    }

    default E removeLast() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        } else {
            return (E)this.remove(this.size() - 1);
        }
    }

}
