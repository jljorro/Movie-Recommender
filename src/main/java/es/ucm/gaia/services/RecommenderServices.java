package es.ucm.gaia.services;

import es.ucm.gaia.cf.RecommenderApp;
import org.apache.mahout.cf.taste.common.TasteException;
import es.ucm.gaia.cf.ItemRatedBean;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Jose L. Jorro-Aragoneses
 * @version 1.0
 */
@Path("")
public class RecommenderServices {

    /*
     * Método para probar si la API funciona.
     */

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String testAPI() {
        return "Movie recommender services works correctly!";
    }

    /*
     * Métodos para añadir nuevos elementos en el recomendador
     */

    @POST @Path("/Item")
    @Produces(MediaType.TEXT_PLAIN)
    public String addItem(@QueryParam("idItem") long idItem) {
        return "Item saved correctly";
    }

    @POST @Path("/User")
    @Produces(MediaType.TEXT_PLAIN)
    public String addUser(@QueryParam("idUser") long idUser) {
        return "User saved correctly";
    }

    @POST @Path("/Rating")
    @Produces(MediaType.TEXT_PLAIN)
    public String addRating(
            @QueryParam("idUser") long idUser,
            @QueryParam("idItem") long idItem,
            @QueryParam("rating") float rating) {
        return "Rating saved correctly";
    }

    /*
     * Método para implementar el servicio de una recomendación
     */

    @GET @Path("/recommendation")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<ItemRatedBean> getRecommendation(@QueryParam("idUser") long idUser) {
        Collection<ItemRatedBean> recommendation = getRecommendationApp(idUser);
        return recommendation;
    }

    /*
     * Métodos para devolver elementos en HTML
     */

    @GET @Path("/form")
    @Produces(MediaType.TEXT_HTML)
    public String getForm() {
        String result = "<form action=\"./recommendation/html\">" +
                "<label for=\"idUser\">idUser</label>\n" +
                "<input type=\"number\" name=\"idUser\">\n" +
                "<input type=\"submit\" value=\"Submit\">" +
                "</form>";

        return result;
    }

    @GET @Path("/recommendation/html")
    @Produces(MediaType.TEXT_HTML)
    public String getRecommendationHtml(@QueryParam("idUser") long idUser) {

        Collection<ItemRatedBean> recommendation = getRecommendationApp(idUser);
        String strResult = "";

        if (recommendation != null) {

            strResult = "<table>";
            strResult += "<tr><th>idItem</th>";
            strResult += "<th>title</th>";
            strResult += "<th>genres</th>";
            strResult += "<th>rating</th>";

            strResult += recommendation.stream()
                    .map(RecommenderServices::toHTMLRow)
                    .collect(Collectors.joining());
        }

        return strResult;
    }

    /*
     * Métodos auxiliares
     */

    private Collection<ItemRatedBean> getRecommendationApp (long idUser) {

        RecommenderApp app = RecommenderApp.getInstance();
        Collection<ItemRatedBean> recommendation = null;

        try {
            app.configure();

            app.precycle();

            app.cycle(idUser, 5);

            app.postCycle();

            recommendation = app.getResult();

        } catch (TasteException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return recommendation;
    }

    private static String toHTMLRow(ItemRatedBean item) {

        String result = "<tr>";

        result += "<td>" + item.getId() + "</td>";
        result += "<td>" + item.getTitle() + "</td>";
        result += "<td>" + item.getGeners() + "</td>";
        result += "<td>" + item.getRating() + "</td>";
        result += "</tr>";

        return result;

    }
}
