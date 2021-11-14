package com.newagedevs.bdbusroute.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.app.ShareCompat
import androidx.core.content.ContextCompat.startActivity
import com.newagedevs.bdbusroute.BuildConfig
import com.newagedevs.bdbusroute.R
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.dialogue_warning.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

fun <R> CoroutineScope.executeAsyncTask(
    onPreExecute: () -> Unit,
    doInBackground: () -> R,
    onPostExecute: (R) -> Unit
) = launch {
    onPreExecute()
    val result = withContext(Dispatchers.IO) {
        doInBackground()
    }
    onPostExecute(result)
}

fun getJsonDataFromAsset(context: Context, fileName: String): String? {
    val jsonString: String
    try {
        jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
    } catch (ioException: IOException) {
        ioException.printStackTrace()
        return null
    }
    return jsonString
}

fun Context.toast(message: CharSequence) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
fun Context.toastySuccess(message: CharSequence) = Toasty.success(this, message, Toast.LENGTH_SHORT, true).show()
fun Context.toastyError(message: CharSequence) = Toasty.error(this, message, Toast.LENGTH_SHORT, true).show()
fun Context.toastyInfo(message: CharSequence) = Toasty.info(this, message, Toast.LENGTH_SHORT, true).show()
fun Context.toastyWarning(message: CharSequence) = Toasty.warning(this, message, Toast.LENGTH_SHORT, true).show()

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}

fun getApplicationVersion():String {
    val versionName: String = BuildConfig.VERSION_NAME
    //val versionCode: Int = BuildConfig.VERSION_CODE
    return "Version: $versionName"
}

fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun shareTheApp(context: Context) {
    ShareCompat.IntentBuilder.from((context as Activity)).setType("text/plain")
        .setChooserTitle("Chooser title")
        .setText("http://play.google.com/store/apps/details?id=" + context.packageName)
        .startChooser()
}

fun openMailApp(context: Context, mail: Array<String>) {
    try {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:")
        intent.putExtra(Intent.EXTRA_EMAIL, mail)
        intent.putExtra(Intent.EXTRA_SUBJECT, "App feedback")
        startActivity(context, intent, null)
    } catch (ex: ActivityNotFoundException) {
        Toast.makeText(
            context,
            "There are no email app installed on your device",
            Toast.LENGTH_SHORT
        ).show()
    }
}

fun openAppStore(context: Context, link: String) {
    try {
        startActivity(context, Intent(Intent.ACTION_VIEW, Uri.parse(link)), null)
    } catch (e: ActivityNotFoundException) {
        startActivity(context, Intent(Intent.ACTION_VIEW, Uri.parse(link)), null)
    }
}

fun showRatingDialogue(context: Context, layoutInflater: LayoutInflater) {

    val dialogBuilder = AlertDialog.Builder(context)
    val dialogView = layoutInflater.inflate(R.layout.dialogue_rating, null)
    dialogBuilder.setView(dialogView)

    val dialogueView = dialogView.findViewById<CardView>(R.id.rating_dialogue_containers)

    val closeButton = dialogView.findViewById<ImageView>(R.id.rating_close_button)
    val ratingWarnings = dialogView.findViewById<TextView>(R.id.rating_warning)
    val ratingBar = dialogView.findViewById<RatingBar>(R.id.rating_bar)

    ratingWarnings.visibility = View.INVISIBLE

    val alertDialog = dialogBuilder.create()
    val animPopUp = AnimationUtils.loadAnimation(context, android.R.anim.fade_in)
    dialogueView.startAnimation(animPopUp)

    alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    alertDialog.setCanceledOnTouchOutside(true)
    alertDialog.show()

    closeButton.setOnClickListener{
        alertDialog.dismiss()
    }

    ratingBar.onRatingBarChangeListener = RatingBar.OnRatingBarChangeListener { _: RatingBar, value: Float, _: Boolean ->
        if(value <= 3){
            ratingBar.visibility = View.INVISIBLE
            ratingWarnings.visibility = View.VISIBLE
        }else{
            openAppStore(context, Constants.appStoreId)
            alertDialog.dismiss()
        }
    }

}

fun showDevelopmentDialogue(context: Context, layoutInflater: LayoutInflater) {

    val dialogBuilder = AlertDialog.Builder(context)
    val dialogView = layoutInflater.inflate(R.layout.dialogue_development, null)
    dialogBuilder.setView(dialogView)

    val dialogueView = dialogView.findViewById<CardView>(R.id.dev_containers)

    val closeButton = dialogView.findViewById<ImageView>(R.id.dev_close_button)

    val alertDialog = dialogBuilder.create()
    val animPopUp = AnimationUtils.loadAnimation(context, android.R.anim.fade_in)
    dialogueView.startAnimation(animPopUp)

    alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    alertDialog.setCanceledOnTouchOutside(true)
    alertDialog.show()

    closeButton.setOnClickListener{
        alertDialog.dismiss()
    }

}


fun showWarningMessage(context: Context,) {
    val dialogBuilder = AlertDialog.Builder(context)
    val dialogView = LayoutInflater.from(context).inflate(R.layout.dialogue_warning, null)
    dialogBuilder.setView(dialogView)

    val dialogueView = dialogView.warning_containers
    val closeButton = dialogView.warning_close_button
    val alertDialog = dialogBuilder.create()
    val animPopUp = AnimationUtils.loadAnimation(context, android.R.anim.fade_in)
    dialogueView.startAnimation(animPopUp)

    alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    alertDialog.setCanceledOnTouchOutside(true)
    alertDialog.show()

    closeButton.setOnClickListener{
        alertDialog.dismiss()
    }

}