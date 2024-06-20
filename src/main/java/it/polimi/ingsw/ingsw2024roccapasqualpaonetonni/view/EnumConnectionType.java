package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view;

/**
 * The Enum connection type.
 */
public enum EnumConnectionType {
    /**
     * Rmi enum connection type.
     */
    RMI,
    /**
     * Socket enum connection type.
     */
    SOCKET;
    EnumConnectionType (){

    }

    /**
     * Get connection type enum connection type.
     *
     * @param s the s
     * @return the enum connection type
     */
    public EnumConnectionType getConnectionType(String s){
        switch (s){
            case "SOCKET"->{return SOCKET;}
            case "RMI"->{return RMI;}
        }
        return null;
    }
}
