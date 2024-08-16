package net.dayner.api.domain.payment.paymentArchive.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

import java.time.LocalDateTime;

@Entity
@Table(name = "payment_archive_tb")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentArchive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    private PaymentType paymentType;

    @Column(nullable = false)
    private String phoneNumber;

    private String identificationNumber;

    private int amount;

    private LocalDateTime date;

    private String description;
}
