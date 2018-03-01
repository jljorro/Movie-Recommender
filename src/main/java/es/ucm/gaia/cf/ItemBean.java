package es.ucm.gaia.cf;

/**
 * @author Jose L. Jorro-Aragoneses
 * @version 1.0
 */
public class ItemBean {

    private long id;
    private String title;
    private String geners;

    public ItemBean(String[] row) {
        this.id = Long.valueOf(row[0]);
        this.title = row[1];
        this.geners = row[2];
    }

    public ItemBean(long id, String title, String geners) {
        this.id = id;
        this.title = title;
        this.geners = geners;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getGeners() {
        return geners;
    }
}
