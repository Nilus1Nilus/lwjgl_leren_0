package engine.gfx.mesh;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL33;

import engine.gfx.BufferData;

public class Mesh {
	
	private int vao_id;
	private int vertex_count;
	private HashMap<Integer, BufferData> buffer_data_map;
	private int[] indices;
	
	public Mesh(int vao_id, HashMap<Integer, BufferData> buffer_data_map, int[] indices, int vertex_count) {
		this.vao_id = vao_id;
		this.buffer_data_map = buffer_data_map;
		this.indices = indices;
		this.vertex_count = vertex_count;
	}
	
	public void bind_buffer_data() {
		GL33.glBindVertexArray(vao_id);
				
		int ibo_id = GL33.glGenBuffers();
		GL33.glBindBuffer(GL33.GL_ELEMENT_ARRAY_BUFFER, ibo_id);
		IntBuffer index_buffer = BufferUtils.createIntBuffer(indices.length);
		index_buffer.put(indices);
		index_buffer.flip();
		GL33.glBufferData(GL33.GL_ELEMENT_ARRAY_BUFFER, index_buffer, GL33.GL_STATIC_DRAW);
		
		buffer_data_map.keySet().forEach(i -> {
			BufferData buffer_data = buffer_data_map.get(i);
			int bo_id = GL33.glGenBuffers();
			GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, bo_id);
			
			FloatBuffer buffer = BufferUtils.createFloatBuffer(buffer_data.getData().length);
			buffer.put(buffer_data.getData());
			buffer.flip();
			
			GL33.glBufferData(GL33.GL_ARRAY_BUFFER, buffer, GL33.GL_STATIC_DRAW);
			GL33.glVertexAttribPointer(i, buffer_data.getVertex_dim(), GL33.GL_FLOAT, false, 0, 0);
		});
		
		GL33.glBindVertexArray(0);
	}
	
	public int getVao_id() {
		return vao_id;
	}
	
	public HashMap<Integer, BufferData> getBuffer_data_map() {
		return buffer_data_map;
	}
	
	public int getVertex_count() {
		return vertex_count;
	}

}
