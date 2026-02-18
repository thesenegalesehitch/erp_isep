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
  MenuItem
} from '@mui/material'
import {
  Add as AddIcon,
  Search as SearchIcon,
  Edit as EditIcon,
  Delete as DeleteIcon,
  Visibility as ViewIcon,
  Schedule as ScheduleIcon
} from '@mui/icons-material'

const SchedulePage = () => {
  const [schedules, setSchedules] = useState([])
  const [loading, setLoading] = useState(true)
  const [searchTerm, setSearchTerm] = useState('')
  const [page, setPage] = useState(1)
  const [totalPages, setTotalPages] = useState(1)
  const [filterDay, setFilterDay] = useState('')
  const [filterType, setFilterType] = useState('')

  useEffect(() => {
    fetchSchedules()
  }, [page, searchTerm, filterDay, filterType])

  const fetchSchedules = async () => {
    setLoading(true)
    try {
      const mockSchedules = [
        {
          id: 1,
          course: { courseCode: 'INF301', title: 'Algorithmique Avancée' },
          teacher: { firstName: 'Dr.', lastName: 'Sow' },
          dayOfWeek: 'LUNDI',
          startTime: '08:00',
          endTime: '10:00',
          roomNumber: 'A101',
          building: 'Batiment A',
          scheduleType: 'LECTURE',
          semester: 'Semestre 1'
        },
        {
          id: 2,
          course: { courseCode: 'GES201', title: 'Marketing Digital' },
          teacher: { firstName: 'Mme.', lastName: 'Fall' },
          dayOfWeek: 'MARDI',
          startTime: '14:00',
          endTime: '16:00',
          roomNumber: 'B205',
          building: 'Batiment B',
          scheduleType: 'TUTORIAL',
          semester: 'Semestre 1'
        }
      ]
      setSchedules(mockSchedules)
      setTotalPages(3)
    } catch (error) {
      console.error('Error fetching schedules:', error)
    } finally {
      setLoading(false)
    }
  }

  const getTypeColor = (type) => {
    switch (type) {
      case 'LECTURE': return 'primary'
      case 'TUTORIAL': return 'success'
      case 'LAB': return 'warning'
      case 'WORKSHOP': return 'info'
      default: return 'default'
    }
  }

  const getTypeText = (type) => {
    switch (type) {
      case 'LECTURE': return 'Cours'
      case 'TUTORIAL': return 'TD'
      case 'LAB': return 'TP'
      case 'WORKSHOP': return 'Atelier'
      default: return type
    }
  }

  return (
    <Box sx={{ p: 3 }}>
      <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 3 }}>
        <Typography variant="h4" component="h1">
          Emploi du Temps
        </Typography>
        <Button variant="contained" startIcon={<AddIcon />}>
          Nouveau Créneau
        </Button>
      </Box>

      <Grid container spacing={3} sx={{ mb: 3 }}>
        <Grid item xs={12} md={3}>
          <Card>
            <CardContent>
              <Typography color="textSecondary" gutterBottom>
                Total Créneaux
              </Typography>
              <Typography variant="h4">
                234
              </Typography>
            </CardContent>
          </Card>
        </Grid>
        <Grid item xs={12} md={3}>
          <Card>
            <CardContent>
              <Typography color="textSecondary" gutterBottom>
                Cours
              </Typography>
              <Typography variant="h4" color="primary.main">
                156
              </Typography>
            </CardContent>
          </Card>
        </Grid>
        <Grid item xs={12} md={3}>
          <Card>
            <CardContent>
              <Typography color="textSecondary" gutterBottom>
                TD/TP
              </Typography>
              <Typography variant="h4" color="success.main">
                67
              </Typography>
            </CardContent>
          </Card>
        </Grid>
        <Grid item xs={12} md={3}>
          <Card>
            <CardContent>
              <Typography color="textSecondary" gutterBottom>
                Salles Utilisées
              </Typography>
              <Typography variant="h4" color="warning.main">
                45
              </Typography>
            </CardContent>
          </Card>
        </Grid>
      </Grid>

      <Card>
        <CardContent>
          <Box sx={{ display: 'flex', gap: 2, mb: 3, flexWrap: 'wrap' }}>
            <TextField
              placeholder="Rechercher un créneau..."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              InputProps={{
                startAdornment: <SearchIcon />
              }}
              sx={{ flexGrow: 1, minWidth: 200 }}
            />
            <FormControl sx={{ minWidth: 150 }}>
              <InputLabel>Jour</InputLabel>
              <Select
                value={filterDay}
                onChange={(e) => setFilterDay(e.target.value)}
                label="Jour"
              >
                <MenuItem value="">Tous</MenuItem>
                <MenuItem value="LUNDI">Lundi</MenuItem>
                <MenuItem value="MARDI">Mardi</MenuItem>
                <MenuItem value="MERCREDI">Mercredi</MenuItem>
                <MenuItem value="JEUDI">Jeudi</MenuItem>
                <MenuItem value="VENDREDI">Vendredi</MenuItem>
                <MenuItem value="SAMEDI">Samedi</MenuItem>
              </Select>
            </FormControl>
            <FormControl sx={{ minWidth: 150 }}>
              <InputLabel>Type</InputLabel>
              <Select
                value={filterType}
                onChange={(e) => setFilterType(e.target.value)}
                label="Type"
              >
                <MenuItem value="">Tous</MenuItem>
                <MenuItem value="LECTURE">Cours</MenuItem>
                <MenuItem value="TUTORIAL">TD</MenuItem>
                <MenuItem value="LAB">TP</MenuItem>
                <MenuItem value="WORKSHOP">Atelier</MenuItem>
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
                  <TableCell>Jour</TableCell>
                  <TableCell>Heures</TableCell>
                  <TableCell>Cours</TableCell>
                  <TableCell>Enseignant</TableCell>
                  <TableCell>Type</TableCell>
                  <TableCell>Salle</TableCell>
                  <TableCell>Bâtiment</TableCell>
                  <TableCell>Semestre</TableCell>
                  <TableCell>Actions</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {schedules.map((schedule) => (
                  <TableRow key={schedule.id}>
                    <TableCell>
                      <Chip label={schedule.dayOfWeek} color="primary" size="small" />
                    </TableCell>
                    <TableCell>
                      <Typography variant="body2">
                        {schedule.startTime} - {schedule.endTime}
                      </Typography>
                    </TableCell>
                    <TableCell>
                      <Box>
                        <Typography variant="body2">
                          {schedule.course.title}
                        </Typography>
                        <Typography variant="caption" color="textSecondary">
                          {schedule.course.courseCode}
                        </Typography>
                      </Box>
                    </TableCell>
                    <TableCell>
                      {schedule.teacher.firstName} {schedule.teacher.lastName}
                    </TableCell>
                    <TableCell>
                      <Chip
                        label={getTypeText(schedule.scheduleType)}
                        color={getTypeColor(schedule.scheduleType)}
                        size="small"
                      />
                    </TableCell>
                    <TableCell>{schedule.roomNumber}</TableCell>
                    <TableCell>{schedule.building}</TableCell>
                    <TableCell>{schedule.semester}</TableCell>
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
                ))}
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

export default SchedulePage
