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
    Camera camera = new Camera();
    Projection projection = new Projection(window.getWidth(), window.getHeight());
    float distance = 1f;
    float angle = 0f;

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
        List<Float> temp = objectObj.get(0).getCenterPoint();
        float change = (float)Math.toRadians(1f);
        angle = angle % (float)Math.toRadians(360);

        if (window.isKeyPressed(GLFW_KEY_W)) {
            objectObj.get(0).translateObject(0f, 0f, -move);
            camera.setPosition(temp.get(0), temp.get(1), temp.get(2));
            camera.moveBackwards(distance);
            if (angle > (float)Math.toRadians(0) && angle < (float)Math.toRadians(180)){
                objectObj.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                objectObj.get(0).rotateObject(-change, 0f, 1f, 0f);
                objectObj.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
                angle = angle - change;
            } else if (angle > (float)Math.toRadians(180) && angle < (float)Math.toRadians(360)){
                objectObj.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                objectObj.get(0).rotateObject(change, 0f, 1f, 0f);
                objectObj.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
                angle = angle + change;
            } else if (angle > (float)Math.toRadians(-180) && angle < (float)Math.toRadians(0)){
                objectObj.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                objectObj.get(0).rotateObject(change, 0f, 1f, 0f);
                objectObj.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
                angle = angle + change;
            } else if (angle > (float)Math.toRadians(-360) && angle < (float)Math.toRadians(-180)){
                objectObj.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                objectObj.get(0).rotateObject(-change, 0f, 1f, 0f);
                objectObj.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
                angle = angle - change;
            }
        }

        if (window.isKeyPressed(GLFW_KEY_A)) {
            objectObj.get(0).translateObject(-move, 0f, 0f);
            camera.setPosition(temp.get(0), temp.get(1), temp.get(2));
            camera.moveBackwards(distance);
            if (angle > (float)Math.toRadians(90) && angle < (float)Math.toRadians(270)){
                objectObj.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                objectObj.get(0).rotateObject(-change, 0f, 1f, 0f);
                objectObj.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
                angle = angle - change;
            } else if (angle > (float)Math.toRadians(270) && angle < (float)Math.toRadians(450)){
                objectObj.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                objectObj.get(0).rotateObject(change, 0f, 1f, 0f);
                objectObj.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
                angle = angle + change;
            } else if (angle > (float)Math.toRadians(-90) && angle < (float)Math.toRadians(90)){
                objectObj.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                objectObj.get(0).rotateObject(change, 0f, 1f, 0f);
                objectObj.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
                angle = angle + change;
            } else if (angle > (float)Math.toRadians(-270) && angle < (float)Math.toRadians(-90)){
                objectObj.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                objectObj.get(0).rotateObject(-change, 0f, 1f, 0f);
                objectObj.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
                angle = angle - change;
            } else if (angle > (float)Math.toRadians(-360) && angle < (float)Math.toRadians(-270)){
                objectObj.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                objectObj.get(0).rotateObject(change, 0f, 1f, 0f);
                objectObj.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
                angle = angle + change;
            }
        }

        if (window.isKeyPressed(GLFW_KEY_S)) {
            objectObj.get(0).translateObject(0f, 0f, move);
            camera.setPosition(temp.get(0), temp.get(1), temp.get(2));
            camera.moveBackwards(distance);
            if (angle > (float)Math.toRadians(180) && angle < (float)Math.toRadians(360)){
                objectObj.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                objectObj.get(0).rotateObject(-change, 0f, 1f, 0f);
                objectObj.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
                angle = angle - change;
            } else if (angle > (float)Math.toRadians(0) && angle < (float)Math.toRadians(180)){
                objectObj.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                objectObj.get(0).rotateObject(change, 0f, 1f, 0f);
                objectObj.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
                angle = angle + change;
            } else if (angle > (float)Math.toRadians(-360) && angle < (float)Math.toRadians(-180)){
                objectObj.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                objectObj.get(0).rotateObject(change, 0f, 1f, 0f);
                objectObj.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
                angle = angle + change;
            } else if (angle > (float)Math.toRadians(-180) && angle < (float)Math.toRadians(0)){
                objectObj.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                objectObj.get(0).rotateObject(-change, 0f, 1f, 0f);
                objectObj.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
                angle = angle - change;
            }
        }

        if (window.isKeyPressed(GLFW_KEY_D)) {
            objectObj.get(0).translateObject(move, 0f, 0f);
            camera.setPosition(temp.get(0), temp.get(1), temp.get(2));
            camera.moveBackwards(distance);
            if (angle > (float)Math.toRadians(-90) && angle < (float)Math.toRadians(90)){
                objectObj.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                objectObj.get(0).rotateObject(-change, 0f, 1f, 0f);
                objectObj.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
                angle = angle - change;
            } else if (angle > (float)Math.toRadians(90) && angle < (float)Math.toRadians(270)){
                objectObj.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                objectObj.get(0).rotateObject(change, 0f, 1f, 0f);
                objectObj.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
                angle = angle + change;
            } else if (angle > (float)Math.toRadians(270) && angle < (float)Math.toRadians(360)){
                objectObj.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                objectObj.get(0).rotateObject(-change, 0f, 1f, 0f);
                objectObj.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
                angle = angle - change;
            } else if (angle > (float)Math.toRadians(-270) && angle < (float)Math.toRadians(-90)){
                objectObj.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                objectObj.get(0).rotateObject(change, 0f, 1f, 0f);
                objectObj.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
                angle = angle + change;
            } else if (angle > (float)Math.toRadians(-450) && angle < (float)Math.toRadians(-270)){
                objectObj.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                objectObj.get(0).rotateObject(-change, 0f, 1f, 0f);
                objectObj.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
                angle = angle - change;
            }
        }

        if (window.isKeyPressed(GLFW_KEY_W) && window.isKeyPressed(GLFW_KEY_A)){
            objectObj.get(0).translateObject(-move, 0f, -move);
            camera.setPosition(temp.get(0), temp.get(1), temp.get(2));
            camera.moveBackwards(distance);
            if (angle > (float)Math.toRadians(45) && angle < (float)Math.toRadians(225)){
                objectObj.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                objectObj.get(0).rotateObject(-change, 0f, 1f, 0f);
                objectObj.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
                angle = angle - change;
            } else if (angle > (float)Math.toRadians(225) && angle < (float)Math.toRadians(405)){
                objectObj.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                objectObj.get(0).rotateObject(change, 0f, 1f, 0f);
                objectObj.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
                angle = angle + change;
            } else if (angle > (float)Math.toRadians(-135) && angle < (float)Math.toRadians(45)){
                objectObj.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                objectObj.get(0).rotateObject(change, 0f, 1f, 0f);
                objectObj.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
                angle = angle + change;
            } else if (angle > (float)Math.toRadians(-315) && angle < (float)Math.toRadians(-135)){
                objectObj.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                objectObj.get(0).rotateObject(-change, 0f, 1f, 0f);
                objectObj.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
                angle = angle - change;
            } else if (angle > (float)Math.toRadians(-360) && angle < (float)Math.toRadians(-315)){
                objectObj.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                objectObj.get(0).rotateObject(change, 0f, 1f, 0f);
                objectObj.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
                angle = angle + change;
            }
        }

        if (window.isKeyPressed(GLFW_KEY_W) && window.isKeyPressed(GLFW_KEY_D)){
            objectObj.get(0).translateObject(move, 0f, -move);
            camera.setPosition(temp.get(0), temp.get(1), temp.get(2));
            camera.moveBackwards(distance);
            if (angle > (float)Math.toRadians(-45) && angle < (float)Math.toRadians(135)){
                objectObj.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                objectObj.get(0).rotateObject(-change, 0f, 1f, 0f);
                objectObj.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
                angle = angle - change;
            } else if (angle > (float)Math.toRadians(135) && angle < (float)Math.toRadians(315)){
                objectObj.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                objectObj.get(0).rotateObject(change, 0f, 1f, 0f);
                objectObj.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
                angle = angle + change;
            } else if (angle > (float)Math.toRadians(315) && angle < (float)Math.toRadians(360)){
                objectObj.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                objectObj.get(0).rotateObject(-change, 0f, 1f, 0f);
                objectObj.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
                angle = angle - change;
            } else if (angle > (float)Math.toRadians(-225) && angle < (float)Math.toRadians(-45)){
                objectObj.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                objectObj.get(0).rotateObject(change, 0f, 1f, 0f);
                objectObj.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
                angle = angle + change;
            } else if (angle > (float)Math.toRadians(-405) && angle < (float)Math.toRadians(-225)){
                objectObj.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                objectObj.get(0).rotateObject(-change, 0f, 1f, 0f);
                objectObj.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
                angle = angle - change;
            }
        }

        if (window.isKeyPressed(GLFW_KEY_S) && window.isKeyPressed(GLFW_KEY_A)) {
            objectObj.get(0).translateObject(-move, 0f, move);
            camera.setPosition(temp.get(0), temp.get(1), temp.get(2));
            camera.moveBackwards(distance);
            if (angle > (float)Math.toRadians(135) && angle < (float)Math.toRadians(315)){
                objectObj.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                objectObj.get(0).rotateObject(-change, 0f, 1f, 0f);
                objectObj.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
                angle = angle - change;
            } else if (angle > (float)Math.toRadians(315) && angle < (float)Math.toRadians(495)){
                objectObj.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                objectObj.get(0).rotateObject(change, 0f, 1f, 0f);
                objectObj.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
                angle = angle + change;
            } else if (angle > (float)Math.toRadians(-45) && angle < (float)Math.toRadians(135)){
                objectObj.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                objectObj.get(0).rotateObject(change, 0f, 1f, 0f);
                objectObj.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
                angle = angle + change;
            } else if (angle > (float)Math.toRadians(-225) && angle < (float)Math.toRadians(-45)){
                objectObj.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                objectObj.get(0).rotateObject(-change, 0f, 1f, 0f);
                objectObj.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
                angle = angle - change;
            } else if (angle > (float)Math.toRadians(-360) && angle < (float)Math.toRadians(-225)){
                objectObj.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                objectObj.get(0).rotateObject(change, 0f, 1f, 0f);
                objectObj.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
                angle = angle + change;
            }
        }

        if (window.isKeyPressed(GLFW_KEY_S) && window.isKeyPressed(GLFW_KEY_D)) {
            objectObj.get(0).translateObject(move, 0f, move);
            camera.setPosition(temp.get(0), temp.get(1), temp.get(2));
            camera.moveBackwards(distance);
            if (angle > (float)Math.toRadians(-135) && angle < (float)Math.toRadians(45)){
                objectObj.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                objectObj.get(0).rotateObject(-change, 0f, 1f, 0f);
                objectObj.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
                angle = angle - change;
            } else if (angle > (float)Math.toRadians(45) && angle < (float)Math.toRadians(225)){
                objectObj.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                objectObj.get(0).rotateObject(change, 0f, 1f, 0f);
                objectObj.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
                angle = angle + change;
            } else if (angle > (float)Math.toRadians(225) && angle < (float)Math.toRadians(360)){
                objectObj.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                objectObj.get(0).rotateObject(-change, 0f, 1f, 0f);
                objectObj.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
                angle = angle - change;
            } else if (angle > (float)Math.toRadians(-315) && angle < (float)Math.toRadians(-135)){
                objectObj.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                objectObj.get(0).rotateObject(change, 0f, 1f, 0f);
                objectObj.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
                angle = angle + change;
            } else if (angle > (float)Math.toRadians(-495) && angle < (float)Math.toRadians(-315)){
                objectObj.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                objectObj.get(0).rotateObject(-change, 0f, 1f, 0f);
                objectObj.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
                angle = angle - change;
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
