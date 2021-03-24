package com.example.readnumbers

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import io.card.payment.CardIOActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){

    val REQUEST_SCAN = 100
    val REQUEST_AUTOTEST = 200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnScan.setOnClickListener {
            val intent = Intent(this@MainActivity, CardIOActivity::class.java)
                .putExtra(CardIOActivity.EXTRA_LANGUAGE_OR_LOCALE, "en")
                .putExtra(CardIOActivity.EXTRA_GUIDE_COLOR, Color.GREEN)
                .putExtra(CardIOActivity.EXTRA_RETURN_CARD_IMAGE, true)
            REQUEST_SCAN?.let { it1 -> startActivityForResult(intent, it1) }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if ((requestCode == REQUEST_SCAN || requestCode == REQUEST_AUTOTEST) && data != null && data.hasExtra(
                CardIOActivity.EXTRA_SCAN_RESULT
            )
        ) {
            //tvCardDetail.visibility = View.VISIBLE
            var resultDisplayStr: String
            if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
                val scanResult =
                    data.getParcelableExtra<io.card.payment.CreditCard>(CardIOActivity.EXTRA_SCAN_RESULT)
                // Never log a raw card number. Avoid displaying it, but if necessary use scanResult.formattedCardNumber can intend of .redactedCardNumber
                resultDisplayStr = scanResult?.formattedCardNumber + "\n"
            } else {
                resultDisplayStr = "Scan was canceled."
            }
            //tvCardDetail.text = resultDisplayStr
            edtNumbers.setText(resultDisplayStr)
        }
    }


}