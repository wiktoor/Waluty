package org.example;



import java.util.Map;

public class LatestData {
    private String base;
    private String date;
    private Map<String, Double> rates;
    private Boolean success;
    private Long timestamp;

    public String getBase() {
        return base;
    }
    public String getDate() {
        return date;
    }
    public Map<String, Double> getRates() {
        return rates;
    }
    public Boolean getSuccess() {
        return success;
    }
    public Long getTimestamp() {
        return timestamp;
    }
    public void setBase(String base) {
        this.base = base;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setRates(Map<String, Double> rates) {
        this.rates = rates;
    }
    public void setSuccess(Boolean success) {
        this.success = success;
    }
    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
