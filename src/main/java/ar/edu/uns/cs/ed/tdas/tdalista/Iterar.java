package ar.edu.uns.cs.ed.tdas.tdalista;
import java.util.Iterator;
import ar.edu.uns.cs.ed.tdas.Position;

public class Iterar<E> implements Iterator<E>{
	   private DNodo<E> puntero;
	    private DNodo<E> centinelaDerecha;

	    public Iterar(DNodo<E> inicio, DNodo<E> centinelaDerecha) {
	        this.puntero = inicio.getSiguiente();              
	        this.centinelaDerecha = centinelaDerecha;
	    }

	    @Override
	    public boolean hasNext() {
	        return puntero != centinelaDerecha;
	    }

	    @Override
	    public E next() {
	        E elem = puntero.element();
	        puntero = puntero.getSiguiente();
	        return elem;           
	    }
	    public String toString() {
	    	return " " + puntero;
	    }
	
}
