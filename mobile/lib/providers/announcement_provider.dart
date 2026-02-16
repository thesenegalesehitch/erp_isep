import 'package:flutter/material.dart';
import '../models/announcement.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

class AnnouncementProvider with ChangeNotifier {
  List<Announcement> _announcements = [];
  Announcement? _currentAnnouncement;
  bool _isLoading = false;
  String? _error;

  List<Announcement> get announcements => _announcements;
  Announcement? get currentAnnouncement => _currentAnnouncement;
  bool get isLoading => _isLoading;
  String? get error => _error;

  final String baseUrl = 'http://localhost:8080/api';

  Future<void> loadAnnouncements(String token, {bool publishedOnly = true}) async {
    _isLoading = true;
    _error = null;
    notifyListeners();

    try {
      final queryParams = publishedOnly ? '?published=true' : '';
      final response = await http.get(
        Uri.parse('$baseUrl/announcements$queryParams'),
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer $token',
        },
      );

      if (response.statusCode == 200) {
        final List<dynamic> data = json.decode(response.body);
        _announcements = data.map((json) => Announcement.fromJson(json)).toList();
      } else {
        _error = 'Failed to load announcements';
      }
    } catch (e) {
      _error = 'Error: $e';
    } finally {
      _isLoading = false;
      notifyListeners();
    }
  }

  Future<void> loadAnnouncement(String token, int announcementId) async {
    _isLoading = true;
    _error = null;
    notifyListeners();

    try {
      final response = await http.get(
        Uri.parse('$baseUrl/announcements/$announcementId'),
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer $token',
        },
      );

      if (response.statusCode == 200) {
        final data = json.decode(response.body);
        _currentAnnouncement = Announcement.fromJson(data);
      } else {
        _error = 'Failed to load announcement';
      }
    } catch (e) {
      _error = 'Error: $e';
    } finally {
      _isLoading = false;
      notifyListeners();
    }
  }

  Future<bool> createAnnouncement(String token, Map<String, dynamic> data) async {
    _isLoading = true;
    _error = null;
    notifyListeners();

    try {
      final response = await http.post(
        Uri.parse('$baseUrl/announcements'),
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer $token',
        },
        body: json.encode(data),
      );

      if (response.statusCode == 200 || response.statusCode == 201) {
        await loadAnnouncements(token, publishedOnly: false);
        return true;
      } else {
        _error = 'Failed to create announcement';
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

  Future<bool> updateAnnouncement(String token, int announcementId, Map<String, dynamic> data) async {
    _isLoading = true;
    _error = null;
    notifyListeners();

    try {
      final response = await http.put(
        Uri.parse('$baseUrl/announcements/$announcementId'),
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer $token',
        },
        body: json.encode(data),
      );

      if (response.statusCode == 200) {
        await loadAnnouncements(token, publishedOnly: false);
        return true;
      } else {
        _error = 'Failed to update announcement';
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

  Future<bool> deleteAnnouncement(String token, int announcementId) async {
    _isLoading = true;
    _error = null;
    notifyListeners();

    try {
      final response = await http.delete(
        Uri.parse('$baseUrl/announcements/$announcementId'),
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer $token',
        },
      );

      if (response.statusCode == 200) {
        _announcements.removeWhere((a) => a.id == announcementId);
        return true;
      } else {
        _error = 'Failed to delete announcement';
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

  Future<bool> publishAnnouncement(String token, int announcementId) async {
    _isLoading = true;
    _error = null;
    notifyListeners();

    try {
      final response = await http.post(
        Uri.parse('$baseUrl/announcements/$announcementId/publish'),
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer $token',
        },
      );

      if (response.statusCode == 200) {
        await loadAnnouncements(token);
        return true;
      } else {
        _error = 'Failed to publish announcement';
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
