import { Container, Typography, Button, Box, Grid, Card, CardContent } from '@mui/material'
import { useNavigate } from 'react-router-dom'
import SchoolIcon from '@mui/icons-material/School'
import MessageIcon from '@mui/icons-material/Message'
import BuildIcon from '@mui/icons-material/Build'
import CalendarTodayIcon from '@mui/icons-material/CalendarToday'
import DirectionsBusIcon from '@mui/icons-material/DirectionsBus'

function HomePage() {
  const navigate = useNavigate()
  const isAuthenticated = localStorage.getItem('accessToken')

  return (
    <Container maxWidth="lg" sx={{ py: 8 }}>
      <Box textAlign="center" mb={6}>
        <Typography variant="h2" component="h1" gutterBottom color="primary">
          ISEP Platform
        </Typography>
        <Typography variant="h5" color="text.secondary" paragraph>
          Plateforme de communication et services pour les étudiants
        </Typography>
        {!isAuthenticated && (
          <Box mt={4}>
            <Button
              variant="contained"
              size="large"
              sx={{ mr: 2 }}
              onClick={() => navigate('/register')}
            >
              S'inscrire
            </Button>
            <Button
              variant="outlined"
              size="large"
              onClick={() => navigate('/login')}
            >
              Se connecter
            </Button>
          </Box>
        )}
      </Box>

      <Grid container spacing={4}>
        <Grid item xs={12} md={6}>
          <Card>
            <CardContent>
              <MessageIcon color="primary" sx={{ fontSize: 40, mb: 2 }} />
              <Typography variant="h5" gutterBottom>
                Messagerie
              </Typography>
              <Typography variant="body2" color="text.secondary">
                Communiquez en temps réel avec vos camarades et enseignants
              </Typography>
            </CardContent>
          </Card>
        </Grid>

        <Grid item xs={12} md={6}>
          <Card>
            <CardContent>
              <BuildIcon color="primary" sx={{ fontSize: 40, mb: 2 }} />
              <Typography variant="h5" gutterBottom>
                Services Étudiants
              </Typography>
              <Typography variant="body2" color="text.secondary">
                Proposez ou recherchez des services professionnels
              </Typography>
            </CardContent>
          </Card>
        </Grid>

        <Grid item xs={12} md={6}>
          <Card>
            <CardContent>
              <CalendarTodayIcon color="primary" sx={{ fontSize: 40, mb: 2 }} />
              <Typography variant="h5" gutterBottom>
                Calendrier
              </Typography>
              <Typography variant="body2" color="text.secondary">
                Consultez et participez aux activités scolaires
              </Typography>
            </CardContent>
          </Card>
        </Grid>

        <Grid item xs={12} md={6}>
          <Card>
            <CardContent>
              <DirectionsBusIcon color="primary" sx={{ fontSize: 40, mb: 2 }} />
              <Typography variant="h5" gutterBottom>
                Bus de Ramassage
              </Typography>
              <Typography variant="body2" color="text.secondary">
                Suivez vos bus en temps réel et réservez vos trajets
              </Typography>
            </CardContent>
          </Card>
        </Grid>
      </Grid>
    </Container>
  )
}

export default HomePage

