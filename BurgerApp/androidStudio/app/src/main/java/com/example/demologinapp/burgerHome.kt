package com.example.demologinapp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.demologinapp.databinding.ActivityBurgerHomeBinding

class burgerHome : AppCompatActivity() {

    // Data class to represent an item in the cart
    data class CartItem(val name: String, val price: String, var quantity: Int = 1) {
        fun getPriceAsDouble(): Double {
            // Remove dollar sign and commas for parsing
            return price.replace("$", "").replace(",", "").toDouble() * quantity
        }
    }

    private lateinit var binding: ActivityBurgerHomeBinding
    private val cartItems = mutableListOf<CartItem>() // List to store items added to the cart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set up view binding
        binding = ActivityBurgerHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set the title of the app
        binding.textView.text = "Welcome to Burger Queen!"

        // Handle "Add to Cart" button clicks
        binding.item1Add.setOnClickListener { addItemToCart("Chicken burger", "$10.00") }
        binding.item2Add.setOnClickListener { addItemToCart("Super burger", "$20.00") }
        binding.item3Add.setOnClickListener { addItemToCart("Ultimate burger", "$30.00") }
        binding.item4Add.setOnClickListener { addItemToCart("Vegetarian pizza", "40.00") }
        binding.item5Add.setOnClickListener { addItemToCart("NotBurger", "$50.00") }
        binding.item6Add.setOnClickListener { addItemToCart("DrinkUp", "$60.00") }

        // Handle "Remove from Cart" button clicks
        binding.item1Remove.setOnClickListener { removeItemFromCart("Chicken burger") }
        binding.item2Remove.setOnClickListener { removeItemFromCart("Super burger") }
        binding.item3Remove.setOnClickListener { removeItemFromCart("Ultimate burger") }
        binding.item4Remove.setOnClickListener { removeItemFromCart("Vegetarian pizza") }
        binding.item5Remove.setOnClickListener { removeItemFromCart("NotBurger") }
        binding.item5Remove.setOnClickListener { removeItemFromCart("DrinkUp") }

        // Handle "Cart Icon" click to show added items
        binding.cartIcon.setOnClickListener {
            showCart()
        }
    }

    // Function to add an item to the cart
    private fun addItemToCart(name: String, price: String) {
        val existingItem = cartItems.find { it.name == name }

        if (existingItem != null) {
            // If the item already exists, increment its quantity
            existingItem.quantity++
        } else {
            // Otherwise, add the item to the cart with quantity 1
            val newItem = CartItem(name, price, 1)
            cartItems.add(newItem)
        }

        // Update the cart icon and badge
        updateCartIcon()

        // Show a toast message that the item was added
        Toast.makeText(this, "$name added to cart!", Toast.LENGTH_SHORT).show()
    }

    // Function to remove an item from the cart
    private fun removeItemFromCart(name: String) {
        val itemToRemove = cartItems.find { it.name == name }
        if (itemToRemove != null) {
            itemToRemove.quantity-- // Decrease quantity by 1
            if (itemToRemove.quantity <= 0) {
                // Remove item if quantity becomes 0 or less
                cartItems.remove(itemToRemove)
            }
            // Update the cart icon and badge
            updateCartIcon()
            // Show a toast message that the item was removed
            Toast.makeText(this, "$name removed from cart!", Toast.LENGTH_SHORT).show()
        } else {
            // Handle the case when the item isn't found
            Toast.makeText(this, "$name is not in the cart.", Toast.LENGTH_SHORT).show()
        }
    }

    // Function to calculate the total cost of the cart items
    private fun getTotalAmount(): Double {
        return cartItems.sumOf { it.getPriceAsDouble() }
    }

    // Function to show the cart items in a dialog
    private fun showCart() {
        val cartItemCount = cartItems.sumOf { it.quantity }

        if (cartItemCount > 0) {
            // Build the cart string showing item name, quantity, and price
            val itemDetails = cartItems.joinToString("\n") { "${it.name} x${it.quantity}: $${it.getPriceAsDouble()}" }
            val totalAmount = getTotalAmount()

            // Create a dialog to display the cart items and total
            val dialog = AlertDialog.Builder(this)
                .setTitle("Burger Cart")
                .setMessage("$itemDetails\n\nTotal amount: $$totalAmount")
                .setPositiveButton("Close") { dialog, _ -> dialog.dismiss() }
                .create()

            // Show the dialog
            dialog.show()
        } else {
            // Show message for empty cart
            Toast.makeText(this, "Your cart is empty.", Toast.LENGTH_SHORT).show()
        }
    }

    // Function to update the cart icon to reflect the number of items in the cart
    private fun updateCartIcon() {
        val cartItemCount = cartItems.sumOf { it.quantity } // Sum the quantity of all items

        // Update the cart item count badge
        binding.cartItemCountBadge.text = if (cartItemCount > 0) cartItemCount.toString() else "0" // Show "0" for empty cart
        binding.cartItemCountBadge.visibility = if (cartItemCount > 0) View.VISIBLE else View.GONE // Hide the badge when empty

        // Optionally, update the cart icon when items are in the cart
        if (cartItemCount > 0) {
            binding.cartIcon.setImageResource(R.drawable.baseline_shopping_cart_checkout_24)
            binding.cartIcon.contentDescription = "Cart ($cartItemCount items)"
        } else {
            binding.cartIcon.setImageResource(R.drawable.ic_empty_cart)
            binding.cartIcon.contentDescription = "Empty Cart"
        }
    }
}
