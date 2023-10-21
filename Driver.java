/*
Universidad del Valle de Guatemala
Angel Esteban Esquit Hernández - 23221
Laboratorio 3
*/

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Driver {
    
    public static void main(String[] args) {
        //Creación de variables e instancias para el funcionamiento del programa principal
        Scanner scanner = new Scanner(System.in);
        ArrayList<Producto> productos = new ArrayList<>();
        boolean salir = true;
        int opcion = 0;
        int conteo = -1;

        //Creación de variables para guardar los atributos de los productos
        int id = 0;
        String nombre = "";
        int cantidad_disponible = 0;
        int cantidad_vendidos = 0;
        String estado = "";
        float precio = 0;
        int mililitros = 0;
        String tipo = "";
        int gramos = 0;
        String sabor = "";
        String tamanio = "";
        String categoria = "";
        float comision = 0;
        float porcentaje = 0;
        

        // Variables para guardar los datos en CSV
        Producto producto;
        Bebida bebida;
        Snack snack;
        Dulce Dulce;

        // Variable para saltar la primera fila de encabezados
        boolean primera_fila = true;

        String csvFilePath = "Productos.csv"; // Establecer el archivo CSV
        try (BufferedReader csvReader = new BufferedReader(new FileReader(csvFilePath))) { // Lector de CSV
            String line;
            while ((line = csvReader.readLine()) != null) {
                if (primera_fila) { // Saltar la primera fila de encabezados
                    primera_fila = false;
                    continue;
                }

                String[] data = line.split(";");
                for (String datos : data) {
                    conteo += 1;

                    if (!datos.equals("-") || datos.equals("0")) { // Identificación y separación de los datos por columna
                        switch (conteo) {
                            case 0: // ID
                                id = Integer.parseInt(datos);
                                break;
                            case 1: // Nombre
                                nombre = datos;
                                break;
                            case 2: // Cantidad disponible
                                cantidad_disponible = Integer.parseInt(datos);
                                break;
                            case 3: // Cantidad vendidos
                                cantidad_vendidos = Integer.parseInt(datos);
                                break;
                            case 4: // Estado
                                estado = datos;
                                break;
                            case 5: // Precio
                                precio = Integer.parseInt(datos);
                                break;
                            case 6: // Categoría
                                categoria = datos;
                                break;
                            case 7: // Mililitros
                                mililitros = Integer.parseInt(datos);
                                break;
                            case 8: // Tipo
                                tipo = datos;
                                break;
                            case 9: // Gramos
                                gramos = Integer.parseInt(datos);
                                break;
                            case 10: // Sabor
                                sabor = datos;
                                break;
                            case 11: // Tamanio
                                tamanio = datos;
                                break;
                            default:
                                break;
                        }
                    }
                }

                switch (categoria.toLowerCase()) { // Crear a los productos leidos en el CSV
                    case "Bebida":
                        productos.add(new Bebida(id, nombre, cantidad_disponible, cantidad_vendidos, estado, precio, mililitros, tipo));
                        break;
                    case "Snack":
                        productos.add(new Snack(id, nombre, cantidad_disponible, cantidad_vendidos, estado, precio, gramos, sabor, tamanio));
                        break;
                    case "Dulce":
                        productos.add(new Dulce(id, nombre, cantidad_disponible, cantidad_vendidos, estado, precio, sabor, tamanio));
                        break;
                }

                Comparator<Producto> comparadorEfectividad = Comparator.comparing(Producto::getId);
                Collections.sort(productos, Collections.reverseOrder(comparadorEfectividad)); // Ordenar la lista de productos por ID
                System.out.println(); // Salto de línea para cada fila
                conteo = -1;
            }
        } catch (IOException e) { // Catch para errores al leer el CSV
            System.err.println("Error al leer el archivo CSV: " + e.getMessage());
        }

        while (salir) { // Ciclo principal del programa
            printMenu();

            try { // Try para proteger el menú
                opcion = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("");
                System.out.println("Ingrese un número.");
                scanner.nextLine();
            }

            switch (opcion) { // Opciones del menú
                case 1: // Buscar un producto
                    break;
                case 2: // Lista de productos
                    break;
                case 3: // Ventas
                    System.out.println("// Lógica para la opción 3");
                    break;
                case 4: // Informe
                    System.out.println("// Lógica para la opción 4");
                    break;
                case 5:
                    salir = false;
                    System.out.println("// Salir del bucle");
                    break;
                case 0:
                    continue;
                default:
                    System.out.println("");
                    System.out.println("Número inválido. Intente nuevamente.");
                    break;
            }

            opcion = 0;
        }
    }


    public static void printMenu() {
        System.out.println("");
        System.out.println("**************************************");
        System.out.println("                Menú");
        System.out.println("**************************************");
        System.out.println("Ingrese la opción que desee:");
        System.out.println("1: Buscar un producto");
        System.out.println("2: Lista de productos");
        System.out.println("3: Ventas");
        System.out.println("4: Ver informe");
        System.out.println("5: Salir");
        System.out.println("");
    }
}
