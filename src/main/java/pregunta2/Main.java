package pregunta2;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class Main {
	 public static void main(String[] args) {
	        // ruta del archivo
		 String archivoJson = "src/main/resources/cuentas.json";

	        
	        try {
	            // leer archivo .json
	            FileReader reader = new FileReader(archivoJson);
	            Type listType = new TypeToken<List<Cuenta>>() {}.getType();
	            List<Cuenta> cuentas = new Gson().fromJson(reader, listType);
	            reader.close();

	            for (Cuenta cuenta : cuentas) {
	                if (cuenta.isEstado()) {
	                    generarArchivoResultado(cuenta);
	                }
	            }

	        } catch (IOException e) {
	            System.out.println("Error al leer el archivo: " + e.getMessage());
	        }
	    }

	    // archivo de texto segun estado de cuenta 
	    public static void generarArchivoResultado(Cuenta cuenta) {
	        String nombreArchivo = "cuenta_" + cuenta.getNro_cuenta() + ".txt";
	        try (FileWriter writer = new FileWriter(nombreArchivo)) {
	            // Verificar si la cuenta tiene más de 5000 soles
	            if (cuenta.getSaldo() > 5000.00) {
	                // Generar archivo para cuentas con saldo mayor a 5000
	                writer.write("Banco de origen: " + cuenta.getBanco() + "\n");
	                writer.write("La cuenta con el nro de cuenta: " + cuenta.getNro_cuenta() + 
	                             " tiene un saldo de " + cuenta.getSaldo() + ".\n");
	                writer.write("Usted es apto a participar en el concurso de la SBS por 10000.00 soles.\n");
	            } else {
	                // Generar archivo para cuentas con saldo menor o igual a 5000
	                writer.write("Banco de origen: " + cuenta.getBanco() + "\n");
	                writer.write("La cuenta con el nro de cuenta: " + cuenta.getNro_cuenta() + 
	                             " no tiene un saldo superior a 5000.00.\n");
	                writer.write("Lamentablemente no podrá acceder al concurso de la SBS por 10000.00 soles.\n");
	                writer.write("Gracias\n");
	            }

	            System.out.println("Archivo generado: " + nombreArchivo);

	        } catch (IOException e) {
	            System.out.println("Error al generar el archivo: " + e.getMessage());
	        }
	    }
}
