import 'dart:async';
import 'dart:convert';
import 'package:stomp_dart_client/stomp.dart';
import 'package:stomp_dart_client/stomp_config.dart';
import 'package:stomp_dart_client/stomp_frame.dart';
import '../services/api_service.dart';

class WebSocketService {
  StompClient? _client;
  final ApiService _apiService = ApiService();
  final Map<String, Function(dynamic)> _listeners = {};

  void connect() async {
    final token = await _apiService.getToken();
    
    _client = StompClient(
      config: StompConfig(
        url: 'ws://localhost:8080/ws',
        onConnect: onConnect,
        beforeConnect: () async {
          print('waiting to connect...');
          await Future.delayed(const Duration(milliseconds: 200));
          print('connecting...');
        },
        onWebSocketError: (dynamic error) => print(error.toString()),
        stompConnectHeaders: {'Authorization': 'Bearer $token'},
        webSocketConnectHeaders: {'Authorization': 'Bearer $token'},
      ),
    );

    _client!.activate();
  }

  void onConnect(StompFrame frame) {
    print('WebSocket connected');
    
    // Subscribe to public topics
    _client!.subscribe(
      destination: '/topic/public',
      callback: (frame) {
        if (frame.body != null) {
          emit('message', json.decode(frame.body!));
        }
      },
    );

    _client!.subscribe(
      destination: '/topic/bus-updates',
      callback: (frame) {
        if (frame.body != null) {
          emit('busUpdate', json.decode(frame.body!));
        }
      },
    );
  }

  void disconnect() {
    _client?.deactivate();
    _client = null;
  }

  void on(String event, Function(dynamic) callback) {
    _listeners[event] = callback;
  }

  void off(String event) {
    _listeners.remove(event);
  }

  void emit(String event, dynamic data) {
    if (_listeners.containsKey(event)) {
      _listeners[event]!(data);
    }
  }

  void sendMessage(String conversationId, String content, {String type = 'TEXT'}) {
    _client?.send(
      destination: '/app/chat.sendMessage',
      body: json.encode({
        'conversationId': conversationId,
        'content': content,
        'type': type,
      }),
    );
  }

  void joinConversation(String conversationId) {
    _client?.send(
      destination: '/app/chat.addUser',
      body: json.encode({'conversationId': conversationId}),
    );
  }

  void leaveConversation(String conversationId) {
    // Implement if backend supports
  }
}

