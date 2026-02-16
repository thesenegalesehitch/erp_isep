class User {
  final int? id;
  final String email;
  final String? firstName;
  final String? lastName;
  final String? studentNumber;
  final String? role;
  final String? specialty;
  final String? profilePhoto;

  User({
    this.id,
    required this.email,
    this.firstName,
    this.lastName,
    this.studentNumber,
    this.role,
    this.specialty,
    this.profilePhoto,
  });

  factory User.fromJson(Map<String, dynamic> json) {
    return User(
      id: json['id'],
      email: json['email'] ?? '',
      firstName: json['firstName'],
      lastName: json['lastName'],
      studentNumber: json['studentNumber'],
      role: json['role'],
      specialty: json['specialty'],
      profilePhoto: json['profilePhoto'],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'email': email,
      'firstName': firstName,
      'lastName': lastName,
      'studentNumber': studentNumber,
      'role': role,
      'specialty': specialty,
      'profilePhoto': profilePhoto,
    };
  }
}

