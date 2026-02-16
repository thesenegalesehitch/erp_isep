import { useEffect, useState, useRef } from 'react'
import {
  Container,
  Typography,
  Box,
  TextField,
  Button,
  List,
  ListItem,
  ListItemText,
  Paper,
  Grid
} from '@mui/material'
import SendIcon from '@mui/icons-material/Send'
import { io } from 'socket.io-client'
import api from '../../services/api'

function MessagingPage() {
  const [conversations, setConversations] = useState([])
  const [selectedConversation, setSelectedConversation] = useState(null)
  const [messages, setMessages] = useState([])
  const [newMessage, setNewMessage] = useState('')
  const [socket, setSocket] = useState(null)
  const messagesEndRef = useRef(null)

  useEffect(() => {
    // Initialize WebSocket connection
    const token = localStorage.getItem('accessToken')
    const newSocket = io('http://localhost:8080', {
      auth: { token },
      transports: ['websocket']
    })

    newSocket.on('connect', () => {
      console.log('WebSocket connected')
    })

    newSocket.on('message', (message) => {
      setMessages(prev => [...prev, message])
    })

    setSocket(newSocket)

    loadConversations()

    return () => {
      newSocket.close()
    }
  }, [])

  useEffect(() => {
    if (selectedConversation) {
      loadMessages(selectedConversation.id)
    }
  }, [selectedConversation])

  useEffect(() => {
    scrollToBottom()
  }, [messages])

  const loadConversations = async () => {
    try {
      // TODO: Implémenter endpoint conversations
      // const response = await api.get('/conversations')
      // setConversations(response.data)
    } catch (error) {
      console.error('Erreur lors du chargement des conversations:', error)
    }
  }

  const loadMessages = async (conversationId) => {
    try {
      const response = await api.get(`/messages/conversation/${conversationId}`)
      setMessages(response.data)
    } catch (error) {
      console.error('Erreur lors du chargement des messages:', error)
    }
  }

  const sendMessage = () => {
    if (!newMessage.trim() || !socket || !selectedConversation) return

    const message = {
      conversationId: selectedConversation.id,
      content: newMessage,
      type: 'TEXT'
    }

    socket.emit('sendMessage', message)
    setNewMessage('')
  }

  const scrollToBottom = () => {
    messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' })
  }

  const user = JSON.parse(localStorage.getItem('user') || '{}')

  return (
    <Container maxWidth="lg" sx={{ py: 4 }}>
      <Typography variant="h4" component="h1" gutterBottom>
        Messagerie
      </Typography>

      <Grid container spacing={2} sx={{ height: '70vh' }}>
        <Grid item xs={12} md={4}>
          <Paper sx={{ height: '100%', overflow: 'auto' }}>
            <List>
              {conversations.map((conv) => (
                <ListItem
                  key={conv.id}
                  button
                  selected={selectedConversation?.id === conv.id}
                  onClick={() => setSelectedConversation(conv)}
                >
                  <ListItemText
                    primary={conv.name}
                    secondary={conv.lastMessageAt ? new Date(conv.lastMessageAt).toLocaleDateString() : ''}
                  />
                </ListItem>
              ))}
            </List>
          </Paper>
        </Grid>

        <Grid item xs={12} md={8}>
          {selectedConversation ? (
            <Paper sx={{ height: '100%', display: 'flex', flexDirection: 'column' }}>
              <Box sx={{ p: 2, borderBottom: 1, borderColor: 'divider' }}>
                <Typography variant="h6">{selectedConversation.name}</Typography>
              </Box>

              <Box sx={{ flexGrow: 1, overflow: 'auto', p: 2 }}>
                {messages.map((message) => (
                  <Box
                    key={message.id}
                    sx={{
                      display: 'flex',
                      justifyContent: message.sender?.id === user.id ? 'flex-end' : 'flex-start',
                      mb: 2
                    }}
                  >
                    <Paper
                      sx={{
                        p: 2,
                        maxWidth: '70%',
                        bgcolor: message.sender?.id === user.id ? 'primary.light' : 'grey.100'
                      }}
                    >
                      <Typography variant="body2" color="text.secondary" gutterBottom>
                        {message.sender?.firstName} {message.sender?.lastName}
                      </Typography>
                      <Typography variant="body1">{message.content}</Typography>
                      <Typography variant="caption" color="text.secondary">
                        {new Date(message.createdAt).toLocaleTimeString()}
                      </Typography>
                    </Paper>
                  </Box>
                ))}
                <div ref={messagesEndRef} />
              </Box>

              <Box sx={{ p: 2, borderTop: 1, borderColor: 'divider', display: 'flex', gap: 1 }}>
                <TextField
                  fullWidth
                  value={newMessage}
                  onChange={(e) => setNewMessage(e.target.value)}
                  placeholder="Écrire un message..."
                  onKeyPress={(e) => {
                    if (e.key === 'Enter' && !e.shiftKey) {
                      e.preventDefault()
                      sendMessage()
                    }
                  }}
                />
                <Button
                  variant="contained"
                  onClick={sendMessage}
                  endIcon={<SendIcon />}
                >
                  Envoyer
                </Button>
              </Box>
            </Paper>
          ) : (
            <Paper sx={{ height: '100%', display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
              <Typography variant="h6" color="text.secondary">
                Sélectionnez une conversation
              </Typography>
            </Paper>
          )}
        </Grid>
      </Grid>
    </Container>
  )
}

export default MessagingPage

