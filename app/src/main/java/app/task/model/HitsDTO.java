package app.task.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class HitsDTO {

    @SerializedName("hits")
    private ArrayList<ImageDTO> hits;

    public ArrayList<ImageDTO> getHits() {
        return hits;
    }
}
