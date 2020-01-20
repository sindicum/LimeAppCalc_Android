package com.example.limeappcalc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceManager
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_input_number.*

class InputNumberActivity : AppCompatActivity() {

    //初期設定
    var numLabel :String = ""
    var inputNumber :Int = 0
    var receivedValue :String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_number)

        //入力初期化
        numLabel = ""
        numberLabel.text = numLabel
        inputNumber = 0

        //入力状態の識別・入力指示ガイダンス表示・小数点ボタンの状態
        receivedValue = intent.getStringExtra("inputBtn_tag")
        when (receivedValue) {
            "presentPh" -> {
                guidanceLabel.text = "現在pHを入力して下さい"
                subguidanceLabel.text = ""
                btnPoint.setEnabled(true)
            }
            "improvedPh" -> {
                guidanceLabel.text = "改善pHを入力して下さい"
                subguidanceLabel.text = ""
                btnPoint.setEnabled(true)
            }
            "plowingDepth" -> {
                guidanceLabel.text = "耕起深(cm)を選んで下さい"
                subguidanceLabel.text = ""
                btnPoint.setEnabled(false)
            }
            "bulkDensity" -> {
                guidanceLabel.text = "仮比重を入力して下さい"
                subguidanceLabel.text = "参考：低地土・台地土1.0、火山性土0.8"
                btnPoint.setEnabled(false)
            }
            "alkalineContent" -> {
                guidanceLabel.text = "アルカリ分(%)を入力して下さい"
                subguidanceLabel.text = ""
                btnPoint.setEnabled(false)
            }
        }

        //数値入力ボタンとメソッドの紐付け
        btn0.setOnClickListener { inputNum(it) }
        btn1.setOnClickListener { inputNum(it) }
        btn2.setOnClickListener { inputNum(it) }
        btn3.setOnClickListener { inputNum(it) }
        btn4.setOnClickListener { inputNum(it) }
        btn5.setOnClickListener { inputNum(it) }
        btn6.setOnClickListener { inputNum(it) }
        btn7.setOnClickListener { inputNum(it) }
        btn8.setOnClickListener { inputNum(it) }
        btn9.setOnClickListener { inputNum(it) }
        btnPoint.setOnClickListener { inputNum(it) }

        //数値クリアボタンとメソッドの紐付け
        btnClear.setOnClickListener { clearNum(it) }

        //画面遷移ボタンとメソッドの紐付け
        nextBtn_ina.setOnClickListener { goNext(it) }
        backBtn_ina.setOnClickListener{ backBefore(it)}
        backmainBtn_ina.setOnClickListener { backMain(it) }
    }

    //数値入力メソッド
    private fun inputNum(view: View?) {
        if (view is Button) {
            val currentText = view.text

            when (receivedValue) {
                //現在pH、改善pHの入力（0-14.0）
                "presentPh","improvedPh" -> {
                    when (currentText) {
                        "." -> {
                            when (inputNumber) {
                                0 -> {
                                    numLabel = "0."
                                    btnPoint.setEnabled(false)
                                    inputNumber = 3
                                }
                                1,2 -> {
                                    numLabel += currentText
                                    btnPoint.setEnabled(false)
                                    inputNumber = 3
                                }
                                3 -> {
                                    inputNumber = 4
                                }
                            }
                        }
                        "0" -> {
                            when (inputNumber) {
                                0,1 -> {
                                    numLabel += currentText
                                    inputNumber = 2
                                }
                                3 -> {
                                    numLabel += currentText
                                    inputNumber = 4
                                }
                            }
                        }
                        "1" -> {
                            when (inputNumber) {
                                0 -> {
                                    numLabel += currentText
                                    inputNumber = 1
                                }
                                1 -> {
                                    numLabel += currentText
                                    inputNumber = 2
                                }
                                3 -> {
                                    if (numLabel != "14.") {
                                        numLabel += currentText
                                    }
                                    inputNumber = 4
                                }
                            }
                        }
                        "2","3","4" -> {
                            when (inputNumber) {
                                0, 1 -> {
                                    numLabel += currentText
                                    inputNumber = 2
                                }
                                3 -> {
                                    if (numLabel != "14.") {
                                        numLabel += currentText
                                    }
                                    inputNumber = 4
                                }
                            }
                        }
                        else -> {
                            when (inputNumber) {
                                0 -> {
                                    numLabel += currentText
                                    inputNumber = 2
                                }
                                1 -> {
                                    inputNumber = 2
                                }
                                3 -> {
                                    if (numLabel != "14.") {
                                        numLabel += currentText
                                    }
                                    inputNumber = 4
                                }
                            }
                        }
                    }
                    numberLabel.text = numLabel
                }
                //耕起深の入力（0-999）
                "plowingDepth" -> {
                    if (inputNumber < 3) {
                        numLabel += currentText
                        inputNumber += 1
                    }
                    numberLabel.text = numLabel + "cm"
                }
                //仮比重の入力（0-1.99）
                "bulkDensity" -> {
                    if (inputNumber < 3) {
                        when(currentText) {
                            "0" -> {
                                if (inputNumber == 0) {
                                    numLabel = "0."
                                    inputNumber = 1
                                } else {
                                    numLabel += currentText
                                    inputNumber += 1
                                }
                            }
                            "1" -> {
                                if (inputNumber == 0) {
                                    numLabel = "1."
                                    inputNumber = 1
                                } else {
                                    numLabel += currentText
                                    inputNumber += 1
                                }
                            }
                            else -> {
                                if (inputNumber != 0) {
                                    numLabel += currentText
                                    inputNumber += 1
                                }
                            }
                        }
                    }
                    numberLabel.text = numLabel
                }
                //アルカリ分の入力（0-100）
                "alkalineContent" -> {
                    if (inputNumber < 2) {
                        numLabel += currentText
                        inputNumber += 1
                    } else {
                        numLabel = "100"
                    }
                    numberLabel.text = numLabel + "%"
                }
            }

            saveToPref(numLabel)
        }
    }

    //数値クリア
    private fun clearNum(view: View?) {
        numLabel = ""
        numberLabel.text = numLabel
        inputNumber = 0

        when(receivedValue) {
            "plowingDepth","bulkDensity","alkalineContent" -> {
                btnPoint.setEnabled(false) }
            else -> {
                btnPoint.setEnabled(true) }
        }
    }

    //画面遷移（次に進む）
    private fun goNext(view: View?) {
        when (receivedValue) {
            "presentPh" -> {
                val intent = Intent(this, InputNumberActivity::class.java)
                intent.putExtra("inputBtn_tag", "improvedPh")
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
            "improvedPh" -> {
                val intent = Intent(this, InputNumberActivity::class.java)
                intent.putExtra("inputBtn_tag", "plowingDepth")
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
            "plowingDepth" -> {
                val intent = Intent(this, SelectActivity::class.java)
                intent.putExtra("inputBtn_tag", "soilTexture")
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
            "bulkDensity" -> {
                val intent = Intent(this, SelectActivity::class.java)
                intent.putExtra("inputBtn_tag", "productSelect")
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
            "alkalineContent" -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
        }

    }


    //画面遷移（一つ前に戻る）
    private fun backBefore(view: View?) {
        when (receivedValue) {
            "presentPh" -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
            "improvedPh" -> {
                val intent = Intent(this, InputNumberActivity::class.java)
                intent.putExtra("inputBtn_tag", "presentPh")
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
            "plowingDepth" -> {
                val intent = Intent(this, InputNumberActivity::class.java)
                intent.putExtra("inputBtn_tag", "improvedPh")
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
            "bulkDensity" -> {
                val intent = Intent(this, SelectActivity::class.java)
                intent.putExtra("inputBtn_tag", "humusContent")
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
            "alkalineContent" -> {
                val intent = Intent(this, SelectActivity::class.java)
                intent.putExtra("inputBtn_tag", "productSelect")
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
        }
    }

    //画面遷移（メイン画面に戻る）
    private fun backMain(view: View?){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    //共有プリファレンスへの保存
    private fun saveToPref(inputValue:String) {
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = pref.edit()
        editor.putString(receivedValue,inputValue)
            .apply()
    }

}
