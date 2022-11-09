package engine.io;

import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;

import org.lwjgl.opengl.GL;

import engine.util.Logger;

public class WindowManager {

	private int window_width, window_height;
	private String window_title;
	private long glfw_window = 0;
	
	public WindowManager(int window_width, int window_height, String window_title) {
		this.window_width = window_width;
		this.window_height = window_height;
		this.window_title = window_title;
	}
	
	public void init() {
		glfw_window = glfwCreateWindow(window_width, window_height, window_title, 0, 0);
		if(glfw_window == 0)
			Logger.log("glfwCreateWindow() failed.", System.err);
		
		glfwMakeContextCurrent(glfw_window);
		GL.createCapabilities();
	}
	
	public void destroyWindow() {
		glfwDestroyWindow(glfw_window);
	}
	
	public void setWindow_width(int window_width) {
		this.window_width = window_width;
	}
	
	public void setWindow_height(int window_height) {
		this.window_height = window_height;
	}

	public int getWindow_width() {
		return window_width;
	}

	public int getWindow_height() {
		return window_height;
	}

	public String getWindow_title() {
		return window_title;
	}

	public long getGlfw_window() {
		return glfw_window;
	}
	
}
