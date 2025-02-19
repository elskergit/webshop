import { Swiper, SwiperSlide } from 'swiper/react';
import "swiper/css";
import "swiper/css/autoplay";
import "swiper/css/effect-coverflow";
import { Autoplay, EffectCoverflow } from 'swiper/modules';
import React from "react";
import { ReviewCard } from './review-card.component';



export function ReviewSwiper({reviews}) {
  return (
    <Swiper
    spaceBetween={20}
    modules={[Autoplay, EffectCoverflow]}
    slidesPerView= "3" // Allows multiple slides to be visible
    autoplay={{ delay: 3000, disableOnInteraction: false }} // Auto-plays every 3s
    effect="coverflow"
    loop={true}
    grabCursor={true} // Allow dragging
    style={{ paddingBottom: '20px' }}
    coverflowEffect={{
      rotate: 50,
      stretch: 0,
      depth: 100,
      modifier: 1,
      slideShadows: true,
    }}
  >
    {reviews.map((review) => (
      <SwiperSlide key={review.id}>
        <ReviewCard review={review} />
      </SwiperSlide>
    ))}
  </Swiper>
  )

}