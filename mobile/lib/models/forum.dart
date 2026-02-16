class Forum {
  final int? id;
  final String? name;
  final String? description;
  final String? specialty;
  final int? moderatorId;
  final String? moderatorName;
  final int? postCount;
  final bool? isActive;
  final DateTime? createdAt;
  final DateTime? updatedAt;

  Forum({
    this.id,
    this.name,
    this.description,
    this.specialty,
    this.moderatorId,
    this.moderatorName,
    this.postCount,
    this.isActive,
    this.createdAt,
    this.updatedAt,
  });

  factory Forum.fromJson(Map<String, dynamic> json) {
    return Forum(
      id: json['id'],
      name: json['name'],
      description: json['description'],
      specialty: json['specialty'],
      moderatorId: json['moderatorId'],
      moderatorName: json['moderatorName'],
      postCount: json['postCount'],
      isActive: json['isActive'],
      createdAt: json['createdAt'] != null ? DateTime.parse(json['createdAt']) : null,
      updatedAt: json['updatedAt'] != null ? DateTime.parse(json['updatedAt']) : null,
    );
  }
}

class ForumPost {
  final int? id;
  final int? forumId;
  final String? forumName;
  final int? authorId;
  final String? authorName;
  final String? title;
  final String? content;
  final int? parentPostId;
  final int? replyCount;
  final DateTime? createdAt;
  final DateTime? updatedAt;

  ForumPost({
    this.id,
    this.forumId,
    this.forumName,
    this.authorId,
    this.authorName,
    this.title,
    this.content,
    this.parentPostId,
    this.replyCount,
    this.createdAt,
    this.updatedAt,
  });

  factory ForumPost.fromJson(Map<String, dynamic> json) {
    return ForumPost(
      id: json['id'],
      forumId: json['forumId'],
      forumName: json['forumName'],
      authorId: json['authorId'],
      authorName: json['authorName'],
      title: json['title'],
      content: json['content'],
      parentPostId: json['parentPostId'],
      replyCount: json['replyCount'],
      createdAt: json['createdAt'] != null ? DateTime.parse(json['createdAt']) : null,
      updatedAt: json['updatedAt'] != null ? DateTime.parse(json['updatedAt']) : null,
    );
  }
}
