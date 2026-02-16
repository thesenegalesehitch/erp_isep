class Announcement {
  final int? id;
  final String? title;
  final String? content;
  final String? type;
  final String? priority;
  final int? authorId;
  final String? authorName;
  final List<String>? attachments;
  final DateTime? expiresAt;
  final bool? isPublished;
  final DateTime? publishedAt;
  final DateTime? createdAt;
  final DateTime? updatedAt;

  Announcement({
    this.id,
    this.title,
    this.content,
    this.type,
    this.priority,
    this.authorId,
    this.authorName,
    this.attachments,
    this.expiresAt,
    this.isPublished,
    this.publishedAt,
    this.createdAt,
    this.updatedAt,
  });

  factory Announcement.fromJson(Map<String, dynamic> json) {
    return Announcement(
      id: json['id'],
      title: json['title'],
      content: json['content'],
      type: json['type'],
      priority: json['priority'],
      authorId: json['authorId'],
      authorName: json['authorName'],
      attachments: json['attachments'] != null
          ? List<String>.from(json['attachments'])
          : null,
      expiresAt: json['expiresAt'] != null ? DateTime.parse(json['expiresAt']) : null,
      isPublished: json['isPublished'],
      publishedAt: json['publishedAt'] != null ? DateTime.parse(json['publishedAt']) : null,
      createdAt: json['createdAt'] != null ? DateTime.parse(json['createdAt']) : null,
      updatedAt: json['updatedAt'] != null ? DateTime.parse(json['updatedAt']) : null,
    );
  }
}
