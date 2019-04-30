package com.se.util;

public class TempUcUtil {
    public static final String uc18=
            "UC18 Maintain a hospital listing Use Case\n" +
            "\n" +
            "18.1 Preconditions:\n" +
            "\n" +
            "The administrator has authenticated himself or herself in the iTrust Medical Records system (UC2).\n" +
            "\n" +
            "18.2 Main Flow:\n" +
            "\n" +
            "An administrator chooses to maintain the hospital listing [S1].(Note: A personnel may be assigned to more than one (UC 2, S1))\n" +
            "\n" +
            "18.3 Sub-flows:\n" +
            "\n" +
            "[S1] The administrator will store (1) hospital Id number for the hospital [E1], (2) up to 30 alphanumeric characters giving the name of the hospital, and (3) an address for the hospital.\n" +
            "[S2]. The system shall enable the administrator to add a new entry for a hospital, or modify the hospital name in an existing entry. Note that the administrator is not allowed through the system interface to delete an existing entry or modify the hospital ID number in an existing entry.\n" +
            "18.4 Alternative Flows:\n" +
            "\n" +
            "[E1] The administrator types an invalid hospital ID and is prompted to try again.\n" +
            "[E2] The administrator types an invalid hospital name and is prompted to try again.";

}
