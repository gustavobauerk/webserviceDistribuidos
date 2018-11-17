package ws;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
    @Path("/{ida}/{dateIda}/{dateVolta}/{origem}/{destino}/{numberOfAirfares}")
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
    @Path("/{id}/{numberOfAirfares}")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response buyTrips(@PathParam("id") Integer id, @PathParam("numberOfAirfares") Integer numberOfAirfares) {
        ResponseDto response = new ResponseDto();
        try {
            rules.buyAirfare(id, numberOfAirfares);
            response.setStatus(Response.Status.OK.getStatusCode());
        } catch (Exception ex) {
            response.setStatus(Response.Status.NOT_FOUND.getStatusCode());
        }
        return Response.ok(response).build();
    }

}
