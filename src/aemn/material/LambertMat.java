
package aemn.material;

import java.awt.Color;

/**
 * @author Alan Maldonado
 *
 */
public class LambertMat extends MaterialShader {

	/**
	 * Definition: Lambert Material! 
	 * @param color
	 * @param intensity
	 * @param shininess
	 * @param diffuse
	 * @param refraction
	 */
	public LambertMat(Color color, double intensity, double shininess, double diffuse) {
		super(color, intensity, shininess, diffuse);
		// TODO Auto-generated constructor stub
	}

}
