package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Glucosa {

    // Clasificacion
    private static final int NORMAL_LIMIT = 99;
    private static final int PREDIABETES_MIN = 100;
    private static final int PREDIABETES_MAX = 125;
    private static final int DIABETES_LIMIT = 126;

    public static void main(String[] args) throws InterruptedException {
    	
        // 3 arrays con numeros enteros
        List<Integer> glucosaArray1 = new ArrayList<>();
        List<Integer> glucosaArray2 = new ArrayList<>();
        List<Integer> glucosaArray3 = new ArrayList<>();

        // llenar los arrays con valores aleatorios
        llenarArray(glucosaArray1);
        llenarArray(glucosaArray2);
        llenarArray(glucosaArray3);

        // mostrar valores de arrays
        System.out.println("Array 1: " + glucosaArray1);
        System.out.println("Array 2: " + glucosaArray2);
        System.out.println("Array 3: " + glucosaArray3);

        // ejecuta hilos 
        Thread thread1 = new Thread(new ClasificadorRunnable(glucosaArray1, "Array 1"));
        Thread thread2 = new Thread(new ClasificadorRunnable(glucosaArray2, "Array 2"));
        Thread thread3 = new Thread(new ClasificadorRunnable(glucosaArray3, "Array 3"));

        // iniciar hilos
        thread1.start();
        thread2.start();
        thread3.start();

        // unir hilos
        thread1.join();
        thread2.join();
        thread3.join();

        // imprime arrays de los hilos cuando termina
        System.out.println("\nClasificación de Array 1:");
        System.out.println(glucosaArray1);

        System.out.println("\nClasificación de Array 2:");
        System.out.println(glucosaArray2);

        System.out.println("\nClasificación de Array 3:");
        System.out.println(glucosaArray3);

        // combina los arrays
        List<Integer> resultados = new ArrayList<>(glucosaArray1);
        resultados.addAll(glucosaArray2);
        resultados.addAll(glucosaArray3);

        // mostrar clasificacion por array
        mostrarResultados(resultados);
    }

    // valores aleatorios de glucosa comprendidos entre 50 - 150
    private static void llenarArray(List<Integer> array) {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            array.add(random.nextInt(150) + 50);
        }
    }

    
    // Runnable para clasificacion de glucosa 
    static class ClasificadorRunnable implements Runnable {
        private List<Integer> array;
        private String nombreArray;

        public ClasificadorRunnable(List<Integer> array, String nombreArray) {
            this.array = array;
            this.nombreArray = nombreArray;
        }

        @Override
        public void run() {
            for (int i = 0; i < array.size(); i++) {
                int nivelGlucosa = array.get(i);
                int clasificacion = clasificarNivel(nivelGlucosa);
                array.set(i, clasificacion); // guarda clasificacion en el array
            }
        }
    }

    // metodo para clasificar glucosa
    private static int clasificarNivel(int nivelGlucosa) {
        if (nivelGlucosa <= NORMAL_LIMIT) {
            return 0; // Normal
        } else if (nivelGlucosa >= PREDIABETES_MIN && nivelGlucosa <= PREDIABETES_MAX) {
            return 1; // Prediabetes
        } else if (nivelGlucosa >= DIABETES_LIMIT) {
            return 2; // Diabetes
        }
        return -1; // Error
    }

    // metodo para mostrar los resultados en %
    private static void mostrarResultados(List<Integer> resultados) {
        int normales = 0, prediabetes = 0, diabetes = 0;
        for (int resultado : resultados) {
            switch (resultado) {
                case 0: normales++; break;
                case 1: prediabetes++; break;
                case 2: diabetes++; break;
            }
        }

        int total = resultados.size();
        System.out.println("\nClasificación de los resultados:");
        System.out.printf("Normal: %.2f%%\n", (normales * 100.0 / total));
        System.out.printf("Prediabetes: %.2f%%\n", (prediabetes * 100.0 / total)); 
        System.out.printf("Diabetes: %.2f%%\n", (diabetes * 100.0 / total));
    }
}
