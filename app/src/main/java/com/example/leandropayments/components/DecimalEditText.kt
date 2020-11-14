package com.example.leandropayments.components

import android.content.Context
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.Spanned
import android.text.method.ReplacementTransformationMethod
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatEditText
import com.example.leandropayments.Constants
import java.text.DecimalFormatSymbols

class DecimalEditText: AppCompatEditText {
        /**
         * Constructor
         *
         * @param context
         * The context for the component
         */
        constructor(context: Context?) : super(context) {
            setup(
                Constants.MAX_AMOUNT_FIELD_CHARS,
                Constants.AMOUNT_FIELD_DECIMAL_DIGITS
            )
        }

        /**
         * Constructor
         *
         * @param context
         * The context for the component
         * @param attrs
         * The attribute set
         */
        constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
            setup(
                Constants.MAX_AMOUNT_FIELD_CHARS,
                Constants.AMOUNT_FIELD_DECIMAL_DIGITS
            )
        }

        /**
         * Constructor
         *
         * @param context
         * The context for the component
         * @param attrs
         * The attribute set
         * @param defStyle
         * The def style
         */
        constructor(
            context: Context?, attrs: AttributeSet?,
            defStyle: Int
        ) : super(context, attrs, defStyle) {
            setup(
                Constants.MAX_AMOUNT_FIELD_CHARS,
                Constants.AMOUNT_FIELD_DECIMAL_DIGITS
            )
        }

        /**
         * Setup the editable component
         *
         * @param integerDigits
         * the number of digits in integral part
         * @param decimalDigits
         * the number of digits in decimal part
         */
        fun setup(integerDigits: Int, decimalDigits: Int) {

            // set edit text length filters
            val integerFilters = arrayOfNulls<InputFilter>(2)
            integerFilters[0] = LengthFilter(
                integerDigits
                        + decimalDigits + 1
            )
            integerFilters[1] = ConstrainedDecimalFilter(
                integerDigits,
                decimalDigits
            )
            this.filters = integerFilters

            // set transformation method
            this.transformationMethod = DecimalSeparatorTransformation()
        }

        /**
         * Filter that limits the number of integer digits and the number of decimal
         * digits
         */
        inner class ConstrainedDecimalFilter(
            private val maxInteger: Int,
            private val maxDecimal: Int
        ) :
            InputFilter {
            private val decimalSeparator: Char

            /**
             * Filter method that limits the length of integer part and the length
             * of decimal part, and replace the decimal point with the current
             * localized decimal separator(the EditText itself didn�t do this
             * because of a bug)
             *
             * @param source
             * Original text before the change is performed
             * @param start
             * The start position of the characters that are going to be
             * changed
             * @param end
             * The end position of the characters that are going to be
             * changed
             * @param dest
             * The text after the change is performed
             * @param dstart
             * The start position of the characters that have been
             * changed
             * @param dend
             * The end position of the characters that have been changed
             * @return CharSequence The characters replaced after the filter
             */
            override fun filter(
                source: CharSequence, start: Int, end: Int,
                dest: Spanned, dstart: Int, dend: Int
            ): CharSequence? {
                var result: CharSequence? = null
                try {
                    val newText = source.subSequence(start, end)
                    val existing = dest.toString()
                    val attempted = (existing.substring(0, dstart) + newText + existing.substring(dend))
                    var decimalPos = attempted.indexOf(".")
                    if (decimalPos == -1) {
                        decimalPos = attempted.indexOf(decimalSeparator)
                    }
                    val integer: String
                    val decimal: String
                    if (decimalPos < 0) {

                        // there's no decimals
                        decimal = ""
                        integer = attempted
                    } else if (decimalPos == 0) {

                        // number starts with decimal separator
                        integer = ""
                        decimal = attempted.substring(1)
                    } else {
                        integer = attempted.substring(0, decimalPos)
                        decimal = attempted.substring(decimalPos + 1)
                    }

                    // the integer part has exceeded its maximum length
                    if (integer.length > maxInteger) {
                        result = ""
                    } else if (decimal.length > maxDecimal) {
                        result = ""
                    }

                    // this will ensure that after introducing the maximum number of
                    // integer digits and
                    // the maximum number of decimal digits, the user is not able to
                    // delete the decimal separator
                    if (((integer.length > maxInteger)
                                && (existing.length > attempted.length))
                    ) {
                        result = existing.substring(dstart, dend)
                    }
                } catch (ex: NumberFormatException) {
                    Log.d("NumberFormatException", ex.toString())
                }
                return result
            }

            /**
             * Constructor
             *
             * @param maxInteger
             * The maximum number of integer digits
             * @param maxDecimal
             * The maximum bumber of decimal digits
             */
            init {
                val decimalFormatSymbols = DecimalFormatSymbols()
                decimalSeparator = decimalFormatSymbols.decimalSeparator
            }
        }

        /**
         * Transformation method that replaces the dot by the real decimal
         * separator(localization dependent)
         */
        inner class DecimalSeparatorTransformation() :
            ReplacementTransformationMethod() {
            /**
             * Returns the list of characters that are to be replaced by other
             * characters when displayed.
             *
             * @return char[] The list of characters that are to be replaced by
             * other characters when displayed.
             */
            override fun getOriginal(): CharArray {
                return charArrayOf('.')
            }

            /**
             * Returns a parallel array of replacement characters for the ones that
             * are to be replaced.
             *
             * @return char[] The parallel array of replacement characters for the
             * ones that are to be replaced.
             */
            override fun getReplacement(): CharArray {
                val decimalFormatSymbols = DecimalFormatSymbols()
                return charArrayOf(decimalFormatSymbols.decimalSeparator)
            }
        }

        companion object {
            /**
             * The tag
             */
            private val TAG = "DecimalEditTextComponent"
        }
}