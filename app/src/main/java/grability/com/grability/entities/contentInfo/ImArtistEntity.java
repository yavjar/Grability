package grability.com.grability.entities.contentInfo;

/**
 * Created by Salsa on 28-May-16.
 */
public class ImArtistEntity {
    String lable;
    String href;

    public ImArtistEntity(String lable, String href) {
        this.lable = lable;
        this.href = href;
    }

    public String getLable() {
        return lable;
    }

    public String getHref() {
        return href;
    }
}
