import { AppBar, Toolbar, Typography, Button, Box } from '@mui/material'
import { useNavigate, useLocation } from 'react-router-dom'
import SchoolIcon from '@mui/icons-material/School'

function Navbar() {
  const navigate = useNavigate()
  const location = useLocation()
  const isAuthenticated = localStorage.getItem('accessToken')
  const user = JSON.parse(localStorage.getItem('user') || '{}')

  const handleLogout = () => {
    localStorage.removeItem('accessToken')
    localStorage.removeItem('refreshToken')
    localStorage.removeItem('user')
    navigate('/')
  }

  return (
    <AppBar position="static">
      <Toolbar>
        <SchoolIcon sx={{ mr: 2 }} />
        <Typography
          variant="h6"
          component="div"
          sx={{ flexGrow: 1, cursor: 'pointer' }}
          onClick={() => navigate('/')}
        >
          ISEP Platform
        </Typography>

        {isAuthenticated ? (
          <Box>
            <Button
              color="inherit"
              onClick={() => navigate('/dashboard')}
              sx={{ mr: 1 }}
            >
              Dashboard
            </Button>
                        <Button
              color="inherit"
              onClick={() => navigate('/messaging')}
              sx={{ mr: 1 }}
            >
              Messagerie
            </Button>
            <Button
              color="inherit"
              onClick={() => navigate('/calendar')}
              sx={{ mr: 1 }}
            >
              Calendrier
            </Button>
            <Button color="inherit" onClick={handleLogout}>
              Déconnexion
            </Button>
          </Box>
        ) : (
          <Box>
            <Button color="inherit" onClick={() => navigate('/login')}>
              Connexion
            </Button>
            <Button color="inherit" onClick={() => navigate('/register')}>
              Inscription
            </Button>
          </Box>
        )}
      </Toolbar>
    </AppBar>
  )
}

export default Navbar

