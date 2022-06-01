package bo.com.ticket.ws.service;

import bo.com.ticket.ws.controller.dto.ReservaDto;
import bo.com.ticket.ws.controller.vo.ValidationResponse;


import java.util.List;
import java.util.Optional;

/**
 * @author John Castillo Valencia
 * @johngnu john.gnu@gmail.com
 * @since 31/05/2022
 */
public interface TicketService {

    ValidationResponse validarDatos(ReservaDto reservaDto);

    List<ReservaDto> reservas();

    Optional<ReservaDto> getReserva(String matricula);
}
