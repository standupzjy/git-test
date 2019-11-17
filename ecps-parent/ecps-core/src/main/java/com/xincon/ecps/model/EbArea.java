package com.xincon.ecps.model;

public class EbArea {
    private Long ereaId;

    private Long parentId;

    private String ereaName;

    private Short areaLevel;

    public Long getEreaId() {
        return ereaId;
    }

    public void setEreaId(Long ereaId) {
        this.ereaId = ereaId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getEreaName() {
        return ereaName;
    }

    public void setEreaName(String ereaName) {
        this.ereaName = ereaName;
    }

    public Short getAreaLevel() {
        return areaLevel;
    }

    public void setAreaLevel(Short areaLevel) {
        this.areaLevel = areaLevel;
    }
}