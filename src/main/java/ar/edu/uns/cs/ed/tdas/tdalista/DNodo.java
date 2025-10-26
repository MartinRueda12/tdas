package ar.edu.uns.cs.ed.tdas.tdalista;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidPositionException;
import ar.edu.uns.cs.ed.tdas.Position;



public class DNodo<E> implements Position<E>{
	private E element;
	private DNodo<E> next;
	private DNodo<E> prev;
	
	public DNodo(E e ,DNodo<E> n, DNodo<E> p) {
		element = e;
		next=n;
		prev=p;
	}
	public E element() {
		if(next == null) {
			throw  new  InvalidPositionException("\"Positionnolongervalid\"");
		}
		return element;
	}
	
	public void establecerNext(DNodo<E> siguiente) {
		next = siguiente;
	}
	public void establecerPrev(DNodo<E> ant) {
		prev= ant;
	}
	public void establecerE(E element) {
		this.element = element;
	}
	public DNodo<E> getSiguiente(){
		return next;
	}
	public DNodo<E> getAnt(){
		return prev;
	}
	
}
