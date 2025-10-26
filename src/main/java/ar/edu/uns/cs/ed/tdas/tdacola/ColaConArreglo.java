package ar.edu.uns.cs.ed.tdas.tdacola;
import ar.edu.uns.cs.ed.tdas.excepciones.EmptyQueueException;
public class ColaConArreglo<E> implements Queue<E>{
	private  E[] cola ;
	private int cant;
	private int f=0;
	
	public ColaConArreglo() {
		cola = (E[]) new Object[10];
		cant=0;
		
	}
	public int size() {return cant;}
	
	public boolean isEmpty() {return cant==0;}
	
    public E front() throws EmptyQueueException {
		if(cant==0) {
			throw new EmptyQueueException("vacio");
		}
		return cola[f];
	}

	public void enqueue(E element) {
		cola[(f+cant)%cola.length]=element;
		cant++;
		if(cant == cola.length) {
			expandir();
		}
	}

	@Override
	public E dequeue()throws EmptyQueueException {
		if(cant==0) {
			throw new EmptyQueueException("vacio");
		}
		E element = cola[f];
		cola[f]=null;
		f= (f+1)%cola.length;
		cant--;
		return element;
	}

	private void expandir() {
	    E[] expandido = (E[])new Object[cola.length*2];
	    // Iteramos sobre los elementos válidos (cant), comenzando desde el índice 'f'
	    for(int i = 0; i < cant; i++) {
	        int oldIndex = (f + i) % cola.length;
	        expandido[i] = cola[oldIndex];
	    }
	    
	    cola = expandido;
	    f = 0; // El nuevo frente siempre comienza en el índice 0
	}
}
