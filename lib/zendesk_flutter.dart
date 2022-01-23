import 'dart:async';
import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class ZendeskFlutter {
  static const MethodChannel _channel = MethodChannel('zendesk_flutter');

  static Future<void> initialize({
    String androidChannelKey = '',
    String iosChannelKey = '',
  }) async {
    if ((androidChannelKey.isEmpty && Platform.isAndroid) ||
        (iosChannelKey.isEmpty && Platform.isIOS)) {
      debugPrint('Zendesk - initialize - keys can not be empty');
      return;
    }
    Map arguments = {
      'channelKey': Platform.isAndroid ? androidChannelKey : iosChannelKey,
    };
    try {
      await _channel.invokeMethod('initialize', arguments);
    } catch (e) {
      debugPrint('Zendesk - initialize - Error: $e}');
    }
  }

  static Future<void> show() async {
    await _channel.invokeMethod('show');
  }

  static Future<void> addPushToken({String token=''}) async {
    if(token.isEmpty){
      debugPrint('Zendesk - addPushToken - token can not be empty');
      return;
    }
    Map arguments = {
      'pushToken': token,
    };
    await _channel.invokeMethod('addPushToken',arguments);
  }
}
