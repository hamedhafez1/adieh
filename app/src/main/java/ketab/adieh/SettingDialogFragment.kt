package ketab.adieh

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.*
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment

class SettingDialogFragment(private val settingListener: SettingListener,
                            private val textSizePrefs: Int,
                            private val textIsBold: Boolean) : DialogFragment() {

    override fun onStart() {
        super.onStart()
        dialog!!.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val window = dialog!!.window
        window!!.setGravity(Gravity.BOTTOM)
        window.requestFeature(Window.FEATURE_NO_TITLE)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return inflater.inflate(R.layout.setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val checkBox = view.findViewById<CheckBox>(R.id.chkBold)
        val radioGroup = view.findViewById<RadioGroup>(R.id.radioGroup)
        view.findViewById<TextView>(R.id.txtContactUs).movementMethod = LinkMovementMethod.getInstance()

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            var size = 23
            when (checkedId) {
                R.id.rbRegular -> size = 23
                R.id.rbLarge -> size = 25
                R.id.rbXLarge -> size = 28
            }
            settingListener.changeSize(size)
        }
        when (textSizePrefs) {
            23 -> radioGroup.check(R.id.rbRegular)
            25 -> radioGroup.check(R.id.rbLarge)
            28 -> radioGroup.check(R.id.rbXLarge)
        }
        checkBox.isChecked = textIsBold
        checkBox.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean -> settingListener.setBold(isChecked) }
    }


}