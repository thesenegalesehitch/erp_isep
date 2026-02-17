import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../../providers/forum_provider.dart';
import '../../routes/app_routes.dart';

class ForumScreen extends StatefulWidget {
  const ForumScreen({super.key});

  @override
  State<ForumScreen> createState() => _ForumScreenState();
}

class _ForumScreenState extends State<ForumScreen> {
  String? _selectedSpecialty;

  @override
  void initState() {
    super.initState();
    WidgetsBinding.instance.addPostFrameCallback((_) {
      final provider = Provider.of<ForumProvider>(context, listen: false);
      provider.loadSpecialties();
      provider.loadForums();
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Forums'),
        bottom: PreferredSize(
          preferredSize: const Size.fromHeight(60),
          child: Consumer<ForumProvider>(
            builder: (context, provider, child) {
              if (provider.specialties.isEmpty) return const SizedBox.shrink();
              
              return SizedBox(
                height: 50,
                child: ListView.builder(
                  scrollDirection: Axis.horizontal,
                  padding: const EdgeInsets.symmetric(horizontal: 16),
                  itemCount: provider.specialties.length + 1,
                  itemBuilder: (context, index) {
                    final isAll = index == 0;
                    final specialty = isAll ? null : provider.specialties[index - 1];
                    final isSelected = _selectedSpecialty == specialty;
                    
                    return Padding(
                      padding: const EdgeInsets.only(right: 8, bottom: 8),
                      child: FilterChip(
                        label: Text(isAll ? 'Tous' : specialty!),
                        selected: isSelected,
                        onSelected: (selected) {
                          setState(() {
                            _selectedSpecialty = isAll ? null : specialty;
                          });
                          provider.loadForums(specialty: _selectedSpecialty);
                        },
                      ),
                    );
                  },
                ),
              );
            },
          ),
        ),
      ),
      body: Consumer<ForumProvider>(
        builder: (context, provider, child) {
          if (provider.isLoading) {
            return const Center(child: CircularProgressIndicator());
          }

          if (provider.forums.isEmpty) {
            return const Center(
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Icon(Icons.forum, size: 64, color: Colors.grey),
                  SizedBox(height: 16),
                  Text('Aucun forum disponible'),
                ],
              ),
            );
          }

          return ListView.builder(
            itemCount: provider.forums.length,
            itemBuilder: (context, index) {
              final forum = provider.forums[index];
              return Card(
                margin: const EdgeInsets.symmetric(horizontal: 16, vertical: 8),
                child: ListTile(
                  leading: const CircleAvatar(
                    child: Icon(Icons.forum),
                  ),
                  title: Text(forum.name ?? 'Forum'),
                  subtitle: Text(forum.description ?? ''),
                  trailing: Chip(
                    label: Text('${forum.postCount ?? 0} posts'),
                  ),
                  onTap: () {
                    // Navigate to forum posts
                    Navigator.of(context).pushNamed(AppRoutes.messaging);
                  },
                ),
              );
            },
          );
        },
      ),
    );
  }
}
