package com.jumbo.store.locator.domain;

import java.util.Objects;

public class StoreInformation implements Comparable<StoreInformation>{
    private String city;
    private String postalCode;
    private String street;
    private String street2;
    private String street3;
    private String addressName;
    private String uuid;
    private double longitude;
    private double latitude;
    private String complexNumber;
    private boolean showWarningMessage;
    private String todayOpen;
    private String locationType;
    private boolean collectionPoint;
    private String sapStoreID;
    private String todayClose;
    private Double distance;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreet2() {
        return street2;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public String getStreet3() {
        return street3;
    }

    public void setStreet3(String street3) {
        this.street3 = street3;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getComplexNumber() {
        return complexNumber;
    }

    public void setComplexNumber(String complexNumber) {
        this.complexNumber = complexNumber;
    }

    public boolean isShowWarningMessage() {
        return showWarningMessage;
    }

    public void setShowWarningMessage(boolean showWarningMessage) {
        this.showWarningMessage = showWarningMessage;
    }

    public String getTodayOpen() {
        return todayOpen;
    }

    public void setTodayOpen(String todayOpen) {
        this.todayOpen = todayOpen;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public boolean isCollectionPoint() {
        return collectionPoint;
    }

    public void setCollectionPoint(boolean collectionPoint) {
        this.collectionPoint = collectionPoint;
    }

    public String getSapStoreID() {
        return sapStoreID;
    }

    public void setSapStoreID(String sapStoreID) {
        this.sapStoreID = sapStoreID;
    }

    public String getTodayClose() {
        return todayClose;
    }

    public void setTodayClose(String todayClose) {
        this.todayClose = todayClose;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getDistance() {
        return distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoreInformation that = (StoreInformation) o;
        return Double.compare(that.longitude, longitude) == 0 &&
                Double.compare(that.latitude, latitude) == 0 &&
                showWarningMessage == that.showWarningMessage &&
                collectionPoint == that.collectionPoint &&
                Objects.equals(city, that.city) &&
                Objects.equals(postalCode, that.postalCode) &&
                Objects.equals(street, that.street) &&
                Objects.equals(street2, that.street2) &&
                Objects.equals(street3, that.street3) &&
                Objects.equals(addressName, that.addressName) &&
                Objects.equals(uuid, that.uuid) &&
                Objects.equals(complexNumber, that.complexNumber) &&
                Objects.equals(todayOpen, that.todayOpen) &&
                Objects.equals(locationType, that.locationType) &&
                Objects.equals(sapStoreID, that.sapStoreID) &&
                Objects.equals(todayClose, that.todayClose) &&
                Objects.equals(distance, that.distance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, postalCode, street, street2, street3, addressName, uuid, longitude, latitude, complexNumber, showWarningMessage, todayOpen, locationType, collectionPoint, sapStoreID, todayClose, distance);
    }

    @Override
    public String toString() {
        return "StoreInformation{" +
                "city='" + city + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", street='" + street + '\'' +
                ", street2='" + street2 + '\'' +
                ", street3='" + street3 + '\'' +
                ", addressName='" + addressName + '\'' +
                ", uuid='" + uuid + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", complexNumber='" + complexNumber + '\'' +
                ", showWarningMessage=" + showWarningMessage +
                ", todayOpen='" + todayOpen + '\'' +
                ", locationType='" + locationType + '\'' +
                ", collectionPoint=" + collectionPoint +
                ", sapStoreID='" + sapStoreID + '\'' +
                ", todayClose='" + todayClose + '\'' +
                ", distance=" + distance +
                '}';
    }

    @Override
    public int compareTo(StoreInformation o) {
        return this.getDistance().compareTo(o.getDistance());
    }
}
