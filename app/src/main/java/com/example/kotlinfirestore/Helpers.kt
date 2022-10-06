package com.example.kotlinfirestore

import android.widget.Button

public class Helpers {
    companion object {
        public fun setBtnEnabled(btn:Button, state: Boolean) {
            btn.isEnabled = state;
            btn.isClickable = state;
        }
    }
}