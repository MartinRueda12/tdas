package ar.edu.uns.cs.ed.tdas.tdaarbolbinario;

import java.util.Iterator;

import ar.edu.uns.cs.ed.tdas.Entry;
import ar.edu.uns.cs.ed.tdas.Position;
import ar.edu.uns.cs.ed.tdas.excepciones.EmptyTreeException;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidOperationException;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidPositionException;
import ar.edu.uns.cs.ed.tdas.tdamapeo.Map;
import ar.edu.uns.cs.ed.tdas.tdamapeo.TDAMapeo;
import ar.edu.uns.cs.ed.tdas.tdaarbol.*;
public class Ejercicios<E> {
	
	  public Map<E,Integer> mapeo(BinaryTree<E> t) throws EmptyTreeException {
		  E aux=null;
	      if (t.size() == 0) {
	         throw new EmptyTreeException("gaga");
	      }
	      boolean encontre=false;
	      Map<E,Integer> reto = new TDAMapeo();
	      Iterator<Entry<E,Integer>> it;
	      Iterator<E> arbol =t.iterator();
	      reto.put(arbol.next(), 1);
	      while(arbol.hasNext()) {
	    	  E rec = arbol.next();
	    	  it = reto.entries().iterator();
	    	  while(!encontre && it.hasNext()) {
	    		  if(it.next().getKey().equals(rec)) {
	    			  encontre=true;
	    			  reto.put(rec, reto.get(rec)+1);
	    		  }
	    	  }
	    	  if(!encontre) {
	    		  reto.put(rec,1);
	    	  }
	    	  encontre=false;
	      }
	      return reto;
	   }
	  
	  /*private void posOr(Position<E> p ,  BinaryTree<E> t, Map<E,Integer> a) {
		  
		  if(p!=null) {
			  
			  if(t.hasLeft(p)) {
				  posOr(t.left(p),t,a);
			  }
			  if(t.hasRight(p)) {
				  posOr(t.right(p),t,a);
			  }

		  }
		  
	  }
	 
		public Map<Character, Integer> eliminarHojas(BinaryTree<Character> arbol,Position<Character> p) throws InvalidPositionException {
			if(p == null) throw new InvalidPositionException("posicion invalida");
			Map<Character, Integer> toret = new TDAMapeo();
			posOr(arbol,p,toret);
			return toret;
		}*/
		/*private void posOr(BinaryTree<Character> t,Position<Character> p, Map<Character, Integer> m){
			if(p!=null) {
				if(t.hasLeft(p)) {
					posOr(t,t.left(p),m);
				}
				if(t.hasRight(p)) {
					posOr(t,t.right(p),m);
				}
				if(t.isExternal(p)) {
					Integer a = m.get(p.element());
					if(a!=null) {
						m.put(p.element(), a+1);
						
					}else {
						m.put(p.element(), 1);
					}
				}
			}
		}*/
		
		public static void eliminarLadoDerecho(BinaryTree<Integer> t)throws EmptyTreeException, InvalidOperationException {
			if(t.isEmpty())throw new EmptyTreeException("el arbol esta vacio");
			Position<Integer> raiz = t.root();
			Position<Integer> derecho = null;
			if(!t.hasRight(raiz))throw new InvalidOperationException("No tiene lado derecho el arbol");
			else
				derecho=t.right(raiz);
			
			posOr(t,derecho);
			
		}
		private static void posOr(BinaryTree<Integer> t,Position<Integer> p) {
			if(p!=null) {
				if(t.hasLeft(p)) {
					posOr(t,t.left(p));
				}
				if(t.hasRight(p)) {
					posOr(t,t.right(p));
				}
				
				t.removeNode(p);
			}
		}
	  public static void main(String[] args) {
	        // Creamos el árbol del ejemplo anterior
	        TDAArbolBinario<Integer> miArbol = new TDAArbolBinario<>();
	        miArbol.createRoot(1);
	        Position<Integer> tres = miArbol.addLeft(miArbol.root(), 3);
	        Position<Integer> cinco = miArbol.addRight(miArbol.root(), 5);
	        miArbol.addLeft(tres, 3);
	        miArbol.addRight(tres, 2);
	        Position<Integer> tresb = miArbol.addLeft(cinco, 3);
	        Position<Integer> dos = miArbol.addRight(cinco, 2);
	        miArbol.addLeft(tresb, 5);
	        miArbol.addRight(tresb, 6);
	        miArbol.addLeft(dos, 1);
	        miArbol.addRight(dos, 5);
	       
	       
	        for(Position<Integer> gh : miArbol.positions()){
	        	  System.out.print(gh.element() + " ");
	        }
	       
	        System.out.println(miArbol.size());
	        System.out.println();
	        eliminarLadoDerecho(miArbol);
	        System.out.println(miArbol.size());
	        System.out.println();
	        for(Position<Integer> gh : miArbol.positions()){
	        	  System.out.print(gh.element() + " ");
	        }
	  }
}
