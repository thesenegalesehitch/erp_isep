import 'package:flutter/foundation.dart';
import '../models/forum.dart';
import '../services/api_service.dart';

class ForumProvider with ChangeNotifier {
  final ApiService _apiService = ApiService();
  
  List<Forum> _forums = [];
  List<String> _specialties = [];
  List<ForumPost> _posts = [];
  bool _isLoading = false;
  
  List<Forum> get forums => _forums;
  List<String> get specialties => _specialties;
  List<ForumPost> get posts => _posts;
  bool get isLoading => _isLoading;
  
  Future<void> loadSpecialties() async {
    try {
      final response = await _apiService.get('/forums/specialties');
      _specialties = List<String>.from(response);
      notifyListeners();
    } catch (e) {
      debugPrint('Error loading specialties: $e');
    }
  }

  Future<void> loadForums({String? specialty}) async {
    _isLoading = true;
    notifyListeners();
    
    try {
      final endpoint = specialty != null ? '/forums?specialty=$specialty' : '/forums';
      final response = await _apiService.get(endpoint);
      _forums = (response as List)
          .map((json) => Forum.fromJson(json))
          .toList();
    } catch (e) {
      debugPrint('Error loading forums: $e');
    } finally {
      _isLoading = false;
      notifyListeners();
    }
  }
  
  Future<List<ForumPost>> loadForumPosts(int forumId) async {
    try {
      final response = await _apiService.get('/forums/$forumId/posts');
      _posts = (response as List)
          .map((json) => ForumPost.fromJson(json))
          .toList();
      notifyListeners();
      return _posts;
    } catch (e) {
      debugPrint('Error loading forum posts: $e');
      return [];
    }
  }
  
  Future<ForumPost> createPost(int forumId, String title, String content) async {
    try {
      final response = await _apiService.post('/forums/$forumId/posts', {
        'title': title,
        'content': content,
      });
      final post = ForumPost.fromJson(response);
      _posts.insert(0, post);
      notifyListeners();
      return post;
    } catch (e) {
      debugPrint('Error creating post: $e');
      rethrow;
    }
  }
  
  Future<ForumPost> replyToPost(int postId, String content) async {
    try {
      final response = await _apiService.post('/forums/posts/$postId/reply', {
        'content': content,
      });
      final post = ForumPost.fromJson(response);
      _posts.add(post);
      notifyListeners();
      return post;
    } catch (e) {
      debugPrint('Error replying to post: $e');
      rethrow;
    }
  }
}
