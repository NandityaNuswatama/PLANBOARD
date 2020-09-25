package com.example.planboard.ui.plan

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.planboard.PlanboardActivity
import com.example.planboard.R
import com.example.planboard.ui.plan.room.EntityPlan
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_planboard.*
import kotlinx.android.synthetic.main.fragment_plan.*
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        planViewModel = ViewModelProvider(this)[PlanViewModel::class.java]

        setInitialText()

        btn_updatePlan.setOnClickListener(this)
        btn_deletePlan.setOnClickListener(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity is PlanboardActivity){
            val planboardActivity = activity as PlanboardActivity
            planboardActivity.nav_view.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (activity is PlanboardActivity){
            val planboardActivity = activity as PlanboardActivity
            planboardActivity.nav_view.visibility = View.VISIBLE
        }
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_updatePlan -> {
                showDialog(dialogTitle = "Mau ganti rencana?",
                    dialogMessage = "Rencana ini akan diganti oleh rencana yang barusan ditulis.",
                    positiveButton = "Ganti",
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
            id = id_.toInt(),
            title = inputEditJudul.text.toString(),
            plan = inputEditRencana.text.toString(),
            date = inputEditTarget.text.toString()
        )
        planViewModel.update(entityPlan)
        Timber.tag("ID: ").d(id_)
    }

    private fun deleteFromDatabase(){
        if(arguments != null) {
            planViewModel.deleteById(id = id_.toInt())
            Timber.tag("ID: ").d(id_)
        }
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
                findNavController().navigate(R.id.action_planEditFragment_to_navigation_dashboard)
            }
            setNegativeButton("Batal"){dialog, which ->
                dialog.cancel()
            }
        }
        builder.show()
    }
}