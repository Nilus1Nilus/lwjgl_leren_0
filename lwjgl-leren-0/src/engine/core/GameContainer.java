package engine.core;

import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_PROFILE;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetFramebufferSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowTitle;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL33;

import engine.gfx.Renderer;
import engine.gfx.shader.ShaderManager;
import engine.io.WindowManager;
import engine.util.Logger;

public class GameContainer implements Runnable {
			
	private int window_width = 2000;
	private int window_height = 1500;
	private final String WINDOW_TITLE = "lwjgl-leren";
	private WindowManager window_manager = null;
	private Renderer renderer = null;
	private ShaderManager shader_manager = null;
	
	private Game game = null;
	
	public GameContainer() {
		window_manager = new WindowManager(window_width, window_height, WINDOW_TITLE);
		if(window_manager == null)
			Logger.log("new WindowManager() failed.", System.err);
		
		renderer = new Renderer(window_manager);
		if(renderer == null)
			Logger.log("new Renderer() failed.", System.err);
		
		shader_manager = new ShaderManager();
		if(shader_manager == null)
			Logger.log("new ShaderManager() failed.", System.err);
	}
	
	public void init() {
		glfwSetErrorCallback(GLFWErrorCallback.createPrint(System.err));
		
		if(!glfwInit())
			Logger.log("glfwInit() failed.", System.err);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		
		window_manager.init();
		
		glfwSetFramebufferSizeCallback(window_manager.getGlfw_window(), (window, width, height) -> {
			window_manager.setWindow_width(width);
			window_manager.setWindow_height(height);
			GL33.glViewport(0, 0, width, height);
		});
		
		if(game == null)
			Logger.log("Game is not set.", System.err);
		game.init();
	}
	
	public void run() {
		double unprocessed = 0d;
		double max_unprocessed = 1d / 60d;
		double last_time = System.nanoTime() / 1e9;
		double timer = 0d;
		
		int fps = 0;
		int ups = 0;
	
		while(!glfwWindowShouldClose(window_manager.getGlfw_window())) {
			double first_time = System.nanoTime() / 1e9;
			double delta_time = first_time - last_time;
			last_time = first_time;
			
			timer += delta_time;
						
			unprocessed += delta_time;
			while(unprocessed >= max_unprocessed) {
				unprocessed -= max_unprocessed;
				
				glfwPollEvents();
				game.update(delta_time);
				
				ups++;
			}
			
			renderer.render(game);
			fps++;
			
			if(timer > 1d) {
				timer = 0;
				glfwSetWindowTitle(window_manager.getGlfw_window(), "fps: " + fps + " | ups: " + ups);
				fps = 0;
				ups = 0;
			}
		}
		
		window_manager.destroyWindow();
		glfwTerminate();
	}
	
	public void setGame(Game game) {
		this.game = game;
	}
	
	public ShaderManager getShader_manager() {
		return shader_manager;
	}
	
}
