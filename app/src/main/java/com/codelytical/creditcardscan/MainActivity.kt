package com.codelytical.creditcardscan

import android.annotation.SuppressLint
import android.nfc.NfcAdapter
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.ExperimentalGetImage
import com.codelytical.creditcardscanner.CreditCardScannerLauncher
import com.codelytical.creditcardscanner.NfcReadLauncher

@ExperimentalGetImage
@SuppressLint("UnsafeExperimentalUsageError")

class MainActivity : AppCompatActivity() {

    private lateinit var scannerLauncher: CreditCardScannerLauncher

    private lateinit var nfcLauncher: NfcReadLauncher

    private var mNfcAdapter: NfcAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupScannerLauncher()

        findViewById<Button>(R.id.scanButton).setOnClickListener {
            scannerLauncher.launch()
        }

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this)

        if (mNfcAdapter == null) {
            Toast.makeText(this, "NFC not supported on this device", Toast.LENGTH_LONG).show()
        } else {
            setupNfcLauncher()
            findViewById<Button>(R.id.nfcButton).setOnClickListener {
                nfcLauncher.launch()
            }
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

    private fun setupNfcLauncher() {
        nfcLauncher = NfcReadLauncher(this, ::handleNfcResult)
    }

    private fun handleNfcResult(cardNumber: String?, expiryDate: String?, cardType: String?) {
        if (cardNumber != null && expiryDate != null && cardType != null) {
            Toast.makeText(this, "Card Number: $cardNumber, Expiry Date: $expiryDate", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Null values were passed", Toast.LENGTH_LONG).show()
        }
    }

}
