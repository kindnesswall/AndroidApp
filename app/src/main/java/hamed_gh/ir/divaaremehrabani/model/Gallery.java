package hamed_gh.ir.divaaremehrabani.model;

/**
 * Created by Hamed on 5/6/16.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Gallery {

    @SerializedName("GalleryId")
    @Expose
    private String GalleryId;

    @SerializedName("Title")
    @Expose
    private String Title;

    @SerializedName("Description")
    @Expose
    private Object Description;

    @SerializedName("ImageSrc")
    @Expose
    private String ImageSrc;
    @SerializedName("FileSrc")
    @Expose
    private String FileSrc;
    @SerializedName("IsNew")
    @Expose
    private Boolean IsNew;
    @SerializedName("CreateDate")
    @Expose
    private String CreateDate;

    /**
     * @return The GalleryId
     */
    public String getGalleryId() {
        return GalleryId;
    }

    /**
     * @param GalleryId The GalleryId
     */
    public void setGalleryId(String GalleryId) {
        this.GalleryId = GalleryId;
    }

    /**
     * @return The Title
     */
    public String getTitle() {
        return Title;
    }

    /**
     * @param Title The Title
     */
    public void setTitle(String Title) {
        this.Title = Title;
    }

    /**
     * @return The Description
     */
    public Object getDescription() {
        return Description;
    }

    /**
     * @param Description The Description
     */
    public void setDescription(Object Description) {
        this.Description = Description;
    }

    /**
     * @return The ImageSrc
     */
    public String getImageSrc() {
        return ImageSrc;
    }

    /**
     * @param ImageSrc The ImageSrc
     */
    public void setImageSrc(String ImageSrc) {
        this.ImageSrc = ImageSrc;
    }

    /**
     * @return The FileSrc
     */
    public String getFileSrc() {
        return FileSrc;
    }

    /**
     * @param FileSrc The FileSrc
     */
    public void setFileSrc(String FileSrc) {
        this.FileSrc = FileSrc;
    }

    /**
     * @return The IsNew
     */
    public Boolean getIsNew() {
        return IsNew;
    }

    /**
     * @param IsNew The IsNew
     */
    public void setIsNew(Boolean IsNew) {
        this.IsNew = IsNew;
    }

    /**
     * @return The CreateDate
     */
    public String getCreateDate() {
        return CreateDate;
    }

    /**
     * @param CreateDate The CreateDate
     */
    public void setCreateDate(String CreateDate) {
        this.CreateDate = CreateDate;
    }

}