package hcmuaf.edu.vn.fit.Model;

import java.util.HashMap;
import java.util.Map;

public abstract class Source {

    protected String sourceId;
    protected String type;
    protected Map<String, String> properties;

    public Source(String sourceId, String type) {
        this.sourceId = sourceId;
        this.type = type;
        this.properties = new HashMap<>();
    }

    public void configure() {
        System.out.println("Configuring source: " + sourceId);
    }

    public void start() {
        System.out.println("Starting source: " + sourceId);
    }

    public void stop() {
        System.out.println("Stopping source: " + sourceId);
    }
}
