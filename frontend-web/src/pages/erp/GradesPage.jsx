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
  LinearProgress
} from '@mui/material'
import {
  Add as AddIcon,
  Search as SearchIcon,
  Edit as EditIcon,
  Visibility as ViewIcon,
  Assessment as AnalyticsIcon
} from '@mui/icons-material'

const GradesPage = () => {
  const [grades, setGrades] = useState([])
  const [loading, setLoading] = useState(true)
  const [searchTerm, setSearchTerm] = useState('')
  const [page, setPage] = useState(1)
  const [totalPages, setTotalPages] = useState(1)
  const [filterCourse, setFilterCourse] = useState('')
  const [filterGradeType, setFilterGradeType] = useState('')

  useEffect(() => {
    fetchGrades()
  }, [page, searchTerm, filterCourse, filterGradeType])

  const fetchGrades = async () => {
    setLoading(true)
    try {
      const mockGrades = [
        {
          id: 1,
          student: { firstName: 'Aliou', lastName: 'Diop', studentId: '2024001' },
          course: { courseCode: 'INF301', title: 'Algorithmique Avancée' },
          evaluationTitle: 'Examen Final',
          gradeType: 'EXAM',
          score: 15.5,
          maxScore: 20,
          weight: 40,
          gradedDate: '2024-11-15',
          isPublished: true,
          semester: 'Semestre 1'
        },
        {
          id: 2,
          student: { firstName: 'Fatou', lastName: 'Ba', studentId: '2024002' },
          course: { courseCode: 'GES201', title: 'Marketing Digital' },
          evaluationTitle: 'Projet',
          gradeType: 'PROJECT',
          score: 17.0,
          maxScore: 20,
          weight: 30,
          gradedDate: '2024-11-10',
          isPublished: true,
          semester: 'Semestre 1'
        }
      ]
      setGrades(mockGrades)
      setTotalPages(6)
    } catch (error) {
      console.error('Error fetching grades:', error)
    } finally {
      setLoading(false)
    }
  }

  const getGradeColor = (percentage) => {
    if (percentage >= 80) return 'success'
    if (percentage >= 60) return 'warning'
    return 'error'
  }

  const getGradeTypeColor = (type) => {
    switch (type) {
      case 'EXAM': return 'error'
      case 'QUIZ': return 'info'
      case 'PROJECT': return 'success'
      case 'ASSIGNMENT': return 'warning'
      default: return 'default'
    }
  }

  const getGradeTypeText = (type) => {
    switch (type) {
      case 'EXAM': return 'Examen'
      case 'QUIZ': return 'Quiz'
      case 'PROJECT': return 'Projet'
      case 'ASSIGNMENT': return 'Devoir'
      default: return type
    }
  }

  return (
    <Box sx={{ p: 3 }}>
      <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 3 }}>
        <Typography variant="h4" component="h1">
          Gestion des Notes
        </Typography>
        <Box sx={{ display: 'flex', gap: 2 }}>
          <Button variant="outlined" startIcon={<AnalyticsIcon />}>
            Analytics
          </Button>
          <Button variant="contained" startIcon={<AddIcon />}>
            Nouvelle Note
          </Button>
        </Box>
      </Box>

      <Grid container spacing={3} sx={{ mb: 3 }}>
        <Grid item xs={12} md={3}>
          <Card>
            <CardContent>
              <Typography color="textSecondary" gutterBottom>
                Total Notes
              </Typography>
              <Typography variant="h4">
                3,456
              </Typography>
            </CardContent>
          </Card>
        </Grid>
        <Grid item xs={12} md={3}>
          <Card>
            <CardContent>
              <Typography color="textSecondary" gutterBottom>
                Moyenne Générale
              </Typography>
              <Typography variant="h4" color="success.main">
                14.2
              </Typography>
            </CardContent>
          </Card>
        </Grid>
        <Grid item xs={12} md={3}>
          <Card>
            <CardContent>
              <Typography color="textSecondary" gutterBottom>
                Taux Réussite
              </Typography>
              <Typography variant="h4" color="info.main">
                78%
              </Typography>
            </CardContent>
          </Card>
        </Grid>
        <Grid item xs={12} md={3}>
          <Card>
            <CardContent>
              <Typography color="textSecondary" gutterBottom>
                Notes Publiées
              </Typography>
              <Typography variant="h4" color="warning.main">
                2,890
              </Typography>
            </CardContent>
          </Card>
        </Grid>
      </Grid>

      <Card>
        <CardContent>
          <Box sx={{ display: 'flex', gap: 2, mb: 3, flexWrap: 'wrap' }}>
            <TextField
              placeholder="Rechercher une note..."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              InputProps={{
                startAdornment: <SearchIcon />
              }}
              sx={{ flexGrow: 1, minWidth: 200 }}
            />
            <FormControl sx={{ minWidth: 150 }}>
              <InputLabel>Cours</InputLabel>
              <Select
                value={filterCourse}
                onChange={(e) => setFilterCourse(e.target.value)}
                label="Cours"
              >
                <MenuItem value="">Tous</MenuItem>
                <MenuItem value="INF301">INF301</MenuItem>
                <MenuItem value="GES201">GES201</MenuItem>
              </Select>
            </FormControl>
            <FormControl sx={{ minWidth: 150 }}>
              <InputLabel>Type</InputLabel>
              <Select
                value={filterGradeType}
                onChange={(e) => setFilterGradeType(e.target.value)}
                label="Type"
              >
                <MenuItem value="">Tous</MenuItem>
                <MenuItem value="EXAM">Examen</MenuItem>
                <MenuItem value="QUIZ">Quiz</MenuItem>
                <MenuItem value="PROJECT">Projet</MenuItem>
                <MenuItem value="ASSIGNMENT">Devoir</MenuItem>
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
                  <TableCell>Étudiant</TableCell>
                  <TableCell>Cours</TableCell>
                  <TableCell>Évaluation</TableCell>
                  <TableCell>Type</TableCell>
                  <TableCell>Note</TableCell>
                  <TableCell>Max</TableCell>
                  <TableCell>Poids</TableCell>
                  <TableCell>Pourcentage</TableCell>
                  <TableCell>Date</TableCell>
                  <TableCell>Statut</TableCell>
                  <TableCell>Actions</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {grades.map((grade) => {
                  const percentage = (grade.score / grade.maxScore) * 100
                  return (
                    <TableRow key={grade.id}>
                      <TableCell>
                        <Box>
                          <Typography variant="body2">
                            {grade.student.firstName} {grade.student.lastName}
                          </Typography>
                          <Typography variant="caption" color="textSecondary">
                            {grade.student.studentId}
                          </Typography>
                        </Box>
                      </TableCell>
                      <TableCell>
                        <Box>
                          <Typography variant="body2">
                            {grade.course.title}
                          </Typography>
                          <Typography variant="caption" color="textSecondary">
                            {grade.course.courseCode}
                          </Typography>
                        </Box>
                      </TableCell>
                      <TableCell>{grade.evaluationTitle}</TableCell>
                      <TableCell>
                        <Chip
                          label={getGradeTypeText(grade.gradeType)}
                          color={getGradeTypeColor(grade.gradeType)}
                          size="small"
                        />
                      </TableCell>
                      <TableCell>
                        <Typography variant="h6" color={getGradeColor(percentage)}>
                          {grade.score}
                        </Typography>
                      </TableCell>
                      <TableCell>{grade.maxScore}</TableCell>
                      <TableCell>{grade.weight}%</TableCell>
                      <TableCell>
                        <Box sx={{ display: 'flex', alignItems: 'center', gap: 1 }}>
                          <Typography variant="body2">
                            {percentage.toFixed(1)}%
                          </Typography>
                          <LinearProgress
                            variant="determinate"
                            value={percentage}
                            sx={{ width: 50, height: 6 }}
                            color={getGradeColor(percentage)}
                          />
                        </Box>
                      </TableCell>
                      <TableCell>{grade.gradedDate}</TableCell>
                      <TableCell>
                        <Chip
                          label={grade.isPublished ? 'Publié' : 'Brouillon'}
                          color={grade.isPublished ? 'success' : 'warning'}
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

export default GradesPage
