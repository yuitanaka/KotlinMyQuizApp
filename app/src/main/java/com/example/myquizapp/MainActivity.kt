package com.example.myquizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_MY_SCORE = "com.example.myquizapp.MYSCORE"
    }
    private val quizSet: MutableList<List<String>> = ArrayList()
    private var scoreText: TextView? = null
    private var qText: TextView? = null
    private var a0Button: Button? = null
    private var a1Button: Button? = null
    private var a2Button: Button? = null
    private var nextButton: Button? = null

    private var currentQuiz = 0

    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadQuizSet()

        getViews()

        setQuiz()
    }

    override fun onResume() {
        super.onResume()

        nextButton?.text = "Next"
        currentQuiz = 0
        score = 0
        setQuiz()

    }

    private fun getViews(){
        scoreText = findViewById(R.id.scoreText)
        qText = findViewById(R.id.qText)
        a0Button = findViewById(R.id.a0Button)
        a1Button = findViewById(R.id.a1Button)
        a2Button = findViewById(R.id.a2Button)
        nextButton = findViewById(R.id.nextButton)

    }

    private fun showScore(){
        scoreText?.text = "Score: $score / ${quizSet.size}"
    }

    private fun setQuiz(){
        qText?.text = quizSet[currentQuiz][0]

        val answers: MutableList<String> = ArrayList()

        for(i in 1..3){
            answers += quizSet[currentQuiz][i]
        }
        answers.shuffle()

        a0Button?.text = answers[0]
        a1Button?.text = answers[1]
        a2Button?.text = answers[2]

        a0Button?.isEnabled = true
        a1Button?.isEnabled = true
        a2Button?.isEnabled = true
        nextButton?.isEnabled = false

        showScore()
    }

    fun checkAnswer(view: View){
        val clickedButton: Button = view as Button
        val clickedAnswer: String = clickedButton.text.toString()

        if(clickedAnswer == quizSet[currentQuiz][1]){
            clickedButton.text = "○ $clickedAnswer"
            score++
        } else {
            clickedButton.text = "× $clickedAnswer"
        }
        showScore()

        a0Button?.isEnabled = false
        a1Button?.isEnabled = false
        a2Button?.isEnabled = false
        nextButton?.isEnabled = true

        currentQuiz++
        if(currentQuiz == quizSet.size){
            // show Result
            nextButton?.text = "Check Result"
        }
    }

    fun goNext(view: View){

        if(currentQuiz == quizSet.size){
            // show Result
            val intent = Intent(this, MyResult::class.java)
            intent.putExtra(EXTRA_MY_SCORE, "$score / ${quizSet.size}")
            startActivity(intent)
        } else {
            setQuiz()
        }
    }

    private fun loadQuizSet(){
        try {
            //読み込み準備
            val inputStream = assets.open("quiz.txt")
//            val bufferedReader = BufferedReader(InputStreamReader(inputStream))

            // KotlinのinputStreamはbufferedReaderメソッドを持つため、BufferedReaderを用意する必要がない
            // useはclosableなのでわざわざfinallyでcloseする必要がなくなる
            inputStream.bufferedReader().use {
                val s: List<String> = it.readLines()
                for(i in s){
                    quizSet += i.split("\t")
                }
            }
            // 1行ずつ読み込み
            // ストリームの終わりに達するとnullが戻り値になる(javaの書き方）
            //            while ((s = bufferedReader.readLine()) != null) {
            //                quizSet.add(s.split("\t"))
            //            }
        }catch(e: IOException){
                e.printStackTrace()
        }

    }
}
