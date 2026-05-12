package padthai.dto.participant.response;

import padthai.dto.event.response.EventResponse;
import padthai.dto.event.response.ReportResponse;
import padthai.model.participant.User;
import java.util.List;

public record ProfileResponse(User user, List<EventResponse> events, List<ReportResponse> reports) {}
