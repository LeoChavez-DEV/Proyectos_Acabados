import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class LeerLinea {

    //Funcion de confirmación
    static boolean respuestaSN(String mensaje) {
        int seleccion = JOptionPane.showConfirmDialog(null, mensaje, "Seleccione la opción deseada",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return seleccion == 0;     
    }
    
    static long LeerLong(String mensaje, long min, long max) {
        String cad; // Cadena leida (a convertir en long)
        long valor = 0; // Valor introducido
        boolean repetir = false; // Variable para bucle 
        if (min > max) {
            long aux = min;
            min = max;
            max = aux;
        }
        do {
            cad = JOptionPane.showInputDialog(null, mensaje, "Introduce un valor entero > 1",
                    JOptionPane.QUESTION_MESSAGE);
            // Si presionamos el boton de cancelar abandonamos el programa
            if (cad == null) {
                JOptionPane.showMessageDialog(null, "Cancelaste la búsqueda.", "Se ha cancelado la busqueda",
                        JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            } else {
                // Si no se puede pasar a long, se genera una excepción
                try {
                    valor = Long.parseLong(cad);
                    if (valor < min || valor > max)
                        throw new Exception("Valor fuera de rango");
                    repetir = false;
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,
                            "Se esperaba un valor entre: " + min + " y " + max, " Introduce un valor entero correcto",
                            JOptionPane.ERROR_MESSAGE);
                    repetir = true;
                }
            }
        } while (repetir);
        return valor;
    }
    // Damos a elegir un arhivo con fileChooser
    static File seleccionaFitxer() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccione un archivo");
        int seleccion = fileChooser.showOpenDialog(null);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = fileChooser.getSelectedFile();
            JOptionPane.showMessageDialog(null,
                    "Archivo seleccionado: " + archivoSeleccionado.getAbsolutePath(), "Archivo seleccionado",
                    JOptionPane.INFORMATION_MESSAGE);
            return archivoSeleccionado;
        } else {
            return null;
        }
    }
    
    static void visualizaLinea(String nomFitxer, long numLinia) {
        String linia; // Línea leída del fichero
        int numLineas = 0; // Número de líneas al fichero
        try (FileInputStream fis = new FileInputStream(nomFitxer);
         InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
         BufferedReader bReader = new BufferedReader(isr)) {
            // Leemos las líneas del fichero
            while ((linia = bReader.readLine()) != null) {
                // Verificamos si la línea leída es vacía o contiene solo caracteres de espacio en blanco
                if (linia.trim().isEmpty()) {
                    continue;
                }
                numLineas++;
                if (numLineas == numLinia) {
                    JOptionPane.showMessageDialog(null, linia, "Línea " + numLinia,
                            JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }
            JOptionPane.showMessageDialog(null, "No se encontró la línea " + numLinia + " del fichero, el fichero solo tiene " + numLineas
                    + " líneas.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al leer el archivo", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void main(String[] args) {
        //Iniciamos el programa con nuestra intro
        if (!respuestaSN("Este programa está hecho por: Leonardo Steven Chávez Ricaurte. \nTendrás que elegir un archivo y la línea que quieras que lea el programa. \n¿Quieres usar el programa?")) {
            JOptionPane.showMessageDialog(null, "¡Es una pena que te vayas tan pronto. \n\t ヽ(；▽；)/", "Adiós",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        File archivo;
        //Do while en donde preguntamos al usuario si desea continuar pese a errores
        do {
            archivo = seleccionaFitxer();
            if (archivo != null && archivo.isFile()) {
                long numLinia = LeerLong("Introduce el número de línea:", 1, Long.MAX_VALUE);
                visualizaLinea(archivo.getAbsolutePath(), numLinia);
            } else if (archivo != null) {
                JOptionPane.showMessageDialog(null, "El archivo seleccionado no es válido.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } while (respuestaSN("¿Desea leer otra línea de un archivo?"));
    
        JOptionPane.showMessageDialog(null, "¡Hasta la próxima! Programa finalizado. \n\t (￣▽￣)/", "Adiós",
                JOptionPane.INFORMATION_MESSAGE);
    }
    
}