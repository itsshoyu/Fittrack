package com.fittrack.model;

public class BMICalculator {
    
    public double calculate(double beratKg, double tinggiCm) {
        if (tinggiCm <= 0) {
            return 0;
        }
        double tinggiM = tinggiCm / 100.0;
        return beratKg / (tinggiM * tinggiM);
    }

    public String getCategory(double bmi) {
        if (bmi <= 0) return "";
        if (bmi < 18.5) return "Kurus";
        if (bmi < 25) return "Normal";
        if (bmi < 30) return "Gemuk";
        return "Obesitas";
    }
}