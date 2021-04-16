package edu.episen.si.ing1.pds.backend.server.workspace.cards.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.episen.si.ing1.pds.backend.server.workspace.users.models.UsersRequest;

import java.time.LocalDate;

public class CardRequest {
    private Long cardId;
    private String cardUId;
    private boolean expirable;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate expiredDate;

    private UsersRequest user;

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public String getCardUId() {
        return cardUId;
    }

    public void setCardUId(String cardUId) {
        this.cardUId = cardUId;
    }

    public boolean isExpirable() {
        return expirable;
    }

    public void setExpirable(boolean expirable) {
        this.expirable = expirable;
    }

    public LocalDate getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(LocalDate expiredDate) {
        this.expiredDate = expiredDate;
    }

    public UsersRequest getUser() {
        return user;
    }

    public void setUser(UsersRequest user) {
        this.user = user;
    }
}