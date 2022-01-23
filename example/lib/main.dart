import 'package:flutter/material.dart';
import 'package:zendesk_flutter/zendesk_flutter.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  final String androidChannelKey =
      "eyJzZXR0aW5nc191cmwiOiJodHRwczovL2JlYXJjb3Zlci56ZW5kZXNrLmNvbS9tb2JpbGVfc2RrX2FwaS9zZXR0aW5ncy8wMUZCUzFNRjFOMjdLMzJQSDdTVEs3VEsxWS5qc29uIn0=";
  final String iosChannelKey =
      'eyJzZXR0aW5nc191cmwiOiJodHRwczovL2hhbmFtaWhlbHAuemVuZGVzay5jb20vbW9iaWxlX3Nka19hcGkvc2V0dGluZ3MvMDFGR1BGVFQ1Q1hFRjdRWVkwUkg2R0JYS0MuanNvbiJ9';

  @override
  void initState() {
    super.initState();
    ZendeskFlutter.initialize(
      androidChannelKey: androidChannelKey,
      iosChannelKey: iosChannelKey,
    );
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Zendesk Messaging Example'),
        ),
        body: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              TextButton(
                onPressed: () {
                  ZendeskFlutter.show();
                },
                child: const Text(
                  "Messaging",
                  style: TextStyle(
                    color: Colors.blue,
                    fontSize: 20.0,
                  ),
                ),
              ),
              const SizedBox(height: 30),
              TextButton(
                onPressed: () {
                  ZendeskFlutter.addPushToken(token: '');
                },
                child: const Text(
                  "Add Token",
                  style: TextStyle(
                    color: Colors.blue,
                    fontSize: 20.0,
                  ),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
