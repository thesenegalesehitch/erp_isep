import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import {
  Container,
  Paper,
  TextField,
  Button,
  Typography,
  Box,
  Alert,
  Link
} from '@mui/material'
import { useForm } from 'react-hook-form'
import api from '../../services/api'

function LoginPage() {
  const navigate = useNavigate()
  const [error, setError] = useState('')
  const { register, handleSubmit, formState: { errors } } = useForm()

  const onSubmit = async (data) => {
    try {
      setError('')
      const response = await api.post('/auth/login', data)
      localStorage.setItem('accessToken', response.data.accessToken)
      localStorage.setItem('refreshToken', response.data.refreshToken)
      localStorage.setItem('user', JSON.stringify(response.data.user))
      navigate('/dashboard')
    } catch (err) {
      setError(err.response?.data?.message || 'Erreur de connexion')
    }
  }

  return (
    <Container maxWidth="sm" sx={{ py: 8 }}>
      <Paper elevation={3} sx={{ p: 4 }}>
        <Typography variant="h4" component="h1" gutterBottom align="center">
          Connexion
        </Typography>
        
        {error && (
          <Alert severity="error" sx={{ mb: 2 }}>
            {error}
          </Alert>
        )}

        <Box component="form" onSubmit={handleSubmit(onSubmit)}>
          <TextField
            {...register('email', {
              required: 'Email requis',
              pattern: {
                value: /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i,
                message: 'Email invalide'
              }
            })}
            label="Email"
            type="email"
            fullWidth
            margin="normal"
            error={!!errors.email}
            helperText={errors.email?.message}
          />

          <TextField
            {...register('password', { required: 'Mot de passe requis' })}
            label="Mot de passe"
            type="password"
            fullWidth
            margin="normal"
            error={!!errors.password}
            helperText={errors.password?.message}
          />

          <Button
            type="submit"
            variant="contained"
            fullWidth
            size="large"
            sx={{ mt: 3, mb: 2 }}
          >
            Se connecter
          </Button>

          <Box textAlign="center">
            <Link
              component="button"
              variant="body2"
              onClick={() => navigate('/register')}
            >
              Pas encore de compte ? S'inscrire
            </Link>
          </Box>
        </Box>
      </Paper>
    </Container>
  )
}

export default LoginPage

