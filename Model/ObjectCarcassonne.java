package Model;

public class ObjectCarcassonne {
    public String typeTerrain;
    private static final String[] terrainPossibles = new String[]{"chemin" , "ville" , "prairie"};
    public Pion pion;


    public ObjectCarcassonne(String typeTerrain) {
        if (typeTerrain.equals(terrainPossibles[0]) || typeTerrain.equals(terrainPossibles[1]) || typeTerrain.equals(terrainPossibles[2])){
            this.typeTerrain = typeTerrain;
        }
    }

    @Override
    public boolean equals(Object o) {
        try {
            ObjectCarcassonne c = (ObjectCarcassonne) o;
            if (typeTerrain.equals(c.typeTerrain)){
                return true;
            }
        }catch (ClassCastException ignored){}
        return false;
    }

    public void setPion(Pion pion) {
        this.pion = pion;
    }
}
