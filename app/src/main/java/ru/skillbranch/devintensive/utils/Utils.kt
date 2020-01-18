package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullName:String?):Pair<String?, String?>{

        if (fullName == "" || fullName == " ") return null to null
        val parts : List<String>? = fullName?.split(" ")

        val firstName = parts?.getOrNull(0)
        val lastName = parts?.getOrNull(1)
        return firstName to lastName
    }

    fun transliteration(payload: String, divider:String = " "): String {

        val map = mapOf(
            'а' to "a",
            'б' to "b",
            'в' to "v",
            'г' to "g",
            'д' to "d",
            'е' to "e",
            'ё' to "e",
            'ж' to "zh",
            'з' to "z",
            'и' to "i",
            'й' to "i",
            'к' to "k",
            'л' to "l",
            'м' to "m",
            'н' to "n",
            'о' to "o",
            'п' to "p",
            'р' to "r",
            'с' to "s",
            'т' to "t",
            'у' to "u",
            'ф' to "f",
            'х' to "h",
            'ц' to "c",
            'ч' to "ch",
            'ш' to "sh",
            'щ' to "sh'",
            'ъ' to "",
            'ы' to "i",
            'ь' to "",
            'э' to "e",
            'ю' to "yu",
            'я' to "ya")
        var translit = ""
            payload.forEach {
                if (map[it] == null){
                    if (map[it.toLowerCase()] == null){
                        if(it == ' '){
                            translit += divider
                        }else{
                            translit += it
                        }
                    }else{
                        translit += map[it.toLowerCase()].toString().capitalize()
                    }
                }else {
                    translit += map[it].toString()
                }
            }

        return translit
    }

    fun toInitials(firstName:String?, lastName:String?): String? {

        val initials : String

        if ((firstName == "" || firstName == " ") &&  (lastName == "" || lastName == " ")) return null
        else if (firstName == "" || firstName == " " || firstName == null) initials = "${lastName?.toUpperCase()?.getOrNull(0)}"
        else if(lastName == "" || lastName == " " || lastName == null) initials = "${firstName?.toUpperCase()?.getOrNull(0)}"
        else initials = "${firstName?.toUpperCase()?.getOrNull(0)}" +
                "${lastName?.toUpperCase()?.getOrNull(0)}"

        return initials
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

}