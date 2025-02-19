import React from "react";
import { BrowserRouter, Routes, Route, Link } from "react-router-dom";
import { Navigation } from "./infrastructure/navigation";
import { theme } from "./infrastructure/theme/theme";
import { ThemeProvider } from "@mui/material/styles";
import {WizardContact} from "./components/contact"
import { AboutPage } from "./pages/about/about.page";

function Services() { return <h2>Services Page</h2>; }

function App() {
  return (
    <ThemeProvider theme={theme}>
        <BrowserRouter>
          <Navigation />
          <Routes>
            <Route path="/" element={<Services />} />
            <Route path="/about" element={<AboutPage />} />
            <Route path="/services" element={<Services />} />
            <Route path="/contact" element={<WizardContact />} />
          </Routes>
        </BrowserRouter>
    </ThemeProvider>
  );
}

export default App;