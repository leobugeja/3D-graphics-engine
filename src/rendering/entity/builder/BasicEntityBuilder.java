package rendering.entity.builder;

import rendering.entity.Entity;
import rendering.entity.IEntity;
import rendering.point.vec3d;
import rendering.shapes.MyPolygon;
import rendering.shapes.Tetrahedron;

import java.awt.*;
import java.util.ArrayList;

public class BasicEntityBuilder {

    public static IEntity createCube(double size, double centerX, double centerY, double centerZ) {
        int s = 200;
        vec3d p1 = new vec3d(centerX + size/2, centerY + -size/2, centerZ + -size/2);
        vec3d p2 = new vec3d(centerX + size/2, centerY + size/2, centerZ + -size/2);
        vec3d p3 = new vec3d(centerX + size/2, centerY + size/2, centerZ + size/2);
        vec3d p4 = new vec3d(centerX + size/2, centerY + -size/2, centerZ + size/2);
        vec3d p5 = new vec3d(centerX + -size/2, centerY + -size/2, centerZ + -size/2);
        vec3d p6 = new vec3d(centerX + -size/2, centerY + size/2, centerZ + -size/2);
        vec3d p7 = new vec3d(centerX + -size/2, centerY + size/2, centerZ + size/2);
        vec3d p8 = new vec3d(centerX + -size/2, centerY + -size/2, centerZ + size/2);
        Tetrahedron tetra = new Tetrahedron(
                new MyPolygon(Color.RED, p1, p2, p3, p4),
                new MyPolygon(Color.BLUE, p5, p6, p7, p8),
                new MyPolygon(Color.WHITE, p1, p2, p6, p5),
                new MyPolygon(Color.YELLOW, p1, p5, p8, p4),
                new MyPolygon(Color.GREEN, p2, p6, p7, p3),
                new MyPolygon(Color.ORANGE, p4, p3, p7, p8));

        ArrayList<Tetrahedron> tetras = new ArrayList<Tetrahedron>();
        tetras.add(tetra);
        return new Entity(tetras);

    }

    public static IEntity createDiamond(Color color, double size, double centerX, double centerY, double centerZ ) {
        ArrayList<Tetrahedron> tetras = new ArrayList<Tetrahedron>();

		int edges = 20;
		double inFactor = 0.8;
		vec3d bottom = new vec3d(centerX, centerY, centerZ - size/2);
		vec3d[] outerPoints = new vec3d[edges];
		vec3d[] innerPoints = new vec3d[edges];
		for(int i = 0; i < edges; i++) {
			double theta = 2 * Math.PI / edges * i;
			double xPos = -Math.sin(theta) * size/2;
			double yPos = Math.cos(theta) * size/2;
			double zPos = size/2;
			outerPoints[i] = new vec3d(centerX + xPos, centerY + yPos, centerZ + zPos * inFactor);
			innerPoints[i] = new vec3d(centerX + xPos * inFactor, centerY + yPos * inFactor, centerZ + zPos);
		}
		
		MyPolygon polygons[] = new MyPolygon[2 * edges + 1];
		for(int i = 0; i < edges; i++) {
			polygons[i] = new MyPolygon(outerPoints[i], bottom, outerPoints[(i+1)%edges]);
		}
		for(int i = 0; i < edges; i++) {
			polygons[i + edges] = new MyPolygon(outerPoints[i], outerPoints[(i+1)%edges], innerPoints[(i+1)%edges], innerPoints[i]);
		}
		polygons[edges * 2] = new MyPolygon(color, innerPoints);
		
		Tetrahedron tetra = new Tetrahedron(color, true, polygons);
		tetras.add(tetra);
		
		return new Entity(tetras);
    }

}
