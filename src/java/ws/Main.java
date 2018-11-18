package ws;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * REST Web Service
 *
 * @author Gustavo
 */
@Path("ws")
public class Main {
    Rules rules;
    @Context
    private UriInfo context;

    /** Creates a new instance of Teste */
    public Main() {
        rules = new Rules();
    }

    /**
     * Retrieves representation of an instance of ws.Teste
     * @param ida
     * @param dateIda
     * @param dateVolta
     * @param origem
     * @param destino
     * @param numberOfAirfares
     * @return
     */
    @GET
    @Path("/getTrips/{ida}/{dateIda}/{dateVolta}/{origem}/{destino}/{numberOfAirfares}")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response getTrips(@PathParam("ida") boolean ida, @PathParam("dateIda") String dateIda,
        @PathParam("dateVolta") String dateVolta, @PathParam("origem") String origem,
        @PathParam("destino") String destino, @PathParam("numberOfAirfares") Integer numberOfAirfares) {
        ResponseDto response = new ResponseDto();
        try {
            response.setTrip(rules.searchAirfare(ida, dateIda, dateVolta, origem, destino, numberOfAirfares));
            response.setStatus(Response.Status.OK.getStatusCode());
        } catch (Exception ex) {
            response.setStatus(Response.Status.NOT_FOUND.getStatusCode());
        }
        return Response.ok(response).build();
    }

    @POST
    @Path("/buyTrips/{ida}/{dateIda}/{dateVolta}/{origem}/{destino}/{numberOfAirfares}")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response buyTrips(@PathParam("ida") boolean ida, @PathParam("dateIda") String dateIda,
        @PathParam("dateVolta") String dateVolta, @PathParam("origem") String origem,
        @PathParam("destino") String destino, @PathParam("numberOfAirfares") Integer numberOfAirfares) {
        ResponseDto response = new ResponseDto();
        try {
            rules.buyAirfare(ida, dateIda, dateVolta, origem, destino, numberOfAirfares);
            response.setStatus(Response.Status.OK.getStatusCode());
        } catch (Exception ex) {
            response.setStatus(Response.Status.NOT_FOUND.getStatusCode());
        }
        return Response.ok(response).build();
    }

    @GET
    @Path("/getHotel/{place}/{entrada}/{saida}/{quartos}/{pessoas}")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response searchHotel(@PathParam("place") String hotel, @PathParam("entrada") String dateEntrada, @PathParam("saida") String dateVolta,
        @PathParam("quartos") int quartos, @PathParam("pessoas") int pessoas) {
        ResponseDto response = new ResponseDto();
        try {
            response.setHotel(rules.searchHotel(hotel, dateEntrada, dateVolta, quartos, pessoas));
            response.setStatus(Response.Status.OK.getStatusCode());
        } catch (Exception ex) {
            response.setStatus(Response.Status.NOT_FOUND.getStatusCode());
        }
        return Response.ok(response).build();
    }

    @POST
    @Path("/buyHotel/{place}/{entrada}/{saida}/{quartos}/{pessoas}")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response buyHotel(@PathParam("place") String hotel, @PathParam("entrada") String dateEntrada, @PathParam("saida") String dateVolta,
        @PathParam("quartos") int quartos, @PathParam("pessoas") int pessoas) {
        ResponseDto response = new ResponseDto();
        try {
            rules.buyHotel(hotel, dateEntrada, dateVolta, quartos, pessoas);
            response.setStatus(Response.Status.OK.getStatusCode());
        } catch (Exception ex) {
            response.setStatus(Response.Status.NOT_FOUND.getStatusCode());
        }
        return Response.ok(response).build();
    }

    @GET
    @Path("/searchPackage/{ida}/{entrada}/{saida}/{source}/{destino}/{pessoas}/{quartos}")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response searchPackage(@PathParam("ida") boolean ida, @PathParam("entrada") String entrada,
        @PathParam("saida") String saida, @PathParam("source") String source,
        @PathParam("destino") String destino, @PathParam("pessoas") Integer pessoas,
        @PathParam("quartos") Integer quartos) {
        ResponseDto response = new ResponseDto();
        List<Trip> trip = new ArrayList<>();
        Hotel hotel = new Hotel();
        try {
            trip = rules.searchAirfare(ida, entrada, saida, source, destino, pessoas);
            hotel = rules.searchHotel(destino, entrada, saida, quartos, pessoas);
            if (hotel != null && trip != null) {
                response.setTrip(trip);
                response.setHotel(hotel);
            }
            response.setStatus(Response.Status.OK.getStatusCode());
        } catch (Exception ex) {
            response.setStatus(Response.Status.NOT_FOUND.getStatusCode());
        }
        return Response.ok(response).build();
    }

    @PUT
    @Path("/buyPackage/{ida}/{entrada}/{saida}/{source}/{destino}/{pessoas}/{quartos}")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response buyPackage(@PathParam("ida") boolean ida, @PathParam("id_trip") int idTrip, @PathParam("entrada") String entrada,
        @PathParam("saida") String saida, @PathParam("source") String source,
        @PathParam("destino") String destino, @PathParam("pessoas") Integer pessoas,
        @PathParam("quartos") Integer quartos) {
        ResponseDto response = new ResponseDto();
        try {
            rules.buyAirfare(ida, entrada, saida, source, destino, pessoas);
            rules.buyHotel(destino, entrada, saida, quartos, pessoas);
            response.setStatus(Response.Status.OK.getStatusCode());
        } catch (Exception ex) {
            response.setStatus(Response.Status.NOT_FOUND.getStatusCode());
        }
        return Response.ok(response).build();
    }

}
