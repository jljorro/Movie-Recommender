package es.ucm.gaia.cf;

import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.model.DataModel;
import es.ucm.gaia.Main;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jose L. Jorro-Aragoneses
 * @version 1.0
 */
public class MovieConnector {

    private static MovieConnector instance = null;

    private MovieConnector() {}

    public static MovieConnector getInstance() {
        if (instance == null)
            instance = new MovieConnector();

        return instance;
    }

    private DataModel dataModel;
    private Collection<ItemBean> itemList;

    public void init(String pathItems, String pathRatings) throws IOException {

        File f = new File(MovieConnector.class.getClassLoader().getResource(pathRatings).getPath());
        dataModel = new FileDataModel(f);

        List<String[]> data = getCSVRows(pathItems);

        itemList = data.stream()
                .map(ItemBean::new)
                .collect(Collectors.toList());

    }

    public DataModel getDataModel() {
        return dataModel;
    }

    public ItemBean getItemById(long id) {

        ItemBean result = itemList.stream()
                .filter(item -> item.getId() == id)
                .findAny()
                .orElse(null);

        return result;
    }

    public void addItem(long idItem, String title, String genres) {

    }

    public void addUser(long idUser) {

    }

    public void addRating(long idUser, long idItem, float rating) throws TasteException {
        //dataModel.setPreference(idUser, idItem, rating);
    }

    private List<String[]> getCSVRows(String path) {
        InputStream f = MovieConnector.class.getClassLoader().getResourceAsStream(path);

        CsvParserSettings settings = new CsvParserSettings();
        settings.getFormat().setLineSeparator("\n");
        settings.getFormat().setDelimiter(',');

        CsvParser parser = new CsvParser(settings);

        List<String[]> result = parser.parseAll(f);

        return result;
    }
}
