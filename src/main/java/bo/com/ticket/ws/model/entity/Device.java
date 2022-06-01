package bo.com.ticket.ws.model.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Device {

    private long id;

    private String clientAddress;

    private String clientOs;

    private String clientBrowser;

    private String userAgent;

    private String device;

    private String brand;

    private Integer level;

    private String fireToken;

    private Date register;

    void prePersist() {
        this.register = new Date();
        this.id = System.nanoTime();
    }

}
