import Engine.*;
import org.joml.*;
import org.lwjgl.opengl.GL;

import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL30.*;

public class Main {
    private Window window = new Window(1080, 1080, "Hello World");
    ArrayList<Object2d> objectObj = new ArrayList<>();
    Camera camera = new Camera();
    Projection projection = new Projection(window.getWidth(), window.getHeight());

    public void run() {

        init();
        loop();

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public void init() {
        window.init();
        GL.createCapabilities();
        camera.setPosition(0, 0.5f, 2.0f);
        camera.setRotation((float) Math.toRadians(0.0f), (float) Math.toRadians(0.0f));

        objectObj.add(new Object2d(Arrays.asList(
                new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
        ),
                new ArrayList<>(),
                new Vector4f(1f, 1, 0, 1.0f), "resources/model/stall/stall.obj"));

        objectObj.get(0).scale(0.5f, 0.5f, 0.5f);
    }

    public void input() {
        float move = 0.01f;
        if (window.isKeyPressed(GLFW_KEY_W)) {
            camera.moveForward(move);
        }
        if (window.isKeyPressed(GLFW_KEY_A)) {
            camera.moveLeft(move);
        }
        if (window.isKeyPressed(GLFW_KEY_S)) {
            camera.moveBackwards(move);
        }
        if (window.isKeyPressed(GLFW_KEY_D)) {
            camera.moveRight(move);
        }
        if (window.getMouseInput().isRightButtonPressed()) {
            Vector2f displVec = window.getMouseInput().getDisplVec();
            camera.addRotation((float) Math.toRadians(displVec.x * 0.1f), (float) Math.toRadians(displVec.y * 0.1f));
        }
        if (window.isKeyPressed(GLFW_KEY_SPACE)) {
            camera.moveUp(move);
        }
        if (window.isKeyPressed(GLFW_KEY_LEFT_SHIFT)) {
            camera.moveDown(move);
        }
        if (window.isKeyPressed(GLFW_KEY_UP)) {
            camera.addRotation(-0.01f, 0f);
        }
        if (window.isKeyPressed(GLFW_KEY_DOWN)) {
            camera.addRotation(0.01f, 0f);
        }
        if (window.isKeyPressed(GLFW_KEY_LEFT)) {
            camera.addRotation(0f, -0.01f);
        }
        if (window.isKeyPressed(GLFW_KEY_RIGHT)) {
            camera.addRotation(0f, 0.01f);
        }
    }

    public void loop() {
        while (window.isOpen()) {
            window.update();
            glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
            GL.createCapabilities();

            input();

            // code here
            for (Object2d object: objectObj) {
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

    public static void main(String[] args) {
        new Main().run();
    }
}
