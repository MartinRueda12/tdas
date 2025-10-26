package ar.edu.uns.cs.ed.tdas.tdadiccionario;
import ar.edu.uns.cs.ed.tdas.Entry;
public class Entrada<K,V> implements Entry<K,V>  {
	
	private K key;
	private V value;
	
	public Entrada( K key, V value) {
		this.key=key;
		this.value=value;
	}
	
	public void setKey(K k) {
		key=k;
	}
	public void setValue(V va) {
		value = va;
	}
	
	public V getValue() {return value;}
	public K getKey() {return key;}
	public String toString() {
		return key + " " + value;
	}
}

