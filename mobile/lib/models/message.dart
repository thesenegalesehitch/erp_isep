class Message {
  final int? id;
  final String content;
  final String? type;
  final int? senderId;
  final String? senderName;
  final int? receiverId;
  final int? conversationId;
  final bool? isRead;
  final String? createdAt;

  Message({
    this.id,
    required this.content,
    this.type,
    this.senderId,
    this.senderName,
    this.receiverId,
    this.conversationId,
    this.isRead,
    this.createdAt,
  });

  factory Message.fromJson(Map<String, dynamic> json) {
    return Message(
      id: json['id'],
      content: json['content'] ?? '',
      type: json['type'],
      senderId: json['sender']?['id'] ?? json['senderId'],
      senderName: json['sender']?['firstName'] ?? json['senderName'],
      receiverId: json['receiver']?['id'] ?? json['receiverId'],
      conversationId: json['conversation']?['id'] ?? json['conversationId'],
      isRead: json['isRead'],
      createdAt: json['createdAt'],
    );
  }
}
