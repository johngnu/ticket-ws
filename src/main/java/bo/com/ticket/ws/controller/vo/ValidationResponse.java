package bo.com.ticket.ws.controller.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidationResponse {

    private boolean success;
    private String message;

    public ValidationResponse(boolean success) {
        this.success = success;
        this.message = "";
    }
}
