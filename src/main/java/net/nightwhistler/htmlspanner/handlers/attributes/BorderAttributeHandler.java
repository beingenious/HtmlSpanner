package net.nightwhistler.htmlspanner.handlers.attributes;

import android.text.SpannableStringBuilder;
import android.util.Log;

import net.nightwhistler.htmlspanner.FontFamily;
import net.nightwhistler.htmlspanner.SpanStack;
import net.nightwhistler.htmlspanner.TagNodeHandler;
import net.nightwhistler.htmlspanner.css.CSSCompiler;
import net.nightwhistler.htmlspanner.handlers.StyledTextHandler;
import net.nightwhistler.htmlspanner.spans.BorderSpan;
import net.nightwhistler.htmlspanner.style.Style;
import org.htmlcleaner.TagNode;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 6/23/13
 * Time: 3:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class BorderAttributeHandler extends WrappingStyleHandler {

    public BorderAttributeHandler(StyledTextHandler handler) {
        super(handler);
    }

    @Override
    public void handleTagNode(TagNode node, SpannableStringBuilder builder, int start, int end,
                              Style useStyle, SpanStack spanStack) {

        if ( node.getAttributeByName("border") != null ) {
//            Log.d("BorderAttributeHandler", "Adding BorderSpan from " + start + " to " + end);
            spanStack.pushSpan(new BorderSpan(useStyle, start, end, getSpanner().isUseColoursFromStyle() ), start, end);
        }

        if ( getSpanner().isAllowStyling() ) {

            String lineHeight = node.getAttributeByName("lineHeight");

            if (lineHeight != null) {
                CSSCompiler.StyleUpdater updater = CSSCompiler.getStyleUpdater("line-height", lineHeight);

                if (updater != null) {
                    useStyle = updater.updateStyle(useStyle, getSpanner());
                }
            }
        }

        super.handleTagNode(node, builder, start, end, useStyle, spanStack);

    }


}
