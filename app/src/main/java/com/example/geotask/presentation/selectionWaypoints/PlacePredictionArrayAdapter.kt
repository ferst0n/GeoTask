package com.example.geotask.presentation.selectionWaypoints

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.*
import androidx.annotation.LayoutRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.geotask.R
import com.example.geotask.domain.entity.PlaceName


class PlacePredictionArrayAdapter(
    context: Context,
    @LayoutRes private val layoutResource: Int,
    autoCompleteTextView: AutoCompleteTextView
): ArrayAdapter<PlaceName>(context, layoutResource, ),
    Filterable {
    var mPlaceName: ArrayList<PlaceName> = ArrayList()
    private  var autoCompleteTextView: AutoCompleteTextView = autoCompleteTextView

    fun setPlace(newPlace: List<PlaceName>){
        mPlaceName.clear()
        mPlaceName.addAll(newPlace)
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return mPlaceName.size
    }

    override fun getItem(p0: Int): PlaceName? {
        return mPlaceName.get(p0)
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = LayoutInflater.from(parent.context).inflate(layoutResource, parent, false)
//        val dropDownTextView = super.getView(position, convertView, parent) as TextView

        var textView: TextView = view.findViewById(R.id.text_view_item)
        textView.text = "${mPlaceName[position].name}"
        var constraintLayout:ConstraintLayout = view.findViewById(R.id.linear)

        textView.viewTreeObserver.addOnGlobalLayoutListener(object :
            OnGlobalLayoutListener {
            override fun onGlobalLayout() {

                autoCompleteTextView.dropDownHeight = (constraintLayout.height * 3)
                textView.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })

        return view
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(
                charSequence: CharSequence?,
                filterResults: Filter.FilterResults
            ) {
                mPlaceName = filterResults.values as ArrayList<PlaceName>
                notifyDataSetChanged()
            }

            override fun performFiltering(charSequence: CharSequence?): Filter.FilterResults {
                val queryString = charSequence?.toString()?.toLowerCase()

                val filterResults = Filter.FilterResults()
                filterResults.values = if (queryString==null || queryString.isEmpty())
                    mPlaceName
                else
                    mPlaceName.filter {
                        it.name.toLowerCase().contains(queryString)
                    }
                return filterResults
            }
        }
    }
}


