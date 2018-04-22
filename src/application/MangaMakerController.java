package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class MangaMakerController implements Initializable {
	
	/**-Toolbar UP, ID 1 */

    @FXML
    private Button layer1Button;

    @FXML
    private Button layer2Button;

    @FXML
    private Button layer3Button;

    @FXML
    private Button layer4Button;

    @FXML
    private Button layer5Button;
    
  /*---------------------- Other -----------*/
	
	private Scene scene;

	
	
	private double startLineX, startLineY;
	
	
	private double lineWidth = 8;
	
	private Canvas canvas1, canvas2, canvas3, canvas4, canvas5;
	

	
	private int selectedLayer = 1;
	
/*----------------LEFT TOOLBAR------------------*/	
	
	
    @FXML
    private ColorPicker colorPicker;
    

    @FXML
    private Button ceruzaButton;

    @FXML
    private Button resizePartButton;
    
    private int selectedToolbar = 1;
    
    @FXML
    private CheckBox resizeAllCanvases; //is selected all
    
       /*resize elements - tégla*/
    
    private double resizeX = 0;
    private double resizeY = 0;
    private double resizeW = 0;
    private double resizeH = 0;
    private boolean resizeOnProgress = false;
    private boolean beginResized = false;
    private boolean rBegin = false;
    private boolean mBegin = false;
    private int cursor = -1;
    
    private boolean firstMozgas = false;
    
    private final double PROX_DIST = 3;
    
    private WritableImage img1, img2, img3, img4, img5;
    
	
	
/*.----------------------------------*/	
    @FXML
    private Canvas canvas;


    @FXML
    private Label statusBar;


    @FXML
    void layer1OnAction(ActionEvent event) {
    	
    	/*-Set Default button, button1, on toolbar UP*/
    	setDefaultButton(1, 1);
    	
    }

    private void setDefaultButton(int toolbarID, int buttonId) {
    	
    	falseToolbarVarables();
		// TODO Auto-generated method stub
    	
    	/*-if toolbar from UP*/
    	
    	if (toolbarID == 1) {
    		switch (buttonId) {
    		
    		case 1: layer1Button.setDefaultButton(true); layer2Button.setDefaultButton(false); 
    			layer3Button.setDefaultButton(false); layer4Button.setDefaultButton(false);
    			layer5Button.setDefaultButton(false); selectedLayer = 1; break;
    			
    		case 2: layer2Button.setDefaultButton(true); layer1Button.setDefaultButton(false); 
    			layer3Button.setDefaultButton(false); layer4Button.setDefaultButton(false);
    			layer5Button.setDefaultButton(false); selectedLayer = 2; break;
			
    		case 3:layer3Button.setDefaultButton(true); layer2Button.setDefaultButton(false); 
				layer1Button.setDefaultButton(false); layer4Button.setDefaultButton(false);
				layer5Button.setDefaultButton(false); selectedLayer = 3; break;
			
    		case 4: layer4Button.setDefaultButton(true); layer2Button.setDefaultButton(false); 
				layer3Button.setDefaultButton(false); layer1Button.setDefaultButton(false);
				layer5Button.setDefaultButton(false); selectedLayer = 4; break;
			
    		case 5: layer5Button.setDefaultButton(true); layer2Button.setDefaultButton(false); 
				layer3Button.setDefaultButton(false); layer4Button.setDefaultButton(false);
				layer1Button.setDefaultButton(false); selectedLayer = 5; break;
    		
    		}
    		
    	} else {
    		if (toolbarID == 2) {
    			
    			switch (buttonId) {
    			case 1: ceruzaButton.setDefaultButton(true); resizePartButton.setDefaultButton(false);
    			selectedToolbar = 1; break;
    			
    			case 2:  ceruzaButton.setDefaultButton(false); resizePartButton.setDefaultButton(true);
    			selectedToolbar = 2; break;
    			
    			}
    		}
    	}
    	
		
	}

	@FXML
    void layer2OnAction(ActionEvent event) {
		
		setDefaultButton(1, 2);
    }

    @FXML
    void layer3OnAction(ActionEvent event) {
    	
    	setDefaultButton(1, 3);
    }

    @FXML
    void layer4OnAction(ActionEvent event) {
    	
    	setDefaultButton(1, 4);
    }

    @FXML
    void layer5OnAction(ActionEvent event) {
    	
    	setDefaultButton(1, 5);
    }

	public void setScene(Scene scene) {
		// TODO Auto-generated method stub
		this.scene = scene;
	}
	
	

    @FXML
    void canvasOnMouseClicked(MouseEvent event) {

    }

    @FXML
    void canvasOnMouseDraged(MouseEvent event) {
    	//System.out.println(canvas.getWidth() +" " + canvas.getHeight())
    	
    	double mouseX = event.getX();
    	double mouseY = event.getY();
    	
    	switch (selectedToolbar) {

    	case 1:
	    	
	    	switch (selectedLayer) {
	    	case 1: drawLineToCanvas(canvas1, mouseX, mouseY); break;
	    	case 2: drawLineToCanvas(canvas2, mouseX, mouseY); break;
	    	case 3: drawLineToCanvas(canvas3, mouseX, mouseY); break;
	    	case 4: drawLineToCanvas(canvas4, mouseX, mouseY); break;
	    	case 5: drawLineToCanvas(canvas5, mouseX, mouseY); break;
	    	
	    	}
	    	
	    	startLineX = mouseX;
	    	
	    	startLineY = mouseY;
	    	
	    	break;
    	
	    	case 2:
    			
	    		
	    		if (beginResized && !resizeOnProgress) {
	    			//itt történik a növesztés, mozgatás, stb.
	    			switch(kockanKivul(mouseX, mouseY)) {
	    			case 0: resizeKocka(mouseX, mouseY) ; rBegin = true; break; //átméretezés
	    			case 1: moveKocka(mouseX, mouseY); 
	    				if (!mBegin) {
	    					mBegin = true;
	    					if (!firstMozgas) {
	    						img1 = this.getPartOfImage(canvas1, (int)resizeX, (int)resizeY, (int)resizeW, (int)resizeH);
	    						img2 = this.getPartOfImage(canvas2, (int)resizeX, (int)resizeY, (int)resizeW, (int)resizeH);
	    						img3 = this.getPartOfImage(canvas3, (int)resizeX, (int)resizeY, (int)resizeW, (int)resizeH);
	    						img4 = this.getPartOfImage(canvas4, (int)resizeX, (int)resizeY, (int)resizeW, (int)resizeH);
	    						img5 = this.getPartOfImage(canvas5, (int)resizeX, (int)resizeY, (int)resizeW, (int)resizeH);
    							
	    						
	    						
	    						firstMozgas = true;
	    						
	    						setDisableResizeValues(true);
	    						
	    						
	    						/**már meg van rajzolva a kocka*/
	    						if (resizeAllCanvases.isSelected()) { //ha mindegyiken
	    							System.out.println("fsdf");
	    							clearRectOnCanvas(resizeX, resizeY, resizeW, resizeH, canvas1, Color.TRANSPARENT);
	    							clearRectOnCanvas(resizeX, resizeY, resizeW, resizeH, canvas2, Color.TRANSPARENT);
	    							clearRectOnCanvas(resizeX, resizeY, resizeW, resizeH, canvas3, Color.TRANSPARENT);
	    							clearRectOnCanvas(resizeX, resizeY, resizeW, resizeH, canvas4, Color.TRANSPARENT);
	    							clearRectOnCanvas(resizeX, resizeY, resizeW, resizeH, canvas5, Color.TRANSPARENT);
	    							drawCanvasesToCanvas();
	    						} else {
	    							clearRectOnCanvas(resizeX, resizeY, resizeW, resizeH, selectedCanvas(), Color.TRANSPARENT);
	    						}
	    						
	    					}
	    					
	    				}
	    			
	    			break; //mozgatás
	    			case -1: beginResized = false; resizeOnProgress = false; firstMozgas = false; 
	    				drawCanvasesToCanvas();
	    			break; //új átméretezés
	    			
	    			}
	    			
	    			
	    		} else {
	    			
	    			drowResizeRecToCanvas6(mouseX, mouseY); 
		    		
		    		
		    		
		    		if (!beginResized) {
		    			beginResized = true;
		    			resizeOnProgress = true;
		    		}
	    			
	    			
	    		}
	    		
	    	break;
    	
    	}
    	
    	
    	
    }

   
	private void setDisableResizeValues(boolean value) {
		// TODO Auto-generated method stub
		resizeAllCanvases.setDisable(value);
		this.layer1Button.setDisable(value);
		this.layer2Button.setDisable(value);
		this.layer3Button.setDisable(value);
		this.layer4Button.setDisable(value);
		this.layer5Button.setDisable(value);
		
	}

	private Canvas selectedCanvas() {
		// TODO Auto-generated method stub
    	switch(selectedLayer) {
    	case 1: return canvas1;
    	case 2: return canvas2;
    	case 3: return canvas3;
    	case 4: return canvas4;
    	case 5: return canvas5;
    	}
    	
    	
		return null;
	}
	
	
	private WritableImage getSelectedImage() {
		// TODO Auto-generated method stub
    	switch(selectedLayer) {
    	case 1: return img1;
    	case 2: return img2;
    	case 3: return img3;
    	case 4: return img4;
    	case 5: return img5;
    	}
    	
    	
		return null;
	}

	private void moveKocka(double mouseX, double mouseY) {
		// TODO Auto-generated method stub
    	//drowResizeRecToCanvas6(mouseX, mouseY);
    	resizeX = resizeX + mouseX - startLineX;
    	resizeY = resizeY + mouseY - startLineY;
    	
    	Canvas canvas6 = new Canvas(canvas.getWidth(), canvas.getHeight());
    	fillCanvas(0, 0, canvas6, Color.TRANSPARENT);
    	
    	GraphicsContext gc = canvas6.getGraphicsContext2D();
    	if (this.resizeAllCanvases.isSelected()) {
    		gc.drawImage(img1, resizeX, resizeY);
    		gc.drawImage(img2, resizeX, resizeY);
    		gc.drawImage(img3, resizeX, resizeY);
    		gc.drawImage(img4, resizeX, resizeY);
    		gc.drawImage(img5, resizeX, resizeY);	
    	} else {
    		
    		switch (selectedLayer) {
    		case 1: 
    			gc.drawImage(img1, resizeX, resizeY);
    			gc.drawImage(convertCanvasToImage(canvas1), 0, 0);
    			gc.drawImage(convertCanvasToImage(canvas2), 0, 0);
    			gc.drawImage(convertCanvasToImage(canvas3), 0, 0);
    			gc.drawImage(convertCanvasToImage(canvas4), 0, 0);
    			gc.drawImage(convertCanvasToImage(canvas5), 0, 0);
    			break;
    			
    		case 2: 
    			gc.drawImage(convertCanvasToImage(canvas1), 0, 0);
    			gc.drawImage(img2, resizeX, resizeY);
    			gc.drawImage(convertCanvasToImage(canvas3), 0, 0);
    			gc.drawImage(convertCanvasToImage(canvas4), 0, 0);
    			gc.drawImage(convertCanvasToImage(canvas5), 0, 0);
    			break;
    			
    		case 3:
    			gc.drawImage(convertCanvasToImage(canvas1), 0, 0);
    			gc.drawImage(convertCanvasToImage(canvas2), 0, 0);
    			gc.drawImage(img3, resizeX, resizeY);
    			gc.drawImage(convertCanvasToImage(canvas4), 0, 0);
    			gc.drawImage(convertCanvasToImage(canvas5), 0, 0);
    			break;
    			
    		case 4:
    			gc.drawImage(convertCanvasToImage(canvas1), 0, 0);
    			gc.drawImage(convertCanvasToImage(canvas2), 0, 0);
    			gc.drawImage(convertCanvasToImage(canvas3), 0, 0);
    			gc.drawImage(img4, resizeX, resizeY);
    			gc.drawImage(convertCanvasToImage(canvas5), 0, 0);
    			break;
    			
    		case 5: 
    			gc.drawImage(convertCanvasToImage(canvas1), 0, 0);
    			gc.drawImage(convertCanvasToImage(canvas2), 0, 0);
    			gc.drawImage(convertCanvasToImage(canvas3), 0, 0);
    			gc.drawImage(convertCanvasToImage(canvas4), 0, 0);
    			gc.drawImage(img5, resizeX, resizeY);
    			break;
    			
    		}
    		
    		
    		//gc.drawImage(getSelectedImage(), resizeX, resizeY);
    	}
    	
    	
    	gc.setStroke(Color.BLACK);
    	
    	gc.setLineDashes(4.2, 5.1, 6.1, 5.3, 6.4, 1.5);
    	gc.strokeRect(resizeX, resizeY, resizeW, resizeH);
    	
    	drawCanvasesToCanvas();
    	
    	canvas.getGraphicsContext2D().drawImage( convertCanvasToImage(canvas6), 0, 0);
    	
    	startLineX = mouseX;
    	startLineY = mouseY;
    	
    	/*darab kép mozgatása*
    	 * 
    	 * 
    	 * 
    	 * */
    	
    	/*PixelReader reader = oldImage.getPixelReader();
WritableImage newImage = new WritableImage(reader, x, y, width, height);*/
    	
    
    	
    	
    	
		
	}
	
	
	private WritableImage getPartOfImage(Canvas cv, int x, int y, int w, int h) {
		
		PixelReader reader = convertCanvasToImage(cv).getPixelReader();
		return new WritableImage(reader, x, y, w, h);
	}

    
    
	private void resizeKocka(double mouseX, double mouseY) {
		// TODO Auto-generated method stub
		
	/*	
		if (mouseX >= startLineX && mouseY >= startLineY) {
			resizeW = resizeW + mouseX - startLineX;
			resizeH = resizeH + mouseY - startLineY;
			
			resizeX = resizeX < mouseX? resizeX: mouseX;
			resizeY = resizeY < mouseY? resizeY: mouseY;
		} else {
			resizeW = resizeW + startLineX - mouseX;
			resizeH = resizeH + startLineY - mouseY;
			
			resizeX = resizeX < mouseX? resizeX: mouseX;
			resizeY = resizeY < mouseY? resizeY: mouseY;
			
			
		} */ 
		
		double dx = Math.abs(mouseX - startLineX);
		double dy = Math.abs(mouseY - startLineY);
		
		if (startLineX < resizeX + 3 && startLineX >= resizeX - 3) {
			if (startLineX - mouseX > 0) { // ha összehúzzuk balról
				resizeW = resizeW + dx;
				resizeX = mouseX;
				
			} else {
				resizeW = resizeW - dx;
				resizeX = mouseX;
				
			}
			
		} else {
			if (startLineX - mouseX > 0) { // ha összehúzzuk balról
				resizeW = resizeW - dx;
				
				
			} else {
				resizeW = resizeW + dx;
			
				
			}
			
		}
		
		
		
		
		if (startLineY < resizeY + 3 && startLineY >= resizeY - 3) {
			if (startLineY - mouseY > 0) { // ha összehúzzuk balról
				resizeH = resizeH + dy;
				resizeY = mouseY;
				
			} else {
				resizeH = resizeH - dy;
				resizeY = mouseY;
				
			}
			
		} else {
			if (startLineY - mouseY > 0) { // ha összehúzzuk balról
				resizeH = resizeH - dy;
				
				
			} else {
				resizeH = resizeH + dy;
			
				
			}
			
		}
		

	
		
		
		Canvas canvas6 = new Canvas(canvas.getWidth(), canvas.getHeight());
    	fillCanvas(0, 0, canvas6, Color.TRANSPARENT);
    	
    	GraphicsContext gc = canvas6.getGraphicsContext2D();
    	gc.setStroke(Color.BLACK);
    	
    	gc.setLineDashes(4.2, 5.1, 6.1, 5.3, 6.4, 1.5);
    	gc.strokeRect(resizeX, resizeY, resizeW, resizeH);
    	
    	drawCanvasesToCanvas();
    	
    	canvas.getGraphicsContext2D().drawImage( convertCanvasToImage(canvas6), 0, 0);
    	
    	startLineX = mouseX;
    	startLineY = mouseY;
		
		
	}

	private void drowResizeRecToCanvas6(double mouseX, double mouseY) {
		// TODO Auto-generated method stub
    	Canvas canvas6 = new Canvas(canvas.getWidth(), canvas.getHeight());
    	fillCanvas(0, 0, canvas6, Color.TRANSPARENT);
    	
    	double novX = mouseX < startLineX? mouseX:startLineX;
    	double novY = mouseY < startLineY? mouseY:startLineY;
    	
    	double novW = Math.abs(mouseX - startLineX);
    	double novH = Math.abs(mouseY - startLineY);
    	
    	GraphicsContext gc = canvas6.getGraphicsContext2D();
    	gc.setStroke(Color.BLACK);
    	
    	//gc.strokeRect(startLineX, startLineY, mouseX - startLineX, mouseY - startLineY);
    
    	gc.setLineDashes(4.2, 5.1, 6.1, 5.3, 6.4, 1.5);
    	gc.strokeRect(novX, novY, novW, novH);
    	drawCanvasesToCanvas();
    	
    	canvas.getGraphicsContext2D().drawImage( convertCanvasToImage(canvas6), 0, 0);
    	
    	
    	/*global varables set*/
    	
    	resizeX = novX;
    	resizeY = novY;
    	resizeW = novW;
    	resizeH = novH;
    	
	}

	private void drawLineToCanvas(Canvas cv, double mouseX, double mouseY) {
		// TODO Auto-generated method stub
    	GraphicsContext gc = cv.getGraphicsContext2D();
    	
    	gc.setStroke(colorPicker.getValue());
    	
    	gc.setLineWidth(lineWidth);

    	
    	gc.strokeLine(startLineX, startLineY, mouseX, mouseY);
    	
    	drawCanvasesToCanvas();
    	
    	
    	
		
	}
	

	 
	
	
	
	

	private void drawCanvasesToCanvas() {
		// TODO Auto-generated method stub
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		fillCanvas(0, 0, canvas.getWidth(), canvas.getHeight(), canvas, Color.WHITE);
         
       
		gc.drawImage(convertCanvasToImage(canvas1), 0, 0);
         gc.drawImage(convertCanvasToImage(canvas2), 0, 0);
         gc.drawImage(convertCanvasToImage(canvas3), 0, 0);
         gc.drawImage(convertCanvasToImage(canvas4), 0, 0);
         gc.drawImage(convertCanvasToImage(canvas5), 0, 0);
		
		
	}

	private WritableImage convertCanvasToImage(Canvas cv) {
		// TODO Auto-generated method stub
		
		SnapshotParameters params = new SnapshotParameters();
		
		params.setFill(Color.TRANSPARENT);
		
		 WritableImage writableImage1 = new WritableImage((int)cv.getWidth(), (int)cv.getHeight());
		 
         cv.snapshot(params, writableImage1);
		
         
		return writableImage1;
	}

	@FXML
    void canvasOnMouseEntered(MouseEvent event) {

    }

    @FXML
    void canvasOnMouseExited(MouseEvent event) {

    }

    @FXML
    void canvasOnMouseMoved(MouseEvent event) {
    	
    	statusBar.setText("(" + event.getX() + ", " + event.getY() + ")");
    	
    	switch (selectedToolbar) {
    	//case 1: break;
    	case 2: 
    		/*ha már befejeztük a húzogatást*/
    		if (!this.resizeOnProgress && this.beginResized) {
    			
    			double mouseX = event.getX();
    			
    			double mouseY = event.getY();
    			
    			switch (kockanKivul(mouseX, mouseY)) {
    			case 1: scene.setCursor(Cursor.HAND); cursor = 1; break;
    			case -1: scene.setCursor(Cursor.DEFAULT); cursor = -1; break;
    			case 0: scene.setCursor(Cursor.CROSSHAIR); cursor = 0;
    			}
    		}
    	}
    }
    
    
    public int kockanKivul (double mouseX, double mouseY) {

    	if (rBegin) {
    		return 0;
    	}
    	
    	if (mBegin) { 
    		return 1;
    	}
    
    	
		if (mouseX > resizeX + PROX_DIST && mouseX + PROX_DIST < resizeX + resizeW &&
				mouseY > resizeY + PROX_DIST && mouseY + PROX_DIST < resizeY + resizeH) {
			return 1;
			
			
		} else 
			if (mouseX + PROX_DIST < resizeX || mouseX > resizeX + resizeW + PROX_DIST ||
					mouseY + PROX_DIST < resizeY || mouseY > resizeY + resizeH + PROX_DIST) {
				
				return -1;
			} else {
				
				return 0;
			}
    }

    @FXML
    void canvasOnMousePressed(MouseEvent event) {
    	startLineX = event.getX();
    	startLineY = event.getY();
    
    }

    @FXML
    void canvasOnMouseReleased(MouseEvent event) {
    	switch (selectedToolbar) {
    	//case 1: /*fentiegyenlőre semmi más*/ break;
    	case 2: /*középső*/ 
    			
    			if (this.kockanKivul(event.getX(), event.getY()) == -1) {
    				berajzol();
    				
    				this.drawCanvasesToCanvas();
    				img1= null;
    				img2 = null;
    				img3 = null;
    				img4 = null;
    				img5 = null;
    				this.setDisableResizeValues(false);
    			}
    			
    			resizeOnProgress = false;
    			rBegin = false;
    			mBegin = false;
    			
    			break;
    		
    		}
    }
    
    public void berajzol() {
    	
    	if (this.resizeAllCanvases.isSelected()) {
    		
    		copyImgToCanvas(img1, canvas1);
    		copyImgToCanvas(img2, canvas2);
    		copyImgToCanvas(img3, canvas3);
    		copyImgToCanvas(img4, canvas4);
    		copyImgToCanvas(img5, canvas5);
    		
    	} else {
    		
    		switch (selectedLayer) {
    		case 1: copyImgToCanvas(img1, canvas1); break;
    		case 2: copyImgToCanvas(img2, canvas2); break;
    		case 3: copyImgToCanvas(img3, canvas3); break;
    		case 4: copyImgToCanvas(img4, canvas4); break;
    		case 5: copyImgToCanvas(img5, canvas5); break;
    			
    		}
    		
    		
    		
    	}
    	
    }

    
    
    private void copyImgToCanvas(WritableImage im, Canvas cv) {
		// TODO Auto-generated method stub
    	
    	GraphicsContext gf = cv.getGraphicsContext2D();
    	gf.drawImage(im, resizeX, resizeY);
		
	}

	public void falseToolbarVarables() {
    	switch (selectedToolbar) {
    	//case 1: break;
    	case 2: 
    		this.resizeOnProgress = false;
    		this.beginResized = false;
    		this.rBegin = false;
    		this.firstMozgas = false;
    		break;
    		}
    	
    }

	
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		canvas.setWidth(1000);
		canvas.setHeight(1000);
		// TODO Auto-generated method stub
		final double w = canvas.getWidth();
		final double h = canvas.getHeight(); 
		
	
		
		fillCanvas(0, 0, w, h, canvas, Color.WHITE);
		
		canvas1 = new Canvas(w, h);
		fillCanvas(0, 0, w, h, canvas1, Color.TRANSPARENT);
		

		canvas2 = new Canvas(w, h);
		fillCanvas(0, 0, w, h, canvas2, Color.TRANSPARENT);
		

		canvas3 = new Canvas(w, h);
		fillCanvas(0, 0, w, h, canvas1, Color.TRANSPARENT);
		

		canvas4 = new Canvas(w, h);
		fillCanvas(0, 0, w, h, canvas1, Color.TRANSPARENT);
		

		canvas5 = new Canvas(w, h);
		fillCanvas(0, 0, w, h, canvas1, Color.TRANSPARENT);
		
		colorPicker.setValue(Color.BLACK);

		
	}

	private void fillRecOnColorMap(int x, int y, int w, int h, Color[][] cm, Color cl) {
		// TODO Auto-generated method stub
		for (int i = 0; i < w; ++i) {
			for (int j = 0; j < h; ++j) {
				cm[i][j] = cl;
			}
			
		}
		
	}

	private void fillCanvas(double x, double y, double w, double h, Canvas cv, Color color) {
		// TODO Auto-generated method stub
		GraphicsContext gc = cv.getGraphicsContext2D();
		gc.setFill(color);
		gc.fillRect(x, y, w, h);
		
	}
	
	
	private void clearRectOnCanvas(double x, double y, double w, double h, Canvas cv, Color color) {
		// TODO Auto-generated method stub
		GraphicsContext gc = cv.getGraphicsContext2D();
		gc.setFill(color);
		gc.clearRect(x, y, w, h);
		
	}
	
	
	
	private void fillCanvas(double x, double y, Canvas cv, Color color) {
		fillCanvas(x, y, cv.getWidth(), cv.getHeight(), cv, color);
		
	}
	
	
	

    @FXML
    void ceruzaButtonOnAction(ActionEvent event) {
    	setDefaultButton(2, 1);
    }
    
    

    @FXML
    void resizeButtonInAction(ActionEvent event) {
    	setDefaultButton(2, 2);
    }


}
