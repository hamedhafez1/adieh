package ketab.adieh

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat

class MySpinnerAdapter constructor(context: Context, resource: Int, items: List<String>)
    : ArrayAdapter<String>(context, resource, items) {

    // Affects default (closed) state of the spinner
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent) as TextView
        view.setTextColor(ContextCompat.getColor(super.getContext(), R.color.colorPrimaryDark))
        return view
    }

    // Affects opened state of the spinner
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent) as TextView
        view.setTextColor(ContextCompat.getColor(super.getContext(), R.color.colorPrimaryDark))
        view.setBackgroundColor(Color.parseColor("#f2eecb"))
        return view
    }

}