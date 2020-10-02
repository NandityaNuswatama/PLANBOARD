package com.example.planboard.ui.plan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.planboard.R
import com.example.planboard.ui.plan.PlanNewFragment.Companion.EXTRA_ID
import com.example.planboard.util.ViewModelFactory
import com.example.planboard.ui.plan.room.Plan
import kotlinx.android.synthetic.main.fragment_plan.*
import java.util.concurrent.atomic.AtomicInteger

class PlanFragment : Fragment() {

    private lateinit var planViewModel: PlanViewModel
    private lateinit var planAdapter: PlanAdapter
    private lateinit var counter: AtomicInteger

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_plan, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        planViewModel = ViewModelProvider(requireActivity(), ViewModelFactory.getInstance(requireActivity().application)).get(PlanViewModel::class.java)
        planAdapter = PlanAdapter(requireActivity())
        counter = AtomicInteger()
        observeLiveData()
        fab.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt(EXTRA_ID, getCount()+1)
            findNavController().navigate(R.id.action_navigation_dashboard_to_planNewFragment)
        }
        showRecyclerview()
    }

    private fun showRecyclerview(){
        if(getCount() > 0) {
            img_empty_plan.visibility = View.GONE
            tv_hint_plan.visibility = View.GONE
            planAdapter = PlanAdapter(requireActivity())
            rv_plan.adapter = planAdapter
            rv_plan.layoutManager = GridLayoutManager(activity, 2)
        }else{
            img_empty_plan.visibility = View.VISIBLE
            tv_hint_plan.visibility = View.VISIBLE
        }
    }

    private fun observeLiveData(){
        planViewModel.getAllPlans().observe(requireActivity(), { plans ->
            plans?.let { planAdapter.setPlans(it as ArrayList<Plan>) }
        })
    }

    private fun getCount(): Int{
        val t = Thread {
            val count = planViewModel.getCount()
            counter.set(count)
        }
        t.start()
        t.join()
        return counter.get()
    }
}