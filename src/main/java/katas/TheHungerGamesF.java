/*
Un extraño corte de energía en el zoológico ha causado que todas las puertas de la jaula eléctrica
funcionen mal y se abran ...
¡Todos los animales están fuera y algunos de ellos se están comiendo entre sí!
¡Es un desastre zoológico!

PreCondiciones:
1. La funcion debe devolver una cadena con todas las cosas del zoologico separadas por comas.

Acciones:
1. DatoEntradaRetornarAnimalEatsAnimal

PostCondiciones:
1. La Funcion debe devolver un array con el primer elemento inicial de todas las cosas del zoo.
2. La funcion debe devolver un array con el ultimo elemento separado por comas de la ultima comida.
3. La funcion debe devolver los demas elementos (segundo al ultimo) en un array.

 */
package katas;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TheHungerGamesF {
    private static final Map<String, String> map = new HashMap<String, String>();
    static {
        map.put("antelope", "grass");
        map.put("big-fish", "little-fish");
        map.put("bug", "leaves");
        map.put("bear", "big-fish,bug,chicken,cow,leaves,sheep");
        map.put("chicken", "bug");
        map.put("cow", "grass");
        map.put("fox", "chicken,sheep");
        map.put("giraffe", "leaves");
        map.put("lion", "antelope,cow");
        map.put("panda", "leaves");
        map.put("sheep", "grass");
    }
    private static String ComerIzquierdaOrDerecha(String presa, String izquierda , String derecha){
        String resultado = puedeComer(presa,izquierda);
        if (resultado.equals("")) {
            return puedeComer(presa,derecha);
        }
        return resultado;
    }
    private static String puedeComer(String presa, String cazador){
        if (!map.containsKey(presa) || "".equals(cazador)){
            return "";
        }
        if (map.get(presa).contains(cazador)){
            return String.format("%s comido %s",presa,cazador);
        }
        return "";
    }
    private static StringBuffer reduce(String[] arr, int presa, String izquierda, String derecha, String regla){
        String objetivo = regla.split(" ")[2];
        int eliminar = -1;
        if (objetivo.equals(izquierda)){
            eliminar = presa -1;
        }
        if (objetivo.equals(derecha)){
            eliminar = presa -1;
        }
        StringBuffer cadenaBuffer = new StringBuffer();
        for (int i = 0; i < arr.length ; i++) {
            if (i != eliminar) {
                cadenaBuffer.append(arr[i]).append(",");
            }
        }
        int len =cadenaBuffer.length();
        if (cadenaBuffer.charAt(len - 1) == ',') {
            cadenaBuffer.deleteCharAt(len -1);
        }
        return cadenaBuffer;
    }
    public static String[] whoEatsWho(final String zoo) {
        // Your code here
        List<String> lista = new ArrayList<>();
        lista.add(zoo);
        StringBuffer cadenaBuffer = new StringBuffer(zoo);
        boolean reduccion = true;
        Label: while (true){
            String[] arr = cadenaBuffer.toString().split(",");
            if (arr.length<2){
                break;
            }
            for (int i = 0; i < arr.length; i++) {
                String presa = arr[i];
                String izquierda = i > 0 ? arr[i - 1] : "";
                String derecha = i < arr.length - 1 ? arr[i+1] : "";

                String check = ComerIzquierdaOrDerecha(presa,izquierda,derecha);

                if (!check.equals("")){
                    lista.add(check);
                    cadenaBuffer = reduce(arr,i,izquierda,derecha,check);
                    break;
                }
                if (i == arr.length -1) {
                    reduccion = false;
                    break Label;
                }
            }
        }
        lista.add(cadenaBuffer.toString());
        return lista.toArray(new String[0]);
        //return new String[]{zoo, zoo};
    }
}
