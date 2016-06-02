package grability.com.grability.entities.contentInfo;

/**
 * Created by Salsa on 28-May-16.
 */
public class ImageEntity {
    String labelHeight53;
    String labelHeight75;
    String labelHeight100;

    public ImageEntity(String labelHeight53, String labelHeight75, String labelHeight100) {
        this.labelHeight53 = labelHeight53;
        this.labelHeight75 = labelHeight75;
        this.labelHeight100 = labelHeight100;
    }

    public String getLabelHeight53() {
        return labelHeight53;
    }

    public String getLabelHeight75() {
        return labelHeight75;
    }

    public String getLabelHeight100() {
        return labelHeight100;
    }
}
