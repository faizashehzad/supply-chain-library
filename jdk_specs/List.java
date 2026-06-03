//package java.util;

// can i remove SequesnceCollection<E>
// for the moment comment out supertypes like SequencedColl, Iterable, Collection
public interface List {
    //public interface List extends SequencedCollection {

    // use ghost for now


    // if there are properties
    /*@ public ghost instance \seq content; */ // imagine like a integer


    /*@ public normal_behavior
      @ ensures \result == content.length;
      @ assignable \strictly_nothing;
     */
    int size();

// ------------------
    /*@ public normal_behavior
      @ ensures \result == (content.length == 0);
      @ assignable \strictly_nothing;
     */
    boolean isEmpty();
//--------------------

/*Added Index Out of Bound check with prof */

    /*@ public normal_behavior
      @ requires 0 <= var1 && var1 < content.length;
      @ ensures \result == \seq_get(content, var1);
      @ assignable \strictly_nothing;
      @ also
      @ public exceptional_behavior
      @   requires var1 < 0 || var1 >= content.length;
      @   assignable \nothing;
      @   signals_only IndexOutOfBoundsException;
      @   signals (IndexOutOfBoundsException e) true;
     */

   // add index out b exc
     get(int var1);

    /*@ public normal_behavior
     @ requires var1 != null;
     @ ensures content == \seq_concat(\old(content), \seq_singleton(var1));
     @ ensures \result == true;
     @ assignable content;
     @ also
     @ public exceptional_behavior
     @   requires var1 == null
     @   signals_only NullPointerException;
     @   signals (NullPointerException e) true;
     @   assignable \nothing;
     @*/

    boolean add(/*@ nullable @*/ Object var1);

    /*@ public normal_behavior
      @ requires var1 != null;
      @ ensures \result == (\exists int i; 0 <= i < content.length; \seq_get(content, i).equals(var1));
      @ assignable \strictly_nothing;
      @ also
      @ public exceptional_behavior
      @   requires var1 == null
      @   signals_only NullPointerException;
      @   signals (NullPointerException e) true;
     */
    boolean contains(Object var1);





    Iterator<E> iterator();

    boolean contains(Object var1);

    Object[] toArray();

    <T> T[] toArray(T[] var1);

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


    E set(int var1, E var2);

    void add(int var1, E var2);

    E remove(int var1);


    boolean equals(Object var1);

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
