package com.example.dawaya.responses;

public class id {

    int billId;
    int productCode;
    int companyId;
    int supplyId;

    public id(int billId, int productCode, int companyId, int supplyId) {
        this.billId = billId;
        this.productCode = productCode;
        this.companyId = companyId;
        this.supplyId = supplyId;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public int getProductCode() {
        return productCode;
    }

    public void setProductCode(int productCode) {
        this.productCode = productCode;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getSupplyId() {
        return supplyId;
    }

    public void setSupplyId(int supplyId) {
        this.supplyId = supplyId;
    }
}
