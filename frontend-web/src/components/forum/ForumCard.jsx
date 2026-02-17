import React from 'react';
import {
  Card,
  CardContent,
  Typography,
  Box,
  Avatar,
  Chip,
} from '@mui/material';
import {
  Forum as ForumIcon,
  Comment as CommentIcon,
} from '@mui/icons-material';

const ForumCard = ({ forum, onClick }) => (
  <Card
    sx={{
      cursor: 'pointer',
      transition: 'transform 0.2s, box-shadow 0.2s',
      '&:hover': {
        transform: 'translateY(-4px)',
        boxShadow: 4,
      },
    }}
    onClick={() => onClick(forum)}
  >
    <CardContent>
      <Box sx={{ display: 'flex', alignItems: 'flex-start', gap: 2 }}>
        <Avatar sx={{ bgcolor: 'primary.main' }}>
          <ForumIcon />
        </Avatar>
        <Box sx={{ flex: 1 }}>
          <Typography variant="h6" component="div">
            {forum.name || forum.title}
          </Typography>
          <Typography variant="body2" color="text.secondary" sx={{ mt: 1 }}>
            {forum.description}
          </Typography>
          <Box sx={{ display: 'flex', gap: 1, mt: 2 }}>
            <Chip
              icon={<CommentIcon />}
              label={`${forum.postCount || forum.posts?.length || 0} posts`}
              size="small"
              variant="outlined"
            />
            {forum.specialty && (
              <Chip
                label={forum.specialty}
                size="small"
                color="primary"
                variant="outlined"
              />
            )}
          </Box>
        </Box>
      </Box>
    </CardContent>
  </Card>
);

export default ForumCard;
