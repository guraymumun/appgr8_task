package app.task.ui.controller.fragments.images;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.net.HttpURLConnection;
import java.util.ArrayList;

import app.task.R;
import app.task.model.HitsDTO;
import app.task.service.ServiceGenerator;
import app.task.service.client.ImagesClient;
import app.task.ui.component.EndlessRecyclerViewScrollListener;
import app.task.ui.component.adapter.ImagesAdapter;
import app.task.ui.controller.activities.SingleImageActivity;
import app.task.utils.GridImagesItemDecoration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImagesFragment extends Fragment {

    private static final int SPAN_COUNT = 2;
    private int currentPage = 1;

    private EditText search;
    private RecyclerView recyclerView;
    private ImagesAdapter adapter;
    private GridLayoutManager layoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_images, container, false);

        initUI(view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requestGetImages();
    }

    private void initUI(View view) {
        search = view.findViewById(R.id.search);
        recyclerView = view.findViewById(R.id.recyclerView);

        setSearchTextChangedListener();
        setRecyclerView();
    }

    private void setSearchTextChangedListener() {
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                currentPage = 1;
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                requestGetImages();
            }
        });
    }

    private void setRecyclerView() {
        setLayoutManager();
        setItemDecoration();
        setScrollListener();
    }

    private void setAdapter() {
        adapter = new ImagesAdapter(getActivity(), new ArrayList<>());
        adapter.setClickListener(item ->
                startActivity(new Intent(getActivity(), SingleImageActivity.class).putExtra("IMAGE", item)));
        recyclerView.setAdapter(adapter);
    }

    private void setLayoutManager() {
        layoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void setItemDecoration() {
        GridImagesItemDecoration itemDecoration = new GridImagesItemDecoration(
                getResources().getDimensionPixelSize(R.dimen.grid_images_spacing),
                SPAN_COUNT
        );
        recyclerView.addItemDecoration(itemDecoration);
    }

    private void setScrollListener() {
        recyclerView.clearOnScrollListeners();
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                currentPage++;
                requestGetImages();
            }
        });
    }

    private void requestGetImages() {
        ImagesClient client = ServiceGenerator.createService(ImagesClient.class);
        client.getImages(currentPage, search.getText().toString().trim()).enqueue(new Callback<HitsDTO>() {
            @Override
            public void onResponse(@NonNull Call<HitsDTO> call, @NonNull Response<HitsDTO> response) {
                if (response.code() == HttpURLConnection.HTTP_OK && response.body() != null) {
                    if (currentPage == 1)
                        setAdapter();

                    if (adapter != null)
                        adapter.addItems(response.body().getHits());
                }
            }

            @Override
            public void onFailure(@NonNull Call<HitsDTO> call, @NonNull Throwable t) {

            }
        });
    }
}