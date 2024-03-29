package com.example.planboard.ui.plan

import android.annotation.SuppressLint
import android.content.res.TypedArray
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.planboard.PlanboardActivity
import com.example.planboard.R
import com.example.planboard.ui.plan.room.Plan
import com.example.planboard.util.ViewModelFactory
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.android.synthetic.main.activity_planboard.*
import kotlinx.android.synthetic.main.fragment_plan_new.*
import timber.log.Timber
import java.util.*

class PlanNewFragment : Fragment() {
    companion object{
        const val EXTRA_ID = "id"
    }

    lateinit var planViewModel: PlanViewModel
    private lateinit var urgent: TypedArray
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
            if (inputJudul.text.isEmpty() && inputRencana.text.isEmpty()) {
                Toast.makeText(activity, "Judul dan Rencana harus diisi", Toast.LENGTH_SHORT).show()
            } else {
                saveToDatabase()
                requireActivity().onBackPressed()
            }
        }

        btn_date.setOnClickListener { showDatePicker() }

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
        var index = 0
        val checked = radioGroup.checkedRadioButtonId
        when(checked){
            R.id.rb_biasa -> index = 0
            R.id.rb_penting -> index = 1
            R.id.rb_sangat_penting -> index = 2
        }
        urgent = resources.obtainTypedArray(R.array.urgency)
            val plan = Plan(
                id = newId,
                title = inputJudul.text.toString(),
                plan = inputRencana.text.toString(),
                date = inputTarget.text.toString(),
                urgent = urgent.getResourceId(index, 0)
            )
        planViewModel.insert(plan)
    }

    @SuppressLint("SetTextI18n")
    private fun showDatePicker(){
        val builder = MaterialDatePicker.Builder.datePicker()
        builder.setTitleText("Pilih tanggal")
        val picker = builder.build()
        picker.addOnPositiveButtonClickListener {
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            calendar.time = Date(it)
            inputTarget.setText("${calendar.get(Calendar.DAY_OF_MONTH)}-" +
                    "${calendar.get(Calendar.MONTH) + 1}-${calendar.get(Calendar.YEAR)}")
        }
        picker.show(parentFragmentManager, picker.toString())
    }
}