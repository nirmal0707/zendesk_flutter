import android.content.Intent
import android.util.Log
import com.arkroot.zendesk_flutter.ZendeskFlutterPlugin
import io.flutter.plugin.common.MethodChannel
import zendesk.messaging.android.FailureCallback
import zendesk.messaging.android.Messaging
import zendesk.messaging.android.MessagingError
import zendesk.messaging.android.SuccessCallback
import zendesk.messaging.android.push.PushNotifications

class ZendeskMessaging(private val plugin: ZendeskFlutterPlugin, private val channel: MethodChannel) {

    private val tag = "[ZendeskMessaging]"

    fun initialize(channelKey: String) {
        Messaging.initialize(
                plugin.activity!!,
                channelKey, successCallback = object : SuccessCallback<Messaging>{
            override fun onSuccess(value: Messaging) {
                plugin.isInitialize = true;

                println("$tag - initialize success - $value")
            }
        }, failureCallback = object: FailureCallback<MessagingError>{
            override fun onFailure(error: MessagingError?) {
                plugin.isInitialize = false;
                println("$tag - initialize failure - $error")
            }
        })
    }

    fun show() {
        Messaging.instance().showMessaging(plugin.activity!!, Intent.FLAG_ACTIVITY_NEW_TASK)
        println("$tag - show")
    }

    fun onNewToken(newToken: String) {
        PushNotifications.updatePushNotificationToken(newToken)
        println("$tag - onNewToken")
    }
}