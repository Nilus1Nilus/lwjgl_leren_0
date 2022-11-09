package engine.gfx.mesh;

import java.util.HashMap;

import org.lwjgl.opengl.GL33;

import engine.gfx.BufferData;

public class MeshLoader {
	
	public static Mesh loadMesh(HashMap<Integer, BufferData> buffer_data_map, int[] indices, int vertex_count) {
		int vao_id = GL33.glGenVertexArrays();
		Mesh mesh = new Mesh(vao_id, buffer_data_map, indices, vertex_count);
		mesh.bind_buffer_data();
		return mesh;
	}

}
