package ar.edu.uns.cs.ed.tdas.tdadiccionario;
import ar.edu.uns.cs.ed.tdas.tdamapeo.*;

import ar.edu.uns.cs.ed.tdas.tdalista.*;
import java.util.Iterator;

//import com.sun.org.apache.bcel.internal.generic.AALOAD;

import ar.edu.uns.cs.ed.tdas.Entry;
import ar.edu.uns.cs.ed.tdas.Position;
import ar.edu.uns.cs.ed.tdas.excepciones.*;

public class TDADiccionario<K,V> implements Dictionary<K,V>{
	
	protected PositionList<Entry<K,V>>[] dic;
	protected int size;
	private static final float carga= 0.75f;
	
	public TDADiccionario() {
		size=0;
		dic = (PositionList<Entry<K,V>>[])new PositionList[10];
		for(int i = 0; i<dic.length;i++) {
			dic[i]= new Mylist();
		}
	}
	public int size() {return size;}
	
	public boolean isEmpty() {return size==0;}
	
	public Entry<K, V> find(K key) throws InvalidKeyException{
		if(key == null) {
			throw new InvalidKeyException("Key invalida");
		}
		Entry<K,V> toret=null;
		int i = hash(key);
		for(Entry<K, V> rec : dic[i]){
			if(rec.getKey().equals(key)) {
				toret=rec;
				break;
			}
		}
		return toret;
	}

	public Iterable<Entry<K, V>> findAll(K key)throws InvalidKeyException{
		if(key == null) {
			throw new InvalidKeyException("Key invalida");
		}
		int i = hash(key);
		PositionList<Entry<K,V>> find = new Mylist();
		for(Entry<K, V> rec : dic[i]){
			if(rec.getKey().equals(key)) {
				find.addLast(rec);
			}
		}
		return find;
	}

	public Entry<K, V> insert(K key, V value) throws InvalidKeyException {
		if(key == null) {
			throw new InvalidKeyException("Key invalida");
		}
		int i =hash(key);

		Entrada<K,V> add = new Entrada(key,value);
		dic[i].addLast(add);
		size++;
		if(dic[i].size()/dic.length > carga) {
			rehash();
		}
		return add;
	}
	
	public Entry<K, V> remove(Entry<K, V> e) throws InvalidEntryException {
		if(e ==null) {
			throw new InvalidEntryException("Key invalida");
		}
		Entry<K,V> toret=null;
		int i =hash(e.getKey());
		Iterator<Position<Entry<K,V>>> it = dic[i].positions().iterator();
		
		while(toret == null && it.hasNext()) {
			Position<Entry<K,V>> busc = it.next();
			if(busc.element().equals(e)) {
				toret=busc.element();
				dic[i].remove(busc);
				size--;
			}
		}
		if(toret==null) {
			throw new InvalidEntryException("Key invalida");
		}
		return toret;
	}
	
	public Iterable<Entry<K, V>> entries() {
		PositionList<Entry<K,V>> entries = new Mylist();
		for(int i = 0; i<dic.length;i++) {
			if(!dic[i].isEmpty()) {
				for(Entry<K, V> add : dic[i]) {
					entries.addLast(add);
				}
			}
		}
		return entries;
	}
	private int hash(K key) {
		return Math.abs(key.hashCode() % dic.length);
	}
	private void rehash() {
		PositionList<Entry<K,V>> [] rehash =new  PositionList[dic.length*2];
		for(int j = 0; j< rehash.length;j++) {
			rehash[j]= new Mylist();
		}
		for(int i = 0; i< dic.length;i++) {
			if(dic[i].size()!=0) {
				for(Entry<K,V> a : dic[i]) {
					int re = a.getKey().hashCode() % rehash.length;
					rehash[re].addLast(a);
				}
			}
		}
		dic =rehash;
	}
	public boolean almenosE(K key, int e) {
		int cont = 0;
		int i=hash(key);
		
		Iterator<Entry<K,V>> it = dic[i].iterator();
		
		while(cont>=e && it.hasNext()) {
			Entry<K,V> rec = it.next();
			if(rec.getKey().equals(key)) {
				cont++;
			}
		}
		
		return cont>=e;
	}
	public Dictionary<K,V> acomodar(Dictionary<K,V>  d){
		Map<K,V> aux = new TDAMapeo();
		
		Iterator<Entry<K,V>> it = d.entries().iterator();
		
		while(it.hasNext()) {
			Entry<K,V> rev = it.next();
			aux.put(rev.getKey(), rev.getValue());
			d.remove(rev);
		}
		Iterator<Entry<K,V>> it2 = aux.entries().iterator();
		while(it2.hasNext()) {
			Entry<K,V> dada = it2.next();
			d.insert(dada.getKey(), dada.getValue());
		}
		return d;
	}
	public Iterable<Entry<K,V>> elmininarTodas( K c, V v)throws InvalidKeyException {
		if(c == null)throw new InvalidKeyException("Key invalida");
		PositionList<Entry<K,V>> l = new Mylist();
		Entrada<K,V> add = new Entrada(c,v);
		int i =hash(c);
		Iterator<Position<Entry<K,V>>> it = dic[i].positions().iterator();
		while(it.hasNext()) {
			Position<Entry<K,V>>rec = it.next();
			if(rec.element().getKey().equals(c) && rec.element().getValue().equals(v)) {
				dic[i].remove(rec);
				size--;
				l.addLast(add);
			}
		}
		return l;
	}
	
	public int contarEntrada(K k) {
		
		int i = hash(k);
		return dic[i].size();
	}
	public static void main(String[]arg) {
		TDADiccionario<Integer,Character> a = new TDADiccionario();
		a.insert(1,'a');
		a.insert(1,'b');
		a.insert(3, 'a');
		a.insert(2,'f');
		a.insert(1, 'd');
		a.insert(4, 'b');
		System.out.println(a.contarEntrada(2));
		//Dictionary<Integer,Character> dada = a.acomodar(a);
		for(Entry<Integer,Character> d : a.elmininarTodas(2, 'b')) {
			System.out.print( "(" + d+")" + " ");
		}
		
	}
	
}
