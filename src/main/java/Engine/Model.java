package Engine;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector3i;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.glNormal3f;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;


public class Model extends Object{
    List <Vector3f> normals;
    List <Vector2f> textures;
    List <Face> faces;
    int nbo;
    public Model(List<ShaderModuleData> shaderModuleDataList, List<Vector3f> vertices, Vector4f color, String filename) throws IOException {
        super(shaderModuleDataList, vertices, color);
        normals = new ArrayList<>();
        faces= new ArrayList<>();
        textures = new ArrayList<>();
        LoadModel(new File(filename));
        setupVAOVBO();
    }

    public void LoadModel(File f ) throws IOException {
        List <Vector3f> normalV= new ArrayList<>();
        List <Vector3f> verticeV= new ArrayList<>();
        List <Vector2f> textureV= new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(f));
        float x,y,z;
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("v ")) {
                    x = Float.parseFloat(line.split(" ")[1]);
                    y = Float.parseFloat(line.split(" ")[2]);
                    z = Float.parseFloat(line.split(" ")[3]);
                    System.out.println("Vertices: "+x+" "+y+" "+z);
                    verticeV.add(new Vector3f(x, y, z));
                }
                else if (line.startsWith("vn ")) {
                    x = Float.parseFloat(line.split(" ")[1]);
                    y = Float.parseFloat(line.split(" ")[2]);
                    z = Float.parseFloat(line.split(" ")[3]);
                    System.out.println("Normals: "+x+" "+y+" "+z);
                    normalV.add(new Vector3f(x, y, z));
                }
                else if (line.startsWith("vt ")) {
                    x = Float.parseFloat(line.split(" ")[1]);
                    y = Float.parseFloat(line.split(" ")[2]);

                    textureV.add(new Vector2f(x, y));
                }

                else if (line.startsWith("f ")) {
                    Vector3f vertexIndices = new Vector3f(
                            Float.parseFloat(line.split(" ")[1].split("/")[0]),
                            Float.parseFloat(line.split(" ")[2].split("/")[0]),
                            Float.parseFloat(line.split(" ")[3].split("/")[0]));

                    Vector3f normalIndices = new Vector3f(
                            Float.parseFloat(line.split(" ")[1].split("/")[2]),
                            Float.parseFloat(line.split(" ")[2].split("/")[2]),
                            Float.parseFloat(line.split(" ")[3].split("/")[2]));
                    if(textureV.size() > 0){
                        Vector3f TextureIndices = new Vector3f(Float.parseFloat(line.split(" ")[1].split("/")[1]),
                                Float.parseFloat(line.split(" ")[2].split("/")[1]),
                                Float.parseFloat(line.split(" ")[3].split("/")[1]));
                    }
                    faces.add(new Face(vertexIndices, normalIndices));
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        reader.close();

        for (Face face: faces ){
            Vector3f n1 = normalV.get((int)face.normal.x - 1);
            System.out.println(n1);
            normals.add(n1);
            Vector3f v1 = verticeV.get((int)face.vertex.x - 1);
            vertices.add(v1);
            Vector3f n2 = normalV.get((int)face.normal.y - 1);
            normals.add(n2);
            Vector3f v2 = verticeV.get((int)face.vertex.y - 1);
            vertices.add(v2);
            Vector3f n3 = normalV.get((int)face.normal.z - 1);
            normals.add(n3);
            Vector3f v3 = verticeV.get((int)face.vertex.z - 1);
            vertices.add(v3);
        }
    }

    public void setupVAOVBO(){
        //set vao
        super.setupVAOVBO();
        nbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, nbo);
        //mengirim vertices ke shader
        glBufferData(GL_ARRAY_BUFFER,
                Utils.listoFloat(normals),
                GL_STATIC_DRAW);

//        uniformsMap.createUniform("lightColor");
//        uniformsMap.createUniform("lightPos");
    }

    public void drawSetup(Camera camera, Projection projection){
        super.drawSetup(camera,projection);
        glEnableVertexAttribArray(1);
        glBindBuffer(GL_ARRAY_BUFFER, nbo);
        glVertexAttribPointer(1,
                3, GL_FLOAT,
                false,
                0, 0);
//        uniformsMap.setUniform("lightColor", new Vector3f(1f,1f,0f));
//        uniformsMap.setUniform("lightPos", new Vector3f(1f,1f,0f));
        uniformsMap.setUniform("dirLight.direction", new Vector3f(0.2f,-1.0f,-0.f));
        uniformsMap.setUniform("dirLight.ambient", new Vector3f(0.05f,0.05f,0.05f));
        uniformsMap.setUniform("dirLight.diffuse", new Vector3f(0.4f,0.4f,0.4f));
        uniformsMap.setUniform("dirLight.specular", new Vector3f(0.5f,0.5f,0.5f));

        //posisi pointlight
        Vector3f[] _pointLightPositions = {
                new Vector3f(0.7f,0.2f,2.0f),
                new Vector3f(2.3f,-3.3f,-4.0f),
                new Vector3f(-4.0f,2.0f,-12.0f),
                new Vector3f(0.0f,0.0f,-3.0f)
        };
        for(int i = 0; i< _pointLightPositions.length; i++){
            uniformsMap.setUniform("pointLight["+i+"].position", _pointLightPositions[i]);
            uniformsMap.setUniform("pointLight["+i+"].ambient", new Vector3f(0.05f,0.05f,0.05f));
            uniformsMap.setUniform("pointLight["+i+"].diffuse", new Vector3f(0.8f,0.8f,0.8f));
            uniformsMap.setUniform("pointLight["+i+"].specular", new Vector3f(1.0f,1.0f,1.0f));
            uniformsMap.setUniform("pointLight["+i+"].constant", 1.0f);
            uniformsMap.setUniform("pointLight["+i+"].linear", 0.09f);
            uniformsMap.setUniform("pointLight["+i+"].quadratic", 0.032f);
        }

        uniformsMap.setUniform("viewPos", camera.getPosition());
    }

//    public void draw(Camera camera, Projection projection){
//        drawSetup(camera,projection);
//        // Draw the vertices
//        glLineWidth(10);
//        glPointSize(10);
//        //GL_TRIANGLES
//        //GL_LINE_LOOP
//        //GL_LINE_STRIP
//        //GL_LINES
//        //GL_POINTS
//        //GL_TRIANGLE_FAN
//        glDrawArrays(GL_LINE_STRIP, 0,
//                vertices.size());
//        for(Object child:childObject){
//            child.draw(camera,projection);
//        }
//    }

}
