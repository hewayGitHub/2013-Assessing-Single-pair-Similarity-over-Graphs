package share;

import java.io.*;
import java.util.*;

public class IOTools {

	/**
	 * 
	 * @param delete a directory even if it is not empty.
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static boolean deleteDir(File dir) throws FileNotFoundException,
			IOException {
		try {
			if (!dir.isDirectory()) {
				dir.delete();
			} else {
				File[] fileList = dir.listFiles();
				for (File f : fileList) {

					if (!f.isDirectory())
						f.delete();
					else
						deleteDir(f);
				}
				dir.delete();
			}
		} catch (FileNotFoundException e) {
			System.err.println("deleteDir() Exception:" + e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * read file into String
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static String readFileToStr(File file) throws IOException {
		StringBuffer sb = new StringBuffer();
		BufferedReader in = new BufferedReader(new FileReader(file));
		String s;
		while ((s = in.readLine()) != null) {
			sb.append(s);
			sb.append("\n");
		}
		in.close();
		return sb.toString();
	}

	/**
	 * read file into a String by specific char set
	 */
	public static String readFileString(File file, String charset) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					new FileInputStream(file), charset));
			StringBuffer fileStr = new StringBuffer();
			String s;
			while ((s = in.readLine()) != null)
				fileStr.append(s + "\r\n");
			in.close();
			return new String(fileStr);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * read file into bytes, never change encodings
	 */
	public static byte[] readFileBytes(File file) {
		try {
			BufferedInputStream in = new BufferedInputStream(
					new FileInputStream(file));
			byte[] saveBuf = new byte[0];
			byte[] buf = new byte[1000];
			while (true) {
				int size = in.read(buf);
				if (size < 0)
					break;
				byte[] newBuf = new byte[saveBuf.length + size];
				int i = 0;
				for (byte b : saveBuf) {
					newBuf[i] = b;
					i++;
				}
				for (int j = 0; j < size; j++)
					newBuf[i + j] = buf[j];
				saveBuf = newBuf;
			}
			if (in != null)
				in.close();
			return saveBuf;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * write a string into a file by specific char set
	 */
	public static void writeFileString(String fileStr, String fileName,
			String charset) {
		try {
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(fileName), charset));
			out.write(fileStr);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * write bytes into a file
	 */
	public static void writeFileBytes(byte[] bytes, String fileName) {
		try {
			BufferedOutputStream out = new BufferedOutputStream(
					new FileOutputStream(fileName));
			out.write(bytes);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// copy file
	public static void copyFile(File sourceFile, File targetFile)
			throws IOException {
		// 新建文件输入流并对它进行缓冲
		FileInputStream input = new FileInputStream(sourceFile);
		BufferedInputStream inBuff = new BufferedInputStream(input);

		// 新建文件输出流并对它进行缓冲
		FileOutputStream output = new FileOutputStream(targetFile);
		BufferedOutputStream outBuff = new BufferedOutputStream(output);

		// 缓冲数组
		byte[] b = new byte[1024 * 5];
		int len;
		while ((len = inBuff.read(b)) != -1) {
			outBuff.write(b, 0, len);
		}
		// 刷新此缓冲的输出流
		outBuff.flush();

		//关闭流
		inBuff.close();
		outBuff.close();
		output.close();
		input.close();
	}

	// copy directory
	public static void copyDirectiory(String sourceDir, String targetDir)
			throws IOException {
		// 新建目标目录
		(new File(targetDir)).mkdirs();
		// 获取源文件夹当前下的文件或目录
		File[] file = (new File(sourceDir)).listFiles();
		for (int i = 0; i < file.length; i++) {
			if (file[i].isFile()) {
				// 源文件
				File sourceFile = file[i];
				// 目标文件
				File targetFile = new File(new File(targetDir)
						.getAbsolutePath()
						+ File.separator + file[i].getName());
				copyFile(sourceFile, targetFile);
			}
			if (file[i].isDirectory()) {
				// 准备复制的源文件夹
				String dir1 = sourceDir + "/" + file[i].getName();
				// 准备复制的目标文件夹
				String dir2 = targetDir + "/" + file[i].getName();
				copyDirectiory(dir1, dir2);
			}
		}
	}

}
