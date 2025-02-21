import React from "react";
import { useTheme } from "@mui/material/styles";



import { Button, TextField, Typography, Container, Box } from '@mui/material';

export function ParcelTeleportationContact() {
  return (
    <Container sx={{ textAlign: 'center', padding: '20px' }}>
      <Typography variant="h3" gutterBottom>
        ✨ Welcome to the Parcel Teleportation Service ✨
      </Typography>
      <Typography variant="h6">
        Need to teleport a package across dimensions? Drop us a message, and we’ll handle the rest!
      </Typography>
      <Box sx={{ padding: '20px', backgroundColor: '#f4f4f4', borderRadius: '8px', boxShadow: 3 }}>
        <Typography variant="h5" gutterBottom>
          🚚 Parcel Teleportation Contact Portal 🚚
        </Typography>
        <form>
          <TextField
            label="Your Name"
            variant="outlined"
            fullWidth
            sx={{ marginBottom: 2 }}
          />
          <TextField
            label="Your Email"
            variant="outlined"
            type="email"
            fullWidth
            sx={{ marginBottom: 2 }}
          />
          <TextField
            label="Parcel Details (Size, Weight, Destination)"
            variant="outlined"
            multiline
            rows={4}
            fullWidth
            sx={{ marginBottom: 2 }}
          />
          <Button variant="contained" color="primary" fullWidth>
            Teleport My Parcel 🌌
          </Button>
        </form>
      </Box>
      <Box sx={{ marginTop: 3 }}>
        <Typography variant="body1">
          🌍 Need urgent teleportation? Contact us via the enchanted mirror at: 
          <strong> teleport@parcelportals.com</strong>
        </Typography>
      </Box>
    </Container>
  );
}




export function WizardContact() {
    const theme = useTheme();
  return (
    <Container sx={{ textAlign: 'center', padding: '20px' }}>
      <Typography variant="h3" gutterBottom>
        ✨ Welcome to the Wizard's Contact Page ✨
      </Typography>
      <Typography variant="h6">
        Need help with a spell or a potion or parcel? The wizard might not respond! 
      </Typography>
      <Box sx={{ padding: '20px', backgroundColor: '#f4f4f4', borderRadius: '8px', boxShadow: 3 }}>
        <Typography variant="h5" gutterBottom>
          🧙‍♂️ Contact the Wizard 🧙‍♀️
        </Typography>
        <form>
          <TextField
            label="Your Name"
            variant="outlined"
            fullWidth
            sx={{ marginBottom: 2 }}
          />
          <TextField
            label="Your Email"
            variant="outlined"
            type="email"
            fullWidth
            sx={{ marginBottom: 2 }}
          />
          <TextField
            label="Your Message"
            variant="outlined"
            multiline
            rows={4}
            fullWidth
            sx={{ marginBottom: 2 }}
          />
          <Button sx={{ backgroundColor: theme.custom.slogan }} fullWidth>
            Enquire Magic 🪄
          </Button>
        </form>
      </Box>
      <Box sx={{ marginTop: 3 }}>
        <Typography variant="body1">
          🦉 Or send an owl at: 
          <strong> contact@wizardry.com</strong>
        </Typography>
      </Box>
    </Container>
  );
}
