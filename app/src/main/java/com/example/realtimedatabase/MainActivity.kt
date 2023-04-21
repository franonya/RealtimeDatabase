package com.example.realtimedatabase

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.realtimedatabase.ui.theme.RealtimeDatabaseTheme
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RealtimeDatabaseTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    RealtimeDatabase()
                }
            }
        }
    }
}

@Composable
fun RealtimeDatabase() {
    val context= LocalContext.current
    val database = Firebase.database
    val myRef = database.getReference("Students")

    var rollno by remember{ mutableStateOf("") }
    var sname by remember{ mutableStateOf("") }
    var course by remember{ mutableStateOf("") }
    var check by remember{ mutableStateOf<Boolean>(false) }
    var result by remember{ mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Text(text = " Student Information",
            fontSize = 18.sp,fontWeight= FontWeight.Bold,
        color= Color.Red)
        Spacer(modifier = Modifier.padding(10.dp))
        OutlinedTextField(
            value =rollno ,
            onValueChange ={rollno=it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            placeholder={ Text(text = "Enter student roll no",fontSize=14.sp)}
        )
        Spacer(modifier = Modifier.padding(10.dp))
        OutlinedTextField(
            value =sname ,
            onValueChange ={sname=it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            placeholder={ Text(text = "Enter student name",fontSize=14.sp)}
        )
        Spacer(modifier = Modifier.padding(10.dp))
        OutlinedTextField(
            value =course ,
            onValueChange ={course=it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            placeholder={ Text(text = "Enter student course",fontSize=14.sp)}
        )
        Spacer(modifier = Modifier.padding(20.dp))
        Row(modifier = Modifier.padding(5.dp)) {
            OutlinedButton(onClick = {
                if(rollno.isNotEmpty() && sname.isNotEmpty() && course.isNotEmpty())
                {
                    val sinfo=StudentInfo(rollno.toInt(),sname,course)
                    myRef.child(sname).setValue(sinfo).addOnSuccessListener {
                        rollno=""
                        sname=""
                        course=""
                        Toast.makeText(context,"Record Inserted successfully",Toast.LENGTH_SHORT).show()

                    }.addOnFailureListener {
                        Toast.makeText(context,it.toString(),Toast.LENGTH_SHORT).show()

                    }
                }else
                {
                    Toast.makeText(context,"Please enter all the info",Toast.LENGTH_SHORT).show()
                }
            }) {
                Text(text = "Insert",fontSize=16.sp)
            }
            OutlinedButton(onClick = { /*TODO*/ }) {
                Text(text = "Display",fontSize=16.sp)
            }
            OutlinedButton(onClick = { /*TODO*/ }) {
                Text(text = "Update",fontSize=16.sp)
            }
            OutlinedButton(onClick = { /*TODO*/ }) {
                Text(text = "Delete",fontSize=16.sp)
            }
        }
        Spacer(modifier = Modifier.padding(10.dp))
        AnimatedVisibility(visible = check,modifier=Modifier.fillMaxWidth()) {
            Text(text = result,fontSize=14.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RealtimeDatabaseTheme {
        RealtimeDatabase()
    }
}