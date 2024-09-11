package com.test.attack;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;

public class CustomOverlayView extends FrameLayout {
    private ArrayList<Drawable> mMarkers = new ArrayList<Drawable>();;
    private Drawable mTrackingMarker;
    private Point mTrackingPoint;
    private int clickCount = 0;

    public CustomOverlayView(Context context) {
        super(context);
        init(context);
    }

    public CustomOverlayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomOverlayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        // 加载自定义布局
        LayoutInflater.from(context).inflate(R.layout.activity_overlay, this, true);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        // 记录点击事件
//        handleEvent(0, this, event);
        // 将事件传递到下层

        return false;
    }


    private void handleEvent(int optionId, View v, MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Drawable current = markerAt(x, y);
                if (current == null) {

//                    mTrackingMarker = addBox(v, x, y);
                    mTrackingPoint = new Point(x, y);
                    mTrackingMarker = addFlag(v, x, y);

                } else {
                    //移除已经存在的标记
//                    removeMarker(v, current);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                //更新当前的标记让其移动起来
                if (mTrackingMarker != null) {
                    resizeBox(v, mTrackingMarker, mTrackingPoint, x, y);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                //手势终止清除状态
                mTrackingMarker = null;
                mTrackingPoint = null;
                break;
        }
    }

    /*
     * 在给定坐标添加一个新的大小可变的盒子
     */
    private Drawable addBox(View v, int x, int y) {
        Drawable box = getResources().getDrawable(R.drawable.ic_launcher_background, null);
        //在触摸点创建一个尺寸为0的盒子
        Rect bounds = new Rect(x, y, x+10, y+10);
        box.setBounds(bounds);
        //添加到浮层
        mMarkers.add(box);
        v.getOverlay().add(box);
        return box;
    }

    /*
     * 基于给定坐标点改变盒子的大小
     */
    private void resizeBox(View v, Drawable target, Point trackingPoint, int x, int y) {
        Rect bounds = new Rect(target.getBounds());
        // 根据触摸点与起始点的位置更新边界
        if (x < trackingPoint.x) {
            bounds.left = x;
        } else {
            bounds.right = x;
        }
        if (y < trackingPoint.y) {
            bounds.top = y;
        } else {
            bounds.bottom = y;
        }
        //更新边界并重绘
        target.setBounds(bounds);
        v.invalidate();
    }

    /*
     * 在给定坐标添加一个新的指示标记
     */
    private Drawable addFlag(View v, int x, int y) {
        //创建一个新的指示标记绘画
        int ID = 0;

        int[] drawableIds = {
                R.drawable.box,
                R.drawable.box1,
                R.drawable.box2,
                R.drawable.box3,
                R.drawable.box4,
                R.drawable.box5
        };

        ID = drawableIds[clickCount % drawableIds.length];

        Drawable marker = getResources().getDrawable(ID, null);

        //创建一个匹配图像的边界
        int sideLength = 50;

        // 创建一个正方形的边界
        Rect bounds = new Rect(0, 0, sideLength / 2, sideLength / 2);
//        Rect bounds = new Rect(0, 0, marker.getIntrinsicWidth(), marker.getIntrinsicHeight());
        //以标识底部中心点作为定位点
        bounds.offset(x - (bounds.width()), y - bounds.height());
        marker.setBounds(bounds);
        //添加到浮层
        mMarkers.add(marker);
        v.getOverlay().add(marker);
        clickCount++;

        return marker;
    }

    /*
     * 更新一个标识标记的位置
     */
    private void offsetFlag(View v, Drawable marker, int x, int y) {
        Rect bounds = new Rect(marker.getBounds());
        //移动边界到新的坐标点
        bounds.offset(x - bounds.left - (bounds.width() / 2), y - bounds.top - bounds.height());
        //跟新并重绘
        marker.setBounds(bounds);
        v.invalidate();
    }

    /*
     * 移除选择的标记
     */
    private void removeMarker(View v, Drawable marker) {
        mMarkers.remove(marker);
        v.getOverlay().remove(marker);
    }

    /*
     * 在给定坐标点找到第一个标记
     */
    private Drawable markerAt(int x, int y) {
        //返回给定点的第一个标记
        for (Drawable marker : mMarkers) {
            if (marker.getBounds().contains(x, y)) {
                return marker;
            }
        }

        return null;
    }
}