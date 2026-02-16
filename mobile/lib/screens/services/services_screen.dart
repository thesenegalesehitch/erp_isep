import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../../providers/services_provider.dart';

class ServicesScreen extends StatefulWidget {
  const ServicesScreen({super.key});

  @override
  State<ServicesScreen> createState() => _ServicesScreenState();
}

class _ServicesScreenState extends State<ServicesScreen> {
  String _selectedCategory = '';
  final _searchController = TextEditingController();

  @override
  void initState() {
    super.initState();
    WidgetsBinding.instance.addPostFrameCallback((_) {
      Provider.of<ServicesProvider>(context, listen: false).loadServices();
    });
  }

  @override
  void dispose() {
    _searchController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Services Étudiants'),
        actions: [
          IconButton(
            icon: const Icon(Icons.add),
            onPressed: () {
              // TODO: Navigate to create service
            },
          ),
        ],
      ),
      body: Column(
        children: [
          Padding(
            padding: const EdgeInsets.all(16.0),
            child: Column(
              children: [
                TextField(
                  controller: _searchController,
                  decoration: const InputDecoration(
                    labelText: 'Rechercher',
                    prefixIcon: Icon(Icons.search),
                    border: OutlineInputBorder(),
                  ),
                  onChanged: (value) {
                    Provider.of<ServicesProvider>(context, listen: false)
                        .searchServices(value);
                  },
                ),
                const SizedBox(height: 16),
                // Category filter dropdown
              ],
            ),
          ),
          Expanded(
            child: Consumer<ServicesProvider>(
              builder: (context, provider, child) {
                if (provider.isLoading) {
                  return const Center(child: CircularProgressIndicator());
                }

                if (provider.services.isEmpty) {
                  return const Center(
                    child: Text('Aucun service trouvé'),
                  );
                }

                return ListView.builder(
                  itemCount: provider.services.length,
                  itemBuilder: (context, index) {
                    final service = provider.services[index];
                    return Card(
                      margin: const EdgeInsets.symmetric(
                        horizontal: 16,
                        vertical: 8,
                      ),
                      child: ListTile(
                        title: Text(service.title ?? ''),
                        subtitle: Text(service.description ?? ''),
                        trailing: service.price != null
                            ? Text('${service.price} FCFA')
                            : null,
                        onTap: () {
                          // TODO: Navigate to service details
                        },
                      ),
                    );
                  },
                );
              },
            ),
          ),
        ],
      ),
    );
  }
}

