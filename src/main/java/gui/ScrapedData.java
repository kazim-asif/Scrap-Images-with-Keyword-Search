package gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ScrapedData {
    private final StringProperty pageUrl;
    private final StringProperty imgUrl;
    private final StringProperty altText;

    public ScrapedData(String pageUrl, String imgUrl, String altText) {
        this.pageUrl = new SimpleStringProperty(pageUrl);
        this.imgUrl = new SimpleStringProperty(imgUrl);
        this.altText = new SimpleStringProperty(altText);
    }

    public String getPageUrl() {
        return pageUrl.get();
    }

    public StringProperty pageUrlProperty() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl.set(pageUrl);
    }

    public String getImgUrl() {
        return imgUrl.get();
    }

    public StringProperty imgUrlProperty() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl.set(imgUrl);
    }

    public String getAltText() {
        return altText.get();
    }

    public StringProperty altTextProperty() {
        return altText;
    }

    public void setAltText(String altText) {
        this.altText.set(altText);
    }
}
