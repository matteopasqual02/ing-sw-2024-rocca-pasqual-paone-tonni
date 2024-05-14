package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view;

public enum EnumConnectionType {
    RMI, SOCKET;
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
