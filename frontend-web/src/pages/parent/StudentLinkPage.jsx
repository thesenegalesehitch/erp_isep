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
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  TextField,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  LinearProgress,
  IconButton
} from '@mui/material'
import {
  PersonAdd,
  CheckCircle,
  Schedule,
  Search,
  QrCode,
  Email
} from '@mui/icons-material'
import { useNavigate } from 'react-router-dom'
import { parentAuthStore } from '../../stores/parentAuthStore'

const StudentLinkPage = () => {
  const navigate = useNavigate()
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')
  const [students, setStudents] = useState([])
  const [linkDialog, setLinkDialog] = useState(false)
  const [verificationDialog, setVerificationDialog] = useState(false)
  const [selectedStudent, setSelectedStudent] = useState(null)
  const [verificationCode, setVerificationCode] = useState('')
  const [searchEmail, setSearchEmail] = useState('')
  const [relationship, setRelationship] = useState('')

  useEffect(() => {
    if (!parentAuthStore.isAuthenticated) {
      navigate('/login')
      return
    }

    loadLinkedStudents()
  }, [])

  const loadLinkedStudents = async () => {
    try {
      setLoading(true)
      // Simuler l'appel API
      const mockStudents = [
        {
          id: 1,
          name: 'Marie Diop',
          email: 'marie.diop@ecole.com',
          studentNumber: 'STU2024001',
          grade: '3ème A',
          school: 'École Excellence',
          relationship: 'Mère',
          isVerified: true,
          linkedAt: '2024-01-15'
        },
        {
          id: 2,
          name: 'Pierre Ba',
          email: 'pierre.ba@lycee.com',
          studentNumber: 'STU2024002',
          grade: '2nde B',
          school: 'Lycée Moderne',
          relationship: 'Père',
          isVerified: false,
          verificationCode: '123456',
          linkedAt: '2024-01-20'
        }
      ]
      setStudents(mockStudents)
    } catch (err) {
      setError(err.message || 'Erreur lors du chargement des élèves')
    } finally {
      setLoading(false)
    }
  }

  const handleLinkStudent = () => {
    if (!searchEmail || !relationship) {
      setError('Veuillez remplir tous les champs')
      return
    }

    // Simuler la recherche de l'élève
    const foundStudent = {
      id: 3,
      name: 'Aminata Fall',
      email: searchEmail,
      studentNumber: 'STU2024003',
      grade: '1ère C',
      school: 'Lycée Scientifique',
      relationship: relationship,
      isVerified: false,
      verificationCode: '789012',
      linkedAt: new Date().toISOString().split('T')[0]
    }

    setStudents([...students, foundStudent])
    setLinkDialog(false)
    setSearchEmail('')
    setRelationship('')
    setSelectedStudent(foundStudent)
    setVerificationDialog(true)
  }

  const handleVerifyLink = () => {
    if (!verificationCode) {
      setError('Veuillez entrer le code de vérification')
      return
    }

    const updatedStudents = students.map(s => 
      s.id === selectedStudent.id 
        ? { ...s, isVerified: true, verificationCode: null }
        : s
    )
    setStudents(updatedStudents)
    setVerificationDialog(false)
    setSelectedStudent(null)
    setVerificationCode('')
  }

  const getStatusColor = (isVerified) => {
    return isVerified ? 'success' : 'warning'
  }

  const getStatusIcon = (isVerified) => {
    return isVerified ? <CheckCircle color="success" /> : <Schedule color="warning" />
  }

  const getStatusLabel = (isVerified) => {
    return isVerified ? 'Vérifié' : 'En attente'
  }

  const getRelationshipColor = (relationship) => {
    switch (relationship) {
      case 'Père': return 'primary'
      case 'Mère': return 'secondary'
      case 'Tuteur': return 'info'
      default: return 'default'
    }
  }

  if (loading) {
    return (
      <Box sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', minHeight: '60vh' }}>
        <CircularProgress />
      </Box>
    )
  }

  return (
    <Box sx={{ p: 3 }}>
      <Typography variant="h4" gutterBottom>
        Gestion des Élèves
      </Typography>

      <Grid container spacing={3} sx={{ mb: 3 }}>
        <Grid item xs={12} md={4}>
          <Card>
            <CardContent>
              <Box sx={{ display: 'flex', alignItems: 'center', mb: 2 }}>
                <PersonAdd color="primary" sx={{ mr: 1 }} />
                <Typography variant="h6">Élèves connectés</Typography>
              </Box>
              <Typography variant="h4" color="primary">
                {students.length}
              </Typography>
              <Typography variant="body2" color="text.secondary">
                Total des élèves
              </Typography>
            </CardContent>
          </Card>
        </Grid>

        <Grid item xs={12} md={4}>
          <Card>
            <CardContent>
              <Box sx={{ display: 'flex', alignItems: 'center', mb: 2 }}>
                <CheckCircle color="success" sx={{ mr: 1 }} />
                <Typography variant="h6">Vérifiés</Typography>
              </Box>
              <Typography variant="h4" color="success.main">
                {students.filter(s => s.isVerified).length}
              </Typography>
              <Typography variant="body2" color="text.secondary">
                Liens actifs
              </Typography>
            </CardContent>
          </Card>
        </Grid>

        <Grid item xs={12} md={4}>
          <Card>
            <CardContent>
              <Box sx={{ display: 'flex', alignItems: 'center', mb: 2 }}>
                <Schedule color="warning" sx={{ mr: 1 }} />
                <Typography variant="h6">En attente</Typography>
              </Box>
              <Typography variant="h4" color="warning.main">
                {students.filter(s => !s.isVerified).length}
              </Typography>
              <Typography variant="body2" color="text.secondary">
                En attente de vérification
              </Typography>
            </CardContent>
          </Card>
        </Grid>
      </Grid>

      <Box sx={{ mb: 3, display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
        <Typography variant="h6">
          Liste des élèves connectés
        </Typography>
        <Button
          variant="contained"
          startIcon={<PersonAdd />}
          onClick={() => setLinkDialog(true)}
        >
          Ajouter un élève
        </Button>
      </Box>

      {error && (
        <Alert severity="error" sx={{ mb: 3 }}>
          {error}
        </Alert>
      )}

      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>Nom</TableCell>
              <TableCell>Email</TableCell>
              <TableCell>Numéro étudiant</TableCell>
              <TableCell>Classe</TableCell>
              <TableCell>École</TableCell>
              <TableCell>Relation</TableCell>
              <TableCell>Status</TableCell>
              <TableCell>Date de liaison</TableCell>
              <TableCell>Actions</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {students.map((student) => (
              <TableRow key={student.id}>
                <TableCell>
                  <Typography variant="body2" fontWeight="bold">
                    {student.name}
                  </Typography>
                </TableCell>
                <TableCell>{student.email}</TableCell>
                <TableCell>
                  <Typography variant="body2" sx={{ fontFamily: 'monospace' }}>
                    {student.studentNumber}
                  </Typography>
                </TableCell>
                <TableCell>{student.grade}</TableCell>
                <TableCell>{student.school}</TableCell>
                <TableCell>
                  <Chip
                    label={student.relationship}
                    color={getRelationshipColor(student.relationship)}
                    size="small"
                  />
                </TableCell>
                <TableCell>
                  <Box sx={{ display: 'flex', alignItems: 'center' }}>
                    {getStatusIcon(student.isVerified)}
                    <Chip
                      label={getStatusLabel(student.isVerified)}
                      color={getStatusColor(student.isVerified)}
                      size="small"
                      sx={{ ml: 1 }}
                    />
                  </Box>
                </TableCell>
                <TableCell>
                  <Typography variant="body2">
                    {student.linkedAt}
                  </Typography>
                </TableCell>
                <TableCell>
                  {!student.isVerified && (
                    <Button
                      variant="outlined"
                      size="small"
                      startIcon={<QrCode />}
                      onClick={() => {
                        setSelectedStudent(student)
                        setVerificationDialog(true)
                      }}
                    >
                      Vérifier
                    </Button>
                  )}
                  {student.isVerified && (
                    <Button
                      variant="outlined"
                      size="small"
                      startIcon={<Email />}
                      onClick={() => navigate('/parent/grades')}
                    >
                      Voir les notes
                    </Button>
                  )}
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>

      <Dialog open={linkDialog} onClose={() => setLinkDialog(false)} maxWidth="sm" fullWidth>
        <DialogTitle>
          <Box sx={{ display: 'flex', alignItems: 'center' }}>
            <PersonAdd sx={{ mr: 1 }} />
            Ajouter un élève
          </Box>
        </DialogTitle>
        <DialogContent>
          <TextField
            fullWidth
            label="Email de l'élève"
            value={searchEmail}
            onChange={(e) => setSearchEmail(e.target.value)}
            placeholder="exemple@ecole.com"
            sx={{ mb: 2 }}
          />

          <FormControl fullWidth>
            <InputLabel>Relation avec l'élève</InputLabel>
            <Select
              value={relationship}
              onChange={(e) => setRelationship(e.target.value)}
            >
              <MenuItem value="Père">Père</MenuItem>
              <MenuItem value="Mère">Mère</MenuItem>
              <MenuItem value="Tuteur">Tuteur</MenuItem>
              <MenuItem value="Grand-père">Grand-père</MenuItem>
              <MenuItem value="Grand-mère">Grand-mère</MenuItem>
              <MenuItem value="Oncle">Oncle</MenuItem>
              <MenuItem value="Tante">Tante</MenuItem>
            </Select>
          </FormControl>

          <Alert severity="info" sx={{ mt: 2 }}>
            L'élève recevra un email avec un code de vérification à 6 chiffres pour confirmer la liaison.
          </Alert>
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setLinkDialog(false)}>
            Annuler
          </Button>
          <Button
            variant="contained"
            onClick={handleLinkStudent}
            disabled={!searchEmail || !relationship}
          >
            Ajouter
          </Button>
        </DialogActions>
      </Dialog>

      <Dialog open={verificationDialog} onClose={() => setVerificationDialog(false)} maxWidth="sm" fullWidth>
        <DialogTitle>
          <Box sx={{ display: 'flex', alignItems: 'center' }}>
            <QrCode sx={{ mr: 1 }} />
            Vérifier la liaison
          </Box>
        </DialogTitle>
        <DialogContent>
          {selectedStudent && (
            <Box sx={{ mb: 3 }}>
              <Typography variant="h6" gutterBottom>
                {selectedStudent.name}
              </Typography>
              <Typography variant="body2" color="text.secondary" gutterBottom>
                {selectedStudent.email}
              </Typography>
              <Typography variant="body2" color="text.secondary" gutterBottom>
                {selectedStudent.grade} - {selectedStudent.school}
              </Typography>
            </Box>
          )}

          <TextField
            fullWidth
            label="Code de vérification"
            value={verificationCode}
            onChange={(e) => setVerificationCode(e.target.value)}
            placeholder="Entrez le code à 6 chiffres"
            inputProps={{ maxLength: 6 }}
            sx={{ mb: 2 }}
          />

          <Alert severity="info">
            Un code de vérification a été envoyé à l'email de l'élève. 
            Entrez ce code pour confirmer la liaison.
          </Alert>
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setVerificationDialog(false)}>
            Annuler
          </Button>
          <Button
            variant="contained"
            onClick={handleVerifyLink}
            disabled={!verificationCode || verificationCode.length !== 6}
          >
            Vérifier
          </Button>
        </DialogActions>
      </Dialog>
    </Box>
  )
}

export default StudentLinkPage
