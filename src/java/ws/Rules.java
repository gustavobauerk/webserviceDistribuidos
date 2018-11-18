package ws;

import connection.postgres.ConnectionDB;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Rules {
    public List<Trip> searchAirfare(boolean ida, String dateIda, String dateVolta, String source,
        String destination, Integer numberOfAirfares) throws Exception {
        List<Trip> result = new ArrayList<>();
        try {
            ConnectionDB c = new ConnectionDB();
            Trip aux = c.selectPassagem(transformInDate(dateIda), source, destination, numberOfAirfares);
            if (aux != null) {
                result.add(aux);
            }
            if (ida) {
                Trip trip = c.selectPassagem(transformInDate(dateVolta), destination, source, numberOfAirfares);
                if (trip != null) {
                    result.add(trip);
                }
                if (result.size() < 2) {
                    result = null;
                }
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return result;
    }

    public synchronized boolean buyAirfare(boolean ida, String dateIda, String dateVolta, String source,
        String destination, Integer numberOfAirfares) throws Exception {
        boolean result, result1 = false;
        try {
            ConnectionDB c = new ConnectionDB();
            result = c.buyTrip(transformInDate(dateIda), source, destination, numberOfAirfares);
            if (ida && result) {
                result1 = c.buyTrip(transformInDate(dateVolta), destination, source, numberOfAirfares);
            }
            if (!result1) {
                c.descompraPassagem(transformInDate(dateVolta), destination, source, numberOfAirfares);
                result = false;
            }
        } catch (Exception ex) {
            result = false;
            throw new Exception(ex.getMessage());
        }
        return result;
    }

    public Hotel searchHotel(String destination, String dateIda, String dateVolta,
        int quartos, int pessoas) {
        Hotel result = new Hotel();
        try {
            ConnectionDB c = new ConnectionDB();
            result = c.selectHotel(destination, transformInDate(dateIda), transformInDate(dateVolta), quartos, pessoas);
        } catch (Exception ex) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        return result;
    }

    public synchronized boolean buyHotel(String destination, String dateIda, String dateVolta,
        int quartos, int pessoas) throws Exception {
        boolean result;
        try {
            ConnectionDB c = new ConnectionDB();
            result = c.buyHotel(destination, transformInDate(dateIda), transformInDate(dateVolta), quartos, pessoas);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return result;
    }

    private static LocalDate transformInDate(String date) {
        LocalDate result = null;
        try {
            if (null != date) {
                result = LocalDate.parse(date);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

}
