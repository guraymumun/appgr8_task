package app.task.ui.controller.activities;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import app.task.R;
import app.task.model.ImageDTO;


public class SingleImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_image);

        ImageView imageView = (ImageView) findViewById(R.id.image);

        ImageDTO image = (ImageDTO) getIntent().getSerializableExtra("IMAGE");
        Glide.with(this)
                .load(image.getImageUrl())
                .placeholder(android.R.drawable.screen_background_dark)
                .into(imageView);
    }
}