package ar.edu.uns.cs.ed.tdas.tdamapeo;


import java.util.Iterator;

import ar.edu.uns.cs.ed.tdas.Entry;
import ar.edu.uns.cs.ed.tdas.Position;
import ar.edu.uns.cs.ed.tdas.tdalista.*;

public class Ejercicio {

	public static  PositionList<Pair<Entrada<Integer,Integer>,Entrada<Integer,Integer>>>faga(Map<Integer,Integer> m1, Map<Integer,Integer> m2) {
		 PositionList<Pair<Entrada<Integer,Integer>,Entrada<Integer,Integer>>>toret = new Mylist();
		 Iterator<Entry<Integer,Integer>> p1 = m1.entries().iterator();
		 boolean add =false;
		 while(p1.hasNext()) {
			 add =false;
			 Entry<Integer,Integer> nota1 = p1.next();
			 Iterator<Entry<Integer,Integer>> p2 = m2.entries().iterator();
			 while(!add && p2.hasNext()) {
				 Entry<Integer,Integer> nota2 = p2.next();
				 if(nota1.getKey().equals(nota2.getKey()) && !nota1.getValue().equals(nota2.getValue())) {
					 toret.addLast(new Pair(nota1,nota2));
					 add=true;
				 }
			 }
		 }
		return toret;
	}
	
	public Map<Character,Integer> rep(PositionList<Character> l){
		boolean esta=false;
		Map<Character,Integer> rep= new TDAMapeo();
		Iterator<Entry<Character,Integer>> busc_k;
		Position<Character> rec=l.first();
		rep.put(rec.element(), 1);
		while(rec!=l.last()) {
			rec=l.next(rec);
			busc_k=rep.entries().iterator();
			while(!esta && busc_k.hasNext()) {
				Entry<Character,Integer> i = busc_k.next();
				if(i.getKey()==rec.element()) {
					rep.put(rec.element(),i.getValue()+1);
					esta=true;
				}
			}
			if(!esta) {
				rep.put(rec.element(),1);
			}
			esta=false;
		}
		return rep;
	}
	public static void main(String[] args) {
		PositionList<Character> chars = new Mylist();
		chars.addLast('a');
		chars.addLast('b');
		chars.addLast('b');
		chars.addLast('d');
		chars.addLast('e');
		chars.addLast('f');
		Map<Character,Integer> ret = new Ejercicio().rep(chars);
		
		for(Entry<Character,Integer> a: ret.entries()) {
			 System.out.print("(" + a + ")" + " ");
		}
		        /*System.out.println("--- INICIANDO PRUEBA DEL MÉTODO ---");

		        // -----------------------------------------------------------------
		        // 1. ARRANGE: Preparamos los datos de entrada
		        // -----------------------------------------------------------------

		     
		        Map<Integer, Integer> mapeoNotasParcial1 = new TDAMapeo<>();
		        mapeoNotasParcial1.put(101, 8);  // Coincide LU, distinta nota -> DEBE ESTAR EN LA LISTA
		        mapeoNotasParcial1.put(102, 6);  // Coincide LU, misma nota -> NO DEBE ESTAR
		        mapeoNotasParcial1.put(103, 9);  // LU solo existe en M1 -> NO DEBE ESTAR

		      
		        Map<Integer, Integer> mapeoNotasRecuperatorio = new TDAMapeo<>();
		        mapeoNotasRecuperatorio.put(101, 7);  // Coincide LU, distinta nota -> DEBE ESTAR EN LA LISTA
		        mapeoNotasRecuperatorio.put(102, 6);  // Coincide LU, misma nota -> NO DEBE ESTAR
		        mapeoNotasRecuperatorio.put(103, 9);  // LU solo existe en M2 -> NO DEBE ESTAR
		        	
		        System.out.println("\nDatos de Entrada:");
		        System.out.println("Mapeo M1 (Parcial 1): " + mapeoNotasParcial1.toString());
		        System.out.println("Mapeo M2 (Recuperatorio): " + mapeoNotasRecuperatorio.toString());

		        PositionList<Pair<Entrada<Integer,Integer>,Entrada<Integer,Integer>>>p1 = faga(mapeoNotasParcial1,mapeoNotasRecuperatorio );
		        System.out.println(p1.size());
		     
		        for(Pair<Entrada<Integer,Integer>,Entrada<Integer,Integer>> a : p1) {
		        	System.out.println(a.toString() + " " + a.toString());
		        }
		        
		    }*/
		
	}
}


