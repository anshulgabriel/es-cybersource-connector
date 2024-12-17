package au.com.escybersourceconnector.validators;

import au.com.es.paymentsassist.enums.Entity;
import au.com.es.paymentsassist.enums.Operation;
import au.com.es.salad.validators.FieldValidationUtils;
import au.com.escybersourceconnector.annotations.OperationComponent;
import au.com.escybersourceconnector.models.request.CyberSourceRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@OperationComponent(entity = Entity.TRANSACTION,
        operation = {
                Operation.REVERSAL_TIMEOUT,
                Operation.REVERSAL
        })
@RequiredArgsConstructor
@Slf4j
public class ReversalValidator implements Validator {

        @Override
        public boolean supports(@NonNull final Class<?> clazz) {
                return CyberSourceRequest.class.isAssignableFrom(clazz);
        }

        @Override
        public void validate(@NonNull final Object target,
                             @NonNull final Errors errors) {
                log.info("Validating Surcharge list request");
                final CyberSourceRequest request = (CyberSourceRequest) target;
                nullOrEmptyCheckRetrieveMany(request, errors);

        }

        private void nullOrEmptyCheckRetrieveMany(final CyberSourceRequest request,
                                                  final Errors errors) {
                FieldValidationUtils.nullOrEmptyCheck(
                        errors,
                        request.getClientReferenceInformation(),
                        "clientReferenceInformation"
                );
                FieldValidationUtils.nullOrEmptyCheck(
                        errors,
                        request.getReversalInformation(),
                        "reversalInformation"
                );
        }
}