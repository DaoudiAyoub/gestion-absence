package net.ayoub.absence

const val REQUEST_CODE = 123

const val DB_NAME="Absence"
const val DB_VERSION=1

const val TABLE="Student"
const val COL_ID="id"
const val COL_CNE="CNE"
const val COL_FIRSTNAME="firstName"
const val COL_LASTNAME="lastName"
const val COL_FILIERE="filiere"

const val TABLE2="Historique"
const val COL_DATE="date"
const val COL_OBSERVATION="observation"
const val COL_STUDENT_ID="studentId"


const val INTENT_STUDENT_FILIERE="filiere"

const val INTENT_STUDENT_ID="id"
const val INTENT_STUDENT_CNE="CNE"
const val INTENT_STUDENT_FIRSTNAME="firstName"
const val INTENT_STUDENT_LASTNAME="lastName"


val sqlCreateStudentTable = "CREATE TABLE IF NOT EXISTS "+TABLE+"("+COL_ID+" INTEGER PRIMARY KEY,"+COL_CNE+" TEXT NOT NULL UNIQUE,"+COL_FIRSTNAME+" TEXT,"+COL_LASTNAME+" TEXT,"+COL_FILIERE+" TEXT);"
val sqlCreateStudentTable2 = "CREATE TABLE IF NOT EXISTS "+TABLE2+"("+COL_ID+" INTEGER PRIMARY KEY,"+COL_DATE+" datetime DEFAULT CURRENT_TIMESTAMP,"+COL_OBSERVATION+" TEXT,"+COL_STUDENT_ID+" INTEGER);"
