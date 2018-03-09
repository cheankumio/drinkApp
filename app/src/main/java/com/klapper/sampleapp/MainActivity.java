package com.klapper.sampleapp;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.klapper.sampleapp.Adapter.teaCategoryAdapter;
import com.klapper.sampleapp.Animation.CustomAnimation;
import com.xw.repo.BubbleSeekBar;

public class MainActivity extends AppCompatActivity {
    View layout2;
    boolean listSwitch = false;
    public static float screenWidth,screenHeight;
    RecyclerView teaGroup;
    String teaCategoryStr;
    LinearLayout increaseLayout;
    EditText incEdittext;
    Button incButton;
    teaCategoryAdapter mTeaAdapter;
    BubbleSeekBar sugarBubbleSeekBar,iceBubbleSeekBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 隱藏ActionBar
        getSupportActionBar().hide();

        // 取得手機螢幕尺寸
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;

        layout2 = findViewById(R.id.layout2);
        teaGroup = findViewById(R.id.teagroup);
        increaseLayout = findViewById(R.id.increaseLayout);
        incEdittext = findViewById(R.id.incTeaEditText);
        incButton = findViewById(R.id.incTeaButton);
        sugarBubbleSeekBar = findViewById(R.id.bubbles);
        iceBubbleSeekBar = findViewById(R.id.bubbles2);

        BubbleSeekBarSetting();

        increaseLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 防止穿透layout
                return true;
            }
        });

        teaCategoryStr = ReadFile.get(this,"teacategory");
        if(teaCategoryStr.length()<2){
            teaCategoryStr = "綠茶,紅茶,青茶,麥茶,烏龍茶,鮮奶茶,奶茶(奶精),普洱茶";
            ReadFile.put(this,"teacategoty",teaCategoryStr);
        }

        mTeaAdapter= new teaCategoryAdapter(this, teaCategoryStr);
        mTeaAdapter.setIncreaseClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomAnimation.singleClick(v);
                CustomAnimation.showLayout(increaseLayout);

            }
        });
        teaGroup.setLayoutManager(new GridLayoutManager(this,4));
        teaGroup.setAdapter(mTeaAdapter);

        incButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String teaName = incEdittext.getText().toString();
                if(teaName == null || teaName.length()<1) teaName="香蕉牛奶";
                mTeaAdapter.getTeaCategoryList().add(teaName);
                mTeaAdapter.notifyItemInserted(mTeaAdapter.getTeaCategoryList().size()-1);
                Log.d("MYLOG","點到了新增");
                increaseLayout.setVisibility(View.GONE);
                incEdittext.setText("");
            }
        });

    }




    //enter是設定在activity_layout.xml內Button的onClick欄位填入的名稱
    public void rightbutton(View v){
        AnimatorSet ans = new AnimatorSet();
        ObjectAnimator[] oan = new ObjectAnimator[4];
        if(listSwitch){
            oan[0] = ObjectAnimator.ofFloat(layout2, "translationX", -(screenWidth),0);
            oan[1] = ObjectAnimator.ofFloat(layout2, "scaleX", 0.87f,1f);
            oan[2] = ObjectAnimator.ofFloat(layout2, "scaleY", 0.87f,1f);
            oan[3] = ObjectAnimator.ofFloat(v, "rotation", 180,0);
        }else{
            oan[0] = ObjectAnimator.ofFloat(layout2, "translationX", 0,-(screenWidth));
            oan[1] = ObjectAnimator.ofFloat(layout2, "scaleX", 1f,0.87f);
            oan[2] = ObjectAnimator.ofFloat(layout2, "scaleY", 1f,0.87f);
            oan[3] = ObjectAnimator.ofFloat(v, "rotation", 0,180);
        }
        listSwitch = !listSwitch;
        ans.setInterpolator(new AnticipateOvershootInterpolator());
        ans.playTogether(oan);
        ans.setDuration(650);
        ans.start();
    }

    public void addbtn(View v){
        CustomAnimation.singleClick(v);

    }
    public void clearbtn(View v){
        CustomAnimation.singleClick(v);

    }

    private void BubbleSeekBarSetting() {
        sugarBubbleSeekBar.getConfigBuilder()
                .min(0)
                .max(4)
                .progress(1)
                .sectionCount(4)
                .trackColor(ContextCompat.getColor(this, R.color.color_gray))
                .secondTrackColor(ContextCompat.getColor(this, R.color.color_blue))
                .thumbColor(ContextCompat.getColor(this, R.color.color_4))
                .showSectionText()
                .sectionTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .trackColor(ContextCompat.getColor(this, R.color.color_4))
                .sectionTextSize(18)
                .showThumbText()
                .touchToSeek()
                .thumbTextColor(ContextCompat.getColor(this, R.color.color_red))
                .thumbTextSize(18)
                .bubbleColor(ContextCompat.getColor(this, R.color.color_red))
                .bubbleTextSize(18)
                .showSectionMark()
                .seekBySection()
                .autoAdjustSectionMark()
                .sectionTextPosition(BubbleSeekBar.TextPosition.BELOW_SECTION_MARK)
                .build();

        sugarBubbleSeekBar.setCustomSectionTextArray(new BubbleSeekBar.CustomSectionTextArray() {
            @NonNull
            @Override
            public SparseArray<String> onCustomize(int sectionCount, @NonNull SparseArray<String> array) {
                array.clear();
                array.put(0, "無糖");
                array.put(1, "微糖");
                array.put(2, "半糖");
                array.put(3, "少糖");
                array.put(4, "全糖");

                return array;
            }
        });

        sugarBubbleSeekBar.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListenerAdapter() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
                int color;
                if (progress < 1) {
                    color = ContextCompat.getColor(MainActivity.this, R.color.color_0);
                } else if (progress < 2) {
                    color = ContextCompat.getColor(MainActivity.this, R.color.color_1);
                } else if (progress < 3) {
                    color = ContextCompat.getColor(MainActivity.this, R.color.color_2);
                } else if (progress < 4) {
                    color = ContextCompat.getColor(MainActivity.this, R.color.color_3);
                } else {
                    color = ContextCompat.getColor(MainActivity.this, R.color.color_4);
                }

                bubbleSeekBar.setSecondTrackColor(color);
            }
        });

        iceBubbleSeekBar.getConfigBuilder()
                .min(0)
                .max(6)
                .progress(3)
                .sectionCount(6)
                .trackColor(ContextCompat.getColor(this, R.color.color_gray))
                .secondTrackColor(ContextCompat.getColor(this, R.color.color_blue))
                .thumbColor(ContextCompat.getColor(this, R.color.color_blue))
                .showSectionText()
                .sectionTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .trackColor(ContextCompat.getColor(this, R.color.icecolor_9))
                .sectionTextSize(18)
                .showThumbText()
                .touchToSeek()
                .thumbTextColor(ContextCompat.getColor(this, R.color.color_blue))
                .thumbTextSize(18)
                .bubbleColor(ContextCompat.getColor(this, R.color.color_blue))
                .bubbleTextSize(18)
                .showSectionMark()
                .seekBySection()
                .autoAdjustSectionMark()
                .sectionTextPosition(BubbleSeekBar.TextPosition.BELOW_SECTION_MARK)
                .build();

        iceBubbleSeekBar.setCustomSectionTextArray(new BubbleSeekBar.CustomSectionTextArray() {
            @NonNull
            @Override
            public SparseArray<String> onCustomize(int sectionCount, @NonNull SparseArray<String> array) {
                array.clear();
                array.put(0, "熱");
                array.put(1, "溫");
                array.put(2, "去冰");
                array.put(3, "微冰");
                array.put(4, "半冰");
                array.put(5, "少冰");
                array.put(6, "全冰");

                return array;
            }
        });

        iceBubbleSeekBar.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListenerAdapter() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
                int color;
                if (progress < 1) {
                    color = ContextCompat.getColor(MainActivity.this, R.color.icecolor_0);
                } else if (progress < 2) {
                    color = ContextCompat.getColor(MainActivity.this, R.color.icecolor_1);
                } else if (progress < 3) {
                    color = ContextCompat.getColor(MainActivity.this, R.color.icecolor_2);
                } else if (progress < 4) {
                    color = ContextCompat.getColor(MainActivity.this, R.color.icecolor_3);
                }  else if (progress < 5) {
                    color = ContextCompat.getColor(MainActivity.this, R.color.icecolor_4);
                }  else if (progress < 6) {
                    color = ContextCompat.getColor(MainActivity.this, R.color.icecolor_5);
                } else {
                    color = ContextCompat.getColor(MainActivity.this, R.color.icecolor_6);
                }

                bubbleSeekBar.setSecondTrackColor(color);
            }
        });
    }
}
