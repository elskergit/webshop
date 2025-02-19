import { createTheme } from '@mui/material/styles';

export const theme = createTheme({
  palette: {
    primary: {
      light: '#b0d0f7',  // Lighter blue shade
      main: '#1a4eab',   // Main DSV blue (matches DSV logo)
      dark: '#003366',   // Darker blue for contrast
      contrastText: '#fff',  // White text on blue for readability
    },
    secondary: {
      light: '#ff6f61',   // Soft red/pinkish shade for accents
      main: '#d32f2f',    // Bold red that complements blue
      dark: '#b0d0f7',    // Darker shade for depth
      contrastText: '#fff',  // White text for high contrast
    },
  },
  custom: {
    background: '#fafafa',
    logo: '#07689f',
    slogan: '#ff7e67',
  },
  typography: {
    fontFamily: '"Roboto", "Helvetica", "Arial", sans-serif', // Matching typical corporate style
    h1: {
      fontWeight: 700,  // Bold headings
    },
    h2: {
      fontWeight: 700,
    },
    body1: {
      fontWeight: 400,  // Regular text
    },
  },
});
