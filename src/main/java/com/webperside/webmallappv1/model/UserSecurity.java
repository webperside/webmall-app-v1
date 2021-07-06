package com.webperside.webmallappv1.model;

import com.webperside.webmallappv1.enums.DataStatus;
import com.webperside.webmallappv1.enums.EmailConfirmation;
import com.webperside.webmallappv1.enums.Gender;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserSecurity {

    private Integer userSecurityId;
    private User user;
    private EmailConfirmation emailConfirmation;
    private String emailConfirmationCode;
    private String passwordResetToken;
    private LocalDateTime passwordResetTokenExpireDate;
    private Instant createdAt;
    private Instant modifiedAt;
    private DataStatus dataStatus;

    public Integer getUserSecurityId() {
        return userSecurityId;
    }

    public void setUserSecurityId(Integer userSecurityId) {
        this.userSecurityId = userSecurityId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public EmailConfirmation getEmailConfirmation() {
        return emailConfirmation;
    }

    public void setEmailConfirmation(EmailConfirmation emailConfirmation) {
        this.emailConfirmation = emailConfirmation;
    }

    public String getEmailConfirmationCode() {
        return emailConfirmationCode;
    }

    public void setEmailConfirmationCode(String emailConfirmationCode) {
        this.emailConfirmationCode = emailConfirmationCode;
    }

    public String getPasswordResetToken() {
        return passwordResetToken;
    }

    public void setPasswordResetToken(String passwordResetToken) {
        this.passwordResetToken = passwordResetToken;
    }

    public LocalDateTime getPasswordResetTokenExpireDate() {
        return passwordResetTokenExpireDate;
    }

    public void setPasswordResetTokenExpireDate(LocalDateTime passwordResetTokenExpireDate) {
        this.passwordResetTokenExpireDate = passwordResetTokenExpireDate;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Instant modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public DataStatus getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(DataStatus dataStatus) {
        this.dataStatus = dataStatus;
    }
}
