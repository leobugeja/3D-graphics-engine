package renderer.geometry.primitives;

public class ClippingResultTri {
    private Vec3d[] out_tri1, out_tri2;
    public int num_clipped_triangles;

    ClippingResultTri(int num_in_points) {
        // All the points on on the outside of viewing fustrum
        this.num_clipped_triangles = 0;
//        if (this.num_inside_points != 0) {
//            if (this.num_inside_points == 4) {
//                throw new Exception("clipping oppertion error");
//            }
//            throw new Exception("number of inside points should be 0 for this call");
//        }
    }

    ClippingResultTri(int num_in_points, Vec3d[] out_tri) {
        // 1 or 3 points are inside the viewing fustrum
        this.num_clipped_triangles = 1;
//        if (this.num_inside_points != 1 || this.num_inside_points != 3) {
//            throw new Exception("number of inside points should either be 0 or 1 for this call");
//        }
        this.out_tri1 = out_tri;
    }
    ClippingResultTri(int num_in_points, Vec3d[] out_tri1, Vec3d[] out_tri2) {
        // 2 points are inside the viewing fustrum
        this.num_clipped_triangles = 2; // Should always be 2;
//        if (this.num_inside_points != 2) {
//            throw new Exception("number of inside points should be 2 for this call");
//        }

        this.out_tri1 = out_tri1;
        this.out_tri2 = out_tri2;
    }

    public Vec3d[] getOutTri(int index) {
        if (index == 1) {
            return out_tri1;
        }
        if (index == 2 ) {
            return out_tri2;
        }
        return out_tri1; // error
    }


}
