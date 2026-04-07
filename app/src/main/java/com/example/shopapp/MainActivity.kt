package com.example.shopapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.card.MaterialCardView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val card1 = findViewById<MaterialCardView>(R.id.cardCar1)
        val card2 = findViewById<MaterialCardView>(R.id.cardCar2)
        val card3 = findViewById<MaterialCardView>(R.id.cardCar3)
        val card4 = findViewById<MaterialCardView>(R.id.cardCar4)

        card1.setOnClickListener { navigateToOrder("BMW M3 (F80 generation)", 38000.0, R.drawable.car_1) }
        card2.setOnClickListener { navigateToOrder("Mercedes-Benz CLA-Class", 46400.0, R.drawable.car_2) }
        card3.setOnClickListener { navigateToOrder("Porsche 911 GT3 RS", 189000.0, R.drawable.car_3) }
        card4.setOnClickListener { navigateToOrder("Ferrari 488 Spider", 260000.0, R.drawable.car_4) }
    }

    private fun navigateToOrder(name: String, price: Double, imageRes: Int) {
        val intent = Intent(this, OrderActivity::class.java).apply {
            putExtra("PRODUCT_NAME", name)
            putExtra("PRODUCT_PRICE", price)
            putExtra("PRODUCT_IMAGE", imageRes)
        }
        startActivity(intent)
    }
}
