package java.util;

// can i remove SequesnceCollection<E>
// for the moment comment out supertypes like SequencedColl, Iterable, Collection
public interface List extends java.util.Collection {
    //public interface List extends SequencedCollection {

    // use ghost for now


    // if there are properties


// ------------------
//--------------------

/*Added Index Out of Bound check with prof */

    /* public normal_behavior
       requires 0 <= var1 && var1 < content.length;
       ensures \result == \seq_get(content, var1);
       assignable \strictly_nothing;
       also
       public exceptional_behavior
         requires var1 < 0 || var1 >= content.length;
         assignable \nothing;
         signals_only IndexOutOfBoundsException;
         signals (IndexOutOfBoundsException e) true;
     */

   // add index out b exc
    java.lang.Object get(int var1);


    java.util.Iterator iterator();

    boolean contains(java.lang.Object var1);

    java.lang.Object[] toArray();

    boolean remove(java.lang.Object var1);

    boolean containsAll(java.util.Collection var1);

    boolean addAll(java.util.Collection var1);
/*
    boolean addAll(int var1, java.util.Collection<? extends E> var2);

    boolean removeAll(java.util.Collection<?> var1);

    boolean retainAll(java.util.Collection<?> var1);

    default void replaceAll(UnaryOperator<E> operator) {
        Objects.requireNonNull(operator);
        ListIterator<E> li = this.listIterator();

        while(li.hasNext()) {
            li.set(operator.apply(li.next()));
        }

    }

    default void sort(Comparator<? super E> c) {
        java.lang.Object[] a = this.toArray();
        Arrays.sort(a, c);
        ListIterator<E> i = this.listIterator();

        for(java.lang.Object e : a) {
            i.next();
            i.set(e);
        }

    }


    E set(int var1, E var2);

    void add(int var1, E var2);

    E remove(int var1);


    boolean equals(java.lang.Object var1);

    int indexOf(java.lang.Object var1);

    int lastIndexOf(java.lang.Object var1);

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
*/
}
