package com.example.moviesearch.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun convertDate(dateStr: String): String {
    return try{
        if (dateStr.isEmpty()){
            return ""
        }

        // Define input and output date formats
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("d MMMM, yyyy", Locale.getDefault())

        // Parse the input date string to a Date object
        val date = inputFormat.parse(dateStr)

        // Format the Date object to the desired output string
        val formattedDate = outputFormat.format(date)

        // Extract the day of the month to determine the suffix
        val calendar = Calendar.getInstance()
        if (date != null) {
            calendar.time = date
        }
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val daySuffix = getDaySuffix(day)

        // Insert the day suffix into the formatted date string
        val dayWithSuffix = day.toString() + daySuffix

        formattedDate.replaceFirst(day.toString(), dayWithSuffix)

    }catch (e: Exception){
        e.printStackTrace()
        dateStr
    }
}


private fun getDaySuffix(day: Int): String {
    return if (day in 11..13) {
        "th"
    } else when (day % 10) {
        1 -> "st"
        2 -> "nd"
        3 -> "rd"
        else -> "th"
    }
}