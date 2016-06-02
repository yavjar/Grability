package grability.com.grability.entities.contentInfo;

/**
 * Created by Salsa on 28-May-16.
 */
public class AuthorEntity {
    String name;
    String uri;

    public AuthorEntity(String name, String uri) {
        this.name = name;
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
