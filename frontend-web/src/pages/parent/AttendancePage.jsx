import React, { useState, useEffect } from 'react'
import {
  Box,
  Card,
  CardContent,
  Typography,
  Grid,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Chip,
  Button,
  Alert,
  CircularProgress,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  LinearProgress,
  CalendarMonth,
  CheckCircle,
  Schedule,
  Warning
} from '@mui/icons-material'
import { useNavigate } from 'react-router-dom'
import { parentAuthStore } from '../../stores/parentAuthStore'

const AttendancePage = () => {
  const navigate = useNavigate()
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')
  const [attendance, setAttendance] = useState([])
  const [selectedStudent, setSelectedStudent] = useState('')
  const [selectedMonth, setSelectedMonth] = useState(new Date().getMonth() + 1)
  const [selectedYear, setSelectedYear] = useState(new Date().getFullYear())

  const students = [
    { id: 1, name: 'Marie Diop' },
    { id: 2, name: 'Pierre Ba' }
  ]

  const months = [
    { value: 1, label: 'Janvier' },
    { value: 2, label: 'Février' },
    { value: 3, label: 'Mars' },
    { value: 4, label: 'Avril' },
    { value: 5, label: 'Mai' },
    { value: 6, label: 'Juin' },
    { value: 9, label: 'Septembre' },
    { value: 10, label: 'Octobre' },
    { value: 11, label: 'Novembre' },
    { value: 12, label: 'Décembre' }
  ]

  const years = [
    { value: 2024, label: '2024' },
    { value: 2023, label: '2023' }
  ]

  useEffect(() => {
    if (!parentAuthStore.isAuthenticated) {
      navigate('/login')
      return
    }

    if (selectedStudent) {
      loadAttendance()
    }
  }, [selectedStudent, selectedMonth, selectedYear])

  const loadAttendance = async () => {
    try {
      setLoading(true)
      // Simuler l'appel API
      const mockAttendance = [
        {
          id: 1,
          date: '2024-02-05',
          subject: 'Mathématiques',
          teacherName: 'M. Diop',
          status: 'PRESENT',
          notes: '',
          lateMinutes: 0
        },
        {
          id: 2,
          date: '2024-02-06',
          subject: 'Français',
          teacherName: 'Mme Ba',
          status: 'LATE',
          notes: 'Retard de 10 minutes',
          lateMinutes: 10
        },
        {
          id: 3,
          date: '2024-02-07',
          subject: 'Physique',
          teacherName: 'M. Fall',
          status: 'ABSENT_EXCUSED',
          notes: 'Maladie - Certificat médical fourni',
          lateMinutes: 0
        },
        {
          id: 4,
          date: '2024-02-08',
          subject: 'Histoire',
          teacherName: 'M. Sarr',
          status: 'PRESENT',
          notes: '',
          lateMinutes: 0
        },
        {
          id: 5,
          date: '2024-02-09',
          subject: 'Anglais',
          teacherName: 'Mme Kane',
          status: 'ABSENT_UNEXCUSED',
          notes: 'Absence injustifiée',
          lateMinutes: 0
        }
      ]
      setAttendance(mockAttendance)
    } catch (err) {
      setError(err.message || 'Erreur lors du chargement des présences')
    } finally {
      setLoading(false)
    }
  }

  const getStatusColor = (status) => {
    switch (status) {
      case 'PRESENT': return 'success'
      case 'LATE': return 'warning'
      case 'ABSENT_EXCUSED': return 'info'
      case 'ABSENT_UNEXCUSED': return 'error'
      default: return 'default'
    }
  }

  const getStatusIcon = (status) => {
    switch (status) {
      case 'PRESENT': return <CheckCircle color="success" />
      case 'LATE': return <Schedule color="warning" />
      case 'ABSENT_EXCUSED': return <Warning color="info" />
      case 'ABSENT_UNEXCUSED': return <Warning color="error" />
      default: return <CalendarMonth />
    }
  }

  const getStatusLabel = (status) => {
    switch (status) {
      case 'PRESENT': return 'Présent'
      case 'LATE': return 'En retard'
      case 'ABSENT_EXCUSED': return 'Absent (justifié)'
      case 'ABSENT_UNEXCUSED': return 'Absent (non justifié)'
      default: return status
    }
  }

  const calculateAttendanceStats = () => {
    const totalDays = attendance.length
    const presentDays = attendance.filter(a => a.status === 'PRESENT').length
    const lateDays = attendance.filter(a => a.status === 'LATE').length
    const excusedAbsences = attendance.filter(a => a.status === 'ABSENT_EXCUSED').length
    const unexcusedAbsences = attendance.filter(a => a.status === 'ABSENT_UNEXCUSED').length

    const attendanceRate = totalDays > 0 ? ((presentDays + lateDays) / totalDays) * 100 : 0

    return {
      totalDays,
      presentDays,
      lateDays,
      excusedAbsences,
      unexcusedAbsences,
      attendanceRate: attendanceRate.toFixed(1)
    }
  }

  const stats = calculateAttendanceStats()

  if (loading && !selectedStudent) {
    return (
      <Box sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', minHeight: '60vh' }}>
        <CircularProgress />
      </Box>
    )
  }

  return (
    <Box sx={{ p: 3 }}>
      <Typography variant="h4" gutterBottom>
        Suivi de Présence
      </Typography>

      <Grid container spacing={3} sx={{ mb: 3 }}>
        <Grid item xs={12} md={3}>
          <FormControl fullWidth>
            <InputLabel>Élève</InputLabel>
            <Select
              value={selectedStudent}
              onChange={(e) => setSelectedStudent(e.target.value)}
            >
              {students.map((student) => (
                <MenuItem key={student.id} value={student.id}>
                  {student.name}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </Grid>

        <Grid item xs={12} md={3}>
          <FormControl fullWidth>
            <InputLabel>Mois</InputLabel>
            <Select
              value={selectedMonth}
              onChange={(e) => setSelectedMonth(e.target.value)}
            >
              {months.map((month) => (
                <MenuItem key={month.value} value={month.value}>
                  {month.label}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </Grid>

        <Grid item xs={12} md={3}>
          <FormControl fullWidth>
            <InputLabel>Année</InputLabel>
            <Select
              value={selectedYear}
              onChange={(e) => setSelectedYear(e.target.value)}
            >
              {years.map((year) => (
                <MenuItem key={year.value} value={year.value}>
                  {year.label}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </Grid>

        <Grid item xs={12} md={3}>
          <Button
            fullWidth
            variant="contained"
            onClick={loadAttendance}
            disabled={!selectedStudent || loading}
            sx={{ height: '56px' }}
          >
            {loading ? 'Chargement...' : 'Actualiser'}
          </Button>
        </Grid>
      </Grid>

      {error && (
        <Alert severity="error" sx={{ mb: 3 }}>
          {error}
        </Alert>
      )}

      {!selectedStudent ? (
        <Paper sx={{ p: 4, textAlign: 'center' }}>
          <CalendarMonth sx={{ fontSize: 64, color: 'text.secondary', mb: 2 }} />
          <Typography variant="h6" color="text.secondary">
            Sélectionnez un élève pour voir le suivi de présence
          </Typography>
        </Paper>
      ) : (
        <>
          <Grid container spacing={3} sx={{ mb: 3 }}>
            <Grid item xs={12} md={2.4}>
              <Card>
                <CardContent>
                  <Box sx={{ display: 'flex', alignItems: 'center', mb: 2 }}>
                    <CalendarMonth color="primary" sx={{ mr: 1 }} />
                    <Typography variant="h6">Total jours</Typography>
                  </Box>
                  <Typography variant="h4" color="primary">
                    {stats.totalDays}
                  </Typography>
                  <Typography variant="body2" color="text.secondary">
                    Jours scolaires
                  </Typography>
                </CardContent>
              </Card>
            </Grid>

            <Grid item xs={12} md={2.4}>
              <Card>
                <CardContent>
                  <Box sx={{ display: 'flex', alignItems: 'center', mb: 2 }}>
                    <CheckCircle color="success" sx={{ mr: 1 }} />
                    <Typography variant="h6">Présents</Typography>
                  </Box>
                  <Typography variant="h4" color="success.main">
                    {stats.presentDays}
                  </Typography>
                  <Typography variant="body2" color="text.secondary">
                    Jours présents
                  </Typography>
                </CardContent>
              </Card>
            </Grid>

            <Grid item xs={12} md={2.4}>
              <Card>
                <CardContent>
                  <Box sx={{ display: 'flex', alignItems: 'center', mb: 2 }}>
                    <Schedule color="warning" sx={{ mr: 1 }} />
                    <Typography variant="h6">Retards</Typography>
                  </Box>
                  <Typography variant="h4" color="warning.main">
                    {stats.lateDays}
                  </Typography>
                  <Typography variant="body2" color="text.secondary">
                    Jours en retard
                  </Typography>
                </CardContent>
              </Card>
            </Grid>

            <Grid item xs={12} md={2.4}>
              <Card>
                <CardContent>
                  <Box sx={{ display: 'flex', alignItems: 'center', mb: 2 }}>
                    <Warning color="info" sx={{ mr: 1 }} />
                    <Typography variant="h6">Absences just.</Typography>
                  </Box>
                  <Typography variant="h4" color="info.main">
                    {stats.excusedAbsences}
                  </Typography>
                  <Typography variant="body2" color="text.secondary">
                    Justifiées
                  </Typography>
                </CardContent>
              </Card>
            </Grid>

            <Grid item xs={12} md={2.4}>
              <Card>
                <CardContent>
                  <Box sx={{ display: 'flex', alignItems: 'center', mb: 2 }}>
                    <Warning color="error" sx={{ mr: 1 }} />
                    <Typography variant="h6">Absences inj.</Typography>
                  </Box>
                  <Typography variant="h4" color="error.main">
                    {stats.unexcusedAbsences}
                  </Typography>
                  <Typography variant="body2" color="text.secondary">
                    Non justifiées
                  </Typography>
                </CardContent>
              </Card>
            </Grid>
          </Grid>

          <Card sx={{ mb: 3 }}>
            <CardContent>
              <Typography variant="h6" gutterBottom>
                Taux de présence
              </Typography>
              <Typography variant="h4" color="primary" gutterBottom>
                {stats.attendanceRate}%
              </Typography>
              <LinearProgress
                variant="determinate"
                value={stats.attendanceRate}
                sx={{ height: 10, borderRadius: 5 }}
                color={stats.attendanceRate >= 90 ? 'success' : stats.attendanceRate >= 80 ? 'warning' : 'error'}
              />
              <Typography variant="body2" color="text.secondary" sx={{ mt: 1 }}>
                {stats.attendanceRate >= 90 ? 'Excellent' : stats.attendanceRate >= 80 ? 'Bon' : 'À améliorer'}
              </Typography>
            </CardContent>
          </Card>

          <TableContainer component={Paper}>
            <Table>
              <TableHead>
                <TableRow>
                  <TableCell>Date</TableCell>
                  <TableCell>Matière</TableCell>
                  <TableCell>Professeur</TableCell>
                  <TableCell>Status</TableCell>
                  <TableCell>Retard (min)</TableCell>
                  <TableCell>Notes</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {attendance.map((record) => (
                  <TableRow key={record.id}>
                    <TableCell>
                      <Typography variant="body2" fontWeight="bold">
                        {record.date}
                      </Typography>
                    </TableCell>
                    <TableCell>{record.subject}</TableCell>
                    <TableCell>{record.teacherName}</TableCell>
                    <TableCell>
                      <Box sx={{ display: 'flex', alignItems: 'center' }}>
                        {getStatusIcon(record.status)}
                        <Chip
                          label={getStatusLabel(record.status)}
                          color={getStatusColor(record.status)}
                          size="small"
                          sx={{ ml: 1 }}
                        />
                      </Box>
                    </TableCell>
                    <TableCell>
                      {record.lateMinutes > 0 ? (
                        <Typography variant="body2" color="warning.main">
                          {record.lateMinutes} min
                        </Typography>
                      ) : (
                        '-'
                      )}
                    </TableCell>
                    <TableCell>
                      <Typography variant="body2" color="text.secondary">
                        {record.notes || '-'}
                      </Typography>
                    </TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </TableContainer>
        </>
      )}
    </Box>
  )
}

export default AttendancePage
