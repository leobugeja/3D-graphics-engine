package renderer;


import input.Move;

import renderer.geometry.primitives.Matrix;
import renderer.geometry.primitives.Vec3d;

public class Camera{

    private static double x = 0;
    private static double y = 0;
    private static double z = 0;

    public static double pitch = 0;
    public static double yaw = 0;
    public static Vec3d normal = new Vec3d();

    private double move_speed = 30; // meters per second

    private static Vec3d screen_center;

    private double a_ratio, fov, z_near, z_far;
    private static Matrix proj_mat;

    private static Matrix rot_mat;


    public static void main(String[] args) {
        Camera cam = new Camera(90, 0.1, 1000);
        Vec3d a = projectPoint(new Vec3d(0, 100, 100));

    }

    public Camera(double fov, double z_near, double z_far) {
        Camera.screen_center = new Vec3d(Display.WIDTH/2.0, Display.HEIGHT/2.0, 0);
        this.a_ratio = ((double) Display.HEIGHT)/Display.WIDTH;
        this.fov = fov;
        this.z_near = z_near;
        this.z_far = z_far;

        double S = 1/Math.tan( (fov/2.0) * (Math.PI/180.0) );
//        proj_mat = new Matrix(new double[][]{
//            {a_ratio*S, 0, 0                           , 0},
//            {0        , S, 0                           , 0},
//            {0        , 0, z_far/(z_far-z_near)        , 1},
//            {0        , 0, -z_far*z_near/(z_far-z_near), 0} });


        proj_mat = new Matrix(new double[][]{
            {a_ratio*S, 0, 0                            , 0},
            {0        , S, 0                            , 0},
            {0        , 0, (z_near+z_far)/(z_near-z_far), 2*z_near*z_far/(z_near-z_far)},
            {0        , 0, -1                           , 0} });


    }

        private static Matrix rotateMatX(double degrees) {
        double rad = Math.PI*degrees/180;
        return new Matrix(new double[][]{
                {1, 0, 0, 0},
                {0, Math.cos(rad),-Math.sin(rad), 0},
                {0, Math.sin(rad),Math.cos(rad), 0},
                {0            , 0,              0, 1}
        });
    }

    private static Matrix rotateMatY(double degrees) {
        double rad = Math.PI*degrees/180;
        return new Matrix(new double[][]{
                {Math.cos(rad), 0, -Math.sin(rad), 0},
                {0            , 1,              0, 0},
                {Math.sin(rad), 0,  Math.cos(rad), 0},
                {0            , 0,              0, 1}
        });

    }

    public static Vec3d[] projectTri2D(Vec3d[] points) {
        Vec3d[] new_points = new Vec3d[3];
        for (int i = 0; i < 3; i++) {
            new_points[i] = projectPoint(points[i]);
        }
        return centerCoordinates(new_points);
    }

    private static Vec3d projectPoint(Vec3d point) {

        Matrix point4D = new Matrix(new double[][]{ {point.x - Camera.x}, {point.y - Camera.y}, {point.z - Camera.z}, {1} });
        Matrix proj_point4D = Matrix.multiply(Camera.rotateMatY(Camera.yaw),point4D); // TODO don't need to recalculate rotation matrix each time, plus can combine together all matrix into 1
        proj_point4D = Matrix.multiply(Camera.rotateMatX(Camera.pitch), proj_point4D);

        proj_point4D = Matrix.multiply(Camera.proj_mat, proj_point4D);

        double w = proj_point4D.extractElement(3,0);
        Vec3d proj_point3D = new Vec3d(proj_point4D.extractElement(0,0)/w, proj_point4D.extractElement(1,0)/w, proj_point4D.extractElement(2,0)/w);
        // Scaling
//        proj_point3D.x += 1;
//        proj_point3D.y += 1;
        proj_point3D.x *= -0.5*Display.WIDTH;
        proj_point3D.y *= -0.5*Display.HEIGHT;

        return proj_point3D;
    }


    public static Vec3d[] centerCoordinates(Vec3d[] points) {
        Vec3d[] centered_points = new Vec3d[points.length];
        for (int i = 0; i < points.length; i++) {
            centered_points[i] = Vec3d.plus(points[i], Camera.screen_center);
        }
        return centered_points;

    }

    public void move(Move movement) {
        if (movement == Move.Forward) {
//            Camera.z += this.move_speed/(Display.fps+1);
            Camera.z += Math.cos(this.yaw*Math.PI/180)*this.move_speed/60;
            Camera.x += Math.sin(this.yaw*Math.PI/180)*this.move_speed/60;
        }
        else if (movement == Move.Backward) {
//            Camera.z -= this.move_speed/(Display.fps+1);
            Camera.z -= Math.cos(this.yaw*Math.PI/180)*this.move_speed/60;
            Camera.x -= Math.sin(this.yaw*Math.PI/180)*this.move_speed/60;
        }
        else if (movement == Move.Left) {
//            Camera.x -= this.move_speed/(Display.fps+1);
            Camera.x -= Math.cos(this.yaw*Math.PI/180)*this.move_speed/60;
            Camera.z += Math.sin(this.yaw*Math.PI/180)*this.move_speed/60;
        }
        else if (movement == Move.Right) {
//            Camera.x += this.move_speed/(Display.fps+1);
            Camera.x += Math.cos(this.yaw*Math.PI/180)*this.move_speed/60;
            Camera.z -= Math.sin(this.yaw*Math.PI/180)*this.move_speed/60;
        }
        else if (movement == Move.Up) {
//            Camera.y -= this.move_speed/(Display.fps+1);
            Camera.y -= this.move_speed/60;
        }
        else if (movement == Move.Down) {
//            Camera.y += this.move_speed/(Display.fps+1);
            Camera.y += this.move_speed/60;
        }

//        System.out.printf("x:%.2f y:%.2f z:%.2f yaw:%.2f pitch: %.2f \n", Camera.x, Camera.y, Camera.z, Camera.yaw, Camera.pitch);

    }

    public void updateNormal() {
        this.normal.x = Math.sin(this.yaw*Math.PI/180)*Math.cos(this.pitch*Math.PI/180);
        this.normal.y = Math.sin(this.pitch*Math.PI/180);
        this.normal.z = Math.cos(this.yaw*Math.PI/180)*Math.cos(this.pitch*Math.PI/180);
    }
}
