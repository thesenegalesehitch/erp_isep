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
  Badge
} from '@mui/material'
import {
  AttachMoney,
  CreditCard,
  Warning,
  CheckCircle,
  Schedule,
  Receipt
} from '@mui/icons-material'
import { useNavigate } from 'react-router-dom'
import { parentAuthStore } from '../../stores/parentAuthStore'

const PaymentsPage = () => {
  const navigate = useNavigate()
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')
  const [payments, setPayments] = useState([])
  const [paymentDialog, setPaymentDialog] = useState(false)
  const [selectedPayment, setSelectedPayment] = useState(null)
  const [paymentMethod, setPaymentMethod] = useState('')

  useEffect(() => {
    if (!parentAuthStore.isAuthenticated) {
      navigate('/login')
      return
    }

    loadPayments()
  }, [])

  const loadPayments = async () => {
    try {
      setLoading(true)
      // Simuler l'appel API
      const mockPayments = [
        {
          id: 1,
          studentName: 'Marie Diop',
          schoolName: 'École Excellence',
          amount: 50000,
          currency: 'XOF',
          paymentType: 'TUITION_FEES',
          description: 'Frais de scolarité - Trimestre 2',
          status: 'PENDING',
          dueDate: '2024-02-15',
          lateFee: 2500,
          createdAt: '2024-01-15'
        },
        {
          id: 2,
          studentName: 'Marie Diop',
          schoolName: 'École Excellence',
          amount: 15000,
          currency: 'XOF',
          paymentType: 'ACTIVITY_FEES',
          description: 'Frais d\'activité - Club de sport',
          status: 'COMPLETED',
          paymentDate: '2024-01-20',
          dueDate: '2024-01-30',
          createdAt: '2024-01-10'
        },
        {
          id: 3,
          studentName: 'Pierre Ba',
          schoolName: 'Lycée Moderne',
          amount: 75000,
          currency: 'XOF',
          paymentType: 'TUITION_FEES',
          description: 'Frais de scolarité - Trimestre 2',
          status: 'OVERDUE',
          dueDate: '2024-01-31',
          lateFee: 5000,
          createdAt: '2024-01-05'
        }
      ]
      setPayments(mockPayments)
    } catch (err) {
      setError(err.message || 'Erreur lors du chargement des paiements')
    } finally {
      setLoading(false)
    }
  }

  const handlePayment = (payment) => {
    setSelectedPayment(payment)
    setPaymentDialog(true)
  }

  const processPayment = async () => {
    if (!paymentMethod) {
      setError('Veuillez sélectionner un moyen de paiement')
      return
    }

    try {
      // Simuler le traitement du paiement
      const updatedPayments = payments.map(p => 
        p.id === selectedPayment.id 
          ? { ...p, status: 'COMPLETED', paymentDate: new Date().toISOString().split('T')[0] }
          : p
      )
      setPayments(updatedPayments)
      setPaymentDialog(false)
      setSelectedPayment(null)
      setPaymentMethod('')
    } catch (err) {
      setError(err.message || 'Erreur lors du traitement du paiement')
    }
  }

  const getStatusColor = (status) => {
    switch (status) {
      case 'COMPLETED': return 'success'
      case 'PENDING': return 'warning'
      case 'OVERDUE': return 'error'
      default: return 'default'
    }
  }

  const getStatusIcon = (status) => {
    switch (status) {
      case 'COMPLETED': return <CheckCircle color="success" />
      case 'PENDING': return <Schedule color="warning" />
      case 'OVERDUE': return <Warning color="error" />
      default: return <Receipt />
    }
  }

  const getStatusLabel = (status) => {
    switch (status) {
      case 'COMPLETED': return 'Payé'
      case 'PENDING': return 'En attente'
      case 'OVERDUE': return 'En retard'
      default: return status
    }
  }

  const getPaymentTypeLabel = (type) => {
    switch (type) {
      case 'TUITION_FEES': return 'Frais de scolarité'
      case 'ACTIVITY_FEES': return 'Frais d\'activité'
      case 'REGISTRATION_FEES': return 'Frais d\'inscription'
      case 'EXAM_FEES': return 'Frais d\'examen'
      default: return type
    }
  }

  const calculateTotalPending = () => {
    return payments
      .filter(p => p.status === 'PENDING' || p.status === 'OVERDUE')
      .reduce((sum, p) => sum + p.amount + (p.lateFee || 0), 0)
  }

  const calculateTotalPaid = () => {
    return payments
      .filter(p => p.status === 'COMPLETED')
      .reduce((sum, p) => sum + p.amount, 0)
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
        Paiements
      </Typography>

      <Grid container spacing={3} sx={{ mb: 3 }}>
        <Grid item xs={12} md={4}>
          <Card>
            <CardContent>
              <Box sx={{ display: 'flex', alignItems: 'center', mb: 2 }}>
                <Schedule color="warning" sx={{ mr: 1 }} />
                <Typography variant="h6">En attente</Typography>
              </Box>
              <Typography variant="h4" color="warning.main">
                {calculateTotalPending().toLocaleString()} XOF
              </Typography>
              <Typography variant="body2" color="text.secondary">
                {payments.filter(p => p.status === 'PENDING' || p.status === 'OVERDUE').length} paiements
              </Typography>
            </CardContent>
          </Card>
        </Grid>

        <Grid item xs={12} md={4}>
          <Card>
            <CardContent>
              <Box sx={{ display: 'flex', alignItems: 'center', mb: 2 }}>
                <CheckCircle color="success" sx={{ mr: 1 }} />
                <Typography variant="h6">Payés</Typography>
              </Box>
              <Typography variant="h4" color="success.main">
                {calculateTotalPaid().toLocaleString()} XOF
              </Typography>
              <Typography variant="body2" color="text.secondary">
                {payments.filter(p => p.status === 'COMPLETED').length} paiements
              </Typography>
            </CardContent>
          </Card>
        </Grid>

        <Grid item xs={12} md={4}>
          <Card>
            <CardContent>
              <Box sx={{ display: 'flex', alignItems: 'center', mb: 2 }}>
                <Warning color="error" sx={{ mr: 1 }} />
                <Typography variant="h6">En retard</Typography>
              </Box>
              <Typography variant="h4" color="error.main">
                {payments.filter(p => p.status === 'OVERDUE').length}
              </Typography>
              <Typography variant="body2" color="text.secondary">
                Paiements en retard
              </Typography>
            </CardContent>
          </Card>
        </Grid>
      </Grid>

      {error && (
        <Alert severity="error" sx={{ mb: 3 }}>
          {error}
        </Alert>
      )}

      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>Élève</TableCell>
              <TableCell>École</TableCell>
              <TableCell>Type</TableCell>
              <TableCell>Description</TableCell>
              <TableCell>Montant</TableCell>
              <TableCell>Frais de retard</TableCell>
              <TableCell>Total</TableCell>
              <TableCell>Status</TableCell>
              <TableCell>Échéance</TableCell>
              <TableCell>Actions</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {payments.map((payment) => (
              <TableRow key={payment.id}>
                <TableCell>
                  <Typography variant="body2" fontWeight="bold">
                    {payment.studentName}
                  </Typography>
                </TableCell>
                <TableCell>{payment.schoolName}</TableCell>
                <TableCell>
                  <Chip label={getPaymentTypeLabel(payment.paymentType)} size="small" />
                </TableCell>
                <TableCell>
                  <Typography variant="body2">
                    {payment.description}
                  </Typography>
                </TableCell>
                <TableCell>
                  <Typography variant="body2" fontWeight="bold">
                    {payment.amount.toLocaleString()} XOF
                  </Typography>
                </TableCell>
                <TableCell>
                  {payment.lateFee ? (
                    <Typography variant="body2" color="error">
                      {payment.lateFee.toLocaleString()} XOF
                    </Typography>
                  ) : (
                    '-'
                  )}
                </TableCell>
                <TableCell>
                  <Typography variant="body2" fontWeight="bold">
                    {(payment.amount + (payment.lateFee || 0)).toLocaleString()} XOF
                  </Typography>
                </TableCell>
                <TableCell>
                  <Box sx={{ display: 'flex', alignItems: 'center' }}>
                    {getStatusIcon(payment.status)}
                    <Chip
                      label={getStatusLabel(payment.status)}
                      color={getStatusColor(payment.status)}
                      size="small"
                      sx={{ ml: 1 }}
                    />
                  </Box>
                </TableCell>
                <TableCell>
                  <Typography variant="body2">
                    {payment.dueDate}
                  </Typography>
                  {payment.status === 'OVERDUE' && (
                    <Badge badgeContent="En retard" color="error" sx={{ mt: 1 }}>
                      <Box />
                    </Badge>
                  )}
                </TableCell>
                <TableCell>
                  {payment.status === 'PENDING' || payment.status === 'OVERDUE' ? (
                    <Button
                      variant="contained"
                      startIcon={<CreditCard />}
                      onClick={() => handlePayment(payment)}
                      size="small"
                    >
                      Payer
                    </Button>
                  ) : (
                    <Button
                      variant="outlined"
                      startIcon={<Receipt />}
                      size="small"
                      disabled
                    >
                      Reçu
                    </Button>
                  )}
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>

      <Dialog open={paymentDialog} onClose={() => setPaymentDialog(false)} maxWidth="sm" fullWidth>
        <DialogTitle>
          <Box sx={{ display: 'flex', alignItems: 'center' }}>
            <AttachMoney sx={{ mr: 1 }} />
            Procéder au paiement
          </Box>
        </DialogTitle>
        <DialogContent>
          {selectedPayment && (
            <Box sx={{ mb: 3 }}>
              <Typography variant="h6" gutterBottom>
                {selectedPayment.description}
              </Typography>
              <Typography variant="body2" color="text.secondary" gutterBottom>
                Élève: {selectedPayment.studentName}
              </Typography>
              <Typography variant="body2" color="text.secondary" gutterBottom>
                École: {selectedPayment.schoolName}
              </Typography>
              <Typography variant="h6" color="primary" gutterBottom>
                Total: {(selectedPayment.amount + (selectedPayment.lateFee || 0)).toLocaleString()} XOF
              </Typography>
            </Box>
          )}

          <FormControl fullWidth sx={{ mb: 2 }}>
            <InputLabel>Moyen de paiement</InputLabel>
            <Select
              value={paymentMethod}
              onChange={(e) => setPaymentMethod(e.target.value)}
            >
              <MenuItem value="orange_money">Orange Money</MenuItem>
              <MenuItem value="wave">Wave</MenuItem>
              <MenuItem value="mtn_money">MTN Mobile Money</MenuItem>
              <MenuItem value="credit_card">Carte bancaire</MenuItem>
            </Select>
          </FormControl>

          <TextField
            fullWidth
            label="Numéro de téléphone"
            placeholder="+221 77 123 45 67"
            sx={{ mb: 2 }}
          />

          <TextField
            fullWidth
            label="Code PIN (optionnel)"
            type="password"
            placeholder="****"
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setPaymentDialog(false)}>
            Annuler
          </Button>
          <Button
            variant="contained"
            onClick={processPayment}
            disabled={!paymentMethod}
          >
            Confirmer le paiement
          </Button>
        </DialogActions>
      </Dialog>
    </Box>
  )
}

export default PaymentsPage
