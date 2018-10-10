package opencvdemo;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class FaceDetector {
	public static void main(String[] args) {
		 // TODO Auto-generated method stub
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        System.out.println("\nRunning FaceDetector");
        CascadeClassifier faceDetector = new CascadeClassifier();
        faceDetector.load("C:\\Users\\CaiZhenZhong\\Desktop\\face\\data\\cascade.xml");
        Mat image = Imgcodecs.imread("C:\\Users\\CaiZhenZhong\\Desktop\\facetest_yuan\\IMG_2901.JPG");

        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(image, faceDetections);
        System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));
        for (Rect rect : faceDetections.toArray())
        {
            Imgproc.rectangle(image, new Point(rect.x, rect.y),
                    new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));
        }

        String filename = "C:\\Users\\CaiZhenZhong\\Desktop\\ouput.jpg";
        Imgcodecs.imwrite(filename, image);

	}
}
