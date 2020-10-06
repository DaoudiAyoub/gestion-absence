package net.ayoub.absence

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_filiere.*
import kotlinx.android.synthetic.main.row_filiere.*
import kotlin.coroutines.coroutineContext

class FiliereActivity : AppCompatActivity() {
    val dBHandler=DBHandler(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filiere)
        rv_dashboard.layoutManager=LinearLayoutManager(this)
        refreshList()

    }

    private fun refreshList(){
        rv_dashboard.adapter=FiliereAdapter(this,dBHandler.getFiliere())
    }

     class FiliereAdapter(val context: Context,val list: MutableList<String>) : RecyclerView.Adapter<FiliereAdapter.ViewHolder>(){


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.row_filiere,parent,false)
            return ViewHolder(v)
        }

        override fun getItemCount(): Int {
            return list.size
         }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.filiereName.text=list[position].toString()

            holder.filiereName.setOnClickListener{
                val intent = Intent(context,StudentActivity::class.java)
                intent.putExtra(INTENT_STUDENT_FILIERE,list[position])
                context.startActivity(intent)
            }
            holder.filiereImage.setOnClickListener{
                val intent = Intent(context,StudentActivity::class.java)
                intent.putExtra(INTENT_STUDENT_FILIERE,list[position])
                context.startActivity(intent)
            }
            holder.filiereRow.setOnClickListener{
                val intent = Intent(context,StudentActivity::class.java)
                intent.putExtra(INTENT_STUDENT_FILIERE,list[position])
                context.startActivity(intent)
            }

        }
         class ViewHolder(v:View) : RecyclerView.ViewHolder(v){
             val filiereName:TextView=v.findViewById(R.id.filierTv)
             var filiereImage:ImageView=v.findViewById(R.id.imageIv)
             val filiereRow:RelativeLayout=v.findViewById(R.id.filierRow)
         }
    }

}
