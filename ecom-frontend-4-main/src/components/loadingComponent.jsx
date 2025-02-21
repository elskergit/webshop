import React from "react";
import { CircularProgress, Box, useTheme } from "@mui/material";

export function LoadingComponent() {
    const theme = useTheme()
  return (
    <Box
      sx={{
        position: "absolute",
        top: "50%",
        left: "50%",
        transform: "translate(-50%, -50%)",
        color: theme.custom.slogan
      }}
    >
      <CircularProgress
      sx={{
        color: theme.custom.slogan
      }} size={70} />
    </Box>
  );
}