package com.example.planboard.ui.plan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.planboard.PlanboardActivity
import com.example.planboard.R
import com.example.planboard.util.ViewModelFactory
import com.example.planboard.ui.plan.room.EntityPlan
import kotlinx.android.synthetic.main.activity_planboard.*
import kotlinx.android.synthetic.main.fragment_plan_new.*
import timber.log.Timber

class PlanNewFragment : Fragment() {
    companion object{
        const val EXTRA_ID = "id"
    }

    lateinit var planViewModel: PlanViewModel
    private var newId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_plan_new, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        planViewModel = ViewModelProvider(requireActivity(), ViewModelFactory.getInstance(requireActivity().application)).get(PlanViewModel::class.java)
        newId = requireArguments().getInt(EXTRA_ID)
        Timber.tag("newId:").d(newId.toString())
        btn_savePlan.setOnClickListener {
            if (inputJudul.text.isEmpty() && inputRencana.text.isEmpty()){
                Toast.makeText(activity, "Judul dan Rencana harus diisi", Toast.LENGTH_SHORT).show()
            }
            else{
                saveToDatabase()
                requireActivity().onBackPressed()
            }
        }

        if (activity is PlanboardActivity){
            val planboardActivity = activity as PlanboardActivity
            planboardActivity.nav_view.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (activity is PlanboardActivity){
            val planboardActivity = activity as PlanboardActivity
            planboardActivity.nav_view.visibility = View.VISIBLE
        }
    }

    private fun saveToDatabase(){
            val plan = EntityPlan(
                id = newId,
                title = inputJudul.text.toString(),
                plan = inputRencana.text.toString(),
                date = inputTarget.text.toString()
            )
        planViewModel.insert(plan)
    }
}