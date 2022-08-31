package com.tongue.dandelion.helper

import android.util.Log
import com.tongue.dandelion.BuildConfig

class AppLog {
    companion object {
        private val debug = BuildConfig.DEBUG
        private val logNetworkData = BuildConfig.DEBUG

        @JvmStatic
        fun e(tag: String?, message: String) {
            if (debug) {
                Log.e(tag, message)
            } else {
            }
        }

        @JvmStatic
        fun e(tag: String?, message: String, e: Exception) {
            if (debug) {
                Log.e(tag, message, e)
            } else {
            }
        }

        @JvmStatic
        fun v(tag: String, message: String) {
            if (debug) {
                Log.w(tag, message)
            }
        }

        @JvmStatic
        fun w(tag: String, message: String) {
            if (debug) {
                Log.w(tag, message)
            }
        }

        @JvmStatic
        fun d(tag: String?, message: String) {
            if (debug) {
                Log.d(tag, message)
            }
        }

        @JvmStatic
        fun i(tag: String, message: String) {
            if (debug) {
                Log.i(tag, message)
            }
        }

        @JvmStatic
        fun i(tag: String?, message: String, e: Exception) {
            if (debug) {
                Log.i(tag, message, e)
            }
        }

        @JvmStatic
        fun logNetworkData(tag: String?, message: String) {
            if (logNetworkData) {
                Log.d(tag, message)
            }
        }
    }
}