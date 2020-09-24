package com.example.planboard.ui.plan

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.planboard.R
import com.example.planboard.ui.plan.PlanEditFragment.Companion.date_
import com.example.planboard.ui.plan.PlanEditFragment.Companion.id_
import com.example.planboard.ui.plan.PlanEditFragment.Companion.plan_
import com.example.planboard.ui.plan.PlanEditFragment.Companion.title_
import com.example.planboard.ui.plan.room.EntityPlan
import kotlinx.android.synthetic.main.fragment_plan.*
import timber.log.Timber

class PlanFragment : Fragment() {

    private lateinit var planViewModel: PlanViewModel
    private lateinit var planAdapter: PlanAdapter
    private lateinit var plans: List<EntityPlan>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_plan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        planViewModel = ViewModelProvider(this)[PlanViewModel::class.java]
        plans = ArrayList()

        fab.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_dashboard_to_planNewFragment)
        }

        showRecyclerview()
    }

    private fun showRecyclerview(){
        planAdapter = PlanAdapter(plans)
        observeLiveData()
        rv_plan.adapter = planAdapter
        rv_plan.layoutManager = GridLayoutManager(activity, 2)

        planAdapter.setOnItemClickCallback(object : PlanAdapter.OnItemClickCallback{
            override fun onItemClicked(plan: EntityPlan) {
                val bundle = Bundle()
                bundle.putInt(id_, plan.id)
                bundle.putString(title_, plan.title)
                bundle.putString(plan_, plan.plan)
                bundle.putString(date_, plan.date)
                findNavController().navigate(R.id.action_navigation_dashboard_to_planEditFragment, bundle)
                Log.d("ID: ", id_)
            }
        })
    }

    private fun observeLiveData(){
        planViewModel.getAllPlans().observe(requireActivity(), { plans ->
            plans?.let { planAdapter.setPlans(it as ArrayList<EntityPlan>) }
        })
    }

}