package io.udemy.ageinminuteapp

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var tvSelectedDate : TextView
    private lateinit var tvResultMinute : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDatePicker : Button = findViewById(R.id.btnDatePicker)
        tvSelectedDate= findViewById(R.id.tv_selectedDate)
        tvResultMinute= findViewById(R.id.tv_resultMinute)
        btnDatePicker.setOnClickListener{dateClick()}
    }

    @SuppressLint("SetTextI18n")
    private fun dateClick (){
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(
            this, {_, selectedYear, selectedMonth, selectedDayofMonth ->
                Toast.makeText(this,
                    "Year: $selectedYear, Month: ${selectedMonth+1}, days: $selectedDayofMonth",
                    Toast.LENGTH_SHORT).show()
                tvSelectedDate.text = "$selectedDayofMonth/${selectedMonth + 1}/$selectedYear"
                // untuk mengatur waktu
                val sdf = SimpleDateFormat("dd/MM/yyy", Locale.ENGLISH)
                val theDate = sdf.parse("$selectedDayofMonth/${selectedMonth + 1}/$selectedYear")
                theDate?.let{ it ->
                    val selectedDateInMinutes = it.time/60000
                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                    currentDate?.let { current ->
                        val currentDateInMinute = current.time / 60000
                        val differenceTimeInMinute = currentDateInMinute - selectedDateInMinutes
                        tvResultMinute.text = "$differenceTimeInMinute"
                    }
                }
            }, year, month, day)
        dpd.datePicker.maxDate = System.currentTimeMillis()
        dpd.show()
    }
}