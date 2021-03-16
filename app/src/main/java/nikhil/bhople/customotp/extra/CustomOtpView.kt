package nikhil.bhople.customotp.extra

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatEditText
import kotlinx.android.synthetic.main.view_otp.view.*
import nikhil.bhople.customotp.R
import nikhil.bhople.customotp.extension.hideKeyboard
import nikhil.bhople.customotp.extension.showKeyboard
import nikhil.bhople.customotp.intdef.Digit

class CustomOtpView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null
) : LinearLayout(context, attrs), TextWatcher {

    var listener: ((isAllDigitFilled: Boolean) -> Unit)? = null
    private var delayInMillis = 1000L

    init {
        View.inflate(context, R.layout.view_otp, this)

        otpEditText.addTextChangedListener(this)
        otpOnClickView.setOnClickListener {
            otpEditText.requestFocus()
            otpEditText.showKeyboard()
        }
    }

    fun getOtp(): String = otpEditText.text.toString()

    fun clearOtp() {
        val otp = getOtp()
        repeat(otp.length) {
            val newOtp = getOtp().dropLast(Digit.ONE)
            otpEditText.setText(newOtp)
        }
    }

    fun autoFillOtp(smsOtpValue: String) {
        smsOtpValue.toCharArray().forEachIndexed { index, indexedValue ->
            setChatAt(index, indexedValue.toString())
        }
        otpEditText.setText(smsOtpValue)
        otpEditText.setSelection(smsOtpValue.length)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit

    override fun afterTextChanged(userInput: Editable?) {
        userInput?.let {
            val toCharArray = userInput.toString().toCharArray()
            var position = toCharArray.size
            clearChatAt(position)
            if (position != Digit.ZERO) {
                setChatAt(position, toCharArray[position - Digit.ONE].toString())
                position++
                clearChatAt(position)
            }
        }
    }

    private fun setChatAt(position: Int, charAtIndex: String) {
        when (position) {
            Digit.ONE -> {
                otpFieldOne.setText(charAtIndex)
                changeInputType(otpFieldOne, true)
            }
            Digit.TWO -> {
                otpFieldTwo.setText(charAtIndex)
                changeInputType(otpFieldTwo, true)
            }
            Digit.THREE -> {
                otpFieldThree.setText(charAtIndex)
                changeInputType(otpFieldThree, true)
            }
            Digit.FOUR -> {
                otpFieldFour.setText(charAtIndex)
                changeInputType(otpFieldFour, true)
            }
            Digit.FIVE -> {
                otpFieldFive.setText(charAtIndex)
                changeInputType(otpFieldFive, true)
            }
            Digit.SIX -> {
                otpFieldSix.setText(charAtIndex)
                changeInputType(otpFieldSix, true)
                otpEditText.hideKeyboard()
                listener?.invoke(true)
            }
            else -> Unit
        }
    }

    private fun clearChatAt(position: Int) {
        when (position) {
            Digit.ZERO, Digit.ONE -> {
                otpFieldOne.text?.clear()
                changeInputType(otpFieldOne, false)
            }
            Digit.TWO -> {
                otpFieldTwo.text?.clear()
                changeInputType(otpFieldTwo, false)
            }
            Digit.THREE -> {
                otpFieldThree.text?.clear()
                changeInputType(otpFieldThree, false)
            }
            Digit.FOUR -> {
                otpFieldFour.text?.clear()
                changeInputType(otpFieldFour, false)
                listener?.invoke(false)
            }
            Digit.FIVE -> {
                otpFieldFive.text?.clear()
                changeInputType(otpFieldFive, false)
            }
            Digit.SIX -> {
                otpFieldSix.text?.clear()
                changeInputType(otpFieldSix, false)
                listener?.invoke(false)
            }
            else -> Unit
        }
    }

    private fun changeInputType(otp: AppCompatEditText, isPasswordTyped: Boolean) {
        if (isPasswordTyped) {
            postDelayed({
                otp.transformationMethod = BiggerDotPasswordTransformationMethod
            }, delayInMillis)
        } else {
            otp.transformationMethod = null
        }
    }
}