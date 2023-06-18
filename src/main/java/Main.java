import Engine.*;
import Engine.Object;
import org.joml.*;
import org.lwjgl.opengl.GL;

import java.io.IOException;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL30.*;

public class Main {
    private Window window = new Window(1080, 1080, "Hello World");
    ArrayList<Object> objectObj = new ArrayList<>();
    ArrayList<Object> objectGround = new ArrayList<>();
    ArrayList<Object> objectEllips = new ArrayList<>();
    ArrayList<Object> objectTrack = new ArrayList<>();
    ArrayList<Object> objectOuterWall = new ArrayList<>();
    Camera camera = new Camera();
    Projection projection = new Projection(window.getWidth(), window.getHeight());
    float distance = 1f;
    float angle = 0f;
    float rotation = (float)Math.toRadians(1f);
    float move = 0.01f;
    List<Float> temp;


    public void run() throws IOException {

        init();
        loop();

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public void init() throws IOException {
        window.init();
        GL.createCapabilities();
        camera.setPosition(0, 1f, distance);
        camera.setRotation((float) Math.toRadians(0.0f), (float) Math.toRadians(0.0f));

        // mobil (ObjectObj(0))
        objectObj.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(1.0f,0.0f,0.0f,1.0f),
                "resources/model/mobil/mobil.obj"
        ));
        objectObj.get(0).scaleObject(0.1f,0.1f,0.1f);

        objectObj.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.0f,0.0f,1.0f,1.0f),
                "resources/model/mobil/mobil.obj"
        ));
        objectObj.get(1).scaleObject(0.1f,0.1f,0.1f);
        objectObj.get(1).translateObject(1f, 0f, 0f);

        // ground (objectGround(0))
        objectGround.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.01f,0.59f,0.17f,1.0f),
                "resources/model/track/Terrain_Grass_Flat_1x1.obj"
        ));
        objectGround.get(0).scaleObject(20f ,1f, 20f);
        objectGround.get(0).translateObject(0f, -0.565f, 0f);

        // matahari (objectEllips(0))
        objectEllips.add(new Object(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.01f,0.59f,0.17f,1.0f)
        ));
        objectEllips.get(0).createEllipsoid();
        objectEllips.get(0).translateObject(0f,3f,0f);

        //Track
        objectTrack.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.5f,0.5f,0.5f,1.0f),
                "resources/model/track/Terrain_Grass_Flat_1x1.obj"
        ));
        objectTrack.get(0).translateObject(0f,-0.564f,0f);

        objectTrack.get(0).getChildObject().add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.5f,0.5f,0.5f,1.0f),
                "resources/model/track/Terrain_Grass_Flat_1x1.obj"
        ));
        objectTrack.get(0).getChildObject().get(0).translateObject(0.0f,-0.564f,-1.0f);

        objectTrack.get(0).getChildObject().add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.5f,0.5f,0.5f,1.0f),
                "resources/model/track/Terrain_Grass_Flat_1x1.obj"
        ));
        objectTrack.get(0).getChildObject().get(1).translateObject(.0f,-0.564f,-2.0f);

        objectTrack.get(0).getChildObject().add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.5f,0.5f,0.5f,1.0f),
                "resources/model/track/Terrain_Grass_Flat_1x1.obj"
        ));
        objectTrack.get(0).getChildObject().get(2).translateObject(.0f,-0.564f,-3.0f);


        objectTrack.get(0).getChildObject().add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.5f,0.5f,0.5f,1.0f),
                "resources/model/track/Terrain_Grass_Flat_1x1.obj"
        ));
        objectTrack.get(0).getChildObject().get(3).translateObject(.0f,-0.564f,-4.0f);

        objectTrack.get(0).getChildObject().add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.5f,0.5f,0.5f,1.0f),
                "resources/model/track/Terrain_Grass_Flat_1x1.obj"
        ));
        objectTrack.get(0).getChildObject().get(4).translateObject(.0f,-0.564f,-5.0f);

        objectTrack.get(0).getChildObject().add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.5f,0.5f,0.5f,1.0f),
                "resources/model/track/Terrain_Grass_Flat_1x1.obj"
        ));
        objectTrack.get(0).getChildObject().get(5).translateObject(.0f,-0.564f,-6.0f);

        objectTrack.get(0).getChildObject().add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.5f,0.5f,0.5f,1.0f),
                "resources/model/track/Terrain_Grass_Flat_1x1.obj"
        ));
        objectTrack.get(0).getChildObject().get(6).translateObject(.0f,-0.564f,-7.0f);

        objectTrack.get(0).getChildObject().add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.5f,0.5f,0.5f,1.0f),
                "resources/model/track/Terrain_Grass_Flat_1x1.obj"
        ));
        objectTrack.get(0).getChildObject().get(7).translateObject(3.19f,-0.555f,-5.6f);
        objectTrack.get(0).getChildObject().get(7).rotateObject((float) Math.toRadians(1.0f),0f,30.0f,0f);


        objectTrack.get(0).getChildObject().add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.5f,0.5f,0.5f,1.0f),
                "resources/model/track/Terrain_Grass_Flat_1x1.obj"
        ));
        objectTrack.get(0).getChildObject().get(8).translateObject(1.0f,-0.564f,-7.53f);

        objectTrack.get(0).getChildObject().add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.5f,0.5f,0.5f,1.0f),
                "resources/model/track/Terrain_Grass_Flat_1x1.obj"
        ));
        objectTrack.get(0).getChildObject().get(9).translateObject(2.0f,-0.564f,-7.53f);

        objectTrack.get(0).getChildObject().add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.5f,0.5f,0.5f,1.0f),
                "resources/model/track/Terrain_Grass_Flat_1x1.obj"
        ));
        objectTrack.get(0).getChildObject().get(10).translateObject(-0.83f,-0.555f,-6.83f);
        objectTrack.get(0).getChildObject().get(10).rotateObject((float) Math.toRadians(1.0f),0f,-30.0f,0f);

        objectTrack.get(0).getChildObject().add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.5f,0.5f,0.5f,1.0f),
                "resources/model/track/Terrain_Grass_Flat_1x1.obj"
        ));
        objectTrack.get(0).getChildObject().get(11).translateObject(3.0f,-0.564f,-7.0f);

        objectTrack.get(0).getChildObject().add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.5f,0.5f,0.5f,1.0f),
                "resources/model/track/Terrain_Grass_Flat_1x1.obj"
        ));
        objectTrack.get(0).getChildObject().get(12).translateObject(3.0f,-0.564f,-6.0f);

        objectTrack.get(0).getChildObject().add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.5f,0.5f,0.5f,1.0f),
                "resources/model/track/Terrain_Grass_Flat_1x1.obj"
        ));
        objectTrack.get(0).getChildObject().get(13).translateObject(3.0f,-0.564f,-5.0f);

        objectTrack.get(0).getChildObject().add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.5f,0.5f,0.5f,1.0f),
                "resources/model/track/Terrain_Grass_Flat_1x1.obj"
        ));
        objectTrack.get(0).getChildObject().get(14).translateObject(3.0f,-0.564f,-4.0f);

        objectTrack.get(0).getChildObject().add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.5f,0.5f,0.5f,1.0f),
                "resources/model/track/Terrain_Grass_Flat_1x1.obj"
        ));
        objectTrack.get(0).getChildObject().get(15).translateObject(3.0f,-0.564f,-3.0f);

        objectTrack.get(0).getChildObject().add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.5f,0.5f,0.5f,1.0f),
                "resources/model/track/Terrain_Grass_Flat_1x1.obj"
        ));
        objectTrack.get(0).getChildObject().get(16).translateObject(3.0f,-0.564f,-2.0f);

        objectTrack.get(0).getChildObject().add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.5f,0.5f,0.5f,1.0f),
                "resources/model/track/Terrain_Grass_Flat_1x1.obj"
        ));
        objectTrack.get(0).getChildObject().get(17).translateObject(3.0f,-0.564f,-1.0f);

        objectTrack.get(0).getChildObject().add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.5f,0.5f,0.5f,1.0f),
                "resources/model/track/Terrain_Grass_Flat_1x1.obj"
        ));
        objectTrack.get(0).getChildObject().get(18).translateObject(3.0f,-0.564f,-.0f);

        objectTrack.get(0).getChildObject().add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.5f,0.5f,0.5f,1.0f),
                "resources/model/track/Terrain_Grass_Flat_1x1.obj"
        ));
        objectTrack.get(0).getChildObject().get(19).translateObject(2.04f,-0.555f,1.34f);
        objectTrack.get(0).getChildObject().get(19).rotateObject((float) Math.toRadians(1.0f),0f,30.0f,0f);

        objectTrack.get(0).getChildObject().add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.5f,0.5f,0.5f,1.0f),
                "resources/model/track/Terrain_Grass_Flat_1x1.obj"
        ));
        objectTrack.get(0).getChildObject().get(20).translateObject(2.0f,-0.564f,0.537f);

        objectTrack.get(0).getChildObject().add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.5f,0.5f,0.5f,1.0f),
                "resources/model/track/Terrain_Grass_Flat_1x1.obj"
        ));
        objectTrack.get(0).getChildObject().get(21).translateObject(1.0f,-0.564f,0.537f);

        objectTrack.get(0).getChildObject().add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.5f,0.5f,0.5f,1.0f),
                "resources/model/track/Terrain_Grass_Flat_1x1.obj"
        ));
        objectTrack.get(0).getChildObject().get(22).translateObject(0.318f,-0.555f,0.11f);
        objectTrack.get(0).getChildObject().get(22).rotateObject((float) Math.toRadians(1.0f),0f,-30.0f,0f);


        //OUTER WALL
        //kiri
        objectOuterWall.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.f,0.f,0.5f,1.0f),
                "resources/model/track/Terrain_Grass_Flat_1x1.obj"

        ));

        objectOuterWall.get(0).translateObject(-0.02f,-1.0f,-0.435f);
        objectOuterWall.get(0).scaleObject(1.0f,1.0f,8.1f);
        objectOuterWall.get(0).rotateObject((float)Math.toRadians(90.0f),.0f,.0f,-1.0f);

        //kanan
        objectOuterWall.get(0).getChildObject().add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.f,0.f,0.5f,1.0f),
                "resources/model/track/Terrain_Grass_Flat_1x1.obj"
        ));

        objectOuterWall.get(0).getChildObject().get(0).translateObject(-0.02f,3.0f,-0.435f);
        objectOuterWall.get(0).getChildObject().get(0).scaleObject(1.0f,1.0f,8.1f);
        objectOuterWall.get(0).getChildObject().get(0).rotateObject((float) Math.toRadians(90.0f),0f,0.0f,-1.0f);


        //sudut kiri atas
        objectOuterWall.get(0).getChildObject().add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.f,0.f,0.5f,1.0f),
                "resources/model/track/Terrain_Grass_Flat_1x1.obj"
        ));

        objectOuterWall.get(0).getChildObject().get(1).translateObject(-0.02f,-7.4f,-3.18f);
        objectOuterWall.get(0).getChildObject().get(1).scaleObject(1.0f,1.0f,1.1f);
        objectOuterWall.get(0).getChildObject().get(1).rotateObject((float) Math.toRadians(90.0f),0f,0.0f,-1.0f);
        objectOuterWall.get(0).getChildObject().get(1).rotateObject((float) Math.toRadians(63.0f),0f,-1.0f,.0f);


        //atas
        objectOuterWall.get(0).getChildObject().add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.f,0.f,0.5f,1.0f),
                "resources/model/track/Terrain_Grass_Flat_1x1.obj"
        ));

        objectOuterWall.get(0).getChildObject().get(2).translateObject(0.7f,-8.48f,-0.02f);
        objectOuterWall.get(0).getChildObject().get(2).scaleObject(2.2f,1.0f,1.0f);
        objectOuterWall.get(0).getChildObject().get(2).rotateObject((float) Math.toRadians(90.0f),1.0f,0.0f,.0f);

        //bawah
        objectOuterWall.get(0).getChildObject().add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),new Vector4f(0.f,0.f,0.5f,1.0f),
                "resources/model/track/Terrain_Grass_Flat_1x1.obj"
        ));

        objectOuterWall.get(0).getChildObject().get(3).translateObject(0.7f,0.5f,-0.02f);
        objectOuterWall.get(0).getChildObject().get(3).scaleObject(2.2f,1.0f,1.0f);
        objectOuterWall.get(0).getChildObject().get(3).rotateObject((float) Math.toRadians(90.0f),1.0f,0.0f,.0f);



        //sudut kanan atas
        objectOuterWall.get(0).getChildObject().add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.f,0.f,0.5f,1.0f),
                "resources/model/track/Terrain_Grass_Flat_1x1.obj"
        ));

        objectOuterWall.get(0).getChildObject().get(4).translateObject(-0.02f,7.77f,-0.7f);
        objectOuterWall.get(0).getChildObject().get(4).scaleObject(1.0f,1.0f,1.1f);
        objectOuterWall.get(0).getChildObject().get(4).rotateObject((float) Math.toRadians(90.0f),0f,0.0f,-1.0f);
        objectOuterWall.get(0).getChildObject().get(4).rotateObject((float) Math.toRadians(-63.0f),0f,-1.0f,.0f);


        //sudut kanan bawah
        objectOuterWall.get(0).getChildObject().add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.f,0.f,0.5f,1.0f),
                "resources/model/track/Terrain_Grass_Flat_1x1.obj"
        ));

        objectOuterWall.get(0).getChildObject().get(5).translateObject(-0.02f,1.53f,-2.13f);
        objectOuterWall.get(0).getChildObject().get(5).scaleObject(1.0f,1.0f,1.1f);
        objectOuterWall.get(0).getChildObject().get(5).rotateObject((float) Math.toRadians(90.0f),0f,0.0f,-1.0f);
        objectOuterWall.get(0).getChildObject().get(5).rotateObject((float) Math.toRadians(63.0f),0f,-1.0f,.0f);


        //sudut kiri bawah
        objectOuterWall.get(0).getChildObject().add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.f,0.f,0.5f,1.0f),
                "resources/model/track/Terrain_Grass_Flat_1x1.obj"
        ));

        objectOuterWall.get(0).getChildObject().get(6).translateObject(-0.02f,-1.17f,0.302f);
        objectOuterWall.get(0).getChildObject().get(6).scaleObject(1.0f,1.0f,1.1f);
        objectOuterWall.get(0).getChildObject().get(6).rotateObject((float) Math.toRadians(90.0f),0f,0.0f,-1.0f);
        objectOuterWall.get(0).getChildObject().get(6).rotateObject((float) Math.toRadians(-63.0f),0f,-1.0f,.0f);

        objectOuterWall.get(0).translateObject(-1.5f, 0f, 3.5f);
        objectTrack.get(0).translateObject(-1.5f, 0f, 3.5f);

        objectTrack.get(0).translateObject(0f, 1f, 0f);
        objectObj.get(0).translateObject(0f, 1f, 0f);
        objectObj.get(1).translateObject(0f, 1f, 0f);
        objectGround.get(0).translateObject(0f, 1f, 0f);
        objectOuterWall.get(0).translateObject(0f, 1f, 0f);
    }

    public void input(){
        temp = objectObj.get(0).getCenterPoint();
        angle = angle % (float)Math.toRadians(360);

        if (window.isKeyPressed(GLFW_KEY_W)) {
            objectObj.get(0).translateObject(0f, 0f, -move);
            camera.setPosition(temp.get(0), temp.get(1), temp.get(2));
            camera.moveBackwards(distance);
            if (angle > (float)Math.toRadians(0) && angle < (float)Math.toRadians(180)){
                objectObj.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                objectObj.get(0).rotateObject(-rotation, 0f, 1f, 0f);
                objectObj.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
                angle = angle - rotation;
            } else if (angle > (float)Math.toRadians(180) && angle < (float)Math.toRadians(360)){
                objectObj.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                objectObj.get(0).rotateObject(rotation, 0f, 1f, 0f);
                objectObj.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
                angle = angle + rotation;
            } else if (angle > (float)Math.toRadians(-180) && angle < (float)Math.toRadians(0)){
                objectObj.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                objectObj.get(0).rotateObject(rotation, 0f, 1f, 0f);
                objectObj.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
                angle = angle + rotation;
            } else if (angle > (float)Math.toRadians(-360) && angle < (float)Math.toRadians(-180)){
                objectObj.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                objectObj.get(0).rotateObject(-rotation, 0f, 1f, 0f);
                objectObj.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
                angle = angle - rotation;
            }
        }

        if (window.isKeyPressed(GLFW_KEY_A)) {
            objectObj.get(0).translateObject(-move, 0f, 0f);
            camera.setPosition(temp.get(0), temp.get(1), temp.get(2));
            camera.moveBackwards(distance);
            if (angle > (float)Math.toRadians(90) && angle < (float)Math.toRadians(270)){
                objectObj.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                objectObj.get(0).rotateObject(-rotation, 0f, 1f, 0f);
                objectObj.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
                angle = angle - rotation;
            } else if (angle > (float)Math.toRadians(270) && angle < (float)Math.toRadians(450)){
                objectObj.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                objectObj.get(0).rotateObject(rotation, 0f, 1f, 0f);
                objectObj.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
                angle = angle + rotation;
            } else if (angle > (float)Math.toRadians(-90) && angle < (float)Math.toRadians(90)){
                objectObj.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                objectObj.get(0).rotateObject(rotation, 0f, 1f, 0f);
                objectObj.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
                angle = angle + rotation;
            } else if (angle > (float)Math.toRadians(-270) && angle < (float)Math.toRadians(-90)){
                objectObj.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                objectObj.get(0).rotateObject(-rotation, 0f, 1f, 0f);
                objectObj.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
                angle = angle - rotation;
            } else if (angle > (float)Math.toRadians(-360) && angle < (float)Math.toRadians(-270)){
                objectObj.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                objectObj.get(0).rotateObject(rotation, 0f, 1f, 0f);
                objectObj.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
                angle = angle + rotation;
            }
        }

        if (window.isKeyPressed(GLFW_KEY_S)) {
            objectObj.get(0).translateObject(0f, 0f, move);
            camera.setPosition(temp.get(0), temp.get(1), temp.get(2));
            camera.moveBackwards(distance);
            if (angle > (float)Math.toRadians(180) && angle < (float)Math.toRadians(360)){
                objectObj.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                objectObj.get(0).rotateObject(-rotation, 0f, 1f, 0f);
                objectObj.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
                angle = angle - rotation;
            } else if (angle > (float)Math.toRadians(0) && angle < (float)Math.toRadians(180)){
                objectObj.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                objectObj.get(0).rotateObject(rotation, 0f, 1f, 0f);
                objectObj.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
                angle = angle + rotation;
            } else if (angle > (float)Math.toRadians(-360) && angle < (float)Math.toRadians(-180)){
                objectObj.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                objectObj.get(0).rotateObject(rotation, 0f, 1f, 0f);
                objectObj.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
                angle = angle + rotation;
            } else if (angle > (float)Math.toRadians(-180) && angle < (float)Math.toRadians(0)){
                objectObj.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                objectObj.get(0).rotateObject(-rotation, 0f, 1f, 0f);
                objectObj.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
                angle = angle - rotation;
            }
        }

        if (window.isKeyPressed(GLFW_KEY_D)) {
            objectObj.get(0).translateObject(move, 0f, 0f);
            camera.setPosition(temp.get(0), temp.get(1), temp.get(2));
            camera.moveBackwards(distance);
            if (angle > (float)Math.toRadians(-90) && angle < (float)Math.toRadians(90)){
                objectObj.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                objectObj.get(0).rotateObject(-rotation, 0f, 1f, 0f);
                objectObj.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
                angle = angle - rotation;
            } else if (angle > (float)Math.toRadians(90) && angle < (float)Math.toRadians(270)){
                objectObj.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                objectObj.get(0).rotateObject(rotation, 0f, 1f, 0f);
                objectObj.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
                angle = angle + rotation;
            } else if (angle > (float)Math.toRadians(270) && angle < (float)Math.toRadians(360)){
                objectObj.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                objectObj.get(0).rotateObject(-rotation, 0f, 1f, 0f);
                objectObj.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
                angle = angle - rotation;
            } else if (angle > (float)Math.toRadians(-270) && angle < (float)Math.toRadians(-90)){
                objectObj.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                objectObj.get(0).rotateObject(rotation, 0f, 1f, 0f);
                objectObj.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
                angle = angle + rotation;
            } else if (angle > (float)Math.toRadians(-450) && angle < (float)Math.toRadians(-270)){
                objectObj.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                objectObj.get(0).rotateObject(-rotation, 0f, 1f, 0f);
                objectObj.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
                angle = angle - rotation;
            }
        }

        if (window.isKeyPressed(GLFW_KEY_UP)) {
            camera.moveForward(distance);
            camera.addRotation(-0.01f, 0f);
            camera.moveBackwards(distance);
        }
        if (window.isKeyPressed(GLFW_KEY_DOWN)) {
            camera.moveForward(distance);
            camera.addRotation(0.01f, 0f);
            camera.moveBackwards(distance);
        }
        if (window.isKeyPressed(GLFW_KEY_LEFT)) {
            camera.moveForward(distance);
            camera.addRotation(0f, -0.01f);
            camera.moveBackwards(distance);
        }
        if (window.isKeyPressed(GLFW_KEY_RIGHT)) {
            camera.moveForward(distance);
            camera.addRotation(0f, 0.01f);
            camera.moveBackwards(distance);
        }

        if (window.getMouseInput().isRightButtonPressed()) {
            camera.moveForward(distance);
            Vector2f displVec = window.getMouseInput().getDisplVec();
            camera.addRotation((float) Math.toRadians(displVec.x * 0.1f), (float) Math.toRadians(displVec.y * 0.1f));
            camera.moveBackwards(distance);
        }

        if (window.getMouseInput().getScroll().y != 0){
            projection.setFOV(projection.getFOV()-(window.getMouseInput().getScroll().y * 0.1f));
            window.getMouseInput().setScroll(new Vector2f());
        }

        if(window.isKeyPressed(GLFW_KEY_SPACE)){
            objectObj.get(0).translateObject(0f, move, 0f);
            camera.setPosition(temp.get(0), temp.get(1), temp.get(2));
            camera.moveBackwards(distance);
        }

        if(window.isKeyPressed(GLFW_KEY_LEFT_SHIFT)){
            objectObj.get(0).translateObject(0f, -move, 0f);
            camera.setPosition(temp.get(0), temp.get(1), temp.get(2));
            camera.moveBackwards(distance);
        }
    }

    public void loop() {
        while (window.isOpen()) {
            window.update();
            glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
            GL.createCapabilities();

            input();

            // code here
            for (Object object: objectObj) {
                object.draw(camera, projection);
            }

            for (Object object: objectGround) {
                object.draw(camera, projection);
            }

            for (Object object: objectEllips) {
                object.drawEllips(camera, projection);
            }

            for (Object object: objectTrack){
                object.draw(camera, projection);
            }

            for (Object object: objectOuterWall){
                object.draw(camera, projection);
            }

            // Restore state
            glDisableVertexAttribArray(0);
            // Poll for window events.
            // The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }

    public static void main(String[] args) throws IOException {
        new Main().run();
    }
}
