package engine.gfx.shader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;

import org.lwjgl.opengl.GL33;

import engine.gfx.BufferData;
import engine.util.Logger;

public class ShaderManager {
	
	private HashMap<String, Shader> shader_map;
	
	public ShaderManager() {
		shader_map = new HashMap<>();
	}
	
	public void startShader(String key) {
		getShader(key).start();
	}
	
	public void stopShader(String key) {
		getShader(key).stop();
	}
	
	public void initShader(String key, String vertex_path, String fragment_path) {
		int program_id = GL33.glCreateProgram();
		int vertex_id = loadTypeShader(program_id, vertex_path, GL33.GL_VERTEX_SHADER);
		int fragment_id = loadTypeShader(program_id, fragment_path, GL33.GL_FRAGMENT_SHADER);
		Shader shader = new Shader(program_id, vertex_id, fragment_id);
		shader.init();
		shader_map.put(key, shader);
	}
	
	public void bindShader(String shader_key, HashMap<Integer, BufferData> buffer_data_map) {
		Shader shader = shader_map.get(shader_key);
		buffer_data_map.keySet().forEach(i -> GL33.glBindAttribLocation(shader.getProgram_id(), i, buffer_data_map.get(i).getVar_name()));
	}
	
	private static int loadTypeShader(int program_id, String local_path, int type) {
		StringBuilder builder = new StringBuilder();
		try {
			BufferedReader buffered_reader = new BufferedReader(new FileReader(new File("assets/shaders/" + local_path)));
			buffered_reader.lines().forEach(i -> builder.append(i + "\n"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String src = builder.toString();
		int shader_id = GL33.glCreateShader(type);
		GL33.glShaderSource(shader_id, src);
		GL33.glCompileShader(shader_id);
		
		if(GL33.glGetShaderi(shader_id, GL33.GL_COMPILE_STATUS) == GL33.GL_FALSE) 
			Logger.log("Shader error (" + local_path + "): " + GL33.glGetShaderInfoLog(shader_id, 1024), System.err);
		
		GL33.glAttachShader(program_id, shader_id);
		GL33.glValidateProgram(program_id);
		GL33.glLinkProgram(program_id);
		return shader_id;
	}
	
	public Shader getShader(String key) {
		if(!shader_map.containsKey(key))
			return null;
		return shader_map.get(key);
	}

}
