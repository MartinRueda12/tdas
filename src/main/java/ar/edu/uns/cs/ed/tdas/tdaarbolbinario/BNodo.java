package ar.edu.uns.cs.ed.tdas.tdaarbolbinario;
import ar.edu.uns.cs.ed.tdas.Position;
public class BNodo<E> implements Position<E>{

	protected E element;
	protected BNodo<E> padre;
	protected BNodo<E> derecho;
	protected BNodo<E> izquierdo;
	
	 public BNodo(E elem, BNodo<E> padre, BNodo<E> i, BNodo<E> d) {
	      this.element = elem;
	      this.padre = padre;
	      this.izquierdo = i;
	      this.derecho = d;
	   }
	public E element() {
		return element;
	}
	public BNodo<E> getPadre(){
		return padre;
	}
	public BNodo<E> getIzquierdo(){
		return izquierdo;
	}
	public BNodo<E> getDerecho(){
		return derecho;
	}
	
	public void setElemento(E elm) {
		element = elm;
	}
	public void setPadre(BNodo<E> p) {
		padre=p;
	}
	public void setIzquierdo(BNodo<E> i) {
		izquierdo=i;
	}
	public void setDerecho(BNodo<E> d) {
		derecho=d;
	}
}
