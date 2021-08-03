package app.task.service.client;

import app.task.model.HitsDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ImagesClient {

    String IMAGES_API_KEY = "22766949-40cf217a1bd4daaf422af0102";
    int PER_PAGE = 12;

    @GET("api/?key=" + IMAGES_API_KEY + "&per_page=" + PER_PAGE)
    Call<HitsDTO> getImages(@Query("page") int page, @Query("q") String search);
}
