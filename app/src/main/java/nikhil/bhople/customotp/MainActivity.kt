package nikhil.bhople.customotp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        otpView.listener = {
             btnValidate.isEnabled = it
        }

        btnValidate.setOnClickListener {
            Toast.makeText(this@MainActivity, otpView.getOtp(),Toast.LENGTH_SHORT).show()
        }
    }
}