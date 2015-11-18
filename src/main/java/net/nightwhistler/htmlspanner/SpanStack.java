package net.nightwhistler.htmlspanner;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.util.Log;
import net.nightwhistler.htmlspanner.css.CompiledRule;
import net.nightwhistler.htmlspanner.style.Style;
import org.htmlcleaner.TagNode;

import java.util.*;

/**
 * Simple stack structure that Spans can be pushed on.
 *
 * Handles the lookup and application of CSS styles.
 *
 * @author Alex Kuiper
 */
public class SpanStack {

    private Stack<SpanCallback> spanItemStack = new Stack<SpanCallback>();

    private Set<CompiledRule> rules = new HashSet<CompiledRule>();

    private Map<TagNode, List<CompiledRule>> lookupCache = new HashMap<TagNode, List<CompiledRule>>();

    public void registerCompiledRule(CompiledRule rule) {
        this.rules.add( rule );
    }

    public Style getStyle( TagNode node, Style baseStyle ) {

        if ( ! lookupCache.containsKey(node) ) {

            List<CompiledRule> matchingRules = new ArrayList<CompiledRule>();
            for ( CompiledRule rule: rules ) {
                if ( rule.matches(node)) {
                    matchingRules.add(rule);
                }
            }

            lookupCache.put(node, matchingRules);
        }

        Style result = baseStyle;

        for ( CompiledRule rule: lookupCache.get(node) ) {

            Style original = result;
            result = rule.applyStyle(result);

        }

        return result;
    }

    private static String option( String s ) {
        if ( s == null ) {
            return "";
        } else {
            return s;
        }
    }

    public void pushSpan( final Object span, final int start, final int end ) {

        if ( end > start ) {
            SpanCallback callback = new SpanCallback() {
                @Override
                public void applySpan(HtmlSpanner spanner, SpannableStringBuilder builder) {
                    builder.setSpan(span, start, end,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            };

            spanItemStack.push(callback);
        }
    }

    public void pushSpan( SpanCallback callback ) {
        spanItemStack.push(callback);
    }

    public void applySpans(HtmlSpanner spanner, SpannableStringBuilder builder ) {
        while ( ! spanItemStack.isEmpty() ) {
            spanItemStack.pop().applySpan(spanner, builder);
        }
    }



}