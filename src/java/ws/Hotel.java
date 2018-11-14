package ws;

import java.util.HashMap;

/**
 *
 */
public class Hotel {
    private Integer id;
    /**
     * Cidade do hotel
     */
    private String city;
    /**
     * Tamanho do quarto, numero de quartos daquele tamanho
     */
    private HashMap<Integer, Integer> numberOfRoomsTotal;
    /**
     * Preço da diaria no hotel
     */
    private Integer price;
    /**
     * Data de entrada no hotel, numero de quartos sendo ocupados
     */
    private HashMap<Integer, Integer> numberOfRoomsFree;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public HashMap<Integer, Integer> getNumberOfRoomsTotal() {
        return numberOfRoomsTotal;
    }

    public void setNumberOfRoomsTotal(HashMap<Integer, Integer> numberOfRoomsTotal) {
        this.numberOfRoomsTotal = numberOfRoomsTotal;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public HashMap<Integer, Integer> getNumberOfRoomsFree() {
        return numberOfRoomsFree;
    }

    public void setNumberOfRoomsFree(HashMap<Integer, Integer> numberOfRoomsFree) {
        this.numberOfRoomsFree = numberOfRoomsFree;
    }

}
