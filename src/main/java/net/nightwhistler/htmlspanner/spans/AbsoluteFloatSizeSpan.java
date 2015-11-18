package net.nightwhistler.htmlspanner.spans;

import android.os.Parcel;
import android.text.TextPaint;
import android.text.style.RelativeSizeSpan;

/**
 * Created by nicolasmuller on 16/11/15.
 */
public class AbsoluteFloatSizeSpan extends RelativeSizeSpan {

    private final float mSize;

    public AbsoluteFloatSizeSpan(float proportion) {
        super(proportion);
        mSize = proportion;
    }

    public AbsoluteFloatSizeSpan(Parcel src) {
        super(src);
        mSize = src.readFloat();
    }

    /** @hide */
    public void writeToParcelInternal(Parcel dest, int flags) {
        dest.writeFloat(mSize);
    }

    public float getSizeChange() {
        return mSize;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setTextSize(mSize);
    }

    @Override
    public void updateMeasureState(TextPaint ds) {
        ds.setTextSize(mSize);
    }
}
