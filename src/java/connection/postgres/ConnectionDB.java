/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection.postgres;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import ws.Hotel;
import ws.Quarto;
import ws.Trip;

public class ConnectionDB {
    /** Usada para a conexao com o banco de dados */
    private Connection con = null;
    private Statement s = null;
    /** Usada para receber o endereco da base de dados */
    private static final String ADDRESS = "jdbc:postgresql://localhost:5432/postgres";
    /** Usada para receber o nome do usuario do banco */
    private static final String USER = "postgres";
    /** Usada para receber a senha do usuario do banco */
    private static final String PW = "admin";

    public void connect() {
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(ADDRESS, USER, PW);
            s = con.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Trip selectPassagem(LocalDate dateIda, String source, String destino, int quantidade) {
        Trip list = new Trip();
        boolean aux = false;
        try {
            connect();
            String sql = "SELECT voo_id, source, destination, date, quantidade, price FROM voo WHERE"
                + " date = '" + Date.valueOf(dateIda) + "' AND "
                + " source = '" + source.toUpperCase() + "' AND destination = '" + destino.toUpperCase() + "' AND quantidade >= " + quantidade;
            ResultSet result = s.executeQuery(sql);
            disconnect();
            while (result.next()) {
                aux = true;
                Trip trip = new Trip();
                trip.setId(result.getInt(1));
                trip.setSource(result.getString(2));
                trip.setDestination(result.getString(3));
                trip.setDate(result.getDate(4).toLocalDate());
                trip.setNumberOfAirfares(result.getInt(5));
                trip.setPrice(result.getInt(6));
                list = trip;
                break;
            }
            if (!aux) {
                list = null;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public synchronized boolean buyTrip(LocalDate dateIda, String source, String destino, int quantidade) {
        boolean result = false;
        boolean aux1 = false;
        try {
            connect();
            String sql = "SELECT voo_id, source, destination, date, quantidade, price FROM voo WHERE"
                + " date = '" + Date.valueOf(dateIda) + "' AND "
                + " source = '" + source.toUpperCase() + "' AND destination = '" + destino.toUpperCase() + "' AND quantidade >= " + quantidade;
            ResultSet aux3 = s.executeQuery(sql);
            while (aux3.next()) {
                int aux = aux3.getInt(5);
                if (quantidade <= aux) {
                    String sql1 = "UPDATE voo SET quantidade = " + (aux - quantidade) + " WHERE voo_id = " + aux3.getInt(1);
                    s.executeUpdate(sql1);
                    result = true;
                    aux1 = true;
                    break;
                }
            }
            if (!aux1) {
                result = false;
            }
            disconnect();
        } catch (SQLException ex) {
            ex.printStackTrace();
            result = false;
        }
        return result;
    }

    /**
     * Retorna quartos disponiveis em um hotel em uma data selecionada
     * @param destination
     * @param dateIda
     * @param dateVolta
     * @param quartos
     * @param pessoas
     * @return
     */
    public Hotel selectHotel(String destination, LocalDate dateIda, LocalDate dateVolta,
        int quartos, int pessoas) {
        Hotel hotel = new Hotel();
        boolean aux2 = true;
        boolean result = false;
        List<Quarto> quartoList = new ArrayList<>();
        List<Quarto> quartoResult = new ArrayList<>();
        int price = 0;
        String sql1, sql2;
        try {
            //conecta ao banco
            connect();
            //monta consulta para descobrir id do hotel do destino
            String sql = "SELECT * FROM hotel WHERE hotel_name = '" + destination.toUpperCase() + "'";
            //executa query
            ResultSet resultSet = s.executeQuery(sql);
            //pega o id
            resultSet.next();
            int hotelId = resultSet.getInt(1);
            //define os dados do hotel
            hotel.setId(hotelId);
            hotel.setCity(resultSet.getString(2));
            //consulta todos os quartos do hotel disponivel para a data
            sql1 = "SELECT * FROM quarto WHERE hotel_id = " + hotelId;
            //executa query
            ResultSet result1 = s.executeQuery(sql1);
            //retorna os quartos indisponiveis durante a data especificada
            //disconecta do banco
            //cria todos os quartos encontrados
            while (result1.next()) {
                Quarto quarto = new Quarto();
                quarto.setId(result1.getInt(1));
                quarto.setTamanho(result1.getInt(2));
                quarto.setPrice(result1.getInt(4));
                quartoList.add(quarto);
            }
            sql2 = "select * from hospedagem where quarto_id in (select quarto_id from quarto where hotel_id = " + hotelId + ") and "
                + "(('" + Date.valueOf(dateIda) + "' between entrada and saida) or ('" + Date.valueOf(dateVolta)
                + "' between entrada and saida) or ((entrada between '" + Date.valueOf(dateIda) + "' "
                + "and '" + Date.valueOf(dateVolta) + "') and (saida between '" + Date.valueOf(dateIda) + "' "
                + "and '" + Date.valueOf(dateVolta) + "')))";
            ResultSet resultSet2 = s.executeQuery(sql2);
            while (resultSet2.next()) {
                Quarto quarto = new Quarto();
                quarto.setId(resultSet2.getInt(2));
                quartoResult.add(quarto);
            }
            disconnect();
            for (int i = 0 ; i < quartoList.size() ; i++) {
                Quarto quarto = quartoList.get(i);
                for (int j = 0 ; j < quartoResult.size() ; j++) {
                    Quarto quarto1 = quartoResult.get(j);
                    if (quarto.getId() == quarto1.getId()) {
                        quartoList.remove(i);
                        break;
                    }
                }
            }
            //adiciona os quartos que o usuário quer
            while ((pessoas > 0 || quartos > 0) && aux2) {
                int tamanho;
                aux2 = false;
                int aux = pessoas % quartos;
                if (aux != 0) {
                    tamanho = (pessoas / quartos) + 1;
                } else {
                    tamanho = pessoas / quartos;
                }
                if (quartos > pessoas) {
                    tamanho = 1;
                }
                for (int i = 0 ; i < quartoList.size() ; i++) {
                    Quarto q = quartoList.get(i);
                    if (q.getTamanho() == tamanho) {
                        quartoResult.add(q);
                        price += q.getPrice();
                        quartoList.remove(i);
                        aux2 = true;
                        pessoas -= tamanho;
                        quartos--;
                        break;
                    }
                }
                result = true;
            }
            if (pessoas > 0 || quartos > 0) {
                result = false;
            }
            if (!result) {
                hotel = null;
            } else {
                hotel.setPrice(price);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return hotel;
    }

    public boolean buyHotel(String destination, LocalDate dateIda, LocalDate dateVolta,
        int quartos, int pessoas) {
        boolean result = false;
        boolean aux2 = true;
        Hotel hotel = new Hotel();
        List<Quarto> quartoList = new ArrayList<>();
        List<Quarto> quartoResult = new ArrayList<>();
        int price = 0;
        String sql1, sql2;
        try {
            //conecta ao banco
            connect();
            //monta consulta para descobrir id do hotel do destino
            String sql = "SELECT * FROM hotel WHERE hotel_name = '" + destination.toUpperCase() + "'";
            //executa query
            ResultSet resultSet = s.executeQuery(sql);
            //pega o id
            resultSet.next();
            int hotelId = resultSet.getInt(1);
            //define os dados do hotel
            hotel.setId(hotelId);
            hotel.setCity(resultSet.getString(2));
            //consulta todos os quartos do hotel disponivel para a data
            sql1 = "SELECT * FROM quarto WHERE hotel_id = " + hotelId;
            //executa query
            ResultSet result1 = s.executeQuery(sql1);
            //retorna os quartos indisponiveis durante a data especificada
            while (result1.next()) {
                Quarto quarto = new Quarto();
                quarto.setId(result1.getInt(1));
                quarto.setTamanho(result1.getInt(2));
                quarto.setPrice(result1.getInt(4));
                quartoList.add(quarto);
            }
            sql2 = "select * from hospedagem where quarto_id in (select quarto_id from quarto where hotel_id = " + hotelId + ") and "
                + "(('" + Date.valueOf(dateIda) + "' between entrada and saida) or ('" + Date.valueOf(dateVolta)
                + "' between entrada and saida) or ((entrada between '" + Date.valueOf(dateIda) + "' "
                + "and '" + Date.valueOf(dateVolta) + "') and (saida between '" + Date.valueOf(dateIda) + "' "
                + "and '" + Date.valueOf(dateVolta) + "')))";
            ResultSet resultSet2 = s.executeQuery(sql2);
            while (resultSet2.next()) {
                Quarto quarto = new Quarto();
                quarto.setId(resultSet2.getInt(2));
                quartoResult.add(quarto);
            }
            //disconecta do banco
            for (int i = 0 ; i < quartoList.size() ; i++) {
                Quarto quarto = quartoList.get(i);
                for (int j = 0 ; j < quartoResult.size() ; j++) {
                    Quarto quarto1 = quartoResult.get(j);
                    if (quarto.getId() == quarto1.getId()) {
                        quartoList.remove(i);
                        break;
                    }
                }
            }
            while ((pessoas > 0 || quartos > 0) && aux2) {
                int tamanho;
                aux2 = false;
                int aux = pessoas % quartos;
                if (aux != 0) {
                    tamanho = (pessoas / quartos) + 1;
                } else {
                    tamanho = pessoas / quartos;
                }
                if (quartos > pessoas) {
                    tamanho = 1;
                }
                for (int i = 0 ; i < quartoList.size() ; i++) {
                    Quarto q = quartoList.get(i);
                    if (q.getTamanho() == tamanho) {
                        quartoResult.add(q);
                        price += q.getPrice();
                        quartoList.remove(i);
                        aux2 = true;
                        pessoas -= tamanho;
                        quartos--;
                        break;
                    }
                }
                result = true;
            }
            if (pessoas > 0 || quartos > 0) {
                result = false;
            }
            hotel.setPrice(price);
            if (result) {
                for (Quarto q : quartoResult) {
                    //insert com id do quarto dateIda e dateVolta
                    String sqlInsert = "INSERT INTO hospedagem (quarto_id, entrada, saida) VALUES ("
                        + q.getId() + ", '" + Date.valueOf(dateIda) + "', '" + Date.valueOf(dateVolta) + "')";
                    s.executeUpdate(sqlInsert);
                }
            }
            disconnect();
        } catch (SQLException ex) {
            ex.printStackTrace();
            result = false;
        }
        return result;
    }

    public void descompraPassagem(LocalDate dateIda, String source, String destino, int quantidade) {
        boolean result = false;
        boolean aux1 = false;
        try {
            connect();
            String sql = "SELECT voo_id, source, destination, date, quantidade, price FROM voo WHERE"
                + " date = '" + Date.valueOf(dateIda) + "' AND "
                + " source = '" + source.toUpperCase() + "' AND destination = '" + destino.toUpperCase() + "' AND quantidade >= " + quantidade;
            ResultSet aux3 = s.executeQuery(sql);
            while (aux3.next()) {
                int aux = aux3.getInt(5);
                if (quantidade <= aux) {
                    String sql1 = "UPDATE voo SET quantidade = " + (aux + quantidade) + " WHERE voo_id = " + aux3.getInt(1);
                    s.executeUpdate(sql1);
                    result = true;
                    aux1 = true;
                    break;
                }
            }
            if (!aux1) {
                result = false;
            }
            disconnect();
        } catch (SQLException ex) {
            ex.printStackTrace();
            result = false;
        }
    }

}
