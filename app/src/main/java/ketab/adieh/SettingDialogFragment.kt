package ketab.adieh

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.*
import android.widget.CompoundButton
import androidx.fragment.app.DialogFragment
import ketab.adieh.databinding.SettingBinding

class SettingDialogFragment(private val settingListener: SettingListener,
                            private val textSizePrefs: Int,
                            private val textIsBold: Boolean) : DialogFragment() {

    private lateinit var binding: SettingBinding

    override fun onStart() {
        super.onStart()
        dialog!!.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val window = dialog!!.window
        window!!.setGravity(Gravity.BOTTOM)
        window.requestFeature(Window.FEATURE_NO_TITLE)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding = SettingBinding.inflate(inflater, container, false)

        val checkBox = binding.chkBold
        val radioGroup = binding.radioGroup
        binding.txtContactUs.movementMethod = LinkMovementMethod.getInstance()

        binding.txtVersion.text = getString(R.string.version, "ادعیه نسخه", " : ", BuildConfig.VERSION_NAME)

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

        return binding.root
    }

}