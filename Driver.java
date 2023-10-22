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
        int cantidad = 0;

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
        float subtotal = 0;
        float total = 0;
        float porcentaje = 0;
        float porcentajeComision = (float) 0.2;
        Producto producto;

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
                                precio = Float.parseFloat(datos);

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
                    ventasTienda(productos);
                    if (comision == 0) {
                        System.out.println("No se ha vendido ningún dulce, por lo tanto la comisión representa un 0%.");
                    }

                    else {
                        porcentaje = comision / total;
                        System.out.println("La comisión de los dulces representa un " + porcentaje * 100 + "% de las ventas totales.");
                    }
                    break;
                case 4: // Informe
                    informe(productos, total, comision);
                    break;
                case 5: // Registrar venta
                    cantidad = preguntarCantidad(scanner);
                    producto = seleccionarProducto(scanner, productos);

                    if (producto instanceof Dulce) {
                        subtotal = venta(scanner, productos, producto.getId(), cantidad);
                        total += subtotal;
                        comision = subtotal * porcentajeComision;
                    }

                    else {
                        venta(scanner, productos, producto.getId(), cantidad);
                        total += subtotal;
                    }
                    break;
                case 6: // Salir
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
        System.out.println("5: Registrar venta");
        System.out.println("6: Salir");
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

    public static void buscarProductoVenta(Scanner scanner, ArrayList<Producto> productos) {
        int id = -1;
        int cantidad = 0;
        System.out.println("");
        System.out.println("Ingrese el ID del producto que desee buscar");

        try {
            id = scanner.nextInt();
            scanner.nextLine();

            cantidad = scanner.nextInt();
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

    public static float venta(Scanner scanner, ArrayList<Producto> productos, int id, int cantidad) {
        float subtotal = 0;
        int opcion = 0;

        if (productos.get(id).getCantidad_disponible() >= cantidad) {
            productos.get(id).setCantidad_disponible(productos.get(id).getCantidad_disponible() - cantidad);
            registrarVendidos(productos, id, cantidad);
            verificarEstado(productos, id);
            subtotal = calcularSubtotal(productos, id, cantidad);
        }

        else {
            System.out.println("La cantidad solicitada de este producto supera la disponibilidad");
            System.out.println("De este producto solo se pueden vender: " + productos.get(id).getCantidad_disponible());

            System.out.println("¿Desea comprar la máxima cantidad disponible?");
            System.out.println("1: Sí");
            System.out.println("2: No");

            try { // Try para proteger el menú
                opcion = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("");
                System.out.println("Ingrese un número.");
                scanner.nextLine();
            }

            switch (opcion) { // Opciones del menú
                case 1: // Sí
                    registrarVendidos(productos, id, productos.get(id).getCantidad_disponible());
                    subtotal = calcularSubtotal(productos, id, productos.get(id).getCantidad_disponible());
                    productos.get(id).setCantidad_disponible(0);
                    verificarEstado(productos, id);
                    break;
                case 2: // No
                    break;
                default:
                    System.out.println("");
                    System.out.println("Número inválido. Intente nuevamente.");
                    break;
            }
        }

        return subtotal;
    }

    public static void verificarEstado(ArrayList<Producto> productos, int id) {
        if (productos.get(id).getCantidad_disponible() == 0) {
            productos.get(id).setEstado("No disponible");
        }
    }

    public static void registrarVendidos(ArrayList<Producto> productos, int id, int cantidad) {
        productos.get(id).setCantidad_vendidos(productos.get(id).getCantidad_vendidos() + cantidad);
    }

    public static float calcularSubtotal(ArrayList<Producto> productos, int id, int cantidad) {
        float subtotal = productos.get(id).getPrecio() * cantidad;

        return subtotal;
    }

    public static int preguntarCantidad(Scanner scanner) {
        int cantidad = 0;
        System.out.println("");
        System.out.println("Ingrese la cantidad de productos que desea");
        System.out.println("");

        try {
            cantidad = scanner.nextInt();
            scanner.nextLine();
        }

        catch (InputMismatchException e) {
            System.out.println("");
            System.out.println("Ingrese un número.");
        }

        return cantidad;
    }

    public static Producto seleccionarProducto(Scanner scanner, ArrayList<Producto> productos) {
        int id = 0;
        Producto producto = null;
        System.out.println("");
        System.out.println("Ingrese la ID del producto");
        System.out.println("");

        try {
            id = scanner.nextInt();
            scanner.nextLine();
            producto = productos.get(id);
        }

        catch (InputMismatchException e) {
            System.out.println("");
            System.out.println("Ingrese un número.");
        }

        catch (IndexOutOfBoundsException e) {
            System.out.println("");
            System.out.println("El ID ingresado no existe");
        }

        return producto;
    }

    public static void ventasTienda(ArrayList<Producto> productos) {
        System.out.println("");
        for (Producto producto : productos) {
            System.out.println(producto.getId() + ": " + producto.getNombre() + " - Cantidad vendida: " + producto.getCantidad_vendidos());
        }
        System.out.println("");
    }

    public static void informe(ArrayList<Producto> productos, float total, float comision) { 
        int totalProductosBebidas = 0;
        int totalProductosSnacks = 0;
        int totalProductosDulces = 0;

        for (Producto producto : productos) {
            if (producto instanceof Bebida) {
                totalProductosBebidas += producto.getCantidad_disponible() + producto.getCantidad_vendidos();
            }
        }
        
        for (Producto producto : productos) {
            if (producto instanceof Snack) {
                totalProductosSnacks += producto.getCantidad_disponible() + producto.getCantidad_vendidos();
            }
        }
        
        for (Producto producto : productos) {
            if (producto instanceof Dulce) {
                totalProductosDulces += producto.getCantidad_disponible() + producto.getCantidad_vendidos();
            }
        }
        
        System.out.println("");
        System.out.println("******************************************");
        System.out.println("");
        System.out.println("Listado de categorías con el total de productos");
        System.out.println("");
        System.out.println("Bebidas - " + totalProductosBebidas);
        System.out.println("Snacks - " + totalProductosSnacks);
        System.out.println("Dulces - " + totalProductosDulces);

        
        System.out.println("");
        System.out.println("******************************************");
        System.out.println("");
        System.out.println("Listado de productos por categoría"); 

        System.out.println("");
        System.out.println("Bebidas");
        System.out.println("");
        for (Producto producto : productos) {
            if (producto instanceof Bebida) {
                System.out.println(producto.getNombre());
            }
        }
        
        System.out.println("");
        System.out.println("");
        System.out.println("Snacks");
        System.out.println("");
        for (Producto producto : productos) {
            if (producto instanceof Snack) {
                System.out.println(producto.getNombre());
            }
        }
        
        System.out.println("");
        System.out.println("******************************************");
        System.out.println("");
        System.out.println("Dulces");
        System.out.println("");
        for (Producto producto : productos) {
            if (producto instanceof Dulce) {
                System.out.println(producto.getNombre());
            }
        }

        System.out.println("");
        System.out.println("");
        System.out.println("Total de ventas");
        System.out.println("");
        System.out.println("Ventas totales: Q" + total);
        System.out.println("Comisión por ventas de Dulces: Q" + comision);
    }
}
