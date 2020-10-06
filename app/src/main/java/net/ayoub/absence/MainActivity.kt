package net.ayoub.absence

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Toast.makeText(this,"Welcome",Toast.LENGTH_SHORT).show()
        importbtn.setOnClickListener{
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "text/*"
            startActivityForResult(intent, REQUEST_CODE)
        }
        listClassBtn.setOnClickListener{
            startActivity(Intent(this,FiliereActivity::class.java))
        }
        about.setOnClickListener{
            startActivity(Intent(this,AboutActivity::class.java))
        }
        settings.setOnClickListener{
            startActivity(Intent(this,SettingsActivity::class.java))
        }
    }
    public override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        super.onActivityResult(requestCode, resultCode, resultData)
        val dBHandler=DBHandler(this)
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            resultData?.let { intent ->
                   intent.data?.let { CSVreader(it).forEach{
                    val id=dBHandler.addStudent(it)
                       if(id>0){
                           println("data added")
                       }
                       else{
                           Toast.makeText(this,"fail to add record",Toast.LENGTH_LONG).show()
                       }
                   } }
            }
        }
        startActivity(Intent(this,FiliereActivity::class.java))
    }
    @Throws(IOException::class)
    fun CSVreader(uri: Uri): ArrayList<Student> {
        val file = contentResolver.openInputStream(uri)
        val isr = InputStreamReader(file)
        var container: List<String> = ArrayList<String>()
        var reader: BufferedReader = BufferedReader(isr)
        var listStudent= arrayListOf<Student>()
        var line:String?=null

        try {
            reader.readLine()
            while ({ line = reader.readLine(); line }() != null) {
                container = line!!.split(";")
                var student: Student = Student()
                student.cne=container[1]
                student.firstName= container[2]
                student.lastName= container[3]
                student.filiere = container[4]
                listStudent.add(student)
            }}catch (ex: Exception){
            print(ex.message)
        }
        return listStudent
    }
}
