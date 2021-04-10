package south.bank.test.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import south.bank.test.dto.ErroDetalhesDTO;
import south.bank.test.exception.BusinessException;

@ControllerAdvice
public class BusinessExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> erroDeNegocioExceptionHandler(BusinessException erroDeNegocio) {
        ErroDetalhesDTO erroNegocioDetail = ErroDetalhesDTO.builder()
                .titulo("Erro de negócio!")
                .mensagem(erroDeNegocio.getMessage())
                .build();
        return new ResponseEntity<>(erroNegocioDetail, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}