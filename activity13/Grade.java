
package com.ayrinhaha.model;

/**
 * Represents a student's grade record.
 *
 * @author ayrinhaha
 */
public class Grade {

    private String subject;
    private double prelim;
    private double midterm;
    private double finals;

    public Grade(String subject, double prelim, double midterm, double finals) {
        this.subject = subject;
        this.prelim = prelim;
        this.midterm = midterm;
        this.finals = finals;
    }

    public double getPrelim() {
        return prelim;
    }

    public double getMidterm() {
        return midterm;
    }

    public double getFinals() {
        return finals;
    }

    @Override
    public String toString() {
        return subject + " | Prelim: " + prelim +
                " | Midterm: " + midterm +
                " | Finals: " + finals;
    }
}
