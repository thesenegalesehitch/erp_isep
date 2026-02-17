import { Client } from '@stomp/stompjs';

class WebSocketService {
  constructor() {
    this.client = null;
    this.subscriptions = new Map();
    this.listeners = new Map();
  }

  connect(token) {
    if (this.client?.active) {
      return;
    }

    this.client = new Client({
      brokerURL: 'ws://localhost:8080/ws',
      connectHeaders: {
        Authorization: `Bearer ${token}`,
      },
      debug: (str) => {
        console.log(str);
      },
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
    });

    this.client.onConnect = (frame) => {
      console.log('Connected: ' + frame);
      this.subscribeTotopics();
      this.emit('connect', frame);
    };

    this.client.onStompError = (frame) => {
      console.error('Broker reported error: ' + frame.headers['message']);
      console.error('Additional details: ' + frame.body);
    };

    this.client.activate();
  }

  subscribeTotopics() {
    // Subscribe to public topics
    this.subscribe('/topic/public', (message) => {
      this.emit('message', JSON.parse(message.body));
    });

    this.subscribe('/topic/bus-updates', (bus) => {
      this.emit('busUpdate', JSON.parse(bus.body));
    });

    // Subscribe to user-specific notifications if needed
    // This requires the backend to send to /user/queue/notifications
    this.subscribe('/user/queue/notifications', (notification) => {
      this.emit('notification', JSON.parse(notification.body));
    });
  }

  subscribe(topic, callback) {
    if (this.client && this.client.active) {
      const subscription = this.client.subscribe(topic, callback);
      this.subscriptions.set(topic, subscription);
    }
  }

  disconnect() {
    if (this.client) {
      this.client.deactivate();
      this.client = null;
      this.subscriptions.clear();
      console.log('Disconnected');
    }
  }

  on(event, callback) {
    if (!this.listeners.has(event)) {
      this.listeners.set(event, []);
    }
    this.listeners.get(event).push(callback);
  }

  off(event, callback) {
    if (this.listeners.has(event)) {
      const callbacks = this.listeners.get(event);
      const index = callbacks.indexOf(callback);
      if (index > -1) {
        callbacks.splice(index, 1);
      }
    }
  }

  emit(event, data) {
    if (this.listeners.has(event)) {
      this.listeners.get(event).forEach(callback => callback(data));
    }
  }

  sendMessage(conversationId, content, type = 'TEXT') {
    if (this.client && this.client.active) {
      this.client.publish({
        destination: '/app/chat.sendMessage',
        body: JSON.stringify({ conversationId, content, type }),
      });
    }
  }

  joinConversation(conversationId) {
    if (this.client && this.client.active) {
        this.client.publish({
            destination: '/app/chat.addUser',
            body: JSON.stringify({ conversationId }),
        });
    }
  }

  leaveConversation(conversationId) {
    // Implement if backend supports leaving conversation explicitly via WS
  }
}

export default new WebSocketService();

