package grability.com.grability.entities.contentInfo;

/**
 * Created by Salsa on 28-May-16.
 */
public class CategoryEntity {
    int imId;
    String term;
    String scheme;
    String label;

    public CategoryEntity(int imId, String term, String scheme, String label) {
        this.imId = imId;
        this.term = term;
        this.scheme = scheme;
        this.label = label;
    }

    public int getImId() {
        return imId;
    }

    public String getTerm() {
        return term;
    }

    public String getScheme() {
        return scheme;
    }

    public String getLabel() {
        return label;
    }
}
