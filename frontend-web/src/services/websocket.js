import { io } from 'socket.io-client'

class WebSocketService {
  constructor() {
    this.socket = null
    this.listeners = new Map()
  }

  connect(token) {
    if (this.socket?.connected) {
      return
    }

    this.socket = io('http://localhost:8080', {
      auth: { token },
      transports: ['websocket']
    })

    this.socket.on('connect', () => {
      console.log('WebSocket connected')
      this.emit('userConnected')
    })

    this.socket.on('disconnect', () => {
      console.log('WebSocket disconnected')
    })

    this.socket.on('message', (message) => {
      this.emit('message', message)
    })

    this.socket.on('busUpdate', (bus) => {
      this.emit('busUpdate', bus)
    })

    this.socket.on('notification', (notification) => {
      this.emit('notification', notification)
    })
  }

  disconnect() {
    if (this.socket) {
      this.socket.disconnect()
      this.socket = null
    }
  }

  on(event, callback) {
    if (!this.listeners.has(event)) {
      this.listeners.set(event, [])
    }
    this.listeners.get(event).push(callback)
  }

  off(event, callback) {
    if (this.listeners.has(event)) {
      const callbacks = this.listeners.get(event)
      const index = callbacks.indexOf(callback)
      if (index > -1) {
        callbacks.splice(index, 1)
      }
    }
  }

  emit(event, data) {
    if (this.listeners.has(event)) {
      this.listeners.get(event).forEach(callback => callback(data))
    }
  }

  sendMessage(conversationId, content, type = 'TEXT') {
    if (this.socket?.connected) {
      this.socket.emit('sendMessage', {
        conversationId,
        content,
        type
      })
    }
  }

  joinConversation(conversationId) {
    if (this.socket?.connected) {
      this.socket.emit('joinConversation', conversationId)
    }
  }

  leaveConversation(conversationId) {
    if (this.socket?.connected) {
      this.socket.emit('leaveConversation', conversationId)
    }
  }
}

export default new WebSocketService()

