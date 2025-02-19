import { Typography, Container, Box, Button, useTheme, Grid2, Card, CardContent, Avatar, Dialog, DialogActions, DialogContent, DialogTitle } from '@mui/material';
import { CheckCircleOutline as CheckIcon } from '@mui/icons-material';
import { List, ListItem, ListItemText } from '@mui/material';

import React, { useContext, useState } from "react";
import { ReviewsContextProvider } from '../../services/reviews/context';
import { LoadingComponent } from '../../components/loading.component';
import { ReviewSwiper } from './components/review-swiper.component';

import { ReviewsContext } from '../../services/reviews/context';
import { Description } from './components/description.component';
import { AddReviewForm } from './add-review';

export function AboutPage() {
  return (
    <ReviewsContextProvider>
      <AboutPageContent  />
    </ReviewsContextProvider>
  );
}

export function AboutPageContent () {
  const theme = useTheme();

  const { reviews, isLoading, error } = useContext(ReviewsContext);
  const [openModal, setOpenModal] = useState(false)

  const handleOpen = () => setOpenModal(true);
  const handleClose = () => setOpenModal(false); 

  return (
    <>
      <Container sx={{ width: '100%', paddingTop: '40px' }}>
        <Description />
      </Container>
      <Container maxWidth='none' sx={{ width: '90%', paddingBottom: '40px' }}>
        <Box sx={{ textAlign: 'center', marginTop: '60px' }}>
          <Typography variant="h5" gutterBottom sx={{ color: theme.palette.primary.dark }}>
            📝 Customer Reviews 📝
          </Typography>
          {isLoading ? <LoadingComponent /> : <ReviewSwiper reviews={reviews} />}
        </Box>
      </Container>
      <Container sx={{ paddingBottom: "40px", display: 'flex', justifyContent: 'center' }}>
        <Button variant="contained" sx={{backgroundColor: theme.custom.logo }} onClick={handleOpen}>
        Submit your own experience!
        </Button>
        
        <Dialog open={openModal} onClose={handleClose}>
          <DialogContent>
          <AddReviewForm onReviewAdded={handleClose} />
          </DialogContent>
          <DialogActions>
            <Button onClick={handleClose} color="primary">
              Cancel
            </Button>
          </DialogActions>
        </Dialog>
      </Container>
    </>
  );
}
