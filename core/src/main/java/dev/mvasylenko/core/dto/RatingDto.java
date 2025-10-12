package dev.mvasylenko.core.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.UUID;

public class RatingDto {

    private UUID id;

    @NotNull
    private UUID userId;

    @Min(1)
    @Max(5)
    private int stars;

    @Size(max = 500)
    private String comment;

    private LocalDateTime date;

    public RatingDto() {
    }

    public RatingDto(UUID id, UUID userId, int stars, String comment, LocalDateTime date) {
        this.id = id;
        this.userId = userId;
        this.stars = stars;
        this.comment = comment;
        this.date = date;
    }

    public UUID getId() {
        return id;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
