import { useEffect, useState } from 'react'
import {
  Container,
  Typography,
  Grid,
  Card,
  CardContent,
  CardActions,
  Button,
  TextField,
  Box,
  MenuItem,
  Chip,
  Rating
} from '@mui/material'
import AddIcon from '@mui/icons-material/Add'
import SearchIcon from '@mui/icons-material/Search'
import api from '../../services/api'

function ServicesPage() {
  const [services, setServices] = useState([])
  const [filteredServices, setFilteredServices] = useState([])
  const [category, setCategory] = useState('')
  const [searchTerm, setSearchTerm] = useState('')

  const categories = [
    'MECHANICS',
    'ELECTRICAL',
    'PLUMBING',
    'IT',
    'TUTORING',
    'DESIGN',
    'OTHER'
  ]

  useEffect(() => {
    loadServices()
  }, [])

  useEffect(() => {
    filterServices()
  }, [services, category, searchTerm])

  const loadServices = async () => {
    try {
      const response = await api.get('/services')
      setServices(response.data)
    } catch (error) {
      console.error('Erreur lors du chargement des services:', error)
    }
  }

  const filterServices = () => {
    let filtered = services

    if (category) {
      filtered = filtered.filter(s => s.category === category)
    }

    if (searchTerm) {
      filtered = filtered.filter(s =>
        s.title.toLowerCase().includes(searchTerm.toLowerCase()) ||
        s.description?.toLowerCase().includes(searchTerm.toLowerCase())
      )
    }

    setFilteredServices(filtered)
  }

  const handleReserve = async (serviceId) => {
    try {
      // TODO: Implémenter la réservation
      alert('Fonctionnalité de réservation à implémenter')
    } catch (error) {
      console.error('Erreur lors de la réservation:', error)
    }
  }

  return (
    <Container maxWidth="lg" sx={{ py: 4 }}>
      <Box display="flex" justifyContent="space-between" alignItems="center" mb={4}>
        <Typography variant="h4" component="h1">
          Services Étudiants
        </Typography>
        <Button
          variant="contained"
          startIcon={<AddIcon />}
          onClick={() => {/* TODO: Navigate to create service */}}
        >
          Proposer un service
        </Button>
      </Box>

      <Box display="flex" gap={2} mb={4}>
        <TextField
          select
          label="Catégorie"
          value={category}
          onChange={(e) => setCategory(e.target.value)}
          sx={{ minWidth: 200 }}
        >
          <MenuItem value="">Toutes</MenuItem>
          {categories.map((cat) => (
            <MenuItem key={cat} value={cat}>
              {cat}
            </MenuItem>
          ))}
        </TextField>

        <TextField
          label="Rechercher"
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
          InputProps={{
            startAdornment: <SearchIcon sx={{ mr: 1, color: 'text.secondary' }} />
          }}
          sx={{ flexGrow: 1 }}
        />
      </Box>

      <Grid container spacing={3}>
        {filteredServices.map((service) => (
          <Grid item xs={12} sm={6} md={4} key={service.id}>
            <Card>
              <CardContent>
                <Box display="flex" justifyContent="space-between" alignItems="start" mb={2}>
                  <Typography variant="h6">{service.title}</Typography>
                  <Chip label={service.category} size="small" />
                </Box>
                
                <Typography variant="body2" color="text.secondary" paragraph>
                  {service.description?.substring(0, 100)}...
                </Typography>

                <Box display="flex" alignItems="center" gap={1} mb={1}>
                  <Rating value={service.averageRating || 0} readOnly size="small" />
                  <Typography variant="body2">
                    ({service.totalRatings || 0} avis)
                  </Typography>
                </Box>

                {service.price && (
                  <Typography variant="h6" color="primary">
                    {service.price} FCFA
                  </Typography>
                )}

                {service.location && (
                  <Typography variant="body2" color="text.secondary">
                    📍 {service.location}
                  </Typography>
                )}
              </CardContent>

              <CardActions>
                <Button
                  size="small"
                  variant="contained"
                  onClick={() => handleReserve(service.id)}
                  disabled={!service.isAvailable}
                >
                  {service.isAvailable ? 'Réserver' : 'Indisponible'}
                </Button>
              </CardActions>
            </Card>
          </Grid>
        ))}
      </Grid>

      {filteredServices.length === 0 && (
        <Box textAlign="center" py={8}>
          <Typography variant="h6" color="text.secondary">
            Aucun service trouvé
          </Typography>
        </Box>
      )}
    </Container>
  )
}

export default ServicesPage

