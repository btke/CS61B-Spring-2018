import java.util.List;
import java.util.ArrayList;

public class Way {
    private long id;
    private List<Long> nodes;
    private String maxSpeed = "";

    public Way(long id) {
        this.id = id;
        nodes = new ArrayList<>();
    }

    public void addNode(long i) {
        nodes.add(i);
    }

    public List<Long> getNodes() {
        return nodes;
    }

    public void setMaxSpeed(String s) {
        maxSpeed = s;
    }

    public String getMaxSpeed() {
        return maxSpeed;
    }
}
