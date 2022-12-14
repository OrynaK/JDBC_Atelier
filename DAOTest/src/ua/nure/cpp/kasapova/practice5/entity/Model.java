package ua.nure.cpp.kasapova.practice5.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class Model {
    private int id;
    private String name;
    private int proposedFabric;
    private BigDecimal tailoringPrice;
    private BigDecimal consumption;

    public Model() {
    }

    public Model(int id, String name, int proposedFabric,
                 BigDecimal tailoringPrice, BigDecimal consumption) {
        this.id = id;
        this.name = name;
        this.proposedFabric = proposedFabric;
        this.tailoringPrice = tailoringPrice;
        this.consumption = consumption;
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

    public int getProposedFabric() {
        return proposedFabric;
    }

    public void setProposedFabric(int proposedFabric) {
        this.proposedFabric = proposedFabric;
    }

    public BigDecimal getTailoringPrice() {
        return tailoringPrice;
    }

    public void setTailoringPrice(BigDecimal tailoringPrice) {
        this.tailoringPrice = tailoringPrice;
    }

    public BigDecimal getConsumption() {
        return consumption;
    }

    public void setConsumption(BigDecimal consumption) {
        this.consumption = consumption;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Model model = (Model) o;
        return id == model.id && proposedFabric == model.proposedFabric && Objects.equals(name, model.name) && Objects.equals(tailoringPrice, model.tailoringPrice) && Objects.equals(consumption, model.consumption);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, proposedFabric, tailoringPrice, consumption);
    }

    @Override
    public String toString() {
        return "Model[" +
                "id:" + id +
                ", name:'" + name + '\'' +
                ", proposedFabric:" + proposedFabric +
                ", tailoringPrice:" + tailoringPrice +
                ", consumption:" + consumption +
                ']' + "\n";
    }
}