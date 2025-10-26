// Asegúrate que el paquete sea el mismo de tu ejercicio
package ar.edu.uns.cs.ed.tdas.tdamapeo;

/**
 * Una clase genérica simple para guardar dos objetos juntos.
 * K representa el tipo del primer elemento (key).
 * V representa el tipo del segundo elemento (value).
 */
public class Pair<K, V> {

    private final K key;
    private final V value;

    // Constructor para crear un par con sus dos valores
    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    // Método para obtener el primer valor
    public K getKey() {
        return key;
    }

    // Método para obtener el segundo valor
    public V getValue() {
        return value;
    }

    // (Opcional) Un método útil para imprimir el par fácilmente
    @Override
    public String toString() {
        return "(" + key + ", " + value + ")";
    }
}
