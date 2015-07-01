package insta;
 
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;





import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.SwingConstants;





import org.jinstagram.entity.comments.CommentData;
import org.jinstagram.entity.common.Caption;
import org.jinstagram.entity.common.Comments;
import org.jinstagram.entity.common.Images;
import org.jinstagram.entity.common.User;
import org.jinstagram.exceptions.InstagramException;

import static java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
 
@SuppressWarnings("MagicConstant")
public class AppWindow {
	
	JFrame frame;
    
    JFrame loginFrame;
   
    static JLayeredPane layeredPane;
   
    static AppWindow window;
   
    static JLabel PictureLabel;
    static JLabel CaptionLabel;
    static JLabel UploaderImageLabel;
    static JLabel UploaderNameLabel;
    static JLabel commenterProfilePictureLabel;
    static JLabel commentLabel;
    
    JPanel WelcomePanel;
   
    static int width;
    static int height;
    static int count = 0;
   
    static ArrayList<Images> imagesList;
    static ArrayList<Caption> captionsList;
    static ArrayList<User> usersList;
    static ArrayList<Comments> commentsList;
   
    InstagramDataReceiver receiver;
   
    static JLabel commenterProfilePicture[];
    static JLabel comment[];
    
    LayoutMetrics metrics;
    
    public String userId;

    boolean useProxy = false;
 
    public AppWindow(String userId) throws IOException{
    	
    	this.userId = userId;
    	System.out.println("receivedInAppWindow " + userId);
        
    }
       
        /**
         * Launch the application.
         * @throws IOException
         */
        public void start(String userId) throws IOException {
        	this.userId = userId;
               
        		EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        window = null;
                        try {
                            window = new AppWindow();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        window.frame.setVisible(true);
                    }

                });
                
                   
                   Timer timer = new Timer();
                   timer.schedule(new TimerTask() {

                       @Override
                       public void run() {
                           if (imagesList != null) {
                               if (count < imagesList.size()) {
                                   //System.out.println(imagesList.size()+"-"+count);///
                                   try {
                                       updateFrame(count);
                                   } catch (IOException e) {
                                       // TODO Auto-generated catch block
                                       e.printStackTrace();
                                   }
                                   count += 1;
                               } else {
                                   count = 0;
                                   try {
                                       updateFrame(count);
                                   } catch (IOException e) {
                                       // TODO Auto-generated catch block
                                       e.printStackTrace();
                                   }
                                   count++;
                               }

                           }

                       }

                   }, 0, 3000);
                   
               }
               
       
               
       
 
        /**
         * Create the application.
         * @throws IOException
         * @wbp.parser.entryPoint
         */
        public AppWindow() throws IOException {
 
                initialize();
               
        }
 
        /**
         * Initialize the contents of the frame.
         * @throws IOException
         */
        private void initialize() throws IOException {

                initializeScreenDimensions();
                initializeInstagramData();
                initializeDataArrays();
                initializeMainFrame();
                initializeLayoutType();
                initializeBackground();
                initializeMediaFields();
                updateFrame(0);
               
        }
       
        void updateFrame(int position) throws IOException{ /// position - start from 1
               
                System.out.println(position);
                removeOldMedia();
                setMainPicture(position);
                setCaption(position);
                setUploaderProfilePicture(position);
                setUploaderName(position);
                setComments(position);
               // dataRefresherThread(position);
               
        }
       
        String getPictureUrl(Images inputData) throws IOException{
               
                String html="";
                String input = inputData.getStandardResolution().toString();
               
                int index = input.indexOf("imageUrl") + 9;
               
                while(input.charAt(index) != ','){
                       
                        html+=input.charAt(index);
                        index++;
                       
                }
                       
                return html;
               
        }
       
        void initializeScreenDimensions(){
               
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
           
                Double widthDouble = screenSize.getWidth();
                Double heightDouble = screenSize.getHeight();
       
                height = heightDouble.intValue();
                width = widthDouble.intValue();
                
                metrics = new LayoutMetrics(width,height);
               
        }
       
        void initializeInstagramData() throws InstagramException{
               
                receiver= new InstagramDataReceiver("vilniuscc");
                receiver.getData();
               
        }
       
        void initializeDataArrays(){

            imagesList = receiver.getImages();
            captionsList = receiver.getCaptions();
            usersList = receiver.getUsers();
            commentsList = receiver.getComments();

        }

        void initializeMainFrame() throws IOException{
               
            frame = new JFrame();
            frame.setBounds(0, 0, width, height);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
            frame.setUndecorated(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
               
        }
       
        void initializeLayoutType(){
               
                frame.getContentPane().setLayout(new CardLayout(0, 0));
            layeredPane = new JLayeredPane();
            frame.getContentPane().add(layeredPane, "name_28865865238250");
               
        }
       
        void initializeMediaFields(){
               
            PictureLabel = new JLabel();
            layeredPane.setLayer(PictureLabel,1);
           
            CaptionLabel = new JLabel();
            layeredPane.setLayer(CaptionLabel, 1);
            
            CaptionLabel.setForeground(Color.ORANGE);
            CaptionLabel.setFont(new Font("Tahoma", Font.PLAIN, 50));
            
            UploaderImageLabel = new JLabel();
            layeredPane.setLayer(UploaderImageLabel, 1);
           
            UploaderNameLabel = new JLabel();
            UploaderNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
            UploaderNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
            UploaderNameLabel.setForeground(Color.ORANGE);
            layeredPane.setLayer(UploaderNameLabel, 1);
               
        }
       
        void initializeBackground() throws IOException{
               
            JLabel BackgroundLabel = new JLabel();
            layeredPane.setLayer(BackgroundLabel, 0);
            BackgroundLabel.setOpaque(true);
            BackgroundLabel.setBackground(Color.BLACK);
            BackgroundLabel.setBounds(0, 0, width, height);
            System.out.println("changed");
            layeredPane.add(BackgroundLabel);
               
        }
       
        void setMainPicture(int pos) throws MalformedURLException, IOException{
           
            URL imageUrl = new URL(getPictureUrl(imagesList.get(pos)));
            
            Point point = getMaximumImageSize();
            
            metrics.imageBounds.width = point.x;
            metrics.imageBounds.height = point.y;
            
            int centeredY = (int)(height / 2 - metrics.imageBounds.getHeight() / 2);

            if(useProxy) {

                ConnectionProxy conProxy = new ConnectionProxy();
                Proxy proxy = conProxy.getProxy();

                URLConnection urlConnection = imageUrl.openConnection(proxy);
                InputStream inStream = urlConnection.getInputStream();

                Image image = ImageIO.read(inStream).getScaledInstance((int) point.x, point.y, Image.SCALE_SMOOTH);

                PictureLabel.setIcon(new ImageIcon(image));

            } else {

                PictureLabel.setIcon(new ImageIcon(ImageIO.read(imageUrl).getScaledInstance((int)point.x, point.y, Image.SCALE_SMOOTH)));

            }
           
            PictureLabel.setBounds((int) metrics.imageBounds.getX(), centeredY, point.x, point.y);
            layeredPane.add(PictureLabel);
               
        }
       
        void setCaption(int pos){
               
        	
        	metrics.captionBounds.y = (int)metrics.imageBounds.getY() / 2;
        	metrics.captionBounds.x = (int)metrics.imageBounds.getX();
        			
            CaptionLabel.setText(captionsList.get(pos).getText());
            CaptionLabel.setBounds(metrics.captionBounds);
            layeredPane.add(CaptionLabel);
           
               
        }
       
        void setUploaderProfilePicture(int pos) throws IOException {
 
            UploaderImageLabel.setBounds(metrics.uploaderImageBounds);
           
            URL userUrl = new URL(usersList.get(pos).getProfilePictureUrl());

            ConnectionProxy conProxy = new ConnectionProxy();
            Proxy proxy = conProxy.getProxy();

            if(useProxy) {

                URLConnection urlConnection = userUrl.openConnection(proxy);
                InputStream inStream = urlConnection.getInputStream();

                Image image = ImageIO.read(inStream).getScaledInstance((int) metrics.uploaderImageBounds.getWidth(), (int) metrics.uploaderImageBounds.getHeight(), Image.SCALE_SMOOTH);

                UploaderImageLabel.setIcon(new ImageIcon(image));

            } else UploaderImageLabel.setIcon(new ImageIcon(ImageIO.read(userUrl).getScaledInstance((int)metrics.uploaderImageBounds.getWidth(), (int)metrics.uploaderImageBounds.getHeight(), Image.SCALE_SMOOTH)));
           
            layeredPane.add(UploaderImageLabel);
               
        }
       
        void setUploaderName(int pos){
               
            UploaderNameLabel.setText(usersList.get(pos).getUserName());
            UploaderNameLabel.setBounds(metrics.uploaderNameBounds);
            layeredPane.add(UploaderNameLabel);
               
        }
       
        void setComments(int pos) throws IOException{

                List<CommentData> commentsData = commentsList.get(pos).getComments();
                int commentsCount = commentsData.size();
                int startPositionY = (int)metrics.commenterImageBounds.getY();
               
                if(commentsCount > 0){
                	
                        commenterProfilePicture = new JLabel[commentsCount];
                        comment = new JLabel[commentsCount];
                        
                        for(int i = 0;i<commentsCount;i++){
                        	
                                commenterProfilePicture[i] = new JLabel("");
                                comment[i] = new JLabel("");
                                layeredPane.setLayer(commenterProfilePicture[i], 1);
               
                                URL commenterPictureUrl = new URL(commentsData.get(i).getCommentFrom().getProfilePicture());

                                if(useProxy) {

                                    ConnectionProxy conProxy = new ConnectionProxy();
                                    Proxy proxy = conProxy.getProxy();

                                    URLConnection urlConnection = commenterPictureUrl.openConnection(proxy);
                                    InputStream inStream = urlConnection.getInputStream();

                                    Image image = ImageIO.read(inStream).getScaledInstance((int) metrics.commenterImageBounds.getWidth(), (int) metrics.commenterImageBounds.getHeight(), Image.SCALE_SMOOTH);

                                    commenterProfilePicture[i].setIcon(new ImageIcon(image));

                                } else commenterProfilePicture[i].setIcon(new ImageIcon(ImageIO.read(commenterPictureUrl).getScaledInstance((int)metrics.commenterImageBounds.getWidth(),(int)metrics.commenterImageBounds.getHeight(),Image.SCALE_SMOOTH)));

                                commenterProfilePicture[i].setBounds((int)metrics.commenterImageBounds.getX(), startPositionY, (int)metrics.commenterImageBounds.getWidth(), (int)metrics.commenterImageBounds.getHeight());
                                layeredPane.add(commenterProfilePicture[i]);
                               
                                comment[i].setText(commentsData.get(i).getText());
                                comment[i].setForeground(Color.ORANGE);
                                comment[i].setFont(new Font("Tahoma", Font.PLAIN, 15));
                                comment[i].setBounds((int)metrics.commentBounds.getX(), startPositionY, (int)metrics.commentBounds.getWidth(), (int)metrics.commentBounds.getHeight());
                                layeredPane.setLayer(comment[i], 1);
                                layeredPane.add(comment[i]);
                               
                                startPositionY+=70;
                                
                        }
                       
                }
               
        }
       
        void removeOldMedia(){
               
                if(commenterProfilePicture != null && commenterProfilePicture.length > 0){
                        for(int i=0;i<commenterProfilePicture.length;i++){
                                layeredPane.remove(commenterProfilePicture[i]);
                                layeredPane.remove(comment[i]);
                        }
                       
                }
               
                layeredPane.remove(PictureLabel);
                layeredPane.remove(CaptionLabel);
                layeredPane.remove(UploaderImageLabel);
                layeredPane.remove(UploaderNameLabel);
               
                layeredPane.revalidate();
                layeredPane.repaint();
               
        }
        
        void initializeMetrics(){
        	
        	int  width = getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
            int height = getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
                metrics = new LayoutMetrics(width,height);
        	
        }
        
        Point getMaximumImageSize(){
        	
        	int endX = (int)(width / 2 - metrics.imageBounds.getX() * 2);
        	int endY = (int)(height - metrics.imageBounds.getY() * 2);
        	
        	int maxX = 0;
        	int maxY = 0;
        	
        	while(maxX < endX && maxY < endY){
        		
        		maxX++;
        		maxY++;
        		
        	}
        	
        	return new Point(maxX,maxY);
        
        }
        
        

        void setUserId(String toSet) throws IOException{
        	
        	this.userId = toSet;
                 
        }
        
        void loadLoginScreen() throws IOException{
        
        	initializeScreenDimensions();
        	LoginScreen loginScreen = new LoginScreen(width,height);
        	
        	initializeScreenDimensions();
            initializeInstagramData();
            initializeDataArrays();
            initializeMainFrame();
        	
        	frame.getContentPane().setLayout(new CardLayout(0, 0));
            layeredPane = new JLayeredPane();
            frame.getContentPane().add(loginScreen.getInitializedLoginPanel(), "name_28865865238250");
            frame.setVisible(true);
            
            JButton button = loginScreen.getInitializetButton();
            
            final JTextField textField = loginScreen.getInitializetTextField();
            
            button.addActionListener(new ActionListener()
    		{
    		  public void actionPerformed(ActionEvent e)
    		  {
    		    // display/center the jdialog when the button is pressed
    			  if(textField.getText().toString().length() > 0){
    				  
    				try {

    					 start(textField.getText().toString());
    
    				} catch (IOException e1) {
    					// TODO Auto-generated catch block
    					e1.printStackTrace();
    				}
    				  
    			  }

    		  }
    		});
        	
        }

    void dataRefresherThread(int position) throws InstagramException {

        if(position == imagesList.size() - 1){

            initializeInstagramData();
            initializeDataArrays();

        }


    }
        
}