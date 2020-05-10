package ru.skillbranch.devintensive.models

import android.graphics.Color

class Bender(var status: Status = Status.NORMAL, var question: Question = Question.NAME) {

    fun askQuestion(): String = when (question) {
        Question.NAME -> Question.NAME.question
        Question.PROFESSION -> Question.PROFESSION.question
        Question.MATERIAL -> Question.MATERIAL.question
        Question.BDAY -> Question.BDAY.question
        Question.SERIAL -> Question.SERIAL.question
        Question.IDLE -> Question.IDLE.question
    }



    fun listenAnswer(answer: String) : Pair<String, Triple<Int,Int,Int>>{
        return if (question.answers.contains(answer)){
            if(question != Question.IDLE){
                question = question.nextQuestion()
                "Отлично - это правильный ответ!\n ${question.question}" to status.color
            }else{
                "Отлично - ты справился\nНа этом все, вопросов больше нет" to status.color
            }
        }else{
           val check = question.check(answer)
           if (check == null) {
               if (status != Status.CRITICAL) {
                   status = status.nextStatus()
                   "Это не правильный ответ! \n ${question.question}" to status.color
               } else {
                   status = Status.NORMAL
                   question = Question.NAME
                   "Это неправильный ответ. Давай все по новой\n${question.question}" to status.color
               }
           }else{
               check + "\n ${question.question}" to status.color
           }
        }


    }

    enum class Status(val color: Triple<Int,Int,Int>) {
        NORMAL(Triple(255, 255, 255)),
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 0, 0));

        fun nextStatus() :Status{
            return if(this.ordinal < values().lastIndex){
                values()[this.ordinal +1]
            }else{
                values()[0]
            }
        }

    }



    enum class Question(val question: String, val answers: List<String>) {
        NAME("Как меня зовут?", listOf("Бендер", "Bender")){
            override fun nextQuestion(): Question = PROFESSION

            override fun check(answer: String):String?{
                return if(answer.getOrNull(0)?.isLetter() != null && answer.getOrNull(0) == answer.getOrNull(0)?.toUpperCase()){
                    null
                }else{
                    "Имя должно начинаться с заглавной буквы"
                }
            }
        },
        PROFESSION("Назови мою профессию?", listOf("сгибальщик", "bender")){
            override fun nextQuestion(): Question = MATERIAL

            override fun check(answer: String):String?{
                return if(answer.getOrNull(0)?.isLetter() != null && answers.getOrNull(0) == answers.getOrNull(0)?.toLowerCase()){
                    null
                }else{
                    "Профессия должна начинаться со строчной буквы"
                }
            }
        },
        MATERIAL("Из чего я сделан?", listOf("металл", "дерево", "metal", "iron", "wood")){
            override fun nextQuestion(): Question = BDAY

            override fun check(answer: String):String?{
                for(char in answer.toCharArray()){
                    if(char.isDigit()){
                        return "Материал не должен содержать цифр"
                    }
                }
                return null
            }
        },
        BDAY("Когда меня создали?", listOf("2993")){
            override fun nextQuestion(): Question = SERIAL

            override fun check(answer: String):String?{
                for(char in answer.toCharArray()){
                    if(char.isLetter()){
                        return "Год моего рождения должен содержать только цифры"
                    }
                }
                return null
            }
        },
        SERIAL("Мой серийный номер?", listOf("2716057")){
            override fun nextQuestion(): Question = IDLE

            override fun check(answer: String):String?{
                for(char in answer.toCharArray()){
                    if(char.isLetter()){
                        return "Серийный номер содержит только цифры, и их 7"
                    }
                }
                return null
            }
        },
        IDLE("На этом все, вопросов больше нет", listOf()){
            override fun nextQuestion(): Question = IDLE

            override fun check(answer: String) : String? = null
        };

        abstract fun nextQuestion(): Question

        abstract fun check(answer: String): String?

    }
}