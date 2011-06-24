package gameui;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.GraphicsConfigTemplate3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.AxisAngle4f;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.universe.SimpleUniverse;

import javax.media.j3d.*;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.behaviors.keyboard.*;

public class GameView extends Applet implements KeyListener{
	String carsBaseFolder;
	java.net.URL url;
	private View view;
	private Car[] cars;
	public GameView(final String model) throws Exception {
		this.carsBaseFolder = model;

		
		VirtualUniverse vu = new VirtualUniverse();
		Locale locale = new Locale(vu);
		
		setLayout(new BorderLayout());
		Canvas3D canvas = createCanvas();
		add("Center", canvas);
				
		canvas.addKeyListener(this);
		
		BranchGroup worldGroup = new BranchGroup();
		
		view = new View();
		view.setBackClipDistance(2000.0);
		view.setPhysicalBody(new PhysicalBody());
		view.setPhysicalEnvironment(new PhysicalEnvironment());
		view.addCanvas3D(canvas);
		
		
		createLights(worldGroup);
		
		
		Track t = new Track();
		worldGroup.addChild(t.GetMainGroup());
		
		cars = new Car[4];
	    cars[0] = createCar(0);     
	    cars[1] = createCar(1);
	    cars[2] = createCar(2);
	    cars[3] = createCar(3);
	    
	    
        KeyNavigatorBehavior keyNavBeh = new KeyNavigatorBehavior(cars[1].GetTranslateTransform());
        keyNavBeh.setSchedulingBounds(new BoundingSphere(new Point3d(),1000.0));
        worldGroup.addChild(keyNavBeh);

	    
	    
	    //mainGroup.addChild(createLand());
	    worldGroup.addChild(cars[0].GetMainGroup());
	    worldGroup.addChild(cars[1].GetMainGroup());
	    worldGroup.addChild(cars[2].GetMainGroup());
	    worldGroup.addChild(cars[3].GetMainGroup());
		
	    
		worldGroup.compile();
		
		
		
		cars[1].attachCamera(view);
		
		locale.addBranchGraph(worldGroup);
		//locale.addBranchGraph(viewGroup);
		
	}

	private Car createCar(int carNo)
    throws Exception
    {	
      String fileName = carsBaseFolder + "car" + (carNo+1) + "/f360.ms3d";
      
      Car c = new Car(fileName,new Vector3f((6f-carNo*4f),0f,5f),0.08);
      
      return c;
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
	  
	private Canvas3D createCanvas() {
		GraphicsConfigTemplate3D template = new GraphicsConfigTemplate3D();
		GraphicsConfiguration gc1 = GraphicsEnvironment
				.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getBestConfiguration(template);
		return new Canvas3D(gc1);
	}

	public static void main(String args[]) {

		try {
			new MainFrame(
					new GameView(
							"./resources/cars/"),
					800, 600);
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyChar() >= '0' && e.getKeyChar() <= '3')
		{
			cars[e.getKeyChar()-'0'].attachCamera(view);
		}
			
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
