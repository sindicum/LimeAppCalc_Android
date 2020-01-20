package com.example.limeappcalc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.TextKeyListener.clear
import android.view.View
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //初期設定
    var aleniusFactor :Int = 0
    var presentPhStr :String? = ""
    var improvedPhStr :String? = ""
    var plowingDepthStr :String? = ""
    var soilTextureStr :String? = ""
    var humusContentStr :String? = ""
    var bulkDensityStr :String? = ""
    var productSelectStr :String? = ""
    var alkalineContentStr :String? = ""
    var calcResult :Double = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //計算ボタンの有効化
        calcBtn.setEnabled(true)

        //共有プリファレンスデータのセット
        PreferenceManager.getDefaultSharedPreferences(this).apply {
            presentPhStr = getString("presentPh", "")
            improvedPhStr = getString("improvedPh", "")
            plowingDepthStr = getString("plowingDepth", "")
            soilTextureStr = getString("soilTexture", "")
            humusContentStr = getString("humusContent", "")
            bulkDensityStr = getString("bulkDensity", "")
            productSelectStr = getString("productSelect", "")
            alkalineContentStr = getString("alkalineContent", "")
        }

        //ボタンテキストに入力データ表示
        presentPhBtn.text = presentPhStr
        improvedPhBtn.text = improvedPhStr
        if (plowingDepthStr == "") {
            plowingDepthBtn.text = ""
        } else {
            plowingDepthBtn.text = plowingDepthStr + "cm"
        }
        soilTextureBtn.text = soilTextureStr
        humusContentBtn.text = humusContentStr
        bulkDensityBtn.text = bulkDensityStr
        productSelectBtn.text = productSelectStr
        if (alkalineContentStr == "") {
            alkalineContentBtn.text = ""
        } else {
            alkalineContentBtn.text = alkalineContentStr + "%"
        }

        //未入力の場合はボタンテキストに「タップで入力と表示」
        if (presentPhStr == "") {
            presentPhBtn.text = "タップで入力"
            calcBtn.setEnabled(false)
        } else if (improvedPhStr == "") {
            improvedPhBtn.text = "タップで入力"
            calcBtn.setEnabled(false)
        } else if (plowingDepthStr == "") {
            plowingDepthBtn.text = "タップで入力"
            calcBtn.setEnabled(false)
        } else if (soilTextureStr == "") {
            soilTextureBtn.text = "タップで入力"
            calcBtn.setEnabled(false)
        } else if (humusContentStr == "") {
            humusContentBtn.text = "タップで入力"
            calcBtn.setEnabled(false)
        } else if (bulkDensityStr == "") {
            bulkDensityBtn.text = "タップで入力"
            calcBtn.setEnabled(false)
        } else if (productSelectStr == "") {
            productSelectBtn.text = "タップで入力"
            calcBtn.setEnabled(false)
        } else if (alkalineContentStr == "") {
            alkalineContentBtn.text = "タップで入力"
            calcBtn.setEnabled(false)
        }

        //ボタンとgoInputNumActメソッドの紐付け
        presentPhBtn.setOnClickListener { goInputNumAct(it) }
        improvedPhBtn.setOnClickListener { goInputNumAct(it) }
        plowingDepthBtn.setOnClickListener { goInputNumAct(it) }
        bulkDensityBtn.setOnClickListener { goInputNumAct(it) }
        alkalineContentBtn.setOnClickListener { goInputNumAct(it) }

        //ボタンとgoSelectAtcメソッドの紐付け
        soilTextureBtn.setOnClickListener { goSelectAct(it) }
        humusContentBtn.setOnClickListener { goSelectAct(it) }
        productSelectBtn.setOnClickListener { goSelectAct(it) }

        //ボタンとdoCalc、resetPrefメソッドの紐付け
        calcBtn.setOnClickListener{ doCalc(it) }
        resetBtn.setOnClickListener{ resetPref(it) }

    }

    //計算実行 つーかこの引数って何？
    private fun doCalc(view: View?) {

        //アレーニウス係数の算出
        when (humusContentBtn.text) {
            "あり・含む(5%未満)" -> {
                when (soilTextureBtn.text) {
                    "砂土(S)" -> aleniusFactor = 8
                    "砂壌土(SL)" -> aleniusFactor = 17
                    "壌土(L)" -> aleniusFactor = 25
                    "埴壌土(CL)" -> aleniusFactor = 34
                    "埴土(C)" -> aleniusFactor = 42
                }
            }
            "富む(5〜10%)" -> {
                when (soilTextureBtn.text) {
                    "砂土(S)" -> aleniusFactor = 13
                    "砂壌土(SL)" -> aleniusFactor = 25
                    "壌土(L)" -> aleniusFactor = 34
                    "埴壌土(CL)" -> aleniusFactor = 42
                    "埴土(C)" -> aleniusFactor = 51
                }
            }
            "すこぶる富む(10〜20%)" -> {
                when (soilTextureBtn.text) {
                    "砂土(S)" -> aleniusFactor = 20
                    "砂壌土(SL)" -> aleniusFactor = 39
                    "壌土(L)" -> aleniusFactor = 51
                    "埴壌土(CL)" -> aleniusFactor = 62
                    "埴土(C)" -> aleniusFactor = 73
                }
            }
            "腐植土(20〜30%)" -> aleniusFactor = 83
            "泥炭土(30%以上)" -> aleniusFactor = 99
        }

        val presentPhValue = presentPhStr!!.toDouble()
        val improvedPhValue = improvedPhStr!!.toDouble()
        val plowingDepthValue = plowingDepthStr!!.toDouble()
        val bulkDensityValue = bulkDensityStr!!.toDouble()
        val alkalineContentValue = alkalineContentStr!!.toDouble()



        if (alkalineContentStr != "0") {
            calcResult = (improvedPhValue - presentPhValue) * plowingDepthValue * bulkDensityValue * aleniusFactor * 53 / alkalineContentValue
        }

        //ResultActivityへ遷移
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra("calcResult",calcResult)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

    }

    //共有プリファレンス保存値のクリア
    private fun resetPref(view: View?) {
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        pref.edit {
            clear()
        }
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

    }


    //InputNumberActivityへの遷移
    private fun goInputNumAct(view: View?) {
        val intent = Intent(this, InputNumberActivity::class.java)
        val tag = view?.tag.toString()
        intent.putExtra("inputBtn_tag", tag)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    //SelectActivityへの遷移
    private  fun goSelectAct(view: View?) {
        val intent = Intent(this, SelectActivity::class.java)
        val tag = view?.tag.toString()
        intent.putExtra("inputBtn_tag", tag)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

}
