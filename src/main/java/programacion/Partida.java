package programacion;

import java.time.LocalDate;

public class Partida {
    private LocalDate fecha_partida;
    private double puntuacion;
    private int nintento;

    public Partida(LocalDate fecha_partida, double puntuacion, int nintento) {
        this.fecha_partida = fecha_partida;
        this.puntuacion = puntuacion;
        this.nintento = nintento;
    }

    public LocalDate getFecha_partida() {
        return fecha_partida;
    }

    public void setFecha_partida(LocalDate fecha_partida) {
        this.fecha_partida = fecha_partida;
    }

    public double getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(double puntuacion) {
        this.puntuacion = puntuacion;
    }

    public int getNintento() {
        return nintento;
    }

    public void setNintento(int nintento) {
        this.nintento = nintento;
    }
}
