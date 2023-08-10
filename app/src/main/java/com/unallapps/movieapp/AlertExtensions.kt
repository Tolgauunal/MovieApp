package com.example.viewmodel3

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.showAlert(title: String, message: String, icon: Int) {
    AlertDialog.Builder(this).setTitle(title).setMessage(message).setIcon(icon).create().show()
}

