import React from "react";
import { BrowserRouter, Routes, Route, Link } from "react-router-dom";
import { Navigation } from "./infrastructure/navigation";
import { theme } from "./infrastructure/theme/theme";
import { ThemeProvider } from "@mui/material/styles";
import { WizardContact } from "./pages/contact/contactPage"
import { AboutPage } from "./pages/about/aboutPage";
import { AddParcel } from "./pages/addParcel/addParcelPage";
import { HomePage } from "./pages/home/homePage";


function App() {
  return (
    <ThemeProvider theme={theme}>
        <BrowserRouter>
          <Navigation />
          <Routes>
            <Route path="/" element={<HomePage />} />
            <Route path="/add-parcel" element={<AddParcel />} />
            <Route path="/about" element={<AboutPage />} />
            <Route path="/contact" element={<WizardContact />} />
          </Routes>
        </BrowserRouter>
    </ThemeProvider>
  );
}

export default App;