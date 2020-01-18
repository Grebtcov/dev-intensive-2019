package ru.skillbranch.devintensive.extensions

fun String.truncate(value: Int = 16, str : String? = null) : String?{
    var str = this.toString()
    if (str == null) return null
    if(str.length < value) return str
    var strRes = ""

    for(i in 0..(value-1)){
        strRes += str[i]
    }

    strRes = strRes.trim() + "..."

    return strRes
}

fun String.stripHtml(str : String? = null) : String?{
    var str = this.toString()
    if (str == null) return null

    str = str.replace("&","")
    str = str.replace("'","")
    str = str.replace("\"","")

    var strRes = str
    var start = -1

    for (i in 0..(str.length-1)){
        if (str[i] == '<' && start == -1) start = i
        if (str[i] == '>' && start != -1){
            strRes = strRes.replace(str.substring(start,i+1),"")
            start = -1
        }
    }

    return strRes.replace("\\s+".toRegex(), " ")

}