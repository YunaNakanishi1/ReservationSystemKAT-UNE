package dto;

/**
 * 予約可能情報を扱うDTO
 * @author リコーITソリューションズ株式会社 z00s600124
 *
 */
public class AvailableDto {
    private String resourceId;
    private TimeDto startResource;
    private TimeDto endResource;
    private int capacity;
    private String officeName;
    private String categoryName;
    private boolean hasSupplement;

    public AvailableDto(String resourceId, TimeDto startResource, TimeDto endResource, int capacity, String officeName, String categoryName,
            boolean hasSupplement) {
        this.resourceId = resourceId;
        this.startResource = startResource;
        this.endResource = endResource;
        this.capacity = capacity;
        this.officeName = officeName;
        this.categoryName = categoryName;
        this.hasSupplement = hasSupplement;
    }

    public TimeDto getStartResource() {
        return startResource;
    }

    public void setStartResource(TimeDto startResource) {
        this.startResource = startResource;
    }

    public TimeDto getEndResource() {
        return endResource;
    }

    public void setEndResource(TimeDto endResource) {
        this.endResource = endResource;
    }

    public String getResourceId() {
        return resourceId;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getOfficeName() {
        return officeName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public boolean isHasSupplement() {
        return hasSupplement;
    }




}
