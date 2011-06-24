package gameui;

import java.io.FileNotFoundException;
import com.sun.j3d.utils.geometry.ColorCube;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.View;
import javax.vecmath.AxisAngle4f;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import com.glyphein.j3d.loaders.milkshape.MS3DLoader;
import com.sun.j3d.loaders.IncorrectFormatException;
import com.sun.j3d.loaders.Loader;
import com.sun.j3d.loaders.ParsingErrorException;
import com.sun.j3d.loaders.Scene;
import com.sun.j3d.utils.universe.SimpleUniverse;
import javax.media.j3d.ViewPlatform;

public class Car {
	private String modelPath;
	private TransformGroup mainTransformGroup;

	
	private TransformGroup carTransformGroup;
	
	private TransformGroup overheadCamTranslateGroup;
	private TransformGroup overheadCamRotateGroup;
	
	private BranchGroup carGroup;
	private ViewPlatform camera;
	// Structure of Car Model..
	//
	//
	// 						mainTransform
	// 								|
	// 				---------------------------------
	//              | 								| 	
	// overheadCamTranslateTransform 			Car Transform (Scale)
	// (translate transform)						|
	//				|							CarModel
	//	overheadCamRotateTransform				
	// (rotate transform)
	
	public Car(String mPath, Vector3f initialCarPosition, double carScale)
			throws FileNotFoundException, IncorrectFormatException,
			ParsingErrorException {
		modelPath = mPath;

		Loader loader = new MS3DLoader(MS3DLoader.LOAD_ALL);

		// Use these 4 lines to load from a local file
		java.io.File file = new java.io.File(modelPath);
		if (file.getParent() != null)
			if (file.getParent().length() > 0) // figure out the base path
				loader.setBasePath(file.getParent() + java.io.File.separator);

		Scene scene = loader.load(file.getName());

		BranchGroup car = scene.getSceneGroup();

		
		mainTransformGroup = new TransformGroup();
		mainTransformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		mainTransformGroup.addChild(new ColorCube(0.1));
		Transform3D initialPos = new Transform3D();
		initialPos.setTranslation(initialCarPosition);
		mainTransformGroup.setTransform(initialPos);
		
		
	    Transform3D carScaleTran = new Transform3D();	    
	    carScaleTran.setScale(carScale);
				
		carTransformGroup = new TransformGroup();
		carTransformGroup.setTransform(carScaleTran);
		carTransformGroup.addChild(car);
		
		mainTransformGroup.addChild(carTransformGroup);

		// TODO: get camera position from the size of the car..
		
		BranchGroup viewGroup = new BranchGroup();
		camera = new ViewPlatform();
		
		overheadCamTranslateGroup = new TransformGroup();
		overheadCamTranslateGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		overheadCamTranslateGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		overheadCamTranslateGroup.setCapability(TransformGroup.ALLOW_LOCAL_TO_VWORLD_READ);
		
		overheadCamRotateGroup = new TransformGroup();

		
		Transform3D overheadTransform3DTranslate = new Transform3D();		
		Transform3D overheadTransform3DRotate = new Transform3D();
		
		overheadTransform3DRotate.setRotation(new AxisAngle4f(0.0f, 1.0f, 0.0f, (float) Math.PI));		
		overheadTransform3DTranslate.setTranslation(new Vector3f(0.0f, 5.0f, -30.0f));		

		overheadCamTranslateGroup.setTransform(overheadTransform3DTranslate);
		overheadCamRotateGroup.setTransform(overheadTransform3DRotate);
			
		
		overheadCamTranslateGroup.addChild(overheadCamRotateGroup);
		
		overheadCamRotateGroup.addChild(camera);
		mainTransformGroup.addChild(overheadCamTranslateGroup);
		
	}

	  private void createLights(BranchGroup graphRoot)
	  {
	    // Create a bounds for the light source influence
	    BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100000.0);

	    // Set up the global, ambient light
	    Color3f alColor = new Color3f(0.7f, 0.7f, 0.7f);
	    AmbientLight aLgt = new AmbientLight(alColor);
	    aLgt.setInfluencingBounds(bounds);
	    graphRoot.addChild(aLgt);
	  }
	  
	public TransformGroup GetMainGroup() {
		return mainTransformGroup;
	}

	public void attachCamera(View u)
	{		
		
		
//		TransformGroup viewTrans = new TransformGroup();
//		viewTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
//		viewTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
//		viewTrans.setCapability(TransformGroup.ALLOW_LOCAL_TO_VWORLD_READ);
//		viewTrans.setTransform(new Transform3D());
		
		u.attachViewPlatform(camera);

	}

	public TransformGroup GetTranslateTransform() {
		// TODO Auto-generated method stub
		return mainTransformGroup;
	}
	
	
	
}
