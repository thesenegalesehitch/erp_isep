import { useEffect, useState } from 'react'
import {
  Container,
  Typography,
  Box,
  Button,
  Card,
  CardContent,
  Grid,
  Chip
} from '@mui/material'
import Calendar from 'react-calendar'
import 'react-calendar/dist/Calendar.css'
import AddIcon from '@mui/icons-material/Add'
import api from '../../services/api'

function CalendarPage() {
  const [date, setDate] = useState(new Date())
  const [activities, setActivities] = useState([])
  const [selectedActivities, setSelectedActivities] = useState([])

  useEffect(() => {
    loadActivities()
  }, [])

  useEffect(() => {
    filterActivitiesByDate()
  }, [date, activities])

  const loadActivities = async () => {
    try {
      const response = await api.get('/activities')
      setActivities(response.data)
    } catch (error) {
      console.error('Erreur lors du chargement des activités:', error)
    }
  }

  const filterActivitiesByDate = () => {
    const filtered = activities.filter(activity => {
      const activityDate = new Date(activity.startDate)
      return activityDate.toDateString() === date.toDateString()
    })
    setSelectedActivities(filtered)
  }

  const handleRegister = async (activityId) => {
    try {
      await api.post(`/activities/${activityId}/register`)
      alert('Inscription réussie !')
      loadActivities()
    } catch (error) {
      alert('Erreur lors de l\'inscription')
      console.error(error)
    }
  }

  const getActivityTypeColor = (type) => {
    const colors = {
      COURSE: 'primary',
      EXAM: 'error',
      WORKSHOP: 'warning',
      CONFERENCE: 'info',
      SPORT: 'success',
      TRIP: 'secondary'
    }
    return colors[type] || 'default'
  }

  return (
    <Container maxWidth="lg" sx={{ py: 4 }}>
      <Box display="flex" justifyContent="space-between" alignItems="center" mb={4}>
        <Typography variant="h4" component="h1">
          Calendrier des Activités
        </Typography>
        <Button
          variant="contained"
          startIcon={<AddIcon />}
          onClick={() => {/* TODO: Navigate to create activity */}}
        >
          Créer une activité
        </Button>
      </Box>

      <Grid container spacing={3}>
        <Grid item xs={12} md={4}>
          <Card>
            <CardContent>
              <Calendar
                onChange={setDate}
                value={date}
                tileContent={({ date }) => {
                  const count = activities.filter(a => {
                    const activityDate = new Date(a.startDate)
                    return activityDate.toDateString() === date.toDateString()
                  }).length
                  return count > 0 ? (
                    <Box sx={{ fontSize: '0.7rem', color: 'primary.main' }}>
                      {count} activité{count > 1 ? 's' : ''}
                    </Box>
                  ) : null
                }}
              />
            </CardContent>
          </Card>
        </Grid>

        <Grid item xs={12} md={8}>
          <Typography variant="h6" gutterBottom>
            Activités du {date.toLocaleDateString('fr-FR')}
          </Typography>

          {selectedActivities.length === 0 ? (
            <Card>
              <CardContent>
                <Typography color="text.secondary">
                  Aucune activité prévue pour cette date
                </Typography>
              </CardContent>
            </Card>
          ) : (
            <Box sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}>
              {selectedActivities.map((activity) => (
                <Card key={activity.id}>
                  <CardContent>
                    <Box display="flex" justifyContent="space-between" alignItems="start" mb={2}>
                      <Box>
                        <Typography variant="h6">{activity.title}</Typography>
                        <Typography variant="body2" color="text.secondary">
                          {new Date(activity.startDate).toLocaleTimeString('fr-FR')} - 
                          {activity.endDate && new Date(activity.endDate).toLocaleTimeString('fr-FR')}
                        </Typography>
                      </Box>
                      <Chip
                        label={activity.type}
                        color={getActivityTypeColor(activity.type)}
                        size="small"
                      />
                    </Box>

                    <Typography variant="body2" paragraph>
                      {activity.description}
                    </Typography>

                    {activity.location && (
                      <Typography variant="body2" color="text.secondary" gutterBottom>
                        📍 {activity.location}
                      </Typography>
                    )}

                    <Box display="flex" justifyContent="space-between" alignItems="center" mt={2}>
                      <Typography variant="body2">
                        {activity.currentParticipants} / {activity.maxParticipants || '∞'} participants
                      </Typography>
                      <Button
                        variant="contained"
                        size="small"
                        onClick={() => handleRegister(activity.id)}
                        disabled={!activity.hasAvailableSpots()}
                      >
                        {activity.hasAvailableSpots() ? 'S\'inscrire' : 'Complet'}
                      </Button>
                    </Box>
                  </CardContent>
                </Card>
              ))}
            </Box>
          )}
        </Grid>
      </Grid>
    </Container>
  )
}

export default CalendarPage

