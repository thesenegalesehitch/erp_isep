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
  Fab
} from '@mui/material'
import {
  Add as AddIcon,
  Search as SearchIcon,
  Edit as EditIcon,
  Delete as DeleteIcon,
  Visibility as ViewIcon
} from '@mui/icons-material'

const StudentsPage = () => {
  const [students, setStudents] = useState([])
  const [loading, setLoading] = useState(true)
  const [searchTerm, setSearchTerm] = useState('')
  const [page, setPage] = useState(1)
  const [totalPages, setTotalPages] = useState(1)

  useEffect(() => {
    fetchStudents()
  }, [page, searchTerm])

  const fetchStudents = async () => {
    setLoading(true)
    try {
      // Simulation API call
      const mockStudents = [
        {
          id: 1,
          studentId: '2024001',
          firstName: 'Aliou',
          lastName: 'Diop',
          email: 'aliou.diop@univ.sn',
          phone: '771234567',
          department: 'Informatique',
          program: 'Licence 3',
          studyLevel: 'BACHELOR_3',
          enrollmentStatus: 'ENROLLED',
          enrollmentDate: '2024-10-15'
        },
        {
          id: 2,
          studentId: '2024002',
          firstName: 'Fatou',
          lastName: 'Ba',
          email: 'fatou.ba@univ.sn',
          phone: '762345678',
          department: 'Gestion',
          program: 'Master 1',
          studyLevel: 'MASTER_1',
          enrollmentStatus: 'ENROLLED',
          enrollmentDate: '2024-10-16'
        }
      ]
      setStudents(mockStudents)
      setTotalPages(5)
    } catch (error) {
      console.error('Error fetching students:', error)
    } finally {
      setLoading(false)
    }
  }

  const handleSearch = (event) => {
    setSearchTerm(event.target.value)
    setPage(1)
  }

  const getStatusColor = (status) => {
    switch (status) {
      case 'ENROLLED': return 'success'
      case 'ON_LEAVE': return 'warning'
      case 'GRADUATED': return 'info'
      case 'SUSPENDED': return 'error'
      default: return 'default'
    }
  }

  const getStatusText = (status) => {
    switch (status) {
      case 'ENROLLED': return 'Inscrit'
      case 'ON_LEAVE': return 'Congé'
      case 'GRADUATED': return 'Diplômé'
      case 'SUSPENDED': return 'Suspendu'
      default: return status
    }
  }

  return (
    <Box sx={{ p: 3 }}>
      <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 3 }}>
        <Typography variant="h4" component="h1">
          Gestion des Étudiants
        </Typography>
        <Button variant="contained" startIcon={<AddIcon />}>
          Nouvel Étudiant
        </Button>
      </Box>

      <Grid container spacing={3} sx={{ mb: 3 }}>
        <Grid item xs={12} md={3}>
          <Card>
            <CardContent>
              <Typography color="textSecondary" gutterBottom>
                Total Étudiants
              </Typography>
              <Typography variant="h4">
                1,234
              </Typography>
            </CardContent>
          </Card>
        </Grid>
        <Grid item xs={12} md={3}>
          <Card>
            <CardContent>
              <Typography color="textSecondary" gutterBottom>
                Inscrits
              </Typography>
              <Typography variant="h4" color="success.main">
                1,156
              </Typography>
            </CardContent>
          </Card>
        </Grid>
        <Grid item xs={12} md={3}>
          <Card>
            <CardContent>
              <Typography color="textSecondary" gutterBottom>
                Nouveaux
              </Typography>
              <Typography variant="h4" color="info.main">
                234
              </Typography>
            </CardContent>
          </Card>
        </Grid>
        <Grid item xs={12} md={3}>
          <Card>
            <CardContent>
              <Typography color="textSecondary" gutterBottom>
                Diplômés
              </Typography>
              <Typography variant="h4" color="warning.main">
                78
              </Typography>
            </CardContent>
          </Card>
        </Grid>
      </Grid>

      <Card>
        <CardContent>
          <Box sx={{ display: 'flex', gap: 2, mb: 3 }}>
            <TextField
              placeholder="Rechercher un étudiant..."
              value={searchTerm}
              onChange={handleSearch}
              InputProps={{
                startAdornment: <SearchIcon />
              }}
              sx={{ flexGrow: 1 }}
            />
            <Button variant="outlined">
              Exporter
            </Button>
          </Box>

          <TableContainer>
            <Table>
              <TableHead>
                <TableRow>
                  <TableCell>ID Étudiant</TableCell>
                  <TableCell>Nom</TableCell>
                  <TableCell>Email</TableCell>
                  <TableCell>Département</TableCell>
                  <TableCell>Programme</TableCell>
                  <TableCell>Statut</TableCell>
                  <TableCell>Date d'inscription</TableCell>
                  <TableCell>Actions</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {students.map((student) => (
                  <TableRow key={student.id}>
                    <TableCell>{student.studentId}</TableCell>
                    <TableCell>{`${student.firstName} ${student.lastName}`}</TableCell>
                    <TableCell>{student.email}</TableCell>
                    <TableCell>{student.department}</TableCell>
                    <TableCell>{student.program}</TableCell>
                    <TableCell>
                      <Chip
                        label={getStatusText(student.enrollmentStatus)}
                        color={getStatusColor(student.enrollmentStatus)}
                        size="small"
                      />
                    </TableCell>
                    <TableCell>{student.enrollmentDate}</TableCell>
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

export default StudentsPage
