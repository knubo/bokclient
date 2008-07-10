package no.knubo.bok.client.validation;

import no.knubo.bok.client.cache.Registry;

public class RegistryValidator extends ValidatorBase {

    private final Registry registry;

    public RegistryValidator(String errorText, Registry registry) {
        super(errorText);
        this.registry = registry;
    }

    protected boolean validate(Validateable val) {
        if (val.getText().length() == 0) {
            return true;
        }
        return registry.keyExists(val.getText());
    }
}
