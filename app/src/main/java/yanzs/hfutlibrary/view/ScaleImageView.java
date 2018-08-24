package yanzs.hfutlibrary.view;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;

/**
 * https://github.com/wangluAndroid/ScaleImageView转自
 * 2018-7-16
 */
public class ScaleImageView extends android.support.v7.widget.AppCompatImageView implements ViewTreeObserver.OnGlobalLayoutListener, ScaleGestureDetector.OnScaleGestureListener, View.OnTouchListener {

    private float initScale;
    private float midScale;
    private float maxScale;
    private Matrix matrix;
    private boolean once = false;
    private ScaleGestureDetector scaleGestureDetector;//捕获用户多指触控时缩放的比例
    private int lastPointerCount;// 记录上次多点触控的数量
    private float lastPointX;//记录上次中心点的X坐标
    private float lastPointY;//记录上次中心点的Y坐标
    private float touchSlop;//系统触发的最小滑动距离
    private boolean isCanDrag;
    private boolean isCheckLeftAndRight;
    private boolean isCheckTopAndBottom;
    private GestureDetector gestureDetector;//用户双击手势的对象变量
    private boolean isScaling;//是否正在放大或缩小---防止用户在正在放大或缩小时疯狂点击

    public ScaleImageView(Context context) {
        this(context, null);
    }

    public ScaleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScaleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        matrix = new Matrix();
        setScaleType(ScaleType.MATRIX);

        scaleGestureDetector = new ScaleGestureDetector(context, this);
        setOnTouchListener(this);
        //系统触发的最小滑动距离
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

        //双击放大与缩小
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                if (isScaling) {
                    return true;
                }
                //以此点为缩放中心
                float x = e.getX();
                float y = e.getY();

                if (getScale() < midScale) {
                    postDelayed(new ScaleRunnable(midScale, x, y), 20);
                    isScaling = true;
                } else {
                    postDelayed(new ScaleRunnable(initScale, x, y), 20);
                    isScaling = true;
                }

                return true;
            }
        });
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);//注册onGlobalLayoutListener
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        float scale = getScale();
        //捕获用户多指触控时缩放的比例
        float scaleFactor = detector.getScaleFactor();
        if (getDrawable() == null) {
            return true;
        }
        //最大最小控制
        if ((scale < maxScale && scaleFactor > 1.0f) || (scale > initScale && scaleFactor < 1.0f)) {
            if (scale * scaleFactor > maxScale) {
                scaleFactor = maxScale / scale;
            }
            if (scale * scaleFactor < initScale) {
                scaleFactor = initScale / scale;
            }

            matrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());
            //不断检测 控制白边和中心位置
            checkBorderAndCenterWhenScale();
            setImageMatrix(matrix);
        }

        return true;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;//修改为true 才会进入onScale()这个函数  否则多指触控一直走onScaleBegin方法 不走 onScale和 onScaleEnd方法
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //双击放大与缩小事件传递给GestureDetector 放在最前面 防止双击时还能产生移动的事件响应
        if (gestureDetector.onTouchEvent(event)) {
            return true;
        }
        //将手势传递给ScaleGestureDetector
        boolean onTouchEvent = scaleGestureDetector.onTouchEvent(event);

        //-------------------------将放大的图片自由移动逻辑处理-----------------start------------
        //得到触控中心点的坐标
        float pointerX = 0;
        float pointerY = 0;
        //拿到多点触控的数量
        int pointerCount = event.getPointerCount();
        for (int i = 0; i < pointerCount; i++) {
            pointerX += event.getX(i);
            pointerY += event.getY(i);
        }
        pointerX /= pointerCount;
        pointerY /= pointerCount;
        if (lastPointerCount != pointerCount) {
            //手指发生改变时 需要重新判断 是否能够移动
            isCanDrag = false;
            lastPointX = pointerX;
            lastPointY = pointerY;
        }
        lastPointerCount = pointerCount;
        RectF rectF = getMatrixRectF();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (getParent() instanceof ViewPager) {
                    //如果图片放大时 处理图片平移与ViewPager的滑动冲突
                    if (rectF.width() - getWidth() > 0.01 || rectF.height() - getHeight() > 0.01) {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                //如果图片放大时 处理图片平移与ViewPager的滑动冲突
                if (getParent() instanceof ViewPager) {
                    if (rectF.width() - getWidth() > 0.01 || rectF.height() - getHeight() > 0.01) {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                }

                float dx = pointerX - lastPointX;
                float dy = pointerY - lastPointY;
                if (!isCanDrag) {
                    isCanDrag = isMoveAction(dx, dy);
                }
                if (isCanDrag) {
                    if (getDrawable() != null) {
                        isCheckLeftAndRight = isCheckTopAndBottom = true;
                        //如果图片宽度小于控件宽度 不允许横向移动
                        if (rectF.width() < getWidth()) {
                            isCheckLeftAndRight = false;
                            dx = 0;
                        }
                        //如果图片的高度小于控件的高度 不允许纵向移动
                        if (rectF.height() < getHeight()) {
                            isCheckTopAndBottom = false;
                            dy = 0;
                        }

                        matrix.postTranslate(dx, dy);
                        checkBorderWhenTranslate();
                        setImageMatrix(matrix);
                    }
                }
                lastPointX = pointerX;
                lastPointY = pointerY;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                lastPointerCount = 0;
                break;
        }
        //-------------------------将放大的图片自由移动逻辑处理-------------------end----------
        return true;
    }

    private void checkBorderWhenTranslate() {
        RectF rect = getMatrixRectF();
        float deltaX = 0;
        float deltaY = 0;
        int width = getWidth();
        int height = getHeight();
        if (rect.top > 0 && isCheckTopAndBottom) {
            deltaY = -rect.top;
        }
        if (rect.bottom < height && isCheckTopAndBottom) {
            deltaY = height - rect.bottom;
        }
        if (rect.left > 0 && isCheckLeftAndRight) {
            deltaX = -rect.left;
        }
        if (rect.right < width && isCheckLeftAndRight) {
            deltaX = width - rect.right;
        }
        matrix.postTranslate(deltaX, deltaY);
    }

    private boolean isMoveAction(float dx, float dy) {
        return Math.sqrt(dx * dx + dy * dy) > touchSlop;
    }

    @Override
    public void onGlobalLayout() {
        //初始化的操作 一次就好  为了保证对缩放只进行一次
        if (!once) {
            //得到控件的宽和高--不一定是屏幕的宽和高 可能会有actionBar等等
            int width = getWidth();
            int height = getHeight();
            //得到我们的图片 以及宽和高
            Drawable drawable = getDrawable();
            if (drawable == null) {
                return;
            }
            /**
             * 这里说下Drawable这个抽象类，具体实现类为BitmapDrawable
             * BitmapDrawable这个类重写了getIntrinsicWidth()和getIntrinsicHeight()方法
             * 这个两个方法看字面意思就知道是什么了，就是得到图片固有的宽和高的
             */
            int intrinsicWidth = drawable.getIntrinsicWidth();
            int intrinsicHeight = drawable.getIntrinsicHeight();
            // 如果图片宽度比控件宽度小  高度比控件大 需要缩小
            float scale = 1.0f;
            if (width > intrinsicWidth && height < intrinsicHeight) {
                scale = height * 1.0f / intrinsicHeight;
            }
            // 如果图片比控件大 需要缩小
            if (width < intrinsicWidth && height > intrinsicHeight) {
                scale = width * 1.0f / intrinsicWidth;
            }

            if ((width < intrinsicWidth && height < intrinsicHeight) || (width > intrinsicWidth && height > intrinsicHeight)) {
                scale = Math.min(width * 1.0f / intrinsicWidth, height * 1.0f / intrinsicHeight);
            }

            /**
             * 得到初始化缩放的比例
             */
            initScale = scale;
            midScale = 2 * initScale;//双击放大的值
            maxScale = 4 * initScale;//放大的最大值

            //将图片移动到控件的中心
            int dx = width / 2 - intrinsicWidth / 2;
            int dy = height / 2 - intrinsicHeight / 2;
            //将一些参数设置到图片或控件上 设置平移缩放 旋转
            matrix.postTranslate(dx, dy);
            matrix.postScale(initScale, initScale, width / 2, height / 2);//以控件的中心进行缩放
            setImageMatrix(matrix);
            once = true;
        }
    }

    private class ScaleRunnable implements Runnable {

        private float targetScale;//缩放的目标值
        private float x;//缩放的中心点
        private float y;

        //放大与缩小的梯度
        private final float BIGGER = 1.05F;
        private final float SMALL = 0.95F;

        private float tmpScale;

        public ScaleRunnable(float targetScale, float x, float y) {
            this.targetScale = targetScale;
            this.x = x;
            this.y = y;
            if (getScale() < targetScale) {
                tmpScale = BIGGER;
            }
            if (getScale() > targetScale) {
                tmpScale = SMALL;
            }
        }

        @Override
        public void run() {
            matrix.postScale(tmpScale, tmpScale, x, y);
            checkBorderAndCenterWhenScale();
            setImageMatrix(matrix);
            float currentScale = getScale();
            if ((tmpScale > 1.0f && currentScale < targetScale) || (tmpScale < 1.0f && currentScale > targetScale)) {
                postDelayed(this, 16);
            } else {
                isScaling = false;
                //到达了目标值
                float scale = targetScale / currentScale;
                matrix.postScale(scale, scale, x, y);
                checkBorderAndCenterWhenScale();
                setImageMatrix(matrix);
            }
        }

    }


    private RectF getMatrixRectF() {
        Matrix mat = matrix;
        RectF rect = new RectF();
        Drawable drawable = getDrawable();
        if (null != drawable) {
            rect.set(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            mat.mapRect(rect);
        }
        return rect;
    }

    private void checkBorderAndCenterWhenScale() {
        RectF rect = getMatrixRectF();
        float delatX = 0;
        float delatY = 0;
        //控件的宽和高
        int width = getWidth();
        int height = getHeight();

        //如果图片的宽和高大于控件的宽和高 在缩放过程中会产生border 进行偏移补偿
        if (rect.width() >= width) {
            if (rect.left > 0) {
                delatX = -rect.left;
            }
            if (rect.right < width) {
                delatX = width - rect.right;
            }
        }

        if (rect.height() >= height) {
            if (rect.top > 0) {
                delatY = -rect.top;
            }
            if (rect.bottom < height) {
                delatY = height - rect.bottom;
            }
        }

        //如果图片的宽和高小于控件的宽和高 让其居中
        if (rect.width() < width) {
            delatX = width / 2 - rect.right + rect.width() / 2f;
        }
        if (rect.height() < height) {
            delatY = height / 2 - rect.bottom + rect.height() / 2f;
        }
        matrix.postTranslate(delatX, delatY);
    }


    public float getScale() {
        float[] values = new float[9];
        matrix.getValues(values);
        return values[Matrix.MSCALE_X];
    }


}
