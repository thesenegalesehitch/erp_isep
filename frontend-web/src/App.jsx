import { Routes, Route } from 'react-router-dom'
import { Box } from '@mui/material'
import Navbar from './components/layout/Navbar'
import HomePage from './pages/HomePage'
import LoginPage from './pages/auth/LoginPage'
import RegisterPage from './pages/auth/RegisterPage'
import DashboardPage from './pages/DashboardPage'
import ServicesPage from './pages/services/ServicesPage'
import MessagingPage from './pages/messaging/MessagingPage'
import CalendarPage from './pages/calendar/CalendarPage'
import BusPage from './pages/bus/BusPage'
import ForumPage from './pages/forum/ForumPage'

function App() {
  return (
    <Box sx={{ display: 'flex', flexDirection: 'column', minHeight: '100vh' }}>
      <Navbar />
      <Box component="main" sx={{ flexGrow: 1 }}>
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/register" element={<RegisterPage />} />
          <Route path="/dashboard" element={<DashboardPage />} />
          <Route path="/services" element={<ServicesPage />} />
          <Route path="/messaging" element={<MessagingPage />} />
          <Route path="/calendar" element={<CalendarPage />} />
          <Route path="/bus" element={<BusPage />} />
          <Route path="/forum" element={<ForumPage />} />
        </Routes>
      </Box>
    </Box>
  )
}

export default App

