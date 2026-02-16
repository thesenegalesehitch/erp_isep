import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../../providers/messaging_provider.dart';
import '../../routes/app_routes.dart';

class MessagingScreen extends StatefulWidget {
  const MessagingScreen({super.key});

  @override
  State<MessagingScreen> createState() => _MessagingScreenState();
}

class _MessagingScreenState extends State<MessagingScreen> {
  final TextEditingController _messageController = TextEditingController();

  @override
  void initState() {
    super.initState();
    WidgetsBinding.instance.addPostFrameCallback((_) {
      Provider.of<MessagingProvider>(context, listen: false).loadConversations();
    });
  }

  @override
  void dispose() {
    _messageController.dispose();
    super.dispose();
  }

  void _sendMessage() {
    if (_messageController.text.trim().isEmpty) return;

    final provider = Provider.of<MessagingProvider>(context, listen: false);
    if (provider.selectedConversation != null && provider.selectedConversation!.id != null) {
      provider.sendMessage(
        provider.selectedConversation!.id.toString(),
        _messageController.text,
      );
      _messageController.clear();
    }
  }

  @override
  Widget build(BuildContext context) {
    return Consumer<MessagingProvider>(
      builder: (context, provider, child) {
        return Scaffold(
          appBar: AppBar(
            title: Text(
              provider.selectedConversation?.name ?? 'Conversations',
            ),
            actions: provider.selectedConversation != null
                ? [
                    IconButton(
                      icon: const Icon(Icons.arrow_back),
                      onPressed: () {
                        provider.clearSelectedConversation();
                      },
                    ),
                  ]
                : null,
          ),
          body: provider.selectedConversation == null
              ? _buildConversationList(provider)
              : _buildChatView(provider),
        );
      },
    );
  }

  Widget _buildConversationList(MessagingProvider provider) {
    if (provider.isLoading) {
      return const Center(child: CircularProgressIndicator());
    }

    if (provider.conversations.isEmpty) {
      return const Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(Icons.message, size: 64, color: Colors.grey),
            SizedBox(height: 16),
            Text('Aucune conversation'),
          ],
        ),
      );
    }

    return ListView.builder(
      itemCount: provider.conversations.length,
      itemBuilder: (context, index) {
        final conversation = provider.conversations[index];
        return ListTile(
          leading: const CircleAvatar(
            child: Icon(Icons.person),
          ),
          title: Text(conversation.name ?? 'Conversation'),
          subtitle: Text(
            conversation.lastMessageAt != null
                ? _formatDate(conversation.lastMessageAt!)
                : '',
          ),
          onTap: () {
            provider.selectConversation(conversation);
          },
        );
      },
    );
  }

  Widget _buildChatView(MessagingProvider provider) {
    final conversation = provider.selectedConversation;
    if (conversation == null) return const SizedBox.shrink();

    return Column(
      children: [
        Expanded(
          child: ListView.builder(
            padding: const EdgeInsets.all(16),
            itemCount: provider.messages.length,
            itemBuilder: (context, index) {
              final message = provider.messages[index];
              final isMe = message.senderId == provider.currentUserId;

              return Align(
                alignment: isMe ? Alignment.centerRight : Alignment.centerLeft,
                child: Container(
                  margin: const EdgeInsets.only(bottom: 8),
                  padding: const EdgeInsets.all(12),
                  decoration: BoxDecoration(
                    color: isMe ? Colors.blue : Colors.grey[300],
                    borderRadius: BorderRadius.circular(12),
                  ),
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      if (!isMe)
                        Text(
                          message.senderName ?? '',
                          style: TextStyle(
                            fontWeight: FontWeight.bold,
                            fontSize: 12,
                            color: isMe ? Colors.white : Colors.black,
                          ),
                        ),
                      Text(
                        message.content,
                        style: TextStyle(
                          color: isMe ? Colors.white : Colors.black,
                        ),
                      ),
                      Text(
                        message.createdAt != null
                            ? _formatTimeString(message.createdAt!)
                            : '',
                        style: TextStyle(
                          fontSize: 10,
                          color: isMe ? Colors.white70 : Colors.black54,
                        ),
                      ),
                    ],
                  ),
                ),
              );
            },
          ),
        ),
        Container(
          padding: const EdgeInsets.all(8),
          decoration: BoxDecoration(
            color: Colors.grey[200],
          ),
          child: Row(
            children: [
              Expanded(
                child: TextField(
                  controller: _messageController,
                  decoration: const InputDecoration(
                    hintText: 'Écrire un message...',
                    border: OutlineInputBorder(
                      borderRadius: BorderRadius.all(Radius.circular(24)),
                    ),
                    contentPadding: EdgeInsets.symmetric(
                      horizontal: 16,
                      vertical: 8,
                    ),
                  ),
                  onSubmitted: (_) => _sendMessage(),
                ),
              ),
              const SizedBox(width: 8),
              IconButton(
                icon: const Icon(Icons.send),
                color: Colors.blue,
                onPressed: _sendMessage,
              ),
            ],
          ),
        ),
      ],
    );
  }

  String _formatDate(DateTime date) {
    final now = DateTime.now();
    final today = DateTime(now.year, now.month, now.day);
    final messageDate = DateTime(date.year, date.month, date.day);

    if (messageDate == today) {
      return _formatTimeStringFromDate(date);
    } else if (messageDate == today.subtract(const Duration(days: 1))) {
      return 'Hier';
    } else {
      return '${date.day}/${date.month}/${date.year}';
    }
  }

  String _formatTimeString(String dateStr) {
    try {
      final date = DateTime.parse(dateStr);
      return '${date.hour.toString().padLeft(2, '0')}:${date.minute.toString().padLeft(2, '0')}';
    } catch (e) {
      return '';
    }
  }

  String _formatTimeStringFromDate(DateTime date) {
    return '${date.hour.toString().padLeft(2, '0')}:${date.minute.toString().padLeft(2, '0')}';
  }
}
