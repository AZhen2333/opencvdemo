package opencvdemo;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.interfaces.RSAKey;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

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

public class DetectFaceTest {
	static {
		// 载入opencv的库
		// String opencvpath = System.getProperty("user.dir") + "\\opencv\\x64\\";
		// String opencvDllName = opencvpath + Core.NATIVE_LIBRARY_NAME + ".dll";
		// System.load(opencvDllName);
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	/**
	 * opencv实现人脸识别
	 * 
	 * @param imagePath
	 * @param outFile
	 * @throws Exception
	 */
	public static void detectFace(String imagePath, String outFile) throws Exception {

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
//		if (rects != null && rects.length > 1) {
//			throw new RuntimeException("超过一个脸");
//		}
		// 在每一个识别出来的人脸周围画出一个方框
//		Rect rect = rects[0];
		for (Rect rect : rects) {
			Imgproc.rectangle(image, new Point(rect.x - 2, rect.y - 2),
					new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));
		}
		Imgcodecs.imwrite(outFile, image);

		System.out.println(String.format("人脸识别成功，人脸图片文件为： %s", outFile));
	}

	public static Mat detectFaceBoo(String imagePath, String outFile) throws Exception {

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
			System.out.println("没检测到脸");
			return null;
		} else {
			System.out.println(String.format("人脸识别成功，人脸图片文件为： %s", outFile));
			return image;
		}
	}

	public static void detectFace1(String imagePath, String outFile) throws Exception {
		Mat image1 = detectFaceBoo(imagePath, outFile);
		// if (image1 != null) {
		System.out.println("Running DetectFace ... ");
		// 从配置文件lbpcascade_frontalface.xml中创建一个人脸识别器，该文件位于opencv安装目录中
		CascadeClassifier faceDetector = new CascadeClassifier(
				"D:\\Azhen\\install\\opencv3\\opencv\\sources\\data\\haarcascades\\cascade.xml");

		Mat image = Imgcodecs.imread(imagePath);
		Mat dstImage = new Mat();
		// 灰度处理
		Imgproc.cvtColor(image, dstImage, Imgproc.COLOR_BGR2GRAY, 0);
		// 在图片中检测人脸
		MatOfRect faceDetections = new MatOfRect();

		faceDetector.detectMultiScale(dstImage, faceDetections, 1.16, 1, 1, new Size(25, 25), new Size(25, 25));
		System.out.println(String.format("Detected %s hand", faceDetections.toArray().length));

		Rect[] rects = faceDetections.toArray();
		// 在每一个识别出来的人脸周围画出一个方框
		if (rects != null && rects.length > 1) {
			Rect rect = rects[0];
			Imgproc.rectangle(dstImage, new Point(rect.x - 2, rect.y - 2),
					new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));
			Imgcodecs.imwrite(outFile, dstImage);
			System.out.println(String.format("hand识别成功，图片文件为： %s", outFile));
		}
		// }

	}

	/**
	 * opencv实现人眼识别
	 * 
	 * @param imagePath
	 * @param outFile
	 * @throws Exception
	 */
	public static void detectEye(String imagePath, String outFile) throws Exception {

		CascadeClassifier eyeDetector = new CascadeClassifier(
				"D:\\Azhen\\install\\opencv3\\opencv\\sources\\data\\\\haarcascades\\haarcascade_eye.xml");

		Mat image = Imgcodecs.imread(imagePath); // 读取图片

		// 在图片中检测人脸
		MatOfRect faceDetections = new MatOfRect();

		eyeDetector.detectMultiScale(image, faceDetections, 1.1, 2, 0, new Size(20, 20), new Size(30, 30));

		System.out.println(String.format("Detected %s eyes", faceDetections.toArray().length));
		Rect[] rects = faceDetections.toArray();
		for (Rect rect : rects) {
			System.out.println(String.format(rect.toString()));
		}
		if (rects != null && rects.length < 2) {
			throw new RuntimeException("不是一双眼睛");
		}
		Rect eyea = rects[0];
		Rect eyeb = rects[1];

		System.out.println("a-中心坐标 " + eyea.x + " and " + eyea.y);
		System.out.println("b-中心坐标 " + eyeb.x + " and " + eyeb.y);

		// 获取两个人眼的角度
		double dy = (eyeb.y - eyea.y);
		double dx = (eyeb.x - eyea.x);
		double len = Math.sqrt(dx * dx + dy * dy);
		System.out.println("dx is " + dx);
		System.out.println("dy is " + dy);
		System.out.println("len is " + len);

		double angle = Math.atan2(Math.abs(dy), Math.abs(dx)) * 180.0 / Math.PI;
		System.out.println("angle is " + angle);
		for (Rect rect : faceDetections.toArray()) {
			Imgproc.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
					new Scalar(0, 255, 0), 2);
		}
		Imgcodecs.imwrite(outFile, image);

		System.out.println(String.format("人眼识别成功，人眼图片文件为： %s", outFile));

	}

	/**
	 * 裁剪图片并重新装换大小
	 * 
	 * @param imagePath
	 * @param posX
	 * @param posY
	 * @param width
	 * @param height
	 * @param outFile
	 */
	public static void imageCut(String imagePath, String outFile, int posX, int posY, int width, int height) {

		// 原始图像
		Mat image = Imgcodecs.imread(imagePath);

		// 截取的区域：参数,坐标X,坐标Y,截图宽度,截图长度
		Rect rect = new Rect(posX, posY, width, height);

		// 两句效果一样
		Mat sub = image.submat(rect); // Mat sub = new Mat(image,rect);

		Mat mat = new Mat();
		Size size = new Size(300, 300);
		Imgproc.resize(sub, mat, size);// 将人脸进行截图并保存

		Imgcodecs.imwrite(outFile, mat);
		System.out.println(String.format("图片裁切成功，裁切后图片文件为： %s", outFile));

	}

	/**
	 *
	 * @param imagePath
	 * @param outFile
	 */
	public static void setAlpha(String imagePath, String outFile) {
		/**
		 * 增加测试项 读取图片，绘制成半透明
		 */
		try {

			ImageIcon imageIcon = new ImageIcon(imagePath);

			BufferedImage bufferedImage = new BufferedImage(imageIcon.getIconWidth(), imageIcon.getIconHeight(),
					BufferedImage.TYPE_4BYTE_ABGR);

			Graphics2D g2D = (Graphics2D) bufferedImage.getGraphics();

			g2D.drawImage(imageIcon.getImage(), 0, 0, imageIcon.getImageObserver());

			// 循环每一个像素点，改变像素点的Alpha值
			int alpha = 100;
			for (int j1 = bufferedImage.getMinY(); j1 < bufferedImage.getHeight(); j1++) {
				for (int j2 = bufferedImage.getMinX(); j2 < bufferedImage.getWidth(); j2++) {
					int rgb = bufferedImage.getRGB(j2, j1);
					rgb = ((alpha + 1) << 24) | (rgb & 0x00ffffff);
					bufferedImage.setRGB(j2, j1, rgb);
				}
			}
			g2D.drawImage(bufferedImage, 0, 0, imageIcon.getImageObserver());

			// 生成图片为PNG
			ImageIO.write(bufferedImage, "png", new File(outFile));

			System.out.println(String.format("绘制图片半透明成功，图片文件为： %s", outFile));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 为图像添加水印
	 * 
	 * @param buffImgFile
	 *            底图
	 * @param waterImgFile
	 *            水印
	 * @param outFile
	 *            输出图片
	 * @param alpha
	 *            透明度
	 * @throws IOException
	 */
	private static void watermark(String buffImgFile, String waterImgFile, String outFile, float alpha)
			throws IOException {
		// 获取底图
		BufferedImage buffImg = ImageIO.read(new File(buffImgFile));

		// 获取层图
		BufferedImage waterImg = ImageIO.read(new File(waterImgFile));

		// 创建Graphics2D对象，用在底图对象上绘图
		Graphics2D g2d = buffImg.createGraphics();

		int waterImgWidth = waterImg.getWidth();// 获取水印层图的宽度

		int waterImgHeight = waterImg.getHeight();// 获取水印层图的高度

		// 在图形和图像中实现混合和透明效果
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));

		// 绘制
		g2d.drawImage(waterImg, 0, 0, waterImgWidth, waterImgHeight, null);

		g2d.dispose();// 释放图形上下文使用的系统资源

		// 生成图片为PNG
		ImageIO.write(buffImg, "png", new File(outFile));

		System.out.println(String.format("图片添加水印成功，图片文件为： %s", outFile));
	}

	/**
	 * 图片合成
	 * 
	 * @param image1
	 * @param image2
	 * @param posw
	 * @param posh
	 * @param outFile
	 * @return
	 */
	public static void simpleMerge(String image1, String image2, int posw, int posh, String outFile)
			throws IOException {

		// 获取底图
		BufferedImage buffImg1 = ImageIO.read(new File(image1));

		// 获取层图
		BufferedImage buffImg2 = ImageIO.read(new File(image2));

		// 合并两个图像
		int w1 = buffImg1.getWidth();
		int h1 = buffImg1.getHeight();

		int w2 = buffImg2.getWidth();
		int h2 = buffImg2.getHeight();

		BufferedImage imageSaved = new BufferedImage(w1, h1, BufferedImage.TYPE_INT_ARGB); // 创建一个新的内存图像

		Graphics2D g2d = imageSaved.createGraphics();

		g2d.drawImage(buffImg1, null, 0, 0); // 绘制背景图像

		for (int i = 0; i < w2; i++) {
			for (int j = 0; j < h2; j++) {
				int rgb1 = buffImg1.getRGB(i + posw, j + posh);
				int rgb2 = buffImg2.getRGB(i, j);

				/*
				 * if (rgb1 != rgb2) { rgb2 = rgb1 & rgb2; }
				 */
				imageSaved.setRGB(i + posw, j + posh, rgb2); // 修改像素值
			}
		}

		ImageIO.write(imageSaved, "png", new File(outFile));

		System.out.println(String.format("图片合成成功，合成图片文件为： %s", outFile));

	}

	public static Mat dobj(Mat src) {
		Mat dst = src.clone();
		Mat dstImage = new Mat();
		// 灰度处理
		Imgproc.cvtColor(dst, dstImage, Imgproc.COLOR_BGR2GRAY, 0);
		CascadeClassifier objDetector = new CascadeClassifier(
				"D:\\Azhen\\install\\opencv3\\opencv\\sources\\data\\haarcascades\\haarcascade_lefteye_2splits.xml");
		MatOfRect objDetections = new MatOfRect();
		objDetector.detectMultiScale(dstImage, objDetections);
		System.out.println(String.format("Detected %s eyes", objDetections.toArray().length));
		if (objDetections.toArray().length <= 0) {
			return src;
		}
		for (Rect rect : objDetections.toArray()) {
			Imgproc.rectangle(dstImage, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
					new Scalar(0, 0, 255), 2);
		}
		if(objDetections.toArray().length == 2) {
			System.err.println("睁眼");
		}else if(objDetections.toArray().length == 1) {
			System.err.println("闭眼");
		}else {
			System.err.println("检测眼睛失败");
		}
		return dstImage;
	}

	public static void main(String[] args) throws Exception {

		// 人脸识别
//		 detectFace("C:\\Users\\CaiZhenZhong\\Desktop\\facetest_yuan\\IMG_2854.JPG",
//		 "C:\\Users\\CaiZhenZhong\\Desktop\\personFaceDetect.png");

		// // 人眼识别
//		 detectEye("C:\\Users\\CaiZhenZhong\\Desktop\\personCut.jpg",
//		 "C:\\Users\\CaiZhenZhong\\Desktop\\personEyeDetect.jpg");

		// // 图片裁切
//		imageCut("C:\\Users\\CaiZhenZhong\\Desktop\\facetest\\IMG_3012.jpg",
//				"C:\\Users\\CaiZhenZhong\\Desktop\\personCut.jpg", 50, 50, 100, 100);
		//
		// // 设置图片为半透明
		// setAlpha("E:\\person.jpg", "E:\\personAlpha.png");
		//
		// // 为图片添加水印
//		 watermark("C:\\Users\\CaiZhenZhong\\Desktop\\personCut.jpg", "C:\\Users\\CaiZhenZhong\\Desktop\\v\\01.jpg", "E:\\personWaterMark.png", 0.2f);
		//
		// // 图片合成
//		 simpleMerge("C:\\Users\\CaiZhenZhong\\Desktop\\personCut.jpg", "C:\\Users\\CaiZhenZhong\\Desktop\\v\\01.jpg", 45, 50, "C:\\Users\\CaiZhenZhong\\Desktop\\personMerge.png");

		Mat src = Imgcodecs.imread("C:\\Users\\CaiZhenZhong\\Desktop\\personCut.jpg");
		if (src.empty()) {
			throw new Exception("no file");
		}
		Mat dst = dobj(src);
		Imgcodecs.imwrite("C:\\Users\\CaiZhenZhong\\Desktop\\personEyeDetect22.jpg", dst);
	}
}
