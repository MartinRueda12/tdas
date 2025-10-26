package ar.edu.uns.cs.ed.tdas.tdalista;
import java.util.Iterator;

import ar.edu.uns.cs.ed.tdas.Position;
import ar.edu.uns.cs.ed.tdas.excepciones.EmptyListException;
import ar.edu.uns.cs.ed.tdas.excepciones.BoundaryViolationException;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidPositionException;


public class Mylist<E> implements PositionList<E> {

	
	private int size;
	private DNodo<E> CentinelaDerecha;
	private DNodo<E> CentinelaIzquierda;
	public Mylist() {
		size = 0;
		CentinelaIzquierda= new DNodo(null,null,null);
		CentinelaDerecha = new DNodo(null,null,CentinelaIzquierda);
		CentinelaIzquierda.establecerNext(CentinelaDerecha);
		
	}
	public int size() {return size;}
	public boolean isEmpty() {return size==0;}
	
	public void addFirst(E element) {
		DNodo add = new DNodo(element,null,CentinelaIzquierda);
		DNodo sig = CentinelaIzquierda.getSiguiente();
		CentinelaIzquierda.establecerNext(add);
		sig.establecerPrev(add);
		add.establecerNext(sig);
		size++;
	}
	public void addLast(E element) {
		DNodo<E> add;
		if(size ==0) {
			add= new DNodo<E>(element,CentinelaDerecha,CentinelaIzquierda);
			CentinelaIzquierda.establecerNext(add);
			CentinelaDerecha.establecerPrev(add);
		}else {
			DNodo<E> ant = CentinelaDerecha.getAnt();
			add= new DNodo<E>(element,CentinelaDerecha,ant);
			CentinelaDerecha.establecerPrev(add);
			ant.establecerNext(add);
		}
		size++;
	}
	
	public Position<E> first() throws EmptyListException{
		if(isEmpty()) {
			throw new EmptyListException("VAcion");
		}
		Position<E> first = CentinelaIzquierda.getSiguiente();
		return first;
		
	}
	public Position<E> last() throws EmptyListException{
		DNodo a=null;
		if(isEmpty()) {
			throw new EmptyListException("Lista vacia");
		}else if(CentinelaDerecha.getAnt()!=CentinelaIzquierda) {
			a=CentinelaDerecha.getAnt();
		}
		return a;
		
	}
	
	
	public Position<E> next(Position<E> p) throws BoundaryViolationException{
		if(isEmpty() || p == null) {
			throw new InvalidPositionException("SWoy gay");
		}
		DNodo<E> val = checkPosition(p);
		if(val.getSiguiente().equals(CentinelaDerecha)) {
			throw new BoundaryViolationException("es ultimo elemento");
		}
		Position<E> next = val.getSiguiente();
		return next;
	}
	
	public Position<E> prev(Position<E> p)throws BoundaryViolationException{
		if(isEmpty() || p == null) {
			throw new InvalidPositionException("SWoy gay");
		}
		DNodo<E> val = checkPosition(p);
		if(val.getAnt() == CentinelaIzquierda) {
			throw new BoundaryViolationException("es primer elemento");
		}
		Position<E> prev = (Position<E>) val.getAnt();
		return prev;
	}
	
	public void addBefore(Position<E> p, E element){
		DNodo<E> val = checkPosition(p);
		if(isEmpty() || p == null || val==null) {
			throw new InvalidPositionException("SWoy gay");
		}
		DNodo<E> ant_val = val.getAnt();
		DNodo<E> add = new DNodo(element,val,ant_val);
		ant_val.establecerNext(add);
		val.establecerPrev(add);
		size++;
	}
	public void addAfter(Position<E> p, E element) {
		DNodo<E> val = checkPosition(p);
		if(isEmpty() || p == null || val==null) {
			throw new InvalidPositionException("SWoy gay");
		}
		DNodo<E> sig_val = val.getSiguiente();
		DNodo<E> add = new DNodo(element,sig_val,val);
		val.establecerNext(add);
		sig_val.establecerPrev(add);
		size++;
	}
	
	public E remove(Position<E> p) {
		
		DNodo<E> val = checkPosition(p);
		if(val == null) {
			throw new BoundaryViolationException("a");
		}
		E ret =val.element();
		DNodo<E> ant_val = val.getAnt();
		DNodo<E> sig_val = val.getSiguiente();
		
		sig_val.establecerPrev(ant_val);
		ant_val.establecerNext(sig_val);
		val.establecerPrev(null);
		val.establecerNext(null);
		size--;
		return ret;
	}
	public E set(Position<E> p, E element) throws InvalidPositionException  {
		DNodo<E> val = checkPosition(p);
		if(isEmpty()) {
			throw new  InvalidPositionException ("VACIO");
		}
		E ret = val.element();
		val.establecerE(element);
		return ret;
	}
	public Iterator<E> iterator(){
		 return new Iterar<>(CentinelaIzquierda, CentinelaDerecha);
	}
    public Iterable<Position<E>> positions() {
    	PositionList<Position<E>> nuevaLista= new Mylist<>();
        if(!isEmpty()){
        	Position<E> n = first();
        while(n != last()){
            nuevaLista.addLast(n);
            n=next(n);
        }
        if(n == last() && n !=null){
            nuevaLista.addLast(n);
        }
        }
        return nuevaLista;
        
    }
	public void segundoYAnteUltimo(E e1,E e2) {
		if(size==0) {
			addFirst(e1);
			addAfter(CentinelaIzquierda.getSiguiente(),e2);
		}else if(size == 1) {
			addBefore(CentinelaIzquierda.getSiguiente(),e1);
			addAfter(CentinelaDerecha.getAnt(),e2);
		}else {
			addFirst(e1);
			addLast(e2);
		}
	}
	public boolean pertenece(E e1) throws EmptyListException {
		boolean esta = false;
		if(size==0) {
			throw new EmptyListException("la lista esta vacia");
		}
		Iterator<E> busc = this.iterator();
		while(!esta && busc.hasNext()) {
			E rec = busc.next();
			if(rec.equals(e1)) {
				esta=true;
			}
		}
		return esta;
	}
	
	public int apariciones(E e) {
		int cant= 0;
		for(E x: this ) {
			if(x == e) {
				cant++;
			}
		}
		return cant;
	}
	
	public boolean alMenos(E x, int n) {
		Iterator<E> busc = this.iterator();
		int cant=0;
		while(cant<=n && busc.hasNext()) {
			E rec = busc.next();
			if(rec == x) {
				cant++;
			}
		}
		return cant>=n;
	}
	
	public PositionList<E> listRepetida(){
		PositionList<E> rep = new Mylist();
		Position<E> rec=null;
		for(E x: this) {
			if(rep.isEmpty()) {
				rep.addFirst(x);
				rec=rep.first();
				rep.addAfter(rec, x);
			}else {
				rec=rep.next(rec);
				rep.addAfter(rec, x);
				rec=rep.next(rec);
				rep.addAfter(rec, x);
			}
		}
		return rep;
	}

	public static int contarImpares(Mylist<Integer> l) {
		int cant = 0;
		for(Integer x : l) {
			if(x % 2==1) {
				cant++;
			}
			
		}
		return cant;
	}
	
	public Mylist<E> partirEnDos(Position<E> p)throws EmptyListException{
		try {
			DNodo chk = checkPosition(p);
			Mylist<E> nueva= new Mylist();
			DNodo Puntero =chk.getSiguiente();
			while(Puntero !=CentinelaDerecha) {
				E elem = (E)Puntero.element();
				nueva.addLast(elem);
				Puntero=Puntero.getSiguiente();
			}
			chk=chk.getAnt();
			chk.establecerNext(CentinelaDerecha);
			CentinelaDerecha.establecerPrev(chk);
			chk.getSiguiente().establecerPrev(null);
			size--;
			size=size-nueva.size();
			return nueva;
		}catch(InvalidPositionException e) {
			System.out.println("Posision invalida");
			return null;
		}
		
	}
	public void vaciarLista()throws EmptyListException{
		if(size==0) {
			throw new EmptyListException("lista vacia");
		}
		CentinelaIzquierda.getSiguiente().establecerNext(null);
		CentinelaIzquierda.establecerNext(CentinelaDerecha);
		CentinelaDerecha.getAnt().establecerNext(null);
		CentinelaDerecha.establecerPrev(CentinelaIzquierda);
		size=0;
	}
	
	
	
	private DNodo<E> checkPosition(Position<E> p)throws InvalidPositionException{
	        DNodo<E> n;
	        if(isEmpty() || p==null){
	            throw new InvalidPositionException("Esta mal xd");
	        }
	        try{
	            n=(DNodo<E>) p;
	        }
	        catch(ClassCastException e){
	            throw new InvalidPositionException("Posicion invalida");
	        }
	        return n;
	    }
	public E medio() {
		DNodo pi= CentinelaIzquierda.getSiguiente();
		DNodo pd = CentinelaDerecha.getAnt();
		if(size % 2 ==0) {
			System.out.println("No se puede hacer la operacion");
			return null;
		}else {
			while(pi!=pd) {
				pi= pi.getSiguiente();
				pd = pd.getAnt();
			}
		}
		return (E) pi.element();
	}
		
		public static <E> Object a(Mylist<E> l){
			E elem = null;
			Iterator<E> it = l.iterator();
			int med = l.size()/2;
			int n=0;
			while(n<=med) {
				elem=it.next();
				n++;
			}
			return elem;
		}
		
	
	public static void main(String []args) {
		
		Mylist<Integer> l1 = new Mylist();

		l1.addLast(1);
		l1.addLast(2);
		l1.addLast(3);
		l1.addLast(4);
		l1.addLast(5);
		l1.addLast(6);
		l1.addLast(7);
		l1.addLast(8);
		l1.addLast(9);
		
		System.out.println();
		System.out.println(l1.medio());
		System.out.println(a(l1));
		/*for(Position<Integer> x : l1.positions()) {
			System.out.println(x + "-");
		}
		for(Integer y : l1) {
			System.out.print(y + "-");
		}
		Iterator<Position<Integer>> it = l1.positions().iterator();
		while(it.hasNext()) {
			System.out.println(it.next());
		}
		/*System.out.println();
		System.out.println();
		l1.vaciarLista();
		System.out.println(l1.size());
		
		Position<Integer> p = l1.first();/*
		//p = l1.next(p);
		
		//Mylist<Integer> l2 = l1.partirEnDos();
		
		/*for(Integer x : l2) {
			System.out.print(x + "-");
		}
		System.out.println();
		for(Integer y : l1) {
			System.out.print(y + "-");
		}
		System.out.println();
		System.out.println(l1.size + " " + l2.size);*/
	}

}
    

	


	


