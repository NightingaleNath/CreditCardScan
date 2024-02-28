package com.codelytical.creditcardscan

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.Toast
import androidx.camera.core.ExperimentalGetImage
import com.codelytical.creditcardscanner.CreditCardScannerActivity
import com.codelytical.creditcardscanner.CreditCardScannerLauncher

@ExperimentalGetImage
@SuppressLint("UnsafeExperimentalUsageError")

class MainActivity : AppCompatActivity() {

    private lateinit var scannerLauncher: CreditCardScannerLauncher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupScannerLauncher()

        findViewById<Button>(R.id.scanButton).setOnClickListener {
            scannerLauncher.launch()
        }

    }

    private fun setupScannerLauncher() {
        scannerLauncher = CreditCardScannerLauncher(this, ::handleScanResult)
    }

    private fun handleScanResult(cardNumber: String?, expiryDate: String?, manualEntryRequested: Boolean) {
        if (manualEntryRequested) {
            // Handle manual card entry here
            // For example, show your own manual entry form
            Toast.makeText(this, "Manual entry requested", Toast.LENGTH_LONG).show()
        } else if (cardNumber != null && expiryDate != null) {
            Toast.makeText(this, "Card Number: $cardNumber, Expiry Date: $expiryDate", Toast.LENGTH_LONG).show()
        }
    }
}