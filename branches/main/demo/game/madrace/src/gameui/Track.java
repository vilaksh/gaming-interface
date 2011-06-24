package gameui;

import javax.media.j3d.Appearance;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.QuadArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Texture;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point2f;
import javax.vecmath.Point3f;

import com.sun.j3d.utils.image.TextureLoader;

public class Track {

	private TransformGroup mainTransformGroup;

	public Track() {

		mainTransformGroup = new TransformGroup();
		Appearance landAppearance = new Appearance();

		QuadArray landGeom = new QuadArray(4, GeometryArray.COORDINATES
				| GeometryArray.TEXTURE_COORDINATE_2);

		landGeom.setCoordinate(0, new Point3f(-10f, -1f, 0f));
		landGeom.setCoordinate(1, new Point3f(-10f, -1f, 1000f));
		landGeom.setCoordinate(2, new Point3f(10f, -1f, 1000f));
		landGeom.setCoordinate(3, new Point3f(10f, -1f, 0f));

		landGeom.setTextureCoordinate(0, new Point2f(0.0f, 0.0f));
		landGeom.setTextureCoordinate(1, new Point2f(0.0f, 0.1f));
		landGeom.setTextureCoordinate(2, new Point2f(5f, 0.1f));
		landGeom.setTextureCoordinate(3, new Point2f(5f, 0.0f));

		Texture texImage = new TextureLoader(
				".\\resources\\textures\\road.jpg",
				null).getTexture();

		landAppearance.setTexture(texImage);

		Shape3D s = new Shape3D(landGeom, landAppearance);
		mainTransformGroup.addChild(s);
	}

	public TransformGroup GetMainGroup() {
		return mainTransformGroup;
	}

}
