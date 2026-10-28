package gr.automation.platform.utils.factory;

import gr.automation.platform.utils.Utils;
import gr.automation.platform.services.users.UserRest;
import gr.automation.platform.services.documents.DocumentRest;

public class ServiceObjectFactory {

    private final Utils utils;

    private UserRest userRest;
    private DocumentRest documentRest;

    public ServiceObjectFactory(Utils utils) {
        this.utils = utils;
    }

    public UserRest userRest() {
        return (userRest == null)
                ? userRest = new UserRest(utils)
                : userRest;
    }

    public DocumentRest documentRest() {
        return (documentRest == null)
                ? documentRest = new DocumentRest(utils)
                : documentRest;
    }
}