/**
 * 
 */
package edu.up.isgc.material;

import java.awt.Color;

/**
 * @author User
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
	
	public RefractiveMat(Color color, double intensity, double shininess, double diffuse, double refraction) {
		super(color, intensity, shininess, diffuse, refraction);
		// TODO Auto-generated constructor stub
	}

}
