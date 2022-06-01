package bo.com.ticket.ws.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@ApiModel(description = "Datos para registrar una reserva")
public class ReservaDto {
    @ApiModelProperty(dataType = "String", required = true, example = "59170685903")
    private String celular;
    @ApiModelProperty(dataType = "String", required = true, example = "AAA111")
    private String matricula;
    @ApiModelProperty(dataType = "Int", required = true, example = "60")
    private int minutos;
    @ApiModelProperty(dataType = "Time", required = true, example = "15:30")
    @JsonFormat(pattern = "HH:mm")
    private String horaInicio;
}
