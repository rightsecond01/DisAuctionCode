package grouplayout;

import java.awt.Color;
import java.net.MalformedURLException;

import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;

public class ChatRight extends JPanel{
    JLabel jlb_right;
    public JLabel jlb_rightimg;
    JPanel chat_right;
    public ChatRight(String id) throws MalformedURLException {
    	jlb_rightimg = new JLabel();
    	chat_right = new ChatRightBubble();
        jlb_right = new JLabel();   
        this.setBackground(new Color(254,231,134));
        this.setAlignmentX(SwingConstants.RIGHT);
        /////////////////RIGHT BUBBLE/////////////////////////////
        GroupLayout chat_rightLayout = new GroupLayout(chat_right);
        chat_right.setLayout(chat_rightLayout);
        chat_rightLayout.setHorizontalGroup(
        		chat_rightLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
        		.addGroup(GroupLayout.Alignment.TRAILING, chat_rightLayout.createSequentialGroup()
        		.addGap(25, 25, 25)
                .addComponent(jlb_rightimg)        
                .addGap(31, 31, 31))
        );
        chat_rightLayout.setVerticalGroup(
        		chat_rightLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
        		.addGroup(chat_rightLayout.createSequentialGroup()
        		.addGap(6, 6, 6) 
        		.addComponent(jlb_rightimg)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(6, 6, 6))
        );
 
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);     
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            		.addGroup(layout.createSequentialGroup()	
            		.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            		.addContainerGap()
            		.addGap(6,6,6)
            		.addContainerGap()
            		.addGap(15,15,15)
            		.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
            		.addComponent(chat_right, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addComponent(jlb_right)
            )
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            	.addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(jlb_right)
                .addComponent(chat_right, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
                .addContainerGap()
                )
        );
    }
}
