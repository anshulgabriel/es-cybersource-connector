package au.com.escybersourceconnector.factories;

import au.com.es.paymentsassist.enums.Entity;
import au.com.es.paymentsassist.models.operationhandler.EntityOperation;
import au.com.escybersourceconnector.annotations.OperationComponent;
import au.com.escybersourceconnector.operationhandlers.OperationHandler;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class OperationComponentsFactory {

    private static final String KEY_PATTERN = "%s-%s";
    private final ApplicationContext applicationContext;
    private final Map<EntityOperation, Validator> operationValidators = new ConcurrentHashMap<>();
    private final Map<EntityOperation, OperationHandler> operationHandlers = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        final Map<String, Object> operationComponents = applicationContext
                .getBeansWithAnnotation(OperationComponent.class);

        operationComponents.forEach((beanName, bean) -> Optional
                .ofNullable(bean.getClass()
                        .getDeclaredAnnotation(OperationComponent.class))
                .ifPresent(operationComponent -> {

                    final Entity entity = operationComponent.entity();
                    final List<EntityOperation> entityOperations = Stream.of(operationComponent.operation())
                            .map(operation -> EntityOperation.of(
                                    entity,
                                    operation
                            ))
                            .toList();

                    if (bean instanceof Validator validatorBean) {

                        entityOperations.forEach(entityOperation -> operationValidators.put(
                                entityOperation,
                                validatorBean
                        ));
                    }

                    if (bean instanceof OperationHandler operationHandlerBean) {

                        entityOperations.forEach(entityOperation -> operationHandlers.put(
                                entityOperation,
                                operationHandlerBean
                        ));
                    }
                })
        );

    }

    public Validator validator(final EntityOperation entityOperation) {
        return operationValidators.get(entityOperation);
    }

    public OperationHandler operationHandler(final EntityOperation entityOperation) {
        return operationHandlers.get(entityOperation);
    }

}
