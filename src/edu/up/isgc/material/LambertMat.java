/**
 * 
 */
package edu.up.isgc.material;

import java.awt.Color;

/**
 * @author User
 *
 */
public class LambertMat extends MaterialShader {

	/**
	 * @param color
	 * @param intensity
	 * @param shininess
	 * @param diffuse
	 * @param refraction
	 */
	public LambertMat(Color color, double intensity, double shininess, double diffuse, double refraction) {
		super(color, intensity, shininess, diffuse, refraction);
		// TODO Auto-generated constructor stub
	}

}
