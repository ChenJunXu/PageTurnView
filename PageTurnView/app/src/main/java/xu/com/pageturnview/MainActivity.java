package xu.com.pageturnview;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.LinearInterpolator;

public class MainActivity extends AppCompatActivity {
    private PageTurnView ptv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    private void initUI() {
        ptv = findViewById(R.id.ptv);

        // 第一个动画
        ObjectAnimator animator1 = ObjectAnimator.ofInt(ptv, "degreeY", 0, -45);
        animator1.setDuration(1000);
        // 第二个动画
        ObjectAnimator animator2 = ObjectAnimator.ofInt(ptv, "degreeZ", 0, -270);
        animator2.setDuration(800);
        // 第三个动画
        ObjectAnimator animator3 = ObjectAnimator.ofInt(ptv, "degreeNextY", 0, 30);
        animator3.setDuration(1000);

        // 三个动画按顺序执行
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.playSequentially(animator1, animator2, animator3);
        animatorSet.start();
    }
}
