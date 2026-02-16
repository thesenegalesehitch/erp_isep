import 'package:flutter/material.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';

class BusScreen extends StatefulWidget {
  const BusScreen({super.key});

  @override
  State<BusScreen> createState() => _BusScreenState();
}

class _BusScreenState extends State<BusScreen> {
  GoogleMapController? _mapController;
  final Set<Marker> _markers = {};
  final LatLng _center = const LatLng(14.7167, -17.4677);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Bus de Ramassage'),
      ),
      body: Column(
        children: [
          Expanded(
            child: GoogleMap(
              initialCameraPosition: CameraPosition(
                target: _center,
                zoom: 12.0,
              ),
              markers: _markers,
              onMapCreated: (GoogleMapController controller) {
                _mapController = controller;
              },
            ),
          ),
          Container(
            height: 200,
            padding: const EdgeInsets.all(16.0),
            child: ListView(
              children: [
                // TODO: Load and display buses
                const Card(
                  child: ListTile(
                    leading: Icon(Icons.directions_bus),
                    title: Text('Ligne 1'),
                    subtitle: Text('Places disponibles: 10/50'),
                  ),
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}

