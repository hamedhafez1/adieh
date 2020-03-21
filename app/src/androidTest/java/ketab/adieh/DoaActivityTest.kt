package ketab.adieh

import android.util.Log
import android.webkit.WebView
import androidx.test.rule.ActivityTestRule
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class DoaActivityTest {

    @Rule @JvmField
    val mActivityTestRule = ActivityTestRule(DoaActivity::class.java)

    private var mActivity: DoaActivity? = null

    @Before
    fun setUp() {
        mActivity = mActivityTestRule.activity
    }

    @Test
    fun testLaunch() {
        val view = mActivity!!.findViewById<WebView>(R.id.web_doa)
        assertNotNull(view)
        Log.d("Pass","passed")
    }

    @After
    fun tearDown() {
        mActivity = null
    }
}