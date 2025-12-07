package com.example.practicelab.model;

public class Projection {
    private int year;
    private double startingAmount;
    private double interest;
    private double endingBalance;

    public Projection(int year, double startingAmount, double interest, double endingBalance) {
        this.year = year;
        this.interest = interest;
        this.startingAmount = startingAmount;
        this.endingBalance = endingBalance;

    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getStartingAmount() {
        return startingAmount;
    }

    public void setStartingAmount(double startingAmount) {
        this.startingAmount = startingAmount;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interestRate) {
        this.interest = interestRate;
    }

    public double getEndingBalance() {
        return endingBalance;
    }

    public void setEndingBalance(double endingBalance) {
        this.endingBalance = endingBalance;
    }
}
