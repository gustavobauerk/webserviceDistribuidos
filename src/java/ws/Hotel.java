package ws;

import java.time.LocalDate;
import java.util.HashMap;

/**
 *
 */
public class Hotel {
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
    private HashMap<LocalDate, HashMap<Integer, Integer>> entrada;
    /**
     * Data de saida do hotel, numero de quartos sendo ocupados
     */
    private HashMap<LocalDate, HashMap<Integer, Integer>> saida;

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

    public HashMap<LocalDate, HashMap<Integer, Integer>> getEntrada() {
        return entrada;
    }

    public void setEntrada(HashMap<LocalDate, HashMap<Integer, Integer>> entrada) {
        this.entrada = entrada;
    }

    public HashMap<LocalDate, HashMap<Integer, Integer>> getSaida() {
        return saida;
    }

    public void setSaida(HashMap<LocalDate, HashMap<Integer, Integer>> saida) {
        this.saida = saida;
    }

}
