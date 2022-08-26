package com.example.quiz

object Constants {
    const val USER_NAME:String="user_name"
    const val TOTAL_QUESTIONS: String="total_questions"
    const val CORRECT_ANSWERS: String="correct_answers"
    const val GENRE: String="genre"
    const val RATING: String="0"

    fun getQuestions(): ArrayList<Question>{
        val questionsList=ArrayList<Question>() //Empty initially

        val que1=Question(1,"Which car company's logo is this?",
            R.drawable.audi,"Audi","Tata","BMW","Jaguar",1,"Audi")
        questionsList.add(que1)

        val que2=Question(2,"Which car company's logo is this?",
        R.drawable.ferrari,"Audi","Lamborghini","Bentley","Ferrari",4,"Ferrari")
        questionsList.add(que2)

        val que3=Question(3,"Which car company's logo is this?",
            R.drawable.tesla,"Audi","Rolls Royce","Land Rover","Tesla",4,"Tesla")
        questionsList.add(que3)

        val que4=Question(4,"Which car company's logo is this?",
            R.drawable.jaguar,"Ferrari","Mazda","Jaguar","BMW",3,"Jaguar")
        questionsList.add(que4)

        val que5=Question(5,"Which car company's logo is this?",
            R.drawable.merc,"Toyota","Mercedes","Ford","BMW",2,"Mercedes")
        questionsList.add(que5)

        val que6=Question(6,"Which car company's logo is this?",
        R.drawable.toyota,"Tata","Volkswagon","Mazda","Toyota",4,"Toyota")
        questionsList.add(que6)

        val que7=Question(7,"Which car company's logo is this?",
            R.drawable.tata,"Tata","Skoda","Mahindra","Chevrolet",1,"Tata")
        questionsList.add(que7)

        val que8=Question(8,"Which car company's logo is this?",
            R.drawable.hyundai,"Honda","Maruti Suzuki","Hyundai","BMW",3,"Hyundai")
        questionsList.add(que8)

        val que9=Question(9,"Which car company's logo is this?",
            R.drawable.ren,"Dodge","Renault","Ford","Tata",2,"Renault")
        questionsList.add(que9)

        val que10=Question(10,"Which car company's logo is this?",
            R.drawable.skoda,"Maruti Suzuki","Toyota","Fiat","Skoda",4,"Skoda")
        questionsList.add(que10)

        return questionsList
    }
    fun getQuestion(): ArrayList<Question>{
        val questionsList2=ArrayList<Question>()
        val ques1=Question(1,
            "Who is the President of the United States Of America?",R.drawable.us,
            "Barack Obama","Donald Trump","Joe Biden","Kamla Harris",3,"Joe Biden")
        questionsList2.add(ques1)

        val ques2=Question(2,"Who was the leading Run Scorer in Indian Premier League in the year 2022?",R.drawable.ipl,
            "KL Rahul","Jos Butler","Faf du Plessis","Ruturaj Gaikwad",2,"Jos Butler")

        questionsList2.add(ques2)

        val ques3=Question(3,"Which is considered to be the biggest Airline Company in India?",R.drawable.airplane,"Vistara",
            "Air India","Spice Jet","Indigo",4,"Indigo")
        questionsList2.add(ques3)

        val ques4=Question(4,"Which of them is not considered under National Capital Region",R.drawable.ncr,
            "Noida", "Ghaziabad", "Goregaon","Meerut",3,"Goregaon")

        questionsList2.add(ques4)

        val ques5=Question(5,"Which planet in the Solar System has the highest temperature?",R.drawable.solar,
            "Venus","Mercury","Saturn","Mars",1,"Venus")
        questionsList2.add(ques5)

        val que6=Question(6,"Who is the Chief Minister of Maharashtra?",
            R.drawable.maha,"Uddhav Thackeray","Devendra Fadnavis","Eknath Shinde","Sanjay Raut",3,"Eknath Shinde")
        questionsList2.add(que6)

        val que7=Question(7,"Who won Ranji Trophy season 2021-2022?",
            R.drawable.ranji,"Maharashtra","Gujarat","Mumbai","Madhya Pradesh",4,"Madhya Pradesh")
        questionsList2.add(que7)

        val que8=Question(8,"India shares boundaries with how many countries?",
            R.drawable.flag,"5","7","4","6",2,"SEVEN")
        questionsList2.add(que8)

        val que9=Question(9,"The Great Basin Desert is located in?",
            R.drawable.basin,"Australia","United States","Canada","China",2,"United States")
        questionsList2.add(que9)

        val que10=Question(10,"Which continent has most Countries?",
            R.drawable.globe,"Europe","Africa","Australia","Asia",2,"Africa")
        questionsList2.add(que10)

        return questionsList2
    }

}