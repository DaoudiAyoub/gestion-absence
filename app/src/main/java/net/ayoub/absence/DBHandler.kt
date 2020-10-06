package net.ayoub.absence

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class DBHandler(val context: Context):SQLiteOpenHelper(context, DB_NAME,null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
         db!!.execSQL(sqlCreateStudentTable)
         db!!.execSQL(sqlCreateStudentTable2)
         Toast.makeText(context,"Database is created", Toast.LENGTH_LONG).show();
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("Drop Table IF EXISTS"+ TABLE)
        db!!.execSQL("Drop Table IF EXISTS"+ TABLE2)

    }

    fun addStudent(student: Student): Long {
        val db=writableDatabase
        val cv=ContentValues()
        cv.put(COL_CNE,student.cne)
        cv.put(COL_FIRSTNAME,student.firstName)
        cv.put(COL_LASTNAME,student.lastName)
        cv.put(COL_FILIERE,student.filiere)
        val result=db.insert(TABLE,null,cv)
        return result
    }

    fun getFiliere():MutableList<String>{
        val result : MutableList<String> = ArrayList()
        val db=readableDatabase
        val queryResult = db.rawQuery("SELECT DISTINCT $COL_FILIERE FROM $TABLE",null)
        if(queryResult.moveToFirst()){
            do {
                val filiere:String
                filiere=queryResult.getString(queryResult.getColumnIndex(COL_FILIERE))
                result.add(filiere)
            }while (queryResult.moveToNext())
        }
        queryResult.close()
        return result
    }

    fun getStudentByFiliere(filiere:String):MutableList<Student>{
        val result : MutableList<Student> = ArrayList()
        val db=readableDatabase
        val queryResult=db.rawQuery("SELECT * FROM $TABLE WHERE $COL_FILIERE LIKE '%$filiere%'",null)
         if (queryResult.moveToFirst()){
             do{
                 val student=Student()
                 student.id=queryResult.getInt(queryResult.getColumnIndex(COL_ID))
                 student.firstName=queryResult.getString(queryResult.getColumnIndex(COL_FIRSTNAME))
                 student.lastName=queryResult.getString(queryResult.getColumnIndex(COL_LASTNAME))
                 student.cne=queryResult.getString(queryResult.getColumnIndex(COL_CNE))
                 student.filiere=queryResult.getString(queryResult.getColumnIndex(COL_FILIERE))
                 result.add(student)
             }while (queryResult.moveToNext())
         }
        queryResult.close()
        return result
    }

    fun addObservation(historique: Historique):Long{
        val db=writableDatabase
        val cv=ContentValues()
        cv.put(COL_OBSERVATION,historique.observation)
        cv.put(COL_STUDENT_ID,historique.studentId)
        val result=db.insert(TABLE2,null,cv)
        return result
    }
    fun getObservations(studentId:Int):MutableList<Historique>{
        val result : MutableList<Historique> = ArrayList()
        val db=readableDatabase
        val queryResult=db.rawQuery("SELECT * FROM $TABLE2 WHERE $COL_STUDENT_ID=$studentId",null)
        if (queryResult.moveToFirst()){
            do{
                val historique=Historique()
                historique.id=queryResult.getInt(queryResult.getColumnIndex(COL_ID))
                historique.observation=queryResult.getString(queryResult.getColumnIndex(COL_OBSERVATION))
                historique.studentId=queryResult.getInt(queryResult.getColumnIndex(COL_STUDENT_ID))
                historique.date=queryResult.getString(queryResult.getColumnIndex(COL_DATE))
                result.add(historique)
            }while (queryResult.moveToNext())
        }
        queryResult.close()
        return result
    }

    fun deleteObservation(id: Int?){
        val db=writableDatabase
        db.delete(TABLE2,"$COL_ID=?", arrayOf(id.toString()))
    }
}