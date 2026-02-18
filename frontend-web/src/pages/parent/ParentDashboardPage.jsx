import React, { useState, useEffect } from 'react'
import {
  Box,
  Card,
  CardContent,
  Typography,
  Grid,
  Paper,
  List,
  ListItem,
  ListItemText,
  Chip,
  Button,
  Alert,
  CircularProgress,
  Divider
} from '@mui/material'
import {
  School,
  Grade,
  AttachMoney,
  Event,
  TrendingUp,
  Notifications
} from '@mui/icons-material'
import { useNavigate } from 'react-router-dom'
import { parentAuthStore } from '../../stores/parentAuthStore'
import { parentDashboardStore } from '../../stores/parentDashboardStore'

const ParentDashboardPage = () => {
  const navigate = useNavigate()
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')
  const [dashboardData, setDashboardData] = useState(null)

  useEffect(() => {
    if (!parentAuthStore.isAuthenticated) {
      navigate('/login')
      return
    }

    loadDashboardData()
  }, [])

  const loadDashboardData = async () => {
    try {
      setLoading(true)
      const data = await parentDashboardStore.getDashboardData()
      setDashboardData(data)
    } catch (err) {
      setError(err.message || 'Erreur lors du chargement du tableau de bord')
    } finally {
      setLoading(false)
    }
  }

  if (loading) {
    return (
      <Box sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', minHeight: '60vh' }}>
        <CircularProgress />
      </Box>
    )
  }

  if (error) {
    return (
      <Alert severity="error" sx={{ m: 2 }}>
        {error}
      </Alert>
    )
  }

  return (
    <Box sx={{ p: 3 }}>
      <Typography variant="h4" gutterBottom>
        Tableau de Bord Parent
      </Typography>
      <Typography variant="body1" color="text.secondary" gutterBottom>
        Bienvenue, {parentAuthStore.user?.firstName} {parentAuthStore.user?.lastName}
      </Typography>

      <Grid container spacing={3}>
        <Grid item xs={12} md={3}>
          <Card>
            <CardContent>
              <Box sx={{ display: 'flex', alignItems: 'center', mb: 2 }}>
                <School color="primary" sx={{ mr: 1 }} />
                <Typography variant="h6">Élèves</Typography>
              </Box>
              <Typography variant="h4" color="primary">
                {dashboardData?.linkedStudents || 0}
              </Typography>
              <Typography variant="body2" color="text.secondary">
                Élèves connectés
              </Typography>
            </CardContent>
          </Card>
        </Grid>

        <Grid item xs={12} md={3}>
          <Card>
            <CardContent>
              <Box sx={{ display: 'flex', alignItems: 'center', mb: 2 }}>
                <Grade color="success" sx={{ mr: 1 }} />
                <Typography variant="h6">Notes</Typography>
              </Box>
              <Typography variant="h4" color="success.main">
                {dashboardData?.recentGrades || 0}
              </Typography>
              <Typography variant="body2" color="text.secondary">
                Nouvelles notes
              </Typography>
            </CardContent>
          </Card>
        </Grid>

        <Grid item xs={12} md={3}>
          <Card>
            <CardContent>
              <Box sx={{ display: 'flex', alignItems: 'center', mb: 2 }}>
                <AttachMoney color="warning" sx={{ mr: 1 }} />
                <Typography variant="h6">Paiements</Typography>
              </Box>
              <Typography variant="h4" color="warning.main">
                {dashboardData?.pendingPayments || 0}
              </Typography>
              <Typography variant="body2" color="text.secondary">
                En attente
              </Typography>
            </CardContent>
          </Card>
        </Grid>

        <Grid item xs={12} md={3}>
          <Card>
            <CardContent>
              <Box sx={{ display: 'flex', alignItems: 'center', mb: 2 }}>
                <TrendingUp color="info" sx={{ mr: 1 }} />
                <Typography variant="h6">Présence</Typography>
              </Box>
              <Typography variant="h4" color="info.main">
                {dashboardData?.attendanceRate || 0}%
              </Typography>
              <Typography variant="body2" color="text.secondary">
                Taux de présence
              </Typography>
            </CardContent>
          </Card>
        </Grid>
      </Grid>

      <Grid container spacing={3} sx={{ mt: 2 }}>
        <Grid item xs={12} md={6}>
          <Paper sx={{ p: 2 }}>
            <Typography variant="h6" gutterBottom>
              <Notifications sx={{ mr: 1, verticalAlign: 'middle' }} />
              Notifications Récentes
            </Typography>
            <List>
              {dashboardData?.recentNotifications?.map((notification, index) => (
                <ListItem key={index} divider={index < dashboardData.recentNotifications.length - 1}>
                  <ListItemText
                    primary={notification.title}
                    secondary={notification.message}
                  />
                  <Chip
                    label={notification.type}
                    size="small"
                    color={notification.type === 'urgent' ? 'error' : 'default'}
                  />
                </ListItem>
              )) || (
                <ListItem>
                  <ListItemText primary="Aucune notification récente" />
                </ListItem>
              )}
            </List>
          </Paper>
        </Grid>

        <Grid item xs={12} md={6}>
          <Paper sx={{ p: 2 }}>
            <Typography variant="h6" gutterBottom>
              Actions Rapides
            </Typography>
            <Grid container spacing={2}>
              <Grid item xs={12} sm={6}>
                <Button
                  fullWidth
                  variant="outlined"
                  startIcon={<Grade />}
                  onClick={() => navigate('/parent/grades')}
                >
                  Voir les Notes
                </Button>
              </Grid>
              <Grid item xs={12} sm={6}>
                <Button
                  fullWidth
                  variant="outlined"
                  startIcon={<AttachMoney />}
                  onClick={() => navigate('/parent/payments')}
                >
                  Paiements
                </Button>
              </Grid>
              <Grid item xs={12} sm={6}>
                <Button
                  fullWidth
                  variant="outlined"
                  startIcon={<Event />}
                  onClick={() => navigate('/parent/attendance')}
                >
                  Présence
                </Button>
              </Grid>
              <Grid item xs={12} sm={6}>
                <Button
                  fullWidth
                  variant="outlined"
                  startIcon={<School />}
                  onClick={() => navigate('/parent/students')}
                >
                  Gérer les Élèves
                </Button>
              </Grid>
            </Grid>
          </Paper>
        </Grid>
      </Grid>

      <Grid container spacing={3} sx={{ mt: 2 }}>
        <Grid item xs={12}>
          <Paper sx={{ p: 2 }}>
            <Typography variant="h6" gutterBottom>
              Résumé par Élève
            </Typography>
            <Grid container spacing={2}>
              {dashboardData?.studentSummaries?.map((student, index) => (
                <Grid item xs={12} md={4} key={index}>
                  <Card variant="outlined">
                    <CardContent>
                      <Typography variant="h6" gutterBottom>
                        {student.name}
                      </Typography>
                      <Typography variant="body2" color="text.secondary" gutterBottom>
                        {student.grade} - {student.school}
                      </Typography>
                      <Divider sx={{ my: 1 }} />
                      <Typography variant="body2" gutterBottom>
                        <strong>Moyenne:</strong> {student.average}/20
                      </Typography>
                      <Typography variant="body2" gutterBottom>
                        <strong>Présence:</strong> {student.attendance}%
                      </Typography>
                      <Typography variant="body2">
                        <strong>Dernière note:</strong> {student.lastGrade}
                      </Typography>
                    </CardContent>
                  </Card>
                </Grid>
              )) || (
                <Grid item xs={12}>
                  <Typography variant="body2" color="text.secondary" align="center">
                    Aucun élève connecté. <Button href="/parent/students">Connecter un élève</Button>
                  </Typography>
                </Grid>
              )}
            </Grid>
          </Paper>
        </Grid>
      </Grid>
    </Box>
  )
}

export default ParentDashboardPage
