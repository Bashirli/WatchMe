package com.bashirli.watchme

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import com.bashirli.watchme.R

@BindingAdapter("app:setImageWithResource")
fun setImage(view: ImageView,imageResource:Int){
    view.setImageResource(imageResource)
}


fun onBackPressedActionFragment(activity:FragmentActivity,view: View){
    activity.onBackPressedDispatcher.addCallback(object: OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
            view.findNavController().popBackStack()
        }
    })
}


class CustomProgressBar(){
    companion object{
        fun progressDialog(context: Context): Dialog {
            val dialog=Dialog(context)
            val layout=LayoutInflater.from(context).inflate(R.layout.loading_layout,null)
            dialog.setContentView(layout)
            dialog.setCancelable(false)
                dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            return dialog
        }
    }

}

@BindingAdapter("app:setDescriptionSpecial")
fun setDescription(view:TextView,text:String){
    if(text.length>146){
        view.setText(text.substring(0,146)+"...")
    }else{
        view.setText(text)
    }
}



