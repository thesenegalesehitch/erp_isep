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
  Link,
  MenuItem
} from '@mui/material'
import { useForm } from 'react-hook-form'
import api from '../../services/api'

function RegisterPage() {
  const navigate = useNavigate()
  const [error, setError] = useState('')
  const { register, handleSubmit, formState: { errors }, watch } = useForm()
  const password = watch('password')

  const onSubmit = async (data) => {
    try {
      setError('')
      const response = await api.post('/auth/register', data)
      localStorage.setItem('accessToken', response.data.accessToken)
      localStorage.setItem('refreshToken', response.data.refreshToken)
      localStorage.setItem('user', JSON.stringify(response.data.user))
      navigate('/dashboard')
    } catch (err) {
      setError(err.response?.data?.message || 'Erreur lors de l\'inscription')
    }
  }

  return (
    <Container maxWidth="sm" sx={{ py: 8 }}>
      <Paper elevation={3} sx={{ p: 4 }}>
        <Typography variant="h4" component="h1" gutterBottom align="center">
          Inscription
        </Typography>
        
        {error && (
          <Alert severity="error" sx={{ mb: 2 }}>
            {error}
          </Alert>
        )}

        <Box component="form" onSubmit={handleSubmit(onSubmit)}>
          <TextField
            {...register('firstName', { required: 'Prénom requis' })}
            label="Prénom"
            fullWidth
            margin="normal"
            error={!!errors.firstName}
            helperText={errors.firstName?.message}
          />

          <TextField
            {...register('lastName', { required: 'Nom requis' })}
            label="Nom"
            fullWidth
            margin="normal"
            error={!!errors.lastName}
            helperText={errors.lastName?.message}
          />

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
            {...register('studentNumber')}
            label="Numéro étudiant (optionnel)"
            fullWidth
            margin="normal"
          />

          <TextField
            {...register('password', {
              required: 'Mot de passe requis',
              minLength: { value: 8, message: 'Minimum 8 caractères' }
            })}
            label="Mot de passe"
            type="password"
            fullWidth
            margin="normal"
            error={!!errors.password}
            helperText={errors.password?.message}
          />

          <TextField
            {...register('passwordConfirmation', {
              required: 'Confirmation requise',
              validate: value => value === password || 'Les mots de passe ne correspondent pas'
            })}
            label="Confirmer le mot de passe"
            type="password"
            fullWidth
            margin="normal"
            error={!!errors.passwordConfirmation}
            helperText={errors.passwordConfirmation?.message}
          />

          <TextField
            {...register('role')}
            select
            label="Rôle"
            fullWidth
            margin="normal"
            defaultValue="STUDENT"
          >
            <MenuItem value="STUDENT">Étudiant</MenuItem>
            <MenuItem value="TEACHER">Enseignant</MenuItem>
          </TextField>

          <Button
            type="submit"
            variant="contained"
            fullWidth
            size="large"
            sx={{ mt: 3, mb: 2 }}
          >
            S'inscrire
          </Button>

          <Box textAlign="center">
            <Link
              component="button"
              variant="body2"
              onClick={() => navigate('/login')}
            >
              Déjà un compte ? Se connecter
            </Link>
          </Box>
        </Box>
      </Paper>
    </Container>
  )
}

export default RegisterPage

