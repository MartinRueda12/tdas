package ar.edu.uns.cs.ed.tdas.tdaarbol;
import ar.edu.uns.cs.ed.tdas.Position;
import ar.edu.uns.cs.ed.tdas.tdalista.*;
public class TNodo<E> implements Position<E> {
	
	private E element;
	private TNodo<E> padre;
	protected PositionList<TNodo<E>> hijos;
	
	public TNodo(E elem, TNodo<E> p) {
		element=elem;
		padre = p;
		hijos = new Mylist();
	}
	
	public E element() {return element;}
	
	public PositionList<TNodo<E>> getHijos() {return hijos;}
	
	public TNodo<E> getPadre() {return padre;}
	
     public void setElement(E element) {this.element=element;}
	
	public void setHijo(TNodo<E> p) {
		hijos.addLast(p);
	}
	public void setPadre(TNodo<E> p) {padre =p;}
	
}
