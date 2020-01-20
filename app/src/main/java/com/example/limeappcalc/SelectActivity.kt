package com.example.limeappcalc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.preference.PreferenceManager
import kotlinx.android.synthetic.main.activity_input_number.guidanceLabel
import kotlinx.android.synthetic.main.activity_select.*

class SelectActivity : AppCompatActivity() {

    //初期設定
    //初期設定
    var numLabel :String = ""
    var receivedValue :String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select)

        //入力状態の識別と入力指示ガイダンス表示
        receivedValue = intent.getStringExtra("inputBtn_tag")

        when (receivedValue) {
            "soilTexture" -> {
                guidanceLabel_sa.text = "土性を選んで下さい"
                buttonLab1.text = "砂土(S)"
                buttonLab2.text = "砂壌土(SL)"
                buttonLab3.text = "壌土(L)"
                buttonLab4.text = "埴壌土(CL)"
                buttonLab5.text = "埴土(C)"
            }
            "humusContent" -> {
                guidanceLabel_sa.text = "腐植含量を選んで下さい"
                buttonLab1.text = "あり・含む(5%未満)"
                buttonLab2.text = "富む(5〜10%)"
                buttonLab3.text = "すこぶる富む(10〜20%)"
                buttonLab4.text = "腐植土(20〜30%)"
                buttonLab5.text = "泥炭土(30%以上)"
            }
            "productSelect" -> {
                guidanceLabel_sa.text = "使用資材を選んで下さい"
                buttonLab1.text = "炭カル（粉）"
                buttonLab2.text = "炭カル（粒）"
                buttonLab3.text = "生石灰"
                buttonLab4.text = "苦土生石灰"
                buttonLab5.text = "その他資材"
            }
            else -> {

            }
        }
        //選択ボタンとメソッドの紐付け
        buttonLab1.setOnClickListener { selectLabel(it) }
        buttonLab2.setOnClickListener { selectLabel(it) }
        buttonLab3.setOnClickListener { selectLabel(it) }
        buttonLab4.setOnClickListener { selectLabel(it) }
        buttonLab5.setOnClickListener { selectLabel(it) }

        //画面遷移ボタンとメソッドの紐付け
        backBtn_sa.setOnClickListener { backBefore(it) }
        backmainBtn_sa.setOnClickListener { backMain(it) }
    }

    private fun selectLabel(view: View?) {
        if (view is Button) {
            val currentText = view.text
            saveToPref(currentText.toString())

            when (receivedValue) {
                "soilTexture" -> {
                    val intent = Intent(this, SelectActivity::class.java)
                    intent.putExtra("inputBtn_tag", "humusContent")
                    startActivity(intent)
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                }
                "humusContent" -> {
                    val intent = Intent(this, InputNumberActivity::class.java)
                    intent.putExtra("inputBtn_tag", "bulkDensity")
                    startActivity(intent)
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                }
                "productSelect" -> {
                    when(currentText) {
                        "炭カル（粉）" -> {
                            receivedValue = "alkalineContent"
                            saveToPref("53")
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                        }
                        "炭カル（粒）" -> {
                            receivedValue = "alkalineContent"
                            saveToPref("50")
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                        }
                        "生石灰" -> {
                            receivedValue = "alkalineContent"
                            saveToPref("80")
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                        }
                        "苦土生石灰" -> {
                            receivedValue = "alkalineContent"
                            saveToPref("100")
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                        }
                        "その他資材" -> {
                            val intent = Intent(this, InputNumberActivity::class.java)
                            intent.putExtra("inputBtn_tag", "alkalineContent")
                            startActivity(intent)
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                        }
                    }

                }
            }


        }
    }

    //画面遷移（一つ前に戻る）
    private fun backBefore(view: View?) {
        when (receivedValue) {
            "soilTexture" -> {
                val intent = Intent(this, InputNumberActivity::class.java)
                intent.putExtra("inputBtn_tag", "plowingDepth")
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
            "humusContent" -> {
                val intent = Intent(this, SelectActivity::class.java)
                intent.putExtra("inputBtn_tag", "soilTexture")
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
            "productSelect" -> {
                val intent = Intent(this, InputNumberActivity::class.java)
                intent.putExtra("inputBtn_tag", "bulkDensity")
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
