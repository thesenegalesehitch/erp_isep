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
  @override
  void initState() {
    super.initState();
    WidgetsBinding.instance.addPostFrameCallback((_) {
      Provider.of<ForumProvider>(context, listen: false).loadForums();
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Forums'),
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
