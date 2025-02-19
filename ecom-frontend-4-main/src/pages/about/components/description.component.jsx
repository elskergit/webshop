import { Typography, Box, Grid2, List, ListItem, ListItemText, useTheme } from '@mui/material';
import { CheckCircleOutline as CheckIcon } from '@mui/icons-material';
import React from 'react';

function Title(props) {
    const theme = useTheme();
    return (
      <Box sx={{ textAlign: "center", marginBottom: "40px" }}>
        <Typography
          variant="h2"
          gutterBottom
          sx={{ fontWeight: "bold", color: theme.custom.logo }}
        >
          {props.title}
        </Typography>
        <Typography variant="h5">{props.description}</Typography>
      </Box>
    );
  }


function CardContentSection(props) {
  const theme = useTheme();

  return (
    <Box sx={{ padding: '20px', backgroundColor: theme.palette.background.paper, borderRadius: '8px', boxShadow: 3, height: '100%' }}>
      <Typography variant="h5" gutterBottom sx={{ color: theme.palette.primary.dark }}>
        {props.title}
      </Typography>
      {props.children}
    </Box>
  );
}

function ListSection(props) {
    const theme = useTheme();
  
    return (
      <List sx={{ paddingLeft: "20px", fontSize: "1rem" }}>
        {props.items.map((item) => {
          return (
            <ListItem key={item.title} sx={{ display: "flex", alignItems: "center", marginBottom: "15px" }}>
            {item.icon}
              <ListItemText primary={item.title} />
            </ListItem>
          );
        })}
      </List>
    );
  }

export function Description() {
    const theme = useTheme()
  return (
    <>
        <Title
        title="✨ About the Parcel Teleportation Service ✨"
        description="Our mission is to revolutionize parcel delivery with teleportation technology. Get your packages from point A to point B in the blink of an eye—safely, securely, and instantly."
      />
      <Grid2 container spacing={4}>
        <Grid2 size={6}>
          <CardContentSection title="🪄 Our Magical Process 🪄">
            Using state-of-the-art technology combined with ancient teleportation spells, we offer a seamless, secure way to teleport your parcels across any distance. Our process is quick, reliable, and completely safe.
          </CardContentSection>
        </Grid2>
        <Grid2 size={6}>
          <CardContentSection title="✨ Why Choose Us? ✨">
          <ListSection
            items={[
                {
                title: "Fast and reliable teleportation with zero delays.",
                icon: <CheckIcon sx={{ marginRight: 1, color: theme.palette.success.main }}/> 
                },
                {
                title: "Full security and tracking for your peace of mind.",
                icon: <CheckIcon sx={{ marginRight: 1, color: theme.palette.success.main }}/>
                },
                {
                title: "Transparent pricing with no hidden fees.",
                icon: <CheckIcon sx={{ marginRight: 1, color: theme.palette.success.main }}/>
                },
            ]}
            />
          </CardContentSection>
        </Grid2>
      </Grid2>
    </>
  );
}
