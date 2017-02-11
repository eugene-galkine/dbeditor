package dbeditor;

/*import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFileChooser;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.sun.xml.internal.messaging.saaj.util.Base64;*/

public class QRCodeHandler
{
	/*public static void createQR(DBPerson p)
	{
		String qrCodeData = new String(Base64.encode((p.getFName() + "," + p.getLName() + "," + p.getID()).getBytes()));
		String filePath = "QRCode.png";
		JFileChooser chooser = new JFileChooser("Save QR Code");
	    chooser.setCurrentDirectory(new File("/home/me/Desktop"));
	    int retrival = chooser.showSaveDialog(null);
	    if (retrival == JFileChooser.APPROVE_OPTION) 
	        filePath = chooser.getSelectedFile() + ".png";
	    else
	    	return;

		String charset = "UTF-8";
		Map hintMap = new HashMap();
		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);

		createQRCode(qrCodeData, filePath, charset, hintMap, 200, 200);
	}
	
	private static void createQRCode(String qrCodeData, String filePath, String charset, Map hintMap, int qrCodeheight, int qrCodewidth)
	{
		try
		{
			BitMatrix matrix = new MultiFormatWriter().encode(
				new String(qrCodeData.getBytes(charset), charset),
				BarcodeFormat.QR_CODE, qrCodewidth, qrCodeheight, hintMap);
			MatrixToImageWriter.writeToFile(matrix, filePath.substring(filePath.lastIndexOf('.') + 1), new File(filePath));
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}*/
}
