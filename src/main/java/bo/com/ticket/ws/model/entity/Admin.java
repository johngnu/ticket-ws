package bo.com.ticket.ws.model.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Admin {

    private long id;

    private String name;

    private String email;

    private String password;

    private Boolean enabled;
}
