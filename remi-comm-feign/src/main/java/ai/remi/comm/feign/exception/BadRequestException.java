package ai.remi.comm.feign.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Startdis
 * @email startdis@dianjiu.cc
 * @desc BadRequestException
 */
@Data
@NoArgsConstructor
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
