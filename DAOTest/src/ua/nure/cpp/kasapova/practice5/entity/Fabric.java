package ua.nure.cpp.kasapova.practice5.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class Fabric {
    protected int id;
    protected String name;
    protected BigDecimal width;
    protected BigDecimal pricePerMeter;

    public Fabric() {
    }

    public Fabric(int id, String name, BigDecimal width, BigDecimal pricePerMeter) {
        this.id = id;
        this.name = name;
        this.width = width;
        this.pricePerMeter = pricePerMeter;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getWidth() {
        return width;
    }

    public void setWidth(BigDecimal width) {
        this.width = width;
    }

    public BigDecimal getPricePerMeter() {
        return pricePerMeter;
    }

    public void setPricePerMeter(BigDecimal pricePerMeter) {
        this.pricePerMeter = pricePerMeter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fabric fabric = (Fabric) o;
        return id == fabric.id && Objects.equals(name, fabric.name) && Objects.equals(width, fabric.width) && Objects.equals(pricePerMeter, fabric.pricePerMeter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, width, pricePerMeter);
    }

    @Override
    public String toString() {
        return "Fabric[" +
                "id:" + id +
                ", name:'" + name + '\'' +
                ", width:" + width +
                ", pricePerMeter:" + pricePerMeter +
                ']' + "\n";
    }
}
