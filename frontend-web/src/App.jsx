import React from 'react'
import { Routes, Route } from 'react-router-dom'
import { Box } from '@mui/material'
import Navbar from './components/layout/Navbar'
import HomePage from './pages/HomePage'
import LoginPage from './pages/auth/LoginPage'
import RegisterPage from './pages/auth/RegisterPage'
import ParentRegisterPage from './pages/auth/ParentRegisterPage'
import DashboardPage from './pages/DashboardPage'
import ParentDashboardPage from './pages/parent/ParentDashboardPage'
import StudentGradesPage from './pages/parent/StudentGradesPage'
import AttendancePage from './pages/parent/AttendancePage'
import PaymentsPage from './pages/parent/PaymentsPage'
import StudentLinkPage from './pages/parent/StudentLinkPage'
import MessagingPage from './pages/messaging/MessagingPage'
import CalendarPage from './pages/calendar/CalendarPage'
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
          <Route path="/parent/register" element={<ParentRegisterPage />} />
          <Route path="/dashboard" element={<DashboardPage />} />
          <Route path="/parent/dashboard" element={<ParentDashboardPage />} />
          <Route path="/parent/grades" element={<StudentGradesPage />} />
          <Route path="/parent/attendance" element={<AttendancePage />} />
          <Route path="/parent/payments" element={<PaymentsPage />} />
          <Route path="/parent/students" element={<StudentLinkPage />} />
          <Route path="/messaging" element={<MessagingPage />} />
          <Route path="/calendar" element={<CalendarPage />} />
          <Route path="/forum" element={<ForumPage />} />
        </Routes>
      </Box>
    </Box>
  )
}

export default App

