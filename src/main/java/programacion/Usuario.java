package programacion;

public class Usuario {
    private String nombre;
    private String dni;

    public Usuario() {
    }

    public Usuario(String nombre, String DNI) {
        this.nombre = nombre;
        this.dni = DNI;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDNI() {
        return dni;
    }

    public void setDNI(String dni) {
        this.dni = dni;
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre + ", DNI: " + dni;
    }
}
