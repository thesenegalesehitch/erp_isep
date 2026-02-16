import 'package:flutter/foundation.dart';
import '../models/service.dart';
import '../services/api_service.dart';

class ServicesProvider with ChangeNotifier {
  final ApiService _apiService = ApiService();
  
  List<Service> _services = [];
  List<Service> _filteredServices = [];
  bool _isLoading = false;
  String _searchQuery = '';

  List<Service> get services => _filteredServices.isEmpty ? _services : _filteredServices;
  bool get isLoading => _isLoading;

  Future<void> loadServices() async {
    _isLoading = true;
    notifyListeners();

    try {
      final response = await _apiService.get('/services');
      _services = (response as List)
          .map((json) => Service.fromJson(json))
          .toList();
      _filteredServices = _services;
    } catch (e) {
      debugPrint('Error loading services: $e');
    } finally {
      _isLoading = false;
      notifyListeners();
    }
  }

  void searchServices(String query) {
    _searchQuery = query;
    if (query.isEmpty) {
      _filteredServices = _services;
    } else {
      _filteredServices = _services.where((service) {
        return service.title?.toLowerCase().contains(query.toLowerCase()) ?? false;
      }).toList();
    }
    notifyListeners();
  }
}

