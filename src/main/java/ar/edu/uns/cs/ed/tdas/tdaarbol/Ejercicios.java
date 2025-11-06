package ar.edu.uns.cs.ed.tdas.tdaarbol;
import java.util.Iterator;

import ar.edu.uns.cs.ed.tdas.Entry;
import ar.edu.uns.cs.ed.tdas.Position;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidPositionException;
import ar.edu.uns.cs.ed.tdas.tdalista.*;
import ar.edu.uns.cs.ed.tdas.tdamapeo.*;

public class Ejercicios<E> {
	public Ejercicios() {}	
	public  int eliminarApariciones(Tree<E> a, E e ) {
		int elim=0;
		Iterator<Position<E>> it =a.positions().iterator();
		
		while(it.hasNext()) {
			Position<E> rec = it.next();
			if(rec.element().equals(e)) {
				a.removeNode(rec);
				elim++;
			}
		}
		return elim;
	}

	
	
	public boolean pertenece(Tree<Integer> tree, Integer a) {
		Iterator<Integer> it = tree.iterator();
		boolean pertenece =false;
		while(!pertenece && it.hasNext()) {
			if(it.next().equals(a)) {
				pertenece=true;
			}
		}
		return pertenece;
	}
	public Map<Character, Integer> cantidadRepeticiones(Tree<Character> t){
		Map<Character, Integer> ret = new TDAMapeo();
		
		ret.put(t.root().element(), 1);
		preOr(t.root(),t,ret);
		
		return ret;
	}
	private void preOr(Position<Character> c, Tree<Character> t, Map<Character, Integer> ret) {
		boolean act=false;
		Iterator<Position<Character>> b = t.children(c).iterator();
		Iterator<Entry<Character,Integer>> check = ret.entries().iterator();
		while(!act && check.hasNext()) {
			Entry<Character,Integer> p = check.next();
			if(c.element().equals(p.getKey()) && !t.isRoot(c)) {
				act=true;
				ret.put(p.getKey(), p.getValue()+1);
				
			}
		}
		if(!act) {
			
			ret.put(c.element(), 1);
		}
		act=false;
		while(b.hasNext()) {
			Position<Character> rec = b.next();
			preOr(rec,t,ret);
			
		}
		
	}
	private void aparicionPostOr(PositionList<Position<String>>l, Position<String> pos, Tree<String> t,String b) {
		Iterator<Position<String>> av = t.children(pos).iterator();
		while(av.hasNext()) {
			Position<String> ver = av.next();

			aparicionPostOr(l,ver,t,b);
		}
		if(pos.element().equals(b)) {
			l.addLast(pos);
		}
	}
	public Iterable<Position<String>> apariciones(Tree<String> t, String b){
		PositionList<Position<String>> ap = new Mylist();
		aparicionPostOr(ap,t.root(),t,b);
		if(t.root().element().equals(b)) {
			ap.addLast(t.root());
		}
		return ap;
	}
	
	public int cantdeA(Tree<Character> t) {
		int cant = 0;
		for(char ver : t) {
			if(ver =='a')cant++;
		}
		return cant;
	}
	 public  int sizeSubArbol(Tree<E> t,Position<E> p) throws InvalidPositionException{
		 if(p == null) throw new InvalidPositionException("Posicion nula");
		 return sizePreOrd(p,t);
	 }
	   private int sizePreOrd(Position<E> n, Tree<E> t) {
		   int cant=1;
		   for(Position<E> rec : t.children(n)) {
			   cant=sizePreOrd(rec,t)+cant;
		   }
		   return cant;
	   }
	public static void main(String[]arg) {
		Ejercicios<Character > ej = new Ejercicios();
		TDAArbol<Character> arbol = new TDAArbol();
		
		arbol.createRoot('a');
		Position<Character> b = arbol.addLastChild(arbol.root(),'b');
		
		Position<Character> c = arbol.addLastChild(b,'c');
		Position<Character> d = arbol.addLastChild(b,'d');
		Position<Character> e = arbol.addLastChild(b,'e');
		arbol.addLastChild(e,'f');
		arbol.addLastChild(e,'g');
		System.out.print(ej.sizeSubArbol(arbol, e));
		/*Tree<Character> arbol = new TDAArbol();
		
		arbol.createRoot('a');
		arbol.addLastChild(arbol.root(), 'a');
		
		Position<Character> c = arbol.addLastChild(arbol.root(), 'c');
		Position<Character> e = arbol.addLastChild(c, 'e');
		arbol.addLastChild(e, 'e');
		Position<Character> b = arbol.addLastChild(arbol.root(), 'b');
		arbol.addLastChild(b, 'u');
		arbol.addLastChild(b, 'u');
		
		Map<Character,Integer> mp = ej.cantidadRepeticiones(arbol);
		
		for(Entry<Character,Integer> most : mp.entries()){
			System.out.print("(" + most + ")" + ",");
		}*/
	}
	
	
}
