package prac;

public class Building {
    private String buildingID;
    private String buildingName;
    private String buildingAdmin;
    private Integer buildingRooms;

    public String getBuildingID() {
        return buildingID;
    }

    public void setBuildingID(String buildingID) {
        this.buildingID = buildingID;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getBuildingAdmin() {
        return buildingAdmin;
    }

    public void setBuildingAdmin(String buildingAdmin) {
        this.buildingAdmin = buildingAdmin;
    }

    public Integer getBuildingRooms() {
        return buildingRooms;
    }

    public void setBuildingRooms(Integer buildingRooms) {
        this.buildingRooms = buildingRooms;
    }
}
