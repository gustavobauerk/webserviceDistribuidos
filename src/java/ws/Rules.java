package ws;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Rules {
    private List<Trip> trips = new ArrayList<>();
    private List<Hotel> hotels = new ArrayList<>();
    private List<String> places = new ArrayList<>();

    protected Rules() {
        places.add("Curitiba");
        places.add("Londres");
        places.add("Paris");
        places.add("Boston");
        geraTrips();
        geraHotels();
    }

    public List<Trip> searchAirfare(boolean ida, String dateIda, String dateVolta, String source,
        String destination, Integer numberOfAirfares) throws Exception {
        List<Trip> result = new ArrayList<>();
        try {
            LocalDate dateIda1 = transformInDate(dateIda);
            LocalDate dateVolta1 = transformInDate(dateVolta);
            for (Trip t : trips) {
                if (t.getDestination().equalsIgnoreCase(destination)
                    && t.getSource().equalsIgnoreCase(source) && t.getDate().equals(dateIda1)
                    && t.getNumberOfAirfares() >= numberOfAirfares) {
                    result.add(t);
                } else if (ida && t.getDestination().equalsIgnoreCase(source)
                    && t.getSource().equalsIgnoreCase(destination)
                    && t.getDate().equals(dateVolta1) && t.getNumberOfAirfares() >= numberOfAirfares) {
                    result.add(t);
                }
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return result;
    }

    public synchronized void buyAirfare(Integer id, Integer passagens) throws Exception {
        try {
            for (Trip t : trips) {
                if (t.getId().equals(id) && t.getNumberOfAirfares() >= passagens) {
                    t.setNumberOfAirfares(t.getNumberOfAirfares() - passagens);
                }
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public List<Hotel> searchHotel(String dateIda, String dateVolta,
        String destination, int quartos, int pessoas) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public synchronized void buyHotel(int id, int quartos, int pessoas) throws Exception {
        HashMap<Integer, Integer> has = new HashMap<>();
        try {
            for (Hotel h : hotels) {
                if (h.getId().equals(id)) {
                    has = h.getNumberOfRoomsFree();
                }
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    public List<Hotel> getHotels() {
        return hotels;
    }

    public void setHotels(List<Hotel> hotels) {
        this.hotels = hotels;
    }

    private void geraTrips() {
        Random rand = new Random();
        int aux, aux1;
        for (int i = 0 ; i < 25 ; i++) {
            Trip trip = new Trip();
            trip.setId(i);
            trip.setNumberOfAirfares(rand.nextInt(50) + 250);
            trip.setPrice(rand.nextInt(1000) + 200);
            aux = rand.nextInt(places.size());
            trip.setSource(places.get(aux));
            do {
                aux1 = rand.nextInt(places.size());
            } while (aux1 == aux);
            trip.setDestination(places.get(aux1));
            trip.setDate(geraDate());
            this.trips.add(trip);
        }
    }

    private void geraHotels() {
        Random rand = new Random();
        for (int i = 0 ; i < places.size() ; i++) {
            Hotel hotel = new Hotel();
            hotel.setId(i);
            hotel.setPrice(rand.nextInt(100) + 200);
            HashMap<Integer, Integer> map = new HashMap<>();
            map.put(1, 10);
            map.put(2, 10);
            map.put(3, 10);
            map.put(4, 10);
            hotel.setNumberOfRoomsTotal(map);
            hotel.setCity(places.get(i));
            hotel.setNumberOfRoomsFree(map);
            hotels.add(hotel);
        }
    }

    private static LocalDate geraDate() {
        Random rand = new Random();
        LocalDate result = LocalDate.of(2018, 12, rand.nextInt(3) + 1);
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
