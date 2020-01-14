package cutcode;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class LList<E> implements List<E> {
	/**
	 * @author arjunk2022
	 */
	private Node<E> head, tail; // Head does not ever contain data
	private int size;

	public LList() {
		head = new Node<E>(null);
		tail = head;
		size = 0;

	}

	@Override
	public int size() {
		return size; // attribute changed upon adding and deleting
	}

	@Override
	/**
	 * @returns true if the list is empty
	 */
	public boolean isEmpty() {
		return (size == 0);
	}

	@Override
	public boolean contains(Object o) {
		Node<E> node = head;
		while(node != null) {
			node = node.getNext();
			if(o == node.data) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Iterator<E> iterator() {
		return new LListIterator<E>(head.getNext()); // ListIterator class is below (head.getNext() because head does
														// not contain data)
	}

	/**
	 * @apiNote O(n^2)
	 */
	@Override
	public Object[] toArray() {
		Object[] arr = new Object[size];
		for(int i = 0; i < size; i++) {
			arr[i] = get(i);
		}
		return arr;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean add(E e) {
		if (size == 0) { // special scenario
			tail = new Node<E>(e);
			size++; // change size attribute
			tail.setPrevious(head);
			head.setNext(tail);
			return true;
		}
		Node<E> n = new Node<E>(e);
		tail.setNext(n);
		n.setPrevious(tail); // creates a new node and links it back to tail
		tail = n;
		size++;
		return true;
	}

	@Override
	public boolean remove(Object o) {
		if (tail == null) {
			return false;
		}
		Node<E> spot = head.next;
		while (spot != null) {
			if (spot.data.equals(o)) {
				if(spot == tail) //if the spot is tail, there is no spot.next
				{
					tail = spot.prev;
					tail.next = null;
					size--;
					return true;
				}
				spot.prev.next = spot.next;
				spot.next.prev = spot.prev;
				size--;
				return true;
			}
			spot = spot.next;
		}
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		head = new Node<E>(null);
		tail = head;
		size = 0; // size must be reset
	}

	/**
	 * @apiNote O(n)
	 * @author Arjun Khanna
	 */
	@Override
	public E get(int index) throws IndexOutOfBoundsException {
		if (index >= size || index < 0) { // Both of these are out of bounds
			throw new IndexOutOfBoundsException(
					(size == 0) ? ("LinkList is empty") : ("Index is out of bounds of LinkedList"));
		} else if (index == 0) {
			return head.getNext().getData(); // again, head does not store data
		}
		Node<E> spot = head.getNext();
		for (int i = 0; i < index; i++) {
			spot = spot.getNext();
		}
		return spot.getData();
	}

	@Override
	public E set(int index, E element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void add(int index, E element) throws IndexOutOfBoundsException {
		if (index < 0) {
			throw new IndexOutOfBoundsException("Index is negative");
		}
		if (index >= size || size == 0) { // When index >= size, we're supposed to add to the end
			add(element);
		} else if (index == 0) { // special scenario
			Node<E> add = new Node<E>(null); // new node with no data
			head.prev = add;
			add.next = head;
			head.setData(element); // maintains the link between the old head and the node succeeding it
			head = add;
			size++;
		} else {
			Node<E> spot = head.getNext();
			for (int i = 0; i < index; i++) {
				spot = spot.next; // goes to the index
			}
			Node<E> add = new Node<E>(element);
			spot.prev.next = add;
			add.prev = spot.prev;
			spot.setPrevious(add);
			add.next = spot; // links all the nodes together
			size++;
		}

	}

	@Override
	public E remove(int index) throws IndexOutOfBoundsException {
		if (index >= size || index < 0) { // out of bounds
			throw new IndexOutOfBoundsException();
		} else if (index == size - 1) { //special scenario
			E data = tail.data;
			tail = tail.prev;
			tail.next = null;
			size--;
			return data;
		} else {
			Node<E> spot = head.next;
			for (int i = 0; i < index; i++) {
				spot = spot.next;
			}

			spot.prev.next = spot.next;
			spot.next.prev = spot.prev; //links the nodes held together by the node we're removing
			size--;
			return spot.getData();
		}
	}


	@Override
	public int indexOf(Object o) {
		throw new UnsupportedOperationException(); 
	}

	@Override
	public int lastIndexOf(Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListIterator<E> listIterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		throw new UnsupportedOperationException();
	}

	private class LListIterator<E> implements Iterator<E> {
		private Node<E> current;

		public LListIterator(Node<E> start) {
			current = start; // I wanted to let it start at different points, but the only use in this
								// program starts at index 0
		}

		@Override
		public boolean hasNext() {
			return (current != null); // the current value is what is returned each time
		}

		@Override
		public E next() throws NoSuchElementException {
			if (current == null) {
				throw new NoSuchElementException(); // the end of the list has been reached
			}
			E data = current.data; // we take the data
			current = current.next; // move on to the next node
			return data; // return the data
		}
	}
	
	public E getEnd() {
		return tail.getData();
	}

	private class Node<E> {
		private Node<E> prev, next; // pointers linking nodes together
		private E data; // value held

		public Node(E data) { // mainly for convenience
			this.data = data;

		}

		public Node<E> getPrevious() { // Standard getters and setters from here
			return prev;
		}

		public void setPrevious(Node<E> prev) {
			this.prev = prev;
		}

		public Node<E> getNext() {
			return next;
		}

		public void setNext(Node<E> next) {
			this.next = next;
		}

		public E getData() {
			return data;
		}

		public void setData(E data) {
			this.data = data;
		}

	}
}
