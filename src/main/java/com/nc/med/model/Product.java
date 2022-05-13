package com.nc.med.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Product implements Serializable {
    private static final long serialVersionUID = -1000119078147252957L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productName;
    @Column(name = "Price", nullable = false)
    private double price;
    private double currentPrice;
    private double purchasePrice;
    private double profit;
    //@ManyToOne(fetch = FetchType.LAZY)
    //private Category category;
    private String productDesc;
    private int qty;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private Date createdDate;
    private int gst;
    private String hsnNo;
}
