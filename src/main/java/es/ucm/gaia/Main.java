package es.ucm.gaia;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.Preference;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/**
 * @author Jose L. Jorro-Aragoneses
 * @version 1.0
 */
public class Main {

    public static void main(String[] args) {

        try {

            // Configuramos el modelo de datos
            File f = new File(Main.class.getClassLoader().getResource("ratings.csv").getFile());
            DataModel dataModel = new FileDataModel(f);

            UserBasedRecommender rec = new GenericUserBasedRecommender(
                    dataModel,
                    new NearestNUserNeighborhood(5, new PearsonCorrelationSimilarity(dataModel), dataModel),
                    new PearsonCorrelationSimilarity(dataModel));

            long userId = 12;
            long[] result = rec.mostSimilarUserIDs(userId, 5);

            for(int i=0; i<result.length; i++) {

                System.out.println("Similar user - " + result[i]);

                PreferenceArray pref = dataModel.getPreferencesFromUser(result[i]);
                pref.sortByValueReversed();

                Iterator<Preference> iter = pref.iterator();
                while(iter.hasNext()) {
                    Preference p = iter.next();
                    System.out.println(p.getItemID() + " --> " + p.getValue());
                }

            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (TasteException e) {
            e.printStackTrace();
        }

    }

}
