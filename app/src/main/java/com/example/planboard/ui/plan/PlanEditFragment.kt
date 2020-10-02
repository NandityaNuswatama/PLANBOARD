package com.example.planboard.ui.plan

import android.annotation.SuppressLint
import android.content.res.TypedArray
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.planboard.PlanboardActivity
import com.example.planboard.R
import com.example.planboard.util.ViewModelFactory
import com.example.planboard.ui.plan.room.Plan
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.android.synthetic.main.activity_planboard.*
import kotlinx.android.synthetic.main.fragment_plan_edit.*
import kotlinx.android.synthetic.main.fragment_plan_new.*
import java.util.*

class PlanEditFragment : Fragment(), View.OnClickListener {
    companion object{
        var id_ = "0"
        var title_ = "title"
        var plan_ = "plan"
        var date_ = "date"
        var urgency_ = "1"
    }

    lateinit var entityPlan: List<Plan>
    private lateinit var urgent: TypedArray

    private val updateId = 14
    private val deleteId = 17

    lateinit var planViewModel: PlanViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        entityPlan = mutableListOf()
        return inflater.inflate(R.layout.fragment_plan_edit, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        planViewModel = ViewModelProvider(this, ViewModelFactory.getInstance(requireActivity().application)).get(PlanViewModel::class.java)

        setInitialText()

        btn_updatePlan.setOnClickListener(this)
        btn_deletePlan.setOnClickListener(this)
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

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_updatePlan -> {
                showDialog(dialogTitle = "Mau ubah rencana?",
                    dialogMessage = "Rencana ini akan diubah oleh rencana yang barusan ditulis.",
                    positiveButton = "Ubah",
                    id = updateId
                )
            }
            R.id.btn_deletePlan -> {
                showDialog(dialogTitle = "Mau hapus rencana?",
                dialogMessage = "Rencana ini akan dihapus secara permanen.",
                positiveButton = "Hapus",
                id = deleteId
                )
            }
            R.id.btn_editDate -> {
                showDatePicker()
            }
        }
    }

    private fun setInitialText(){
        if (arguments != null) {
            inputEditJudul.setText(requireArguments().getString(title_).toString())
            inputEditRencana.setText(requireArguments().getString(plan_).toString())
            inputEditTarget.setText(requireArguments().getString(date_).toString())
        }
    }

    private fun updateDatabase() {
        var index = 0
        val checked = radioGroup2.checkedRadioButtonId
        when(checked){
            R.id.rb_editBiasa -> index = 0
            R.id.rb_editPenting -> index = 1
            R.id.rb_editSangat -> index = 2
        }
        urgent = resources.obtainTypedArray(R.array.urgency)
        val entityPlan = Plan(
            id = requireArguments().getInt(id_),
            title = inputEditJudul.text.toString(),
            plan = inputEditRencana.text.toString(),
            date = inputEditTarget.text.toString(),
            urgent = urgent.getResourceId(index, 0)
        )
        planViewModel.insert(entityPlan)
    }

    private fun deleteFromDatabase(){
        val planId = requireArguments().getInt(id_)
        planViewModel.deleteById(planId)
    }

    private fun showDialog(
        dialogTitle: String,
        dialogMessage: String,
        positiveButton: String,
        id: Int){
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(dialogTitle)
        builder.setMessage(dialogMessage)
        builder.apply {
            setPositiveButton(positiveButton){ dialog, which ->
                if (id == updateId){
                    updateDatabase()
                }
                else {
                    deleteFromDatabase()
                }
                requireActivity().onBackPressed()
            }
            setNegativeButton("Batal"){dialog, which ->
                dialog.cancel()
            }
        }
        builder.show()
    }


    @SuppressLint("SetTextI18n")
    private fun showDatePicker(){
        val builder = MaterialDatePicker.Builder.datePicker()
        val picker = builder.build()
        picker.addOnPositiveButtonClickListener {
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            calendar.time = Date(it)
            inputEditTarget.setText("${calendar.get(Calendar.DAY_OF_MONTH)}-" +
                    "${calendar.get(Calendar.MONTH) + 1}-${calendar.get(Calendar.YEAR)}")
        }
        picker.show(parentFragmentManager, picker.toString())
    }
}