package com.timaklokov.tickets.presentation.operation.views

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.appbar.MaterialToolbar
import com.jjoe64.graphview.DefaultLabelFormatter
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import com.timaklokov.tickets.R
import com.timaklokov.tickets.data.net.models.SymbolExtraEntity
import com.timaklokov.tickets.presentation.ViewModels.SymbolExtraViewModel
import java.lang.Math.min
import java.text.SimpleDateFormat
import java.util.*

class SymbolActivity : AppCompatActivity() {
    lateinit var symbolViewModel: SymbolExtraViewModel
    lateinit var graph: GraphView
    lateinit var companyName: TextView
    lateinit var symbol: TextView
    lateinit var open: TextView
    lateinit var low: TextView
    lateinit var high: TextView
    lateinit var close: TextView
    lateinit var swipeRefreshLayout: SwipeRefreshLayout

    lateinit var toolbar: MaterialToolbar

    @SuppressLint("ShowToast")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_symbol)

        swipeRefreshLayout = findViewById(R.id.swipe_container)
        swipeRefreshLayout.isRefreshing = true
        swipeRefreshLayout.setOnRefreshListener {
            symbolViewModel.getSymbolExtra()
        }

        graph = findViewById(R.id.graph)

        open = findViewById(R.id.open)
        low = findViewById(R.id.low)
        high = findViewById(R.id.high)
        close = findViewById(R.id.close)

        companyName = findViewById(R.id.companyName)
        companyName.text = intent.extras!!.get("company") as String
        symbol = findViewById(R.id.symbolName)
        symbol.text = intent.extras!!.get("symbol") as String


        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true);
            getSupportActionBar()!!.setDisplayShowHomeEnabled(true);
        }
        toolbar.setTitle("Return")

        symbolViewModel =
            SymbolExtraViewModel(this.applicationContext, intent.extras!!.get("symbol") as String)

        symbolViewModel.getData().observe(this, androidx.lifecycle.Observer {
            try {
                it?.let {
                    var lastData = it[0]
                    lastData = it[0]


                    open.text = "Open: " + lastData.open.toString() + "$"
                    low.text = "Low: " + lastData.low.toString() + "$"
                    high.text = "High: " + lastData.high.toString() + "$"
                    close.text = "Close " + lastData.close.toString() + "$"
                    try {
                        initGraph(it)
                    } catch(e: Exception) {
                        Toast.makeText(applicationContext, "Can't build graph!", Toast.LENGTH_LONG).show()
                    }
                    swipeRefreshLayout.isRefreshing = false
                }
            } catch (e: Exception) {
                swipeRefreshLayout.isRefreshing = false
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun initGraph(it: List<SymbolExtraEntity>) {
        var points: MutableList<DataPoint> = arrayListOf()
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var date: Date
        var currDate = Date()
        var neededIndex = 0
        var minimum = min(1439, it.size - 1)
        for (i in minimum downTo 0) {
            if (i == minimum) {
                currDate = format.parse(it[0].date)
            }
            date = format.parse(it[i].date)
            if (date.day == currDate.day) {
                neededIndex = i
                break
            }
        }
        for (i in neededIndex downTo 0) {
            date = format.parse(it[i].date)
            points.add(DataPoint(date.time.toDouble(), it[i].open))
        }
        var series = LineGraphSeries<DataPoint>(points.toTypedArray())
        println(it)

        graph.getGridLabelRenderer()
            .setLabelFormatter(DateAsXAxisLabelFormatter(applicationContext));

        graph.gridLabelRenderer.labelFormatter = object : DefaultLabelFormatter() {
            override fun formatLabel(
                value: Double,
                isValueX: Boolean
            ): String {
                if (isValueX) {
                    println(value)
                    var date = Date(value.toLong())
                    var format = SimpleDateFormat("HH:mm")
                    return format.format(date)
                } else {
                    return super.formatLabel(value, isValueX) + "$"
                }
            }
        }
        graph.getViewport().setMinX(format.parse(it[neededIndex].date).getTime().toDouble())
        println(format.parse(it[neededIndex].date).getTime().toDouble())
        graph.setBackgroundColor(applicationContext.getColor(R.color.colorPrimary))
        series.color = applicationContext.getColor(R.color.viewHolderBackground)

        graph.addSeries(series)
    }
}