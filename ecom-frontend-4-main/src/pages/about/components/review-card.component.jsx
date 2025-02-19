import { Typography, Box, Button, useTheme, Card, CardContent, Avatar, Rating } from '@mui/material';
import { Star as StarIcon } from '@mui/icons-material';
import React from "react";



export const ReviewCard = ({ review }) => {
  const { name, text, rating, image } = review;
  const theme = useTheme()

  return (
    <Card sx={{ boxShadow: 3, borderRadius: '8px', padding: '20px', backgroundColor: theme.palette.background.paper  }}>
      <CardContent>
        <Avatar sx={{ marginBottom: '15px', width: 50, height: 50 }} alt={name} src="/images/john_doe.jpg"/>
        <Typography variant="body1" paragraph>
          {text}
        </Typography>
        <Box sx={{ display: 'flex', alignItems: 'center' }}>
        <Rating value={rating} readOnly precision={0.5} max={5} />
        </Box>
        <Typography variant="body2" sx={{ marginTop: '10px', fontWeight: 'bold' }}>
          {name}
        </Typography>
      </CardContent>
    </Card>
  );
};