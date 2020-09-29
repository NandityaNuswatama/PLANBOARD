package com.example.planboard.ui.plan

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
import com.example.planboard.ui.plan.room.EntityPlan
import kotlinx.android.synthetic.main.activity_planboard.*
import kotlinx.android.synthetic.main.fragment_plan_edit.*
import timber.log.Timber


class PlanEditFragment : Fragment(), View.OnClickListener {
    companion object{
        var id_ = "0"
        var title_ = "title"
        var plan_ = "plan"
        var date_ = "date"
    }

    lateinit var entityPlan: List<EntityPlan>

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
        val entityPlan = EntityPlan(
            id = requireArguments().getInt(id_),
            title = inputEditJudul.text.toString(),
            plan = inputEditRencana.text.toString(),
            date = inputEditTarget.text.toString()
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
}