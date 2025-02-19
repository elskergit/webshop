import { Typography, Container, Box, Button, useTheme, Grid2, Card, CardContent, Avatar } from '@mui/material';
import { CheckCircleOutline as CheckIcon,  } from '@mui/icons-material';
import { List, ListItem, ListItemText } from '@mui/material';

import { Star as StarIcon } from '@mui/icons-material';
import { Swiper, SwiperSlide } from 'swiper/react';
import "swiper/css";
import "swiper/css/autoplay";
import "swiper/css/effect-coverflow";
import { Autoplay, EffectCoverflow } from 'swiper/modules';
import React, { useContext, useState, useEffect, useRef } from "react";
import { ReviewsContextProvider } from '../services/reviews/context';
import { LoadingComponent } from './loading.component';

import { ReviewsContext } from '../services/reviews/context';

export function AboutPage() {
  return (
    <ReviewsContextProvider>
      <AboutPage2 />
    </ReviewsContextProvider>
  );
}

export function AboutPage2() {
  const theme = useTheme();

  const { reviews, isLoading, error } = useContext(ReviewsContext);
  console.log(reviews); // Check if reviews are being passed correctly

  return (
    <Container sx={{ width: '100%', padding: '40px 20px' }}>
      <Box sx={{ textAlign: 'center', marginBottom: '40px' }}>
        <Typography variant="h2" gutterBottom sx={{ fontWeight: 'bold', color: theme.custom.logo }}>
          ✨ About the Parcel Teleportation Service ✨
        </Typography>
        <Typography variant="h5">
          Our mission is to revolutionize parcel delivery with teleportation technology. Get your packages from point A to point B in the blink of an eye—safely, securely, and instantly.
        </Typography>
      </Box>
      <Grid2 container spacing={4}>
        <Grid2 size={6}>
          <Box sx={{ padding: '20px', backgroundColor: theme.palette.background.paper, borderRadius: '8px', boxShadow: 3, 'height': '100%' }}>
            <Typography variant="h5" gutterBottom sx={{ color: theme.palette.primary.dark }}>
              🪄 Our Magical Process 🪄
            </Typography>
            <Typography variant="body1">
              Using state-of-the-art technology combined with ancient teleportation spells, we offer a seamless, secure way to teleport your parcels across any distance. Our process is quick, reliable, and completely safe.
            </Typography>
          </Box>
        </Grid2>
        <Grid2 size={6} alignItems="stretch">
          <Box sx={{ padding: '20px', backgroundColor: theme.palette.background.paper, borderRadius: '8px', boxShadow: 3, 'height': '100%' }}>
            <Typography variant="h5" gutterBottom sx={{ color: theme.palette.primary.dark }}>
              ✨ Why Choose Us? ✨
            </Typography>
            <List sx={{ paddingLeft: '20px', fontSize: '1rem' }}>
              <ListItem sx={{ display: 'flex', alignItems: 'center', marginBottom: '15px' }}>
                <CheckIcon sx={{ marginRight: 1, color: theme.palette.success.main }} />
                <ListItemText primary="Fast and reliable teleportation with zero delays." />
              </ListItem>
              <ListItem sx={{ display: 'flex', alignItems: 'center', marginBottom: '15px' }}>
                <CheckIcon sx={{ marginRight: 1, color: theme.palette.success.main }} />
                <ListItemText primary="Full security and tracking for your peace of mind." />
              </ListItem>
              <ListItem sx={{ display: 'flex', alignItems: 'center', marginBottom: '15px' }}>
                <CheckIcon sx={{ marginRight: 1, color: theme.palette.success.main }} />
                <ListItemText primary="Transparent pricing with no hidden fees." />
              </ListItem>
            </List>
          </Box>
        </Grid2>
      </Grid2>


      <Box sx={{ textAlign: 'center', marginTop: '60px' }}>
        <Typography variant="h5" gutterBottom sx={{ color: theme.palette.primary.dark }}>
          🌟 What Our Customers Are Saying 🌟
        </Typography>
        {isLoading ? <LoadingComponent /> : <ReviewSwiper reviews={reviews}/>}
      </Box>
    </Container>
  );
}

function ReviewSwiper({reviews}) {
  return (
    <Swiper
    spaceBetween={20}
    modules={[Autoplay, EffectCoverflow]}
    slidesPerView= "3" // Allows multiple slides to be visible
    autoplay={{ delay: 3000, disableOnInteraction: false }} // Auto-plays every 3s
    effect="coverflow" // Set effect to coverflow
    loop={true}
    grabCursor={true} // Allow dragging
    style={{ paddingBottom: '20px' }}
    coverflowEffect={{
      rotate: 50,
      stretch: 0,
      depth: 100,
      modifier: 1,
      slideShadows: true,
    }}
  >
      {reviews.map((review, index) => (
        <SwiperSlide key={index}>
          <ReviewCard review={review} />
        </SwiperSlide>
      ))}
  </Swiper>
  )

}

const ReviewCard = ({ review }) => {
  const { name, text, rating } = review;
  const theme = useTheme()

  return (
    <Card sx={{ boxShadow: 3, borderRadius: '8px', padding: '20px', backgroundColor: theme.palette.background.paper  }}>
      <CardContent>
        <Avatar sx={{ marginBottom: '15px', width: 50, height: 50 }} alt={name} src="/images/john_doe.jpg"/>
        <Typography variant="body1" paragraph>
          {text}
        </Typography>
        <Box sx={{ display: 'flex', alignItems: 'center' }}>
          {[...Array(5)].map((_, index) => (
            <StarIcon key={index} sx={{ color: index < rating ? 'warning.main' : 'grey.500', fontSize: '18px' }} />
          ))}
        </Box>
        <Typography variant="body2" sx={{ marginTop: '10px', fontWeight: 'bold' }}>
          {name}
        </Typography>
      </CardContent>
    </Card>
  );
};