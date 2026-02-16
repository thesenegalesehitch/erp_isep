import 'package:socket_io_client/socket_io_client.dart' as IO;
import '../services/api_service.dart';

class WebSocketService {
  IO.Socket? _socket;
  final ApiService _apiService = ApiService();

  void connect() async {
    final token = await _apiService.getToken();
    
    _socket = IO.io('http://localhost:8080', <String, dynamic>{
      'transports': ['websocket'],
      'autoConnect': false,
      'auth': {'token': token},
    });

    _socket!.connect();

    _socket!.on('connect', (_) {
      print('WebSocket connected');
    });

    _socket!.on('disconnect', (_) {
      print('WebSocket disconnected');
    });
  }

  void disconnect() {
    _socket?.disconnect();
    _socket = null;
  }

  void on(String event, Function(dynamic) callback) {
    _socket?.on(event, callback);
  }

  void off(String event) {
    _socket?.off(event);
  }

  void emit(String event, dynamic data) {
    _socket?.emit(event, data);
  }

  void sendMessage(String conversationId, String content, {String type = 'TEXT'}) {
    emit('sendMessage', {
      'conversationId': conversationId,
      'content': content,
      'type': type,
    });
  }

  void joinConversation(String conversationId) {
    emit('joinConversation', conversationId);
  }

  void leaveConversation(String conversationId) {
    emit('leaveConversation', conversationId);
  }
}

