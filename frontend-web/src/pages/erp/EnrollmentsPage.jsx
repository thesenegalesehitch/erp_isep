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
  CheckCircle as ApproveIcon,
  Cancel as RejectIcon,
  Visibility as ViewIcon
} from '@mui/icons-material'

const EnrollmentsPage = () => {
  const [enrollments, setEnrollments] = useState([])
  const [loading, setLoading] = useState(true)
  const [searchTerm, setSearchTerm] = useState('')
  const [page, setPage] = useState(1)
  const [totalPages, setTotalPages] = useState(1)
  const [filterStatus, setFilterStatus] = useState('')

  useEffect(() => {
    fetchEnrollments()
  }, [page, searchTerm, filterStatus])

  const fetchEnrollments = async () => {
    setLoading(true)
    try {
      const mockEnrollments = [
        {
          id: 1,
          enrollmentId: 'INS2024001',
          student: { firstName: 'Aliou', lastName: 'Diop', studentId: '2024001' },
          course: { courseCode: 'INF301', title: 'Algorithmique Avancée' },
          enrollmentType: 'FULL_TIME',
          status: 'PENDING',
          enrollmentDate: '2024-10-15',
          tuitionFee: 250000,
          paymentStatus: 'PENDING'
        },
        {
          id: 2,
          enrollmentId: 'INS2024002',
          student: { firstName: 'Fatou', lastName: 'Ba', studentId: '2024002' },
          course: { courseCode: 'GES201', title: 'Marketing Digital' },
          enrollmentType: 'FULL_TIME',
          status: 'APPROVED',
          enrollmentDate: '2024-10-16',
          tuitionFee: 200000,
          paymentStatus: 'PAID'
        }
      ]
      setEnrollments(mockEnrollments)
      setTotalPages(4)
    } catch (error) {
      console.error('Error fetching enrollments:', error)
    } finally {
      setLoading(false)
    }
  }

  const getStatusColor = (status) => {
    switch (status) {
      case 'PENDING': return 'warning'
      case 'APPROVED': return 'success'
      case 'REJECTED': return 'error'
      case 'ENROLLED': return 'info'
      default: return 'default'
    }
  }

  const getStatusText = (status) => {
    switch (status) {
      case 'PENDING': return 'En attente'
      case 'APPROVED': return 'Approuvé'
      case 'REJECTED': return 'Rejeté'
      case 'ENROLLED': return 'Inscrit'
      default: return status
    }
  }

  return (
    <Box sx={{ p: 3 }}>
      <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 3 }}>
        <Typography variant="h4" component="h1">
          Gestion des Inscriptions
        </Typography>
        <Button variant="contained" startIcon={<AddIcon />}>
          Nouvelle Inscription
        </Button>
      </Box>

      <Grid container spacing={3} sx={{ mb: 3 }}>
        <Grid item xs={12} md={3}>
          <Card>
            <CardContent>
              <Typography color="textSecondary" gutterBottom>
                Total Inscriptions
              </Typography>
              <Typography variant="h4">
                2,345
              </Typography>
            </CardContent>
          </Card>
        </Grid>
        <Grid item xs={12} md={3}>
          <Card>
            <CardContent>
              <Typography color="textSecondary" gutterBottom>
                En Attente
              </Typography>
              <Typography variant="h4" color="warning.main">
                156
              </Typography>
            </CardContent>
          </Card>
        </Grid>
        <Grid item xs={12} md={3}>
          <Card>
            <CardContent>
              <Typography color="textSecondary" gutterBottom>
                Approuvées
              </Typography>
              <Typography variant="h4" color="success.main">
                1,890
              </Typography>
            </CardContent>
          </Card>
        </Grid>
        <Grid item xs={12} md={3}>
          <Card>
            <CardContent>
              <Typography color="textSecondary" gutterBottom>
                Revenus
              </Typography>
              <Typography variant="h4" color="info.main">
                450M
              </Typography>
            </CardContent>
          </Card>
        </Grid>
      </Grid>

      <Card>
        <CardContent>
          <Box sx={{ display: 'flex', gap: 2, mb: 3, flexWrap: 'wrap' }}>
            <TextField
              placeholder="Rechercher une inscription..."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              InputProps={{
                startAdornment: <SearchIcon />
              }}
              sx={{ flexGrow: 1, minWidth: 200 }}
            />
            <FormControl sx={{ minWidth: 150 }}>
              <InputLabel>Statut</InputLabel>
              <Select
                value={filterStatus}
                onChange={(e) => setFilterStatus(e.target.value)}
                label="Statut"
              >
                <MenuItem value="">Tous</MenuItem>
                <MenuItem value="PENDING">En attente</MenuItem>
                <MenuItem value="APPROVED">Approuvé</MenuItem>
                <MenuItem value="REJECTED">Rejeté</MenuItem>
                <MenuItem value="ENROLLED">Inscrit</MenuItem>
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
                  <TableCell>ID Inscription</TableCell>
                  <TableCell>Étudiant</TableCell>
                  <TableCell>Cours</TableCell>
                  <TableCell>Type</TableCell>
                  <TableCell>Statut</TableCell>
                  <TableCell>Date</TableCell>
                  <TableCell>Frais (FCFA)</TableCell>
                  <TableCell>Paiement</TableCell>
                  <TableCell>Actions</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {enrollments.map((enrollment) => (
                  <TableRow key={enrollment.id}>
                    <TableCell>
                      <Typography variant="subtitle2" color="primary">
                        {enrollment.enrollmentId}
                      </Typography>
                    </TableCell>
                    <TableCell>
                      <Box>
                        <Typography variant="body2">
                          {enrollment.student.firstName} {enrollment.student.lastName}
                        </Typography>
                        <Typography variant="caption" color="textSecondary">
                          {enrollment.student.studentId}
                        </Typography>
                      </Box>
                    </TableCell>
                    <TableCell>
                      <Box>
                        <Typography variant="body2">
                          {enrollment.course.title}
                        </Typography>
                        <Typography variant="caption" color="textSecondary">
                          {enrollment.course.courseCode}
                        </Typography>
                      </Box>
                    </TableCell>
                    <TableCell>{enrollment.enrollmentType}</TableCell>
                    <TableCell>
                      <Chip
                        label={getStatusText(enrollment.status)}
                        color={getStatusColor(enrollment.status)}
                        size="small"
                      />
                    </TableCell>
                    <TableCell>{enrollment.enrollmentDate}</TableCell>
                    <TableCell>{enrollment.tuitionFee.toLocaleString()}</TableCell>
                    <TableCell>
                      <Chip
                        label={enrollment.paymentStatus}
                        color={enrollment.paymentStatus === 'PAID' ? 'success' : 'warning'}
                        size="small"
                      />
                    </TableCell>
                    <TableCell>
                      <IconButton size="small" color="info">
                        <ViewIcon />
                      </IconButton>
                      {enrollment.status === 'PENDING' && (
                        <>
                          <IconButton size="small" color="success">
                            <ApproveIcon />
                          </IconButton>
                          <IconButton size="small" color="error">
                            <RejectIcon />
                          </IconButton>
                        </>
                      )}
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

export default EnrollmentsPage
