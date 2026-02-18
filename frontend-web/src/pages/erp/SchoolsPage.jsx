import React, { useState, useEffect } from 'react'
import {
  Box,
  Card,
  CardContent,
  Typography,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  Button,
  TextField,
  IconButton,
  Chip,
  Pagination,
  Grid,
  Fab,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  Switch,
  FormControlLabel
} from '@mui/material'
import {
  Add as AddIcon,
  Search as SearchIcon,
  Edit as EditIcon,
  Delete as DeleteIcon,
  Visibility as ViewIcon,
  Business as BusinessIcon
} from '@mui/icons-material'

const SchoolsPage = () => {
  const [schools, setSchools] = useState([])
  const [loading, setLoading] = useState(true)
  const [searchTerm, setSearchTerm] = useState('')
  const [page, setPage] = useState(1)
  const [totalPages, setTotalPages] = useState(1)
  const [filterType, setFilterType] = useState('')
  const [filterSize, setFilterSize] = useState('')

  useEffect(() => {
    fetchSchools()
  }, [page, searchTerm, filterType, filterSize])

  const fetchSchools = async () => {
    setLoading(true)
    try {
      const mockSchools = [
        {
          id: 1,
          name: 'Institut Supérieur d\'Enseignement Professionnel',
          code: 'ISEP',
          email: 'contact@isep.sn',
          phone: '338257925',
          city: 'Dakar',
          schoolType: 'PROFESSIONAL_INSTITUTE',
          schoolSize: 'MEDIUM',
          maxStudents: 2000,
          currentStudentCount: 1234,
          hasDormitories: false,
          hasRestaurant: true,
          hasResearchLab: true,
          hasEnterprisePartnership: true,
          licenseStart: '2024-01-01',
          licenseEnd: '2025-12-31',
          isActive: true,
          rectorName: 'Prof. Ndiaye'
        },
        {
          id: 2,
          name: 'Université Cheikh Anta Diop',
          code: 'UCAD',
          email: 'info@ucad.sn',
          phone: '338246598',
          city: 'Dakar',
          schoolType: 'PUBLIC_UNIVERSITY',
          schoolSize: 'VERY_LARGE',
          maxStudents: 50000,
          currentStudentCount: 45000,
          hasDormitories: true,
          hasRestaurant: true,
          hasResearchLab: true,
          hasEnterprisePartnership: true,
          licenseStart: '2024-01-01',
          licenseEnd: '2025-12-31',
          isActive: true,
          rectorName: 'Prof. Sarr'
        }
      ]
      setSchools(mockSchools)
      setTotalPages(2)
    } catch (error) {
      console.error('Error fetching schools:', error)
    } finally {
      setLoading(false)
    }
  }

  const getTypeColor = (type) => {
    switch (type) {
      case 'PUBLIC_UNIVERSITY': return 'primary'
      case 'PRIVATE_UNIVERSITY': return 'success'
      case 'PROFESSIONAL_INSTITUTE': return 'warning'
      case 'TECHNICAL_SCHOOL': return 'info'
      case 'BUSINESS_SCHOOL': return 'secondary'
      default: return 'default'
    }
  }

  const getTypeText = (type) => {
    switch (type) {
      case 'PUBLIC_UNIVERSITY': return 'Université Publique'
      case 'PRIVATE_UNIVERSITY': return 'Université Privée'
      case 'PROFESSIONAL_INSTITUTE': return 'Institut Professionnel'
      case 'TECHNICAL_SCHOOL': return 'École Technique'
      case 'BUSINESS_SCHOOL': return 'École de Commerce'
      default: return type
    }
  }

  const getSizeText = (size) => {
    switch (size) {
      case 'SMALL': return 'Petite (<500)'
      case 'MEDIUM': return 'Moyenne (500-2000)'
      case 'LARGE': return 'Grande (2000-10000)'
      case 'VERY_LARGE': return 'Très Grande (>10000)'
      default: return size
    }
  }

  const getOccupancyRate = (current, max) => {
    return ((current / max) * 100).toFixed(1)
  }

  const getOccupancyColor = (rate) => {
    if (rate >= 90) return 'error'
    if (rate >= 70) return 'warning'
    return 'success'
  }

  return (
    <Box sx={{ p: 3 }}>
      <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 3 }}>
        <Typography variant="h4" component="h1">
          Gestion des Établissements
        </Typography>
        <Button variant="contained" startIcon={<AddIcon />}>
          Nouvel Établissement
        </Button>
      </Box>

      <Grid container spacing={3} sx={{ mb: 3 }}>
        <Grid item xs={12} md={3}>
          <Card>
            <CardContent>
              <Typography color="textSecondary" gutterBottom>
                Total Établissements
              </Typography>
              <Typography variant="h4">
                384
              </Typography>
            </CardContent>
          </Card>
        </Grid>
        <Grid item xs={12} md={3}>
          <Card>
            <CardContent>
              <Typography color="textSecondary" gutterBottom>
                Actifs
              </Typography>
              <Typography variant="h4" color="success.main">
                367
              </Typography>
            </CardContent>
          </Card>
        </Grid>
        <Grid item xs={12} md={3}>
          <Card>
            <CardContent>
              <Typography color="textSecondary" gutterBottom>
                Total Étudiants
              </Typography>
              <Typography variant="h4" color="info.main">
                125K
              </Typography>
            </CardContent>
          </Card>
        </Grid>
        <Grid item xs={12} md={3}>
          <Card>
            <CardContent>
              <Typography color="textSecondary" gutterBottom>
                Revenus Annuels
              </Typography>
              <Typography variant="h4" color="warning.main">
                2.5M
              </Typography>
            </CardContent>
          </Card>
        </Grid>
      </Grid>

      <Card>
        <CardContent>
          <Box sx={{ display: 'flex', gap: 2, mb: 3, flexWrap: 'wrap' }}>
            <TextField
              placeholder="Rechercher un établissement..."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              InputProps={{
                startAdornment: <SearchIcon />
              }}
              sx={{ flexGrow: 1, minWidth: 200 }}
            />
            <FormControl sx={{ minWidth: 200 }}>
              <InputLabel>Type</InputLabel>
              <Select
                value={filterType}
                onChange={(e) => setFilterType(e.target.value)}
                label="Type"
              >
                <MenuItem value="">Tous</MenuItem>
                <MenuItem value="PUBLIC_UNIVERSITY">Université Publique</MenuItem>
                <MenuItem value="PRIVATE_UNIVERSITY">Université Privée</MenuItem>
                <MenuItem value="PROFESSIONAL_INSTITUTE">Institut Professionnel</MenuItem>
                <MenuItem value="TECHNICAL_SCHOOL">École Technique</MenuItem>
                <MenuItem value="BUSINESS_SCHOOL">École de Commerce</MenuItem>
              </Select>
            </FormControl>
            <FormControl sx={{ minWidth: 150 }}>
              <InputLabel>Taille</InputLabel>
              <Select
                value={filterSize}
                onChange={(e) => setFilterSize(e.target.value)}
                label="Taille"
              >
                <MenuItem value="">Toutes</MenuItem>
                <MenuItem value="SMALL">Petite</MenuItem>
                <MenuItem value="MEDIUM">Moyenne</MenuItem>
                <MenuItem value="LARGE">Grande</MenuItem>
                <MenuItem value="VERY_LARGE">Très Grande</MenuItem>
              </Select>
            </FormControl>
            <Button variant="outlined">
              Exporter
            </Button>
          </Box>

          <TableContainer>
            <Table>
              <TableHead>
                <TableRow>
                  <TableCell>Nom</TableCell>
                  <TableCell>Code</TableCell>
                  <TableCell>Type</TableCell>
                  <TableCell>Taille</TableCell>
                  <TableCell>Ville</TableCell>
                  <TableCell>Étudiants</TableCell>
                  <TableCell>Taux Occupation</TableCell>
                  <TableCell>Modules</TableCell>
                  <TableCell>Licence</TableCell>
                  <TableCell>Statut</TableCell>
                  <TableCell>Actions</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {schools.map((school) => {
                  const occupancyRate = getOccupancyRate(school.currentStudentCount, school.maxStudents)
                  return (
                    <TableRow key={school.id}>
                      <TableCell>
                        <Box sx={{ display: 'flex', alignItems: 'center', gap: 1 }}>
                          <BusinessIcon color="action" />
                          <Box>
                            <Typography variant="body2" fontWeight="bold">
                              {school.name}
                            </Typography>
                            <Typography variant="caption" color="textSecondary">
                              {school.email}
                            </Typography>
                          </Box>
                        </Box>
                      </TableCell>
                      <TableCell>
                        <Typography variant="subtitle2" color="primary">
                          {school.code}
                        </Typography>
                      </TableCell>
                      <TableCell>
                        <Chip
                          label={getTypeText(school.schoolType)}
                          color={getTypeColor(school.schoolType)}
                          size="small"
                        />
                      </TableCell>
                      <TableCell>{getSizeText(school.schoolSize)}</TableCell>
                      <TableCell>{school.city}</TableCell>
                      <TableCell>
                        <Typography variant="body2">
                          {school.currentStudentCount.toLocaleString()} / {school.maxStudents.toLocaleString()}
                        </Typography>
                      </TableCell>
                      <TableCell>
                        <Box sx={{ display: 'flex', alignItems: 'center', gap: 1 }}>
                          <Typography variant="body2">
                            {occupancyRate}%
                          </Typography>
                          <Chip
                            label={occupancyRate}
                            color={getOccupancyColor(parseFloat(occupancyRate))}
                            size="small"
                          />
                        </Box>
                      </TableCell>
                      <TableCell>
                        <Box sx={{ display: 'flex', flexDirection: 'column', gap: 0.5 }}>
                          <FormControlLabel
                            control={<Switch size="small" checked={school.hasDormitories} />}
                            label="Cités"
                            disabled
                          />
                          <FormControlLabel
                            control={<Switch size="small" checked={school.hasRestaurant} />}
                            label="Resto"
                            disabled
                          />
                          <FormControlLabel
                            control={<Switch size="small" checked={school.hasResearchLab} />}
                            label="Labos"
                            disabled
                          />
                        </Box>
                      </TableCell>
                      <TableCell>
                        <Box>
                          <Typography variant="caption" display="block">
                            {school.licenseStart} - {school.licenseEnd}
                          </Typography>
                          <Chip
                            label="Active"
                            color="success"
                            size="small"
                          />
                        </Box>
                      </TableCell>
                      <TableCell>
                        <Chip
                          label={school.isActive ? 'Actif' : 'Inactif'}
                          color={school.isActive ? 'success' : 'error'}
                          size="small"
                        />
                      </TableCell>
                      <TableCell>
                        <IconButton size="small" color="info">
                          <ViewIcon />
                        </IconButton>
                        <IconButton size="small" color="primary">
                          <EditIcon />
                        </IconButton>
                        <IconButton size="small" color="error">
                          <DeleteIcon />
                        </IconButton>
                      </TableCell>
                    </TableRow>
                  )
                })}
              </TableBody>
            </Table>
          </TableContainer>

          <Box sx={{ display: 'flex', justifyContent: 'center', mt: 3 }}>
            <Pagination
              count={totalPages}
              page={page}
              onChange={(event, value) => setPage(value)}
              color="primary"
            />
          </Box>
        </CardContent>
      </Card>

      <Fab
        color="primary"
        aria-label="add"
        sx={{ position: 'fixed', bottom: 16, right: 16 }}
      >
        <AddIcon />
      </Fab>
    </Box>
  )
}

export default SchoolsPage
