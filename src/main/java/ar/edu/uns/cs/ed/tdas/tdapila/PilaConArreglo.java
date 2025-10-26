package ar.edu.uns.cs.ed.tdas.tdapila;
import ar.edu.uns.cs.ed.tdas.excepciones.EmptyStackException;

public class PilaConArreglo<E>  implements Stack<E>{
	private E[] pila;
	private static final int max =10000;
	private int size;
	
	public PilaConArreglo() {
		pila = (E[]) new Object[max];
		size=0;
	}
	public boolean isEmpty() {return size == 0;}
	public int size() {return size;}
	
	public E top() throws EmptyStackException{
		if(size==0) {
			throw new EmptyStackException("vacio");
		}
		return pila[size-1];
	}
	public void push(E e) {
		
			pila[size]=e;
			size++;
		
	}
	public E pop() throws EmptyStackException  {
		E ret = pila[size-1];
		if(size==0) {
			throw new EmptyStackException("vacio");
		}
		pila[size-1]=null;
		return ret;
	}

	/*public static void invertir(String[] per) {
		int n=0;
		Stack<String> aux = new Stack<>();
		for(int i = 0; i < per.length;i++) {
			aux.push(per[i]);
		}
		while(!aux.isEmpty()) {
			per[n++]=aux.pop();	
		}	
	}*/
	public static void main(String[]args) {
		/*String[] a = {"dada","pepe","morez"};
		invertir(a);
		
		for(int i =0; i<a.length;i++) {
			System.out.print(a[i] + " ");
		}*/
	}
}
