package ru.skillbranch.devintensive.extensions

import ru.skillbranch.devintensive.utils.Utils
import java.text.SimpleDateFormat
import java.util.*



const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun Date.format(pattern:String="HH:mm:ss dd.MM.yy"):String{
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND) : Date{
    var time = this.time

    time += when(units){
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }

    this.time = time
    return this

}

enum class TimeUnits{
    SECOND,
    MINUTE,
    HOUR,
    DAY
}

fun TimeUnits.plural(value: Int,units: TimeUnits = TimeUnits.SECOND) : String{
    var units = this.name
    when (units){
        "SECOND" -> return "$value ${GetDeclension(value,"сенунда","секунды","cекунд")}"
        "MINUTE" -> return "$value ${GetDeclension(value,"минута","минуты","минут")}"
        "HOUR" -> return "$value ${GetDeclension(value,"час","часа","часов")}"
        "DAY" -> return "$value ${GetDeclension(value,"день","дня","дней")}"
        else -> return units
    }

}

fun GetDeclension(number : Int, nominativ: String, genetiv: String , plural:String ) : String
{
    var number = number % 100
    if ((number >= 11) && (number <= 19))
    {
        return plural;
    }
    var i = number % 10;
    when (i)
    {
        1 -> return nominativ;
        in 2..4 -> return genetiv;
        else -> return plural;
    }
}




fun Date.humanizeDiff(date:Date = Date()): String {
    var time = this.time
    var date = Date().time
    time = date - time
    time = time / 1000L

    var answer = ""
    var start = "через "
    var end = " назад"

    if (time > 31104000) return "более года назад"
    if (time < -31104000) return "более чем через год"

    if (time < 0){
        end = ""
        time += time * -2
    }else{
        start = ""
    }

    answer = when(time){
        in 0..1 -> "только что"
        in 1..45 -> "${start}несколько секунд$end"
        in 45..75 -> "${start}минуту$end"
        in 75..2700 -> "$start${time/60} ${GetDeclension((time / 60).toInt(), "минута","минуты","минут")}$end"
        in 2700..4500 -> "${start}час$end"
        in 4500..79200 -> "$start${time / 60 / 60} ${GetDeclension((time / 60 / 60).toInt(),"час","часа","часов")}$end"
        in 79200..93600 -> "${start}день$end"
        in 93600..31104000 -> "${start}${time  / 60 / 60 /24} ${GetDeclension((time  / 60 / 60 /24).toInt(),"день","дня","дней")}$end"
        else -> "нихрена не робит"
    }


    return answer
}