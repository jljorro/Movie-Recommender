package es.ucm.gaia.cf;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jose L. Jorro-Aragoneses
 * @version 1.0
 */
public class RecommenderApp {

    private static RecommenderApp instance = null;

    private RecommenderApp() {}

    public static RecommenderApp getInstance() {
        if(instance == null)
            instance = new RecommenderApp();

        return instance;
    }

    private MovieConnector connector;
    private Recommender recommender;
    private Collection<ItemRatedBean> result;

    public void configure() {
        connector = MovieConnector.getInstance();
    }

    public void precycle() throws TasteException, IOException {

        // Cargamos el conector
        connector.init("movies.csv", "ratings.csv");

        recommender = new GenericUserBasedRecommender(
                connector.getDataModel(),
                new NearestNUserNeighborhood(5, new PearsonCorrelationSimilarity(connector.getDataModel()), connector.getDataModel()),
                new PearsonCorrelationSimilarity(connector.getDataModel()));

    }

    public void cycle(long idUser, int numRecommendations) throws TasteException {
        List<RecommendedItem> recommendedItems = recommender.recommend(idUser, numRecommendations);

        result = recommendedItems.stream()
                .map(r -> new ItemRatedBean(connector.getItemById(r.getItemID()), r.getValue()))
                .collect(Collectors.toList());

    }

    public void postCycle() {}

    public Collection<ItemRatedBean> getResult() {
        return result;
    }

}
