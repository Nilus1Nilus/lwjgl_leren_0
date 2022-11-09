package engine.gfx;

import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;

import org.lwjgl.opengl.GL33;

import engine.core.Game;
import engine.gfx.mesh.Mesh;
import engine.io.WindowManager;

public class Renderer {
	
	private WindowManager window_manager;
	
	public Renderer(WindowManager window_manager) {
		this.window_manager = window_manager;
	}
	
	public void render(Game game) {
		GL33.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		GL33.glClear(GL33.GL_COLOR_BUFFER_BIT);
		
		game.render(this);
		
		glfwSwapBuffers(window_manager.getGlfw_window());
	}
	
	public void renderMesh(Mesh mesh) {
		GL33.glBindVertexArray(mesh.getVao_id());
		mesh.getBuffer_data_map().keySet().forEach(i -> GL33.glEnableVertexAttribArray(i));
		GL33.glDrawElements(GL33.GL_TRIANGLES, mesh.getVertex_count(), GL33.GL_UNSIGNED_INT, 0);
		mesh.getBuffer_data_map().keySet().forEach(i -> GL33.glDisableVertexAttribArray(i));
		GL33.glBindVertexArray(0);
	}

}
