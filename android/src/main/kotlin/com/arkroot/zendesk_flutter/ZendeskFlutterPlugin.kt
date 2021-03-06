package com.arkroot.zendesk_flutter

import ZendeskMessaging
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import zendesk.messaging.android.push.PushNotifications
import zendesk.messaging.android.push.PushResponsibility.MESSAGING_SHOULD_DISPLAY
import zendesk.messaging.android.push.PushResponsibility.MESSAGING_SHOULD_NOT_DISPLAY
import zendesk.messaging.android.push.PushResponsibility.NOT_FROM_MESSAGING


/** ZendeskFlutterPlugin */
//class ZendeskFlutterPlugin : FlutterPlugin, MethodCallHandler, ActivityAware, FirebaseMessagingService() {
    class ZendeskFlutterPlugin : FlutterPlugin, MethodCallHandler, ActivityAware {
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
        } else if (call.method == "addPushToken") {
            if (!isInitialize) {
                println("$tag - Zendesk needs to initialize first")
                return
            }
            val pushToken = call.argument<String>("pushToken")!!
            zendeskMessaging.onNewToken(pushToken)
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

//    override fun onNewToken(newToken: String) {
//        println("$tag - onNewToken")
//        PushNotifications.updatePushNotificationToken(newToken)
//        println("$tag - onNewToken.end")
//    }
//
//    override fun onMessageReceived(remoteMessage: RemoteMessage) {
//        val responsibility = PushNotifications.shouldBeDisplayed(remoteMessage.data)
//        when (responsibility) {
//            MESSAGING_SHOULD_DISPLAY -> {
//                Log.d("Zendesk Flutter","MESSAGING_SHOULD_DISPLAY")
//                // This push belongs to Messaging and the SDK is able to display it to the end user
//                PushNotifications.displayNotification(context = this, messageData = remoteMessage.data)
//            }
//            MESSAGING_SHOULD_NOT_DISPLAY -> {
//                Log.d("Zendesk Flutter","MESSAGING_SHOULD_NOT_DISPLAY")
//                // This push belongs to Messaging but it should not be displayed to the end user
//            }
//            NOT_FROM_MESSAGING -> {
//                Log.d("Zendesk Flutter","NOT_FROM_MESSAGING")
//                // This push does not belong to Messaging
//            }
//        }
//    }
}
