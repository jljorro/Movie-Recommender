package es.ucm.gaia.cf;

/**
 * @author Jose L. Jorro-Aragoneses
 * @version 1.0
 */
public class ItemRatedBean extends ItemBean {

    private float rating;

    public ItemRatedBean(ItemBean item, float rating) {
        super(item.getId(), item.getTitle(), item.getGeners());
        this.rating = rating;
    }

    public ItemRatedBean(long id, String title, String geners, float rating) {
        super(id, title, geners);
        this.rating = rating;
    }

    public float getRating() {
        return rating;
    }
}
