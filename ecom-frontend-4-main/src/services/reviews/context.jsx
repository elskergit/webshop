import React, { useState, useEffect, createContext, useMemo } from "react";
import { reviewsRequest, reviewsTransform, addReview } from "./service";
import PropTypes from "prop-types";

export const ReviewsContext = createContext();

export function ReviewsContextProvider({ children }) {
	const [reviews, setReviews] = useState([]);
	const [isLoading, setIsLoading] = useState(false);
	const [error, setError] = useState(null);

    const retrieveReviews = () => {
        setIsLoading(true);
        setTimeout(() => {
          reviewsRequest()
            .then(reviewsTransform)
            .then((results) => {
              setIsLoading(false);
              setReviews(results);
            })
            .catch((err) => {
              setIsLoading(false);
              setError(err);
            });
        }, 2000);
      };
      
      useEffect(() => {
        retrieveReviews();
      }, []);

      const handleAddReview = async (reviewData) => {
        try {
          const newReview = await addReview(reviewData);
          setReviews((prevReviews) => [...prevReviews, newReview]);
        } catch (err) {
          setError(err);
        }
      };
  
    const contextValue = useMemo(() => ({ 
          reviews, 
          isLoading, 
          error, 
          addReview: handleAddReview 
      }), [reviews, isLoading, error]);
    

	return (
		<ReviewsContext.Provider value={contextValue}>
			{children}
		</ReviewsContext.Provider>
	);
}

ReviewsContextProvider.propTypes = {
    children: PropTypes.node.isRequired,
};




