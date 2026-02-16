import 'package:flutter/foundation.dart';
import '../models/user.dart';
import '../services/api_service.dart';

class AuthProvider with ChangeNotifier {
  final ApiService _apiService = ApiService();
  
  User? _user;
  String? _token;
  bool _isAuthenticated = false;
  bool _isLoading = false;

  User? get user => _user;
  String? get token => _token;
  bool get isAuthenticated => _isAuthenticated;
  bool get isLoading => _isLoading;

  Future<void> login(String email, String password) async {
    _isLoading = true;
    notifyListeners();

    try {
      final response = await _apiService.post('/auth/login', {
        'email': email,
        'password': password,
      });

      _token = response['accessToken'];
      _user = User.fromJson(response['user']);
      _isAuthenticated = true;

      await _apiService.saveToken(_token!);
    } catch (e) {
      _isAuthenticated = false;
      rethrow;
    } finally {
      _isLoading = false;
      notifyListeners();
    }
  }

  Future<void> register({
    required String firstName,
    required String lastName,
    required String email,
    required String password,
    String? studentNumber,
    String role = 'STUDENT',
  }) async {
    _isLoading = true;
    notifyListeners();

    try {
      final response = await _apiService.post('/auth/register', {
        'firstName': firstName,
        'lastName': lastName,
        'email': email,
        'password': password,
        'studentNumber': studentNumber,
        'role': role,
      });

      _token = response['accessToken'];
      _user = User.fromJson(response['user']);
      _isAuthenticated = true;

      await _apiService.saveToken(_token!);
    } catch (e) {
      _isAuthenticated = false;
      rethrow;
    } finally {
      _isLoading = false;
      notifyListeners();
    }
  }

  void logout() {
    _user = null;
    _token = null;
    _isAuthenticated = false;
    _apiService.clearToken();
    notifyListeners();
  }

  Future<void> checkAuth() async {
    final token = await _apiService.getToken();
    if (token != null) {
      _token = token;
      _isAuthenticated = true;
      // TODO: Load user data
      notifyListeners();
    }
  }
}

