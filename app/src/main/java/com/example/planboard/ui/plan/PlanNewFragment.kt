package com.example.planboard.ui.plan

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.planboard.R
import com.example.planboard.ui.plan.PlanFragment.Companion.date_
import com.example.planboard.ui.plan.PlanFragment.Companion.plan_
import com.example.planboard.ui.plan.PlanFragment.Companion.title_
import com.example.planboard.ui.plan.room.EntityPlan
import kotlinx.android.synthetic.main.fragment_plan_new.*

class PlanNewFragment : Fragment() {

    lateinit var planViewModel: PlanViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_plan_new, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        planViewModel = ViewModelProvider(this)[PlanViewModel::class.java]

        btn_savePlan.setOnClickListener {
            if (inputJudul.text.isEmpty() && inputRencana.text.isEmpty()){
                Toast.makeText(activity, "Judul dan Rencana harus diisi", Toast.LENGTH_SHORT).show()
            }
            else{
                btn_savePlan.isEnabled
                saveToDatabase()
                findNavController().navigate(R.id.action_planNewFragment_to_navigation_dashboard)
            }
        }
    }

    private fun saveToDatabase(){
        val plan = EntityPlan(
            title = inputJudul.text.toString(),
            plan = inputRencana.text.toString(),
            date = inputTarget.text.toString()
        )
        planViewModel.insert(plan)
    }
}