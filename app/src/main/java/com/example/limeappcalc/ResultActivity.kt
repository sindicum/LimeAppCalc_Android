package com.example.limeappcalc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {

    //初期設定
    var resultValue :Double = 0.0
    var changeResult :Double = 0.0
    var unitStatus :Int = 1 //単位の状態　1:10a、2:坪、3:㎡

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        //計算値の受け取りと表示
        resultValue = intent.getDoubleExtra("calcResult",0.0)
        resultLabel.text = resultValue.toInt().toString()

        //ボタンとメソッドの紐付け
        backmainBtn_ra.setOnClickListener { backMain(it) }
        changeBtn.setOnClickListener { changeUnit(it) }

        //注意事項ラベルのテキストセット
        cautionLab.text = "《注意事項》\n" +
                "上記の施用量はアレニウス表による簡易算出法で求めたものであり、土壌により改善結果が異なる場合があります。特に有機物含有量が多いと結果は大きく異なります。"
    }


    //画面遷移（メイン画面に戻る）
    private fun backMain(view: View?){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    //単位変換メソッド
    private fun changeUnit(view: View?) {
        unitStatus += 1

        when (unitStatus) {
            1 -> {
                resultLabel.text = resultValue.toInt().toString()
                unitLabel.text = "10a"
                weightUnitLabel.text = "kg"
            }
            2 -> {
                changeResult = resultValue / 300 * 1000
                resultLabel.text = changeResult.toInt().toString()
                unitLabel.text = "坪"
                weightUnitLabel.text = "g"
            }
            3 -> {
                resultLabel.text = resultValue.toInt().toString()
                unitLabel.text = "㎡"
                weightUnitLabel.text = "g"
            }
            else -> {
                unitStatus = 1
                resultLabel.text = resultValue.toInt().toString()
                unitLabel.text = "10a"
                weightUnitLabel.text = "kg"
            }

        }
    }
}
