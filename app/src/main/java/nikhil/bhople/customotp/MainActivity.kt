package nikhil.bhople.customotp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /* Listen the callback from OTP view when user completely typed the OTP
         so that you can perform you action like enabling the button or API calling */
        otpView.listener = {
             btnValidate.isEnabled = it
        }

        btnValidate.setOnClickListener {
            Toast.makeText(this@MainActivity, otpView.getOtp(),Toast.LENGTH_SHORT).show()
        }
    }
}