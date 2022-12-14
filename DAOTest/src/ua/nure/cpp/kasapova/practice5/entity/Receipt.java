package ua.nure.cpp.kasapova.practice5.entity;

import java.util.Date;
import java.util.Objects;

public class Receipt {
    private int id;
    private int modelId;
    private int fabricId;
    private int clientId;
    private int cutterId;
    private Date dateAcceptance;
    private Date dueDate;
    private Date dateFitting;
    private Boolean status;

    public Receipt() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getModelId() {
        return modelId;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    public int getFabricId() {
        return fabricId;
    }

    public void setFabricId(int fabricId) {
        this.fabricId = fabricId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getCutterId() {
        return cutterId;
    }

    public void setCutterId(int cutterId) {
        this.cutterId = cutterId;
    }

    public Date getDateAcceptance() {
        return dateAcceptance;
    }

    public void setDateAcceptance(Date dateAcceptance) {
        this.dateAcceptance = dateAcceptance;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getDateFitting() {
        return dateFitting;
    }

    public void setDateFitting(Date dateFitting) {
        this.dateFitting = dateFitting;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Receipt receipt = (Receipt) o;
        return id == receipt.id && modelId == receipt.modelId && fabricId == receipt.fabricId && clientId == receipt.clientId && cutterId == receipt.cutterId && Objects.equals(dateAcceptance, receipt.dateAcceptance) && Objects.equals(dueDate, receipt.dueDate) && Objects.equals(dateFitting, receipt.dateFitting) && Objects.equals(status, receipt.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, modelId, fabricId, clientId, cutterId, dateAcceptance, dueDate, dateFitting, status);
    }

    @Override
    public String toString() {
        return "Receipt[" +
                "id:" + id +
                ", modelId:" + modelId +
                ", fabricId:" + fabricId +
                ", clientId:" + clientId +
                ", cutterId:" + cutterId +
                ", dateAcceptance:" + dateAcceptance +
                ", dueDate:" + dueDate +
                ", dateFitting:" + dateFitting +
                ", status:" + status +
                ']'+"\n";
    }
}