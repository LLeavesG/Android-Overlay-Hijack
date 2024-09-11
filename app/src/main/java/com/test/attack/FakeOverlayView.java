package com.test.attack;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class FakeOverlayView extends View {

    public FakeOverlayView(Context context) {
        super(context);
    }

    public FakeOverlayView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FakeOverlayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        // 记录点击事件
        handleEvent(event);
        // 将事件传递到下层
        return false;
    }

    private void handleEvent(MotionEvent event) {
        // 在这里处理点击事件
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 处理按下事件
                break;
            case MotionEvent.ACTION_MOVE:
                // 处理移动事件
                break;
            case MotionEvent.ACTION_UP:
                // 处理抬起事件
                break;
        }
    }
}