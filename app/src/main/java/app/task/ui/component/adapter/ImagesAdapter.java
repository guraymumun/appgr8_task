package app.task.ui.component.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import app.task.R;
import app.task.model.ImageDTO;


public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ViewHolder> {

    private final ArrayList<ImageDTO> items;
    private final LayoutInflater mInflater;
    private final Context context;
    private ItemClickListener mClickListener;

    public ImagesAdapter(Context context, ArrayList<ImageDTO> items) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.items = items;
    }

    public void addItems(ArrayList<ImageDTO> posts) {
        for (ImageDTO post : posts)
            addItem(post);
    }

    public void addItem(ImageDTO post) {
        items.add(post);
        this.notifyItemInserted(items.size() - 1);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void clear() {
        items.clear();
        this.notifyDataSetChanged();
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_grid_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context)
                .load(items.get(holder.getAdapterPosition()).getImageUrl())
                .placeholder(android.R.drawable.screen_background_dark)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(items.get(getAdapterPosition()));
        }
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(ImageDTO item);
    }
}
