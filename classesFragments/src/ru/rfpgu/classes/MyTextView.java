package ru.rfpgu.classes;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created with IntelliJ IDEA.
 * User: AlexChe
 * Date: 10.04.14
 * Time: 23:02
 * To change this template use File | Settings | File Templates.
 */
public class MyTextView extends TextView {

    private Context context;

    public MyTextView (Context context) {
        super(context);
        this.context = context;
        Typeface tfs = Typeface.createFromAsset(context.getAssets(),
                "font/1.ttf");
        setTypeface(tfs);

    }
    public MyTextView (Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        Typeface tfs = Typeface.createFromAsset(this.context.getAssets(),
                "font/1.ttf");
        setTypeface(tfs);
    }

    public MyTextView (Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        Typeface tfs = Typeface.createFromAsset(this.context.getAssets(),
                "font/1.ttf");
        setTypeface(tfs);
    }
}
