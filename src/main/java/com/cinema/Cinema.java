package com.cinema;

import java.util.ArrayList;

/**
 * Clase que representa una sala de cine.
 */
public class Cinema {

    private Seat[][] seats;

    /**
     * Construye una sala de cine. Se le pasa como dato un arreglo cuyo tamaño
     * es la cantidad de filas y los enteros que tiene son el número de butacas en cada fila.
     */
    public Cinema(int[] rows) {
        seats = new Seat[rows.length][];
        initSeats(rows);
    }

    /**
     * Inicializa las butacas de la sala de cine.
     *
     * @param rows arreglo que contiene la cantidad de butacas en cada fila
     */
    private void initSeats(int[] rows) {
        for (int i = 0; i < rows.length; i++) {
            seats[i] = new Seat[rows[i]];
            for (int j = 0; j < seats[i].length; j++) {
                seats[i][j] = new Seat(i, j);
            }
        }
    }

    /**
     * Cuenta la cantidad de seats disponibles en el cine.
     */
    public int countAvailableSeats() {
        int count = 0;
        for (Seat[] row : seats) {
            for (Seat seat : row) {
                if (seat.isAvailable()) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Busca la primera butaca libre dentro de una fila o null si no encuentra.
     */
    public Seat findFirstAvailableSeatInRow(int row) {
        if (row < 0 || row >= seats.length) {
            return null;
        }
        for (Seat seat : seats[row]) {
            if (seat.isAvailable()) {
                return seat;
            }
        }
        return null;
    }

    /**
     * Busca la primera butaca libre o null si no encuentra.
     */
    public Seat findFirstAvailableSeat() {
        for (Seat[] row : seats) {
            for (Seat seat : row) {
                if (seat.isAvailable()) {
                    return seat;
                }
            }
        }
        return null;
    }

    /**
     * Busca las N butacas libres consecutivas en una fila. Si no hay, retorna null.
     *
     * @param row    fila en la que buscará las butacas.
     * @param amount el número de butacas necesarias (N).
     * @return La primer butaca de la serie de N butacas, si no hay retorna null.
     */
    public Seat getAvailableSeatsInRow(int row, int amount) {
        if (row < 0 || row >= seats.length || amount <= 0) {
            return null;
        }
        int consecutive = 0;
        for (int i = 0; i < seats[row].length; i++) {
            if (seats[row][i].isAvailable()) {
                consecutive++;
                if (consecutive == amount) {
                    return seats[row][i - amount + 1];
                }
            } else {
                consecutive = 0;
            }
        }
        return null;
    }

    /**
     * Busca en toda la sala N butacas libres consecutivas. Si las encuentra
     * retorna la primer butaca de la serie, si no retorna null.
     *
     * @param amount el número de butacas pedidas.
     */
    public Seat getAvailableSeats(int amount) {
        if (amount <= 0) {
            return null;
        }
        int consecutive = 0;
        for (Seat[] row : seats) {
            for (int i = 0; i < row.length; i++) {
                if (row[i].isAvailable()) {
                    consecutive++;
                    if (consecutive == amount) {
                        return row[i - amount + 1];
                    }
                } else {
                    consecutive = 0;
                }
            }
        }
        return null;
    }

    /**
     * Marca como ocupadas la cantidad de butacas empezando por la que se le pasa.
     *
     * @param seat   butaca inicial de la serie.
     * @param amount la cantidad de butacas a reservar.
     */
    public void takeSeats(Seat seat, int amount) {
        int row = seat.getRow();
        int seatNumber = seat.getSeatNumber();
        for (int i = 0; i < amount; i++) {
            seats[row][seatNumber + i].takeSeat();
        }
    }

    /**
     * Libera la cantidad de butacas consecutivas empezando por la que se le pasa.
     *
     * @param seat   butaca inicial de la serie.
     * @param amount la cantidad de butacas a liberar.
     */
    public void releaseSeats(Seat seat, int amount) {
        int row = seat.getRow();
        int seatNumber = seat.getSeatNumber();
        for (int i = 0; i < amount; i++) {
            seats[row][seatNumber + i].releaseSeat();
        }
    }
}
