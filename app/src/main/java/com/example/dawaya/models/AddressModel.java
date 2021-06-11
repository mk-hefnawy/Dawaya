package com.example.dawaya.models;

public class AddressModel {

    public String county;
    public String street;
    public String buildingNo;
    public String floorNo;
    public String apartmentNo;

    public AddressModel(){
        this.county = "";
        this.street = "";
        this.buildingNo = "";
        this.floorNo = "";
        this.apartmentNo = "";
    }

    public AddressModel( String county, String street, String buildingNo, String floorNo, String apartmentNo) {
        this.county = county;
        this.street = street;
        this.buildingNo = buildingNo;
        this.floorNo = floorNo;
        this.apartmentNo = apartmentNo;
    }

    public String getCounty() {
        return county;
    }
    public void setCounty(String county) {
        this.county = county;
    }

    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuildingNo() {
        return buildingNo;
    }
    public void setBuildingNo(String buildingNo) {
        this.buildingNo = buildingNo;
    }

    public String getFloorNo() {
        return floorNo;
    }
    public void setFloorNo(String floorNo) {
        this.floorNo = floorNo;
    }

    public String getApartmentNo() {
        return apartmentNo;
    }
    public void setApartmentNo(String apartmentNo) {
        this.apartmentNo = apartmentNo;
    }


}
