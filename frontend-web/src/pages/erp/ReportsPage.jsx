import React, { useState } from 'react'
import {
  Box,
  Card,
  CardContent,
  Typography,
  Grid,
  Button,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Chip
} from '@mui/material'
import {
  Download as DownloadIcon,
  Assessment as AnalyticsIcon,
  TrendingUp as TrendingUpIcon,
  People as PeopleIcon,
  School as SchoolIcon,
  AttachMoney as MoneyIcon
} from '@mui/icons-material'

const ReportsPage = () => {
  const [reportType, setReportType] = useState('')
  const [period, setPeriod] = useState('')

  const handleGenerateReport = () => {
    // Logic to generate report
    console.log('Generating report:', { reportType, period })
  }

  const statsData = [
    {
      title: 'Revenus Totaux',
      value: '450M FCFA',
      change: '+12%',
      icon: <MoneyIcon />,
      color: 'success'
    },
    {
      title: 'Nombre d\'Étudiants',
      value: '12,345',
      change: '+8%',
      icon: <PeopleIcon />,
      color: 'info'
    },
    {
      title: 'Établissements Actifs',
      value: '384',
      change: '+5%',
      icon: <SchoolIcon />,
      color: 'warning'
    },
    {
      title: 'Taux de Réussite',
      value: '78.5%',
      change: '+2.3%',
      icon: <TrendingUpIcon />,
      color: 'primary'
    }
  ]

  const recentReports = [
    {
      id: 1,
      name: 'Rapport Mensuel - Octobre 2024',
      type: 'Mensuel',
      generatedDate: '2024-11-01',
      status: 'Completed',
      downloadUrl: '#'
    },
    {
      id: 2,
      name: 'Analyse des Inscriptions - Semestre 1',
      type: 'Semestriel',
      generatedDate: '2024-10-28',
      status: 'Completed',
      downloadUrl: '#'
    },
    {
      id: 3,
      name: 'Performance Académique Annuelle',
      type: 'Annuel',
      generatedDate: '2024-10-15',
      status: 'Processing',
      downloadUrl: null
    }
  ]

  return (
    <Box sx={{ p: 3 }}>
      <Typography variant="h4" component="h1" gutterBottom>
        Rapports et Analytics
      </Typography>

      {/* Stats Cards */}
      <Grid container spacing={3} sx={{ mb: 4 }}>
        {statsData.map((stat, index) => (
          <Grid item xs={12} md={3} key={index}>
            <Card>
              <CardContent>
                <Box sx={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between' }}>
                  <Box>
                    <Typography color="textSecondary" gutterBottom>
                      {stat.title}
                    </Typography>
                    <Typography variant="h4" component="div">
                      {stat.value}
                    </Typography>
                    <Box sx={{ display: 'flex', alignItems: 'center', mt: 1 }}>
                      <Typography variant="body2" color={stat.color}>
                        {stat.change}
                      </Typography>
                    </Box>
                  </Box>
                  <Box sx={{ 
                    backgroundColor: `${stat.color}.main`, 
                    color: 'white', 
                    p: 1, 
                    borderRadius: 1,
                    display: 'flex',
                    alignItems: 'center',
                    justifyContent: 'center'
                  }}>
                    {stat.icon}
                  </Box>
                </Box>
              </CardContent>
            </Card>
          </Grid>
        ))}
      </Grid>

      <Grid container spacing={3}>
        {/* Generate Report Section */}
        <Grid item xs={12} md={6}>
          <Card>
            <CardContent>
              <Typography variant="h6" gutterBottom>
                Générer un Rapport
              </Typography>
              <Box sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}>
                <FormControl fullWidth>
                  <InputLabel>Type de Rapport</InputLabel>
                  <Select
                    value={reportType}
                    onChange={(e) => setReportType(e.target.value)}
                    label="Type de Rapport"
                  >
                    <MenuItem value="financial">Financier</MenuItem>
                    <MenuItem value="academic">Académique</MenuItem>
                    <MenuItem value="enrollment">Inscriptions</MenuItem>
                    <MenuItem value="performance">Performance</MenuItem>
                    <MenuItem value="attendance">Présence</MenuItem>
                    <MenuItem value="custom">Personnalisé</MenuItem>
                  </Select>
                </FormControl>
                <FormControl fullWidth>
                  <InputLabel>Période</InputLabel>
                  <Select
                    value={period}
                    onChange={(e) => setPeriod(e.target.value)}
                    label="Période"
                  >
                    <MenuItem value="daily">Quotidien</MenuItem>
                    <MenuItem value="weekly">Hebdomadaire</MenuItem>
                    <MenuItem value="monthly">Mensuel</MenuItem>
                    <MenuItem value="quarterly">Trimestriel</MenuItem>
                    <MenuItem value="semester">Semestriel</MenuItem>
                    <MenuItem value="yearly">Annuel</MenuItem>
                  </Select>
                </FormControl>
                <Button
                  variant="contained"
                  startIcon={<AnalyticsIcon />}
                  onClick={handleGenerateReport}
                  disabled={!reportType || !period}
                >
                  Générer le Rapport
                </Button>
              </Box>
            </CardContent>
          </Card>
        </Grid>

        {/* Quick Actions */}
        <Grid item xs={12} md={6}>
          <Card>
            <CardContent>
              <Typography variant="h6" gutterBottom>
                Actions Rapides
              </Typography>
              <Grid container spacing={2}>
                <Grid item xs={12} sm={6}>
                  <Button
                    variant="outlined"
                    fullWidth
                    startIcon={<DownloadIcon />}
                    sx={{ textTransform: 'none' }}
                  >
                    Exporter les Données
                  </Button>
                </Grid>
                <Grid item xs={12} sm={6}>
                  <Button
                    variant="outlined"
                    fullWidth
                    startIcon={<AnalyticsIcon />}
                    sx={{ textTransform: 'none' }}
                  >
                    Dashboard Analytics
                  </Button>
                </Grid>
                <Grid item xs={12} sm={6}>
                  <Button
                    variant="outlined"
                    fullWidth
                    startIcon={<PeopleIcon />}
                    sx={{ textTransform: 'none' }}
                  >
                    Rapport Étudiants
                  </Button>
                </Grid>
                <Grid item xs={12} sm={6}>
                  <Button
                    variant="outlined"
                    fullWidth
                    startIcon={<MoneyIcon />}
                    sx={{ textTransform: 'none' }}
                  >
                    Rapport Financier
                  </Button>
                </Grid>
              </Grid>
            </CardContent>
          </Card>
        </Grid>

        {/* Recent Reports */}
        <Grid item xs={12}>
          <Card>
            <CardContent>
              <Typography variant="h6" gutterBottom>
                Rapports Récents
              </Typography>
              <TableContainer component={Paper}>
                <Table>
                  <TableHead>
                    <TableRow>
                      <TableCell>Nom du Rapport</TableCell>
                      <TableCell>Type</TableCell>
                      <TableCell>Date de Génération</TableCell>
                      <TableCell>Statut</TableCell>
                      <TableCell>Actions</TableCell>
                    </TableRow>
                  </TableHead>
                  <TableBody>
                    {recentReports.map((report) => (
                      <TableRow key={report.id}>
                        <TableCell>{report.name}</TableCell>
                        <TableCell>
                          <Chip label={report.type} size="small" color="primary" />
                        </TableCell>
                        <TableCell>{report.generatedDate}</TableCell>
                        <TableCell>
                          <Chip
                            label={report.status === 'Completed' ? 'Terminé' : 'En cours'}
                            size="small"
                            color={report.status === 'Completed' ? 'success' : 'warning'}
                          />
                        </TableCell>
                        <TableCell>
                          <Button
                            variant="outlined"
                            size="small"
                            startIcon={<DownloadIcon />}
                            disabled={!report.downloadUrl}
                          >
                            Télécharger
                          </Button>
                        </TableCell>
                      </TableRow>
                    ))}
                  </TableBody>
                </Table>
              </TableContainer>
            </CardContent>
          </Card>
        </Grid>
      </Grid>
    </Box>
  )
}

export default ReportsPage
