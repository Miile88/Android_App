package com.example.shopapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import java.text.NumberFormat
import java.util.Locale

class OrderActivity : AppCompatActivity() {

    private var originalPrice: Double = 0.0
    private var shippingCost: Double = 0.0
    private val discountRate = 0.05

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        val productName = intent.getStringExtra("PRODUCT_NAME") ?: "Product"
        originalPrice = intent.getDoubleExtra("PRODUCT_PRICE", 0.0)
        val imageResId = intent.getIntExtra("PRODUCT_IMAGE", android.R.drawable.ic_menu_gallery)

        val tvName = findViewById<TextView>(R.id.tvProductName)
        val tvPrice = findViewById<TextView>(R.id.tvOriginalPrice)
        val tvTotal = findViewById<TextView>(R.id.tvTotalPrice)
        val ivProduct = findViewById<ImageView>(R.id.ivProduct)
        val btnPay = findViewById<MaterialButton>(R.id.btnPay)

        val cardStandard = findViewById<MaterialCardView>(R.id.cardStandard)
        val cardExpress = findViewById<MaterialCardView>(R.id.cardExpress)
        val rbStandard = findViewById<RadioButton>(R.id.rbStandard)
        val rbExpress = findViewById<RadioButton>(R.id.rbExpress)

        tvName.text = productName
        tvPrice.text = formatCurrency(originalPrice)
        ivProduct.setImageResource(imageResId)

        // Initial update
        updateTotal(tvTotal)

        // Manual selection logic for custom radio button cards
        cardStandard.setOnClickListener {
            selectShipping(true, rbStandard, rbExpress, cardStandard, cardExpress, tvTotal)
        }
        cardExpress.setOnClickListener {
            selectShipping(false, rbStandard, rbExpress, cardStandard, cardExpress, tvTotal)
        }

        btnPay.setOnClickListener {
            val intent = Intent(this, SuccessActivity::class.java)
            startActivity(intent)
        }
    }

    private fun selectShipping(
        isStandard: Boolean,
        rbStandard: RadioButton,
        rbExpress: RadioButton,
        cardStandard: MaterialCardView,
        cardExpress: MaterialCardView,
        tvTotal: TextView
    ) {
        rbStandard.isChecked = isStandard
        rbExpress.isChecked = !isStandard

        shippingCost = if (isStandard) 0.0 else 1700.0

        val primaryColor = ContextCompat.getColor(this, R.color.primary)
        val lightGreyColor = ContextCompat.getColor(this, R.color.light_grey)

        cardStandard.strokeColor = if (isStandard) primaryColor else lightGreyColor
        cardStandard.strokeWidth = if (isStandard) dpToPx(2) else dpToPx(1)

        cardExpress.strokeColor = if (!isStandard) primaryColor else lightGreyColor
        cardExpress.strokeWidth = if (!isStandard) dpToPx(2) else dpToPx(1)

        updateTotal(tvTotal)
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }

    private fun updateTotal(tvTotal: TextView) {
        val discountedPrice = originalPrice * (1 - discountRate)
        val finalPrice = discountedPrice + shippingCost
        tvTotal.text = formatCurrency(finalPrice)
    }

    private fun formatCurrency(amount: Double): String {
        val formatter = NumberFormat.getCurrencyInstance(Locale.US)
        return formatter.format(amount)
    }
}
