package engine.gfx.shader;

import org.lwjgl.opengl.GL33;

public class Shader {
	
	private int program_id;
	private int vertex_id;
	private int fragment_id;
	
	public Shader(int program_id, int vertex_id, int fragment_id) {
		this.program_id = program_id;
		this.vertex_id = vertex_id;
		this.fragment_id = fragment_id;
	}
	
	public void start() {
		GL33.glUseProgram(program_id);
	}
	
	public void stop() {
		GL33.glUseProgram(0);
	}
	
	public void init() {}

	public int getProgram_id() {
		return program_id;
	}

	public int getVertex_id() {
		return vertex_id;
	}

	public int getFragment_id() {
		return fragment_id;
	}

}
