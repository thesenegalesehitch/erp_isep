class Conversation {
  final int? id;
  final String? name;
  final String? type;
  final List<int>? participantIds;
  final String? lastMessage;
  final DateTime? lastMessageAt;
  final DateTime? createdAt;
  final DateTime? updatedAt;

  Conversation({
    this.id,
    this.name,
    this.type,
    this.participantIds,
    this.lastMessage,
    this.lastMessageAt,
    this.createdAt,
    this.updatedAt,
  });

  factory Conversation.fromJson(Map<String, dynamic> json) {
    return Conversation(
      id: json['id'],
      name: json['name'],
      type: json['type'],
      participantIds: json['participantIds'] != null
          ? List<int>.from(json['participantIds'])
          : null,
      lastMessage: json['lastMessage'],
      lastMessageAt: json['lastMessageAt'] != null
          ? DateTime.parse(json['lastMessageAt'])
          : null,
      createdAt: json['createdAt'] != null
          ? DateTime.parse(json['createdAt'])
          : null,
      updatedAt: json['updatedAt'] != null
          ? DateTime.parse(json['updatedAt'])
          : null,
    );
  }
}
