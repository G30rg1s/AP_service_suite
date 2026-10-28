package gr.automation.platform.utils.factory;

import java.util.ArrayList;
import java.util.List;

public class StaticContextDataFactory {

    // Authentication
    public static String token;
    public static String username;

    // Users
    public static String userId;
    public static List<String> userIdsList = new ArrayList<>();
    public static List<String> userEmailList = new ArrayList<>();

    // Documents
    public static String documentId;
    public static List<String> documentIdsList = new ArrayList<>();

    public static void clearAll() {

        System.out.println("Start clearing context data");

        // Authentication
        token = null;
        username = null;

        // Users
        userId = null;
        userIdsList.clear();
        userEmailList.clear();

        // Documents
        documentId = null;
        documentIdsList.clear();
    }
}