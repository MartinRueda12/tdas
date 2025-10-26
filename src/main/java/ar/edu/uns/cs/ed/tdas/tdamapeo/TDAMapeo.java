package ar.edu.uns.cs.ed.tdas.tdamapeo;
import java.util.Iterator;

import ar.edu.uns.cs.ed.tdas.tdalista.*;
import ar.edu.uns.cs.ed.tdas.Entry;
import ar.edu.uns.cs.ed.tdas.Position;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidKeyException;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidPositionException;
public class TDAMapeo<K,V> implements Map<K,V>{
	
	private int size=0;
	protected  PositionList<Entrada<K,V>> [] A;
	private static final float carga= 0.75f;
	
	
	public TDAMapeo() {
		A = (PositionList<Entrada<K,V>>[])new PositionList[7];
		for(int i = 0; i< A.length;i++) {
			A[i] = new Mylist();
		}
	}
	public int size() {return size;}
	public boolean isEmpty() {return size ==0;}
	
	public V put(K key, V val)throws InvalidKeyException{
		V toret = null;
		if(key ==null) {
			throw new InvalidKeyException("caca");
		}
		Entrada<K,V> add = new Entrada(key,val);
		int index = hash(key);
		Iterator<Entrada<K,V>> it = A[index].iterator();
		while(toret == null && it.hasNext()) {
			Entrada<K,V> busc = it.next();
			if(busc.getKey().equals(key)) {
				toret=busc.getValue();
				busc.setValue(val);
			}
		}
		if(toret == null) {
			A[index].addLast(add);
		}
		if(A[index].size()/A.length > carga) {
			rehash();
		}
		size++;
		return toret;
	}
	public V get(K key)throws InvalidKeyException {
		V toret = null;
		if(key ==null) {
			throw new InvalidKeyException("caca");
		}
		int index = hash(key);
		Iterator<Entrada<K,V>> it = A[index].iterator();
		while(toret == null && it.hasNext()) {
			Entrada<K,V> busc = it.next();
			if(busc.getKey().equals(key)) {
				toret=busc.getValue();
			}
		}
		return toret;
	}
	public V remove(K key)throws InvalidKeyException {
		V toret = null;
		if(key ==null) {
			throw new InvalidKeyException("caca");
		}
		int index = hash(key);
		Iterator<Position<Entrada<K,V>>> it = A[index].positions().iterator();
		while(toret == null && it.hasNext()) {
			Position<Entrada<K,V>>busc = it.next();
			if(busc.element().getKey().equals(key)) {
				toret=busc.element().getValue();
				A[index].remove(busc);
				size--;
			}
		}
		return toret;
	}
	
	private int hash(K key) {
		return key.hashCode() % A.length;
	}
	private void rehash() {
		PositionList<Entrada<K,V>> [] rehash =new  PositionList[A.length*2];
		for(int j = 0; j< rehash.length;j++) {
			rehash[j]= new Mylist();
		}
		for(int i = 0; i< A.length;i++) {
			if(A[i].size()!=0) {
				for(Entrada<K,V> a : A[i]) {
					int re = a.getKey().hashCode() % rehash.length;
					rehash[re].addLast(a);
				}
			}
		}
		A =rehash;
	}

	public Iterable<K> keys() {
		PositionList<K> keys = new Mylist();
		for(int i = 0;i < A.length;i++) {
			if(A[i].size()!=0) {
				for(Entrada<K,V> a : A[i]) {
					keys.addLast(a.getKey());
				}
			}
		}
		return keys;
	}
	public Iterable<V> values() {
		PositionList<V> values = new Mylist();
		for(int i = 0;i < A.length;i++) {
			if(A[i].size()!=0) {
				for(Entrada<K,V> a : A[i]) {
					values.addLast(a.getValue());
				}
			}
		}
		return values;
	}

	public Iterable<Entry<K,V>> entries() {
	    PositionList<Entry<K,V>> listaDeEntries = new Mylist<>();
	    for (int i = 0; i < A.length; i++) {
	    	if(!A[i].isEmpty()) {
		        for (Entrada<K,V> entradaActual : A[i])
		        	{listaDeEntries.addLast(entradaActual);} 
	    	}
	    }
	    return listaDeEntries;
	}
	public void copy(Map<K,V> m) {
		boolean control=false;
		for(Entry<K,V> a : m.entries()) {
			control=false;
			int i=hash(a.getKey());
			Iterator<Entrada<K,V>> pis = A[i].iterator();
			while(!control && pis.hasNext()) {
				Entrada<K,V> rec = pis.next();
				if(rec.getKey().equals(a.getKey())){
					rec.setValue(a.getValue());
					control=true;
				}
				}
			if(!control) {
				Entrada<K,V> add = new Entrada(a.getKey(),a.getValue());
				A[i].addLast(add);
				size++;
			}
			}
		}
	public static void main(String[] args) {
		TDAMapeo<Character,Integer> ap = new TDAMapeo();
		TDAMapeo<Character,Integer> ap2 = new TDAMapeo();
		ap.put('a', 2);
		ap.put('e', 6);
		ap.put('i', 4);
		
		ap2.put('a', 3);
		ap2.put('e', 7);
		ap2.put('i', 1);
		ap2.put('q', 10);
		ap2.put('y', 0);
		for(Entry<Character,Integer> gg: ap.entries()) {
			System.out.print("(" + gg + ")" + ",");
		}
		ap.copy(ap2);
		System.out.println();
		for(Entry<Character,Integer> gg: ap.entries()) {
			System.out.print("(" + gg + ")" + ",");
		}
	}
}

	
