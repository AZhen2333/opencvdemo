package opencvdemo;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class FaceEye {

	static {
		// 载入opencv的库
		// String opencvpath = System.getProperty("user.dir") + "\\opencv\\x64\\";
		// String opencvDllName = opencvpath + Core.NATIVE_LIBRARY_NAME + ".dll";
		// System.load(opencvDllName);
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	/**
	 * 人脸识别
	 * 
	 * @param imagePath
	 * @param outFile
	 * @return
	 * @throws Exception
	 */
	public static Mat detectFace(String imagePath, String outFile) throws Exception {
		System.out.println("Running DetectFace ... ");
		// 从配置文件lbpcascade_frontalface.xml中创建一个人脸识别器，该文件位于opencv安装目录中
		CascadeClassifier faceDetector = new CascadeClassifier(
				"D:\\Azhen\\install\\opencv3\\opencv\\sources\\data\\haarcascades\\haarcascade_frontalface_alt.xml");

		Mat image = Imgcodecs.imread(imagePath);

		// 在图片中检测人脸
		MatOfRect faceDetections = new MatOfRect();

		faceDetector.detectMultiScale(image, faceDetections);

		System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));

		Rect[] rects = faceDetections.toArray();
		if (rects == null || rects.length == 0) {
			throw new RuntimeException("无法检测到人脸");
		}
		Rect rectMax = new Rect();
		int max = 0;
		for (Rect rect : rects) {
			Imgproc.rectangle(image, new Point(rect.x - 2, rect.y - 2),
					new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));
			if (rect.width * rect.height > max) {
				max = rect.width * rect.height;
				rectMax = rect;
			}
		}
		Imgcodecs.imwrite(outFile, image);
		System.out.println(String.format("人脸识别成功，人脸图片文件为： %s", outFile));
		if (rectMax != null) {
			// 截图
			Mat cutMat = imageCut(image, rectMax, "C:\\Users\\CaiZhenZhong\\Desktop\\personCut.jpg");
			return cutMat;
		}
		return null;
	}

	/**
	 * 剪切图片
	 * 
	 * @param image
	 * @param rect
	 * @param outFile
	 * @return
	 */
	public static Mat imageCut(Mat image, Rect rect, String outFile) {
		System.err.println(rect.toString());
		int y = (int) (rect.y * 0.8);
		Rect rect1 = new Rect(rect.x, rect.y - y, rect.width, rect.height);
		// 两句效果一样
		Mat sub = image.submat(rect1); // Mat sub = new Mat(image,rect);
		Mat mat = new Mat();
		Size size = new Size(300, 300);
		Imgproc.cvtColor(sub, mat, Imgproc.COLOR_BGR2GRAY, 0);
		Imgproc.resize(sub, mat, size);// 将人脸进行截图并保存
		Imgcodecs.imwrite(outFile, mat);
		System.out.println(String.format("图片裁切成功，裁切后图片文件为： %s", "C:\\Users\\CaiZhenZhong\\Desktop\\personCut.jpg"));
		return mat;
	}

	/**
	 * 睁眼和眼睛识别
	 * 
	 * @param src
	 * @return
	 */
	public static Mat dobj(Mat src) {
		Mat dst = src.clone();
		Mat dstImage = new Mat();
		// 灰度处理
		Imgproc.cvtColor(dst, dstImage, Imgproc.COLOR_BGR2GRAY, 0);

		// 左眼识别xml
		CascadeClassifier leftObjDetector = new CascadeClassifier(
				"D:\\Azhen\\install\\opencv3\\opencv\\sources\\data\\haarcascades\\haarcascade_lefteye_2splits.xml");
		MatOfRect leftObjDetections = new MatOfRect();
		leftObjDetector.detectMultiScale(dstImage, leftObjDetections);
		System.out.println(String.format("Detected %s left eyes", leftObjDetections.toArray().length));
		if (leftObjDetections.toArray().length <= 0) {
			return src;
		}
		for (Rect rect : leftObjDetections.toArray()) {
			System.out.println(String.format("left eyes-x: %s ", rect.x));
			Imgproc.rectangle(dstImage, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
					new Scalar(0, 0, 255), 2);
		}

		// 右眼识别xml
		CascadeClassifier rightObjDetector = new CascadeClassifier(
				"D:\\Azhen\\install\\opencv3\\opencv\\sources\\data\\haarcascades\\haarcascade_righteye_2splits.xml");
		MatOfRect rightObjDetections = new MatOfRect();
		rightObjDetector.detectMultiScale(dstImage, rightObjDetections);
		System.out.println(String.format("Detected %s eyes", rightObjDetections.toArray().length));
		if (rightObjDetections.toArray().length <= 0) {
			return src;
		}
		for (Rect rect : rightObjDetections.toArray()) {
			System.out.println(String.format("right eyes-x: %s ", rect.x));
			Imgproc.rectangle(dstImage, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
					new Scalar(0, 0, 255), 2);
		}

		int leftUnm = leftObjDetections.toArray().length;
		int rightUnm = rightObjDetections.toArray().length;
		if (leftUnm == 2 && rightUnm == 2) {
			System.err.println("双眼睁开");
		} else if (leftUnm == 1 && rightUnm == 1) {
			System.err.println("双眼闭眼");
		} else if (leftUnm == 1 && rightUnm == 2) {
			System.err.println("右眼闭眼");
		} else if (leftUnm == 2 && rightUnm == 1) {
			System.err.println("左眼闭眼");
		} else {
			System.err.println("检测眼睛失败");
		}
		Imgcodecs.imwrite("C:\\Users\\CaiZhenZhong\\Desktop\\personEyeDetect22.jpg", dstImage);
		return dstImage;
	}

	public static void main(String[] args) throws Exception {
		// 人脸识别
		Mat src = detectFace("C:\\Users\\CaiZhenZhong\\Desktop\\v\\73.jpg",
				"C:\\Users\\CaiZhenZhong\\Desktop\\personFaceDetect.png");
		// 眼睛识别
		if (src != null) {
			if (src.empty()) {
				throw new Exception("no file");
			}
			Mat dst = dobj(src);
		}
	}
}
