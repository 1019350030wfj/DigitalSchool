package com.onesoft.digitaledu.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.widget.TextView;

import com.yancy.gallerypick.utils.AppUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmoticonsTextView extends TextView {
    private int size;

    public EmoticonsTextView(Context context) {
        super(context);
    }

    public EmoticonsTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public EmoticonsTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (!TextUtils.isEmpty(text)) {
            super.setText(replace(text.toString()), type);
        } else {
            super.setText(text, type);
        }
    }

    private Pattern buildPattern() {
        return Pattern.compile("[0-9]{3}", Pattern.CASE_INSENSITIVE);
    }

    private CharSequence replace(String text) {
        try {
            size = (int) AppUtils.dipToPx(getContext(), 10);
            SpannableString spannableString = new SpannableString(text);
            int start = 0;
            Pattern pattern = buildPattern();
            Matcher matcher = pattern.matcher(text);
            while (matcher.find()) {
                String faceText = matcher.group();
                String key = "p"+faceText;
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.outHeight = size;
                options.outWidth = size;
                Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(),
                        getContext().getResources().getIdentifier(key, "drawable", getContext().getPackageName()), options);
                if (bitmap == null){
                    return text;
                }
                ImageSpan imageSpan = new ImageSpan(getContext(), bitmap);
                int startIndex = text.indexOf(faceText, start)-1;
                int endIndex = startIndex + faceText.length()+2;
                if (startIndex >= 0)
                    spannableString.setSpan(imageSpan, startIndex, endIndex, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                start = (endIndex - 1);
            }
            return spannableString;
        } catch (Exception e) {
            return text;
        }
    }
}
