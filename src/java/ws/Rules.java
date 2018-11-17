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
            result = c.selectPassagem(transformInDate(dateIda), source, destination, numberOfAirfares);
            if (ida) {
                result.addAll(c.selectPassagem(transformInDate(dateVolta), destination, source, numberOfAirfares));
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return result;
    }

    public synchronized boolean buyAirfare(Integer id, Integer passagens) throws Exception {
        boolean result;
        try {
            ConnectionDB c = new ConnectionDB();
            result = c.buyTrip(id, passagens);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return result;
    }

    public List<Hotel> searchHotel(String dateIda, String dateVolta,
        String destination, int quartos, int pessoas) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public synchronized void buyHotel(int id, int quartos, int pessoas) throws Exception {
        try {
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
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
