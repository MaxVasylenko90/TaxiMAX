package dev.mvasylenko.ratingservice.mapper;

import dev.mvasylenko.core.dto.RatingDto;
import dev.mvasylenko.ratingservice.entity.Rating;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RatingMapper {
    RatingMapper INSTANCE = Mappers.getMapper(RatingMapper.class);

    RatingDto ratingToRatingDto(Rating rating);
    Rating ratingDtoToRating(RatingDto rating);
}
