#version 330
uniform vec4 uni_color;
out vec4 frag_color;

uniform vec3 lightColor;
uniform vec3 lightPos;
uniform vec3 viewPos;

in vec3 Normal;
in vec3 FragPos;
void main() {
    // ambient
    float ambientStrength = 0.1f;
    vec3 ambient = ambientStrength * lightColor;

    // diffuse
    vec3 lightDirection = normalize(lightPos - FragPos);
    float diff = max(dot(Normal, lightDirection), 0.0f);
    vec3 diffuse = diff * lightColor;

    // specular
    float specularStrength = 0.5f;
    vec3 viewDir = normalize(viewPos - FragPos);

    // blinn phong
//    vec3 halfwayDir = normalize(lightDirection + viewDir);
//    float spec = pow(max(dot(Normal, halfwayDir), 0.0f), 64.0f*3.0f);

    // original phong
    vec3 reflectDir = reflect(-lightDirection, Normal);
    float spec = pow(max(dot(viewDir, reflectDir), 0.0f), 64.0f);

    vec3 specular = specularStrength * spec * lightColor;

    vec3 result = (ambient + diffuse + specular) * vec3(uni_color);
    frag_color = vec4(result, 1.0f);
}

//#version 330
//uniform vec4 uni_color;
//out vec4 frag_color;
//void main() {
//    //    frag_color = vec4(1.0, 0.0, 0.0, 1.0);
//    frag_color = uni_color;
//}
