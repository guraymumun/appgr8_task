package app.task.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ImageDTO implements Serializable {

    @SerializedName("webformatURL")
    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }
}
