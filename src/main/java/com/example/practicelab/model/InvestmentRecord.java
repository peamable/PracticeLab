package com.example.practicelab.model;

import jakarta.persistence.*;


@Entity
@Table(name="records")
public class InvestmentRecord {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY) //add this if we include id
    private Long id;

    private int customerNumber;
    private String customerName;
    private Double customerDeposit;
    private int numberOfYears;
    private String savingsType;

    public InvestmentRecord() {}

    //important to have a constructor with all the features
    public InvestmentRecord(int customerNumber, String customerName, Double customerDeposit, int years, String savingsType) {
        this.customerNumber = customerNumber;
        this.customerName = customerName;
        this.customerDeposit = customerDeposit;
        this.numberOfYears = years;
        this.savingsType = savingsType;
    }

    public int getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(int customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Double getCustomerDeposit() {
        return customerDeposit;
    }

    public void setCustomerDeposit(Double customerDeposit) {
        this.customerDeposit = customerDeposit;
    }

    public int getNumberOfYears() {
        return numberOfYears;
    }

    public void setNumberOfYears(int years) {
        this.numberOfYears = years;
    }

    public String getSavingsType() {
        return savingsType;
    }

    public void setSavingsType(String savingsType) {
        this.savingsType = savingsType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
