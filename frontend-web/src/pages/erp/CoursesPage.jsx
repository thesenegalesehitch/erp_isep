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
  MenuItem,
  Select,
  FormControl,
  InputLabel
} from '@mui/material'
import {
  Add as AddIcon,
  Search as SearchIcon,
  Edit as EditIcon,
  Delete as DeleteIcon,
  Visibility as ViewIcon,
  Schedule as ScheduleIcon
} from '@mui/icons-material'

const CoursesPage = () => {
  const [courses, setCourses] = useState([])
  const [loading, setLoading] = useState(true)
  const [searchTerm, setSearchTerm] = useState('')
  const [page, setPage] = useState(1)
  const [totalPages, setTotalPages] = useState(1)
  const [filterDepartment, setFilterDepartment] = useState('')
  const [filterType, setFilterType] = useState('')

  useEffect(() => {
    fetchCourses()
  }, [page, searchTerm, filterDepartment, filterType])

  const fetchCourses = async () => {
    setLoading(true)
    try {
      // Simulation API call
      const mockCourses = [
        {
          id: 1,
          courseCode: 'INF301',
          title: 'Algorithmique Avancée',
          department: 'Informatique',
          program: 'Licence 3',
          courseType: 'MANDATORY',
          credits: 4,
          totalHours: 60,
          currentEnrolled: 45,
          maxStudents: 50,
          teacher: 'Dr. Sow',
          semester: 'Semestre 1'
        },
        {
          id: 2,
          courseCode: 'GES201',
          title: 'Marketing Digital',
          department: 'Gestion',
          program: 'Master 1',
          courseType: 'OPTIONAL',
          credits: 3,
          totalHours: 45,
          currentEnrolled: 28,
          maxStudents: 40,
          teacher: 'Mme. Fall',
          semester: 'Semestre 1'
        }
      ]
      setCourses(mockCourses)
      setTotalPages(3)
    } catch (error) {
      console.error('Error fetching courses:', error)
    } finally {
      setLoading(false)
    }
  }

  const handleSearch = (event) => {
    setSearchTerm(event.target.value)
    setPage(1)
  }

  const getTypeColor = (type) => {
    switch (type) {
      case 'MANDATORY': return 'error'
      case 'OPTIONAL': return 'warning'
      case 'ELECTIVE': return 'info'
      case 'WORKSHOP': return 'success'
      default: return 'default'
    }
  }

  const getTypeText = (type) => {
    switch (type) {
      case 'MANDATORY': return 'Obligatoire'
      case 'OPTIONAL': return 'Optionnel'
      case 'ELECTIVE': return 'Électif'
      case 'WORKSHOP': return 'Atelier'
      default: return type
    }
  }

  const getEnrollmentColor = (current, max) => {
    const percentage = (current / max) * 100
    if (percentage >= 90) return 'error'
    if (percentage >= 70) return 'warning'
    return 'success'
  }

  return (
    <Box sx={{ p: 3 }}>
      <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 3 }}>
        <Typography variant="h4" component="h1">
          Gestion des Cours
        </Typography>
        <Button variant="contained" startIcon={<AddIcon />}>
          Nouveau Cours
        </Button>
      </Box>

      <Grid container spacing={3} sx={{ mb: 3 }}>
        <Grid item xs={12} md={3}>
          <Card>
            <CardContent>
              <Typography color="textSecondary" gutterBottom>
                Total Cours
              </Typography>
              <Typography variant="h4">
                156
              </Typography>
            </CardContent>
          </Card>
        </Grid>
        <Grid item xs={12} md={3}>
          <Card>
            <CardContent>
              <Typography color="textSecondary" gutterBottom>
                Cours Obligatoires
              </Typography>
              <Typography variant="h4" color="error.main">
                89
              </Typography>
            </CardContent>
          </Card>
        </Grid>
        <Grid item xs={12} md={3}>
          <Card>
            <CardContent>
              <Typography color="textSecondary" gutterBottom>
                Cours Optionnels
              </Typography>
              <Typography variant="h4" color="warning.main">
                45
              </Typography>
            </CardContent>
          </Card>
        </Grid>
        <Grid item xs={12} md={3}>
          <Card>
            <CardContent>
              <Typography color="textSecondary" gutterBottom>
                Taux Occupation
              </Typography>
              <Typography variant="h4" color="success.main">
                78%
              </Typography>
            </CardContent>
          </Card>
        </Grid>
      </Grid>

      <Card>
        <CardContent>
          <Box sx={{ display: 'flex', gap: 2, mb: 3, flexWrap: 'wrap' }}>
            <TextField
              placeholder="Rechercher un cours..."
              value={searchTerm}
              onChange={handleSearch}
              InputProps={{
                startAdornment: <SearchIcon />
              }}
              sx={{ flexGrow: 1, minWidth: 200 }}
            />
            <FormControl sx={{ minWidth: 150 }}>
              <InputLabel>Département</InputLabel>
              <Select
                value={filterDepartment}
                onChange={(e) => setFilterDepartment(e.target.value)}
                label="Département"
              >
                <MenuItem value="">Tous</MenuItem>
                <MenuItem value="Informatique">Informatique</MenuItem>
                <MenuItem value="Gestion">Gestion</MenuItem>
                <MenuItem value="Marketing">Marketing</MenuItem>
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
                <MenuItem value="MANDATORY">Obligatoire</MenuItem>
                <MenuItem value="OPTIONAL">Optionnel</MenuItem>
                <MenuItem value="ELECTIVE">Électif</MenuItem>
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
                  <TableCell>Code</TableCell>
                  <TableCell>Titre</TableCell>
                  <TableCell>Département</TableCell>
                  <TableCell>Programme</TableCell>
                  <TableCell>Type</TableCell>
                  <TableCell>Credits</TableCell>
                  <TableCell>Heures</TableCell>
                  <TableCell>Inscription</TableCell>
                  <TableCell>Enseignant</TableCell>
                  <TableCell>Actions</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {courses.map((course) => (
                  <TableRow key={course.id}>
                    <TableCell>
                      <Typography variant="subtitle2" color="primary">
                        {course.courseCode}
                      </Typography>
                    </TableCell>
                    <TableCell>{course.title}</TableCell>
                    <TableCell>{course.department}</TableCell>
                    <TableCell>{course.program}</TableCell>
                    <TableCell>
                      <Chip
                        label={getTypeText(course.courseType)}
                        color={getTypeColor(course.courseType)}
                        size="small"
                      />
                    </TableCell>
                    <TableCell>{course.credits}</TableCell>
                    <TableCell>{course.totalHours}h</TableCell>
                    <TableCell>
                      <Box sx={{ display: 'flex', alignItems: 'center', gap: 1 }}>
                        <Typography variant="body2">
                          {course.currentEnrolled}/{course.maxStudents}
                        </Typography>
                        <Chip
                          label={`${Math.round((course.currentEnrolled / course.maxStudents) * 100)}%`}
                          color={getEnrollmentColor(course.currentEnrolled, course.maxStudents)}
                          size="small"
                        />
                      </Box>
                    </TableCell>
                    <TableCell>{course.teacher}</TableCell>
                    <TableCell>
                      <IconButton size="small" color="info">
                        <ViewIcon />
                      </IconButton>
                      <IconButton size="small" color="primary">
                        <ScheduleIcon />
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

export default CoursesPage
