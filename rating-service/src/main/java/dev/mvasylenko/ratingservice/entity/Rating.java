package dev.mvasylenko.ratingservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "ratings")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Min(1)
    @Max(5)
    private int stars;

    @Size(max = 500)
    private String comment;

    private LocalDateTime date;

    public Rating() {
    }

    public Rating(int stars, String comment, LocalDateTime date) {
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
}
