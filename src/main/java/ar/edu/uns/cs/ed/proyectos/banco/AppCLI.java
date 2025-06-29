package ar.edu.uns.cs.ed.proyectos.banco;

import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;

import ar.edu.uns.cs.ed.proyectos.banco.entities.*;
import ar.edu.uns.cs.ed.proyectos.banco.util.Par;

public class AppCLI {

    private static Set<Puesto> puestos = new HashSet<>();
    private static SucursalBancaria sb = new SucursalBancaria();

    public static void main(String[] args) {
        System.out.println("Proyecto: Sistema Bancario - Estructuras de Datos - 1ºC 2025 (0.1.0beta)");
        System.out.println();

        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            mostrarMenu();
            System.out.print("Elegí una opción: ");
            String opcion = scanner.nextLine().trim();

            switch (opcion.toUpperCase()) {
                case "1":
                    asociarTramite(scanner);
                    break;
                case "2":
                    desasociarTramite(scanner);
                    break;
                case "3":
                    sacarTurno(scanner);
                    break;
                case "4":
                    llamar(scanner);
                    break;
                case "E":
                    configurarSucursalDeTests();
                    break;
                case "X":
                    System.out.println("¡Hasta luego!");
                    salir = true;
                    break;
                default:
                    System.out.println("Opción inválida. Intentá de nuevo.");
            }

            System.out.println(); // Separador visual entre interacciones
        }

        scanner.close();
    }

    private static void mostrarMenu() {
        System.out.println("========== Menú Principal ==========");
        System.out.println("1. Asociar trámite a puesto");
        System.out.println("2. Desasociar trámite de puesto");
        System.out.println("E. Configurar sucursal del tester");
        System.out.println("------------------------------------");
        System.out.println("3. Sacar turno");
        System.out.println("4. Llamar/Atender");
        System.out.println("------------------------------------");
        System.out.println("X. Salir");
    }

    private static String tramitesDePuestoToString(Puesto p) {
        StringBuilder strb = new StringBuilder();
        boolean sep = false;
        for(Tramite t: sb.obtenerTramitesAsociadosAPuesto(p)) {
            if (!sep) 
                sep = true; 
            else 
                strb.append(", ");
            strb.append(t);
        }
        return strb.toString();
    }



    private static void mostrarPuestos() {
        System.out.println("======== Esquema de atención =================");
        for(Puesto p:puestos) {
            System.out.println(p + ": "+ tramitesDePuestoToString(p));
        }
        System.out.println("==============================================");
    }

    private static void mostrarPantalla() {
        System.out.println("+============= TURNOS =============+");
        int cdor = 0;
        for(Par<Turno,Puesto> p: sb.obtenerUltimos4Llamados()) {
            System.out.println(String.format("%-34s",("| "+String.format("%5s",p.getPrimero())+" --> "+p.getSegundo())).substring(0, 34)+" |");
            cdor++;
        }
        for (int i=cdor; i<4; i++) {
            System.out.println("|                                  |");
        }
        System.out.println("+==================================+");
    }


    private static Tramite seleccionarTramite(Scanner scanner) {
        boolean salir = false;
        Tramite tr = null;

        while (!salir) {
            System.out.println(java.util.Arrays.asList(Tramite.values()));
            System.out.print("Seleccioná un trámite (V = volver): ");
            String opcion = scanner.nextLine().trim();

            switch (opcion.toUpperCase()) {
                case "C":
                    tr = Tramite.CAJA;
                    salir = true;
                    break;
                case "A":
                    tr = Tramite.CLIENTE;
                    salir = true;
                    break;
                case "B":
                    tr = Tramite.COMERCIAL;
                    salir = true;
                    break;
                case "X":
                    tr = Tramite.COMEX;
                    salir = true;
                    break;
                case "T":
                    tr = Tramite.TARJETAS;
                    salir = true;
                    break;
                case "O":
                    tr = Tramite.OTROS;
                    salir = true;
                    break;
                case "V":
                    salir = true;
                    break;
                default:
                    System.out.println("Opción inválida. Intentá de nuevo.");
            }

            System.out.println(); // Separador visual entre interacciones
        }

        return tr;
    }


    private static void asociarTramite(Scanner scanner) {
        mostrarPuestos();
        Tramite tr = seleccionarTramite(scanner);
        if (tr!=null) {
            System.out.print("Ingresá el puesto: ");
            String nombre = scanner.nextLine();
            Puesto p = new Puesto(nombre);
            if (!puestos.contains(p)) puestos.add(p);

            boolean res = sb.asociarTramiteAPuesto(tr, p);
            if (res)
                System.out.println("Asociación exitosa!");
            else
                System.out.println("Error al asociar el trámite!");
        }
        System.out.println();
        mostrarPuestos();
    }


    private static void desasociarTramite(Scanner scanner) {
        mostrarPuestos();
        Tramite tr = seleccionarTramite(scanner);
        if (tr!=null) {
            System.out.print("Ingresá el puesto: ");
            String nombre = scanner.nextLine();
            Puesto p = new Puesto(nombre);
            if (!puestos.contains(p)) {
                System.out.print("Error: el puesto no existe!");
                return;
            }

            boolean res = sb.desasociarTramiteAPuesto(tr, p);
            if (res)
                System.out.println("Desasociación exitosa!");
            else
                System.out.println("Error al desasociar el trámite!");

            if (!sb.obtenerTramitesAsociadosAPuesto(p).iterator().hasNext()) {
                puestos.remove(p);
            }
        }
        System.out.println();
        mostrarPuestos();
    }

    private static void sacarTurno(Scanner scanner) {
        Tramite tr = null;
        do {
            tr = seleccionarTramite(scanner);
            if (tr!=null && sb.obtenerCantidadDePuestosAtendiendoElTramite(tr)<=0) {
                System.out.println("No hay puestos disponibles para el trámite - seleccione trámite nuevamente...");
            }
        } while ((tr!=null) && (sb.obtenerCantidadDePuestosAtendiendoElTramite(tr)<=0));

        if (tr!=null) {
            System.out.print("Ingresá el nombre: ");
            String nombre = scanner.nextLine();
            boolean esCliente = seleccionarSiEsCliente(scanner);

            if (!esCliente && tr!=Tramite.CAJA && tr!=Tramite.COMERCIAL) {
                System.out.println("Error: Una persona no cliente no puede solicitar el trámite elegido.");
            }

            Turno t = sb.sacarTurno(new Persona(nombre, esCliente), tr);
            if (t!=null) {
                System.out.println(nombre+" obtuvo el turno "+t+". Tiempo estimado de espera: "+sb.obtenerTiempoDeEsperaEstimado(t)+" minuto(s).");
            }
        }
        System.out.println();
    }

    private static boolean seleccionarSiEsCliente(Scanner scanner) {
        boolean salir = false;
        boolean esCliente = false;

        while (!salir) {
            System.out.print("¿Es cliente? (S/N): ");
            String opcion = scanner.nextLine().trim();

            switch (opcion.toUpperCase()) {
                case "S":
                    esCliente = true;
                    salir = true;
                    break;
                case "N":
                    esCliente = false;
                    salir = true;
                    break;
                default:
                    System.out.println("Opción inválida. Intentá de nuevo.");
            }

            System.out.println(); // Separador visual entre interacciones
        }

        return esCliente;
    }


    private static void llamar(Scanner scanner) {
        mostrarPuestos();
        System.out.print("Ingresá el puesto: ");
        String nombre = scanner.nextLine();
        Puesto p = new Puesto(nombre);
        Par<Turno, Integer> res = sb.llamarYAtenderProximoTurno(p);
        if (res == null) {
            System.out.println("No hay personas esperando para los trámites del puesto "+p+" ("+tramitesDePuestoToString(p)+").");
        } else {
            System.out.println("Se llamó al turno "+res.getPrimero()+" de "+res.getPrimero().getPoseedor()+" en el puesto "+p+".");
            System.out.println("La atención demoró "+res.getSegundo()+" minuto(s).");
        }
        System.out.println();
        mostrarPantalla();
    }


    private static void configurarSucursalDeTests() {

        System.out.println("Configurando la sucursal bancaria de testing...");

        //Asociación de puestos y trámites - cajas
        for (int i = 1; i<=3; i++) {
            Puesto c = new Puesto("caja"+i);
            puestos.add(c);
            sb.asociarTramiteAPuesto(Tramite.CAJA, c);
        }

        //Asociación de puestos y trámites - atención general
        Puesto p1 = new Puesto("puesto1");
        puestos.add(p1);
        sb.asociarTramiteAPuesto(Tramite.CLIENTE, p1);
        sb.asociarTramiteAPuesto(Tramite.TARJETAS, p1);

        Puesto p2 = new Puesto("puesto2");
        puestos.add(p2);
        sb.asociarTramiteAPuesto(Tramite.COMERCIAL, p2);
        sb.asociarTramiteAPuesto(Tramite.COMEX, p2);
        sb.asociarTramiteAPuesto(Tramite.TARJETAS, p2);

        Puesto p3 = new Puesto("puesto3");
        puestos.add(p3);
        sb.asociarTramiteAPuesto(Tramite.CLIENTE, p3);
        sb.asociarTramiteAPuesto(Tramite.COMERCIAL, p3);

        Puesto p4 = new Puesto("puesto4");
        puestos.add(p4);
        sb.asociarTramiteAPuesto(Tramite.CLIENTE, p4);
        sb.asociarTramiteAPuesto(Tramite.COMEX, p4);
        sb.asociarTramiteAPuesto(Tramite.OTROS, p4);

        System.out.println("Configuración completa!");
        mostrarPuestos();
    }
}