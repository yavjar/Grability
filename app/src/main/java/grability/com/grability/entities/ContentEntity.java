package grability.com.grability.entities;

import grability.com.grability.entities.contentInfo.CategoryEntity;
import grability.com.grability.entities.contentInfo.IdEntity;
import grability.com.grability.entities.contentInfo.ImArtistEntity;
import grability.com.grability.entities.contentInfo.ImReleaseDateEntity;
import grability.com.grability.entities.contentInfo.ImageEntity;
import grability.com.grability.entities.contentInfo.LinkEntity;
import grability.com.grability.entities.contentInfo.PriceEntity;

/**
 * Created by Salsa on 28-May-16.
 */
public class ContentEntity {

    String imName;
    ImageEntity imImage;
    String summary;
    PriceEntity imPrice;

    String contentTypeTerm;
    String contentTypeLabel;

    String rightsLabel;
    String titleLabel;

    LinkEntity link;
    IdEntity id;
    ImArtistEntity imArtist;
    CategoryEntity category;
    ImReleaseDateEntity imReleaseDate;

    public ContentEntity() {
    }

    public ContentEntity(String imName, ImageEntity imImage, String summary, PriceEntity imPrice, String contentTypeTerm, String contentTypeLabel, String rightsLabel, String titleLabel, LinkEntity link, IdEntity id, ImArtistEntity imArtist, CategoryEntity category, ImReleaseDateEntity imReleaseDate) {
        this.imName = imName;
        this.imImage = imImage;
        this.summary = summary;
        this.imPrice = imPrice;
        this.contentTypeTerm = contentTypeTerm;
        this.contentTypeLabel = contentTypeLabel;
        this.rightsLabel = rightsLabel;
        this.titleLabel = titleLabel;
        this.link = link;
        this.id = id;
        this.imArtist = imArtist;
        this.category = category;
        this.imReleaseDate = imReleaseDate;
    }

    public String getImName() {
        return imName;
    }

    public void setImName(String imName) {
        this.imName = imName;
    }

    public ImageEntity getImImage() {
        return imImage;
    }

    public void setImImage(ImageEntity imImage) {
        this.imImage = imImage;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public PriceEntity getImPrice() {
        return imPrice;
    }

    public void setImPrice(PriceEntity imPrice) {
        this.imPrice = imPrice;
    }

    public String getContentTypeTerm() {
        return contentTypeTerm;
    }

    public void setContentTypeTerm(String contentTypeTerm) {
        this.contentTypeTerm = contentTypeTerm;
    }

    public String getContentTypeLabel() {
        return contentTypeLabel;
    }

    public void setContentTypeLabel(String contentTypeLabel) {
        this.contentTypeLabel = contentTypeLabel;
    }

    public String getRightsLabel() {
        return rightsLabel;
    }

    public void setRightsLabel(String rightsLabel) {
        this.rightsLabel = rightsLabel;
    }

    public String getTitleLabel() {
        return titleLabel;
    }

    public void setTitleLabel(String titleLabel) {
        this.titleLabel = titleLabel;
    }

    public LinkEntity getLink() {
        return link;
    }

    public void setLink(LinkEntity link) {
        this.link = link;
    }

    public IdEntity getId() {
        return id;
    }

    public void setId(IdEntity id) {
        this.id = id;
    }

    public ImArtistEntity getImArtist() {
        return imArtist;
    }

    public void setImArtist(ImArtistEntity imArtist) {
        this.imArtist = imArtist;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public ImReleaseDateEntity getImReleaseDate() {
        return imReleaseDate;
    }

    public void setImReleaseDate(ImReleaseDateEntity imReleaseDate) {
        this.imReleaseDate = imReleaseDate;
    }

    @Override
    public String toString() {
        return
                "Precio: " + imPrice.getAmount() + "\n" + summary ;
    }
}
