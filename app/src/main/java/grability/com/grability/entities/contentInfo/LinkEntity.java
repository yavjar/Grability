package grability.com.grability.entities.contentInfo;

/**
 * Created by Salsa on 28-May-16.
 */
public class LinkEntity {
    String rel;
    String type;
    String href;

    public LinkEntity(String rel, String type, String href) {
        this.rel = rel;
        this.type = type;
        this.href = href;
    }

    public String getRel() {
        return rel;
    }

    public String getType() {
        return type;
    }

    public String getHref() {
        return href;
    }
}
