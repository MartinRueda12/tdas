package ar.edu.uns.cs.ed.tdas.tdaarbol;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidOperationException;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidPositionException;
import ar.edu.uns.cs.ed.tdas.excepciones.BoundaryViolationException;
import ar.edu.uns.cs.ed.tdas.excepciones.EmptyListException;
import ar.edu.uns.cs.ed.tdas.excepciones.EmptyTreeException;
import ar.edu.uns.cs.ed.tdas.tdalista.Mylist;
import ar.edu.uns.cs.ed.tdas.tdalista.PositionList;
import ar.edu.uns.cs.ed.tdas.Position;
import java.util.Iterator;

public class TDAArbol<E> implements Tree<E> {
	
	private int size;
	private TNodo<E> root;
	
	public TDAArbol() {
		size=0;
		root = null;
	}
	public boolean isEmpty() {return size==0;}
	public int size() {return size;}
	
	public void createRoot(E e) throws InvalidOperationException {
		if(root !=null) {
			throw new InvalidOperationException("Ya fue creado el nodo raiz");
		}
		root = new TNodo(e,null);
		size++;
	}
	public Position<E> root() throws EmptyTreeException {
		if(size==0) {
			throw new EmptyTreeException("vacion");
		}
		return root;
	}
	public boolean isRoot(Position<E> v) {
		TNodo<E> check = checkPosition(v);
		return check == root;
	}


	private void preOrdenP(TNodo<E> e, PositionList<Position<E>> a) {
		if (e == null) {
	        return;
	    }
		a.addLast(e);
		for(TNodo<E> add : e.getHijos()) {
			preOrdenP(add,a);
		}
	}

	
	public Iterable<Position<E>> positions() {
		PositionList<Position<E>> l =new Mylist();
		
		preOrdenP(root,l);
		
		return l;
	}
	private void preOrden(TNodo<E> add, PositionList<E> l) {
		if(add!=null) {
			l.addLast(add.element());
		}
		
		if(!add.getHijos().isEmpty()) {
			for(TNodo<E> cap : add.getHijos()) {
				preOrden(cap,l);
			}
		}
	}
	
	public Iterable<Position<E>> children(Position<E> v) throws InvalidPositionException{
		if(v == null) {
			throw new InvalidPositionException("ac");
		}
		TNodo<E> check = checkPosition(v);
			
		PositionList<Position<E>> hijos =new Mylist();
		
		for(TNodo<E> ch : check.getHijos()) {
			hijos.addLast(ch);
		}
		return hijos;
		
	}
	public E replace(Position<E> v, E e) throws InvalidPositionException {
		try {
			TNodo<E> check = checkPosition(v);
			E toret = check.element();
			check.setElement(e);
			return toret;
		}catch(InvalidPositionException a) {
			throw new InvalidPositionException("invalido");
		}

	}

	public Position<E> parent(Position<E> v) throws BoundaryViolationException,InvalidPositionException{
			TNodo<E> check = checkPosition(v);
			if(check == root) {
				throw new BoundaryViolationException("es el nodo raiz");
			}
			return check.getPadre();	
	}
	public boolean isInternal(Position<E> v)  throws InvalidOperationException {
			TNodo<E> check = checkPosition(v);
			return check.getHijos().size()>0;
	}
	public boolean isExternal(Position<E> v) throws InvalidOperationException {		
			TNodo<E> check = checkPosition(v);
			return check.getHijos().size()==0;

	}
	public Position<E> addFirstChild(Position<E> p, E e) throws  InvalidPositionException{
		if(size==0) {
			throw new  InvalidPositionException("esta vacio");
		}
		try {
			TNodo<E> check = checkPosition(p);
			TNodo<E> newhijo = new TNodo(e,check);
			check.getHijos().addFirst(newhijo);
			size++;
			return newhijo;
		}
		catch(InvalidPositionException w){
			System.out.println("InvalidPositionException");
			return null;
		}
		
	}
	public Position<E> addLastChild(Position<E> p, E e) throws  InvalidPositionException{
		if(size==0) {
			throw new  InvalidPositionException("esta vacio");
		}
		try {
			TNodo<E> check = checkPosition(p);
			TNodo<E> newhijo = new TNodo(e,check);
			
			check.getHijos().addLast(newhijo);
			size++;
			return newhijo;
		}
		catch(InvalidPositionException w){
			System.out.println("InvalidPositionException");
			return null;
		}
	}

	public Position<E> addBefore(Position<E> p, Position<E> rb, E e) {
		boolean esta=false;
		TNodo<E> padre =checkPosition(p);
		TNodo<E> hermano =checkPosition(rb);
		if(hermano.getPadre()!=padre) {
			throw new InvalidPositionException("No es el padre");
		}
		TNodo<E>add = new TNodo(e,padre);
		Iterator<Position<TNodo<E>>> it = padre.getHijos().positions().iterator();
		
		while(!esta && it.hasNext()) {
			Position<TNodo<E>> busc = it.next();
			if(busc.element() == hermano) {
				esta=true;
				padre.getHijos().addBefore(busc, add);
			}
		}
		size++;
		return add;
	}
	@Override
	public void removeExternalNode(Position<E> p) throws InvalidPositionException {
		boolean eliminado=false;
		if(p==null || size ==0) {
			throw new  InvalidPositionException("Posicion invalida");
		}
		TNodo<E> nod =checkPosition(p);
		if(!nod.getHijos().isEmpty()) {
			throw new  InvalidPositionException("No es un nodo Externo");
		}
		if(nod == root) {
			if(root.getHijos().isEmpty()) {
				root=null;
			}else {
				throw new  InvalidPositionException("No se puede eliminar la raiz porque tiene al menos 1 hijo");
			}
		}else {
			Iterator<Position<TNodo<E>> > it = nod.getPadre().getHijos().positions().iterator();
			while(!eliminado && it.hasNext()) {
				Position<TNodo<E>> rec = it.next();
				if(rec.element().equals(nod)) {
					nod.getPadre().getHijos().remove(rec);
					nod.setPadre(null);
					eliminado=true;
				}
			}
		}
		size--;
	}

	
	public void removeInternalNode(Position<E> p) {
        TNodo<E> tnod = checkPosition(p);
        if (isExternal(tnod)){
            throw new InvalidPositionException("El nodo no es interno");
        }
        if (tnod == root){
            if (tnod.getHijos().size()>1){
                throw new InvalidPositionException("El nodo es la raiz, y tiene mas de un hijo");
            }
            root = tnod.getHijos().first().element();
            root.setPadre(null);
            size--;
        }
        else {
            Iterator<Position<TNodo<E>>> ite = tnod.getPadre().getHijos().positions().iterator();
            Position<TNodo<E>> posB = null;
            while (ite.hasNext() && posB == null){
                Position<TNodo<E>> pos = ite.next();
                if (pos.element() == tnod){
                    posB = pos;
                }
            }

            for (TNodo<E> h : tnod.getHijos()){
                h.setPadre(tnod.getPadre());
                tnod.getPadre().getHijos().addBefore(posB, h);
            }
            tnod.getPadre().getHijos().remove(posB);
            tnod.setPadre(null);
            size--;
        }
    }

	public void removeNode(Position<E> p) throws InvalidPositionException{
		if(size==0)
			throw new InvalidPositionException("No se puede eliminar de un arbol vacio");
		
		TNodo<E> n= checkPosition(p);
		try {
			if(n==root)// el nodo que se pretende eliminar es la raiz
				if(root.getHijos().size()==1)//la raiz tiene un solo hijo
				{
					Position<TNodo<E>> rN=root.getHijos().first();
					rN.element().setPadre(null);
					root.getHijos().remove(rN);
					root= rN.element();
				}
				else
					if (size==1)//el arbol tiene un unico nodo 
						root=null;
					else// se quiere eliminar la raiz pero no es posible por la estructura del arbol
						  throw new InvalidPositionException("Solo se puede eliminar la raiz si es el unico elemento o si tiene un solo hijo");
			else// Se quiere eliminar un nodo interno o un nodo hoja.
			{ 
				TNodo<E> padre=n.getPadre();
				PositionList<TNodo<E>> hPadre=padre.getHijos(); //hijos del padre (hermanos de n)
				PositionList<TNodo<E>> hN=n.getHijos();//hijos de n
					
				//buscar a n dentro de los hijos del padre
					Position<TNodo<E>>posDeN;
					Position<TNodo<E>> cursor= hPadre.first();
					while(cursor.element()!=n && cursor!=null){
						if (cursor==hPadre.last())
							cursor=null;
						else
							cursor= hPadre.next(cursor);}
					if(cursor!=null)	
						posDeN= cursor;
					else
						throw new InvalidPositionException("La estructura no corresponde a un arbol valido");
					
				while(!hN.isEmpty())
				{
					Position<TNodo<E>> hijoN=hN.first();
					hPadre.addBefore(posDeN,hijoN.element());
					hijoN.element().setPadre(padre);
					hN.remove(hijoN);
				}
				//eliminamos a n de la lista
				hPadre.remove(posDeN);
			}
			//decrementamos el tamaño de la estructura
			size--;
			} catch (EmptyListException  | BoundaryViolationException e) {
				throw new InvalidPositionException("La estructura no corresponde a un arbol valido");
			}
			}

	public Iterator<E> iterator() {
		PositionList<E> l = new Mylist();
		if(root!=null) {
			preOrden(root,l);
		}
		return l.iterator();
	}

	private TNodo<E> checkPosition(Position<E> p)throws InvalidPositionException{
        TNodo<E> n;
        if(isEmpty() || p==null){
            throw new InvalidPositionException("Esta mal xd");
        }
        try{
            n=(TNodo)p;
            return n;
        }
        catch(ClassCastException e){
            throw new InvalidPositionException("Posicion invalida");
        }
        
    }
	public Position<E> addAfter(Position<E> p, Position<E> lb, E e) throws InvalidPositionException {
		boolean esta=false;
		TNodo<E> padre =checkPosition(p);
		TNodo<E> hermano =checkPosition(lb);
		if(hermano.getPadre()!=padre) {
			throw new InvalidPositionException("No es el padre");
		}
		TNodo<E>add = new TNodo(e,padre);
		Iterator<Position<TNodo<E>>> it = padre.getHijos().positions().iterator();
		
		while(!esta && it.hasNext()) {
			Position<TNodo<E>> busc = it.next();
			if(busc.element() == hermano) {
				esta=true;
				padre.getHijos().addAfter(busc, add);
			}
			
		}
		size++;
		return add;
	}
	public Iterator<Position<E>> positionsPosOr() {
		PositionList<Position<E>> l =new Mylist();
		posOr(root,l);
		l.addLast(root);
		return l.iterator();
	}
	private void posOr(TNodo<E> a, PositionList<Position<E>> l ){
			Iterator<Position<TNodo<E>>> b = a.getHijos().positions().iterator();
			while(b.hasNext()) {
				Position<TNodo<E>> rem = b.next();
				posOr(rem.element(),l);
				l.addLast(rem.element());
				
				a.getHijos().remove(rem);
				a.setPadre(null);
				size--;
			}
	}
	public Iterator<Position<E>> covertirAHoja(Position<E> p)throws InvalidPositionException{
		try {
			PositionList<Position<E>> l =new Mylist();
			TNodo<E> tnod = checkPosition(p);
			if(!tnod.getHijos().isEmpty()) {
				posOr(tnod,l);
			}
			return l.iterator();
		}catch(InvalidPositionException a ) {
			System.out.print("Posicion Invalida");
			return null;
		}

	}
	
	
	public Iterator<Position<E>> positionIn(){
		PositionList<Position<E>> l =new Mylist();
		inOrden(root,l);
		return l.iterator();
	}
	
	private void inOrden(TNodo<E> a, PositionList<Position<E>> l ) {
		if(isExternal(a)) {
			l.addLast(a);
		}else {
			Position<TNodo<E>> primer = a.getHijos().first();
			inOrden(primer.element(),l);
			l.addLast(a);
			while(primer!=a.getHijos().last()) {
				primer=a.getHijos().next(primer);
				inOrden(primer.element(),l);
			}
		}
	}
	public boolean eliminarUltimoHijo(Position<E> p)throws InvalidPositionException{
		if(p == null) throw new  InvalidPositionException("pe");
		try {
			 TNodo<E> nodo = checkPosition(p);
			 if(nodo == root) throw new  InvalidPositionException("pe");
			 TNodo<E> padre = nodo.getPadre();
			 PositionList<TNodo<E>> lista_padre = padre.getHijos();
			 Position<TNodo<E>> ult = lista_padre.last();
			 boolean ultimo = ult.element() == nodo;
			 if(ultimo) {
				 lista_padre.remove(ult);
				 nodo.setPadre(null);
				 if(!nodo.getHijos().isEmpty()) {
					 Iterator<Position<TNodo<E>>> it = nodo.getHijos().positions().iterator();
					 while(it.hasNext()) {
						 Position<TNodo<E>> rec = it.next();
						 rec.element().setPadre(padre);
						 lista_padre.addLast(rec.element());
						 nodo.getHijos().remove(rec);
					 }
				 }
				 size--;
			 }
			 return ultimo;
		}catch(InvalidPositionException a) {
			a.printStackTrace();
			return false;
		}
		
	}
	public static void main(String[]arg) {
		TDAArbol<Character> arbol = new TDAArbol();
		
		arbol.createRoot('a');
		Position<Character> b = arbol.addLastChild(arbol.root(),'b');
		
		Position<Character> c = arbol.addLastChild(b,'c');
		Position<Character> d = arbol.addLastChild(b,'d');
		Position<Character> e = arbol.addLastChild(b,'e');
		arbol.addLastChild(e,'f');
		arbol.addLastChild(e,'g');
		System.out.println(arbol.eliminarUltimoHijo(e));
		for(Position<Character> ver : arbol.children(b)) {
			System.out.print(ver.element() + " ");
		}
		
	}
}
