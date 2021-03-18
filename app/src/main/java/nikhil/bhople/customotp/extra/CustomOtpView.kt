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
import nikhil.bhople.customotp.intdef.Digit.Companion.FIVE
import nikhil.bhople.customotp.intdef.Digit.Companion.FOUR
import nikhil.bhople.customotp.intdef.Digit.Companion.ONE
import nikhil.bhople.customotp.intdef.Digit.Companion.SIX
import nikhil.bhople.customotp.intdef.Digit.Companion.THREE
import nikhil.bhople.customotp.intdef.Digit.Companion.TWO
import nikhil.bhople.customotp.intdef.Digit.Companion.ZERO

class CustomOtpView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null
) : LinearLayout(context, attrs), TextWatcher {

    var listener: ((isAllDigitFilled: Boolean) -> Unit)? = null // listener is used to get callback when OTP is completely typed
    private val delayInMillis = 1000L // Specify your own delay for masking OTP after typed

    init {
        View.inflate(context, R.layout.view_otp, this)

        otpEditText.addTextChangedListener(this)
        otpOnClickView.setOnClickListener {
            otpEditText.requestFocus()
            otpEditText.showKeyboard()
        }
    }

    fun getOtp(): String = otpEditText.text.toString()

    /**
     * When user type wrong OTP and you want to clear after API call then call this method
     */
    fun clearOtp() {
        val otp = getOtp()
        repeat(otp.length) {
            val newOtp = getOtp().dropLast(ONE)
            otpEditText.setText(newOtp)
        }
    }

    /**
     * autoFillOtp method is used to autofill the OTP when user received by SMS
     */
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
            if (position != ZERO) {
                setChatAt(position, toCharArray[position - ONE].toString())
                position++
                clearChatAt(position)
            }
        }
    }

    private fun setChatAt(position: Int, charAtIndex: String) {
        when (position) {
            ONE -> {
                otpFieldOne.setText(charAtIndex)
                changeInputType(otpFieldOne, true)
            }
            TWO -> {
                otpFieldTwo.setText(charAtIndex)
                changeInputType(otpFieldTwo, true)
            }
            THREE -> {
                otpFieldThree.setText(charAtIndex)
                changeInputType(otpFieldThree, true)
            }
            FOUR -> {
                otpFieldFour.setText(charAtIndex)
                changeInputType(otpFieldFour, true)
            }
            FIVE -> {
                otpFieldFive.setText(charAtIndex)
                changeInputType(otpFieldFive, true)
            }
            SIX -> {
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
            ZERO, ONE -> {
                otpFieldOne.text?.clear()
                changeInputType(otpFieldOne, false)
            }
            TWO -> {
                otpFieldTwo.text?.clear()
                changeInputType(otpFieldTwo, false)
            }
            THREE -> {
                otpFieldThree.text?.clear()
                changeInputType(otpFieldThree, false)
            }
            FOUR -> {
                otpFieldFour.text?.clear()
                changeInputType(otpFieldFour, false)
                listener?.invoke(false)
            }
            FIVE -> {
                otpFieldFive.text?.clear()
                changeInputType(otpFieldFive, false)
            }
            SIX -> {
                otpFieldSix.text?.clear()
                changeInputType(otpFieldSix, false)
                listener?.invoke(false)
            }
            else -> Unit
        }
    }


    /**
     *  if you don't want to show typed OTP by user then refactor changeInputType method like below
     *  private fun changeInputType(otp: AppCompatEditText) {
             otp.transformationMethod = BiggerDotPasswordTransformationMethod
        }
     */
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