import React from "react";
import { AppBar, Toolbar, Typography, Button, Box, Avatar} from "@mui/material";
import { Link, useLocation } from "react-router-dom";
import { useTheme } from "@mui/material/styles";
import logo2 from '../../assets/pratels2.png';
import slogan2 from '../../assets/slogan2.png';



export function Navigation() {
  const theme = useTheme();

  const location = useLocation();

  const isActive = (path) => location.pathname === path;
  
  return (
<AppBar position="static" sx={{ backgroundColor: theme.custom.background }}>
  <Toolbar sx={{display: "flex", justifyContent: "space-between" }}>
  <Box component={Link} to="/" sx={{ display: "flex", alignItems: "center", my: 1 }}> 
    <Avatar src={logo2} alt="Logo" sx={{ mr: 2, width: 300, height: "auto", borderRadius: 0 }} />
    <Avatar src={slogan2} alt="slogan" sx={{ width: 250, height: "auto", mt: 5,borderRadius: 0 }} />
  </Box>
  <Box sx={{ display: "flex", alignItems: "center" }}>
    <Button component={Link} to="/add-parcel" sx={{ color: theme.palette.text.secondary, fontWeight: isActive("/add-parcel") ? "bold" : "normal", }}>
      Add Parcel
    </Button>
  </Box>
  <Box sx={{ display: "flex", alignItems: "center" }}>
    <Button component={Link} to="/" sx={{ color: theme.palette.text.secondary, fontWeight: isActive("/") ? "bold" : "normal", }}>
      Home
    </Button>
    <Button component={Link} to="/about" sx={{ color: theme.palette.text.secondary, fontWeight: isActive("/about") ? "bold" : "normal", }}>
      About
    </Button>
    <Button component={Link} to="/contact" sx={{ color: theme.palette.text.secondary, fontWeight: isActive("/contact") ? "bold" : "normal", }}>
      Contact
    </Button>
  </Box>
  </Toolbar>
</AppBar>

  );
}
