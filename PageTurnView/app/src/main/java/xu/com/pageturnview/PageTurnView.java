package xu.com.pageturnview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 图标的翻页旋转动画。详解 blog：
 *
 * @author chenxuxu
 * @date 2018/6/16
 */
public class PageTurnView extends View {
    private Paint paint;
    private Camera camera;
    private Bitmap bitmap;
    /**
     * a半边的动画旋转角度(第一个动画 3d)
     */
    private int degreeY;
    /**
     * b半边的动画旋转角度(第三个动画 3d)
     */
    private int degreeNextY;
    /**
     * 画布的动画旋转角度(第二个动画 2d)
     */
    private int degreeZ;

    public PageTurnView(Context context) {
        super(context);
    }

    public PageTurnView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PageTurnView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        // 获取图标位图
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.maps);

        // 抗锯齿
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        // 移动相机位置，使投影正常
        camera = new Camera();
        float newZ = -6 * getResources().getDisplayMetrics().density;
        camera.setLocation(0, 0, newZ);
    }

    public int getDegreeY() {
        return degreeY;
    }

    public void setDegreeY(int degreeY) {
        this.degreeY = degreeY;
        invalidate();
    }

    public int getDegreeZ() {
        return degreeZ;
    }

    public void setDegreeZ(int degreeZ) {
        this.degreeZ = degreeZ;
        invalidate();
    }

    public int getDegreeNextY() {
        return degreeNextY;
    }

    public void setDegreeNextY(int degreeNextY) {
        this.degreeNextY = degreeNextY;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int x = centerX - bitmap.getWidth() / 2;
        int y = centerY - bitmap.getHeight() / 2;

        // a半边的处理
        canvas.save();

        camera.save();
        canvas.translate(centerX, centerY);
        // 第一个动画的3d旋转
        camera.rotateY(degreeY);
        // 旋转画布
        canvas.rotate(degreeZ);
        // 此时图标的正中心跟坐标原点位置相同
        canvas.clipRect(0, -centerY, centerX, centerY);
        camera.applyToCanvas(canvas);
        // 记住画布要旋转回来
        canvas.rotate(-degreeZ);
        canvas.translate(-centerX, -centerY);
        camera.restore();

        canvas.drawBitmap(bitmap, x, y, paint);
        canvas.restore();

        // b半边的处理
        canvas.save();

        camera.save();
        canvas.translate(centerX, centerY);
        // b半边也要跟着旋转（保持另外半边不动）
        canvas.rotate(degreeZ);
        // 第三个动画的 3d 旋转
        camera.rotateY(degreeNextY);
        // 此时图标的正中心跟坐标原点位置相同
        canvas.clipRect(-centerX, -centerY, 0, centerY);
        camera.applyToCanvas(canvas);
        // 记住画布要旋转回来
        canvas.rotate(-degreeZ);
        canvas.translate(-centerX, -centerY);
        camera.restore();

        canvas.drawBitmap(bitmap, x, y, paint);
        canvas.restore();
    }
}
