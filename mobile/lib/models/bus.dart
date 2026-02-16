class Bus {
  final int? id;
  final String? lineNumber;
  final String? driverName;
  final String? driverPhone;
  final int? totalSeats;
  final int? availableSeats;
  final double? currentLocationLat;
  final double? currentLocationLng;
  final String? status;
  final String? departureTime;
  final String? arrivalTime;
  final String? routeDescription;
  final DateTime? createdAt;
  final DateTime? updatedAt;

  Bus({
    this.id,
    this.lineNumber,
    this.driverName,
    this.driverPhone,
    this.totalSeats,
    this.availableSeats,
    this.currentLocationLat,
    this.currentLocationLng,
    this.status,
    this.departureTime,
    this.arrivalTime,
    this.routeDescription,
    this.createdAt,
    this.updatedAt,
  });

  factory Bus.fromJson(Map<String, dynamic> json) {
    return Bus(
      id: json['id'],
      lineNumber: json['lineNumber'],
      driverName: json['driverName'],
      driverPhone: json['driverPhone'],
      totalSeats: json['totalSeats'],
      availableSeats: json['availableSeats'],
      currentLocationLat: json['currentLocationLat']?.toDouble(),
      currentLocationLng: json['currentLocationLng']?.toDouble(),
      status: json['status'],
      departureTime: json['departureTime'],
      arrivalTime: json['arrivalTime'],
      routeDescription: json['routeDescription'],
      createdAt: json['createdAt'] != null ? DateTime.parse(json['createdAt']) : null,
      updatedAt: json['updatedAt'] != null ? DateTime.parse(json['updatedAt']) : null,
    );
  }
}
