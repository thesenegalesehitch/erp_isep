import 'package:flutter/foundation.dart';
import '../models/message.dart';
import '../models/conversation.dart';
import '../services/api_service.dart';
import '../services/websocket_service.dart';

class MessagingProvider with ChangeNotifier {
  final ApiService _apiService = ApiService();
  final WebSocketService _websocketService = WebSocketService();
  
  List<Message> _messages = [];
  List<Conversation> _conversations = [];
  Conversation? _selectedConversation;
  bool _isLoading = false;
  bool _isConnected = false;
  int? _currentUserId;

  List<Message> get messages => _messages;
  List<Conversation> get conversations => _conversations;
  Conversation? get selectedConversation => _selectedConversation;
  bool get isLoading => _isLoading;
  bool get isConnected => _isConnected;
  int? get currentUserId => _currentUserId;

  void connect(int userId) {
    _currentUserId = userId;
    _websocketService.connect();
    _websocketService.on('message', _handleMessage);
    _isConnected = true;
    notifyListeners();
  }

  void disconnect() {
    _websocketService.disconnect();
    _isConnected = false;
    notifyListeners();
  }

  void _handleMessage(dynamic data) {
    _messages.add(Message.fromJson(data));
    notifyListeners();
  }

  Future<void> loadConversations() async {
    _isLoading = true;
    notifyListeners();

    try {
      final response = await _apiService.get('/conversations');
      _conversations = (response as List)
          .map((json) => Conversation.fromJson(json))
          .toList();
    } catch (e) {
      debugPrint('Error loading conversations: $e');
    } finally {
      _isLoading = false;
      notifyListeners();
    }
  }

  Future<void> loadMessages(String conversationId) async {
    _isLoading = true;
    notifyListeners();

    try {
      final response = await _apiService.get('/messages/conversation/$conversationId');
      _messages = (response as List)
          .map((json) => Message.fromJson(json))
          .toList();
    } catch (e) {
      debugPrint('Error loading messages: $e');
    } finally {
      _isLoading = false;
      notifyListeners();
    }
  }

  void selectConversation(Conversation conversation) {
    _selectedConversation = conversation;
    loadMessages(conversation.id.toString());
    notifyListeners();
  }

  void clearSelectedConversation() {
    _selectedConversation = null;
    _messages.clear();
    notifyListeners();
  }

  void sendMessage(String conversationId, String content) {
    _websocketService.sendMessage(conversationId, content);
  }
}
