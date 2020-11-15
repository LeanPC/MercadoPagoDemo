package com.example.leandropayments.ui

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

open class BaseActivity: AppCompatActivity(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job
    lateinit var job: Job

    var progressBarIsShowing: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun showProgressIndicator(): Boolean {
        if (progressIndicator != null) {
            progressBarIsShowing = true
            progressIndicator.bringToFront()
            progressIndicator.visibility = View.VISIBLE
        }
        return true
    }

    fun hideProgressIndicator(): Boolean {
        if (progressIndicator != null) {
            progressBarIsShowing = false
            runOnUiThread { progressIndicator.visibility = View.GONE }
            return true
        }
        return false
    }

    fun showErrorToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        outState!!.putBoolean("progressbarIsShowing", progressBarIsShowing)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        if (savedInstanceState != null && savedInstanceState.containsKey("progressbarIsShowing")) {
            progressBarIsShowing = savedInstanceState.getBoolean("progressbarIsShowing")
            if(progressBarIsShowing){
                showProgressIndicator()
            } else {
                hideProgressIndicator()
            }
        }
    }
}