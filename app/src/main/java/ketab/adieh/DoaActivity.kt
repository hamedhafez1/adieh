package ketab.adieh

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.material.snackbar.Snackbar

@SuppressLint("SetJavaScriptEnabled")
class DoaActivity : AppCompatActivity(), SettingListener {

    @BindView(R.id.web_doa)
    @JvmField
    var webView: WebView? = null

    @BindView(R.id.btnSetting)
    @JvmField
    var btnSetting: ImageButton? = null

    @BindView(R.id.bookmark)
    @JvmField
    var bookmark: ImageButton? = null

    @BindView(R.id.spinner)
    @JvmField
    var spinner: Spinner? = null
    private var page: Int = 0
    private var currentPage: Int = 0
    var isBold: Boolean? = false
    var textSize: Int = 23

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doa)
        ButterKnife.bind(this)
        setUpActionBar()

        webView?.settings?.javaScriptEnabled = true
        bookmark!!.setOnClickListener {
            if (currentPage != intPrefs) {
                intPrefs = currentPage
                bookmark!!.setImageResource(R.drawable.ic_bookmark_fill_24dp)
                val snackBar = Snackbar.make(findViewById(android.R.id.content), R.string.bookmark_added, Snackbar.LENGTH_INDEFINITE)
                val snackBarView = snackBar.view
                snackBarView.layoutDirection = View.LAYOUT_DIRECTION_RTL
                snackBarView.setBackgroundColor(Color.parseColor("#ef161616"))
                val textView = snackBarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
                textView.maxLines = 3
                snackBar.setAction(R.string.all_correct) { snackBar.dismiss() }
                        .setActionTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
                        .setDuration(3500)
                        .show()
            } else {
                intPrefs = 1
                bookmark!!.setImageResource(R.drawable.ic_bookmark_border_white_24dp)
            }
        }

        val adapter = MySpinnerAdapter(this, R.layout.simple_spinner_item,
                listOf(*resources.getStringArray(R.array.fehrest)))
        adapter.setDropDownViewResource(R.layout.dropdown_view)
        spinner!!.adapter = adapter
        val markPage = intPrefs
        if (markPage != 0) {
            spinner?.setSelection(markPage)
        }

        isBold = textIsBold
        textSize = textSizePrefs

        webView?.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(webView, url)
                injectCssFontSize(textSize)
                injectCSSBold(isBold!!)
            }
        }
        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                currentPage = position
                page = positionToPage(position)
                Handler().postDelayed({
                    try {
                        webView?.loadUrl(getString(page))
                        setBookMarkBackground(position)
                    } catch (e: Exception) {
                        Log.d("log", e.message!!)
                    }
                }, 250)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        btnSetting?.setOnClickListener {
            SettingDialogFragment(this, textSize, isBold!!).show(supportFragmentManager, "setting")
        }

    }

    override fun changeSize(size: Int) {
        injectCssFontSize(size)
        textSizePrefs = size
        this.textSize = size
    }

    override fun setBold(isBold: Boolean) {
        injectCSSBold(isBold)
        textIsBold = isBold
        this.isBold = isBold
    }

    private fun positionToPage(position: Int): Int {
        var page = -1
        when (position) {
            0 -> page = R.string.almobit
            1 -> page = R.string.tahlilat
            2 -> page = R.string.adedat
            3 -> page = R.string.taghib_zohr
            4 -> page = R.string.taghib_asr
            5 -> page = R.string.taghib_maghreb
            6 -> page = R.string.taghib_esha
            7 -> page = R.string.taghib_sobh
            8 -> page = R.string.emad
            9 -> page = R.string.amir
            10 -> page = R.string.eteghad
            11 -> page = R.string.tahlil
            12 -> page = R.string.hojb
            13 -> page = R.string.asmaollah
            14 -> page = R.string.herz_sadegh
            15 -> page = R.string.rajab
            16 -> page = R.string.zelzeleh
            17 -> page = R.string.gharigh
            18 -> page = R.string.sahar
            19 -> page = R.string.eftetah
            20 -> page = R.string.ezn
            21 -> page = R.string.ziarat_peighambar
            22 -> page = R.string.ziarat_fatemeh
            23 -> page = R.string.amin
            24 -> page = R.string.ziarat_amir
            25 -> page = R.string.ezn_baghii
            26 -> page = R.string.ziarat_hasan
            27 -> page = R.string.vares
            28 -> page = R.string.ziarat_emam_hosein
            29 -> page = R.string.ashora
            30 -> page = R.string.emam_hosein_az_dor
            31 -> page = R.string.kazemein
            32 -> page = R.string.askari
            33 -> page = R.string.adam
            34 -> page = R.string.nooh
            35 -> page = R.string.jame_saghire
            36 -> page = R.string.jame_kabire
            37 -> page = R.string.veda
            38 -> page = R.string.abbas
            39 -> page = R.string.masome
            40 -> page = R.string.abdolazim
        }
        return page
    }

    private fun setBookMarkBackground(position: Int) {
        if (position == intPrefs) {
            bookmark?.setImageResource(R.drawable.ic_bookmark_fill_24dp)
        } else {
            bookmark?.setImageResource(R.drawable.ic_bookmark_border_white_24dp)
        }
    }

    @SuppressLint("InflateParams")
    private fun setUpActionBar() {
        try {
            val view = layoutInflater.inflate(R.layout.banner, null)
            val lp = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT)
            view.layoutParams = lp
            view.findViewById<TextView>(R.id.txt_banner_title)
            supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
            supportActionBar?.customView = view
        } catch (e: Exception) {
            Log.e("log", e.message!!)
        }

    }

    private var intPrefs: Int
        get() {
            return try {
                val prefs = getSharedPreferences(packageName, Context.MODE_PRIVATE)
                prefs.getInt("mark", 0)
            } catch (e: Exception) {
                Log.e("log", e.message!!)
                0
            }
        }
        set(currentPage) {
            try {
                val editor = getSharedPreferences(packageName, Context.MODE_PRIVATE).edit()
                editor.remove("mark")
                editor.putInt("mark", currentPage)
                editor.apply()
            } catch (e: Exception) {
                Log.e("log", e.message.toString())
            }

        }

    private var textSizePrefs: Int
        get() {
            return try {
                val prefs = getSharedPreferences(packageName, Context.MODE_PRIVATE)
                prefs.getInt("textSize", 23)
            } catch (e: Exception) {
                Log.e("log", e.message!!)
                23
            }
        }
        set(size) {
            try {
                val editor = getSharedPreferences(packageName, Context.MODE_PRIVATE).edit()
                editor.remove("textSize")
                editor.putInt("textSize", size)
                editor.apply()
            } catch (e: Exception) {
                Log.e("log", e.message.toString())
            }
        }

    private var textIsBold: Boolean
        get() {
            return try {
                val prefs = getSharedPreferences(packageName, Context.MODE_PRIVATE)
                prefs.getBoolean("isBold", false)
            } catch (e: Exception) {
                Log.e("log", e.message!!)
                false
            }
        }
        set(isBold) {
            try {
                val editor = getSharedPreferences(packageName, Context.MODE_PRIVATE).edit()
                editor.remove("isBold")
                editor.putBoolean("isBold", isBold)
                editor.apply()
            } catch (e: Exception) {
                Log.e("log", e.message.toString())
            }
        }


    // Inject CSS method: read style.css from assets folder
    // Append stylesheet to document head
    private fun injectCssFontSize(size: Int) {
        try {
            webView?.loadUrl("javascript:(function() {document.body.style.fontSize = '" + size + "pt';})()")
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun injectCSSBold(checked: Boolean) {
        try {
            if (checked) {
                webView?.loadUrl("javascript:(function() {document.body.style.fontWeight = 'bold';})()")
            } else
                webView?.loadUrl("javascript:(function() {document.body.style.fontWeight = '400';})()")
        } catch (e: Exception) {
            Log.d("js", e.message.toString())
        }
    }

}
