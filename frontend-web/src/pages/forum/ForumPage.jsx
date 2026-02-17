import React, { useState, useEffect } from 'react';
import {
  Container,
  Typography,
  Card,
  CardContent,
  Button,
  TextField,
  Grid,
  Chip,
  Avatar,
  IconButton,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  Box,
  CircularProgress,
  Alert,
  MenuItem,
} from '@mui/material';
import {
  Add as AddIcon,
  Forum as ForumIcon,
  Comment as CommentIcon,
  ThumbUp as ThumbUpIcon,
  MoreVert as MoreVertIcon,
} from '@mui/icons-material';
import api from '../../services/api';
import ForumCard from '../../components/forum/ForumCard';

const ForumPage = () => {
  const [forums, setForums] = useState([]);
  const [specialties, setSpecialties] = useState([]);
  const [selectedSpecialty, setSelectedSpecialty] = useState('');
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [createDialogOpen, setCreateDialogOpen] = useState(false);
  const [newForum, setNewForum] = useState({ title: '', description: '', specialty: '' });
  const [selectedForum, setSelectedForum] = useState(null);

  useEffect(() => {
    fetchForums();
    fetchSpecialties();
  }, [selectedSpecialty]);

  const fetchForums = async () => {
    try {
      setLoading(true);
      const url = selectedSpecialty ? `/forums?specialty=${selectedSpecialty}` : '/forums';
      const response = await api.get(url);
      setForums(response.data);
      setError(null);
    } catch (err) {
      setError('Failed to load forums');
      console.error('Error fetching forums:', err);
    } finally {
      setLoading(false);
    }
  };

  const fetchSpecialties = async () => {
    try {
      const response = await api.get('/forums/specialties');
      setSpecialties(response.data);
    } catch (err) {
      console.error('Error fetching specialties:', err);
    }
  };

  const handleCreateForum = async () => {
    try {
      const response = await api.post('/forums', newForum);
      setForums([...forums, response.data]);
      setCreateDialogOpen(false);
      setNewForum({ title: '', description: '', specialty: '' });
    } catch (err) {
      setError('Failed to create forum');
      console.error('Error creating forum:', err);
    }
  };

  const handleSelectForum = (forum) => {
    setSelectedForum(forum);
  };

  const ForumDetail = ({ forum }) => (
    <Box>
      <Button
        variant="outlined"
        onClick={() => setSelectedForum(null)}
        sx={{ mb: 2 }}
      >
        ← Back to Forums
      </Button>
      <Card sx={{ mb: 3 }}>
        <CardContent>
          <Box sx={{ display: 'flex', alignItems: 'center', gap: 2, mb: 2 }}>
            <Avatar sx={{ bgcolor: 'primary.main', width: 56, height: 56 }}>
              <ForumIcon />
            </Avatar>
            <Box>
              <Typography variant="h5">
                {forum.name || forum.title}
              </Typography>
              <Typography variant="body2" color="text.secondary">
                {forum.description}
              </Typography>
            </Box>
          </Box>
          <Box sx={{ display: 'flex', gap: 1 }}>
            <Chip
              icon={<CommentIcon />}
              label={`${forum.postCount || forum.posts?.length || 0} discussions`}
              color="primary"
            />
            <Chip
              label={`Created ${new Date(forum.createdAt).toLocaleDateString()}`}
              variant="outlined"
            />
          </Box>
        </CardContent>
      </Card>

      <Typography variant="h6" sx={{ mb: 2 }}>
        Discussions
      </Typography>

      {forum.posts && forum.posts.length > 0 ? (
        forum.posts.map((post) => (
          <Card key={post.id} sx={{ mb: 2 }}>
            <CardContent>
              <Box sx={{ display: 'flex', alignItems: 'center', gap: 2, mb: 2 }}>
                <Avatar>{post.author?.name?.[0] || 'U'}</Avatar>
                <Box>
                  <Typography variant="subtitle1">
                    {post.author?.name || 'Unknown User'}
                  </Typography>
                  <Typography variant="caption" color="text.secondary">
                    {new Date(post.createdAt).toLocaleString()}
                  </Typography>
                </Box>
              </Box>
              <Typography variant="body1">{post.content}</Typography>
              <Box sx={{ display: 'flex', alignItems: 'center', gap: 1, mt: 2 }}>
                <IconButton size="small">
                  <ThumbUpIcon fontSize="small" />
                </IconButton>
                <Typography variant="body2" color="text.secondary">
                  {post.likeCount || 0}
                </Typography>
              </Box>
            </CardContent>
          </Card>
        ))
      ) : (
        <Alert severity="info">No discussions yet. Be the first to post!</Alert>
      )}

      <Card sx={{ mt: 3 }}>
        <CardContent>
          <Typography variant="h6" sx={{ mb: 2 }}>
            Start a Discussion
          </Typography>
          <TextField
            fullWidth
            multiline
            rows={3}
            placeholder="What's on your mind?"
            sx={{ mb: 2 }}
          />
          <Button variant="contained" startIcon={<AddIcon />}>
            Post Discussion
          </Button>
        </CardContent>
      </Card>
    </Box>
  );

  if (loading) {
    return (
      <Container maxWidth="lg" sx={{ py: 4, textAlign: 'center' }}>
        <CircularProgress />
        <Typography sx={{ mt: 2 }}>Loading forums...</Typography>
      </Container>
    );
  }

  return (
    <Container maxWidth="lg" sx={{ py: 4 }}>
      <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 4 }}>
        <Box>
          <Typography variant="h4" component="h1" gutterBottom>
            Forums
          </Typography>
          <Typography variant="body1" color="text.secondary">
            Join the conversation with the ISEP community
          </Typography>
        </Box>
        <Button
          variant="contained"
          startIcon={<AddIcon />}
          onClick={() => setCreateDialogOpen(true)}
        >
          Create Forum
        </Button>
      </Box>

      <Box sx={{ mb: 4, display: 'flex', gap: 2 }}>
        <TextField
          select
          label="Filtrer par spécialité"
          value={selectedSpecialty}
          onChange={(e) => setSelectedSpecialty(e.target.value)}
          sx={{ minWidth: 200 }}
          size="small"
        >
          <MenuItem value="">Toutes les spécialités</MenuItem>
          {specialties.map((specialty) => (
            <MenuItem key={specialty} value={specialty}>
              {specialty}
            </MenuItem>
          ))}
        </TextField>
      </Box>

      {error && (
        <Alert severity="error" sx={{ mb: 3 }} onClose={() => setError(null)}>
          {error}
        </Alert>
      )}

      {selectedForum ? (
        <ForumDetail forum={selectedForum} />
      ) : (
        <Grid container spacing={3}>
          {forums.map((forum) => (
            <Grid item xs={12} md={6} lg={4} key={forum.id}>
              <ForumCard forum={forum} onClick={handleSelectForum} />
            </Grid>
          ))}
          {forums.length === 0 && (
            <Grid item xs={12}>
              <Card>
                <CardContent sx={{ textAlign: 'center', py: 4 }}>
                  <ForumIcon sx={{ fontSize: 64, color: 'text.secondary', mb: 2 }} />
                  <Typography variant="h6" color="text.secondary">
                    No forums available
                  </Typography>
                  <Typography variant="body2" color="text.secondary" sx={{ mb: 2 }}>
                    Be the first to create a forum!
                  </Typography>
                  <Button
                    variant="contained"
                    startIcon={<AddIcon />}
                    onClick={() => setCreateDialogOpen(true)}
                  >
                    Create Forum
                  </Button>
                </CardContent>
              </Card>
            </Grid>
          )}
        </Grid>
      )}

      <Dialog
        open={createDialogOpen}
        onClose={() => setCreateDialogOpen(false)}
        maxWidth="sm"
        fullWidth
      >
        <DialogTitle>Create New Forum</DialogTitle>
        <DialogContent>
          <TextField
            autoFocus
            margin="dense"
            label="Forum Title"
            fullWidth
            variant="outlined"
            value={newForum.title}
            onChange={(e) => setNewForum({ ...newForum, title: e.target.value })}
            sx={{ mb: 2 }}
          />
          <TextField
            margin="dense"
            label="Description"
            fullWidth
            multiline
            rows={3}
            variant="outlined"
            value={newForum.description}
            onChange={(e) =>
              setNewForum({ ...newForum, description: e.target.value })
            }
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setCreateDialogOpen(false)}>Cancel</Button>
          <Button
            variant="contained"
            onClick={handleCreateForum}
            disabled={!newForum.title.trim()}
          >
            Create
          </Button>
        </DialogActions>
      </Dialog>
    </Container>
  );
};

export default ForumPage;
