package bo.com.ticket.ws.controller.v1;

import bo.com.ticket.ws.controller.dto.ReservaDto;
import bo.com.ticket.ws.controller.vo.ValidationResponse;
import bo.com.ticket.ws.service.TicketService;
import bo.com.ticket.ws.util.RestConstant;
import bo.com.ticket.ws.util.ResultResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Log4j2
@CrossOrigin
@RestController
@RequestMapping("/api/v1/tickect")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping
    public ResponseEntity<?> reservar(@RequestBody ReservaDto reservaDto) {

        ValidationResponse validationResponse = ticketService.validarDatos(reservaDto);
        if (validationResponse.isSuccess()) {

            return new ResponseEntity<>(ResultResponse.builder().status(true).message(RestConstant.RESPONSE_PERSIST_SUCCESSFULY).data(validationResponse).build(), HttpStatus.OK);
        }

        return new ResponseEntity<>(ResultResponse.builder().status(false).message(RestConstant.RESPONSE_BAD_REQUEST).data(validationResponse).build(), HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/reservas/{matricula}")
    public ResponseEntity<?> eliminarReserva(@PathVariable String matricula) {
        //
        return new ResponseEntity<>(ResultResponse.builder().status(false).message(RestConstant.RESPONSE_NOT_FOUND_RECORD).data(null).build(), HttpStatus.NOT_FOUND);
    }

    @GetMapping("/reservas")
    public ResponseEntity<?> reservas() {
        List<ReservaDto> reservaDtoList = ticketService.reservas();
        if (!reservaDtoList.isEmpty()) {
            return new ResponseEntity<>(ResultResponse.builder().status(true).message(RestConstant.RESPONSE_FIND_SUCCESSFULLY).data(reservaDtoList).build(), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultResponse.builder().status(false).message(RestConstant.RESPONSE_NOT_FOUND_RECORD).data(null).build(), HttpStatus.NOT_FOUND);
    }

    @GetMapping("/reservas/{matricula}")
    public ResponseEntity<?> reservas(@PathVariable String matricula) {
        Optional en = ticketService.getReserva(matricula);
        if (en.isPresent()) {
            return new ResponseEntity<>(ResultResponse.builder().status(true).message(RestConstant.RESPONSE_FIND_SUCCESSFULLY).data(en.get()).build(), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultResponse.builder().status(true).message(RestConstant.RESPONSE_NOT_FOUND_RECORD).data(matricula).build(), HttpStatus.NOT_FOUND);
    }

}
