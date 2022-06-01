package bo.com.ticket.ws.util;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ErrorMessage {
    private String message;
    private String details;
}
