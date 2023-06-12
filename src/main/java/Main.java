import Engine.*;
import Engine.Object;
import org.joml.*;
import org.lwjgl.opengl.GL;

import java.io.IOException;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL30.*;

public class Main {
    private Window window = new Window(1080, 1080, "Hello World");
    ArrayList<Object> objectObj = new ArrayList<>();
    Camera camera = new Camera();
    Projection projection = new Projection(window.getWidth(), window.getHeight());
    float move2 = 1f;
    boolean press_r = false;
    float derajatCam_r = 0f;

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
        camera.setPosition(0, 0.5f, 2.0f);
        camera.setRotation((float) Math.toRadians(0.0f), (float) Math.toRadians(0.0f));

//        objectObj.add(new Object2d(Arrays.asList(
//                new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
//                new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
//        ),
//                new ArrayList<>(),
//                new Vector4f(1f, 1, 0, 1.0f), "resources/model/stall/stall.obj"));
//
//        objectObj.get(0).scale(0.5f, 0.5f, 0.5f);

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
    }

    public void input(){
        float move = 0.01f;
        if (window.isKeyPressed(GLFW_KEY_W)) {
            camera.moveForward(1.7f);
            camera.addRotation(0, (float)Math.toRadians(-derajatCam_r));
            camera.moveBackwards(1.7f);

            objectObj.get(0).translateObject(0.0f, 0.0f, -move);
            camera.moveForward(move);

            camera.moveForward(1.7f);
            camera.addRotation(0, (float)Math.toRadians(derajatCam_r));
            camera.moveBackwards(1.7f);
        }
        else if (window.isKeyPressed(GLFW_KEY_S)){
            camera.moveForward(1.7f);
            camera.addRotation(0, (float)Math.toRadians(-derajatCam_r));
            camera.moveBackwards(1.7f);

            objectObj.get(0).translateObject(0.0f, 0.0f, move);
            camera.moveBackwards(move);

            camera.moveForward(1.7f);
            camera.addRotation(0, (float)Math.toRadians(derajatCam_r));
            camera.moveBackwards(1.7f);
        }
        else if (window.isKeyPressed(GLFW_KEY_D)){
            camera.moveForward(1.7f);
            camera.addRotation(0, (float)Math.toRadians(-derajatCam_r));
            camera.moveBackwards(1.7f);

            objectObj.get(0).translateObject(move, 0.0f, 0.0f);
            camera.moveRight(move);

            camera.moveForward(1.7f);
            camera.addRotation(0, (float)Math.toRadians(derajatCam_r));
            camera.moveBackwards(1.7f);
        }
        else if (window.isKeyPressed(GLFW_KEY_A)){
            camera.moveForward(1.7f);
            camera.addRotation(0, (float)Math.toRadians(-derajatCam_r));
            camera.moveBackwards(1.7f);

            objectObj.get(0).translateObject(-move, 0.0f, 0.0f);
            camera.moveLeft(move);

            camera.moveForward(1.7f);
            camera.addRotation(0, (float)Math.toRadians(derajatCam_r));
            camera.moveBackwards(1.7f);
        }
        if(window.isKeyPressed(GLFW_KEY_UP)){
            camera.moveUp(move);
            objectObj.get(0).translateObject(0.0f, move, 0.0f);
        }
        else if(window.isKeyPressed(GLFW_KEY_DOWN)){
            camera.moveDown(move);
            objectObj.get(0).translateObject(0.0f, -move, 0.0f);
        }
        else if(window.isKeyPressed(GLFW_KEY_LEFT)){
            camera.addRotation(0.0f,0.05f);
        }
        else if(window.isKeyPressed(GLFW_KEY_RIGHT)){
            camera.addRotation(0.0f,-0.05f);
        }
        if (window.getMouseInput().isRightButtonPressed()){
            Vector2f displVec = window.getMouseInput().getDisplVec();
            camera.addRotation((float) Math.toRadians(displVec.x * 0.1f) , (float) Math.toRadians(displVec.y * 0.1f));
        }
        if (window.getMouseInput().getScroll().y != 0){
            projection.setFOV(projection.getFOV()-(window.getMouseInput().getScroll().y * 0.1f));
            window.getMouseInput().setScroll(new Vector2f());
        }


        if (window.isKeyPressed(GLFW_KEY_R)){
            press_r = true;
        }
        if (press_r){
            float posX = camera.getPosition().x;
            float posY = camera.getPosition().y;
            float posZ = camera.getPosition().z;
            camera.setPosition(-posX,-posY,-posZ);
            camera.addRotation(0.0f,(float) Math.toRadians(move2));
            camera.setPosition(posX,posY,posZ);
            derajatCam_r += move2;
            if(derajatCam_r >= 360.0){
                press_r = false;
                derajatCam_r = 0.0f;
            }
        }

        if (window.isKeyPressed(GLFW_KEY_Q)){
            camera.moveForward(1.7f);
            camera.addRotation(0, (float)Math.toRadians(-1));
            derajatCam_r+=-1;
            camera.moveBackwards(1.7f);
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
