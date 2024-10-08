package kz.singularity.bankappdelivery.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class ErrorRes {
    HttpStatus httpStatus;
    String message;
}
