package com.arkroot.zendesk_flutter

import ZendeskMessaging
import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

/** ZendeskFlutterPlugin */
class ZendeskFlutterPlugin : FlutterPlugin, MethodCallHandler {
    private val tag = "[ZendeskFlutterPlugin]"

    private lateinit var channel: MethodChannel

    var activity: Activity? = null

    var isInitialize: Boolean = false

    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "zendesk_flutter")
        channel.setMethodCallHandler(this)
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        val sendData: Any? = call.arguments

        val zendeskMessaging = ZendeskMessaging(this, channel)

        if (call.method == "initialize") {
            if (isInitialize) {
                println("$tag - Zendesk is already initialize!")
                return
            }
            val channelKey = call.argument<String>("channelKey")!!
            zendeskMessaging.initialize(channelKey)
        } else if (call.method == "show") {
            if (!isInitialize) {
                println("$tag - Zendesk needs to initialize first")
                return
            }

            zendeskMessaging.show()
        } else {
            result.notImplemented()
        }

        if (sendData != null) {
            result.success(sendData)
        } else {
            result.success(0)
        }
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }

    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
        activity = binding.activity
    }

    override fun onDetachedFromActivityForConfigChanges() {
        activity = null
    }

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
        activity = binding.activity
    }

    override fun onDetachedFromActivity() {
        activity = null
    }
}
