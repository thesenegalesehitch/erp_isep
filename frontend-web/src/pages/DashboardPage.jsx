import { useEffect, useState } from 'react'
import {
  Container,
  Typography,
  Grid,
  Card,
  CardContent,
  Box,
  Button
} from '@mui/material'
import { useNavigate } from 'react-router-dom'
import MessageIcon from '@mui/icons-material/Message'
import BuildIcon from '@mui/icons-material/Build'
import CalendarTodayIcon from '@mui/icons-material/CalendarToday'
import DirectionsBusIcon from '@mui/icons-material/DirectionsBus'
import PersonIcon from '@mui/icons-material/Person'
import api from '../services/api'

function DashboardPage() {
  const navigate = useNavigate()
  const [user, setUser] = useState(null)
  const [stats, setStats] = useState({
    services: 0,
    messages: 0,
    activities: 0,
    busReservations: 0
  })

  useEffect(() => {
    const userData = localStorage.getItem('user')
    if (userData) {
      setUser(JSON.parse(userData))
    }
    // Load stats
    loadStats()
  }, [])

  const loadStats = async () => {
    try {
      // TODO: Implémenter les endpoints de statistiques
      // const response = await api.get('/dashboard/stats')
      // setStats(response.data)
    } catch (error) {
      console.error('Erreur lors du chargement des statistiques:', error)
    }
  }

  const handleLogout = () => {
    localStorage.removeItem('accessToken')
    localStorage.removeItem('refreshToken')
    localStorage.removeItem('user')
    navigate('/')
  }

  return (
    <Container maxWidth="lg" sx={{ py: 4 }}>
      <Box display="flex" justifyContent="space-between" alignItems="center" mb={4}>
        <Typography variant="h4" component="h1">
          Tableau de bord
        </Typography>
        <Button variant="outlined" onClick={handleLogout}>
          Déconnexion
        </Button>
      </Box>

      {user && (
        <Card sx={{ mb: 4 }}>
          <CardContent>
            <Box display="flex" alignItems="center" gap={2}>
              <PersonIcon />
              <Box>
                <Typography variant="h6">
                  {user.firstName} {user.lastName}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  {user.email}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  Rôle: {user.role}
                </Typography>
              </Box>
            </Box>
          </CardContent>
        </Card>
      )}

      <Grid container spacing={3}>
        <Grid item xs={12} sm={6} md={3}>
          <Card sx={{ cursor: 'pointer' }} onClick={() => navigate('/services')}>
            <CardContent>
              <BuildIcon color="primary" sx={{ fontSize: 40, mb: 1 }} />
              <Typography variant="h6">Services</Typography>
              <Typography variant="h4">{stats.services}</Typography>
            </CardContent>
          </Card>
        </Grid>

        <Grid item xs={12} sm={6} md={3}>
          <Card sx={{ cursor: 'pointer' }} onClick={() => navigate('/messaging')}>
            <CardContent>
              <MessageIcon color="primary" sx={{ fontSize: 40, mb: 1 }} />
              <Typography variant="h6">Messages</Typography>
              <Typography variant="h4">{stats.messages}</Typography>
            </CardContent>
          </Card>
        </Grid>

        <Grid item xs={12} sm={6} md={3}>
          <Card sx={{ cursor: 'pointer' }} onClick={() => navigate('/calendar')}>
            <CardContent>
              <CalendarTodayIcon color="primary" sx={{ fontSize: 40, mb: 1 }} />
              <Typography variant="h6">Activités</Typography>
              <Typography variant="h4">{stats.activities}</Typography>
            </CardContent>
          </Card>
        </Grid>

        <Grid item xs={12} sm={6} md={3}>
          <Card sx={{ cursor: 'pointer' }} onClick={() => navigate('/bus')}>
            <CardContent>
              <DirectionsBusIcon color="primary" sx={{ fontSize: 40, mb: 1 }} />
              <Typography variant="h6">Bus</Typography>
              <Typography variant="h4">{stats.busReservations}</Typography>
            </CardContent>
          </Card>
        </Grid>
      </Grid>
    </Container>
  )
}

export default DashboardPage

