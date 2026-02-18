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
  LinearProgress
} from '@mui/material'
import {
  Grade,
  TrendingUp,
  TrendingDown,
  School
} from '@mui/icons-material'
import { useNavigate } from 'react-router-dom'
import { parentAuthStore } from '../../stores/parentAuthStore'

const StudentGradesPage = () => {
  const navigate = useNavigate()
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')
  const [grades, setGrades] = useState([])
  const [selectedStudent, setSelectedStudent] = useState('')
  const [selectedSemester, setSelectedSemester] = useState('')
  const [selectedYear, setSelectedYear] = useState('2023-2024')

  const students = [
    { id: 1, name: 'Marie Diop' },
    { id: 2, name: 'Pierre Ba' }
  ]

  const semesters = [
    { value: '1', label: 'Semestre 1' },
    { value: '2', label: 'Semestre 2' }
  ]

  const years = [
    { value: '2023-2024', label: '2023-2024' },
    { value: '2022-2023', label: '2022-2023' }
  ]

  useEffect(() => {
    if (!parentAuthStore.isAuthenticated) {
      navigate('/login')
      return
    }

    if (selectedStudent && selectedSemester) {
      loadGrades()
    }
  }, [selectedStudent, selectedSemester, selectedYear])

  const loadGrades = async () => {
    try {
      setLoading(true)
      // Simuler l'appel API
      const mockGrades = [
        {
          id: 1,
          subject: 'Mathématiques',
          examType: 'DS',
          grade: 15,
          maxGrade: 20,
          coefficient: 3,
          percentage: 75,
          isPassing: true,
          date: '2024-01-15',
          comments: 'Bon travail, progression notable'
        },
        {
          id: 2,
          subject: 'Physique',
          examType: 'Examen',
          grade: 12,
          maxGrade: 20,
          coefficient: 2,
          percentage: 60,
          isPassing: true,
          date: '2024-01-20',
          comments: 'Peut mieux faire en révision'
        },
        {
          id: 3,
          subject: 'Français',
          examType: 'DS',
          grade: 16,
          maxGrade: 20,
          coefficient: 2,
          percentage: 80,
          isPassing: true,
          date: '2024-01-18',
          comments: 'Excellente expression'
        },
        {
          id: 4,
          subject: 'Histoire',
          examType: 'Composition',
          grade: 14,
          maxGrade: 20,
          coefficient: 1,
          percentage: 70,
          isPassing: true,
          date: '2024-01-22',
          comments: 'Bonne analyse des documents'
        }
      ]
      setGrades(mockGrades)
    } catch (err) {
      setError(err.message || 'Erreur lors du chargement des notes')
    } finally {
      setLoading(false)
    }
  }

  const calculateAverage = () => {
    if (grades.length === 0) return 0
    
    const totalWeighted = grades.reduce((sum, grade) => 
      sum + (grade.grade * grade.coefficient), 0)
    const totalCoefficients = grades.reduce((sum, grade) => 
      sum + grade.coefficient, 0)
    
    return (totalWeighted / totalCoefficients).toFixed(2)
  }

  const getGradeColor = (percentage) => {
    if (percentage >= 80) return 'success'
    if (percentage >= 60) return 'warning'
    return 'error'
  }

  const getGradeIcon = (percentage) => {
    if (percentage >= 80) return <TrendingUp color="success" />
    if (percentage >= 60) return <Grade color="warning" />
    return <TrendingDown color="error" />
  }

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
        Notes des Élèves
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
          <FormControl fullWidth>
            <InputLabel>Semestre</InputLabel>
            <Select
              value={selectedSemester}
              onChange={(e) => setSelectedSemester(e.target.value)}
            >
              {semesters.map((semester) => (
                <MenuItem key={semester.value} value={semester.value}>
                  {semester.label}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </Grid>

        <Grid item xs={12} md={3}>
          <Button
            fullWidth
            variant="contained"
            onClick={loadGrades}
            disabled={!selectedStudent || !selectedSemester || loading}
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

      {!selectedStudent || !selectedSemester ? (
        <Paper sx={{ p: 4, textAlign: 'center' }}>
          <School sx={{ fontSize: 64, color: 'text.secondary', mb: 2 }} />
          <Typography variant="h6" color="text.secondary">
            Sélectionnez un élève et un semestre pour voir les notes
          </Typography>
        </Paper>
      ) : (
        <>
          <Grid container spacing={3} sx={{ mb: 3 }}>
            <Grid item xs={12} md={4}>
              <Card>
                <CardContent>
                  <Typography variant="h6" gutterBottom>
                    Moyenne Générale
                  </Typography>
                  <Typography variant="h4" color="primary">
                    {calculateAverage()}/20
                  </Typography>
                  <LinearProgress 
                    variant="determinate" 
                    value={(calculateAverage() / 20) * 100} 
                    sx={{ mt: 1 }}
                  />
                </CardContent>
              </Card>
            </Grid>

            <Grid item xs={12} md={4}>
              <Card>
                <CardContent>
                  <Typography variant="h6" gutterBottom>
                    Notes au-dessus de la moyenne
                  </Typography>
                  <Typography variant="h4" color="success.main">
                    {grades.filter(g => g.isPassing).length}/{grades.length}
                  </Typography>
                  <Typography variant="body2" color="text.secondary">
                    Réussite
                  </Typography>
                </CardContent>
              </Card>
            </Grid>

            <Grid item xs={12} md={4}>
              <Card>
                <CardContent>
                  <Typography variant="h6" gutterBottom>
                    Meilleure note
                  </Typography>
                  <Typography variant="h4" color="success.main">
                    {grades.length > 0 ? Math.max(...grades.map(g => g.grade)) : 0}/20
                  </Typography>
                  <Typography variant="body2" color="text.secondary">
                    {grades.find(g => g.grade === Math.max(...grades.map(g => g.grade)))?.subject || '-'}
                  </Typography>
                </CardContent>
              </Card>
            </Grid>
          </Grid>

          <TableContainer component={Paper}>
            <Table>
              <TableHead>
                <TableRow>
                  <TableCell>Matière</TableCell>
                  <TableCell>Type</TableCell>
                  <TableCell>Note</TableCell>
                  <TableCell>Max</TableCell>
                  <TableCell>Coefficient</TableCell>
                  <TableCell>Pourcentage</TableCell>
                  <TableCell>Date</TableCell>
                  <TableCell>Commentaire</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {grades.map((grade) => (
                  <TableRow key={grade.id}>
                    <TableCell>
                      <Box sx={{ display: 'flex', alignItems: 'center' }}>
                        {getGradeIcon(grade.percentage)}
                        <Typography sx={{ ml: 1 }}>{grade.subject}</Typography>
                      </Box>
                    </TableCell>
                    <TableCell>
                      <Chip label={grade.examType} size="small" />
                    </TableCell>
                    <TableCell>
                      <Typography variant="h6" color={getGradeColor(grade.percentage) + '.main'}>
                        {grade.grade}
                      </Typography>
                    </TableCell>
                    <TableCell>{grade.maxGrade}</TableCell>
                    <TableCell>{grade.coefficient}</TableCell>
                    <TableCell>
                      <Box sx={{ display: 'flex', alignItems: 'center' }}>
                        <Typography variant="body2">
                          {grade.percentage}%
                        </Typography>
                        <LinearProgress 
                          variant="determinate" 
                          value={grade.percentage} 
                          sx={{ ml: 1, width: 60 }}
                          color={getGradeColor(grade.percentage)}
                        />
                      </Box>
                    </TableCell>
                    <TableCell>{grade.date}</TableCell>
                    <TableCell>
                      <Typography variant="body2" color="text.secondary">
                        {grade.comments || '-'}
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

export default StudentGradesPage
