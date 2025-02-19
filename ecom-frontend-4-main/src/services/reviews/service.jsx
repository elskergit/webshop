import camelize from "camelize";


const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;


export async function reviewsRequest() {
    try {
      const response = await fetch(`${API_BASE_URL}/reviews`);
  
      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }
  
      const data = await response.json();
      return data;
    } catch (error) {
      console.error("Error fetching reviews:", error);
      throw error;
    }
  }

export async function addReview(reviewData) {

try {
    const response = await fetch(`${API_BASE_URL}/reviews`, {
    method: 'POST',
    headers: {
        "Content-Type": "application/json",
    },
    body: JSON.stringify(reviewData),
    });

    if (!response.ok) {
    throw new Error(`Failed to add review. Status: ${response.status}`);
    }

    const data = await response.json();
    return data;
    
} catch (error) {
    console.error("Error adding review:", error);
    throw error;
}
}

  export const reviewsTransform = (reviews = []) => {
    const mappedResults = reviews.map((review) => {
      return { ...review, rating: parseFloat(review.rating) };
    });
    return camelize(mappedResults);
  };