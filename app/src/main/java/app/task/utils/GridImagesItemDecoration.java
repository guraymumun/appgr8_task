package app.task.utils;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class GridImagesItemDecoration extends RecyclerView.ItemDecoration {

    private int mSizeGridSpacingPx;
    private int mGridSize;

    private boolean mNeedLeftSpacing = false;

    private boolean firstItemIsLarge = false;

    public GridImagesItemDecoration(int gridSpacingPx, int gridSize) {
        mSizeGridSpacingPx = gridSpacingPx;
        mGridSize = gridSize;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int frameWidth = (int) ((parent.getWidth() - (float) mSizeGridSpacingPx * (mGridSize - 1)) / mGridSize);
        int padding = parent.getWidth() / mGridSize - frameWidth;
        int itemPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewAdapterPosition();

        if(firstItemIsLarge) {
            if (itemPosition == 0) {
                outRect.top = 0;
                return;
            } else
                outRect.top = mSizeGridSpacingPx;

            if ((itemPosition + 2) % mGridSize == 0)
                updateOffsets(outRect, 0, padding, true);
            else if (itemPosition % mGridSize == 0)
                updateOffsets(outRect, padding, 0, false);
            else if (mNeedLeftSpacing) {
                mNeedLeftSpacing = false;
                outRect.left = mSizeGridSpacingPx - padding;
                if ((itemPosition + 1) % mGridSize == 0)
                    updateOffsets(outRect, mSizeGridSpacingPx - padding, mSizeGridSpacingPx - padding, false);
                else
                    updateOffsets(outRect, mSizeGridSpacingPx - padding, mSizeGridSpacingPx / 2, false);
            } else if ((itemPosition + 1) % mGridSize == 0)
                updateOffsets(outRect, mSizeGridSpacingPx / 2, mSizeGridSpacingPx - padding, false);
            else
                updateOffsets(outRect, mSizeGridSpacingPx / 2, mSizeGridSpacingPx / 2, false);
        } else {
            if (itemPosition < mGridSize || itemPosition == lastItemPosition)
                outRect.top = 0;
            else
                outRect.top = mSizeGridSpacingPx;

            if (itemPosition % mGridSize == 0)
                updateOffsets(outRect, 0, padding, true);
            else if ((itemPosition + 1) % mGridSize == 0)
                updateOffsets(outRect, padding, 0, false);
            else if (mNeedLeftSpacing) {
                mNeedLeftSpacing = false;
                outRect.left = mSizeGridSpacingPx - padding;
                if ((itemPosition + 2) % mGridSize == 0)
                    updateOffsets(outRect, mSizeGridSpacingPx - padding, mSizeGridSpacingPx - padding, false);
                else
                    updateOffsets(outRect, mSizeGridSpacingPx - padding, mSizeGridSpacingPx / 2, false);
            } else if ((itemPosition + 2) % mGridSize == 0)
                updateOffsets(outRect, mSizeGridSpacingPx / 2, mSizeGridSpacingPx - padding, false);
            else
                updateOffsets(outRect, mSizeGridSpacingPx / 2, mSizeGridSpacingPx / 2, false);
        }
        outRect.bottom = 0;
    }

    private void updateOffsets(Rect outRect, int leftPadding, int rightPadding, boolean needLeftSpacing) {
        outRect.left = leftPadding;
        outRect.right = rightPadding;
        mNeedLeftSpacing = needLeftSpacing;
    }

    public int lastItemPosition = 0;
}
