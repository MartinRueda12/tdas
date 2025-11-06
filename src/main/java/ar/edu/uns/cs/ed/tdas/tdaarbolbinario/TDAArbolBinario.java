package ar.edu.uns.cs.ed.tdas.tdaarbolbinario;

import java.util.Iterator;
//import ar.edu.uns.cs.ed.tdas.tdaarbol.GTTree;
import ar.edu.uns.cs.ed.tdas.Entry;
import ar.edu.uns.cs.ed.tdas.Position;
import ar.edu.uns.cs.ed.tdas.excepciones.*;
import ar.edu.uns.cs.ed.tdas.tdadiccionario.Dictionary;
import ar.edu.uns.cs.ed.tdas.tdadiccionario.TDADiccionario;
import ar.edu.uns.cs.ed.tdas.tdalista.*;
import ar.edu.uns.cs.ed.tdas.tdamapeo.Map;
import ar.edu.uns.cs.ed.tdas.tdamapeo.TDAMapeo;

public class TDAArbolBinario<E> implements BinaryTree<E> {
	   protected BNodo<E> root = null;
	   protected int size = 0;

	   public int size() {
	      return this.size;
	   }

	   public void createRoot(E elem) throws InvalidOperationException {
	      if (this.root == null) {
	         this.root = new BNodo(elem, null, null, null);
	         ++this.size;
	      } else {
	         throw new InvalidOperationException("El árbol ya tiene una raíz.");
	      }
	   }

	   public boolean isEmpty() {
	      return this.size == 0;
	   }

	   public Position<E> root() throws EmptyTreeException {
	      if (this.size == 0) {
	         throw new EmptyTreeException("vacio");
	      } else {
	         return this.root;
	      }
	   }

	   public Iterator<E> iterator() {
	      PositionList<E> l = new Mylist();
	      this.postOrd(this.root, l);
	      return l.iterator();
	   }

	   private void postOrd(BNodo<E> b, PositionList<E> l) {
	      if (b != null) {
	         this.postOrd(b.getIzquierdo(), l);
	         this.postOrd(b.getDerecho(), l);
	         l.addLast(b.element());
	      }

	   }

	   private void postOrdP(BNodo<E> b, PositionList<Position<E>> l) {
	      if (b != null) {
	         this.postOrdP(b.getDerecho(), l);
	         this.postOrdP(b.getIzquierdo(), l);
	         l.addLast(b);
	      }

	   }

	   public Iterable<Position<E>> positions() {
	      PositionList<Position<E>> l = new Mylist();
	      if (this.root != null) {
	         this.postOrdP(this.root, l);
	      }

	      return l;
	   }

	   public E replace(Position<E> v, E e) {
	      BNodo<E> ch = this.checkPosition(v);
	      E toret = ch.element();
	      ch.setElemento(e);
	      return toret;
	   }

	   public Position<E> parent(Position<E> v) throws BoundaryViolationException {
	      BNodo<E> ch = this.checkPosition(v);
	      if (ch == this.root) {
	         throw new BoundaryViolationException("es la raiz");
	      } else {
	         return ch.getPadre();
	      }
	   }

	   public Iterable<Position<E>> children(Position<E> v) {
	      BNodo<E> ch = this.checkPosition(v);
	      PositionList<Position<E>> l = new Mylist();
	      if (ch.getDerecho() == null && ch.getIzquierdo() == null) {
	         return l;
	      } else {
	         if (ch.getDerecho() == null) {
	            l.addLast(ch.getIzquierdo());
	         } else if (ch.getIzquierdo() == null) {
	            l.addLast(ch.getDerecho());
	         } else {
	            l.addLast(ch.getIzquierdo());
	            l.addLast(ch.getDerecho());
	         }

	         return l;
	      }
	   }

	   public boolean isInternal(Position<E> v) {
	      BNodo<E> ch = this.checkPosition(v);
	      return ch.getDerecho() != null || ch.getIzquierdo() != null;
	   }

	   public boolean isExternal(Position<E> v) {
	      BNodo<E> ch = this.checkPosition(v);
	      return ch.getDerecho() == null && ch.getIzquierdo() == null;
	   }

	   public boolean isRoot(Position<E> v) {
	      BNodo<E> ch = this.checkPosition(v);
	      return ch == this.root;
	   }

	   public Position<E> left(Position<E> v) throws BoundaryViolationException {
	      BNodo<E> chk = this.checkPosition(v);
	      if (chk.getIzquierdo() == null) {
	         throw new BoundaryViolationException("No tiene hijo Izquierdo");
	      } else {
	         return chk.getIzquierdo();
	      }
	   }

	   public Position<E> right(Position<E> v) throws BoundaryViolationException {
	      BNodo<E> chk = this.checkPosition(v);
	      if (chk.getDerecho() == null) {
	         throw new BoundaryViolationException("No tiene hijo derecho");
	      } else {
	         return chk.getDerecho();
	      }
	   }

	   public boolean hasLeft(Position<E> v) {
	      BNodo<E> chk = this.checkPosition(v);
	      return chk.getIzquierdo() != null;
	   }

	   public boolean hasRight(Position<E> v) {
	      BNodo<E> chk = this.checkPosition(v);
	      return chk.getDerecho() != null;
	   }

	   public Position<E> addLeft(Position<E> v, E r) throws InvalidOperationException {
	      BNodo<E> chk = this.checkPosition(v);
	      if (chk.getIzquierdo() != null) {
	         throw new InvalidOperationException("ya tiene hijo derecho");
	      } else {
	         BNodo<E> add = new BNodo(r, chk, (BNodo)null, (BNodo)null);
	         chk.setIzquierdo(add);
	         ++this.size;
	         return add;
	      }
	   }

	   public Position<E> addRight(Position<E> v, E r) throws InvalidOperationException {
	      BNodo<E> chk = this.checkPosition(v);
	      if (chk.getDerecho() != null) {
	         throw new InvalidOperationException("ya tiene hijo izquierdo");
	      } else {
	         BNodo<E> add = new BNodo(r, chk, null, null);
	         chk.setDerecho(add);
	         ++this.size;
	         return add;
	      }
	   }

	   public void attach(Position<E> r, BinaryTree<E> T1, BinaryTree<E> T2) throws InvalidPositionException {
	      if (r != null && this.size != 0) {
	         BNodo<E> izq = null;
	         BNodo<E> der = null;
	         BNodo<E> chk = checkPosition(r);
	         if (T1.size() > 0) {
	            izq = checkPosition(T1.root());
	         }

	         if (T2.size() > 0) {
	            der = checkPosition(T2.root());
	         }

	         if (chk.getDerecho() == null && chk.getIzquierdo() == null) {
	            chk.setDerecho(der);
	            chk.setIzquierdo(izq);
	            size = size + T1.size() + T2.size();
	         } else {
	            throw new InvalidPositionException("V no es hoja");
	         }
	      } else {
	         throw new InvalidPositionException("Arbol vacio o Posicion Invalida");
	      }
	   }

	   public E removeNode(Position<E> r) throws InvalidPositionException {
	     if(r==null || size==0) throw new InvalidPositionException("Posicion nula o El arbol esta vacio");
	     
	    	 BNodo<E> chk = checkPosition(r);
	    	 if(chk.getIzquierdo()!=null && chk.getDerecho()!=null)
	    		 throw new InvalidPositionException("El nodo tiene mas de un hijo");
	    	 BNodo<E> child =(chk.getIzquierdo()!=null ? chk.getIzquierdo() : chk.getDerecho());

	    	 if(child!=null)
	    		 child.setIzquierdo(chk.getPadre());
	    	 if(chk==root)
	    		 root=child;
	    	 else {
	    		 BNodo<E> padre = chk.getPadre();
	    		 if(chk==padre.getIzquierdo())
	    			 padre.setIzquierdo(child);
	    		 else
	    			 padre.setDerecho(child);
	    	 }
	    	 E ret = chk.element();
	    	 size--;
	    	 chk.setPadre(chk);
	    	 return ret;
	     
	   }

	   public Dictionary<E, E> dic() {
	      Dictionary<E, E> dic = new TDADiccionario();
	      dic.insert(root.element(), root.getIzquierdo().element());
	      dic.insert(root.element(), root.getDerecho().element());
	      preOrd(root.getDerecho(), dic);
	      preOrd(root.getIzquierdo(), dic);
	      return dic;
	   }

	   private void preOrd(BNodo<E> b, Dictionary<E, E> e) {
	      if (b != null) {
	        
	         if (b.getIzquierdo() != null) {
	            e.insert(b.element(), b.getIzquierdo().element());
	         }

	         if (b.getDerecho() != null) {
	            e.insert(b.element(), b.getDerecho().element());
	         }
	         this.preOrd(b.getDerecho(), e);
	         this.preOrd(b.getIzquierdo(), e);
	      }

	   }
	   
	   public Map<E,Integer> devM(){
		  
		   Map<E,Integer> ret = new TDAMapeo();
		   ret.put(root.element(), 1);
		   postOrM(root,ret);
		   return ret;
	   }
	   public void eliminarDerecho() throws EmptyTreeException, InvalidOperationException {
		   if(size==0) throw new  EmptyTreeException("Arbol vacio");
		   if(root.getDerecho()== null) throw new InvalidOperationException("No hay lado derecho en el arbol");
		   
		   posOrDerecho(root.getDerecho());
	   }
	   private void posOrDerecho(BNodo<E> p) {
		   if(p!= null) {
			   BNodo<E> padre = p.getPadre();
			   if(p.getDerecho()!= null) {
				   posOrDerecho(p.getDerecho());
			   }
			   if(p.getIzquierdo()!=null) {
				   posOrDerecho(p.getIzquierdo());
			   }
			   
			   if(padre.getIzquierdo() == p) 
				   padre.setIzquierdo(null);
			   else
				   padre.setDerecho(null);
			   p.setPadre(null);
			   p.setElemento(null);
			   size--;
			   
		   }
	   }
	   private void postOrM(BNodo<E> p, Map<E,Integer> m) {
		   if(p!=null) {
			   boolean encontre =false;
			   Iterator<Entry<E,Integer>> ir = m.entries().iterator();
			   while(!encontre && ir.hasNext()) {
				   Entry<E,Integer> rec = ir.next();
				   if(rec.getKey().equals(p.element())) {
					   encontre=true;
					   m.put(p.element(), rec.getValue()+1); 
				   }
			   }
			   if(!encontre)m.put(p.element(), 1);
			   if(p.getDerecho()!=null)postOrM(p.getDerecho(),m);
			   if(p.getIzquierdo()!=null)postOrM(p.getIzquierdo(),m);
		   }
	   }

	   private BNodo<E> checkPosition(Position<E> p) throws InvalidPositionException {
	      if ( p == null || size==0) {
	         throw new InvalidPositionException("Posicion invalida");
	      }
	         try {
	        	 
	            BNodo<E> ch = (BNodo)p;
	            
	            return ch;
	         } catch (ClassCastException var3) {
	            return null;
	         }
	      }
	   

		  public static void main(String[] args) {
		        // Creamos el árbol del ejemplo anterior
		        TDAArbolBinario<Integer> miArbol = new TDAArbolBinario<>();
		        miArbol.createRoot(1);
		        Position<Integer> tres = miArbol.addLeft(miArbol.root(), 3);
		        //Position<Integer> cuatro =miArbol.addRight(miArbol.root(), 4);
		        Position<Integer> siete = miArbol.addLeft(tres, 7);
		        //Position<Integer> cinco = miArbol.addLeft(cuatro, 7);
		       // miArbol.addRight(cinco, 3);
		        miArbol.addLeft(siete, 2);
		        //Dictionary<Integer,Integer> dev = miArbol.retDic();
		        //System.out.print(dev.size());
		        miArbol.completarDerechoPos(1);;
		        for(Integer a: miArbol) {
		        	System.out.print(a + " ");
		        }
		        /*for(Entry<Integer,Integer> mos : dev.entries()) {
		        	System.out.print("(" + mos + ")" + " ");
		        }*/
		  }
		  
		  /*public Dictionary<E,E> retDic()throws EmptyTreeException {
			  if(size==0)throw new EmptyTreeException("Arbol Vacio");
			  
			  Dictionary<E,E> toRet= new TDADiccionario();
			  if(root.getDerecho()!=null) {
				  toRet.insert(root.element(), root.getDerecho().element());
			  	  recDic(toRet,root.getDerecho());}
			  if(root.getIzquierdo()!=null) {
				  toRet.insert(root.element(), root.getIzquierdo().element());
			  	  recDic(toRet,root.getIzquierdo());}
			  return toRet;
		  }*/
		  
		  /*private void recDic(Dictionary<E,E> d, BNodo<E> b) {
			  if(b.getDerecho()!=null) {
				  d.insert(b.element(), b.getDerecho().element());
				  recDic(d,b.getDerecho());
			  }
			  if(b.getIzquierdo()!=null) {
				  d.insert(b.element(), b.getIzquierdo().element());
				  recDic(d,b.getIzquierdo());
			  }
			  
		  */
		  public void completarDerechoPos(E e) throws EmptyTreeException {
			  if(size==0)throw new EmptyTreeException("arbol vacio");
			  compOr(e,root);
		  }
		  private void compOr(E e, BNodo<E> b) {
			  if(b!=null) {
				  if(b.getIzquierdo()!=null) {
					  compOr(e,b.getIzquierdo());
				  }
				  if(b.getDerecho()!=null) {
					  compOr(e,b.getDerecho());
				  }
				  
				  if(b.getDerecho()==null) {
					  BNodo<E> add = new BNodo(e,b,null,null);
					  b.setDerecho(add);
				  }
					  
				  
			  }
		  }
}
	   


	

