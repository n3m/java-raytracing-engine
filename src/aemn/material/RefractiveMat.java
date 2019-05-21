package aemn.material;

import java.awt.Color;

/**
 * @author Alan Maldonado
 *
 */
public class RefractiveMat extends MaterialShader {

	/**
	 * @param color
	 * @param intensity
	 * @param shininess
	 * @param diffuse
	 * @param refraction
	 */
	
	/**
	 * Refraction index values:
	 * Water: 1.3
	 * Glass: 1.5
	 * Diamond: 1.8
	 */
	
	private double refractionIndex = 0.0;
	
	public RefractiveMat(Color color, double intensity, double shininess, double diffuse, double refraction) {
		super(color, intensity, shininess, diffuse);
		// TODO Auto-generated constructor stub
		setRefractionIndex(refraction);
	}
	
	/***
	 * Get Refraction Index
	 * @return
	 */
	public double getRefractionIndex() {
		return refractionIndex;
	}

	/***
	 * Set Refraction Index
	 * @param refractionIndex
	 */
	public void setRefractionIndex(double refractionIndex) {
		this.refractionIndex = refractionIndex;
	}

}
