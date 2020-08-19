//Nombre: Adnan Abdul Khalek, Cedula: 27750460, Proyecto FINAL

//Primera Edicion
//Faltaria poner lo de  si es publico o privado, y otras cosas mas


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.Math;

public class CALCULADORA extends JPanel{
	    
    JTextField JTIP1;
    JTextField JTIP2;
    JTextField JTIP3;
    JTextField JTIP4;
    JTextField JTsub;
    JTextField JTH;
    JTextArea Jarea;
    JLabel mask1;
    JLabel smask1;    
    JLabel Lsaltos,LN;
    JLabel mask2;
    JButton Calip;
    boolean ban = false;
    boolean ban1 = false;
    int C1, H, S, sal = 0;
    int n, n1;
    int oc1= 0, oc2 = 0, oc3 = 0, oc4 = 0;
    int TS = 0;
    int TH= 0;
    int A,B,C,D;
        
    JRadioButton BotonHost, BotonS;
	ButtonGroup BGGrupoBotonesOpcion;      

	KeyAdapter Validacion = new KeyAdapter(){
		public void keyReleased(KeyEvent TeclaObjeto) {
			int TeclaValor = TeclaObjeto.getKeyCode();
			char TeclaCaracter = TeclaObjeto.getKeyChar();
			if((TeclaValor >= 48 && TeclaValor <= 57) || TeclaValor == KeyEvent.VK_BACK_SPACE){
				JTextField textField = (JTextField) TeclaObjeto.getSource();
				String text = textField.getText();
			}else
				if (TeclaValor == KeyEvent.VK_ENTER)
					((JTextField)TeclaObjeto.getSource()).transferFocus();
				else{
					JTextField textField = (JTextField) TeclaObjeto.getSource();
					String text = textField.getText ();
					textField.setText(text.substring(0,text.length() -1));
				}
			}
		};
	
 	public int claseDeRed(String cad) {

        int valor = Integer.parseInt(cad);
        int salida = 0;

        if ((valor >=0) && (valor <= 127)) {
            salida = 1;
        } else if ((valor >= 128) && (valor <= 191)) {
            salida = 2;
        } else if ((valor >= 192) && (valor <= 223)) {
            salida = 3;
        } else if ((valor >= 224) ) {
            salida = 4;
        } 
        return salida;
    }
			
	public String BorrarDatos (){
    	String Vaciar = "0";
    	
    	JTIP1.setText(Vaciar);
        JTIP2.setText(Vaciar);
        JTIP3.setText(Vaciar);
        JTIP4.setText(Vaciar);
        mask1.setText(Vaciar);
        smask1.setText(Vaciar);
        Lsaltos.setText(Vaciar);
        LN.setText(Vaciar);
        JTH.setText("");
        JTsub.setText("");
        Jarea.setText("");
        ban = false;
        ban1 = false;
        
        JTIP1.grabFocus();
        
       return Vaciar;
    }
    
    public void valida(){
    	C1 = claseDeRed(JTIP1.getText());
		if(C1 == 1){
						mask1.setText("255.0.0.0"+"     Clase A"); 
		}else	
		if(C1 == 2){
						mask1.setText("255.255.0.0"+"     Clase B"); 
		}else
		if(C1 == 3){
						mask1.setText("255.255.255.0"+"   Clase C"); 
		}else   JOptionPane.showMessageDialog(null,"Elige una ip menor a 224.0.0.0","ERROR",JOptionPane.INFORMATION_MESSAGE);				
    }
    
    public void impresiones(int o){
    	//clase A segundo octeto
    	if(o==1){
	    	for(int i=0; i<3;i++){
						Jarea.append(i+"\t"+A+"."+B+"."+C+"."+D+"\t"+A+"."+B+"."+C+"."+(D+1)+"\t"+A+"."+(B+(sal-1))+".255.254\t"+A+"."+(B+(sal-1))+".255.255\n");
						B=B+sal;
			}
			B=(TS-3)*sal;
			for(int i=(TS-3); i<TS;i++){
				Jarea.append(i+"\t"+A+"."+B+"."+C+"."+D+"\t"+A+"."+B+"."+C+"."+(D+1)+"\t"+A+"."+(B+(sal-1))+".255.254\t"+A+"."+(B+(sal-1))+".255.255\n");
				B=B+sal;
			}
    	}
    	//Clase A tercer octeto
    	if(o==2){
    		for(int i=0; i<3;i++){
					//SALTO SE HACE EN TERCER OCTETO
					Jarea.append(i+"\t"+A+"."+B+"."+C+"."+D+"\t"+A+"."+B+"."+C+"."+(D+1)+"\t"+A+"."+B+"."+(C+(sal-1))+".254\t"+A+"."+B+"."+(C+(sal-1))+".255\n");
					C=C+sal;
					if(C==256){
						C=0;
						B++;
					}
			}
				
			/*
			IP =(saltos*no. subred)/256
			partes decimal * 256
			*/	
			float temp =(float) (sal*(TS-3))/256;
			B=(int)temp; 			  //Se toma parte entera
			float temp2 = temp-B;//Se resta parte entera y se toma parte decimal
			C=(int)(temp2*256);    //Parte decimal se multiplica * 256
				
			for(int i=(TS-3); i<TS;i++){
				Jarea.append(i+"\t"+A+"."+B+"."+C+"."+D+"\t"+A+"."+B+"."+C+"."+(D+1)+"\t"+A+"."+B+"."+(C+(sal-1))+".254\t"+A+"."+B+"."+(C+(sal-1))+".255\n");
				C=C+sal;
				if(C==256){
					C=0;
					B++;
				}
			}
    	}
		//Clase A cuarto octeto
		if(o==3){
			for(int i=0; i<3;i++){
				Jarea.append(i+"\t"+A+"."+B+"."+C+"."+D+"\t"+A+"."+B+"."+C+"."+(D+1)+"\t"+A+"."+B+"."+C+"."+(D+(sal-2))+"\t"+A+"."+B+"."+C+"."+(D+(sal-1))+"\n");
				D=D+sal;
				if(D==256){
					D=0;
					C++;
					if(C==256){
						C=0;
						B++;
					}
				}
			}
			/*
			 IP = (saltos*No.sub)/65536-> (256*256)
			 se toma parte entera
			 parte decimal * 256
			 se toma parte entera
			 parte decimal * 256				 
			 */
				 
			float temp =(float) (sal*(TS-3))/65536;
			B=(int)temp; 			  //Se toma parte entera
			float temp2 = temp-B;//Se resta parte entera y se toma parte decimal
			temp2 = temp2*256;    //Parte decimal se multiplica * 256
			C=(int)temp2;
			float temp3 =temp2-C;
			D=(int)(temp3*256); 
				
			for(int i=(TS-3); i<TS;i++){
				Jarea.append(i+"\t"+A+"."+B+"."+C+"."+D+"\t"+A+"."+B+"."+C+"."+(D+1)+"\t"+A+"."+B+"."+C+"."+(D+(sal-2))+"\t"+A+"."+B+"."+C+"."+(D+(sal-1))+"\n");
				D=D+sal;
				if(D==256){
					D=0;
					C++;
					if(C==256){
						C=0;
						B++;
					}
				}
			}
		}
		//Clase B Tercer octeto
		if(o==4){
			for(int i=0; i<3;i++){
				Jarea.append(i+"\t"+A+"."+B+"."+C+"."+D+"\t"+A+"."+B+"."+C+"."+(D+1)+"\t"+A+"."+B+"."+(C+(sal-1))+".254\t"+A+"."+B+"."+(C+(sal-1))+".255\n");
				C=C+sal;
			}
				
			C=(TS-3)*sal;
			for(int i=(TS-3); i<TS;i++){
				Jarea.append(i+"\t"+A+"."+B+"."+C+"."+D+"\t"+A+"."+B+"."+C+"."+(D+1)+"\t"+A+"."+B+"."+(C+(sal-1))+".254\t"+A+"."+B+"."+(C+(sal-1))+".255\n");
				C=C+sal;
			}
		}
		//Clase B cuarto octeto
		if(o==5){
			for(int i=0; i<3;i++){
				//SALTO SE HACE EN CUARTO OCTETO
				Jarea.append(i+"\t"+A+"."+B+"."+C+"."+D+"\t"+A+"."+B+"."+C+"."+(D+1)+"\t"+A+"."+B+"."+C+"."+(D+(sal-2))+"\t"+A+"."+B+"."+C+"."+(D+(sal-1))+"\n");
				D=D+sal;
			}
				
			/*
			IP =(saltos*no. subred)/256
			partes decimal * 256
			*/			
			float temp =(float) (sal*(TS-3))/256;
			C=(int)temp; 			  //Se toma parte entera
			float temp2 = temp-C;//Se resta parte entera y se toma parte decimal
			D=(int)(temp2*256);    //Parte decimal se multiplica * 256*/
				
			for(int i=(TS-3); i<TS;i++){
				Jarea.append(i+"\t"+A+"."+B+"."+C+"."+D+"\t"+A+"."+B+"."+C+"."+(D+1)+"\t"+A+"."+B+"."+C+"."+(D+(sal-2))+"\t"+A+"."+B+"."+C+"."+(D+(sal-1))+"\n");
				D=D+sal;
				if(D==256){
					D=0;
					C++;
				}
			}
		}
		//Clase C
		if(o==6){
			for(int i=0; i<3;i++){
				//SALTO SE HACE EN CUARTO OCTETO
				Jarea.append(i+"\t"+A+"."+B+"."+C+"."+D+"\t"+A+"."+B+"."+C+"."+(D+1)+"\t"+A+"."+B+"."+C+"."+(D+(sal-2))+"\t"+A+"."+B+"."+C+"."+(D+(sal-1))+"\n");
				D=D+sal;
			}
				
			D=(TS-3)*sal;
			for(int i=(TS-3); i<TS;i++){
				Jarea.append(i+"\t"+A+"."+B+"."+C+"."+D+"\t"+A+"."+B+"."+C+"."+(D+1)+"\t"+A+"."+B+"."+C+"."+(D+(sal-2))+"\t"+A+"."+B+"."+C+"."+(D+(sal-1))+"\n");
				D=D+sal;
			}
		}
    }
    
    public void hosts(){
    	
    	A = Integer.parseInt(JTIP1.getText());
    	B = Integer.parseInt(JTIP2.getText());
    	C = Integer.parseInt(JTIP3.getText());
    	D = Integer.parseInt(JTIP4.getText());
    	H = Integer.parseInt(JTH.getText());				
		n=1;
		int x =0;
		while(ban==false){							
		   x = (int)Math.pow((int)2,(int)n);
		   if((x-2)>=H){
			   ban=true;
			}else 
			   n++;
		}
		
		LN.setText(Integer.toString(n));			
		/*CLASE A**/
		if(C1==1){
			//--------------------------------------------------------------------------
			int y=24-(n);
			TS = (int)Math.pow(2,y);
			JTsub.setText(Integer.toString(TS));	
			//--------------------------------------------------------------------------
			//segundo octeto clase a			
			if(y<=8){				
				for(int i=7;i>=(8-y);i--){									
					oc1 =(int)(Math.pow(2,i))+oc1;
				}
				
				B=C=D=0;
				sal=256-oc1;
				Lsaltos.setText(Integer.toString(sal));
				smask1.setText("255."+oc1+".0.0");
				impresiones(1);			
			}else
							
			//tercero octeto clase a
			if(y<=16){
				y=y-8;				
				for(int i=7;i>=(8-y);i--){									
					oc3=(int)(Math.pow(2,i))+oc3;
				}
				B=C=D=0;			
				sal=256-oc3;
				smask1.setText("255.255."+oc3+".0");
				Lsaltos.setText(Integer.toString(sal));
				impresiones(2);
			}else 
							
			//Cuarto octeto clase A
			if(y<=24){
				y=y-16;				
				for(int i=7;i>=(8-y);i--){
					oc4=(int)(Math.pow(2,i))+oc4;
				}
				B=C=D=0;
				sal=256-oc4;
				smask1.setText(" 255.255.255."+oc4+"");
				Lsaltos.setText(Integer.toString(sal));	
				impresiones(3);	
			}					
		}
						
		/*Clase B*/
		if(C1==2){
			//--------------------------------------------------------------------------
			int y=16-(n);
			TS = (int)Math.pow(2,y);
			JTsub.setText(Integer.toString(TS));
			//--------------------------------------------------------------------------												
			//Clase B Tercer Octeto
			if(y<=8){
				C=D=0;				
				for(int i=7;i>=(8-y);i--){
					oc3=(int)(Math.pow(2,i))+oc3;
				}
				
				sal=256-oc3;
				smask1.setText("255.255."+oc3+".0");
				Lsaltos.setText(Integer.toString(sal));	
				impresiones(4);						
			}else 
							
			//Clase B Cuarto Octeto						
			if(y<=16){
				y=y-8;			
				C=D=0;	
				for(int i=7;i>=(8-y);i--){									
					oc4=(int)(Math.pow(2,i))+oc4;
				}
				
				sal=256-oc4;
				smask1.setText("255.255.255."+oc4);
				Lsaltos.setText(Integer.toString(sal));				
				impresiones(5);
			}								
		}
						
		/*Clase C*/
		if(C1==3){
			//--------------------------------------------------------------------------
			int y=8-(n);
			TS = (int)Math.pow(2,y);
			JTsub.setText(Integer.toString(TS));
			//--------------------------------------------------------------------------
			if(y<=8){				
				D=0;
				for(int i=7;i>=(8-y);i--){									
					oc4=(int)(Math.pow(2,i))+oc4;
				}
				
				sal=256-oc4;
				smask1.setText("255.255.255."+oc4);
				Lsaltos.setText(Integer.toString(sal));
				impresiones(6);
				
			}
		}
    }
    
    public void subr(){
    	
    	A = Integer.parseInt(JTIP1.getText());
    	B = Integer.parseInt(JTIP2.getText());
    	C = Integer.parseInt(JTIP3.getText());
    	D = Integer.parseInt(JTIP4.getText());
    	S = Integer.parseInt(JTsub.getText());
						
		n1=1;
		int x =0;
		while(ban==false){							
			x = (int)Math.pow((int)2,(int)n1);
			if((x-2)>=S){
				ban=true;
			}else 
				n1++;
		}
		
		LN.setText(Integer.toString(n1 ));
						
		/*CLASE A**/
		if(C1==1){			
			//--------------------------------------------------------------------------				
			int y=24-(n1);
			TH = (int)Math.pow(2,y);
			JTH.setText(Integer.toString(TH));
			y=(n1);
			TS= (int)Math.pow(2,y);
			JTsub.setText(Integer.toString(TS));
			//--------------------------------------------------------------------------
			//segundo octeto clase a
			if(y<=8){				
				for(int i=7;i>=(8-y);i--){   									
					oc1 =(int)(Math.pow(2,i))+oc1;
				}
				B=C=D=0;
				sal=256-oc1;
				Lsaltos.setText(Integer.toString(sal));
				smask1.setText("255."+oc1+".0.0");
				impresiones(1);
								
			}else			
			//tercero octeto clase a
			if(y<=16){
				y=y-8;				
				for(int i=7;i>=(8-y);i--){									
					oc3=(int)(Math.pow(2,i))+oc3;
				}
				B=C=D=0;
				sal=256-oc3;
				smask1.setText("255.255."+oc3+".0");
				Lsaltos.setText(Integer.toString(sal));
				impresiones(2);
			}else 
			//Cuarto octeto clase A
			if(y<=24){
				y=y-16;				
				for(int i=7;i>=(8-y);i--){		
					oc4=(int)(Math.pow(2,i))+oc4;
				}
				B=C=D=0;	
				sal=256-oc4;
				smask1.setText(" 255.255.255."+oc4+"");
				Lsaltos.setText(Integer.toString(sal));
				impresiones(3);
			}				
		}
		/*Clase B x subred*/
		if(C1==2){
			//--------------------------------------------------------------------------
			int y=16-(n1);
			TH = (int)Math.pow(2,y);
			JTH.setText(Integer.toString(TH));
			y=(n1);
			TS= (int)Math.pow(2,y);
			JTsub.setText(Integer.toString(TS));
			
			//--------------------------------------------------------------------------							
			//Tercer octeto clase B
			if(y<=8){				
				for(int i=7;i>=(8-y);i--){
					oc3=(int)(Math.pow(2,i))+oc3;
				}
				C=D=0;
				sal=256-oc3;
				smask1.setText("255.255."+oc3+".0");
				Lsaltos.setText(Integer.toString(sal));
				impresiones(4);
				
			}else 
			//Clase B Cuarto Octeto						
			if(y<=16){
				y=y-8;				
				for(int i=7;i>=(8-y);i--){									
					oc4=(int)(Math.pow(2,i))+oc4;
				}
				C=D=0;
				sal=256-oc4;
				smask1.setText("255.255.255."+oc4);
				Lsaltos.setText(Integer.toString(sal));
				impresiones(5);
			}
		}
		/*CLASE C*/
		if(C1==3){
			//--------------------------------------------------------------------------
			int y=8-(n1);
			TH = (int)Math.pow(2,y);
			JTH.setText(Integer.toString(TH));
			y=(n1);
			TS= (int)Math.pow(2,y);
			JTsub.setText(Integer.toString(TS));
			//--------------------------------------------------------------------------
			if(y<=8){	
				D=0;			
				for(int i=7;i>=(8-y);i--){								
					oc4=(int)(Math.pow(2,i))+oc4;
				}
				
				sal=256-oc4;
				smask1.setText("255.255.255."+oc4);
				Lsaltos.setText(Integer.toString(sal));
				impresiones(6);				
			}
		}
    }
    
    public void calcula (){   
				
		if(JTIP1.getText().length()>0 && JTIP2.getText().length()>0 && JTIP3.getText().length()>0 && JTIP4.getText().length()>0){
			
			valida();//Identifica la clase y asigna la mascara de red correspondiente
			Jarea.setText("No. Subred\tSubred   \tIP inicial   \tIPfinal   \tBroadcast\n\n");
			//CALCULO X HOST------------------------------------------------------------------------------------------------------------------------------------------------------------
			if(JTH.getText().length()>0){
				hosts();		
				C1=H =S = sal = oc1= oc2 = oc3 =  oc4 =TS = TH= 0;	
    			n=n1=1;				
			}else		
			//CALCULO X SUBRED-----------------------------------------------------------------------------------------------------------------------------------------------------------
			if(JTsub.getText().length()>0){						
				subr();
				C1=H =S = sal = oc1= oc2 = oc3 =  oc4 =TS = TH= 0;	
    			n=n1=1;		
			}
		}else{
				JOptionPane.showMessageDialog(null,"CAMPO VACIO","ERROR",JOptionPane.INFORMATION_MESSAGE);
				JTIP1.grabFocus();
		}
    }
     // JFRAME INTERFAZ
    public CALCULADORA(){
    	setLayout(null);
    	    	
    	
    	
    	JLabel Nombre = new JLabel ("IP CALCULADORA", SwingConstants.CENTER);
		Nombre.setBounds(new Rectangle(90,20,400,30));
		Nombre.setForeground(Color.BLACK);
		add(Nombre);

		JLabel IP = new JLabel ("IP:", SwingConstants.CENTER);
		IP.setBounds(new Rectangle(10,80,120,30));
		IP.setForeground(Color.BLUE);
		add(IP);
		
		JLabel mask = new JLabel ("Mascara de Red:", SwingConstants.RIGHT);
		mask.setBounds(new Rectangle(10,110,120,30));
		mask.setForeground(Color.BLUE);
		add(mask);
		
		mask1 = new JLabel ("", SwingConstants.LEFT);
		mask1.setBounds(new Rectangle(150,110,200,30));
		add(mask1);
		
		JLabel JLSalto = new JLabel ("Saltos:", SwingConstants.RIGHT);
		JLSalto.setBounds(new Rectangle(210,110,120,30));
		JLSalto.setForeground(Color.BLUE);
		add(JLSalto);
		
		Lsaltos = new JLabel ("", SwingConstants.LEFT);
		Lsaltos.setBounds(new Rectangle(350,110,200,30));
		add(Lsaltos);
		
		JLabel JLN = new JLabel ("n:", SwingConstants.RIGHT);
		JLN.setBounds(new Rectangle(210,140,120,30));
		JLN.setForeground(Color.BLUE);
		add(JLN);
		
		LN = new JLabel ("", SwingConstants.LEFT);
		LN.setBounds(new Rectangle(350,140,200,30));
		add(LN);
		
		JLabel smask = new JLabel ("Mascara de Subred:", SwingConstants.RIGHT);
		smask.setBounds(new Rectangle(10,140,120,30));
		smask.setForeground(Color.BLUE);
		add(smask);
		
		smask1 = new JLabel ("", SwingConstants.LEFT);
		smask1.setBounds(new Rectangle(150,140,200,30));
		add(smask1);
		
		JTIP1 = new JTextField ("0");
		JTIP1.setBounds(new Rectangle(130,80,50,30));
		JTIP1.setBackground(Color.BLACK);
		JTIP1.setForeground(Color.RED);
		JTIP1.addKeyListener(Validacion);
		JTIP1.setHorizontalAlignment(JTextField.CENTER);
		add(JTIP1);
		
		JTIP2 = new JTextField ("0");
		JTIP2.setBounds(new Rectangle(250,80,50,30));
		JTIP2.setBackground(Color.BLACK);
		JTIP2.setForeground(Color.RED);
		JTIP2.addKeyListener(Validacion);
		JTIP2.setHorizontalAlignment(JTextField.CENTER);
		add(JTIP2);
		
		JTIP3 = new JTextField ("0");
		JTIP3.setBounds(new Rectangle(370,80,50,30));
		JTIP3.setBackground(Color.BLACK);
		JTIP3.setForeground(Color.RED);
		JTIP3.addKeyListener(Validacion);
		JTIP3.setHorizontalAlignment(JTextField.CENTER);
		add(JTIP3);
    	
    	JTIP4 = new JTextField ("0");
		JTIP4.setBounds(new Rectangle(490,80,50,30));
		JTIP4.setBackground(Color.BLACK);
		JTIP4.setForeground(Color.RED);
		JTIP4.addKeyListener(Validacion);
		JTIP4.setHorizontalAlignment(JTextField.CENTER);
		add(JTIP4);
    
    	JTsub = new JTextField ();
		JTsub.setBounds(new Rectangle(370,170,80,30));
		JTsub.addKeyListener(Validacion);
		JTsub.setHorizontalAlignment(JTextField.CENTER);
		JTsub.setEnabled(false);
		add(JTsub);
		
		JTH = new JTextField ();
		JTH.setBounds(new Rectangle(150,170,50,30));
		JTH.addKeyListener(Validacion);
		JTH.setHorizontalAlignment(JTextField.CENTER);
		JTH.setEnabled(false);
		add(JTH);
		
		Jarea=new JTextArea(" ");
        Jarea.setBounds(20,240,540,250);
        Jarea.setEditable(false);
        add(Jarea);
		
		Font LetraAzul = new Font("Arial",Font.BOLD,24);		
		Nombre.setFont(LetraAzul);
				
    	ActionListener Iniciar = new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				calcula();
				Calip.setEnabled(false);
        	}
        };
        
        ActionListener Borrar = new ActionListener(){
			public void actionPerformed(ActionEvent evt){
			     BorrarDatos();
			     Calip.setEnabled(true);
        	}
        };
        
         ActionListener Host = new ActionListener(){
			public void actionPerformed(ActionEvent evt){
			     JTH.setEnabled(true);
			     JTsub.setEnabled(false);
			     JTsub.setText("");
        	}
        };
         ActionListener Sub = new ActionListener(){
			public void actionPerformed(ActionEvent evt){
			     JTH.setEnabled(false);
			     JTsub.setEnabled(true);
			     JTH.setText("");
        	}
        };
        BotonHost = new JRadioButton("Calcular/Host",true);
    	BotonHost.setBounds(new Rectangle(10,170,130,35));
    	add(BotonHost);
    	BotonHost.addActionListener(Host);
    	
    	BotonS = new JRadioButton("Calcular/Subred",false);
    	BotonS.setBounds(new Rectangle(230,170,140,35));
    	add(BotonS);
    	BotonS.addActionListener(Sub);
    	BGGrupoBotonesOpcion = new ButtonGroup();
    	
    	BGGrupoBotonesOpcion.add(BotonHost);
    	BGGrupoBotonesOpcion.add(BotonS);
        
        Calip = new JButton("Iniciar");
        Calip.setBounds(new Rectangle(470,130,80,30));
        add(Calip);
        Calip.addActionListener(Iniciar);
        
        JButton BorrarDatos = new JButton("Borrar");
        BorrarDatos.setBounds(new Rectangle(470,168,80,30));
        add(BorrarDatos);
        BorrarDatos.addActionListener(Borrar);
    }
    // LA VENTANA DE LA CALCULADORA		
   	public static void main(String []args){
    		JFrame Ventana = new JFrame("Calculadora IP");
    		Ventana.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    		Ventana.getContentPane().add(new CALCULADORA(),BorderLayout.CENTER);
    		Ventana.setSize(600,550);
    		Ventana.setLocationRelativeTo(null);
    		Ventana.setVisible(true);
    	}
    }