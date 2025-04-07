package org.example.travelexpertsphase3desktop.model;

import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

public class DashBoard {
    private double totalSales;
    private double totalCommission;
    private int totalCustomers;
    private ObservableList<PieChart.Data> topSellingPackages;
    private XYChart.Series<String, Number> salesOverTime;
    private XYChart.Series<String, Number> agentPerformance; // Only for Managers

    //  Constructor
    public DashBoard(double totalSales, double totalCommission, int totalCustomers,
                     ObservableList<PieChart.Data> topSellingPackages,
                     XYChart.Series<String, Number> salesOverTime,
                     XYChart.Series<String, Number> agentPerformance) {
        this.totalSales = totalSales;
        this.totalCommission = totalCommission;
        this.totalCustomers = totalCustomers;
        this.topSellingPackages = topSellingPackages;
        this.salesOverTime = salesOverTime;
        this.agentPerformance = agentPerformance;
    }

    //  Getters
    public double getTotalSales() { return totalSales; }
    public double getTotalCommission() { return totalCommission; }
    public int getTotalCustomers() { return totalCustomers; }
    public ObservableList<PieChart.Data> getTopSellingPackages() { return topSellingPackages; }
    public XYChart.Series<String, Number> getSalesOverTime() { return salesOverTime; }
    public XYChart.Series<String, Number> getAgentPerformance() { return agentPerformance; }

    //  Setters
    public void setTotalSales(double totalSales) { this.totalSales = totalSales; }
    public void setTotalCommission(double totalCommission) { this.totalCommission = totalCommission; }
    public void setTotalCustomers(int totalCustomers) { this.totalCustomers = totalCustomers; }
    public void setTopSellingPackages(ObservableList<PieChart.Data> topSellingPackages) { this.topSellingPackages = topSellingPackages; }
    public void setSalesOverTime(XYChart.Series<String, Number> salesOverTime) { this.salesOverTime = salesOverTime; }
    public void setAgentPerformance(XYChart.Series<String, Number> agentPerformance) { this.agentPerformance = agentPerformance; }

    //  toString() for Debugging and Logging
    @Override
    public String toString() {
        return "DashBoard{" +
                "totalSales=" + totalSales +
                ", totalCommission=" + totalCommission +
                ", totalCustomers=" + totalCustomers +
                ", topSellingPackages=" + topSellingPackages +
                ", salesOverTime=" + salesOverTime +
                ", agentPerformance=" + agentPerformance +
                '}';
    }
}
