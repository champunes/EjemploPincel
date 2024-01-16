package es.iesaguadulce.ejemplopincel;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Lienzo extends View {

    private Bitmap mBitmapBrush;
    private Vector2 mBitmapBrushDimensions;
    private List<Vector2> mPositions = new ArrayList<Vector2>(100);

    private static final class Vector2 {
        public final float x;
        public final float y;

        public Vector2(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }

    public static Bitmap drawableToBitmap (Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable)drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    public Lienzo(Context context, AttributeSet attrs) {
        super(context, attrs);

        mBitmapBrush = drawableToBitmap(getResources().getDrawable(R.drawable.star));
        mBitmapBrushDimensions = new Vector2(mBitmapBrush.getWidth(), mBitmapBrush.getHeight());
        setBackgroundColor(Color.WHITE);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        Paint alphaPaint = new Paint();
        alphaPaint.setAlpha(100);

        for (Vector2 position : mPositions) {
            canvas.drawBitmap(mBitmapBrush, position.x, position.y, alphaPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_MOVE:
                final float posX = event.getX();
                final float posY = event.getY();
                mPositions.add(new Vector2(posX - mBitmapBrushDimensions.x / 2, posY - mBitmapBrushDimensions.y / 2));
                invalidate();
        }

        return true;
    }
}
