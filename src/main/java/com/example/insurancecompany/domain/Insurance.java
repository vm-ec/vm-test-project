package com.example.insurancecompany.domain;

import com.example.insurancecompany.constant.InsuranceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "insurance")
public class Insurance {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", unique = true, nullable = false)
        private Long id;

        @Enumerated(EnumType.STRING)
        @Column(name = "type", nullable = false)
        private InsuranceType type;

        @Column(name = "start_date", nullable = false)
        private LocalDate startDate;

        @Column(name = "end_date", nullable = false)
        private LocalDate endDate;

        @Column(name = "price", nullable = false)
        private Integer price;

        @Column(name = "printable_details", nullable = false)
        private String printableDetails;

        @ManyToOne
        @JoinColumn(name = "user_id", referencedColumnName = "id")
        private User user;

}
