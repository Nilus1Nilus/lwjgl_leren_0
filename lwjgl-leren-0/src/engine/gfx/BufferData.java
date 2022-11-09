package engine.gfx;

public class BufferData {
	
	private int	vertex_dim;
	private float[] data;
	private String var_name;
	
	public BufferData(float[] data, int vertex_dim, String var_name) {
		this.data = data;
		this.vertex_dim = vertex_dim;
		this.var_name = var_name;
	}

	public int getVertex_dim() {
		return vertex_dim;
	}

	public float[] getData() {
		return data;
	}
	
	public String getVar_name() {
		return var_name;
	}
	
}
