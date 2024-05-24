package programacion;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Datos {
    private static final String url = "jdbc:mysql://192.168.80.186:3306/juego?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC"; //PARA DEFENSA USAR IP MÁQUINA (192.168.80.186)
    private static final String user = "gamemaster"; //gamemaster se conecta desde 192.168.80.24
    private static final String pass = "GameMaster1!";
    private static Connection conex = conector();
    public static Statement st;

    static {
        try {
            assert conex != null;
            st = conex.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Connection conector() {
        try {
            return DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            System.out.println("Error en la conexión." + e.getMessage());
            return null;
        }
    }

    public static Usuario obtenerUsuario(String param) throws SQLException {
        Scanner sc = new Scanner(System.in);
        Usuario emp = new Usuario();
        String consulta1 = "select * from usuario where nombre_completo " + param + ";";
        ResultSet rs = st.executeQuery(consulta1);
        int opcion = 1;
        boolean encontrado = false;
        while(rs.next()) {
            emp.setDNI(rs.getString(1));
            emp.setNombre(rs.getString(2));
            System.out.println("¿Es este el usuario buscado? " + emp.toString());
            boolean repetir;
            do {
                repetir = false;
                try {
                    System.out.print("0. Sí\n1. No ");
                    opcion = sc.nextInt();
                } catch (InputMismatchException e) {
                    System.err.println("Introduzca un número válido");
                    sc.nextLine();
                    repetir = true;
                }
            }while (repetir);

            if (opcion == 0){
                encontrado = true;
                break;
            } else {
                encontrado = false;
            }
        }
        if (!encontrado){
            System.out.println("Usuario no encontrado. Regístrese con sus datos.");
            emp = registrarUsuario();
            insertarUsuario(emp);
        }
        return emp;
    }

    public static void insertarUsuario(Usuario emp) {
        String sqlStr = "INSERT INTO usuario VALUES("+"'"+emp.getDNI()+"'"+","+"'"+ emp.getNombre()+"'"+");";
        try {
            st.executeUpdate(sqlStr);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void insertarPartida(Partida part, Usuario emp) {
        String sqlStr = "INSERT INTO partida VALUES("+"'"+emp.getDNI()+"'"+","+"'"+ part.getFecha_partida()+"'"+","+"'"+ part.getPuntuacion()+"'"+","+"'"+ part.getNintento()+"'"+");";
        try {
            st.executeUpdate(sqlStr);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Usuario registrarUsuario() {
        Usuario emp = new Usuario();
        Scanner sc = new Scanner(System.in);
        System.out.print("Ingrese el nombre: ");
        emp.setNombre(sc.nextLine());
        System.out.print("Ingrese el DNI: ");
        emp.setDNI(sc.nextLine());
        return emp;
    }

    public static void obtenerPartidas() throws SQLException {
        String consulta1 = "select distinct(dni), fecha_partida, puntuacion, nintento from partida order by puntuacion desc;";
        ArrayList<Partida> partidas = new ArrayList<>();
        ResultSet rs = st.executeQuery(consulta1);
        boolean encontrado = false;
        System.out.println("DNI       | Fecha partida | Puntuación | Núm. intentos");
        int contador = 0;
        while(rs.next() && contador <=10) {
            //Partida partida = new Partida(LocalDate.parse(rs.getString(2)), rs.getDouble(3), rs.getInt(4));
            contador++;
            System.out.println(rs.getString(1) + "   " + rs.getString(2) + "         " + rs.getString(3) + "         " + rs.getString(4) );
        }
    }
}
