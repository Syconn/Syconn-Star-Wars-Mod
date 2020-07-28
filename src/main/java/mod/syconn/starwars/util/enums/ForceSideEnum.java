package mod.syconn.starwars.util.enums;

public enum ForceSideEnum {

    JEDI("jedi", 0),
    BOTH("both", 1),
    SITH("sith", 2);

    private String name;
    private int ID;

    ForceSideEnum(String name, int ID) {
        this.name = name;
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return ID;
    }

    public static ForceSideEnum getForcePowerById(int id){
        for (ForceSideEnum side : ForceSideEnum.values()){
            if (side.ID == id){
                return side;
            }
        }
        return null;
    }
}
