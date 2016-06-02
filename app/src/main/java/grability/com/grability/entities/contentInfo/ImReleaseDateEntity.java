package grability.com.grability.entities.contentInfo;

/**
 * Created by Salsa on 28-May-16.
 */
public class ImReleaseDateEntity {
    String label;
    String attrLabel;

    public ImReleaseDateEntity(String label, String attrLabel) {
        this.label = label;
        this.attrLabel = attrLabel;
    }

    public String getLabel() {
        return label;
    }

    public String getAttrLabel() {
        return attrLabel;
    }
}
