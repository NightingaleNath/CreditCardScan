package com.codelytical.creditcardscanner


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.ExperimentalGetImage
import androidx.fragment.app.FragmentActivity

@ExperimentalGetImage
@SuppressLint("UnsafeExperimentalUsageError")
class CreditCardScannerLauncher(
    private val activity: FragmentActivity,
    private val onResult: (String?, String?, Boolean) -> Unit
) {

    private var launcher: ActivityResultLauncher<Intent> = activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val cardNumber = data?.getStringExtra("CARD_NUMBER")
            val expiryDate = data?.getStringExtra("EXPIRY_DATE")
            val manualEntryRequested = data?.getBooleanExtra("MANUAL_ENTRY_REQUESTED", false) ?: false
            onResult(cardNumber, expiryDate, manualEntryRequested)
        } else {
            onResult(null, null, false)
        }
    }

    fun launch() {
        val intent = Intent(activity, CreditCardScannerActivity::class.java)
        launcher.launch(intent)
    }
}

