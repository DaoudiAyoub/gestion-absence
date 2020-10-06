package net.ayoub.absence

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_filiere.*
import kotlinx.android.synthetic.main.activity_student.*

class StudentActivity : AppCompatActivity() {
    var filiere:String=""
    val dBHandler=DBHandler(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student)
        rv_student.layoutManager= LinearLayoutManager(this)
         filiere=intent.getStringExtra(INTENT_STUDENT_FILIERE)
        filiereTvv.text="( "+filiere+" )"
        refreshList()

    }
    fun refreshList(){
        rv_student.adapter= StudentActivity.StudentAdapter(this, dBHandler.getStudentByFiliere(filiere))
    }

    class StudentAdapter(val context: Context, val list: MutableList<Student>) : RecyclerView.Adapter<StudentAdapter.ViewHolder>(){


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.row_student,parent,false)
            return ViewHolder(v)
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.name.text=list[position].firstName.toString()+" "+list[position].lastName
            holder.name.setOnClickListener{
                val intent=Intent(context,StudentObservationActivity::class.java)
                intent.putExtra(INTENT_STUDENT_ID,list[position].id)
                intent.putExtra(INTENT_STUDENT_CNE,list[position].cne)
                intent.putExtra(INTENT_STUDENT_FIRSTNAME,list[position].firstName)
                intent.putExtra(INTENT_STUDENT_LASTNAME,list[position].lastName)
                intent.putExtra(INTENT_STUDENT_FILIERE,list[position].filiere)
                context.startActivity(intent)
            }
            holder.imageIv.setOnClickListener{
                val intent=Intent(context,StudentObservationActivity::class.java)
                intent.putExtra(INTENT_STUDENT_ID,list[position].id)
                intent.putExtra(INTENT_STUDENT_CNE,list[position].cne)
                intent.putExtra(INTENT_STUDENT_FIRSTNAME,list[position].firstName)
                intent.putExtra(INTENT_STUDENT_LASTNAME,list[position].lastName)
                intent.putExtra(INTENT_STUDENT_FILIERE,list[position].filiere)
                context.startActivity(intent)
            }
            holder.StudentRow.setOnClickListener{
                val intent=Intent(context,StudentObservationActivity::class.java)
                intent.putExtra(INTENT_STUDENT_ID,list[position].id)
                intent.putExtra(INTENT_STUDENT_CNE,list[position].cne)
                intent.putExtra(INTENT_STUDENT_FIRSTNAME,list[position].firstName)
                intent.putExtra(INTENT_STUDENT_LASTNAME,list[position].lastName)
                intent.putExtra(INTENT_STUDENT_FILIERE,list[position].filiere)
                context.startActivity(intent)
            }

        }
        class ViewHolder(v: View) : RecyclerView.ViewHolder(v){
            val name: TextView =v.findViewById(R.id.name)
            val imageIv:ImageView=v.findViewById(R.id.imageIv)
            val StudentRow:RelativeLayout=v.findViewById(R.id.StudentRow)
        }
    }
}
