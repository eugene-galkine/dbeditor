package dbeditor;

import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;


public class Main
{
	private static MainView mv;
	
	public static void main(String[] args) 
	{
		//System.out.println(new String(Base64.encode("Dylan,Lehotsky,1".getBytes())));
		
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					mv = new MainView();
					mv.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
		
		String qrCodeData = "Hello World!";
		String filePath = "QRCode.png";
		String charset = "UTF-8"; // or "ISO-8859-1"
		Map hintMap = new HashMap();
		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

		try {
			createQRCode(qrCodeData, filePath, charset, hintMap, 200, 200);
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static MainView getMainView()
	{
		return mv;
	}
	
	public static void createQRCode(String qrCodeData, String filePath,
			String charset, Map hintMap, int qrCodeheight, int qrCodewidth)
			throws WriterException, IOException {
		BitMatrix matrix = new MultiFormatWriter().encode(
				new String(qrCodeData.getBytes(charset), charset),
				BarcodeFormat.QR_CODE, qrCodewidth, qrCodeheight, hintMap);
		MatrixToImageWriter.writeToFile(matrix, filePath.substring(filePath
				.lastIndexOf('.') + 1), new File(filePath));
	}
}
