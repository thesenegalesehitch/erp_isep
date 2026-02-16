import 'package:flutter/material.dart';
import '../models/activity.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';
import 'auth_provider.dart';

class ActivityProvider with ChangeNotifier {
  List<Activity> _activities = [];
  List<Activity> _myActivities = [];
  Activity? _currentActivity;
  bool _isLoading = false;
  String? _error;

  List<Activity> get activities => _activities;
  List<Activity> get myActivities => _myActivities;
  Activity? get currentActivity => _currentActivity;
  bool get isLoading => _isLoading;
  String? get error => _error;

  final String baseUrl = 'http://localhost:8080/api';

  Future<void> loadActivities(String token) async {
    _isLoading = true;
    _error = null;
    notifyListeners();

    try {
      final response = await http.get(
        Uri.parse('$baseUrl/activities'),
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer $token',
        },
      );

      if (response.statusCode == 200) {
        final List<dynamic> data = json.decode(response.body);
        _activities = data.map((json) => Activity.fromJson(json)).toList();
      } else {
        _error = 'Failed to load activities';
      }
    } catch (e) {
      _error = 'Error: $e';
    } finally {
      _isLoading = false;
      notifyListeners();
    }
  }

  Future<void> loadMyActivities(String token) async {
    _isLoading = true;
    _error = null;
    notifyListeners();

    try {
      final response = await http.get(
        Uri.parse('$baseUrl/activities/my'),
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer $token',
        },
      );

      if (response.statusCode == 200) {
        final List<dynamic> data = json.decode(response.body);
        _myActivities = data.map((json) => Activity.fromJson(json)).toList();
      } else {
        _error = 'Failed to load my activities';
      }
    } catch (e) {
      _error = 'Error: $e';
    } finally {
      _isLoading = false;
      notifyListeners();
    }
  }

  Future<void> loadActivity(String token, int activityId) async {
    _isLoading = true;
    _error = null;
    notifyListeners();

    try {
      final response = await http.get(
        Uri.parse('$baseUrl/activities/$activityId'),
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer $token',
        },
      );

      if (response.statusCode == 200) {
        final data = json.decode(response.body);
        _currentActivity = Activity.fromJson(data);
      } else {
        _error = 'Failed to load activity';
      }
    } catch (e) {
      _error = 'Error: $e';
    } finally {
      _isLoading = false;
      notifyListeners();
    }
  }

  Future<bool> registerForActivity(String token, int activityId) async {
    _isLoading = true;
    _error = null;
    notifyListeners();

    try {
      final response = await http.post(
        Uri.parse('$baseUrl/activities/$activityId/register'),
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer $token',
        },
      );

      if (response.statusCode == 200 || response.statusCode == 201) {
        await loadActivities(token);
        return true;
      } else {
        _error = 'Failed to register for activity';
        return false;
      }
    } catch (e) {
      _error = 'Error: $e';
      return false;
    } finally {
      _isLoading = false;
      notifyListeners();
    }
  }

  Future<bool> unregisterFromActivity(String token, int activityId) async {
    _isLoading = true;
    _error = null;
    notifyListeners();

    try {
      final response = await http.delete(
        Uri.parse('$baseUrl/activities/$activityId/unregister'),
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer $token',
        },
      );

      if (response.statusCode == 200) {
        await loadActivities(token);
        return true;
      } else {
        _error = 'Failed to unregister from activity';
        return false;
      }
    } catch (e) {
      _error = 'Error: $e';
      return false;
    } finally {
      _isLoading = false;
      notifyListeners();
    }
  }

  void clearError() {
    _error = null;
    notifyListeners();
  }
}
