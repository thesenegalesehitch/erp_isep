import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../../providers/activity_provider.dart';
import '../../models/activity.dart';

class ActivityDetailScreen extends StatefulWidget {
  final int activityId;

  const ActivityDetailScreen({super.key, required this.activityId});

  @override
  State<ActivityDetailScreen> createState() => _ActivityDetailScreenState();
}

class _ActivityDetailScreenState extends State<ActivityDetailScreen> {
  @override
  void initState() {
    super.initState();
    WidgetsBinding.instance.addPostFrameCallback((_) {
      final authProvider = Provider.of<ActivityProvider>(context, listen: false);
      final token = authProvider.activities.isNotEmpty ? '' : ''; // Get token from AuthProvider
      // Load activity
      Provider.of<ActivityProvider>(context, listen: false)
          .loadActivity('', widget.activityId);
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Détails de l\'activité'),
      ),
      body: Consumer<ActivityProvider>(
        builder: (context, provider, child) {
          if (provider.isLoading) {
            return const Center(child: CircularProgressIndicator());
          }

          if (provider.currentActivity == null) {
            return const Center(
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Icon(Icons.error, size: 64, color: Colors.red),
                  SizedBox(height: 16),
                  Text('Activité non trouvée'),
                ],
              ),
            );
          }

          final activity = provider.currentActivity!;

          return SingleChildScrollView(
            padding: const EdgeInsets.all(16),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Card(
                  child: Padding(
                    padding: const EdgeInsets.all(16),
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Text(
                          activity.title ?? 'Sans titre',
                          style: Theme.of(context).textTheme.headlineMedium,
                        ),
                        const SizedBox(height: 8),
                        Row(
                          children: [
                            Icon(Icons.category, size: 16, color: Colors.grey),
                            const SizedBox(width: 4),
                            Text(activity.type ?? 'Non catégorisé'),
                          ],
                        ),
                        const SizedBox(height: 16),
                        Text(
                          activity.description ?? 'Aucune description',
                          style: Theme.of(context).textTheme.bodyLarge,
                        ),
                      ],
                    ),
                  ),
                ),
                const SizedBox(height: 16),
                Card(
                  child: Padding(
                    padding: const EdgeInsets.all(16),
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Text(
                          'Informations',
                          style: Theme.of(context).textTheme.titleLarge,
                        ),
                        const Divider(),
                        _buildInfoRow(
                          Icons.calendar_today,
                          'Date de début',
                          activity.startDate != null
                              ? '${activity.startDate!.day}/${activity.startDate!.month}/${activity.startDate!.year}'
                              : 'Non définie',
                        ),
                        _buildInfoRow(
                          Icons.calendar_month,
                          'Date de fin',
                          activity.endDate != null
                              ? '${activity.endDate!.day}/${activity.endDate!.month}/${activity.endDate!.year}'
                              : 'Non définie',
                        ),
                        _buildInfoRow(
                          Icons.location_on,
                          'Lieu',
                          activity.location ?? 'Non défini',
                        ),
                        _buildInfoRow(
                          Icons.people,
                          'Participants',
                          '${activity.currentParticipants ?? 0}/${activity.maxParticipants ?? 'illimité'}',
                        ),
                        _buildInfoRow(
                          Icons.person,
                          'Organisateur',
                          activity.organizerName ?? 'Non défini',
                        ),
                      ],
                    ),
                  ),
                ),
                const SizedBox(height: 16),
                if (activity.hasAvailableSpots == true)
                  SizedBox(
                    width: double.infinity,
                    child: ElevatedButton(
                      onPressed: () async {
                        final success = await provider.registerForActivity(
                          '',
                          widget.activityId,
                        );
                        if (mounted) {
                          ScaffoldMessenger.of(context).showSnackBar(
                            SnackBar(
                              content: Text(
                                success
                                    ? 'Inscription réussie!'
                                    : 'Échec de l\'inscription',
                              ),
                              backgroundColor:
                                  success ? Colors.green : Colors.red,
                            ),
                          );
                        }
                      },
                      child: const Text('S\'inscrire'),
                    ),
                  ),
              ],
            ),
          );
        },
      ),
    );
  }

  Widget _buildInfoRow(IconData icon, String label, String value) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 8),
      child: Row(
        children: [
          Icon(icon, size: 20, color: Colors.blue),
          const SizedBox(width: 12),
          Expanded(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  label,
                  style: TextStyle(fontSize: 12, color: Colors.grey),
                ),
                Text(
                  value,
                  style: const TextStyle(fontSize: 16),
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}
