import React, { useState, useContext } from "react";
import { Button, TextField, Box, Typography, Rating } from "@mui/material";
import { useTheme } from "@mui/system";
import { ReviewsContext } from "../../services/reviews/context"; 

export function AddReviewForm({ onReviewAdded }) {
  const theme = useTheme();
  const { addReview } = useContext(ReviewsContext);
  const [name, setName] = useState("");
  const [rating, setRating] = useState(0);
  const [reviewText, setReviewText] = useState("");
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [error, setError] = useState(null);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setIsSubmitting(true);
    try {
      const newReview = { name, rating, text: reviewText };
      await addReview(newReview);
      onReviewAdded(newReview);
      setName("");
      setRating(5);
      setReviewText("");
    } catch (err) {
      setError("Failed to submit the review. Please try again.");
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <Box sx={{ padding: "20px", backgroundColor: theme.palette.background.paper, borderRadius: "8px", boxShadow: 3 }}>
      <Typography variant="h6" gutterBottom sx={{ color: theme.palette.primary.main }}>
        📝 Add Your Review
      </Typography>
      {error && <Typography variant="body2" sx={{ marginBottom: "15px" }}>{error}</Typography>}
      <form onSubmit={handleSubmit}>
        <TextField
          fullWidth
          label="Your Name"
          variant="outlined"
          value={name}
          onChange={(e) => setName(e.target.value)}
          sx={{ marginBottom: "15px" }}
        />
        <Box sx={{ marginBottom: "15px" }}>
          <Rating
            name="rating"
            value={rating}
            onChange={(e, newValue) => setRating(newValue)}
            precision={0.5}
            max={5}
          />
        </Box>
        <TextField
          fullWidth
          label="Your Review"
          multiline
          rows={4}
          variant="outlined"
          value={reviewText}
          onChange={(e) => setReviewText(e.target.value)}
          sx={{ marginBottom: "15px" }}
        />
        <Button
          variant="contained"
          sx={{ backgroundColor: theme.custom.logo }}
          type="submit"
          fullWidth
          disabled={isSubmitting}
        >
          {isSubmitting ? "Submitting..." : "Submit Review"}
        </Button>
      </form>
    </Box>
  );
}
