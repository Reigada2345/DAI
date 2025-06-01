package models;
public class Route {
    private int routeId;
    private String agencyId;
    private String routeShortName;
    private String routeLongName;
    private int routeType;

    public Route(int routeId, String agencyId, String routeShortName, String routeLongName, int routeType) {
        this.routeId = routeId;
        this.agencyId = agencyId;
        this.routeShortName = routeShortName;
        this.routeLongName = routeLongName;
        this.routeType = routeType;
    }

    public int getRouteId() {
        return routeId;
    }

    public String getAgencyId() {
        return agencyId;
    }

    public String getRouteShortName() {
        return routeShortName;
    }

    public String getRouteLongName() {
        return routeLongName;
    }

    public int getRouteType() {
        return routeType;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    public void setRouteShortName(String routeShortName) {
        this.routeShortName = routeShortName;
    }

    public void setRouteLongName(String routeLongName) {
        this.routeLongName = routeLongName;
    }

    public void setRouteType(int routeType) {
        this.routeType = routeType;
    }

    @Override
    public String toString() {
        return "Route{" +
                "routeId=" + routeId +
                ", agencyId='" + agencyId + '\'' +
                ", routeShortName='" + routeShortName + '\'' +
                ", routeLongName='" + routeLongName + '\'' +
                ", routeType=" + routeType +
                '}';
    }
}
