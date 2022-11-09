package game.main;

import java.util.HashMap;

import org.lwjgl.opengl.GL33;

import engine.core.Game;
import engine.core.GameContainer;
import engine.gfx.BufferData;
import engine.gfx.Renderer;
import engine.gfx.mesh.Mesh;
import engine.gfx.mesh.MeshLoader;
import engine.gfx.shader.ShaderManager;

public class Test extends Game {

	public Test(GameContainer game_container) {
		super(game_container);
	}

	Mesh mesh;
	HashMap<Integer, BufferData> buffer_data_map = new HashMap<>();
	
	float[] vertices = {
			-0.5f, -0.5f, 0.0f,
			0.0f, 0.5f, 0.0f,
			0.5f, -0.5f, 0.0f,
	};
	
	int[] indices = {
			0, 1, 2
	};
	
	float[] colors = {
			1.0f, 0.0f, 0.0f,
			0.0f, 1.0f, 0.0f,
			0.0f, 0.0f, 1.0f,
	};
	
	@Override
	public void init() {
		buffer_data_map.put(0, new BufferData(vertices, 3, "position"));
		buffer_data_map.put(1, new BufferData(colors, 3, "colors"));
		mesh = MeshLoader.loadMesh(buffer_data_map, indices, indices.length);
		
		ShaderManager shader_manager = game_container.getShader_manager();
		shader_manager.initShader("static", "static/vertex.glsl", "static/fragment.glsl");
		shader_manager.bindShader("static", buffer_data_map);
	}
	
	float scale = 0.0f;
	
	@Override
	public void render(Renderer renderer) {
		game_container.getShader_manager().startShader("static");
		int location = GL33.glGetUniformLocation(1, "scale");
		GL33.glUniform1f(location, scale);
		renderer.renderMesh(mesh);
		game_container.getShader_manager().stopShader("static");
	}

	@Override
	public void update(double dt) {
		scale += 0.01f;
	}

}
