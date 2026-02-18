import React from 'react'
import { Routes, Route } from 'react-router-dom'
import { Box } from '@mui/material'
import Navbar from './components/layout/Navbar'
import HomePage from './pages/HomePage'
import LoginPage from './pages/auth/LoginPage'
import RegisterPage from './pages/auth/RegisterPage'
import DashboardPage from './pages/DashboardPage'
import StudentsPage from './pages/erp/StudentsPage'
import CoursesPage from './pages/erp/CoursesPage'
import EnrollmentsPage from './pages/erp/EnrollmentsPage'
import GradesPage from './pages/erp/GradesPage'
import SchedulePage from './pages/erp/SchedulePage'
import SchoolsPage from './pages/erp/SchoolsPage'
import ReportsPage from './pages/erp/ReportsPage'
import SettingsPage from './pages/erp/SettingsPage'

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
          <Route path="/students" element={<StudentsPage />} />
          <Route path="/courses" element={<CoursesPage />} />
          <Route path="/enrollments" element={<EnrollmentsPage />} />
          <Route path="/grades" element={<GradesPage />} />
          <Route path="/schedule" element={<SchedulePage />} />
          <Route path="/schools" element={<SchoolsPage />} />
          <Route path="/reports" element={<ReportsPage />} />
          <Route path="/settings" element={<SettingsPage />} />
        </Routes>
      </Box>
    </Box>
  )
}

export default App

