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

    public EnumConnectionType getConnectionType(String s){
        switch (s){
            case "SOCKET"->{return SOCKET;}
            case "RMI"->{return RMI;}
        }
        return null;
    }
}
