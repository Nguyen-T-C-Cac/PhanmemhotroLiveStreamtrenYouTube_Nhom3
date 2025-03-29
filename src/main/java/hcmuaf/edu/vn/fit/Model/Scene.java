package hcmuaf.edu.vn.fit.Model;

import java.util.ArrayList;
import java.util.List;

public class Scene {

        private String sceneId;
        private String name;
        private List<Source> sources;

        public Scene(String sceneId, String name) {
            this.sceneId = sceneId;
            this.name = name;
            this.sources = new ArrayList<>();
        }

        public void addSource(Source source) {
            sources.add(source);
        }

        public void removeSource(Source source) {
            sources.remove(source);
        }

        public void switchScene() {
            System.out.println("Switched to scene: " + name);
        }
}
