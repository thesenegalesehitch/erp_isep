class ServiceRequest {
  final int? id;
  final int? serviceId;
  final String? serviceTitle;
  final int? requesterId;
  final String? requesterName;
  final String? message;
  final DateTime? requestedDate;
  final String? status;
  final DateTime? createdAt;
  final DateTime? updatedAt;

  ServiceRequest({
    this.id,
    this.serviceId,
    this.serviceTitle,
    this.requesterId,
    this.requesterName,
    this.message,
    this.requestedDate,
    this.status,
    this.createdAt,
    this.updatedAt,
  });

  factory ServiceRequest.fromJson(Map<String, dynamic> json) {
    return ServiceRequest(
      id: json['id'],
      serviceId: json['serviceId'],
      serviceTitle: json['serviceTitle'],
      requesterId: json['requesterId'],
      requesterName: json['requesterName'],
      message: json['message'],
      requestedDate: json['requestedDate'] != null ? DateTime.parse(json['requestedDate']) : null,
      status: json['status'],
      createdAt: json['createdAt'] != null ? DateTime.parse(json['createdAt']) : null,
      updatedAt: json['updatedAt'] != null ? DateTime.parse(json['updatedAt']) : null,
    );
  }
}

class ServiceRating {
  final int? id;
  final int? serviceId;
  final String? serviceTitle;
  final int? userId;
  final String? userName;
  final int? rating;
  final String? comment;
  final DateTime? createdAt;

  ServiceRating({
    this.id,
    this.serviceId,
    this.serviceTitle,
    this.userId,
    this.userName,
    this.rating,
    this.comment,
    this.createdAt,
  });

  factory ServiceRating.fromJson(Map<String, dynamic> json) {
    return ServiceRating(
      id: json['id'],
      serviceId: json['serviceId'],
      serviceTitle: json['serviceTitle'],
      userId: json['userId'],
      userName: json['userName'],
      rating: json['rating'],
      comment: json['comment'],
      createdAt: json['createdAt'] != null ? DateTime.parse(json['createdAt']) : null,
    );
  }
}
