package Engine;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class Object2d extends ShaderProgram {
    public List<Vector3f> vertices;
    int vao;
    int vbo;
    int tbo;
    Vector4f color;
    UniformsMap uniformsMap;
    List<Vector3f> curve = new ArrayList<>();
    public Matrix4f model;
    public Vector3f currentPosition;
    private List<Object2d> childObject;
    List<Vector3f> normal = new ArrayList<>();
    int nbo;
    List<Vector2f> textures = new ArrayList<>();
    List<Integer> indices = new ArrayList<>();
    String filename;
    ArrayList<Vector2f> textureArray = new ArrayList<>();
    ArrayList<Vector3f> normalsArray = new ArrayList<>();
    int ibo;

    public Vector3f updateCenterPoint() {
        Vector3f centerTemp = new Vector3f();
        model.transformPosition(0f, 0f, 0f, centerTemp);
        return centerTemp;
    }

    public List<Object2d> getChildObject() {
        return childObject;
    }

    public void setChildObject(List<Object2d> childObject) {
        this.childObject = childObject;
    }

    public Object2d(List<ShaderModuleData> shaderModuleDataList, List<Vector3f> vertices, Vector4f color) {
        super(shaderModuleDataList);
        this.vertices = vertices;
        setupVAOVBO();
        this.color = color;
        uniformsMap = new UniformsMap(getProgramId());
        uniformsMap.createUniform("uni_color");
        uniformsMap.createUniform("model");
        uniformsMap.createUniform("view");
        uniformsMap.createUniform("projection");
        uniformsMap.createUniform("lightColor");
        uniformsMap.createUniform("lightPos");
        uniformsMap.createUniform("viewPos");
        model = new Matrix4f().scale(1, 1, 1);
        childObject = new ArrayList<>();
    }

    public Object2d(List<ShaderModuleData> shaderModuleDataList, List<Vector3f> vertices, Vector4f color, String filename) {
        super(shaderModuleDataList);
        this.vertices = vertices;
        this.color = color;
        uniformsMap = new UniformsMap(getProgramId());
        uniformsMap.createUniform("uni_color");
        uniformsMap.createUniform("model");
        uniformsMap.createUniform("view");
        uniformsMap.createUniform("projection");
        uniformsMap.createUniform("lightColor");
        uniformsMap.createUniform("lightPos");
        uniformsMap.createUniform("viewPos");
        model = new Matrix4f().scale(1, 1, 1);
        childObject = new ArrayList<>();
        this.filename = filename;
        generateOBJ();
        setupVAOVBONBOTBOIBO();
    }

    public void generateOBJ() {
        List<String> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(this.filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                list.add(line);
                String[] tokens = line.split("\\s+");
                if ("v".equals(tokens[0])) {
                    Vector3f verticesVec = new Vector3f(Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]), Float.parseFloat(tokens[3]));
                    vertices.add(verticesVec);
                } else if ("vt".equals(tokens[0])) {
                    Vector2f texturesVec = new Vector2f(Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]));
                    textures.add(texturesVec);
                } else if ("vn".equals(tokens[0])) {
                    Vector3f normalVec = new Vector3f(Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]), Float.parseFloat(tokens[3]));
                    normal.add(normalVec);
                }
                else if ("f".equals(tokens[0])) {
                    String[] currentLine = line.split(" ");
                    String[] vertex1 = currentLine[1].split("/");
                    String[] vertex2 = currentLine[2].split("/");
                    String[] vertex3 = currentLine[3].split("/");

                    processVertex(vertex1, indices, textures, normal, textureArray, normalsArray);
                    processVertex(vertex2, indices, textures, normal, textureArray, normalsArray);
                    processVertex(vertex3, indices, textures, normal, textureArray, normalsArray);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void processVertex(String[] vertexData, List<Integer> indices, List<Vector2f> textures, List<Vector3f> normals, ArrayList<Vector2f> textureArray, ArrayList<Vector3f> normalsArray) {
        int currentVertexPointer = Integer.parseInt(vertexData[0]) - 1;
        indices.add(currentVertexPointer);
        Vector2f currentTex = textures.get(Integer.parseInt(vertexData[1]) - 1);
        textureArray.add(new Vector2f(currentTex.x, currentTex.y));
        Vector3f currentNorm = normals.get(Integer.parseInt(vertexData[2]) - 1);
        normalsArray.add(new Vector3f(currentNorm.x, currentNorm.y, currentNorm.z));
    }

    public void setupVAOVBO() {
        // set vao
        vao = glGenVertexArrays();
        glBindVertexArray(vao);

        // set vbo
        vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        // mengirim vertices ke shader
        glBufferData(GL_ARRAY_BUFFER, Utils.listoFloat(vertices), GL_STATIC_DRAW);
    }

    public void setupVAOVBONBO() {
        // set vao
        vao = glGenVertexArrays();
        glBindVertexArray(vao);

        // set vbo
        vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        // mengirim vertices ke shader
        glBufferData(GL_ARRAY_BUFFER, Utils.listoFloat(vertices), GL_STATIC_DRAW);

        // set nbo
        nbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, nbo);
        // mengirim vertices ke shader
        glBufferData(GL_ARRAY_BUFFER, Utils.listoFloat(normal), GL_STATIC_DRAW);

        uniformsMap.createUniform("lightColor");
        uniformsMap.createUniform("lightPos");
    }

    public void setupVAOVBONBOTBOIBO() {
        // set vao
        vao = glGenVertexArrays();
        glBindVertexArray(vao);

        // set vbo
        vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, Utils.listoFloat(vertices), GL_STATIC_DRAW);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(0);

        // set nbo
        nbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, nbo);
        glBufferData(GL_ARRAY_BUFFER, Utils.listoFloat(normalsArray), GL_STATIC_DRAW);
        glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(1);

        // set tbo
        tbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, tbo);
        float[] textureCoordsArray = new float[textureArray.size() * 2];
        for (int i = 0; i < textureArray.size(); i++) {
            Vector2f texCoord = textureArray.get(i);
            textureCoordsArray[i * 2] = texCoord.x;
            textureCoordsArray[i * 2 + 1] = texCoord.y;
        }
        glBufferData(GL_ARRAY_BUFFER, textureCoordsArray, GL_STATIC_DRAW);
        glVertexAttribPointer(2, 2, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(2);

        // set ibo
        ibo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, Utils.listoInt(indices), GL_STATIC_DRAW);

        uniformsMap.createUniform("lightColor");
        uniformsMap.createUniform("lightPos");
    }

    public void drawSetup(Camera camera, Projection projection) {
        bind();
        uniformsMap.setUniform("uni_color", color);
        uniformsMap.setUniform("model", model);
        uniformsMap.setUniform("view", camera.getViewMatrix());
        uniformsMap.setUniform("projection", projection.getProjMatrix());
        // bind VBO
        glEnableVertexAttribArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
    }

    public void drawSetupNBOIBO(Camera camera, Projection projection) {
        bind();
        uniformsMap.setUniform("uni_color", color);
        uniformsMap.setUniform("model", model);
        uniformsMap.setUniform("view", camera.getViewMatrix());
        uniformsMap.setUniform("projection", projection.getProjMatrix());
        // bind VBO
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(0);
        // bind NBO
        glBindBuffer(GL_ARRAY_BUFFER, nbo);
        glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(1);
        // bind TBO
        glBindBuffer(GL_ARRAY_BUFFER, tbo);
        glVertexAttribPointer(2, 2, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(2);
        // bind IBO
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);

        uniformsMap.setUniform("lightColor", new Vector3f(1.0f, 1.0f, 0.0f));
        uniformsMap.setUniform("lightPos", new Vector3f(0, 3f, 1f));
        uniformsMap.setUniform("viewPos", camera.getPosition());
    }

    public void draw(Camera camera, Projection projection) {
        drawSetupNBOIBO(camera, projection);
        // Draw the vertices
        glLineWidth(0);
        glPointSize(0);
        // GL_TRIANGLES
        // GL_LINE_LOOP
        // GL_LINE_STRIP
        // GL_LINES
        // GL_POINTS
        // GL_TRIANGLE_FAN
        glDrawElements(GL_TRIANGLES, indices.size(), GL_UNSIGNED_INT, 0);
//        glDrawArrays(GL_POLYGON, 0, indices.size());
    }

    public void drawWithChild(Camera camera, Projection projection) {
        drawSetup(camera, projection);
        // Draw the vertices
        glLineWidth(0);
        glPointSize(0);
        // GL_TRIANGLES
        // GL_LINE_LOOP
        // GL_LINE_STRIP
        // GL_LINES
        // GL_POINTS
        // GL_TRIANGLE_FAN
        glDrawArrays(GL_TRIANGLE_FAN, 0, vertices.size());
        for (Object2d child : childObject) {
            child.draw(camera, projection);
        }
    }

    public void setupVAOVBOForCurve() {
        // set vao
        vao = glGenVertexArrays();
        glBindVertexArray(vao);

        // set vbo
        vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        // mengirim vertices ke shader
        glBufferData(GL_ARRAY_BUFFER, Utils.listoFloat(curve), GL_STATIC_DRAW);
    }

    public void createCurve() {
        curve.clear();
        for (double i = 0; i <= 1.01; i += 0.01) {
            curve.add(bezierCurve(i));
        }
        setupVAOVBOForCurve();
    }

    private Vector3f bezierCurve(double t) {
        int i = 0;
        int size = vertices.size() - 1;
        Vector3f result = new Vector3f(0.0f, 0.0f, 0.0f);
        for (Vector3f vertice : vertices) {
            result.x += combinations(size, i) * Math.pow((1 - t), size - i) * vertice.x * Math.pow(t, i);
            result.y += combinations(size, i) * Math.pow((1 - t), size - i) * vertice.y * Math.pow(t, i);
            i += 1;
        }
        return result;
    }

    private int combinations(int n, int r) {
        return factorial(n) / factorial(r) / factorial(n - r);
    }

    private int factorial(int n) {
        int result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    public void translate(float x, float y, float z) {
        model = (new Matrix4f().translate(x, y, z)).mul(model);
    }

    public void translateWithChild(float x, float y, float z) {
        model = (new Matrix4f().translate(x, y, z)).mul(model);
        for (Object2d child : childObject) {
            child.translateWithChild(x, y, z);
        }
    }

    public void rotate(float angle, float x, float y, float z) {
        model = new Matrix4f().rotate(angle, x, y, z).mul(new Matrix4f(model));
    }

    public void rotateWithChild(float angle, float x, float y, float z) {
        model = new Matrix4f().rotate(angle, x, y, z).mul(new Matrix4f(model));
        for (Object2d child : childObject) {
            child.rotate(angle, x, y, z);
        }
    }

    public void scale(float x, float y, float z) {
        model = model.mul(new Matrix4f().scale(x, y, z));
    }

    public void scaleWithChild(float x, float y, float z) {
        model = model.mul(new Matrix4f().scale(x, y, z));
        for (Object2d child : childObject) {
            child.scale(x, y, z);
        }
    }

    public void createEllipsoid() {
        vertices.clear();
        ArrayList<Vector3f> temp = new ArrayList<>();

        for (double v = -Math.PI / 2; v <= Math.PI / 2; v += Math.PI / 60) {
            for (double u = -Math.PI; u <= Math.PI; u += Math.PI / 60) {
                float x = 0.5f * (float) (Math.cos(v) * Math.cos(u));
                float y = 0.5f * (float) (Math.cos(v) * Math.sin(u));
                float z = 0.5f * (float) (Math.sin(v));
                temp.add(new Vector3f(x, y, z));
            }
        }
        vertices = temp;
    }

    public void createCylinder() {
        vertices.clear();
        ArrayList<Vector3f> temp = new ArrayList<>();

        for (float i = 0.0f; i <= 1f; i += 0.01f) {
            for (double v = 0; v <= Math.PI * 2; v += Math.PI / 60) {
                for (double u = 0; u <= Math.PI * 2; u += Math.PI / 60) {
                    float x = 0.3f * (float) (Math.cos(v));
                    float y = 0.3f * (float) (Math.sin(v));
                    temp.add(new Vector3f(x, y, i));
                }
            }
        }
        vertices = temp;

        normal = new ArrayList<>(Arrays.asList(
                // belakang
                new Vector3f(0.0f, 0.0f, -1.0f),
                new Vector3f(0.0f, 0.0f, -1.0f),
                new Vector3f(0.0f, 0.0f, -1.0f),
                new Vector3f(0.0f, 0.0f, -1.0f),
                new Vector3f(0.0f, 0.0f, -1.0f),
                new Vector3f(0.0f, 0.0f, -1.0f),
                // depan
                new Vector3f(0.0f, 0.0f, 1.0f),
                new Vector3f(0.0f, 0.0f, 1.0f),
                new Vector3f(0.0f, 0.0f, 1.0f),
                new Vector3f(0.0f, 0.0f, 1.0f),
                new Vector3f(0.0f, 0.0f, 1.0f),
                new Vector3f(0.0f, 0.0f, 1.0f),
                // atas
                new Vector3f(0.0f, 1.0f, 0.0f),
                new Vector3f(0.0f, 1.0f, 0.0f),
                new Vector3f(0.0f, 1.0f, 0.0f),
                new Vector3f(0.0f, 1.0f, 0.0f),
                new Vector3f(0.0f, 1.0f, 0.0f),
                new Vector3f(0.0f, 1.0f, 0.0f),
                // bawah
                new Vector3f(0.0f, -1.0f, 0.0f),
                new Vector3f(0.0f, -1.0f, 0.0f),
                new Vector3f(0.0f, -1.0f, 0.0f),
                new Vector3f(0.0f, -1.0f, 0.0f),
                new Vector3f(0.0f, -1.0f, 0.0f),
                new Vector3f(0.0f, -1.0f, 0.0f),
                // kanan
                new Vector3f(1.0f, 0.0f, 0.0f),
                new Vector3f(1.0f, 0.0f, 0.0f),
                new Vector3f(1.0f, 0.0f, 0.0f),
                new Vector3f(1.0f, 0.0f, 0.0f),
                new Vector3f(1.0f, 0.0f, 0.0f),
                new Vector3f(1.0f, 0.0f, 0.0f),
                // kiri
                new Vector3f(-1.0f, 0.0f, 0.0f),
                new Vector3f(-1.0f, 0.0f, 0.0f),
                new Vector3f(-1.0f, 0.0f, 0.0f),
                new Vector3f(-1.0f, 0.0f, 0.0f),
                new Vector3f(-1.0f, 0.0f, 0.0f),
                new Vector3f(-1.0f, 0.0f, 0.0f)
        ));
    }
}
