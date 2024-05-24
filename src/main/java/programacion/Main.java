package programacion;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static int iniciarPartida() {
        int numero = (int) ((Math.random()*5000));
        System.out.println("Nueva partida. Adivina el número entre 0 y 5000");
        return numero;
    }

    public static void juego(int numero, Usuario usuario){
        Scanner sc = new Scanner(System.in);
        double puntuacion = 10;
        int nintento = 0;
        int ningresado = 0;
        while(puntuacion > 0){
            boolean repetir;
            do {
                repetir = false;
                try {
                    System.out.print("Ingrese un numero: ");
                    ningresado = sc.nextInt();
                } catch (InputMismatchException e) {
                    System.err.println("Introduzca un número válido");
                    sc.nextLine();
                    repetir = true;
                }
            }while (repetir);
            sc.nextLine();
            if(ningresado == numero){
                nintento++;
                puntuar(usuario, nintento, puntuacion);
                break;
            } else {
                nintento++;
            }
            if(nintento == 25){
                puntuacion = 0;
                System.out.println("No has logrado adivinar el número. Se acabó la partida.");
            }
        }
    }

    public static void puntuar (Usuario usuario, int nintento, double puntuacion){
        if (nintento == 1){
            System.out.println("¡Felicidades, "+usuario.getNombre()+ ", lo has adivinado! Has obtenido 10 puntos.");
            Datos.insertarPartida(new Partida(LocalDate.now(), puntuacion, nintento), usuario);
        }
        if (nintento > 1 && nintento <= 5){
            puntuacion = puntuacion - (2 * (nintento - 1));
            System.out.println("¡Felicidades, "+usuario.getNombre()+ ", lo has adivinado! Has obtenido "+puntuacion+" puntos.");
            Datos.insertarPartida(new Partida(LocalDate.now(), puntuacion, nintento), usuario);
        }
        if (nintento > 5){
            puntuacion = 2 - (0.1*(nintento-5));
            System.out.println("¡Felicidades, "+usuario.getNombre()+ ", lo has adivinado! Has obtenido "+puntuacion+" puntos.");
            Datos.insertarPartida(new Partida(LocalDate.now(), puntuacion, nintento), usuario);
        }
    }

    public static void menu() throws SQLException {
        int opcion = 1;
        Scanner sc = new Scanner(System.in);
        System.out.println("Bienvenido al sistema de partidas");
        while (opcion != 0) {
            System.out.println("Menú:\n1. Jugar\n2. Ver los récords\n0. Salir\n");
            boolean repetir;
            do {
                repetir = false;
                try {
                    System.out.print("Ingrese opción: ");
                    opcion = sc.nextInt();
                } catch (InputMismatchException e) {
                    System.err.println("Introduzca un número válido");
                    sc.nextLine();
                    repetir = true;
                }
            }while (repetir);
            sc.nextLine();
            switch (opcion) {
                case 1:
                    System.out.println("Introduzca su nombre de usuario: ");
                    String nombre = sc.nextLine();
                    Usuario usuario = Datos.obtenerUsuario("like '"+ nombre + "%'");
                    juego(iniciarPartida(), usuario);
                    break;
                case 2:
                    Datos.obtenerPartidas();
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.err.println("Opcion no válida");
                    break;
            }
        }
    }

    public static void main(String[] args) throws SQLException {
        menu();
    }
}