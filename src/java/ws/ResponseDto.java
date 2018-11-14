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
    private List<Trip> data;

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

    /**
     * Retorna a lista de dados de treinamento
     * @return a lista de dados de treinamento
     */
    public List<Trip> getData() {
        return data;
    }

    /**
     * Define a lista de dados de treinamento
     * @param data lista de dados de treinamento
     */
    public void setData(List<Trip> data) {
        this.data = data;
    }

}