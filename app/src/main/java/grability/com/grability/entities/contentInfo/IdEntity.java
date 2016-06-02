package grability.com.grability.entities.contentInfo;

/**
 * Created by Salsa on 28-May-16.
 */
public class IdEntity {
String label;
int imId;
String imBundleID;

    public IdEntity(String label, int imId, String imBundleID) {
        this.label = label;
        this.imId = imId;
        this.imBundleID = imBundleID;
    }

    public String getLabel() {
        return label;
    }

    public int getImId() {
        return imId;
    }

    public String getImBundleID() {
        return imBundleID;
    }
}
