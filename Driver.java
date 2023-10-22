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

                    if (!datos.equals("-") && !datos.isEmpty()) { // Identificación y separación de los datos por columna
                        switch (conteo) {
                            case 0: // ID
                                id = Integer.parseInt(datos);
                                System.out.println(id);
                                break;
                            case 1: // Nombre
                                nombre = datos;
                                System.out.println(nombre);
                                break;
                            case 2: // Cantidad disponible
                                cantidad_disponible = Integer.parseInt(datos);
                                System.out.println(cantidad_disponible);
                                break;
                            case 3: // Cantidad vendidos
                                cantidad_vendidos = Integer.parseInt(datos);
                                System.out.println(cantidad_vendidos);
                                break;
                            case 4: // Estado
                                estado = datos;
                                System.out.println(estado);
                                break;
                            case 5: // Precio
                                precio = Float.parseFloat(datos);
                                System.out.println(precio);
                                break;
                            case 6: // Categoría
                                categoria = datos;
                                System.out.println(categoria);
                                break;
                            case 7: // Mililitros
                                mililitros = Integer.parseInt(datos);
                                System.out.println(mililitros);
                                break;
                            case 8: // Tipo
                                tipo = datos;
                                System.out.println(tipo);
                                break;
                            case 9: // Gramos
                                gramos = Integer.parseInt(datos);
                                System.out.println(gramos);
                                break;
                            case 10: // Sabor
                                sabor = datos;
                                System.out.println(sabor);
                                break;
                            case 11: // Tamanio
                                tamanio = datos;
                                System.out.println(tamanio);
                                break;
                            default:
                                break;
                        }
                    }
                }

                switch (categoria.toLowerCase()) { // Crear a los productos leidos en el CSV
                    case "bebida":
                        id = productos.size();
                        productos.add(new Bebida(id, nombre, cantidad_disponible, cantidad_vendidos, estado, precio, mililitros, tipo));
                        break;
                    case "snack":
                        id = productos.size();
                        productos.add(new Snack(id, nombre, cantidad_disponible, cantidad_vendidos, estado, precio, gramos, sabor, tamanio));
                        break;
                    case "dulce":
                        id = productos.size();
                        productos.add(new Dulce(id, nombre, cantidad_disponible, cantidad_vendidos, estado, precio, sabor, tamanio));
                        break;
                }

                Comparator<Producto> comparadorId = Comparator.comparing(Producto::getId);
                Collections.sort(productos, comparadorId); // Ordenar la lista de productos por ID
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
                    System.out.println("");
                    buscarProducto(scanner, productos);
                    break;
                case 2: // Lista de productos
                    System.out.println("");
                    categoriaLista(scanner, productos);
                    break;
                case 3: // Ventas
                    for (Producto producto2 : productos) {
                        if (producto2 instanceof Bebida) {
                            System.out.println(producto2.toString());
                        }
                    }
                    System.out.println("// Lógica para la opción 3");
                    break;
                case 4: // Informe
                    System.out.println("// Lógica para la opción 4");
                    break;
                case 5: // Salir
                    salir = false;
                    System.out.println("Hasta pronto :)");
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

    public static void buscarProducto(Scanner scanner, ArrayList<Producto> productos) {
        int id = -1;
        System.out.println("");
        System.out.println("Ingrese el ID del producto que desee buscar");

        try {
            id = scanner.nextInt();
            scanner.nextLine();
            System.out.println(productos.get(id).toString());
        }

        catch (InputMismatchException e) {
            System.out.println("");
            System.out.println("Ingrese un número.");
        }

        catch (IndexOutOfBoundsException e) {
            System.out.println("");
            System.out.println("El ID ingresado no existe");
        }

        System.out.println("");
    }

    public static void categoriaLista(Scanner scanner, ArrayList<Producto> productos) {
        int opcion = 0;

        System.out.println("");
        System.out.println("Ingrese la opción que desee:");
        System.out.println("1: Todos los productos");
        System.out.println("2: Bebidas");
        System.out.println("3: Snacks");
        System.out.println("4: Dulces");
        System.out.println("");

        try { // Try para proteger el menú
            opcion = scanner.nextInt();
            scanner.nextLine();
            
        } catch (InputMismatchException e) {
            System.out.println("");
            System.out.println("Ingrese un número.");
            scanner.nextLine();
        }

        switch (opcion) {
            case 1:
                System.out.println(""); 
                System.out.println("Bebidas");
                for (Producto producto : productos) {
                    if (producto instanceof Bebida) {
                        System.out.println(producto.toString());
                    }
                }
                
                System.out.println("");
                System.out.println("Snacks");
                for (Producto producto : productos) {
                    if (producto instanceof Snack) {
                        System.out.println(producto.toString());
                    }
                }
                
                System.out.println("");
                System.out.println("Dulces");
                for (Producto producto : productos) {
                    if (producto instanceof Dulce) {
                        System.out.println(producto.toString());
                    }
                }
                break;
            case 2:
                System.out.println("");
                System.out.println("Bebidas");
                for (Producto producto : productos) {
                    if (producto instanceof Bebida) {
                        System.out.println(producto.toString());
                    }
                }
                break;
            case 3:
                System.out.println("");
                System.out.println("Snacks");
                for (Producto producto : productos) {
                    if (producto instanceof Snack) {
                        System.out.println(producto.toString());
                    }
                }
                break;
            case 4:
                System.out.println("");
                System.out.println("Dulces");
                for (Producto producto : productos) {
                    if (producto instanceof Dulce) {
                        System.out.println(producto.toString());
                    }
                }
                break;
            default:
                System.out.println("");
                System.out.println("Número inválido. Intente nuevamente.");
                break;
        }
    }
}
