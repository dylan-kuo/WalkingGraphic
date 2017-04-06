package com.example.mayra.walking;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {

    protected ImageView character;
    AnimationDrawable animationDrawable;
    private Handler repeatUpdateHandler = new Handler();
    private float currentLocation;
    private boolean autoIncrement = false;
    private boolean autoDecrement = false;
    private final long REPEAT_DELAY = 30;    // The walking speed will go up/down based on REPEAT_DELAY
    private int shifting_Unit = 10;  // The default value for location shifting

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        character = (ImageView) findViewById(R.id.imageView);
        character.setBackgroundResource(R.drawable.man_walking);
        animationDrawable = (AnimationDrawable) character.getBackground();
        ImageButton ibRunRight = (ImageButton) findViewById(R.id.imageButton2);
        ImageButton ibRunLeft = (ImageButton) findViewById(R.id.imageButton);

        class RepetitiveUpdater implements Runnable {

            @Override
            public void run() {
                if (autoIncrement) {
                    increment();
                    repeatUpdateHandler.postDelayed(new RepetitiveUpdater(), REPEAT_DELAY);
                }
                else if (autoDecrement) {
                    decrement();
                    repeatUpdateHandler.postDelayed(new RepetitiveUpdater(), REPEAT_DELAY);
                }
            }
        }
        // Once the right button long clicked, the character will keep on going rightward
        ibRunRight.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                autoIncrement = true;
                repeatUpdateHandler.post(new RepetitiveUpdater());
                return false;
            }
        });

        ibRunRight.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP && autoIncrement) {
                    autoIncrement = false;
                }
                return false;
            }
        });

        // Once the left button long clicked, the character will keep on going leftward
        ibRunLeft.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                autoDecrement = true;
                repeatUpdateHandler.post(new RepetitiveUpdater());
                return false;
            }
        });

        ibRunLeft.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP && autoDecrement){
                    autoDecrement = false;
                }
                return false;
            }
        });
    }

    // If increment function called, the character will change its x-axis location by adding default unit
    public void increment() {
        currentLocation = character.getX();
        character.setX(currentLocation + shifting_Unit);
        character.setScaleX(1);
        animationDrawable.start();
    }

    // If decrement function called, the character will change its x-axis location by decreasing default unit
    public void decrement() {
        currentLocation = character.getX();
        character.setX(currentLocation - shifting_Unit);
        character.setScaleX(-1);
        animationDrawable.start();
    }


    // Once the right button clicked, the character will go rightward default units
    public void goRight(View view) {
        currentLocation = character.getX();
        character.setX(currentLocation + shifting_Unit);
        character.setScaleX(1);
        animationDrawable.start();
        //animatedBackground(1.0f, 0.0f, 10000L).start();
    }

    // Once the left button clicked, the character will go leftward default units
    public void goLeft(View view) {
        currentLocation = character.getX();
        character.setX(currentLocation - shifting_Unit);
        character.setScaleX(-1);
        animationDrawable.start();
        //animatedBackground(0.0f, 1.0f, 10000L).start();
    }

    // Once the stop button clicked, the character will stop
    public void onClick(View view) {
        animationDrawable.stop();
    }

    /*// Method to animate the background
    protected ValueAnimator animatedBackground(float d1, float d2, long d3){
        // Backgrounds are initialized
        final ImageView background1 = (ImageView) findViewById(R.id.background1);
        final ImageView background2 = (ImageView) findViewById(R.id.background2);

        final ValueAnimator animator = ValueAnimator.ofFloat(d1, d2);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(d3);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float progress = (float) animation.getAnimatedValue();
                final float width = background1.getWidth();
                final float translationX = width * progress;
                background1.setTranslationX(translationX);
                background2.setTranslationX(translationX - width);
            }
        });
        return animator;
    }*/
}

