package com.example.leandropayments.components

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.RelativeLayout

class IndicatorRelativeLayout : RelativeLayout {
    /**
     * Indicates the interaction is allowed
     * @return true if allowed
     */
    /**
     * Sets the interaction lock
     * @param allowInteraction true if allowed
     */
    /**
     * Flag to know if needs to allow or disable interaction
     */
    var isAllowInteraction = false

    /**
     * Constructor
     * @param context the context
     */
    constructor(context: Context) : super(context) {
        init(context)
    }

    /**
     * Constructor
     * @param context the context
     * @param attrs the attributes
     */
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    /**
     * Constructor
     * @param context the context
     * @param attrs the attributes
     * @param defStyle the default style
     */
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(context)
    }

    /**
     * Initializes the component
     * @param context the context
     */
    private fun init(context: Context) {}

    /**
     * {@inheritDoc}
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (isAllowInteraction) {
            super.onTouchEvent(event)
        } else true
    }
}
