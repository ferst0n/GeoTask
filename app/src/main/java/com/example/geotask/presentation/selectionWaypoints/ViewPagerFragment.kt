package com.example.geotask.presentation.selectionWaypoints

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.geotask.R
import com.example.geotask.presentation.PassDataBetweenFragmentsViewModel
import com.example.geotask.presentation.selectionWaypoints.endPoint.EndPointFragment
import com.example.geotask.presentation.selectionWaypoints.startPoint.StartPointFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class ViewPagerFragment : Fragment() {

    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: ViewPagerAdapter
    private lateinit var tabLayout: TabLayout
    private lateinit var findPathBttn: Button
    private  var mContext: Context? = null

    private val passDataBetweenFragmentsViewModel: PassDataBetweenFragmentsViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_view_pager, container, false)

        viewPager = view.findViewById<ViewPager2>(R.id.viewPager)
        viewPager.isUserInputEnabled = false

        findPathBttn = view.findViewById(R.id.btnFindPath)

        tabLayout = view.findViewById(R.id.tab_TabLayout)

        val fragmentList = arrayListOf<Fragment>(

                StartPointFragment(),
                EndPointFragment()
        )


        adapter = ViewPagerAdapter(
                fragmentList,
                childFragmentManager,
                lifecycle
        )

        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager){ tab, position ->
            when(position){
                0 -> tab.text = "From"
                1-> tab.text = "To"
            }
        }.attach()

        findPathBttn.setOnClickListener(object :View.OnClickListener {

            override fun onClick(v: View?) {

                    val origin = passDataBetweenFragmentsViewModel.getIdOrigin()
                    val destination = passDataBetweenFragmentsViewModel.getIdDestination()
                    findRoute(origin, destination.toString())


            }

        })

        return view
    }


    private fun findRoute(origin: String?,destination: String?){

        if (origin.isNullOrEmpty()){

            Toast.makeText(activity?.applicationContext, "Enter ORIGIN Address!", Toast.LENGTH_SHORT).show()
            return
        }else if(destination.isNullOrEmpty()){

            Toast.makeText(activity?.applicationContext, "Enter DESTINATION Address!", Toast.LENGTH_SHORT).show()
            return
        }

        findNavController().navigate(R.id.routeBuildingFragment)

    }








}