/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import java.time.LocalDate;

/**
 * Classe que simboliza a passagem aérea
 */
public class Trip extends Object {
    private Integer id;
    /**
     * Origem da viagem
     */
    private String source;
    /**
     * Destino da viagem
     */
    private String destination;
    /**
     * Data da viagem
     */
    private LocalDate date;
    /**
     * Numero de passagens disponiveis
     */
    private Integer numberOfAirfares;
    private Integer price;

    /**
     * Retorna
     * @return
     */
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Integer getNumberOfAirfares() {
        return numberOfAirfares;
    }

    public void setNumberOfAirfares(Integer numberOfAirfares) {
        this.numberOfAirfares = numberOfAirfares;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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

}
