package com.example.turnippricetrends

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // スタイルとフォントファミリーの設定
    private var mTypeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)

    // データの個数
    private val chartDataCount = 10

    override fun onCreate(savedInstanceState: Bundle?) {

        try {
            Thread.sleep(500)
        } catch (e: InterruptedException) {
        }

        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dataStore: SharedPreferences = getSharedPreferences("KabuData", Context.MODE_PRIVATE)

        // グラフの設定
        setupLineChart()
        // データの設定
        //lineChart1.data = lineData(chartDataCount, 100f)

        //値の保存
        button_save.setOnClickListener {

            //買値
            val price = editText_kaine.text.toString().toInt()

            //月曜
            val data_MON_AM = editText11.text.toString().toInt()
            val data_MON_PM = editText21.text.toString().toInt()

            //火曜
            val data_TUE_AM = editText12.text.toString().toInt()
            val data_TUE_PM = editText22.text.toString().toInt()

            //水曜
            val data_WED_AM = editText13.text.toString().toInt()
            val data_WED_PM = editText23.text.toString().toInt()

            //木曜
            val data_THU_AM = editText14.text.toString().toInt()
            val data_THU_PM = editText24.text.toString().toInt()

            //金曜
            val data_FRI_AM = editText15.text.toString().toInt()
            val data_FRI_PM = editText25.text.toString().toInt()

            //データの保存
            val editor = dataStore.edit()
            editor.putInt("price", price)
            editor.putInt("Mon_AM", data_MON_AM)
            editor.putInt("Mon_PM", data_MON_PM)
            editor.putInt("Tue_AM", data_TUE_AM)
            editor.putInt("Tue_PM", data_TUE_PM)
            editor.putInt("Wed_AM", data_WED_AM)
            editor.putInt("Wed_PM", data_WED_PM)
            editor.putInt("Thu_AM", data_THU_AM)
            editor.putInt("Thu_PM", data_THU_PM)
            editor.putInt("Fri_AM", data_FRI_AM)
            editor.putInt("Fri_PM", data_FRI_PM)

            editor.apply()
        }

        button_read.setOnClickListener {
            val input_price = dataStore.getInt("price", 0).toString()
            val input_MON_AM = dataStore.getInt("Mon_AM", 0).toString()
            val input_MON_PM = dataStore.getInt("Mon_PM", 0).toString()
            val input_TUE_AM = dataStore.getInt("Tue_AM", 0).toString()
            val input_TUE_PM = dataStore.getInt("Tue_PM", 0).toString()
            val input_WED_AM = dataStore.getInt("Wed_AM", 0).toString()
            val input_WED_PM = dataStore.getInt("Wed_PM", 0).toString()
            val input_THU_AM = dataStore.getInt("Thu_AM", 0).toString()
            val input_THU_PM = dataStore.getInt("Thu_PM", 0).toString()
            val input_FRI_AM = dataStore.getInt("Fri_AM", 0).toString()
            val input_FRI_PM = dataStore.getInt("Fri_PM", 0).toString()

            editText_kaine.setText(input_price, TextView.BufferType.NORMAL)
            editText11.setText(input_MON_AM, TextView.BufferType.NORMAL)
            editText21.setText(input_MON_PM, TextView.BufferType.NORMAL)
            editText12.setText(input_TUE_AM, TextView.BufferType.NORMAL)
            editText22.setText(input_TUE_PM, TextView.BufferType.NORMAL)
            editText13.setText(input_WED_AM, TextView.BufferType.NORMAL)
            editText23.setText(input_WED_PM, TextView.BufferType.NORMAL)
            editText14.setText(input_THU_AM, TextView.BufferType.NORMAL)
            editText24.setText(input_THU_PM, TextView.BufferType.NORMAL)
            editText15.setText(input_FRI_AM, TextView.BufferType.NORMAL)
            editText25.setText(input_FRI_PM, TextView.BufferType.NORMAL)

            val data = input_MON_AM.toFloat() / input_price.toFloat()

            if (data < 0.6) {
                textView_expectation.text = "今週の予想パターンは4期型です。"
            } else if (0.6 <= data && data < 0.8) {
                textView_expectation.text = "今週の予想パターンは波or4期型です。"
            } else if (0.8 <= data && data < 0.85) {
                textView_expectation.text = "今週の予想パターンは3期or4期型です。"
            } else if (0.85 <= data && data < 0.91) {
                textView_expectation.text = "今週の予想パターンはジリ貧or3期or4期型です。"
            } else {
                textView_expectation.text = "今週の予想パターンは波or4期型です。"
            }

            textView_d.text = data.toString()

            //setupLineChart()
            lineChart1.data = lineData(chartDataCount, 100f)
            lineChart1.invalidate()
        }
    }

    private fun lineData(count: Int, range: Float): LineData {
        val dataStore: SharedPreferences = getSharedPreferences("KabuData", Context.MODE_PRIVATE)
        val values = mutableListOf<Entry>().apply {
            add(Entry(0F, dataStore.getInt("Mon_AM", 0).toFloat()))
            add(Entry(1F, dataStore.getInt("Mon_PM", 0).toFloat()))
            add(Entry(2F, dataStore.getInt("Tue_AM", 0).toFloat()))
            add(Entry(3F, dataStore.getInt("Tue_PM", 0).toFloat()))
            add(Entry(4F, dataStore.getInt("Wed_AM", 0).toFloat()))
            add(Entry(5F, dataStore.getInt("Wed_PM", 0).toFloat()))
            add(Entry(6F, dataStore.getInt("Thu_AM", 0).toFloat()))
            add(Entry(7F, dataStore.getInt("Thu_PM", 0).toFloat()))
            add(Entry(8F, dataStore.getInt("Fri_AM", 0).toFloat()))
            add(Entry(9F, dataStore.getInt("Fri_PM", 0).toFloat()))
        }

        val yVals = LineDataSet(values, "カブ価").apply {
            axisDependency = YAxis.AxisDependency.LEFT
            color = Color.BLACK
            setDrawCircles(true)
            setDrawCircleHole(true)
            // 点の値非表示
            setDrawValues(false)
            // 線の太さ
            lineWidth = 2f
        }
        val data = LineData(yVals)
        return data
    }

    private fun setupLineChart(){
        lineChart1.apply {
            description.isEnabled = false
            setTouchEnabled(true)
            isDragEnabled = true
            // 拡大縮小可能
            isScaleXEnabled = true
            setPinchZoom(false)
            setDrawGridBackground(false)

            //データラベルの表示
            legend.apply {
                form = Legend.LegendForm.LINE
                typeface = mTypeface
                textSize = 11f
                textColor = Color.BLACK
                verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
                horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
                orientation = Legend.LegendOrientation.HORIZONTAL
                setDrawInside(false)
            }

            //y軸右側の設定
            axisRight.isEnabled = false

            //X軸表示
            xAxis.apply {
                typeface = mTypeface
                setDrawLabels(false)
                // 格子線を表示する
                setDrawGridLines(true)
            }

            //y軸左側の表示
            axisLeft.apply {
                typeface = mTypeface
                textColor = Color.BLACK
                // 格子線を表示する
                setDrawGridLines(true)
            }
        }
    }
}
