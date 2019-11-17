package com.xincon.ecps.model;

public class EbShipAddr {
    private Long shipAddrId;

    private Long ptlUserId;

    private String shipName;

    private String province;

    private String city;

    private String district;

    private String zipCode;

    private String addr;

    private String phone;

    private Short defaultAddr;

    private String fixedPhone;

    private String provText;

    private String cityText;

    private String disText;

    public String getProvText() {
        return provText;
    }

    public void setProvText(String provText) {
        this.provText = provText;
    }

    public String getCityText() {
        return cityText;
    }

    public void setCityText(String cityText) {
        this.cityText = cityText;
    }

    public String getDisText() {
        return disText;
    }

    public void setDisText(String disText) {
        this.disText = disText;
    }

    public Long getShipAddrId() {
        return shipAddrId;
    }

    public void setShipAddrId(Long shipAddrId) {
        this.shipAddrId = shipAddrId;
    }

    public Long getPtlUserId() {
        return ptlUserId;
    }

    public void setPtlUserId(Long ptlUserId) {
        this.ptlUserId = ptlUserId;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Short getDefaultAddr() {
        return defaultAddr;
    }

    public void setDefaultAddr(Short defaultAddr) {
        this.defaultAddr = defaultAddr;
    }

    public String getFixedPhone() {
        return fixedPhone;
    }

    public void setFixedPhone(String fixedPhone) {
        this.fixedPhone = fixedPhone;
    }
}