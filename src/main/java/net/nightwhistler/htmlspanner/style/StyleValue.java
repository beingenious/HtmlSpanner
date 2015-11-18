package net.nightwhistler.htmlspanner.style;

import android.util.Log;
import net.nightwhistler.htmlspanner.HtmlSpanner;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 6/29/13
 * Time: 9:48 AM
 * To change this template use File | Settings | File Templates.
 */
public class StyleValue {

    public static enum Unit { PX, EM, PERCENTAGE, PS };

    private Integer intValue;
    private Float floatValue;

    private Unit unit;

    public static StyleValue parse( String value ) {

        if ( value.equals("0") ) {
            return new StyleValue(0f, Unit.EM);
        }

        if ( value.endsWith("px") ) {

            try {
                final Integer intValue = Integer.parseInt( value.substring(0, value.length() -2) );
                return new StyleValue(intValue);
            } catch (NumberFormatException nfe ) {
                return null;
            }
        }

        if ( value.endsWith("ps") ) {

            try {
                final Float floatValue = Float.parseFloat(value.substring(0, value.length() - 2));
                return new StyleValue(floatValue, Unit.PS);
            } catch (NumberFormatException nfe ) {
                return null;
            }
        }


        if ( value.endsWith("%") ) {
            try {
                final float percentage = Float.parseFloat( value.substring(0, value.length() -1 ) );
                final float floatValue = Math.round(percentage) / 100f;

                return new StyleValue(floatValue, Unit.PERCENTAGE);
            } catch ( NumberFormatException nfe ) {
                return null;
            }
        }

        if ( value.endsWith("em") ) {
            try {
                final Float number = Float.parseFloat(value.substring(0, value.length() - 2));
                return new StyleValue(number, Unit.EM);
            } catch ( NumberFormatException nfe ) {
                return null;
            }
        }

        return null;
    }

    public StyleValue( int intValue ) {
        this.unit = Unit.PX;
        this.intValue = intValue;
    }

    public StyleValue( float floatValue, Unit unit ) {
        this.floatValue = floatValue;
        this.unit = unit;
    }

    public int getIntValue() {
        return this.intValue;
    }

    public float getFloatValue() {
        return this.floatValue;
    }

    public Unit getUnit() {
        return this.unit;
    }

    @Override
    public String toString() {
        if ( intValue != null ) {
            return "" + intValue + this.unit;
        } else {
            return "" + floatValue + this.unit;
        }
    }
}
