package ar.edu.uns.cs.ed.tdas.tdaarbolbinario;

import java.util.Iterator;

import ar.edu.uns.cs.ed.tdas.Entry;
import ar.edu.uns.cs.ed.tdas.Position;
import ar.edu.uns.cs.ed.tdas.excepciones.EmptyTreeException;


import ar.edu.uns.cs.ed.tdas.tdamapeo.Map;
import ar.edu.uns.cs.ed.tdas.tdamapeo.TDAMapeo;
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
		  
	  }*/
	  
	  public static void main(String[] args) {
	        // Creamos el árbol del ejemplo anterior
	        TDAArbolBinario<Integer> miArbol = new TDAArbolBinario<>();
	        miArbol.createRoot(1);
	        Position<Integer> tres = miArbol.addLeft(miArbol.root(), 3);
	        Position<Integer> cuatro =miArbol.addRight(miArbol.root(), 4);
	        Position<Integer> siete = miArbol.addLeft(tres, 7);
	        Position<Integer> cinco = miArbol.addLeft(cuatro, 5);
	        miArbol.addRight(cinco, 3);
	        miArbol.addLeft(siete, 1);
	        
	        /*for(Integer a : miArbol) {
	        	System.out.print(a + " ");
	        }*/
	        Ejercicios<Integer> ver = new Ejercicios();
	        Map<Integer,Integer> a = ver.mapeo(miArbol);
	        //System.out.println(ver.iguales(5, miArbol));
	        
	        for(Entry<Integer,Integer> b : a.entries()) {
	        	System.out.print("(" + b + ")" + " ");
	        }
	  }
}
