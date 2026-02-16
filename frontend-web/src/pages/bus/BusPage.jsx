import { useEffect, useState } from 'react'
import {
  Container,
  Typography,
  Box,
  Card,
  CardContent,
  Button,
  Grid,
  Chip,
  TextField,
  MenuItem
} from '@mui/material'
import DirectionsBusIcon from '@mui/icons-material/DirectionsBus'
import LocationOnIcon from '@mui/icons-material/LocationOn'
import { GoogleMap, LoadScript, Marker } from '@react-google-maps/api'
import api from '../../services/api'

const mapContainerStyle = {
  width: '100%',
  height: '400px'
}

const center = {
  lat: 14.7167,
  lng: -17.4677
}

function BusPage() {
  const [buses, setBuses] = useState([])
  const [selectedBus, setSelectedBus] = useState(null)
  const [reservationDate, setReservationDate] = useState('')
  const [pickupLocation, setPickupLocation] = useState('')

  useEffect(() => {
    loadBuses()
    // Simuler le suivi en temps réel
    const interval = setInterval(loadBuses, 5000)
    return () => clearInterval(interval)
  }, [])

  const loadBuses = async () => {
    try {
      const response = await api.get('/buses')
      setBuses(response.data)
    } catch (error) {
      console.error('Erreur lors du chargement des bus:', error)
    }
  }

  const handleReserve = async (busId) => {
    if (!reservationDate || !pickupLocation) {
      alert('Veuillez remplir tous les champs')
      return
    }

    try {
      await api.post(`/buses/${busId}/reserve`, {
        reservationDate,
        pickupLocation,
        departureTime: selectedBus.departureTime
      })
      alert('Réservation réussie !')
      setSelectedBus(null)
    } catch (error) {
      alert('Erreur lors de la réservation')
      console.error(error)
    }
  }

  const getStatusColor = (status) => {
    const colors = {
      STATIONARY: 'default',
      IN_TRANSIT: 'primary',
      DELAYED: 'warning',
      ARRIVED: 'success',
      CANCELLED: 'error'
    }
    return colors[status] || 'default'
  }

  return (
    <Container maxWidth="lg" sx={{ py: 4 }}>
      <Typography variant="h4" component="h1" gutterBottom>
        Bus de Ramassage
      </Typography>

      <Grid container spacing={3}>
        <Grid item xs={12} md={8}>
          <Card sx={{ mb: 3 }}>
            <CardContent>
              <LoadScript googleMapsApiKey={import.meta.env.VITE_GOOGLE_MAPS_API_KEY || ''}>
                <GoogleMap
                  mapContainerStyle={mapContainerStyle}
                  center={center}
                  zoom={12}
                >
                  {buses.map((bus) => (
                    <Marker
                      key={bus.id}
                      position={{
                        lat: bus.currentLocationLat || center.lat,
                        lng: bus.currentLocationLng || center.lng
                      }}
                      title={`Bus ${bus.lineNumber}`}
                    />
                  ))}
                </GoogleMap>
              </LoadScript>
            </CardContent>
          </Card>

          <Typography variant="h6" gutterBottom>
            Bus disponibles
          </Typography>

          <Grid container spacing={2}>
            {buses.map((bus) => (
              <Grid item xs={12} sm={6} key={bus.id}>
                <Card
                  sx={{ cursor: 'pointer' }}
                  onClick={() => setSelectedBus(bus)}
                >
                  <CardContent>
                    <Box display="flex" justifyContent="space-between" alignItems="start" mb={2}>
                      <Box display="flex" alignItems="center" gap={1}>
                        <DirectionsBusIcon color="primary" />
                        <Typography variant="h6">Ligne {bus.lineNumber}</Typography>
                      </Box>
                      <Chip
                        label={bus.status}
                        color={getStatusColor(bus.status)}
                        size="small"
                      />
                    </Box>

                    <Typography variant="body2" color="text.secondary" gutterBottom>
                      Départ: {bus.departureTime}
                    </Typography>
                    <Typography variant="body2" color="text.secondary" gutterBottom>
                      Arrivée: {bus.arrivalTime}
                    </Typography>
                    <Typography variant="body2" color="text.secondary">
                      Places disponibles: {bus.getAvailableSeats()} / {bus.totalSeats}
                    </Typography>
                  </CardContent>
                </Card>
              </Grid>
            ))}
          </Grid>
        </Grid>

        <Grid item xs={12} md={4}>
          {selectedBus ? (
            <Card>
              <CardContent>
                <Typography variant="h6" gutterBottom>
                  Réserver - Ligne {selectedBus.lineNumber}
                </Typography>

                <TextField
                  fullWidth
                  type="date"
                  label="Date"
                  value={reservationDate}
                  onChange={(e) => setReservationDate(e.target.value)}
                  margin="normal"
                  InputLabelProps={{ shrink: true }}
                />

                <TextField
                  fullWidth
                  label="Lieu de prise en charge"
                  value={pickupLocation}
                  onChange={(e) => setPickupLocation(e.target.value)}
                  margin="normal"
                  InputProps={{
                    startAdornment: <LocationOnIcon sx={{ mr: 1, color: 'text.secondary' }} />
                  }}
                />

                <Box mt={2}>
                  <Button
                    fullWidth
                    variant="contained"
                    onClick={() => handleReserve(selectedBus.id)}
                    disabled={selectedBus.getAvailableSeats() <= 0}
                  >
                    {selectedBus.getAvailableSeats() > 0 ? 'Réserver' : 'Complet'}
                  </Button>
                </Box>
              </CardContent>
            </Card>
          ) : (
            <Card>
              <CardContent>
                <Typography color="text.secondary">
                  Sélectionnez un bus pour réserver
                </Typography>
              </CardContent>
            </Card>
          )}
        </Grid>
      </Grid>
    </Container>
  )
}

export default BusPage

