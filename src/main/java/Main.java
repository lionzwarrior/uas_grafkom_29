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
        camera.setPosition(0, 0f, distance);
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
            camera.moveUp(move);
            objectObj.get(0).translateObject(0.0f, move, 0.0f);
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
