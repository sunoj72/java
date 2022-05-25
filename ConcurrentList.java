package com.lgcns.test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ConcurrentList<T> implements List<T>, Serializable {
	private static final long serialVersionUID = -403250596485482968L;
	private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	private final List<T> list;

	public ConcurrentList(List<T> list) {
		this.list = list;
	}
	
	@Override
	public boolean add(T t) {
		readWriteLock.writeLock().lock();
		boolean sucsses;

		try {
			sucsses = list.add(t);
			
		} finally {
			readWriteLock.writeLock().unlock();
		}
		
		return sucsses;
	}
	
	@Override
	public T get(int index) {
		readWriteLock.writeLock().lock();
		try {
			return list.get(index);
			
		} finally {
			readWriteLock.writeLock().unlock();
		}
	}

	@Override
	public int size() {
		readWriteLock.writeLock().lock();
		try {
			return list.size();
			
		} finally {
			readWriteLock.writeLock().unlock();
		}
	}

	@Override
	public boolean isEmpty() {
		readWriteLock.writeLock().lock();
		try {
			return list.isEmpty();
			
		} finally {
			readWriteLock.writeLock().unlock();
		}
	}

	@Override
	public boolean contains(Object o) {
		readWriteLock.writeLock().lock();
		try {
			return list.contains(o);
			
		} finally {
			readWriteLock.writeLock().unlock();
		}
	}

	@Override
	public Iterator<T> iterator() {
		readWriteLock.writeLock().lock();
		try {
			return new ArrayList<T>(list).iterator();
			
		} finally {
			readWriteLock.writeLock().unlock();
		}
	}

	@Override
	public Object[] toArray() {
		readWriteLock.writeLock().lock();
		try {
			return list.toArray();
			
		} finally {
			readWriteLock.writeLock().unlock();
		}
	}

	@Override
	public <T> T[] toArray(T[] a) {
		readWriteLock.writeLock().lock();
		try {
			return list.toArray(a);
			
		} finally {
			readWriteLock.writeLock().unlock();
		}
	}

	@Override
	public boolean remove(Object o) {
		readWriteLock.writeLock().lock();
		try {
			return list.remove(o);
			
		} finally {
			readWriteLock.writeLock().unlock();
		}
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		readWriteLock.writeLock().lock();
		try {
			return list.containsAll(c);
			
		} finally {
			readWriteLock.writeLock().unlock();
		}
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		readWriteLock.writeLock().lock();
		try {
			return list.addAll(c);
			
		} finally {
			readWriteLock.writeLock().unlock();
		}
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		readWriteLock.writeLock().lock();
		try {
			return list.addAll(index, c);
			
		} finally {
			readWriteLock.writeLock().unlock();
		}
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		readWriteLock.writeLock().lock();
		try {
			return list.removeAll(c);
			
		} finally {
			readWriteLock.writeLock().unlock();
		}
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		readWriteLock.writeLock().lock();
		try {
			return list.retainAll(c);
			
		} finally {
			readWriteLock.writeLock().unlock();
		}
	}

	@Override
	public void clear() {
		readWriteLock.writeLock().lock();
		try {
			list.clear();
			
		} finally {
			readWriteLock.writeLock().unlock();
		}
	}

	@Override
	public T set(int index, T element) {
		readWriteLock.writeLock().lock();
		try {
			return list.set(index, element);
			
		} finally {
			readWriteLock.writeLock().unlock();
		}
	}

	@Override
	public void add(int index, T element) {
		readWriteLock.writeLock().lock();
		try {
			list.add(index, element);
			
		} finally {
			readWriteLock.writeLock().unlock();
		}
	}

	@Override
	public T remove(int index) {
		readWriteLock.writeLock().lock();
		try {
			return list.remove(index);
			
		} finally {
			readWriteLock.writeLock().unlock();
		}
	}

	@Override
	public int indexOf(Object o) {
		readWriteLock.writeLock().lock();
		try {
			return list.indexOf(o);
			
		} finally {
			readWriteLock.writeLock().unlock();
		}
	}

	@Override
	public int lastIndexOf(Object o) {
		readWriteLock.writeLock().lock();
		try {
			return list.lastIndexOf(o);
			
		} finally {
			readWriteLock.writeLock().unlock();
		}
	}

	@Override
	public ListIterator<T> listIterator() {
		readWriteLock.writeLock().lock();
		try {
			return list.listIterator();
			
		} finally {
			readWriteLock.writeLock().unlock();
		}
	}

	@Override
	public ListIterator<T> listIterator(int index) {
		readWriteLock.writeLock().lock();
		try {
			return list.listIterator(index);
			
		} finally {
			readWriteLock.writeLock().unlock();
		}
	}

	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		readWriteLock.writeLock().lock();
		try {
			return list.subList(fromIndex, toIndex);
			
		} finally {
			readWriteLock.writeLock().unlock();
		}
	}
}
