public class Matrix4 {
    public float m00, m01, m02, m03;
    public float m10, m11, m12, m13;
    public float m20, m21, m22, m23;
    public float m30, m31, m32, m33;

    public void transform(float[] in, float[] out) {
        float x = in[0];
        float y = in[1];
        float z = in[2];

        out[0] = m00 * x + m01 * y + m02 * z + m03;
        out[1] = m10 * x + m11 * y + m12 * z + m13;
        out[2] = m20 * x + m21 * y + m22 * z + m23;
    }
}
