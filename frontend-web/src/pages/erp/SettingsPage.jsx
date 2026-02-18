import React, { useState } from 'react'
import {
  Box,
  Card,
  CardContent,
  Typography,
  Grid,
  Button,
  TextField,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  Switch,
  FormControlLabel,
  Divider,
  Paper,
  List,
  ListItem,
  ListItemText,
  ListItemSecondaryAction,
  Chip
} from '@mui/material'
import {
  Save as SaveIcon,
  Backup as BackupIcon,
  Security as SecurityIcon,
  Notifications as NotificationsIcon,
  Language as LanguageIcon,
  Storage as StorageIcon
} from '@mui/icons-material'

const SettingsPage = () => {
  const [settings, setSettings] = useState({
    general: {
      schoolName: 'ISEP - Institut Supérieur d\'Enseignement Professionnel',
      schoolCode: 'ISEP',
      academicYear: '2024-2025',
      language: 'fr',
      timezone: 'Africa/Dakar',
      currency: 'XOF'
    },
    notifications: {
      emailNotifications: true,
      smsNotifications: false,
      pushNotifications: true,
      enrollmentReminders: true,
      gradePublishing: true,
      paymentReminders: true,
      systemUpdates: true
    },
    security: {
      twoFactorAuth: false,
      sessionTimeout: 30,
      passwordComplexity: true,
      loginAttempts: 3,
      auditLogging: true
    },
    backup: {
      autoBackup: true,
      backupFrequency: 'daily',
      retentionPeriod: 30,
      backupLocation: 'cloud',
      encryptionEnabled: true
    },
    modules: {
      dormitories: false,
      restaurant: true,
      research: true,
      enterprise: true,
      library: false,
      transport: false
    }
  })

  const handleSave = () => {
    console.log('Saving settings:', settings)
    // Save logic here
  }

  const handleBackup = () => {
    console.log('Starting backup...')
    // Backup logic here
  }

  const updateSetting = (category, field, value) => {
    setSettings(prev => ({
      ...prev,
      [category]: {
        ...prev[category],
        [field]: value
      }
    }))
  }

  return (
    <Box sx={{ p: 3 }}>
      <Typography variant="h4" component="h1" gutterBottom>
        Paramètres du Système
      </Typography>

      <Grid container spacing={3}>
        {/* General Settings */}
        <Grid item xs={12} md={6}>
          <Card>
            <CardContent>
              <Typography variant="h6" gutterBottom>
                Paramètres Généraux
              </Typography>
              <Box sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}>
                <TextField
                  label="Nom de l'établissement"
                  value={settings.general.schoolName}
                  onChange={(e) => updateSetting('general', 'schoolName', e.target.value)}
                  fullWidth
                />
                <TextField
                  label="Code de l'établissement"
                  value={settings.general.schoolCode}
                  onChange={(e) => updateSetting('general', 'schoolCode', e.target.value)}
                  fullWidth
                />
                <TextField
                  label="Année académique"
                  value={settings.general.academicYear}
                  onChange={(e) => updateSetting('general', 'academicYear', e.target.value)}
                  fullWidth
                />
                <FormControl fullWidth>
                  <InputLabel>Langue</InputLabel>
                  <Select
                    value={settings.general.language}
                    onChange={(e) => updateSetting('general', 'language', e.target.value)}
                    label="Langue"
                  >
                    <MenuItem value="fr">Français</MenuItem>
                    <MenuItem value="en">English</MenuItem>
                    <MenuItem value="wo">Wolof</MenuItem>
                  </Select>
                </FormControl>
                <FormControl fullWidth>
                  <InputLabel>Fuseau Horaire</InputLabel>
                  <Select
                    value={settings.general.timezone}
                    onChange={(e) => updateSetting('general', 'timezone', e.target.value)}
                    label="Fuseau Horaire"
                  >
                    <MenuItem value="Africa/Dakar">Africa/Dakar</MenuItem>
                    <MenuItem value="Africa/Abidjan">Africa/Abidjan</MenuItem>
                    <MenuItem value="Africa/Bamako">Africa/Bamako</MenuItem>
                  </Select>
                </FormControl>
              </Box>
            </CardContent>
          </Card>
        </Grid>

        {/* Module Settings */}
        <Grid item xs={12} md={6}>
          <Card>
            <CardContent>
              <Typography variant="h6" gutterBottom>
                Modules Activés
              </Typography>
              <List>
                <ListItem>
                  <ListItemText
                    primary="Gestion des Cités Universitaires"
                    secondary="Logements et résidences étudiantes"
                  />
                  <ListItemSecondaryAction>
                    <Switch
                      checked={settings.modules.dormitories}
                      onChange={(e) => updateSetting('modules', 'dormitories', e.target.checked)}
                    />
                  </ListItemSecondaryAction>
                </ListItem>
                <ListItem>
                  <ListItemText
                    primary="Restauration Universitaire"
                    secondary="Restaurants et plans de repas"
                  />
                  <ListItemSecondaryAction>
                    <Switch
                      checked={settings.modules.restaurant}
                      onChange={(e) => updateSetting('modules', 'restaurant', e.target.checked)}
                    />
                  </ListItemSecondaryAction>
                </ListItem>
                <ListItem>
                  <ListItemText
                    primary="Laboratoires de Recherche"
                    secondary="Gestion des projets de recherche"
                  />
                  <ListItemSecondaryAction>
                    <Switch
                      checked={settings.modules.research}
                      onChange={(e) => updateSetting('modules', 'research', e.target.checked)}
                    />
                  </ListItemSecondaryAction>
                </ListItem>
                <ListItem>
                  <ListItemText
                    primary="Partenariats Entreprises"
                    secondary="Stages et alternance"
                  />
                  <ListItemSecondaryAction>
                    <Switch
                      checked={settings.modules.enterprise}
                      onChange={(e) => updateSetting('modules', 'enterprise', e.target.checked)}
                    />
                  </ListItemSecondaryAction>
                </ListItem>
                <ListItem>
                  <ListItemText
                    primary="Bibliothèque"
                    secondary="Gestion des ressources documentaires"
                  />
                  <ListItemSecondaryAction>
                    <Switch
                      checked={settings.modules.library}
                      onChange={(e) => updateSetting('modules', 'library', e.target.checked)}
                    />
                  </ListItemSecondaryAction>
                </ListItem>
                <ListItem>
                  <ListItemText
                    primary="Transport"
                    secondary="Bus et navettes"
                  />
                  <ListItemSecondaryAction>
                    <Switch
                      checked={settings.modules.transport}
                      onChange={(e) => updateSetting('modules', 'transport', e.target.checked)}
                    />
                  </ListItemSecondaryAction>
                </ListItem>
              </List>
            </CardContent>
          </Card>
        </Grid>

        {/* Notification Settings */}
        <Grid item xs={12} md={6}>
          <Card>
            <CardContent>
              <Typography variant="h6" gutterBottom sx={{ display: 'flex', alignItems: 'center', gap: 1 }}>
                <NotificationsIcon /> Notifications
              </Typography>
              <Box sx={{ display: 'flex', flexDirection: 'column', gap: 1 }}>
                <FormControlLabel
                  control={
                    <Switch
                      checked={settings.notifications.emailNotifications}
                      onChange={(e) => updateSetting('notifications', 'emailNotifications', e.target.checked)}
                    />
                  }
                  label="Notifications par email"
                />
                <FormControlLabel
                  control={
                    <Switch
                      checked={settings.notifications.smsNotifications}
                      onChange={(e) => updateSetting('notifications', 'smsNotifications', e.target.checked)}
                    />
                  }
                  label="Notifications SMS"
                />
                <FormControlLabel
                  control={
                    <Switch
                      checked={settings.notifications.pushNotifications}
                      onChange={(e) => updateSetting('notifications', 'pushNotifications', e.target.checked)}
                    />
                  }
                  label="Notifications push"
                />
                <Divider />
                <FormControlLabel
                  control={
                    <Switch
                      checked={settings.notifications.enrollmentReminders}
                      onChange={(e) => updateSetting('notifications', 'enrollmentReminders', e.target.checked)}
                    />
                  }
                  label="Rappels d'inscription"
                />
                <FormControlLabel
                  control={
                    <Switch
                      checked={settings.notifications.gradePublishing}
                      onChange={(e) => updateSetting('notifications', 'gradePublishing', e.target.checked)}
                    />
                  }
                  label="Publication des notes"
                />
                <FormControlLabel
                  control={
                    <Switch
                      checked={settings.notifications.paymentReminders}
                      onChange={(e) => updateSetting('notifications', 'paymentReminders', e.target.checked)}
                    />
                  }
                  label="Rappels de paiement"
                />
              </Box>
            </CardContent>
          </Card>
        </Grid>

        {/* Security Settings */}
        <Grid item xs={12} md={6}>
          <Card>
            <CardContent>
              <Typography variant="h6" gutterBottom sx={{ display: 'flex', alignItems: 'center', gap: 1 }}>
                <SecurityIcon /> Sécurité
              </Typography>
              <Box sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}>
                <FormControlLabel
                  control={
                    <Switch
                      checked={settings.security.twoFactorAuth}
                      onChange={(e) => updateSetting('security', 'twoFactorAuth', e.target.checked)}
                    />
                  }
                  label="Authentification à deux facteurs"
                />
                <TextField
                  label="Délai d'expiration de session (minutes)"
                  type="number"
                  value={settings.security.sessionTimeout}
                  onChange={(e) => updateSetting('security', 'sessionTimeout', parseInt(e.target.value))}
                  fullWidth
                />
                <FormControlLabel
                  control={
                    <Switch
                      checked={settings.security.passwordComplexity}
                      onChange={(e) => updateSetting('security', 'passwordComplexity', e.target.checked)}
                    />
                  }
                  label="Complexité du mot de passe"
                />
                <TextField
                  label="Tentatives de connexion max"
                  type="number"
                  value={settings.security.loginAttempts}
                  onChange={(e) => updateSetting('security', 'loginAttempts', parseInt(e.target.value))}
                  fullWidth
                />
                <FormControlLabel
                  control={
                    <Switch
                      checked={settings.security.auditLogging}
                      onChange={(e) => updateSetting('security', 'auditLogging', e.target.checked)}
                    />
                  }
                  label="Journal d'audit"
                />
              </Box>
            </CardContent>
          </Card>
        </Grid>

        {/* Backup Settings */}
        <Grid item xs={12} md={6}>
          <Card>
            <CardContent>
              <Typography variant="h6" gutterBottom sx={{ display: 'flex', alignItems: 'center', gap: 1 }}>
                <BackupIcon /> Sauvegarde
              </Typography>
              <Box sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}>
                <FormControlLabel
                  control={
                    <Switch
                      checked={settings.backup.autoBackup}
                      onChange={(e) => updateSetting('backup', 'autoBackup', e.target.checked)}
                    />
                  }
                  label="Sauvegarde automatique"
                />
                <FormControl fullWidth>
                  <InputLabel>Fréquence de sauvegarde</InputLabel>
                  <Select
                    value={settings.backup.backupFrequency}
                    onChange={(e) => updateSetting('backup', 'backupFrequency', e.target.value)}
                    label="Fréquence de sauvegarde"
                  >
                    <MenuItem value="hourly">Toutes les heures</MenuItem>
                    <MenuItem value="daily">Quotidienne</MenuItem>
                    <MenuItem value="weekly">Hebdomadaire</MenuItem>
                    <MenuItem value="monthly">Mensuelle</MenuItem>
                  </Select>
                </FormControl>
                <TextField
                  label="Période de rétention (jours)"
                  type="number"
                  value={settings.backup.retentionPeriod}
                  onChange={(e) => updateSetting('backup', 'retentionPeriod', parseInt(e.target.value))}
                  fullWidth
                />
                <FormControl fullWidth>
                  <InputLabel>Emplacement de sauvegarde</InputLabel>
                  <Select
                    value={settings.backup.backupLocation}
                    onChange={(e) => updateSetting('backup', 'backupLocation', e.target.value)}
                    label="Emplacement de sauvegarde"
                  >
                    <MenuItem value="local">Local</MenuItem>
                    <MenuItem value="cloud">Cloud</MenuItem>
                    <MenuItem value="hybrid">Hybride</MenuItem>
                  </Select>
                </FormControl>
                <FormControlLabel
                  control={
                    <Switch
                      checked={settings.backup.encryptionEnabled}
                      onChange={(e) => updateSetting('backup', 'encryptionEnabled', e.target.checked)}
                    />
                  }
                  label="Chiffrement activé"
                />
                <Button
                  variant="outlined"
                  startIcon={<BackupIcon />}
                  onClick={handleBackup}
                  sx={{ mt: 2 }}
                >
                  Lancer une sauvegarde manuelle
                </Button>
              </Box>
            </CardContent>
          </Card>
        </Grid>

        {/* System Information */}
        <Grid item xs={12} md={6}>
          <Card>
            <CardContent>
              <Typography variant="h6" gutterBottom sx={{ display: 'flex', alignItems: 'center', gap: 1 }}>
                <StorageIcon /> Informations Système
              </Typography>
              <Box sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}>
                <Box sx={{ display: 'flex', justifyContent: 'space-between' }}>
                  <Typography variant="body2">Version ISEP ERP</Typography>
                  <Chip label="v1.0.0" size="small" color="primary" />
                </Box>
                <Box sx={{ display: 'flex', justifyContent: 'space-between' }}>
                  <Typography variant="body2">Base de données</Typography>
                  <Chip label="PostgreSQL 14" size="small" />
                </Box>
                <Box sx={{ display: 'flex', justifyContent: 'space-between' }}>
                  <Typography variant="body2">Cache</Typography>
                  <Chip label="Redis 7" size="small" />
                </Box>
                <Box sx={{ display: 'flex', justifyContent: 'space-between' }}>
                  <Typography variant="body2">Espace utilisé</Typography>
                  <Chip label="2.3 GB / 10 GB" size="small" color="warning" />
                </Box>
                <Box sx={{ display: 'flex', justifyContent: 'space-between' }}>
                  <Typography variant="body2">Dernière mise à jour</Typography>
                  <Chip label="2024-11-15" size="small" />
                </Box>
                <Box sx={{ display: 'flex', justifyContent: 'space-between' }}>
                  <Typography variant="body2">Licence</Typography>
                  <Chip label="Active" size="small" color="success" />
                </Box>
              </Box>
            </CardContent>
          </Card>
        </Grid>

        {/* Save Actions */}
        <Grid item xs={12}>
          <Paper sx={{ p: 3, display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
            <Typography variant="body2" color="textSecondary">
              Les modifications seront appliquées après le redémarrage du système
            </Typography>
            <Box sx={{ display: 'flex', gap: 2 }}>
              <Button variant="outlined">
                Réinitialiser
              </Button>
              <Button
                variant="contained"
                startIcon={<SaveIcon />}
                onClick={handleSave}
              >
                Sauvegarder les paramètres
              </Button>
            </Box>
          </Paper>
        </Grid>
      </Grid>
    </Box>
  )
}

export default SettingsPage
