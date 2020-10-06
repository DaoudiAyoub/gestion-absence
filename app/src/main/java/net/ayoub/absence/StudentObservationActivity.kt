package net.ayoub.absence

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_student.*
import kotlinx.android.synthetic.main.activity_student_observation.*

class StudentObservationActivity : AppCompatActivity() {
    var studentId:Int=-1
    var studentCne:String?=null
    var studentFirstName:String=""
    var studentLastName:String=""
    var studentFiliere:String=""
    lateinit var dbHandler: DBHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_observation)
        rv_observation.layoutManager= LinearLayoutManager(this)
        dbHandler= DBHandler(this)
        refreshList()
        this.studentId =intent.getIntExtra(INTENT_STUDENT_ID,-1)
        studentCne=intent.getStringExtra(INTENT_STUDENT_CNE)
        studentFirstName=intent.getStringExtra(INTENT_STUDENT_FIRSTNAME)
        studentLastName=intent.getStringExtra(INTENT_STUDENT_LASTNAME)
        studentFiliere=intent.getStringExtra(INTENT_STUDENT_FILIERE)

        studentNameCneObs.text= studentFirstName+" "+studentLastName+" : "+studentCne
        studentFiliereObs.text= studentFiliere

        addObs.setOnClickListener{
            val dialog=AlertDialog.Builder(this)
            val view=layoutInflater.inflate(R.layout.dialogadd,null)
            val obser=view.findViewById<EditText>(R.id.observationEt)
            dialog.setView(view)
            dialog.setPositiveButton("Add",{ dialogInterface: DialogInterface, _: Int ->
                if(obser.text.isNotEmpty()){
                    val historique=Historique()
                    historique.observation=obser.text.toString()
                    historique.studentId=studentId
                    val id=dbHandler.addObservation(historique)
                    refreshList()
                    if(id>0){
                        Toast.makeText(this,"Observation added", Toast.LENGTH_LONG).show()
                    }
                    else{
                        Toast.makeText(this,"fail to add record", Toast.LENGTH_LONG).show()
                    }
                }

            })
            dialog.setNegativeButton("Cancel",{ dialogInterface: DialogInterface, _: Int -> })
            dialog.show()
        }
    }

    override fun onResume() {
        super.onResume()
        refreshList()
    }
    fun refreshList(){
        rv_observation.adapter= StudentObservationActivity.StudentObservationAdapter(this,dbHandler.getObservations(studentId))
    }

    class StudentObservationAdapter(val activity: StudentObservationActivity,val list: MutableList<Historique>) : RecyclerView.Adapter<StudentObservationAdapter.ViewHolder>(){


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val v = LayoutInflater.from(activity).inflate(R.layout.row_observation,parent,false)
            return ViewHolder(v)
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.observationTv.text=list[position].observation.toString()
            holder.date.text=list[position].date.toString()
            holder.delete.setOnClickListener{
                activity.dbHandler.deleteObservation(list[position].id)
                activity.refreshList()
            }

        }
        class ViewHolder(v: View) : RecyclerView.ViewHolder(v){
            val observationTv: TextView =v.findViewById(R.id.observationTv)
            val date: TextView =v.findViewById(R.id.dateTv)
            val delete:ImageView=v.findViewById(R.id.deleteIm)

        }
    }

}
