/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import java.io.Serializable;
import java.util.List;

/**
 * Dto de resposta
 */
public class ResponseDto implements Serializable {
    //Código https de resposta
    private Integer status;
    //Lista de dados
    private List<Trip> trip;

    /**
     * Construtor vazio
     */
    public ResponseDto() {
        //Construtor padrão.
    }

    /**
     * Retorna o código de resposta
     * @return o código de resposta
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * Define o código de resposta
     * @param status código de resposta
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Trip> getTrip() {
        return trip;
    }

    public void setTrip(List<Trip> trip) {
        this.trip = trip;
    }

}
