package padthai.dto.event.request;

public record EventCreateRequest(String title, String category, String tags, String startAt, String location,
                                 int capacity, String imageUrl, String description, int ownerId) {}
