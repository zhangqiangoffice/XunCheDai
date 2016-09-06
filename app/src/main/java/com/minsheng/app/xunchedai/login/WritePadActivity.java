package com.minsheng.app.xunchedai.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.minsheng.app.xunchedai.R;
import com.minsheng.app.xunchedai.utils.ScreenUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**手写签名界面**/
public class WritePadActivity extends Activity {

    Context context;
    private Bitmap mSignBitmap;
    private String signPath;
    private FrameLayout frameLayout;
    static final int BACKGROUND_COLOR = Color.WHITE;
    private Boolean hasDraw;
    PaintView mView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_pad);
        context=this;
        hasDraw = false;

        mView = new PaintView(context);
        frameLayout = (FrameLayout) findViewById(R.id.tablet_view);
        frameLayout.addView(mView);
        mView.requestFocus();
        Button btnClear = (Button) findViewById(R.id.tablet_clear);
        btnClear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mView.clear();
            }
        });

        Button btnOk = (Button) findViewById(R.id.tablet_ok);
        btnOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (hasDraw) {
                    mSignBitmap=mView.getCachebBitmap();
                    signPath = createFile();
                    Log.i("123", "signPath:" + signPath);
                    Intent intent=new Intent();
                    intent.putExtra("imageUrl",signPath);
                    setResult(2, intent);
                    finish();
                } else {
                    finish();
                }
            }
        });

        Button btnCancel = (Button)findViewById(R.id.tablet_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    /**
     * 创建手写签名文件
     *
     * @return
     */
    private String createFile() {
        ByteArrayOutputStream baos = null;
        String _path = null;
        try {
            String sign_dir = Environment.getExternalStorageDirectory() + File.separator;
            _path = sign_dir + System.currentTimeMillis() + ".jpg";
            baos = new ByteArrayOutputStream();
            mSignBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] photoBytes = baos.toByteArray();
            if (photoBytes != null) {
                new FileOutputStream(new File(_path)).write(photoBytes);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null)
                    baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return _path;
    }


    /**
     * This view implements the drawing canvas.
     *
     * It handles all of the input events and drawing functions.
     */
    class PaintView extends View {
        private Paint paint;
        private Canvas cacheCanvas;
        private Bitmap cachebBitmap;
        private Path path;

        public Bitmap getCachebBitmap() {
            return cachebBitmap;
        }

        public PaintView(Context context) {
            super(context);
            init();
        }

        private void init(){
            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStrokeWidth(3);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.BLACK);
            path = new Path();
            cachebBitmap = Bitmap.createBitmap(ScreenUtils.getScreenWidth(WritePadActivity.this), (int)((ScreenUtils.getScreenHeight(WritePadActivity.this))*0.8), Config.ARGB_8888);
//            cachebBitmap = Bitmap.createBitmap(100, 75, Config.ARGB_8888);
            cacheCanvas = new Canvas(cachebBitmap);
            cacheCanvas.drawColor(Color.WHITE);
        }
        public void clear() {
            if (cacheCanvas != null) {

                paint.setColor(BACKGROUND_COLOR);
                cacheCanvas.drawPaint(paint);
                paint.setColor(Color.BLACK);
                cacheCanvas.drawColor(Color.WHITE);
                invalidate();
            }
        }



        @Override
        protected void onDraw(Canvas canvas) {
            // canvas.drawColor(BRUSH_COLOR);
            canvas.drawBitmap(cachebBitmap, 0, 0, null);
            canvas.drawPath(path, paint);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {

            int curW = cachebBitmap != null ? cachebBitmap.getWidth() : 0;
            int curH = cachebBitmap != null ? cachebBitmap.getHeight() : 0;
            if (curW >= w && curH >= h) {
                return;
            }

            if (curW < w)
                curW = w;
            if (curH < h)
                curH = h;

            Bitmap newBitmap = Bitmap.createBitmap(curW, curH, Config.ARGB_8888);
            Canvas newCanvas = new Canvas();
            newCanvas.setBitmap(newBitmap);
            if (cachebBitmap != null) {
                newCanvas.drawBitmap(cachebBitmap, 0, 0, null);
            }
            cachebBitmap = newBitmap;
            cacheCanvas = newCanvas;
        }

        private float cur_x, cur_y;

        @Override
        public boolean onTouchEvent(MotionEvent event) {

            float x = event.getX();
            float y = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    cur_x = x;
                    cur_y = y;
                    path.moveTo(cur_x, cur_y);
                    break;
                }

                case MotionEvent.ACTION_MOVE: {
                    path.quadTo(cur_x, cur_y, x, y);
                    cur_x = x;
                    cur_y = y;
                    hasDraw = true;
                    break;
                }

                case MotionEvent.ACTION_UP: {
                    cacheCanvas.drawPath(path, paint);
                    path.reset();
                    break;
                }
            }
            invalidate();
            return true;
        }
    }

}