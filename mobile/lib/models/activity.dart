class Activity {
  final int? id;
  final String? title;
  final String? description;
  final String? type;
  final DateTime? startDate;
  final DateTime? endDate;
  final String? location;
  final int? maxParticipants;
  final int? currentParticipants;
  final int? organizerId;
  final String? organizerName;
  final bool? isActive;
  final bool? hasAvailableSpots;
  final DateTime? createdAt;
  final DateTime? updatedAt;

  Activity({
    this.id,
    this.title,
    this.description,
    this.type,
    this.startDate,
    this.endDate,
    this.location,
    this.maxParticipants,
    this.currentParticipants,
    this.organizerId,
    this.organizerName,
    this.isActive,
    this.hasAvailableSpots,
    this.createdAt,
    this.updatedAt,
  });

  factory Activity.fromJson(Map<String, dynamic> json) {
    return Activity(
      id: json['id'],
      title: json['title'],
      description: json['description'],
      type: json['type'],
      startDate: json['startDate'] != null ? DateTime.parse(json['startDate']) : null,
      endDate: json['endDate'] != null ? DateTime.parse(json['endDate']) : null,
      location: json['location'],
      maxParticipants: json['maxParticipants'],
      currentParticipants: json['currentParticipants'],
      organizerId: json['organizerId'],
      organizerName: json['organizerName'],
      isActive: json['isActive'],
      hasAvailableSpots: json['hasAvailableSpots'],
      createdAt: json['createdAt'] != null ? DateTime.parse(json['createdAt']) : null,
      updatedAt: json['updatedAt'] != null ? DateTime.parse(json['updatedAt']) : null,
    );
  }
}
