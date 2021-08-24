package renderer.geometry;

import java.util.Comparator;

class CameraDistanceComparator implements Comparator<Entity> {

    @Override
    public int compare(Entity o1, Entity o2) {
        if (o1.distanceToCamera() < o2.distanceToCamera())
            return 1;
        else if (o1.distanceToCamera() == o2.distanceToCamera())
            return 0;
        else
            return -1;
    }
}
