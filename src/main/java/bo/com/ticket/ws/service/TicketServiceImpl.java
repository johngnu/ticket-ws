package bo.com.ticket.ws.service;

import bo.com.ticket.ws.controller.dto.ReservaDto;
import bo.com.ticket.ws.controller.vo.ValidationResponse;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author John Castillo Valencia
 * @johngnu john.gnu@gmail.com
 * @since 31/05/2022
 */

@Service
public class TicketServiceImpl implements TicketService {
    // Database List
    private List<ReservaDto> dataBase = new ArrayList<>();
    private static final String PATTERN = "([01]?[0-9]|2[0-3]):[0-5][0-9]";

    // interval static time
    private static final LocalTime start = LocalTime.parse("10:00");
    private static final LocalTime stop = LocalTime.parse("18:00");

    @Override
    public ValidationResponse validarDatos(ReservaDto reservaDto) {
        ValidationResponse validationResponse = new ValidationResponse(true);

        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(reservaDto.getHoraInicio());
        if (!matcher.matches()) {
            validationResponse.setSuccess(false);
            validationResponse.setMessage("El formato de hora no es válido");
            return validationResponse;
        }


        LocalTime now = LocalTime.now();
        Boolean isNowValid = (now.isAfter(start) && now.isBefore(stop));
        if (!isNowValid) {
            validationResponse.setSuccess(false);
            validationResponse.setMessage("El horario de compra es de 10:00 a 18:00. Hora actual: " + now.toString());
            return validationResponse;
        }

        // set time
        LocalTime requestTime = LocalTime.parse(reservaDto.getHoraInicio());
        Boolean isRequestTimeValid = (requestTime.isAfter(now) && requestTime.isBefore(stop));
        if (!isRequestTimeValid) {
            validationResponse.setSuccess(false);
            validationResponse.setMessage("La hora solicitada debe ser posterior a: " + now.toString());
            return validationResponse;
        }
        // End request time - requesttime += requestMinutes
        LocalTime endTime = requestTime.plusMinutes(reservaDto.getMinutos());

        Optional<ReservaDto> en = findByMatricula(reservaDto.getMatricula());
        if (en.isPresent()) {
            LocalTime exitTime = LocalTime.parse(en.get().getHoraInicio()).plusMinutes(en.get().getMinutos());
            Boolean hasMatriculaTicket = requestTime.isAfter(exitTime);
            if (!hasMatriculaTicket) {
                validationResponse.setSuccess(false);
                validationResponse.setMessage("La matricula: " + reservaDto.getMatricula() + " ya tiene ticket en el horario solicitado");
                return validationResponse;
            }
        }
        // persist dummy
        dataBase.add(reservaDto);
        validationResponse.setMessage("Reservado de: " + requestTime.toString() + " a: " + endTime.toString());

        return validationResponse;
    }

    @Override
    public List<ReservaDto> reservas() {
        return dataBase;
    }

    @Override
    public Optional<ReservaDto> getReserva(String matricula) {
        return findByMatricula(matricula);
    }

    private Optional<ReservaDto> findByMatricula(String matricula) {
        for (ReservaDto reservaDto : dataBase) {
            if (reservaDto.getMatricula().equals(matricula)) {
                return Optional.of(reservaDto);
            }
        }
        return Optional.empty();
    }
}
