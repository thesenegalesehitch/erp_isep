class Service {
  final int? id;
  final String? title;
  final String? description;
  final String? category;
  final double? price;
  final String? location;
  final bool? isAvailable;
  final double? averageRating;
  final int? totalRatings;

  Service({
    this.id,
    this.title,
    this.description,
    this.category,
    this.price,
    this.location,
    this.isAvailable,
    this.averageRating,
    this.totalRatings,
  });

  factory Service.fromJson(Map<String, dynamic> json) {
    return Service(
      id: json['id'],
      title: json['title'],
      description: json['description'],
      category: json['category'],
      price: json['price']?.toDouble(),
      location: json['location'],
      isAvailable: json['isAvailable'],
      averageRating: json['averageRating']?.toDouble(),
      totalRatings: json['totalRatings'],
    );
  }
}

